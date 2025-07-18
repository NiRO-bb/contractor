# Contractor microservice
This project provides interaction methods for <i>contractors</i>.

## Install
### Preferenced requirements
* Java 21
* Maven 3.9.9
* Spring Boot 3.5.3
* Docker (for testing)
* <b>PostgreSQL only</b> 

### Steps to install project
1. Clone repository
```shell
git clone https://github.com/NiRO-bb/contractor.git
```

2. Compile
```shell
mvn clean compile 
```

3. Complete rewriting data files (not necessary but welcome).
   Some data files that used during DB migration can be written in invalid format.
```shell
java -cp "target/classes" com.example.Contractor.RewriterCSV 
"src/main/resources/dataFiles/country.csv" 
"src/main/resources/dataFiles/industry.csv" 
"src/main/resources/dataFiles/org_form.csv"
```

4. Build
```shell
mvn package
```

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
* `<database>` with name of your real database
* `<username>` with name of user who has access to specified database
* `<password>` with password of specified user
* `<secret>` with secret - cryptographic key used for signing and verifying the token's integrity
```shell
java -jar <jar_name>.jar \ --spring.datasource.url=jdbc:postgresql://localhost:<port>/<database> \ --spring.datasource.username=<username> \ --spring.datasource.password=<password> \ --token.secret.key=<secret>
```

## Contributing
<a href="https://github.com/NiRO-bb/contractor/graphs/contributors/">Contributors</a>

## License
No license 