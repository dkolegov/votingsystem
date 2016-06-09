###Requirements for this project
Design and implement a JSON API using Hibernate/Spring/SpringMVC without frontend.
The task is:
Build a voting system for deciding where to have lunch.
2 types of users: admin and regular users
Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)
Menu changes each day (admins do the updates)
Users can vote on which restaurant they want to have lunch at
Only one vote counted per user
If user votes again the same day:
If it is before 11:00 we asume that he changed his mind.
If it is after 11:00 then it is too late, vote can't be changed
Each restaurant provides new menu each day.

As a result, provide a link to github repository. It should contain the code, README.md with API documentation and couple curl commands to test it.

P.S.: you can use a project seed you find where all technologies are already preconfigured.

P.P.S.: Make sure everything works with latest version that is on github :)

P.P.P.S.: Asume that your API will used by a frontend developer to build frontend on top of that.

###Packaging and deploying
#### Prerequisites
- Java 8
- Maven > 3.0

#### Main steps:
- Command to create an executable jar:

    $ mvn package

- Command to run the executable jar created on the previous step(with embedded Tomcat):

    $ java -jar target/websocketauth-0.0.1-SNAPSHOT.jar

- After step 2 the application should be accessable by 'http://localhost:8080/'
###ABOUT
The application configured with predefined users(username, role, password):
- admin, ADMIN, qwerty123
- visitor1, USER, 123456
- visitor2, USER, qwerty
- visitor3, USER, qwerty123

If you want to add new user you can change init script: src/main/resources/db/mysql/populateSecurityDB.sql. As you can see we use a hash instead of a real password to store in the DB.
To generate a hash use a test class com.voting.system.utils.EncryptPassword. Put a generated hash in the users table with a user name. Add role for the user in user_roles table. Available roles: ROLE_ADMIN, ROLE_USER.

###Supported services:
- Get all restaurants: 
	* `GET /restaurants`
	* no parameters
- Add new restaurant:
	* `POST /admin/restaurant`
	* parameters: restarant json. Ex.:`{"name":"testRestaurant","menu":[{"dishid":null,"name":"dish21","price":2100},{"dishid":null,"name":"dish22","price":2300},{"dishid":null,"name":"dish23","price":2400}]}`
- Change restaurant's menu. Note: restaurantId you can receive from 'Get all restaurants' services response:
	* `PUT /admin/restaurant/{restaurantId}`
	* parameters: restarant json. Ex.:`{"id":"restaurantId","menu":[{,"name":"dish21","price":2100},{"name":"dish22","price":2300},{"name":"dish23","price":2400}]}`

- Vote for restaurant. Note: restaurantId you can receive from 'Get all restaurants' services response:
	* `POST /vote/{restaurantId}`
	* no parameters

- Get all votes:
	* `GET /admin/votes`
	* no parameters

### cURL commands to test the RESTful services:
The REST API of this application is secured with Spring Security and every user of the API should be logged in.
So primarily execute first command from the list below and then you can run the rest commands.
- Log in command.
	Change the file path `E:\opt\cookies.txt` for any you want.
This file will contain an authentication information for the user visitor1.
	* `curl http://localhost:8080/login -i -X POST -d username=visitor1 -d password=password -c E:\opt\cookies.txt`
	* `curl http://localhost:8080/login -i -X POST -d username=admin -d password=password -c E:\opt\cookies.txt`

- Get restaurants command.
	Use file path(Ex. E:\opt\cookies.txt) from log in command
	* `curl http://localhost:8080/restaurants -i --header -X GET -b E:\opt\cookies.txt`

- Add new restaurant command.
	Use file path(Ex. E:\opt\cookies.txt) from log in command
	* `curl http://localhost:8080/admin/restaurant -i --header -X POST  -b E:\opt\cookies.txt -H "Content-Type: application/json;charset=UTF-8" -d "{\"name\":\"testRestaurant\",\"menu\":[{\"name\":\"dish21\",\"price\":2100},{\"name\":\"dish22\",\"price\":2300},{\"name\":\"dish23\",\"price\":2400}]}"`

- Vote command.
	Use file path(Ex. `E:\opt\cookies.txt`) from log in command
	* `curl http://localhost:8080/vote/2 -i -X POST -b E:\opt\cookies.txt`

