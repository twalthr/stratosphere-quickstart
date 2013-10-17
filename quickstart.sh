#!/usr/bin/env bash


mvn archetype:generate								\
  -DarchetypeGroupId=eu.stratosphere 				\
  -DarchetypeArtifactId=stratosphere-quickstart		\
  -DarchetypeVersion=0.4-SNAPSHOT					\
  -DarchetypeCatalog=https://oss.sonatype.org/content/repositories/snapshots/archetype-catalog.xml
