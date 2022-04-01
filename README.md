# CSC1009 P3T09

## About The Project

This is an Automated Teller Machine (ATM) CLI application build with Java. The objective of this project is to apply concepts from Object Oriented Programming (OOP) taught in this module. Based on our research and personal experience with ATM, we have implemented serveral key features on this ATM CLI application. They are

1. Cash Withdrawal
2. Cash Deposit
3. Bank Transfer
4. Transaction History
5. Manage Account

Some of the features such as transaction history and account management are not commonly found on ATMs. We decided to include them to enhance our ATM.

## Built With

- [Gradle](https://gradle.org/) - For Package Management Purposes
- [Java17](https://adoptium.net/) - Development Kit

### Java Packages

- [JUnit Jupiter 5](https://junit.org/junit5/) - Unit and Integration Testing
- [JaCoCo Plugin](https://docs.gradle.org/current/userguide/jacoco_plugin.html) - For Code Coverage Metrics

## Getting Started

### Installation

Install [Java17 JDK](https://adoptium.net/)

Have an IDE or Code Editor installed on your PC. We recommend

1. [VSCode](https://code.visualstudio.com/) - Multi-language Lightweight Code Editor
2. [JetBrains IntelliJ IDEA](https://www.jetbrains.com/idea/) - Professional Java IDE

#### VSCode

Additional installation required if you are you using VScode. Refer to this [link](https://code.visualstudio.com/docs/languages/java)

### Run the application

Simplest way to execute the application will be going to `app/src/main/java/App.java` file and clicking on the `run` link button above `main` method.

Another way is via a CLI command.

```bash
gradlew.bat run # Windows

./gradlew run # linux, mac
```

#### Test Data

| Card Number      | PIN    | Account Number |
|------------------|--------|----------------|
| 5424053513915781 | 666666 | 6454856238     |
| 4071666471445613 | 141981 | 6458795246     |

### Code Coverage Metrics Report

Go to `Gradle` menu in your IDE or Code Editor and execute the task `jacocoTestReport` to generate it.

Another way to generate the report is via a CLI command.

```bash
gradlew.bat jacocoTestReport # Windows

./gradlew jacocoTestReport # linux, mac
```

`jacocoHtml` folder will be generated under the `build` folder. Open the `index.html` in your browser to view the report.
