Usage
=====

Panda just abstracts DSL for API testing, after all it's still just cucumber steps

This page demonstrates the usage of the DSLs


Table of Contents
-----------------

* [Feature Configurtion](#feature-configuration)
    * [dir](#dir)
    * [base uri](#base-uri)
* [Test HTTP APIs](#test-http-apis)
    * [GET](#get)
    * [POST](#post)
    * [POST request body from file](#post-request-body-from-file)
    * [PUT](#put)
    * [DELETE](#delete)
    * [PATCH](#patch)
    * [HEAD](#head)
    * [OPTIONS](#options)
    * [TRACE](#trace)
* [Variables](#variables)
    * [Defintion](#defintion)
        * [Literal string](#literal-string)
        * [String](#string)
        * [Integer](#integer)
        * [Extract from response body](#extract-from-response-body)
    * [Use Variables](#use-variables)
        * [In URI](#in-uri)
        * [In File](#in-file)
        * [In Text](#in-text)

* [Verfiation](#verification)
    * [Verify http response](#verify-http-response)
        * [response status](#status)
        * [response header](#response-header)
        * [response body](#response-body)
    * [Verify String](#verify-string)
        * [Contains](#contains)

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


Test HTTP APIs
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

### OPTIONS

```
Scenario: simple options
  * uri: /users
  * send: OPTIONS
  * status: 200
  * response header: 'Allow'='OPTIONS, GET, HEAD'
```

### TRACE

```
Scenario: simple trace
  * uri: /users
  * send: TRACE
  * status: 200
  * response header: 'Content-Type'='message/http'
```

Variables
---------

### Defintion

#### Literal string
If you define variable use single quote, `'${name}'`, variable will **NOT** be replaced.

```
Scenario: const string
  * var 'name'='panda'
  * verify: ${name}='panda'
```

#### String
If you define variable use double quote, `"${name}"`, variable will be replaced.

```
Scenario: string
  * var 'name'='panda'
  * var 'great'="hello ${name}"
  * verify: ${great}='hello panda'
  * verify: ${great}="hello ${name}"
```

### Integer
```
Scenario: integer
  * var 'age'=18
  * verify: ${age}=18
```

#### Extract from response body

It's useful if we can extract values from response body as variables. you can do it using `<-` like below.

`var: 'name'<-'json path'`  will extract value from the http response body json using the json path and assign it to the variable with specified name

```
Scenario: from json
  * uri: /not_important
  * send: GET
  * status: 200
  * var: 'name'<-'$.name'
  * var: 'age'<-'$.age'
  * var: 'iq'<-'$.iq'
  * verify: ${name}='panda'
  * verify: ${age}=18
  * verify: ${iq}=80.0
```

### Use Variables
#### In URI
```
Scenario: variable being replaced in uri
  * var: 'path'="not_important"
  * uri: /${path}
  * send: GET
  * status: 200
  * verify: '$.name'='panda'
  * verify: '$.age'=18
  * verify: '$.iq'=80.0
```

#### In file

requsts/someone.json
```json
{
  "name": "${name}"
}
```

```
Scenario: variable used in request file
  * var: 'name'='someone'
  * uri: /users
  * request body: requests/someone.json
  * send: POST
  * status: 201
```

#### In text
```
* response body:
"""
{"user":"${username}"}
"""
```


Verificaton
-----------

### Verify http response

#### response status
```
* uri: /empty_request
* send: POST
* status: 201
```

#### response header
```
* uri: /users
* send: TRACE
* status: 200
* response header: 'Content-Type'='message/http'
```

#### response body
verify use json string
```
* verify: '$.username'='jakim'
* verify: '$.age'=18
```

verify as text
```
* response body:
"""
success
"""
```

verify as text in file
```
* response body: responses/success.txt
```

### Verify String

#### Contains

```
Scenario: contains
  * uri: /users/me
  * send: GET
  * status: 200
  * verify: '$.username'='jakim'
  * verify: '$.username' contains: 'kim'
```
