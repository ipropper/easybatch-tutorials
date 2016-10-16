# Tutorial:

## Description

This tutorial is a batch application that reads tweets from a relational database (master) and index them in a ElasticSearch server:

![db-to-db](db-to-db.png)

A similar use case is synchronizing data from the master database to a [reporting database](http://martinfowler.com/bliki/ReportingDatabase.html).

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
