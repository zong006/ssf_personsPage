## See application.properties. use the relevant section for running the code via
- local machine
- docker
- railway service

### To test locally
- run  --> mvn clean package --> mvn spring-boot::run to run as per normal

### To run using Docker
- docker build -t `<`name of programme `>` .
- docker run -it -p `<`port number`>`:3000 `<`name of programme`>`
<!-- cos port 3000 is specified in dockerfile -->
- docker ps 
- docker stop `<`name of programmes`>`
Other docker commands:
- docker rmi -f `<`image name`>`
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

- if there is an API key, add the variable into application.properties: api_key=${API_KEY}
    - use @Value("${api_key}") in the relevant code (probably service code) to use it. (see 2022 crypto paper, or prac1 on github)
    - add environment variable to railway service

- to access the redis service on railway, either to check if data is saved or whatnot
    - go to the redis service -> variables -> copy the REDIS_PUBLIC_URL
    - on command line, type >>> redis-cli -u "insert REDIS_PUBLIC_URL"