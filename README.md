# Contractor microservice
This project provides interaction methods for <i>contractors</i>.

## Install
### Preferenced requirements
* Java 21
* Maven 3.9.9
* Spring Boot 3.5.3
* Docker (for testing)

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
java -cp "target/classes" com.example.Contractor.Utils.RewriterCSV 
"src/main/resources/dataFiles/country.csv" 
"src/main/resources/dataFiles/industry.csv" 
"src/main/resources/dataFiles/org_form.csv"
```

4. Create .env files
   You must write .env_dev and .env_prod files with following values (you can use .env_template file from root directory):
* POSTGRES_USER (only for .env_prod - used for PSQL container)
* POSTGRES_PASSWORD (only for .env_prod - used for PSQL container)
* POSTGRES_DB (only for .env_prod - used for PSQL container)
* SPRING_DATASOURCE_URL
* SPRING_DATASOURCE_USERNAME
* SPRING_DATASOURCE_PASSWORD
* TOKEN_SECRET_KEY
* APP_RABBIT_HOST
* APP_RABBIT_PORT
* APP_RABBIT_EXCHANGE
* APP_RABBIT_QUEUE
* APP_SCHEDULE_FIXED_DELAY
* APP_SCHEDULE_INITIAL_DELAY

<p>.env_dev - for local development </p>
<p>.env_prod - for container (docker) development</p>

4. Build
```shell
mvn package
```
<b>!</b> docker-compose.yml uses docker network - 'rabbit-system'.
This for interaction with other containers. But you must create this network manually:
```shell
docker network create rabbit-system
```
## Usage
Launch docker
```shell
docker-compose up -d
```


## Contributing
<a href="https://github.com/NiRO-bb/contractor/graphs/contributors/">Contributors</a>

## License
No license 