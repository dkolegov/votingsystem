#ABOUT
###The application configured with
###predefined users:
	admin, roles: ADMIN, USER
	visitor1, roles: USER
	visitor2, roles: USER
	visitor3, roles: USER

##Supported services:
###1. Get all restaurants
	Sublink: /restaurants
	Method: GET
	Parameters: no
###2. Add new restaurant
	Sublink: /admin/addrestaurant
	Method: POST
	Parameters: restarant json. Ex.:{"name":"testRestaurant","menu":[{"dishid":null,"name":"dish21","price":2100},{"dishid":null,"name":"dish22","price":2300},{"dishid":null,"name":"dish23","price":2400}]}
###3. Change restaurant's menu. Note: restaurantId you can receive from 'Get all restaurants' services response. 
	Sublink: /admin/changemenu/
	Method: POST
	Parameters: restarant json. Ex.:{"id":"restaurantId","menu":[{,"name":"dish21","price":2100},{"name":"dish22","price":2300},{"name":"dish23","price":2400}]}

###4. Vote for restaurant. Note: restaurantId you can receive from 'Get all restaurants' services response.
	Sublink: /vote/{restaurantId}
	Method: POST
	Parameters: no

## cURL commands(tested on Windows 7 only):
### REST API of this application is secured with Spring Security and every user of the API should be loged in.
### So primarily execute first command from the list below and then you can run the rest commands.
### 1. Log in command.
### Change the file path 'E:\opt\cookies.txt' for any you want.
### This file will contain an authentication information for the user visitor1.
	curl http://localhost:8080/login -i -X POST -d username=visitor1 -d password=password -c E:\opt\cookies.txt
	curl http://localhost:8080/login -i -X POST -d username=admin -d password=password -c E:\opt\cookies.txt

### 2. Get restaurants command.
### Use file path(Ex. E:\opt\cookies.txt) from log in command
	curl http://localhost:8080/restaurants -i --header -X GET -b E:\opt\cookies.txt

### 3. Add new restaurant command.
###  Use file path(Ex. E:\opt\cookies.txt) from log in command
	curl http://localhost:8080/admin/addrestaurant -i --header -X POST  -b E:\opt\cookies.txt -H "Content-Type: application/json;charset=UTF-8" -d "{\"id\":123,\"name\":\"testRestaurant\",\"menu\":[{\"name\":\"dish21\",\"price\":2100},{\"name\":\"dish22\",\"price\":2300},{\"name\":\"dish23\",\"price\":2400}]}"

### 4. Vote command.
###  Use file path(Ex. E:\opt\cookies.txt) from log in command
	curl http://localhost:8080/vote/2 -i -X POST -b E:\opt\cookies.txt

