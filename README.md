stratosphere-quickstart
=======================

A simple quickstart maven archetype for Stratosphere.

###Create an empty Java Stratosphere Job
Maven is required

```
curl https://raw.github.com/stratosphere/stratosphere-quickstart/master/quickstart.sh | bash
```



###Generate project manually:
using this command. This call will ask you to name your newly created Job.
``` bash
mvn archetype:generate                              \
   -DarchetypeGroupId=eu.stratosphere               \
   -DarchetypeArtifactId=stratosphere-quickstart    \
   -DarchetypeVersion=0.4-SNAPSHOT                  \
   -DarchetypeCatalog=https://oss.sonatype.org/content/repositories/snapshots/
```


[![Build Status](https://travis-ci.org/stratosphere/stratosphere-quickstart.png?branch=master)](https://travis-ci.org/stratosphere/stratosphere-quickstart)
