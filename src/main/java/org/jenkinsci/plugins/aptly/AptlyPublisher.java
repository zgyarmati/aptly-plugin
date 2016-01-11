package org.jenkinsci.plugins.aptly;

import hudson.Extension;
import hudson.Launcher;
import hudson.Util;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.model.BuildListener;
import hudson.model.Descriptor;
import hudson.model.Result;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.BuildStepMonitor;
import hudson.tasks.Notifier;
import hudson.tasks.Publisher;
import hudson.util.CopyOnWriteList;
import hudson.util.FormValidation;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONObject;

import org.kohsuke.stapler.StaplerRequest;

/**
 * This class implements the Aptly publisher, takes care of orchestring the
 * configuration and the uploading/publishing process
 *
 * @author $Author: zgyarmati <mr.zoltan.gyarmati@gmail.com>
 *
 */
public class AptlyPublisher extends Notifier {

	/**
	 * Hold an instance of the Descriptor implementation of this publisher.
	 */
	@Extension
	public static final DescriptorImpl DESCRIPTOR = new DescriptorImpl();

    private String repoSiteName;
    private final List<PackageItem> packageItems = new ArrayList<PackageItem>();
    private Boolean skip = false;

    public AptlyPublisher() { }

    /**
    * The constructor which take a configured Aptly repo site name to use
    *
    * @param repoSiteName
    *          the name of the aptly repo configuration to use
    */
    public AptlyPublisher(String repoSiteName) {
        this.repoSiteName = repoSiteName;
    }

    public void setSkip(boolean skip) {
        this.skip = skip;
    }

    public boolean isSkip() {
        return skip;
    }

    public List<PackageItem> getPackageItems(){
        System.console().printf(">>>>>> getPackageItems %d\n", this.packageItems.size());
//        System.console().printf(">>>>>> getPackageItems %s\n", this.packageItems.get(0).getSourceFiles());
        return this.packageItems;
    }


    public String getRepoSiteName() {
		String repositename = repoSiteName;
		if (repositename == null) {
		    AptlySite[] sites = DESCRIPTOR.getSites();
			if (sites.length > 0) {
				repositename = sites[0].getName();
			}
		}
        System.console().printf(">>> getSiteName ret: %s\n", repositename);
		return repositename;
	}

	public void setRepoSiteName(String repoSiteName) {
		this.repoSiteName = repoSiteName;
	}

    /**
    * This method returns the configured AptlySite object which match the repoSiteName.
    * (see Manage Hudson and System
    * Configuration point FTP)
    *
    * @return the matching AptlySite or null
    */
    public AptlySite getSite() {
        AptlySite[] sites = DESCRIPTOR.getSites();
        if (repoSiteName == null && sites.length > 0) {
            // default
            return sites[0];
        }
        for (AptlySite site : sites) {
            if (site.getDisplayName().equals(repoSiteName)) {
                return site;
            }
        }
        return null;
    }

    public BuildStepMonitor getRequiredMonitorService() {
        return BuildStepMonitor.BUILD;
    }

	/**
	 * {@inheritDoc}
	 *
	 * @param build
	 * @param launcher
	 * @param listener
	 * @return
	 * @throws InterruptedException
	 * @throws IOException
	 *           {@inheritDoc}
	 * @see hudson.tasks.BuildStep#perform(hudson.model.Build, hudson.Launcher, hudson.model.BuildListener)
	 */
	@Override
	public boolean perform(AbstractBuild<?, ?> build, Launcher launcher, BuildListener listener) throws InterruptedException, IOException {

        listener.getLogger().println("Perform AptlyPublisher ");
        if (skip != null && skip) {
            listener.getLogger().println("Publish built packages via Aptly - Skipping... ");
            return true;
        }

        if (build.getResult() == Result.FAILURE || build.getResult() == Result.ABORTED) {
            // build failed. don't post
            return true;
        }

        AptlySite aptlysite = null;
        try {
            aptlysite = getSite();
            listener.getLogger().println("Using aptly site: " + aptlysite.getHostname());
            listener.getLogger().println("Port " + aptlysite.getPort());
            listener.getLogger().println("Username " + aptlysite.getUsername());
            listener.getLogger().println("Password " + aptlysite.getPassword());
            listener.getLogger().println("Timeout " + aptlysite.getTimeOut());

            AptlyRestClient client = new AptlyRestClient(aptlysite.getHostname(),
                                aptlysite.getPort(), aptlysite.getTimeOut(),
                                aptlysite.getUsername(), aptlysite.getPassword());

            String result = client.getAptlyServerVersion();
            listener.getLogger().println("Version result " +  result);

        } catch (Throwable th) {
            th.printStackTrace(listener.error("Failed to upload files"));
            build.setResult(Result.UNSTABLE);
        } finally {
            if (aptlysite != null) {
                //ftpsite.closeSession();
            }
        }

        return true;
    }

