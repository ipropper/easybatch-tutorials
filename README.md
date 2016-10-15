# Welcome to Easy Batch tutorials

Tutorials are organised in 3 levels:

* Basic
* Intermediate
* Advanced

Each tutorial is contained in a separate package containing a `README.md` file that describes the tutorial and how to run it.
The `org.easybatch.tutorials.common` package contains classes that are common to all tutorials.

Tutorials described in this page use the current stable version of Easy Batch: [![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.easybatch/easybatch-core/badge.svg?style=flat)](http://search.maven.org/#artifactdetails|org.easybatch|easybatch-core|5.0.0-RC2|)

If you are looking for previous versions, please refer to the [releases page](https://github.com/EasyBatch/easybatch-tutorials/releases).

## Pre-requisite

* JDK 1.7+
* Maven

## Download and build tutorials

* [:arrow_down: Download](https://github.com/EasyBatch/easybatch-tutorials/releases/tag/v5.0.0-RC2) the source code of tutorials and unzip it
* Build tutorials:

```shell
$>cd easybatch-tutorials
$>mvn install
```

## Run tutorials

Each tutorial comes with a launcher class and a maven profile to run it.
Please refer to the `README.md` file of each tutorial for more details about how to run it.

# Basic tutorials

* [Hello world][]
* [Word Count][]

# Intermediate tutorials

* [Transforming data from one format to another][]
* [Importing data from a flat file into a database][]
* [Exporting data from a database to a flat file][]
* [Synchronizing a reporting database][]
* [Indexing a search database][]

# Advanced tutorials

* [Monitoring jobs with JMX][]
* [Scheduling jobs with Quartz][]
* [Configuring jobs with Spring][]
* [Writing a custom listener to restart a failed job from the last save point][]
* [Writing a custom reader to support multi-line records][]
* [Processing data in parallel with multiple worker jobs][]
* [Processing data asynchronously using a JMS queue][]
* [Distribute records to worker jobs based on their content][]:

[Hello world]: https://github.com/EasyBatch/easybatch-tutorials/tree/master/src/main/java/org/easybatch/tutorials/basic/helloworld
[Word Count]: https://github.com/EasyBatch/easybatch-tutorials/tree/master/src/main/java/org/easybatch/tutorials/basic/wordcount

[Transforming data from one format to another]: https://github.com/EasyBatch/easybatch-tutorials/tree/master/src/main/java/org/easybatch/tutorials/basic/csv2xml
[Importing data from a flat file into a database]: https://github.com/EasyBatch/easybatch-tutorials/tree/master/src/main/java/org/easybatch/tutorials/intermediate/jdbc/load
[Exporting data from a database to a flat file]: https://github.com/EasyBatch/easybatch-tutorials/tree/master/src/main/java/org/easybatch/tutorials/intermediate/jdbc/extract
[Synchronizing a reporting database]: https://github.com/EasyBatch/easybatch-tutorials/tree/master/src/main/java/org/easybatch/tutorials/intermediate/mongodb/extract
[Elastic Search]: https://github.com/EasyBatch/easybatch-tutorials/tree/master/src/main/java/org/easybatch/tutorials/intermediate/elasticsearch
[Indexing a search database]: https://github.com/EasyBatch/easybatch-tutorials/tree/master/src/main/java/org/easybatch/tutorials/intermediate/mongodb/load

[Monitoring jobs with JMX]: https://github.com/EasyBatch/easybatch-tutorials/tree/master/src/main/java/org/easybatch/tutorials/advanced/jmx
[Scheduling jobs with Quartz]: https://github.com/EasyBatch/easybatch-tutorials/tree/master/src/main/java/org/easybatch/tutorials/advanced/quartz
[Configuring jobs with Spring]: https://github.com/EasyBatch/easybatch-tutorials/tree/master/src/main/java/org/easybatch/tutorials/advanced/spring
[Writing a custom listener to restart a failed job from the last save point]: https://github.com/EasyBatch/easybatch-tutorials/tree/master/src/main/java/org/easybatch/tutorials/advanced/restart
[Writing a custom reader to support multi-line records]: https://github.com/EasyBatch/easybatch-tutorials/tree/master/src/main/java/org/easybatch/tutorials/intermediate/recipes
[Processing data asynchronously using a JMS queue]: https://github.com/EasyBatch/easybatch-tutorials/tree/master/src/main/java/org/easybatch/tutorials/advanced/jms
[Processing data in parallel with multiple worker jobs]: https://github.com/EasyBatch/easybatch-tutorials/tree/master/src/main/java/org/easybatch/tutorials/advanced/parallel
[Distribute records to worker jobs based on their content]: https://github.com/EasyBatch/easybatch-tutorials/tree/master/src/main/java/org/easybatch/tutorials/advanced/cbrd