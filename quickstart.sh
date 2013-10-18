#!/usr/bin/env bash

PACKAGE=quickstart

mvn archetype:generate								\
  -DarchetypeGroupId=eu.stratosphere 				\
  -DarchetypeArtifactId=stratosphere-quickstart		\
  -DarchetypeVersion=0.4-SNAPSHOT					\
  -DgroupId=eu.stratosphere 						\
  -DartifactId=$PACKAGE								\
  -Dversion=0.1										\
  -Dpackage=eu.stratosphere.quickstart 				\
  -DinteractiveMode=false							\
  -DarchetypeCatalog=https://oss.sonatype.org/content/repositories/snapshots/

#
# Give some guidance
#
echo -e "\\n\\n"
echo -e "\\tA sample quickstart Stratosphere Job has been created."
echo -e "\\tSwitch into the directory using"
echo -e "\\t\\t cd $PACKAGE"
echo -e "\\tImport the project there using your favorite IDE (Import it as a maven project)"
echo -e "\\tBuild a jar inside the directory using"
echo -e "\\t\\t mvn clean package"
echo -e "\\tYou will find the runnable jar in $PACKAGE/target"
echo -e "\\tConsult our mailing list if you have any troubles: https://groups.google.com/forum/#!forum/stratosphere-dev"
echo -e "\\n\\n"


# Use this command if you want to specify the coordinates of your generated artifact
# in an interactive menu:
#
# mvn archetype:generate								\
#   -DarchetypeGroupId=eu.stratosphere 				\
#   -DarchetypeArtifactId=stratosphere-quickstart		\
#   -DarchetypeVersion=0.4-SNAPSHOT					\
#   -DarchetypeCatalog=https://oss.sonatype.org/content/repositories/snapshots/
