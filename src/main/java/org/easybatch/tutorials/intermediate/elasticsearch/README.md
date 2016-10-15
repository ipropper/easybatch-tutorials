# ElasticSearch Tutorial

## Description

This tutorial is an application that reads tweets from a relational database and index them in a ElasticSearch server in bulk mode.

A similar use case is synchronizing data from the main database to a [reporting database](http://martinfowler.com/bliki/ReportingDatabase.html).

## Run the tutorial

### From the command line

Open a terminal an run the following command:

```
$>cd easybatch-tutorials
$>mvn install
$>mvn exec:java -PrunElasticSearchTutorial
```

### From Your IDE

* Import the `easybatch-tutorials` project in your IDE
* Resolve maven dependencies
* Navigate to the `org.easybatch.tutorials.intermediate.elasticsearch` package
* Run the `org.easybatch.tutorials.intermediate.elasticsearch.Launcher` class without any argument
