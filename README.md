# Leave Management System - LMS - User Service

This is the user service repository of LMS application. Follow the steps to build and bring up the lms

The system where you will be brining up the application should have docker client installed in it. 

Go to the repository home, that is where you have the docker compose file.

The docker compose file already have the configurations required to  bring up both lms-user service and lms service. 

Open Command window or terminal and run the command **docker --version** , if you are getting a command not found error docker installation is not completed in the system, please install docker.

If you are able to see the docker version run command docker compose up.

It will take a few minutes for docker to bring up all the containers.

Open Command window or terminal and run the command **ipconfig/ifconfig** to get the ip address. Use the VMware Network Adapter VMnet1 IPV4 address to access the lms instance and use the postman collections in the repo to invoke the APIs


## Technologies

* Spring Boot
* Spring Batch
* Java Persistence API, Repositories and JPQL
* MySql
* React






