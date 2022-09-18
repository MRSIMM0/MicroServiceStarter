# MicroServiceStarter
Entry point for futher microservices developement - Api gateway , eureka server and auth service

This project uses `java 18`,`spring boot 2.5.7` , `docker` , `liquidbase`, `postgresql`

To build this project download project files and use `docker compose build`.

Then when project files builds run `docker compose up`.

The eureka default port `8761` and it will be exposed and can be used to access microservices.

Api gateway is on port `8080` and it is the access point for the application

