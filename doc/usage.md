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

`dir` supports relative path and absolute path(relative to classpath)

#### relative

```
Background:
* dir: features/http

Scenario: simple post with jsn
* uri: http://localhost:10080/users
* request body: requests/user.json
```

#### absolute, add `classpath:` as prefix
```
* request body: classpath:user.json
```

### base uri
In order to shorten the uri and reduce the duplication, you can configure a `base uri` and then use relative uri when
sending requests.

```
Feature: Http feature
  Basic http operations with verifications

  Background:
    * dir: features/http
    * base uri: http://localhost:10080

  Scenario: simple get
    * uri: /users/me
    * send: GET
    * status: 200
    * verify: '$.username'='jakim'
    * verify: '$.age'=18
```

You can still use absolute uri with `uri: http://host:port`


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

### Custom Header

```
Scenario: get with http header
  * uri: /custom_header
  * header: 'Accept'='text.plain'
  * send: GET
  * status: 200
```

### verify response as plain text
```
* response body:
"""
success
"""
```

### PUT
```
Scenario: simple put
  * uri: /users/me
  * request body:
  """
  {"username": "lj"}
  """
  * send: PUT
  * status: 200
  * verify: '$.username'='lj'
```