    /**
    * This class holds the metadata for the AptlyPublisher.
    * @author zgyarmati <mr.zoltan.gyarmati@gmail.com>
    * @see Descriptor
    */
    public static final class DescriptorImpl extends BuildStepDescriptor<Publisher> {

        private final CopyOnWriteList<AptlySite> sites = new CopyOnWriteList<AptlySite>();
        private final CopyOnWriteList<PackageItem> packageItems = new CopyOnWriteList<PackageItem>();


        /**
        * The default constructor.
        */
        public DescriptorImpl() {
            super(AptlyPublisher.class);
            load();
        }

        /**
        * The name of the plugin to display them on the project configuration web page.
        *
        * {@inheritDoc}
        *
        * @return {@inheritDoc}
        * @see hudson.model.Descriptor#getDisplayName()
        */
        @Override
        public String getDisplayName() {
            return "Publish built packages via Aptly";
        }

        /**
        * The getter for the packageItems field. (this field is set by the UI part of this plugin see config.jelly file)
        *
        * @return the value of the packageItems field
        */
        public CopyOnWriteList<PackageItem> getPackageItems() {
            return packageItems;
        }


        @Override
        public boolean isApplicable(Class<? extends AbstractProject> jobType) {
            return true;
        }

        /**
        * This method is called by hudson if the user has clicked the add button of the Aptly site hosts point in the System Configuration
        * web page. It's create a new instance of the {@link AptlyPublisher} class and added all configured ftp sites to this instance by calling
        * the method {@link AptlyPublisher#getEntries()} and on it's return value the addAll method is called.
        *
        * {@inheritDoc}
        *
        * @param req
        *          {@inheritDoc}
        * @return {@inheritDoc}
        * @see hudson.model.Descriptor#newInstance(org.kohsuke.stapler.StaplerRequest)
        */
        @Override
        public Publisher newInstance(StaplerRequest req, JSONObject formData) {
            System.console().printf("@@@@@@@@@@@@@@@@@@@@@@@@@@@@ SYSTEMCONSOLPRINTF");
            AptlyPublisher pub = new AptlyPublisher();
            req.bindParameters(pub, "publisher.");
            req.bindParameters(pub, "aptly.");
            pub.getPackageItems().addAll(req.bindParametersToList(PackageItem.class, "aptly.entry."));
//            JSONObject data;
//            try {
//                data = req.getSubmittedForm();
//            } catch (Exception e) {
//                System.console().printf(">>>>>>>>> getSubmittedForm Exception: " + e.getMessage() + "\n");
//                return null;
//            }
//            List<PackageItem> entries = req.bindJSONToList(PackageItem.class, data.getJSONObject("publisher").get("items"));
////            pub.getPackageItems().addAll(entries);
            return pub;
        }


        /**
        * The getter of the sites field.
        *
        * @return the value of the sites field.
        */
        public AptlySite[] getSites() {
            Iterator<AptlySite> it = sites.iterator();
            int size = 0;
            while (it.hasNext()) {
                it.next();
                size++;
            }
            return sites.toArray(new AptlySite[size]);
        }

        /**
        * {@inheritDoc}
        *
        * @param req
        *          {@inheritDoc}
        * @return {@inheritDoc}
        * @see hudson.model.Descriptor#configure(org.kohsuke.stapler.StaplerRequest)
        */
        @Override
        public boolean configure(StaplerRequest req, JSONObject formData) {
            sites.replaceBy(req.bindParametersToList(AptlySite.class, "aptly."));
            packageItems.replaceBy(req.bindParametersToList(PackageItem.class, "aptly.entry."));
            save();
            return true;
        }

        /**
        * This method validates the current entered Aptly site configuration data. 
        *
        * @param request
        *          the current {@link javax.servlet.http.HttpServletRequest}
        */
        public FormValidation doLoginCheck(StaplerRequest request) {
            String hostname = Util.fixEmpty(request.getParameter("hostname"));
            if (hostname == null) { // hosts is not entered yet
                return FormValidation.ok();
            }
            AptlySite site = new AptlySite(hostname, request.getParameter("port"), request.getParameter("timeOut"), request.getParameter("user"),
                request.getParameter("pass"));
            try {
                //site.createSession();
                //site.closeSession();

                return FormValidation.ok();
            } catch (Exception e) {
                return FormValidation.error(e.getMessage());
            }
        }
    }
}
