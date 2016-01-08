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

## Jenkins plugin setup
mvn -U org.jenkins-ci.tools:maven-hpi-plugin:create


## Notes
### Bookmarks
REST client lib: http://unirest.io/java.html
