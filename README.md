# Welcome to Easy Batch tutorials

You will find here several tutorials of different levels that will introduce you to all APIs of the framework.

Tutorials are organised in 3 levels:

* Basic
* Intermediate
* Advanced

Each tutorial is contained in a separate package. In each package, there is `README.md` file that describes the tutorial and how to run it.

Navigate to the package you are interested in and you will be able to get started quickly. Each tutorial is shipped with a
main class to launch it. To run a tutorial, just import the `easybatch-tutorials` project in your favorite IDE and launch the main class.

The `org.easybatch.tutorials.common` package contains main classes that are common to all tutorials.

### Basic tutorials

* [Hello world][]: The simplest batch application that you can write with Easy Batch
* [Word Count][]: The classic word count example using Easy Batch
* [Transforming a CSV file to Xml format][]: A typical ETL application that transforms a CSV file to XML format

### Intermediate tutorials

* [Loading data in a relational database using JDBC][]: A batch application that imports data from a flat file into a relational database using JDBC
* [Loading data in a relational database using Hibernate][]: A batch application that imports data from a flat file into a relational database using Hibernate
* [Loading data in a NoSQL database][]: A batch application that loads data from a flat file into a MongoDB server
* [Extracting data from a relational database using JDBC][]: A batch application that exports data from a relational database to a flat file
* [Extracting data from a NoSQL database][]: A batch application that extracts data from a MongoDB collection and export it in a XML file
* [Elastic Search][]: A sample application that extracts data from a relational database and index it in a ElasticSearch server
* [Recipes][]: A showcase of how to write a custom reader to read data in a non standard format

### Advanced tutorials

* [Spring][]: A tutorial to show how to use configure an Easy Batch job as a Spring bean
* [Quartz][]: Learn how to schedule Easy Batch jobs with Quartz
* [JMX][]: Learn how to monitor Easy Batch jobs with JMX
* [JMS][]: Learn how to create asynchronous batch applications using Easy Batch and JMS
* [Parallel processing][]: An example of how to use Easy Batch to process data in parallel
* [Content-Based Record Writer][]: An example of how to process multiple files in parallel based on their content
* [Restart a failed job Tutorial][]: An example of how to create a custom checkpoint listener to restart a failed job from where it left off

[Hello world]: https://github.com/EasyBatch/easybatch-tutorials/tree/master/src/main/java/org/easybatch/tutorials/basic/helloworld
[Word Count]: https://github.com/EasyBatch/easybatch-tutorials/tree/master/src/main/java/org/easybatch/tutorials/basic/wordcount
[Transforming a CSV file to Xml format]: https://github.com/EasyBatch/easybatch-tutorials/tree/master/src/main/java/org/easybatch/tutorials/basic/csv2xml

[Loading data in a relational database using JDBC]: https://github.com/EasyBatch/easybatch-tutorials/tree/master/src/main/java/org/easybatch/tutorials/intermediate/jdbc/load
[Loading data in a relational database using Hibernate]: https://github.com/EasyBatch/easybatch-tutorials/tree/master/src/main/java/org/easybatch/tutorials/intermediate/hibernate
[Loading data in a NoSQL database]: https://github.com/EasyBatch/easybatch-tutorials/tree/master/src/main/java/org/easybatch/tutorials/intermediate/mongodb/load
[Extracting data from a relational database using JDBC]: https://github.com/EasyBatch/easybatch-tutorials/tree/master/src/main/java/org/easybatch/tutorials/intermediate/jdbc/extract
[Extracting data from a NoSQL database]: https://github.com/EasyBatch/easybatch-tutorials/tree/master/src/main/java/org/easybatch/tutorials/intermediate/mongodb/extract
[Elastic Search]: https://github.com/EasyBatch/easybatch-tutorials/tree/master/src/main/java/org/easybatch/tutorials/intermediate/elasticsearch
[Recipes]: https://github.com/EasyBatch/easybatch-tutorials/tree/master/src/main/java/org/easybatch/tutorials/intermediate/recipes

[Spring]: https://github.com/EasyBatch/easybatch-tutorials/tree/master/src/main/java/org/easybatch/tutorials/advanced/spring
[Quartz]: https://github.com/EasyBatch/easybatch-tutorials/tree/master/src/main/java/org/easybatch/tutorials/advanced/quartz
[JMX]: https://github.com/EasyBatch/easybatch-tutorials/tree/master/src/main/java/org/easybatch/tutorials/advanced/jmx
[JMS]: https://github.com/EasyBatch/easybatch-tutorials/tree/master/src/main/java/org/easybatch/tutorials/advanced/jms
[Parallel processing]: https://github.com/EasyBatch/easybatch-tutorials/tree/master/src/main/java/org/easybatch/tutorials/advanced/parallel
[Content-Based Record Writer]: https://github.com/EasyBatch/easybatch-tutorials/tree/master/src/main/java/org/easybatch/tutorials/advanced/cbrd
[Restart a failed job Tutorial]: https://github.com/EasyBatch/easybatch-tutorials/tree/master/src/main/java/org/easybatch/tutorials/advanced/restart
