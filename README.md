stratosphere-quickstart
=======================

Two simple quickstart maven archetypes for Stratosphere.

###Create an empty Java Stratosphere Job
Maven is required

```
curl https://raw.github.com/stratosphere/stratosphere-quickstart/master/quickstart.sh | bash
```

###Create a simple scala Stratosphere Job
Maven is required

```
curl https://raw.github.com/stratosphere/stratosphere-quickstart/master/quickstart-scala.sh | bash
```

When you import the scala project into eclipse you will also need the following plugins:

Eclipse 4.x:
  * scala-ide: http://download.scala-ide.org/sdk/e38/scala210/stable/site
  * m2eclipse-scala: http://alchim31.free.fr/m2e-scala/update-site
  * build-helper-maven-plugin: https://repository.sonatype.org/content/repositories/forge-sites/m2e-extras/0.15.0/N/0.15.0.201206251206/

Eclipse 3.7:
  * scala-ide: http://download.scala-ide.org/sdk/e37/scala210/stable/site
  * m2eclipse-scala: http://alchim31.free.fr/m2e-scala/update-site
  * build-helper-maven-plugin: https://repository.sonatype.org/content/repositories/forge-sites/m2e-extras/0.14.0/N/0.14.0.201109282148/



###Generate project manually:
using this command. This call will ask you to name your newly created Job.
``` bash
mvn archetype:generate                              \
   -DarchetypeGroupId=eu.stratosphere               \
   -DarchetypeArtifactId=quickstart-java                 \
   -DarchetypeVersion=0.4-SNAPSHOT                  \
   -DarchetypeCatalog=https://oss.sonatype.org/content/repositories/snapshots/
```

###Generate scala project manually:
using this command. This call will ask you to name your newly created Job.
``` bash
mvn archetype:generate                              \
   -DarchetypeGroupId=eu.stratosphere               \
   -DarchetypeArtifactId=quickstart-scala           \
   -DarchetypeVersion=0.4-SNAPSHOT                  \
   -DarchetypeCatalog=https://oss.sonatype.org/content/repositories/snapshots/
```


[![Build Status](https://travis-ci.org/stratosphere/stratosphere-quickstart.png?branch=master)](https://travis-ci.org/stratosphere/stratosphere-quickstart)
