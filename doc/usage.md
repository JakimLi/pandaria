Usage
=====

Panda just abstract DSL for API testing, all usages just cucumber gherkin steps

This page demonstrates the usage of the DSLs

Feature Configuration
---------------------
You must configure some basics to make the framework work properly.

### dir
In order to be able to use files locateds relate to current feature file, you must use `dir` to specify
the directory of the current feature file, suggest to put it in the `Background` section.

If we have below directory structure.

```
resources
└── features
    └── http
         ├── http.feature
         └── requests
         └── user.json
```

We can use the json file as request body as below
```
Feature: Http feature
  Basic http operations with verifications

  Background:
    * dir: features/http

  Scenario: simple post with jsn
    * uri: http://localhost:10080/users
    * request body: requests/user.json
    * send: POST
    * status: 200
    * verify: '$.id'='auto-generated'
    * verify: '$.username'='jakim'
    * verify: '$.age'=18
```


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
