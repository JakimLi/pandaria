Usage
=====

Pandaria just abstracts DSL for API testing, after all it's still just cucumber steps

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

* [Database Operations](#database-operations)
    * [Queries](#queries)
    * [Execute SQL](#execute-sql)

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
    * [Verify database tables](#verify-database-tables)
    * [Verify String](#verify-string)
        * [Equals](#equals)
        * [Contains](#contains)
        * [Starts With](#starts-with)
        * [Ends With](#ends-with)
        * [Length](#length)
        * [Regex Match](#regex-match)
    * [Verify numbers](#verify-numbers)
        * [Greater than](#greater-than)
        * [Less than](#less-than)

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

Database Operations
-------------------
You can directly use sql to operate on database, pandaria use spring jdbc, you need to configure your datasource
in `application.properties` like below

```
spring.datasource.url=jdbc:mysql://localhost:3307/pandaria?useSSL=false&allowMultiQueries=true
spring.datasource.username=root
spring.datasource.password=password
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
```

### Queries
You can write sql with select statements to query database and verify the results using the json path.

**The results of select will always be array, keep it in mind when using json path**

```
Feature: database
  database related operations

  Background:
    * dir: features/database

  Scenario: execute sql and query
    * execute sql:
    """
    DROP TABLE IF EXISTS USERS;

    CREATE TABLE USERS(
        NAME VARCHAR(256) NOT NULL,
        AGE INTEGER(5) NOT NULL
    );

    INSERT INTO USERS(NAME, AGE) VALUES('jakim', 18);
    """

    * query:
    """
    SELECT NAME, AGE FROM USERS
    """
    * verify: '$[0].name'='jakim'
    * verify: '$[0].age'=18

  Scenario: sql from file
    * execute sql: drop_table.sql
    * execute sql: setup.sql
    * query: select.sql
    * verify: '$[0].name'='jakim'
    * verify: '$[0].age'=18
```

### Execute SQL
```
* execute sql:
"""
DROP TABLE IF EXISTS USERS;

CREATE TABLE USERS(
    NAME VARCHAR(256) NOT NULL,
    AGE INTEGER(5) NOT NULL
);

* execute sql: drop_table.sql
* execute sql: setup.sql
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

### Verify database tables
You can verify database tables by writing sql with select statements, and then verify the result.

Map the table to json array.

| name | age |
|------|-----|
| jakim | 18 |
| panda | 28 |

is json

```
[
    { "name": "jakim", "age": 18 },
    { "name": "panda", "age": 28 }
]
```
**The query result will always be json array even if only one row in the result**

Then just using the same as you verify json http response body

```
* query: select.sql
* verify: '$[0].name'='jakim'
* verify: '$[0].age'=18
```


### Verify String
Although you can use string verificaton to non-string types, it will be converted to its string format.

#### Equals
`=` for equals, `!=` for not equals

```
Scenario: equals
  * uri: /users/me
  * send: GET
  * status: 200
  * verify: '$.username'='jakim'

  * var: 'kim'="kim"
  * var: 'user'='jakim'
  * verify: ${user}="ja${kim}"

  * verify: '$.username'="jakim"
  * verify: '$.username'="ja${kim}"

  * var: 'age'=18
  * var: 'iq'=80.0
  * verify: ${user}!='notjakim'
  * verify: ${user}!="notja${kim}"

  * verify: ${age}!=19
  * verify: ${iq}!=89.0
  * verify: ${age}!=${iq}

  * verify: '$.username'!="notjakim"
  * verify: '$.username'!="notja${kim}"

  * verify: '$.age'!=19
  * verify: '$.iq'!=89.0
```

#### Contains

```
Scenario: contains
  * uri: /users/me
  * send: GET
  * status: 200
  * verify: '$.username'='jakim'
  * verify: '$.username' contains: 'kim'

  * var: 'username'="panda"
  * verify: ${username} contains: 'anda'
```

#### Starts with

```
* verify: '$.username'='jakim'
* verify: '$.username' starts with: 'jak'

* var: 'username'="jakim"
* verify: ${username} starts with: 'jak'

* var: 'prefix'='jak'
* verify: '$.username' starts with: "${prefix}i"
* verify: ${username} starts with: "${prefix}i"
```

#### Ends with
```
* verify: '$.username'='jakim'
* verify: '$.username' ends with: 'kim'

* var: 'username'="jakim"
* verify: ${username} ends with: 'kim'

* var: 'suffix'='kim'
* verify: '$.username' ends with: "ja${suffix}"
* verify: ${username} ends with: "ja${suffix}"
```

#### Length
```
* verify: '$.username'='jakim'
* verify: '$.username' length: 5

* var: 'username'="jakim"
* verify: ${username} length: 5

* var: 'abc'=3
* verify: ${abc} length: 1
```

#### Regex match
```
* verify: '$.username'='jakim'
* verify: '$.username' matches: '.*'

* var: 'username'="jakim"
* verify: ${username} matches: 'j.*im'
```

### Verify numbers

#### Greater than

```
* verify: '$.age'>17
* verify: '$.iq'>70.0
* verify: '$.age'>=18
* verify: '$.iq'>=80.0

* var: 'age'=18
* var: 'iq'=80.0

* verify: ${age}>17
* verify: ${iq}>79.0
* verify: ${age}>=17
* verify: ${iq}>=80.0
```

### Less Than

```
* verify: '$.age'<19
* verify: '$.iq'<90.0
* verify: '$.age'<=18
* verify: '$.iq'<=80.0

* var: 'age'=18
* var: 'iq'=80.0

* verify: ${age}<19
* verify: ${iq}<99.0
* verify: ${age}<=18
* verify: ${iq}<=80.0
```
