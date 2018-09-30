Usage
=====

Panda just abstracts DSL for API testing, after all it's still just cucumber steps

This page demonstrates the usage of the DSLs


Table of Contents
=================

* [Feature Configurtion](#feature-configuration)
    * [dir](#dir)
    * [base uri](#base-uri)
* [Basic HTTP Request](#basic-http-request)
    * [GET](#get)
    * [POST](#post)
    * [POST request body from file](#post-request-body-from-file)
    * [PUT](#put)
    * [DELETE](#delete)
    * [PATCH](#patch)
    * [HEAD](#head)


Feature Configuration
---------------------
You must configure some basics to make the framework work properly.

### dir
In order to be able to use files locates relate to the feature file, you must use `dir` to specify
the directory of the current feature file, best practice is put it in the `Background` section.

If you have below directory structure.

```
resources
└── features
    └── http
         ├── http.feature
         └── requests
         └── user.json
```

You can use the json file as request body as below
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

You can use absolute uri with `uri: http://host:port`, best practice is to make it short


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

### DELETE

```
Scenario: simple delete
  * uri: /users/20
  * send: DELETE
  * status: 200
```

### PATCH
```
Scenario: simple patch
  * uri: /users/20
  * request body:
  """
  {"username": "lj"}
  """
  * send: PATCH
```

### HEAD
```
Scenario: simple head
  * uri: /users
  * send: HEAD
  * status: 200
  * response header: 'test'='first,second,third'
  * response header: 'Date'='Thur, 2018 12'
```
