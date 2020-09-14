# Exercise - People within Location
This is the program to get the users within the defined distance from city


About the program
---
Application is the solution to the requirement being provided by exposing the endpoints of the Rest API using Java,Spring, Spring boot framework.<br/>
The service can be reached at    http://{ipaddress}:8080/api/v1/location/users <br/>
Service by default returns the people who are within London or whose coordinates are within 50 miles of London <br/>



## How to execute 


* Application is maven project in zipped format. After unzipping it ,please import as maven project in IDE which supports Java projects (like eclipse, intellij etc). Run DwpTechnologyApplication.java as Java Application. Application should launch at port default port 8080.
<br/><br/>
* Use the jar file available in the zip and use java -jar command to launch the jar file.
<br/><br/>
* Use Postman/curl or any application using which HTTP request can be made to application to get the response from the application.
<br/><br/>

## Requirements with endpoints and assumptions

* To get the detail of the people within 50 miles of London : Is exposed as 
     
 ``` 
  URL :- http://{ip-address}:8080/api/v1/location/users 
  Response :- Returns data with HTTP status code 200 OK 
 ```
  
* Service is capable to return data by providing number miles as request parameter to return response with people details within passed number of miles of London  
    					  
```
  URL :- http://{ip-address}:8080/api/v1/location/users?distance=500  
  Response :- Returns data with HTTP status code 200 OK 
	
```

* Service can also return by providing number miles & city as request parameter to return response with people details within asked number of miles of city passed  
            
 ```
  URL :- http://{ip-address}:8080/api/v1/location/users?distance=500&location=london
  Response :- Returns data with HTTP status code 200 OK 

 ```
 
  ```
  URL :- http://{ip-address}:8080/api/v1/location/users?distance=500&location=birmingham
  Response :- Returns data with HTTP status code 200 OK 

 ```
 
 * Few Error scenarios
 
  ```
  URL :- http://{ip-address}:8080/api/v1/location/users?distance=-1&location=birmingham
  Response :-															  HTTP status code 500 Internal Server Error 
  {
    "description": "getUsersWithinDistance.distance: must be greater than or equal to 0"
  }
  
    URL :-  http://{ip-address}:8080/api/v1/location/users?distance=1&location=dodo
  Response :-	<No Response>												           HTTP status code 200 OK 
 

 ```
 
 
 * If location passed is not supported by Remote application https://bpdts-test-app.herokuapp.com , then also this service can reply for the cities whose coordinates are configured <br/>
	Right now London , Birmingham , Sheffield , Manchester are supported . Service checks if user location lies within passed distance of the city.
	
 ```
  URL :- http://{ip-address}:8080/api/v1/location/users?distance=500&location=birmingham
  Response :- Returns data with HTTP status code 200 OK 
 ```

## Assumptions
 * The remote application on which this service is dependent https://bpdts-test-app.herokuapp.com/users is up and running.
 * Distance passed as parameter to call the service will be considered in miles only.
 * Right now cities like London , Birmingham , Sheffield , Manchester are supported.
 * If no parameters are passed then the default distance of 50 miles and default location London is considered. 
 * Haversine formula is used to calculate the distance between the city cordinates and the user cordinates.  
 
  ```
 This service contacts remote service 
 https://bpdts-test-app.herokuapp.com/users : To get all the users <br/>
 https://bpdts-test-app.herokuapp.com/city/London/users : To get users within London
 ```



## Useful Commands
 * use maven command :-  to build the application
 
 ```
 > mvn clean install   
 Expected output :
  
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
 ```
  
 * use maven command :- to execute the test cases
      
 ```
 >  mvn clean test      
  Expected out :
 
[INFO]
[INFO] Results:
[INFO]
[INFO] Tests run: 19, Failures: 0, Errors: 0, Skipped: 0
 ```
 
 <br/>
<h4 align='center'>Thankyou</h4> 


 