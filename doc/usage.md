Usage
=====

Panda just abstract DSL for API testing, all usages just cucumber gherkin steps

This page demonstrates the usage of the DSLs


BASIC HTTP REQUEST
------------------

### GET

```gherkin
* uri: http://localhost:10080/users/me
* send: GET
* status: 200
* verify: '$.username'='jakim'
* verify: '$.age'=18
```

### POST

```gherkin
* uri: http://localhost:10080/users
* request body:
"""
{"username": "jakim", "age": 18}
"""
* send: POST
* status: 200
* verify: '$.id'='auto-generated'
* verify: '$.username'='jakim'
* verify: '$.age'=18
```

### POST request body from file

```
* uri: http://localhost:10080/users
* request body: requests/user.json
* send: POST
* status: 200
* verify: '$.id'='auto-generated'
* verify: '$.username'='jakim'
* verify: '$.age'=18
```
