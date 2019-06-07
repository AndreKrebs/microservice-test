# microservice-test
Repositório criado para testar o uso de microsserviços com Spring, OAuth2 e Elasticsearch usando o gerador de código JHipster

## How to run:
The UAA and Blog services need to use the mysql database, check the application-dev.yml file to see the name of the databases and set username and password.

On each service run `mvn spring-boot: run` (the projects are failing in STS and eclipse, need to run on the terminal)

At the gateway, in addition to the mvn command, open another terminal and run the mvn start command to start the Angular front-end 7.
