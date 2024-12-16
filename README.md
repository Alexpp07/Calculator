# Java Challenge - Calculator

## Run the application

### Build JAR files

```
mvn clean package
```

### Build and run containers

```
docker-compose up --build
```

### Run tests

```
mvn test
```

## Useful links

### Endpoints

- sum : http://localhost:8080/sum?a=2&b=3

- subtract : http://localhost:8080/subtract?a=2&b=3

- multiply : http://localhost:8080/multiply?a=2&b=3

- divide : http://localhost:8080/divide?a=2&b=3

### Documentation with Swagger

http://localhost:8080/swagger-ui/index.html
