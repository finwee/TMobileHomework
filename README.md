# TMobileHomework

Sample demonstration how to create RESTful microservices using Sping Boot, JPA and H2 database

## Build

### Application archive:

Checkout sources from [https://github.com/finwee/TMobileHomework](https://github.com/finwee/TMobileHomework)

### Compile and start application:
Open terminal and execute following:

        mvn spring-boot:run
        

This will start application together with in memory H2 database. Basic initial data for testing are loaded automatically. The console for H2 is available at URL [http://localhost:8080/h2-console](http://localhost:8080/h2-console/). Here edit following:
- JDBC URL = jdbc:h2:mem:tmobile
- Username = sa
- Password leave empty


Application provides 4 endpoints under path [http://localhost:8080/api/tasks/](http://localhost:8080/api/tasks/). Swagger documentation of it can be found at [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

Whole app can be tested using CURL, PostMan, SOAPUI etc.

To create new task, just use CURL like this:
	
	curl -H "Content-Type: application/json" -X POST -d '{"userNote":"user note 1", "taskData" : "Zadany ukol", "acquiredByUserId" : 1, 
	"createdByUserId": 3}' 'http://localhost:8080/api/tasks'

To update existing task, just use CURL like this:

	curl -H "Content-Type: application/json" -X PUT -d '{"id":1, "userNote":"user note changed", "taskData" : "Zadany ukol zmenen", "acquiredByUserId" : 1, "createdByUserId" : 2}' 'http://localhost:8080/api/tasks'


To get task based on their user note, just use CURL like this with parameter userNote equals to what you want to search:
	
	curl -X GET 'http://localhost:8080/api/tasks/?userNote=REST'

To get task based on their acquired user, just use CURL like this with path variable acquired_by equals to what user id you want to search for:

	curl -X GET 'http://localhost:8080/api/tasks/acquired_by/2'

### Tests:
To run tests just type in terminal following:
		
		mvn test


## Question asked
- 	What DB to use? For demonstration purposes in-memory H2 will be sufficient
-	What DB constraint / indexes / datapyses to use? Please see comment in file [https://github.com/finwee/TMobileHomework/blob/main/src/main/resources/schema-h2.sql](https://github.com/finwee/TMobileHomework/blob/main/src/main/resources/schema-h2.sql)
-	Should we use some security layer? In real world yes but for sake of this demo not. Thats why value for createdBy user is also part of input JSON and not retrived based on authenticated user
- 	Please if any other questions needs to be answered, just drop me email to hubert.dostal@gmail.com

Thank you very much!
