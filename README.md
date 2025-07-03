# Contractor microservice
This project provides interaction methods for <i>contractors</i>.

## Install
### Preferenced requirements
* Java 21
* Maven 3.9.9
* Spring Boot 3.5.3
* <b>PostgreSQL only</b> 

### Steps to install project
1. Clone repository
```shell
git clone https://github.com/NiRO-bb/contractor.git
```
2. Build with Maven <p>
   <b>Below just a pattern!</b>
   You <b>must</b> replace the following:
* `<port>` with your real port
* `<database1>` with name of database <b>for testing</b>
* `<username>` with name of user who has access to specified database
* `<password>` with password of specified user
```shell
mvn clean install 
"-Dspring.datasource.url=jdbc:postgresql://localhost:<port>/<database1>"
"-Dspring.datasource.username=<username>" 
"-Dspring.datasource.password=<password>" 
```
Do not use the same database for testing and usage.
The application cleans up your database during testing.
It is better to use a completely new database (for testing). 

## Usage
After project installing go to "target" directory.
```shell
cd target
```
Then launch JAR with specified database.
<b>Below just a pattern!</b>
You <b>must</b> replace the following:
* `<jar_name>` with name of JAR file that produced by Maven (actual is `Contractor-0.0.1-SNAPSHOT.jar`)
* `<port>` with your real port
* `<database2>` with name of your real database
* `<username>` with name of user who has access to specified database
* `<password>` with password of specified user
```shell
java -jar <jar_name>.jar \
 --spring.datasource.url=jdbc:postgresql://localhost:<port>/<database2> \
  --spring.datasource.username=<username> \
   --spring.datasource.password=<password>
```

## Contributing
<a href="https://github.com/NiRO-bb/contractor/graphs/contributors/">Contributors</a>

## License
No license 