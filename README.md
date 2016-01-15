Req
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


## cURL commands:
## REST API of this application is secured with Spring Security and every user of the API should be loged in.
## So primarily execute first command from the list below and then you can run the rest commands.
## 1. Log in command.
## Change the file path 'E:\opt\cookies.txt' for any you want.
## This file will contain an authentication information for the user visitor1.
curl http://localhost:8080/login -i -X POST -d username=visitor1 -d password=password -c E:\opt\cookies.txt
curl http://localhost:8080/login -i -X POST -d username=admin -d password=password -c E:\opt\cookies.txt

## 2. Get mock restaurants command.
## Use file path from 'log in command'
curl http://localhost:8080/restaurants -i --header -X GET -b E:\opt\cookies.txt

## 3. Add new restaurant command.
## Use file path from 'log in command'
curl http://localhost:8080/admin/addrestaurant -i --header -X POST  -b E:\opt\cookies.txt -H "Content-Type: application/json;charset=UTF-8" -d "{\"id\":123,\"name\":\"testRestaurant\",\"menu\":[{\"dishid\":null,\"name\":\"dish21\",\"price\":2100},{\"dishid\":null,\"name\":\"dish22\",\"price\":2300},{\"dishid\":null,\"name\":\"dish23\",\"price\":2400}]}"

curl http://localhost:8080/admin/addrestaurant -i --header -X POST  -b E:\opt\cookies.txt -H "Content-Type: application/json;charset=UTF-8" -d "{\"id\":123,\"name\":\"test2Restaurant\"}"