# Welcome to Easy Batch tutorials

Tutorials are organised in 3 levels:

* Basic
* Intermediate
* Advanced

Each tutorial is contained in a separate package containing a `README.md` file that describes the tutorial and how to run it.
The `org.easybatch.tutorials.common` package contains classes that are common to all tutorials.

Tutorials described in this page use the current stable version of Easy Batch: [![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.easybatch/easybatch-core/badge.svg?style=flat)](http://search.maven.org/#artifactdetails|org.easybatch|easybatch-core|5.0.0-RC2|)

If you are looking for previous versions, please refer to the [releases page](https://github.com/EasyBatch/easybatch-tutorials/releases).

| :information_source: Prerequisites | :arrow_down: Download   | :hash: Build | :arrow_forward: Run |
|------------------------------------|-------------------------|--------------|---------------------|
|Java 7+ && maven 3+ |[Get source code](https://github.com/EasyBatch/easybatch-tutorials/releases/tag/v5.0.0-RC2)|`$>mvn install`|`$>mvn exec:java -P run[tutorial name]`|

# Basic tutorials

* [Hello world][]
* [Word Count][]

# Intermediate tutorials

* [Transforming data from one format to another][]
* [Importing data from a flat file into a database][]
* [Exporting data from a database to a flat file][]
* [Indexing a search database][]

# Advanced tutorials

* [Monitoring jobs with JMX][]
* [Scheduling jobs with Quartz][]
* [Configuring jobs with Spring][]
* [Writing a custom listener to restart a failed job from the last save point][]
* [Writing a custom reader to support multi-line records][]
* [Processing data in parallel with multiple worker jobs][]
* [Processing data asynchronously using a JMS queue][]
* [Distribute records to worker jobs based on their content][]

[Hello world]: https://github.com/EasyBatch/easybatch-tutorials/tree/master/src/main/java/org/easybatch/tutorials/basic/helloworld
[Word Count]: https://github.com/EasyBatch/easybatch-tutorials/tree/master/src/main/java/org/easybatch/tutorials/basic/wordcount

[Transforming data from one format to another]: https://github.com/EasyBatch/easybatch-tutorials/tree/master/src/main/java/org/easybatch/tutorials/intermediate/csv2xml
[Importing data from a flat file into a database]: https://github.com/EasyBatch/easybatch-tutorials/tree/master/src/main/java/org/easybatch/tutorials/intermediate/load
[Exporting data from a database to a flat file]: https://github.com/EasyBatch/easybatch-tutorials/tree/master/src/main/java/org/easybatch/tutorials/intermediate/extract
[Indexing a search database]: https://github.com/EasyBatch/easybatch-tutorials/tree/master/src/main/java/org/easybatch/tutorials/intermediate/elasticsearch

[Monitoring jobs with JMX]: https://github.com/EasyBatch/easybatch-tutorials/tree/master/src/main/java/org/easybatch/tutorials/advanced/jmx
[Scheduling jobs with Quartz]: https://github.com/EasyBatch/easybatch-tutorials/tree/master/src/main/java/org/easybatch/tutorials/advanced/quartz
[Configuring jobs with Spring]: https://github.com/EasyBatch/easybatch-tutorials/tree/master/src/main/java/org/easybatch/tutorials/advanced/spring
[Writing a custom listener to restart a failed job from the last save point]: https://github.com/EasyBatch/easybatch-tutorials/tree/master/src/main/java/org/easybatch/tutorials/advanced/restart
[Writing a custom reader to support multi-line records]: https://github.com/EasyBatch/easybatch-tutorials/tree/master/src/main/java/org/easybatch/tutorials/advanced/recipes
[Processing data asynchronously using a JMS queue]: https://github.com/EasyBatch/easybatch-tutorials/tree/master/src/main/java/org/easybatch/tutorials/advanced/jms
[Processing data in parallel with multiple worker jobs]: https://github.com/EasyBatch/easybatch-tutorials/tree/master/src/main/java/org/easybatch/tutorials/advanced/parallel
[Distribute records to worker jobs based on their content]: https://github.com/EasyBatch/easybatch-tutorials/tree/master/src/main/java/org/easybatch/tutorials/advanced/cbrd