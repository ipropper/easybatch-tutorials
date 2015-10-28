# Asynchronous jobs Tutorial

## Description

This tutorial is a show case of how to implement asynchronous jobs using a JMS queue.

## Pre-requisite

* JDK 1.7+
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
$>mvn exec:java -PrunJmsTutorial
```

This will start an embedded JMS broker listening to incoming messages.

Open a second terminal and run the following command:

`mvn exec:java -PrunJmsSender`

You will be able to type in tweets in the console to post them to the JMS queue and see how the job will process them as they come.
To quit the application, type in "quit" in order to send a poison record that will stop the job.

### From Your IDE

* Import the `easybatch-tutorials` project in your IDE
* Resolve maven dependencies
* Navigate to the `org.easybatch.tutorials.advanced.jms` package
* Run the `org.easybatch.tutorials.advanced.jms.Launcher` class without any argument to launch the job
* Run the `org.easybatch.tutorials.advanced.jms.JMSSenderLauncher` class without any argument to launch the JMS message sender application
