## General requests

### Request all users
>`curl "http://localhost:8080//topjava17/rest/admin/users"`
#### Example of expected result
>{"id":100001,"name":"Admin","email":"admin@gmail.com","password":"admin","enabled":true,"registered":"2019-07-30T17:52:32.687+0000","roles":["ROLE_ADMIN","ROLE_USER"],"caloriesPerDay":2000,"meals":null},{"id":100000,"name":"User","email":"user@yandex.ru","password":"password","enabled":true,"registered":"2019-07-30T17:52:32.687+0000","roles":["ROLE_USER"],"caloriesPerDay":2000,"meals":null}
***
### Request all meals for current user
>`curl "http://localhost:8080//topjava17/rest/meals"`
#### Example of expected result
>{"id":100007,"dateTime":"2015-05-31T20:00:00","description":"Ужин","calories":510,"excess":true},{"id":100006,"dateTime":"2015-05-31T13:00:00","description":"Обед","calories":1000,"excess":true},{"id":100005,"dateTime":"2015-05-31T10:00:00","description":"Завтрак","calories":500,"excess":true},{"id":100004,"dateTime":"2015-05-30T20:00:00","description":"Ужин","calories":500,"excess":false},{"id":100003,"dateTime":"2015-05-30T13:00:00","description":"Обед","calories":1000,"excess":false},{"id":100002,"dateTime":"2015-05-30T10:00:00","description":"Завтрак","calories":500,"excess":false}
***
## Meal specific requests

### Request all meals for current user
>`curl "http://localhost:8080//topjava17/rest/meals/100002"`
>>where `100002` is a meal ID
#### Example of expected result
>{"id":100002,"dateTime":"2015-05-30T10:00:00","description":"Завтрак","calories":500,"user":null}
***
### Request deleting meal by id
>`curl -v -X DELETE http://localhost:8080//topjava17/rest/meals/100002`
>>where `100002` is a meal ID
#### Example of expected result
> TCP_NODELAY set
Connected to localhost (127.0.0.1) port 8080 (#0)
DELETE //topjava17/rest/meals/100002 HTTP/1.1
Host: localhost:8080
User-Agent: curl/7.65.1
Accept: */*
>
> Mark bundle as not supporting multiuse
HTTP/1.1 **`204`**
Date: Tue, 30 Jul 2019 18:55:25 GMT
***
### Request updating meal by id
>`curl -v -X PUT -H 'Content-Type: application/json' -d '{"dateTime":"2015-05-30T10:00:00","description":"Updated breakfast","calories":200}' http://localhost:8080//topjava17/rest/meals/100002`
>>where `100002` is a meal ID. required fields in json: dateTime, description and calories
#### Example of expected result
>TCP_NODELAY set
Connected to localhost (127.0.0.1) port 8080 (#0)
PUT //topjava17/rest/meals/100002 HTTP/1.1
Host: localhost:8080
User-Agent: curl/7.65.1
Accept: */*
Content-Type: application/json
Content-Length: 83
>
>[83 bytes data]
upload completely sent off: 83 out of 83 bytes
Mark bundle as not supporting multiuse
HTTP/1.1 **`204`**
Date: Tue, 30 Jul 2019 19:18:50 GMT
***
### Request creating meal for current user
>`curl -H 'Content-Type: application/json' -d '{"dateTime":"2015-06-01T18:00:00","description":"Created dinner","calories":300}' http://localhost:8080//topjava17/rest/meals`
#### Example of expected result
>{"id":100010,"dateTime":"2015-06-01T18:00:00","description":"Created dinner","calories":300,"user":null}
***
### Request meals for current user filtered by date and time
>`curl -G -d 'startDate=2015-05-30&endDate=2015-05-30&startTime=12:00:00&endTime=' http://localhost:8080//topjava17/rest/meals/filtered`
>>where `100002` is a meal ID. params startDate, endDate, startTime, endTime could be not specified like `endTime=`
#### Example of expected result
>{"id":100004,"dateTime":"2015-05-30T20:00:00","description":"Ужин","calories":500,"excess":false},{"id":100003,"dateTime":"2015-05-30T13:00:00","description":"Обед","calories":1000,"excess":false}