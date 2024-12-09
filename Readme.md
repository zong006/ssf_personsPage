## See application.properties. use the relevant section for running the code via
- local machine
- docker
- railway service

### To test locally
- run  --> mvn clean package --> mvn spring-boot::run to run as per normal

### To run using Docker
- docker build -t <<name of programme>> .
- docker run -it -p <<port number>>:3000 <<name of programme>>
<!-- cos port 3000 is specified in dockerfile -->
- docker ps 
- docker stop <<name of programmes>>
Other docker commands:
- docker rmi -f <<image name>>
- docker images ls
-docker images prune

### To run via railway
- Redis requires user, password. Together with host and port, specify these as environment variables in application.properties file
- set-up a redis service on railway.
- in the same railway project, add the spring-boot project
    - railway init
    - railway link
this sets up the spring-boot project as an additional service in the railway project
- to link the spring-boot project with the redis service in railway, go to the railway dashboard and set environment variables in the spring-boot project 
    - REDISHOST
    - REDISUSER
    - REDISPORT
    - REDIS_PASSWORD