#!/bin/bash
APP_HOME=/usr/local/autocomp
TMP_HOME=${APP_HOME}/tmp
JAR_NAME=autocomplete

# Remove old file
if [ -f ${APP_HOME}/${JAR_NAME}.jar ]
then
  rm -f ${APP_HOME}/${JAR_NAME}.jar
fi

# move to target directory with file name change
mv ${TMP_HOME}/*.jar ${APP_HOME}/${JAR_NAME}.jar
chmod +x ${APP_HOME}/${JAR_NAME}.jar

sudo rm -f /etc/init.d/${JAR_NAME}
sudo ln -s ${APP_HOME}/${JAR_NAME}.jar /etc/init.d/${JAR_NAME}

sudo /etc/init.d/${JAR_NAME} restart
## make ln
## /etc/init.d/autocomplete

## application restart


