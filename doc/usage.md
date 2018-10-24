Usage
=====

Pandaria just abstracts DSL for API testing, after all it's still just cucumber steps

This page demonstrates the usage of the DSLs


Table of Contents
-----------------

* [Feature Configurtion](#feature-configuration)
    * [dir](#dir)
    * [base uri](#base-uri)
* [Test HTTP(S) APIs](#test-https-apis)
    * [Methods](#methods)
        * [GET](#get)
        * [POST](#post)
        * [PUT](#put)
        * [DELETE](#delete)
        * [PATCH](#patch)
        * [HEAD](#head)
        * [OPTIONS](#options)
        * [TRACE](#trace)
     * [Query Parameter](#query-parameter)
     * [Request header](#request-header)
     * [Cookie](#cookie)
     * [Request body](#request-body)
     * [Upload file](#upload-file)
     * [HTTPS](#https)

* [Database Operations](#database-operations)
    * [Queries](#queries)
    * [Execute SQL](#execute-sql)

* [MongoDB Operations](#mongodb-operations)
    * [Insert](#insert)
    * [Clear](#clear)
    * [Find All](#find-all)
    * [Find](#find)

* [Variables](#variables)
    * [Initialization](#initializaton)
    * [Definition](#definition)
        * [Literal string](#literal-string)
        * [String](#string)
        * [Integer](#integer)
        * [Extract from response body or results](#extract-from-response-body-or-results)
        * [Result of code evaluation](#result-of-code-evaluation)
    * [Use Variables](#use-variables)
        * [In URI](#in-uri)
        * [In File](#in-file)
        * [In Text](#in-text)
        * [In Code Evaluation](#in-code-evaluation)
    * [Escape](#escape)

* [Verification](#verification)
    * [Verify http response](#verify-http-response)
        * [response status](#status)
        * [response header](#response-header)
        * [response body](#response-body)
    * [Verify database tables](#verify-database-tables)
        * [Different database types](jdbc_types.md#verificaion-for-different-types)
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
        * [Other types](jdbc_types.md#verificaion-for-different-types)
    * [Verify datetime](#verify-datetime)
        * [Equals](#datetime-equals)
        * [Before](#before)
        * [After](#after)
    * [Verify JSON](#verify-json)
    * [Verify JSON schema](#verify-json-schema)
    * [Verify null](#verify-null)
    * [Verify variable](#verify-variable)
    * [Verify code evaluation](#verify-code-evaluation)
    * [Write your own](#write-your-own)

* [Wait](#wait)
    * [Simple Wait](#simple-wait)
    * [Wait Until](#wait-until)

* [Utilities](#utilities)

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
```gherkin
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

```gherkin
Background:
* dir: features/http

Scenario: simple post with jsn
* uri: http://localhost:10080/users
* request body: requests/user.json
```

#### absolute, add `classpath:` as prefix
```gherkin
* request body: classpath:user.json
```

### base uri
In order to shorten the uri and reduce the duplication, you can configure a `base uri` and then use relative uri when
sending requests.

```gherkin
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


Test HTTP(S) APIs
------------------

### Methods

#### GET

```gherkin
* uri: http://localhost:10080/users/me
* send: GET
* status: 200
* verify: '$.username'='jakim'
* verify: '$.age'=18
```

#### POST

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


#### Custom Header

```gherkin
Scenario: get with http header
  * uri: /custom_header
  * header: 'Accept'='text.plain'
  * send: GET
  * status: 200
```

#### verify response as plain text
```gherkin
* response body:
"""
success
"""
```

#### PUT
```gherkin
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

#### DELETE

```gherkin
Scenario: simple delete
  * uri: /users/20
  * send: DELETE
  * status: 200
```

#### PATCH
```gherkin
Scenario: simple patch
  * uri: /users/20
  * request body:
  """
  {"username": "lj"}
  """
  * send: PATCH
```

#### HEAD
```gherkin
Scenario: simple head
  * uri: /users
  * send: HEAD
  * status: 200
  * response header: 'test'='first,second,third'
  * response header: 'Date'='Thur, 2018 12'
```

#### OPTIONS

```gherkin
Scenario: simple options
  * uri: /users
  * send: OPTIONS
  * status: 200
  * response header: 'Allow'='OPTIONS, GET, HEAD'
```

#### TRACE

```gherkin
Scenario: simple trace
  * uri: /users
  * send: TRACE
  * status: 200
  * response header: 'Content-Type'='message/http'
```

### Query Parameter
```gherkin
* uri: /users?name=jakim&age=18

* uri: /users
* query parameter: 'name'="${name}"
* query parameter: 'age'='18'


* uri: /users?name=jakim
* query parameter: 'age'='18'
```

**If your query parameter has special charaters which need to be encoded, please use `query parameter`**

```gherkin
* uri: /users
* query parameter: 'name'='jakim li'
* send: GET
```

### Request Header
```gherkin
* uri: /custom_header
* header: 'Accept'='text.plain'
* header: 'Content-Type'='text/plain'
* send: GET
* status: 200
```
**The default request header is application/json, you can override it by setting a `Content-Type` header**

#### global request header
It's useful to have a http header that can be applied to every http request, for testing account authentication.
global header can be used in this way. Put below in your `application.properties`:

application.properties
```
http.headers.Authorization=Bear Token
http.headers.global=globalHeader

http.headers.will_be_overrided=not_overrided
```

Every http request will automatically apply these headers.

Global headers can be overrided use `header` keyword.

### Cookie
You can add cookie(s) to request
```gherkin
@http
Feature: Http feature
  Basic http operations with verifications

  Background:
    * dir: features/http
    * base uri: http://localhost:10080

  Scenario: add cookie
    * uri: /cookie
    * cookie: 'key'='value'
    * send: get
    * response body:
    """
    cookie added
    """

    * uri: /cookie
    * var: 'val'="value"
    * cookie: 'key'="${val}"
    * send: get
    * response body:
    """
    cookie added
    """
```

### Request Body

The request body can either come from a file or you write it directly as docstring in feature file.

#### From file
```gherkin
* uri: http://localhost:10080/users
* request body: requests/user.json
* send: POST
* status: 200
* verify: '$.id'='auto-generated'
* verify: '$.username'='jakim'
* verify: '$.age'=18
```

#### As docstring
```gherkin
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

The convention is the string right after `* request body: ` is path to the file, docstring is directly the request body

```gherkin
* request body: path_to_file
* request body:
"""
request body from docstring
"""
```

### Upload file
You can upload file as attachment
```gherkin
@file_upload
Feature: file upload
  be able to upload file

  Background:
    * dir: features/http
    * base uri: http://localhost:10080

  Scenario: upload file
    * uri: /files
    * attachment: attachments/abc.txt
    * send: POST
    * status: 200
    * response body:
    """
    uploaded
    """
```
**If you have attachment, request body(if have) will be ignored**

Multiple attachments are allowed.

### HTTPS
In most test environment, self-signed certificates are used for HTTPS, pandaria *DOES NOT* do host verification by
default. But you can enable it by put below configuration in your `application.properties`
```
http.ssl.verify=true
```
If you enable the host verification, you **MUST** specify the certificates information based on standardized system
properties for SSL configuration.

[JSSE](https://docs.oracle.com/javase/8/docs/technotes/guides/security/jsse/JSSERefGuide.html)


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

```gherkin
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
```gherkin
* execute sql:
"""
DROP TABLE IF EXISTS USERS;

CREATE TABLE USERS(
    NAME VARCHAR(256) NOT NULL,
    AGE INTEGER(5) NOT NULL
);
"""

* execute sql: drop_table.sql
* execute sql: setup.sql
```

MongoDB Operations
------------------
@since 0.2.0

### Insert
@since 0.2.0

Insert one document into a collection

```gherkin
* collection: 'users' insert:
"""
{"user": "jakim"}
"""
```
or put document in file

```gherkin
* collection: 'users' insert: document/alice.json
```

### Clear
@since 0.2.0

Delete all documents in collection

```gherkin
* collection: 'users' clear
```

### Find All
@since 0.2.0

Find all documents from collection, you can verify like verify in database, **it's always an JSON array**.

```gherkin
* collection: 'users' find all
* verify: '$[0].user'="alice"
```

### Find
@since 0.2.0

Instead of find all documents, you can filter the results.

```gherkin
* collection: 'users' clear

* collection: 'users' insert:
"""
{"user": "jakim", "age": 18}
"""

* collection: 'users' insert:
"""
{"user": "alice", "age": 16}
"""

* collection: 'users' find:
"""
{"age": {$gt: 17}}
"""

* verify: '$[0].user'="jakim"

* collection: 'users' find:
"""
{"age": {$lt: 17}}
"""

* verify: '$[0].user'="alice"

* collection: 'users' find: filter/greater_than_17.json
* verify: '$[0].user'="jakim"
```


Variables
---------

### Initialization
@since 0.2.1

You can put initial value in `application.properties`.

application.properties
```
variables.environment=test
```

```gherkin
Scenario: initial value from configuration file
  * verify: ${environment}="test"
  * var: 'environment'="production"
  * verify: ${environment}="production"
```

### Definition

#### Literal string
If you define variable use single quote, `'${name}'`, variable will **NOT** be replaced.

```gherkin
Scenario: const string
  * var 'name'='panda'
  * verify: ${name}='panda'
```

#### String
If you define variable use double quote, `"${name}"`, variable will be replaced.

```gherkin
Scenario: string
  * var 'name'='panda'
  * var 'great'="hello ${name}"
  * verify: ${great}='hello panda'
  * verify: ${great}="hello ${name}"
```

#### Integer
```gherkin
Scenario: integer
  * var 'age'=18
  * verify: ${age}=18
```

#### Extract from response body or results

It's useful if we can extract values from response body as variables. you can do it using `<-` like below.

`var: 'name'<-'json path'`  will extract value from the http response body json using the json path and assign it to the variable with specified name

```gherkin
Scenario: from json
  * uri: /not_important
  * send: GET
  * status: 200
  * var: 'name'<-'$.name'
  * var: 'age'<-'$.age'
  * var: 'iq'<-'$.iq'
  * verify: ${name}='panda'
  * verify: ${age}=18
  * verify: ${iq}=double: 80.0
```

You can also extract from database query results
```gherkin
* query: select.sql
* var: 'age'<-'$[0].age'
```

#### Result of code evaluation
@since 0.2.1

You can evaluate javascript code and assign the result as a variable.
```gherkin
* var: 'three'=3

* var: 'six'=code:
"""
${three} + 3
"""

* verify: ${six}=6

* var: 'zero'=code: ${three} - 3
* verify: ${zero}=0

* var: 'ten'=code file: six_add_four.js
* verify: ${ten}=10
```

### Use Variables
#### In URI
```gherkin
Scenario: variable being replaced in uri
  * var: 'path'="not_important"
  * uri: /${path}
  * send: GET
  * status: 200
  * verify: '$.name'='panda'
  * verify: '$.age'=18
  * verify: '$.iq'=double: 80.0
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
```gherkin
* response body:
"""
{"user":"${username}"}
"""
```

#### In code evaluation
@since 0.2.1

```gherkin
* var: 'three'=3
* var: 'six'=code:
"""
${three} + 3
"""
```

### Escape
You can escape the variables by place an extra `$`
```gherkin
* var: 'six'=code:
"""
$${three} + 3
"""
```
This gives error because `${three}` is not understand by javascript engine.

Verification
-----------

### Verify http response

#### response status
```gherkin
* uri: /empty_request
* send: POST
* status: 201
```

#### response header
```gherkin
* uri: /users
* send: TRACE
* status: 200
* response header: 'Content-Type'='message/http'
```

#### response body
verify use json path
```gherkin
* verify: '$.username'='jakim'
* verify: '$.age'=18
```

verify as text
```gherkin
* response body:
"""
success
"""
```

verify as text in file
```gherkin
* response body: responses/success.txt
```

Same with the request body, the convention is string right after `* response body:` is the path to file. the text
in docstring in next line is dirctly the response body.

### Verify database tables
You can verify database tables by writing sql with select statements, and then verify the result.

For table

| name | age |
|------|-----|
| jakim | 18 |
| panda | 28 |

in json array

```json
[
    { "name": "jakim", "age": 18 },
    { "name": "panda", "age": 28 }
]
```
**The query result will always be json array even if only one row in the result**

Then just using the same as you verify json http response body

```gherkin
* query: select.sql
* verify: '$[0].name'='jakim'
* verify: '$[0].age'=18
```


### Verify String
Although you can use string verificaton to non-string types, it will be converted to its string format.

#### Equals
`=` for equals, `!=` for not equals

```gherkin
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
  * verify: ${iq}!=double: 89.0
  * verify: ${age}!=${iq}

  * verify: '$.username'!="notjakim"
  * verify: '$.username'!="notja${kim}"

  * verify: '$.age'!=19
  * verify: '$.iq'!=double: 89.0
```

#### Contains

```gherkin
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

```gherkin
* verify: '$.username'='jakim'
* verify: '$.username' starts with: 'jak'

* var: 'username'="jakim"
* verify: ${username} starts with: 'jak'

* var: 'prefix'='jak'
* verify: '$.username' starts with: "${prefix}i"
* verify: ${username} starts with: "${prefix}i"
```

#### Ends with
```gherkin
* verify: '$.username'='jakim'
* verify: '$.username' ends with: 'kim'

* var: 'username'="jakim"
* verify: ${username} ends with: 'kim'

* var: 'suffix'='kim'
* verify: '$.username' ends with: "ja${suffix}"
* verify: ${username} ends with: "ja${suffix}"
```

#### Length
```gherkin
* verify: '$.username'='jakim'
* verify: '$.username' length: 5

* var: 'username'="jakim"
* verify: ${username} length: 5

* var: 'abc'=3
* verify: ${abc} length: 1
```

#### Regex match
```gherkin
* verify: '$.username'='jakim'
* verify: '$.username' matches: '.*'

* var: 'username'="jakim"
* verify: ${username} matches: 'j.*im'
```

### Verify numbers

#### Greater than

```gherkin
* verify: '$.age'>17
* verify: '$.iq'>double: 70.0
* verify: '$.age'>=18
* verify: '$.iq'>=double: 80.0

* var: 'age'=18
* var: 'iq'=80.0

* verify: ${age}>17
* verify: ${iq}>double: 79.0
* verify: ${age}>=17
* verify: ${iq}>=double: 80.0
```

### Less Than

```gherkin
* verify: '$.age'<19
* verify: '$.iq'<double: 90.0
* verify: '$.age'<=18
* verify: '$.iq'<=double: 80.0

* var: 'age'=18
* var: 'iq'=80.0

* verify: ${age}<19
* verify: ${iq}<double: 99.0
* verify: ${age}<=18
* verify: ${iq}<=double: 80.0
```

### Verify Datetime

#### datetime equals
```gherkin
Scenario: equals
  * query:
  """
  select `date`, `datetime`, `timestamp`, `time` from all_data_types;
  """
  * verify: '$[0].date'=datetime: '2008-10-10' pattern: 'yyyy-MM-dd'
  * verify: '$[0].date'=datetime: '10/10/2008+0800' pattern: 'dd/MM/yyyyZ'
  * verify: '$[0].datetime'=datetime: '2008-08-08 10:30:30' pattern: 'yyyy-MM-dd hh:mm:ss'
  * verify: '$[0].timestamp'=datetime: '2008-01-01 00:00:01' pattern: 'yyyy-MM-dd HH:mm:ss'
  * verify: '$[0].time'=datetime: '10:30:10' pattern: 'hh:mm:ss'
```

#### before
```gherkin
Scenario: before
  * query:
  """
  select `date`, `datetime`, `timestamp`, `time` from all_data_types;
  """
  * verify: '$[0].date' before: datetime: '2008-10-11' pattern: 'yyyy-MM-dd'
  * verify: '$[0].date' before: datetime: '11/10/2008+0800' pattern: 'dd/MM/yyyyZ'
  * verify: '$[0].datetime' before: datetime: '2008-08-08 10:30:31' pattern: 'yyyy-MM-dd hh:mm:ss'
  * verify: '$[0].timestamp' before: datetime: '2008-01-01 00:00:02' pattern: 'yyyy-MM-dd HH:mm:ss'
  * verify: '$[0].time' before: datetime: '10:30:11' pattern: 'hh:mm:ss'
```

#### After
```gherkin
Scenario: after
  * query:
  """
  select `date`, `datetime`, `timestamp`, `time` from all_data_types;
  """
  * verify: '$[0].date' after: datetime: '2008-10-09' pattern: 'yyyy-MM-dd'
  * verify: '$[0].date' after: datetime: '09/10/2008+0800' pattern: 'dd/MM/yyyyZ'
  * verify: '$[0].datetime' after: datetime: '2008-08-08 10:30:29' pattern: 'yyyy-MM-dd hh:mm:ss'
  * verify: '$[0].timestamp' after: datetime: '2008-01-01 00:00:00' pattern: 'yyyy-MM-dd HH:mm:ss'
  * verify: '$[0].time' after: datetime: '10:30:09' pattern: 'hh:mm:ss'
```

### Verify JSON

#### same json
* Allow different order in array
* **NOT** allow extra items in array
* **NOT** allow extra or missing object

```
* uri: /users/me
* send: get
* verify: '$' same json:
"""
{
  "iq": 80.0,
  "username": "jakim",
  "age": 18
}
"""
* verify: '$' same json: responses/jakim.json
```

#### contains json
* Allow different order in array
* Allow extra item(s) in array
* Allow extra object(s)
* **NOT** allow missing fields

```gherkin
Scenario: contains json, extra fields allowed
  * uri: /users/list
  * send: get
  * verify: '$' contains json:
  """
  [
    {
      "name": "jakim"
    },
    {
      "name": "smart", friends: ["sue"]
    }
  ]
  """

  * var: 'response'<-'$'
  * verify: '$' contains json:
  """
  [
    {
      "name": "jakim"
    },
    {
      "name": "smart", friends: ["sue"]
    }
  ]
  """
```

### Verify JSON Schema
[JSON schema](https://json-schema.org/) is useful to describe the API, and it's useful especially for contract testing. you can verify json
document(instance) conforms to a given json schema or not.

```gherkin
@verify_json_schema
Feature: verify json schema
  verify if json valid for json schema

  Background:
    * dir: features/verification
    * base uri: http://localhost:10080

  Scenario: verify json schema
    * uri: /products/1
    * send: get
    * verify: '$' conform to:
    """
    {
    "$schema": "http://json-schema.org/draft-07/schema#",
    "$id": "http://example.com/product.schema.json",
    "title": "Product",
    "description": "A product in the catalog",
    "type": "object"
    }
    """

    * verify: '$' conform to: schema/product.schema.json

    * verify: '$.tags' conform to:
    """
    {
      "$schema": "http://json-schema.org/draft-07/schema#",
      "$id": "http://example.com/product.tags.schema.json",
      "title": "product tags",
      "description": "Product tags",
      "type": "array",
      "items": {
        "type": "string"
      }
    }
    """
```


### Verify null
```gherkin
Scenario: null check
  * uri: /users/me
  * send: GET
  * status: 200
  * verify: '$.username'='jakim'
  * verify: '$.username' is not null

  * var: 'username'="jakim"
  * verify: ${username} is not null
  * verify: ${hello} is null

  * uri: /getnull
  * send: GET
  * status: 200
  * verify: '$.notexist' is null
```

### Verify variable
You can verify is response/result/variable equals/not-equals to a variable.
```gherkin
* verify: '$.username'=${user}
* verify: '$.username'!=${kim}

* verify: ${user}=${user}
* verify: ${user}!=${kim}
```

### Verify code evaluation
@since 0.2.1

You can write javascript code snippet for verification, there are two forms, verify against the evaluation result or
verify the evaluation result is true.

You can write the code snippet in one line, in block as doc string, or in separate file.

**Things to note**
* If you have multiple lines in the code snippet, only the result of the last line will be used as the result.
* If you need to write complex code in the snippet, consider to put them in java with cucumber steps first.
* [Nashorn](https://docs.oracle.com/javase/8/docs/technotes/guides/scripting/nashorn/api.html) is used for script evaluation.
* If your jdk version under java 8u40, you might encounter issue about 0 was returned as 0.0, you can either upgrade your jdk version
or you need to use it as double.

#### verify response and variable equals the result of the evaluation
@since 0.2.1

```gherkin
* var: 'age'=16
* var: 'iq'=90.0

* uri: http://localhost:10080/not_important
* send: get
* verify: '$.age'=code: ${age} + 2
* verify: '$.iq'=code: ${iq} - 10

* verify: '$.age'!=code: ${age} + 3
* verify: '$.iq'!=code: ${iq} - 11

* verify: '$.age'=code:
"""
${age} + 2
"""
* verify: '$.iq'=code:
"""
${iq} - 10
"""

* verify: '$.age'=code file: 18.js
* verify: '$.iq'!=code file: 18.js
```

#### verify the evaluation to be true
@since 0.2.1

**Be notice the double equals `==` are used for comparison in javascript instead of single equal `=` which was used in pandaria.**

```gherkin
* verify code: ${name} == ${iq} / 3
* verify code:
"""
${name} != ${iq} % 3
"""
* verify code file: verification.js
```

### Write your own
It's impossible for pandaria to provide all the verificaitons, you can always write your own verifications.
Here is a [tutorial on how](custom_verification_tutorial.md)


Wait
----
Wait is useful for automation testing and sometimes is necessary.

### Simple wait
Only support milliseconds and seconds, we don't recomment to wait for very long time.

```gherkin
Scenario: wait
  * wait: 1000ms
  * wait: 1s
```

### Wait until
Waiting is a time consuming step, sometimes make the tests slow, but it's necessary.

`wait 1000ms times 3` specifies the framework to wait 3 times, each time wait 1000ms

Run this step **DOSE NOT** put the thread in sleep immediately. if the first coming verification failed, then it
actually put the thread in sleep for `1000ms`, and then retry once, and this process will repeat `3` times.

#### wait until API respond expected response

`GET /sequence` returns plain text in sequence

```java
server.server()
        .get(by(uri("/sequence")))
        .response(seq("1", "2", "3", "4", "5", "6", "7", "8", "9", "10"));
```

Wait until:
```gherkin
Scenario: wait until
  * wait: 1000ms times: 3
  * uri: /sequence
  * send: GET
  * response body:
  """
  3
  """

  * uri: /sequence
  * send: GET
  * response body:
  """
  4
  """

  * wait: 1s times: 3
  * uri: /sequence
  * send: GET
  * response body:
  """
  5
  """

  * uri: /sequence
  * send: GET
  * response body:
  """
  6
  """
```

#### wait until database query results expected

```gherkin
* wait: 1000ms times: 3
* query:
"""
SELECT NAME, AGE FROM USERS;
"""
* verify: '$[0].name'="jakim"
* verify: '$[0].age'=18
```

**Although between wait and the first verificaiton, there can be multiple actions(http request or database queries),**
**all actions between will be take as retry, but only the last action can be verified.**
**For example:**
```gherkin
* wait: 1000ms times: 3
* uri: /sequence
* send: GET
# no verifiction for this http request

* query:
"""
SELECT NAME, AGE FROM USERS;
"""
* verify: '$[0].name'="jakim"
* verify: '$[0].age'=18
```
**Both the database query and the http request will be repeated, but verification can only be applied to the database query**

Utilities
---------

You use utitlities by assign them as variable and use it.

### Random UUID
```gherkin
Scenario: generate random number
  * var: 'uuid'=random uuid
  * verify: ${uuid} length: 36
```

Example uuid: `123e4567-e89b-12d3-a456-556642440000`
