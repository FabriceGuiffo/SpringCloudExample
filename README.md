# SpringCloudExample
This is an example app on how to use spring cloud edge micro service

Spring cloud config explanatory notes

So I read on the use of spring cloud config on openclassrooms to set up a configuration server and wasn’t able to use the example provided so I set up my own example.

•	I created a base spring boot project with dependencies: web (to have and mvc and rest), Lombok (to perform getter and setters),
•	Initially I create an API with a 2 classes in the base project. The base class is a configuration bean which uses the @configurationproperties anno to scan the properties files to get property values. A rest controller with a single endpoint which simply returns a string of the properties fetched.
•	In the base project’s application.properties all we do is mention the base project name with spring.application.name property
•	In the base project pom we added a dependency 
•	<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-configuration-processor</artifactId>
    <optional>true</optional>
</dependency>
 This was quite strange and was primarily because some annotations weren’t recognized.
In order to host the properties elsewhere we do the following:
•	We create a folder (preferably in the base project folder) which would be a local git repo for our properties server. We can name it anyhow ex: git-properties-repo or configuration-server-repo
•	For convenience sake and just to be able to access this repo from our ide we add this folder to our base project as a module from existing sources
•	We initialize a git repo in this folder with git init
•	In this folder we will create a file which will use a naming convention to know which microservice element to target. We name this file [name of microservice].properties
•	In this properties folder we can place all the properties we want
•	In the meantime we also create a github/gitlab (distant repo) and we link it to the local repo and push our properties from the local to the distant repo?”
We then need to set up the spring cloud config server(edge microservice which would use this distant repo) as a datasource to serve our main microservices on the go.
•	We create a spring boot app with the following dependencies:  <artifactId>spring-cloud-config-server</artifactId>
•	NB: don’t confuse the server and the client cloud config dependency, don’t forget to add/check the dependency management in the pom.xml
•	We add this app to the base project as a module and we need to give it a few properties so it basically knows where to look for its datasource ie the distant repo. We do this by working on this module’s application.properties.

spring.cloud.compatibility-verifier.enabled=false

spring.application.name=config-server
server.port:9101
spring.cloud.config.server.git.uri=https://github.com/FabriceGuiffo/spring-cloud-repo.git

•	We also give a server prop for this module as well as give this spring server app a name.
•	In the server module main class we need to add an annotation which enables the cloud config server: @EnableConfigServer
•	On the base project end we now need to configure a few elems which would permit it to communicate with the config server to query/fetch its config properties.
•	First in the pom we will add a few dependencies: spring cloud config client and actuator starter.  Make sure we have the spring.cloud.version set as well as our dependencies management
•	In the base project application properties we need to add a property which will indicate the uri for our configuration server: spring.config.import=optional:configserver:http://localhost:9101
•	As well as a property to enable the actuator property give us access to the refresh endpoint which will help us reinitialize our config server reads: management.endpoints.web.exposure.include=refresh
•	We add @RefreshScope to the base project configuration class so it knows it has to fetch properties a fresh from its server once it receives a refresh POST Request.

Henceforth what we do is on the run (if the targeted microservice is running and we don’t want to restart it):
•	The config server is running
•	We add property changes to our local repo and push it to our distant repo
•	We run a post request against the endpoint [microservice uri]/actuator/refresh
•	If there are changes in the distant repo to acknowledge we get a non empty list
•	If we refresh our targeted microservice page we have the updated changes displayed.

NB: running a get request against [edge microservice spring cloud domain]/[targeted microservice name]/default we should receive a list of the currently tracked properties for the targeted microservice in the distant repo.
