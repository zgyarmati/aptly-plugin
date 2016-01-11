/*
 * The MIT License
 *
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package org.jenkinsci.plugins.aptly;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import hudson.Extension;
import hudson.model.Describable;
import hudson.model.Descriptor;
import hudson.model.Hudson;

import org.kohsuke.stapler.DataBoundConstructor;

/**
 * Represents a package or a set of packages to publish into a repository on a
 * AptlySite
 * @author $Author: zgyarmati <mr.zoltan.gyarmati@gmail.com>
 */
public class PackageItem implements Describable<PackageItem>, Serializable {

    private String sourceFiles;
    private String repositoryName;
    private String distributionName;

    /**
     * Constructor
     * @param codeSigningIdentityName name of the code signing identity
     */
    @DataBoundConstructor
    public PackageItem(String sourceFiles,
                       String distributionName,
                       String repositoryName) {
        System.console().printf("###### PackageItem constructor\n\n");
        this.repositoryName = repositoryName;
        this.distributionName = distributionName;
        this.sourceFiles = sourceFiles;
        //this.sourceFiles = Arrays.asList(sourceFiles.split("\\s*,\\s*"));
    }

    /**
     * Get the list of the filenames.
     * @return the comma-separated list as String
     */
    public String getSourceFiles() {
        System.console().printf("##### getSourceFiles %s\n\n", sourceFiles);
        return sourceFiles;
    }

    public String getRepositoryName(){
        System.console().printf("##### getRepositoryName %s\n\n", repositoryName);
        return repositoryName;
    }

    public void setSourceFiles(String sourceFiles) {
        this.sourceFiles = sourceFiles;
    }

    public void setRepositoryName(String repositoryName) {
        this.repositoryName = repositoryName;
    }

    public void setDistributionName(String distributionName) {
        this.distributionName = distributionName;
    }

    public String getDistributionName(){
        return this.distributionName;
    }


     /**
     * Get the descriptor.
     * @return descriptor
     */
    public Descriptor getDescriptor() {
        Descriptor ds = Hudson.getInstance().getDescriptorOrDie(getClass());
        return ds;
    }

    /**
     * Descriptor of the {@link PackageItem}.
     */
    @Extension
    public static final class DescriptorImpl extends Descriptor<PackageItem> {

        @Override
        public String getDisplayName() {
            return "";
        }
    }
}
