
## Prerequisites

The following technologies required:  

* Java 17
* Postgres db  
* Spring boot 3  
* MyBatis
* RabbitMQ
* Liquibase
* Swagger

## For horizontal application scaling

To scale your application horizontally, should add monitoring to track application performance and resource usage.
If necessary, we can create several application instances and think about database sharding

## Building & setup 

Follow the steps below:

1. install docker  
2. `cd /tuum`  
3. `docker-compose up --build`
4. or run in your favorite ide:  
5. `cd etc/docker/test_task_dev` 
6. `docker-compose up` *To create a database and RabbitMQ in docker  
7. The **prod** profile is used only for deploying a project in Docker. For local development, the profile does not need to be installed    
8. After running the project in ide or in Docker, the documentation will be available at - http://localhost:8095/api/v1/swagger-ui.html
9. enjoy

