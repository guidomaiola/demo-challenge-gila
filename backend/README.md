# Gila Software challenge

## Instructions to test:

### Build
Run the following command to build the application

```bash
mvn clean install
```

### Run 
- Set the 'spring.jpa.hibernate.ddl-auto' located in application.properties to 'update' to load a sample Customer subscribed to SPORTS
- Execute the following command to run the application 
```bash
mvn spring-boot:run
```

The data.sql file will load sample data of a user subscribed to two categories, and two channels.



## TESTS

### POST MESSAGE
```bash
POST http://localhost:8080/messages/send
{
    "category": "SPORTS",
    "message": "This is a message"
}
```
### GET NOTIFICATIONS PERSISTED
```bash
GET http://localhost:8080/
```