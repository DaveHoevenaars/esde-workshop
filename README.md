# ESDE Workshop - Quarkus Base

### Table of Contents
## Table of contents
  * [Introduction](#introduction)
  * [Prerequisites](#prerequisites)
  * [Quarkus Theory](#quarkus-theory)
    * [Performance](#performance)
  * [Get Quarkus](#get-quarkus)
    * [Configuring package](#configure-package)
    * [Running the application in dev mode](#running-the-application-in-dev-mode)
      * [macOS and Linux](#macos-and-linux)
      * [Windows](#windows)
      * [Packaging and running the application](#packaging-and-running-the-application)
      * [Creating a native executable](#creating-a-native-executable)
    + [Sample Application](#sample-application)
      * [Clone Application](#clone-application)
      * [API Specification](#api-specification)
      * [Step 3](#step-3)
      
### Introduction
Welcome to our Workshop about Quarkus Base.

This project uses Quarkus, the **Supersonic Subatomic Java Framework**.  
If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

### Prerequisites
- min. 16GB RAM for building native images

### Quarkus Theory

#### What is Quarkus?
#### Why Quarkus?
##### Supersonic
##### Subatomic
#### How does Quarkus work?

Move startup time to build time  
**Steps a framework performs during startup**
- Parse config files (application properties, etc.)
- Classpath & classes scanning
    - Annotations
    - Getters
    - Meta data
- Build framework
- Prepare reflection 
- Start and open IO, threads, etc.

Quarkus moves those steps from the normal startup time to the build time resulting in a decreased startup time.

### Get Quarkus
#### Configure package 
Please visit https://quarkus.io/ and click the "start coding" button in the upper right corner. Make a selection of frameworks you want to develop with and download your package.

#### Running the application in dev mode
You can run your application in dev mode that enables live coding using:

##### macOS and Linux
```
./mvnw quarkus:dev
```
##### Windows
```
mvnw.cmd compile quarkus:dev
```

##### Packaging and running the application
The application can be packaged using `./mvnw package`.
It produces the `code-with-quarkus-1.0.0-SNAPSHOT-runner.jar` file in the `/target` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/lib` directory.

The application is now runnable using `java -jar target/code-with-quarkus-1.0.0-SNAPSHOT-runner.jar`.

For issues building a native image on windows review documentation at: https://quarkus.io/guides/building-native-image

##### Creating a native executable

You can create a native executable using: `./mvnw package -Pnative`.

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: `./mvnw package -Pnative -Dquarkus.native.container-build=true`.

You can then execute your native executable with: `./target/code-with-quarkus-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/building-native-image.

## Sample Application

### Clone application
Please visit https://github.com/codelakegmbh/esde-workshop and clone the repository.

### Run application
##### macOS and Linux
```
./mvnw quarkus:dev
```
##### Windows
```
mvnw.cmd compile quarkus:dev
```

##### Try application
Visit <a href="http://127.0.0.1:8080">127.0.0.1:8080</a> to view the web user interface for application testing.

### API Specification
We have created an openAPI specification for this project describing all relevant endpoints of our sample application.
You can find the specification at: <a href="https://shorturl.at/cdvwO">shorturl.at/cdvwO</a>
