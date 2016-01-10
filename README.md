aptly-plugin
===============

Jenkins plugin for aptly debian repository manager

# Setting up development environment

## Install aptly

(from http://www.aptly.info/download/)
echo "deb http://repo.aptly.info/ squeeze main" >> /etc/apt/sources.list
apt-key adv --keyserver keys.gnupg.net --recv-keys E083A3782A194991
apt-get update
apt-get install aptly
Running it:
aptly api serve -listen=:1080



aptly repo create delbalaton-testing


## Jenkins plugin setup
mvn -U org.jenkins-ci.tools:maven-hpi-plugin:create


## Notes
### Bookmarks
REST client lib: http://unirest.io/java.html

Aptly multiple distributions
https://groups.google.com/forum/#!topic/aptly-discuss/QhgkRlR577w



## Usacases
* One package into one repository
* Multiple packages into one repository
* Packages from dir into one repository

* Packages into multiple repositories


## Terminology

aptly site: a remote or the local host where Aptly is running and serving the
REST API, and one and more repositories

repository: a repository on an Aptly site. One Aptly site can have multiple
repositories

package: a .deb file, which has to be uploaded and added to a repository
registeren on an aptly site
