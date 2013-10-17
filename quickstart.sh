#!/usr/bin/env bash

mvn archetype:generate								\
  -DarchetypeGroupId=eu.stratosphere 				\
  -DarchetypeArtifactId=stratosphere-quickstart		\
  -DarchetypeVersion=0.4-SNAPSHOT					\
  -DgroupId=eu.stratosphere 						\
  -DartifactId=quickstart 							\
  -Dversion=0.1										\
  -Dpackage=quickstart 								\
  -DinteractiveMode=false							\
  -DarchetypeCatalog=https://oss.sonatype.org/content/repositories/snapshots/


# Use this command if you want to specify the coordinates of your generated artifact
# in an interactive menu:
#
# mvn archetype:generate								\
#   -DarchetypeGroupId=eu.stratosphere 				\
#   -DarchetypeArtifactId=stratosphere-quickstart		\
#   -DarchetypeVersion=0.4-SNAPSHOT					\
#   -DarchetypeCatalog=https://oss.sonatype.org/content/repositories/snapshots/
