# Database Tutorial

## Description

This tutorial is an introduction to the MultiRecord API. A multi-record represents a chunk of records.
 Easy Batch provides several MultiRecordReaders and MultiRecordWriters to read and write records in chunks.
 
This tutorial shows the usage of the `IterableMultiRecordReader` to read multi-records from an iterable data source
and the `CollectionMultiRecordWriter` to write multi-records to a collection.

## Pre-requisite

* JDK 1.6+
* Maven
* Git (optional)
* Your favorite IDE (optional)

## Get source code

### Using git

`git clone https://github.com/EasyBatch/easybatch-tutorials.git`

### Downloading a zip file

Download the [zip file](https://github.com/EasyBatch/easybatch-tutorials/archive/master.zip) containing the source code and extract it.

## Run the tutorial

### From the command line

Open a terminal in the directory where you have extracted the source code of the project, then proceed as follows:

```
$>cd easybatch-tutorials
$>mvn install
$>mvn exec:java -PrunMultiRecordTutorial
```

### From Your IDE

* Import the `easybatch-tutorials` project in your IDE
* Resolve maven dependencies
* Navigate to the `org.easybatch.tutorials.intermediate.chunks` package
* Run the `org.easybatch.tutorials.intermediate.chunks.Launcher` class without any argument
