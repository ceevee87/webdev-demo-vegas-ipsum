# webdev-demo-vegas-ipsum

### << UNDER CONSTRUCTION (end of April/early May --- see progress down below. >>


## Overview
This project is an Ipsum Lorem generator with a Las Vegas twist. Below is a list of items that are planned for development.


## Technologies
This project demonstrates (will demonstrate) the following technologies:
- RESTful JSON API implemented in Java with Jersey
- Web service deployment using Apache Tomcat
- Source code compilation and dependency management using Maven
- Automated unit testing (Java) using JUnit
- Manual validation using Postman
- Front end web development using HTML/JS/CSS
  * styling with Bootstrap and other popular 3rd party packages


## Task List & Progress
- [x] implement java class interface that defines basic features of a lorem ipsum generator.
- [x] implement the VegasIpsum generator 
- [x] add JUnit testing for VegasIpsum (java) generator.
- [x] create a WebApi that implements GET operations using the VegasIpsum backend engine.
- [x] write a basic HTML/JS/CSS front-end that interacts with the deployed (Tomcat) Web application
  * [x] add in options in web page to set min/max paragraphs
    * [x] enhance java side GET code to handle @queryparam parameters. 
  * [x] add in ability to start first paragraph, optionally, with 'vegas ipsum dolor ...'
- [x] update Maven to deploy VegasIpsum WebApi onto an Apache Tomcat server
- [ ] validate WebApi interface to the VegasIpsum generator with Postman.

## Build
IMPORTANT: Automatically deploying and undeploying applications to an Apache Tomcat server requires modification of the tomcat-users.xml file. I do not include instructions for doing that here.

This application is run as a WAR deployed on Apached Tomcat. To build & deploy to Tomcat make sure you first have Tomcat up and running. 
Then do:
```
   mvn tomcat7:undeploy clean package tomcat7:deploy
```

## Running the application
The [default landing page](http://localhost:8080/VegasIpsum/) for the application is http://localhost:8080/VegasIpsum/. 


## IDE
The java portion of this project was developed in NetBeans. The Web portion
was developed using VS Code.

The operating system is Ubuntu 16.04 LTS.

No testing was done on Windows based machines or alternate browsers.



