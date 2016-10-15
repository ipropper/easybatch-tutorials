# Recipes Tutorial

## Description

This tutorial is an application that reads recipes from flat file and prints them out to the standard output.
The goal of the tutorial is to show how to write a custom record reader to read a multi-line records.

## Run the tutorial

### From the command line

Open a terminal and run the following commands:

```
$>cd easybatch-tutorials
$>mvn install
$>mvn exec:java -PrunRecipesTutorial
```

### From Your IDE

* Import the `easybatch-tutorials` project in your IDE
* Resolve maven dependencies
* Navigate to the `org.easybatch.tutorials.advanced.recipes` package
* Run the `org.easybatch.tutorials.advanced.recipes.Launcher` class without any argument
