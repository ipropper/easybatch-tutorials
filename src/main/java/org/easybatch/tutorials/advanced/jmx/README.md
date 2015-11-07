# Job monitoring Tutorial

## Description

This tutorial is an application that reads tweets from a flat file and prints them out to the standard output.
The `TweetSlowProcessor` simulates a long running processor to allow you to monitor the application using a JMX client.

This tutorial contains 2 examples:

- `SingleJobJmxTutorial`: shows how to monitor a single job
- `ParallelJobsJmxTutorial`: shows how to monitor two jobs running in parallel 

## Pre-requisite

* JDK 1.7+
* Maven
* Any JMX compliant client (such as VisualVM)
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
$> # Launch the single job jmx tutorial
$>mvn exec:java -PrunSingleJobJmxTutorial
```

Using your JMX client, navigate to the `org.easybatch.core.monitor:name=job` MBean
 and you will be able to monitor the execution progress of the application in real time.
 
```
$> # Launch the parallel job jmx tutorial
$>mvn exec:java -PrunParallelJobsJmxTutorial
```
When you want to monitor multiple jobs in parallel, you can give each job a name. This name will be the name
of the JMX MBean that monitors the job.
In this tutorial, we are running two jobs in parallel named `worker-job1` and `worker-job2`.
Using your JMX client, navigate to the `org.easybatch.core.monitor` type.
 You can see two MBeans named `worker-job1` and `worker-job2` registered there. You can monitor the execution progress of each job
 in real time.

### From Your IDE

* Import the `easybatch-tutorials` project in your IDE
* Resolve maven dependencies
* Navigate to the `org.easybatch.tutorials.advanced.jmx` package
* Run the `org.easybatch.tutorials.advanced.jmx.SingleJobJmxTutorial` class without any argument

Using your JMX client, navigate to the `org.easybatch.core.monitor:name=job` MBean
 and you will be able to monitor the execution progress of the application in real time.
 
* Run the `org.easybatch.tutorials.advanced.jmx.ParallelJobsJmxTutorial` class without any argument

When you want to monitor multiple jobs in parallel, you can give each job a name. This name will be the name
of the JMX MBean that monitors the job.
In this tutorial, we are running two jobs in parallel named `worker-job1` and `worker-job2`.
Using your JMX client, navigate to the `org.easybatch.core.monitor` type.
 You can see two MBeans named `worker-job1` and `worker-job2` registered there. You can monitor the execution progress of each job
 in real time.
