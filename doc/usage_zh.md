使用介绍
=====

Pandaria只是基于cucumber抽象的DSL，用于API自动化测试。

本文介绍DSL的相关用法。


目录
-----------------

* [特性配置](#特性配置)
    * [dir](#dir)
    * [base uri](#base-uri)
    * [faker locale](#faker-locale)
* [测试HTTP(S) APIs](#测试https-apis)
    * [方法](#方法)
        * [GET](#get)
        * [POST](#post)
        * [PUT](#put)
        * [DELETE](#delete)
        * [PATCH](#patch)
        * [HEAD](#head)
        * [OPTIONS](#options)
        * [TRACE](#trace)
     * [查询参数](#查询参数)
     * [请求头](#请求头)
     * [Cookie](#cookie)
     * [请求体](#请求体)
     * [上传文件](#上传文件)
     * [表单](#表单)
     * [HTTPS](#https)
     * [代理](#代理)

* [Graphql测试](#graphql测试)
    * [URL](#url)
    * [query](#query)
    * [mutation](#mutation)
    * [variables](#varaibles)
    * [操作名称](#操作名称)

* [数据库操作](#数据库操作)
    * [查询](#查询)
    * [执行SQL](#执行sql)
    * [多数据源](#多数据源)

* [MongoDB操作](#mongodb操作)
    * [插入](#插入)
    * [清除](#清除)
    * [查找所有](#查找所有)
    * [查找](#查询)

* [变量](#变量)
    * [初始化](#初始化)
    * [定义](#定义)
        * [字面量](#字面量)
        * [字符串](#字符串)
        * [整形](#整形)
        * [从返回体或结果中抽取](#从返回体或结果中抽取)
        * [从返回cookie中抽取](#从返回cookie中抽取)
        * [从返回体中抽取纯文本](#从返回体中抽取纯文本)
        * [从代码块中抽取](#从代码块中抽取)
    * [使用变量](#使用变量)
        * [在URI中](#在URI中)
        * [在文件中](#在文件中)
        * [在文本中](#在文本中)
        * [在请求头信息中](#在请求头信息中)
        * [在代码块时](#在代码块中)
    * [转义](#转义)
    * [Faker特殊变量](#Faker特殊变量)
    * [上次请求结果特殊变量](#上次结果特殊变量)

* [验证](#验证)
    * [验证http响应](#验证http响应)
        * [响应状态](#响应状态)
        * [响应头](#响应头)
        * [响应体](#响应体)
    * [验证数据库表](#验证数据库表)
        * [不同表类型](jdbc_types.md#verificaion-for-different-types)
    * [验证字符串](#验证字符串)
        * [相等](#相等)
        * [包含](#包含)
        * [开始](#开始)
        * [结束](#结束)
        * [长度](#长度)
        * [正则表达式](#正则表达式)
    * [验证数字](#验证数字)
        * [大于](#大于)
        * [小于](#小于)
        * [其他类型](jdbc_types.md#verificaion-for-different-types)
    * [验证日期和时间](#验证日期和时间)
        * [等于](#日期或时间相等)
        * [之前](#之前)
        * [之后](#之后)
    * [验证JSON](#验证json)
    * [验证JSON schema](#验证json-schema)
    * [验证null](#验证null)
    * [验证变量](#验证变量)
    * [验证代码](#验证代码)
    * [自定义验证](#自定义验证)

* [等待](#等待)
    * [简单等待](#简单等待)
    * [等待直至次数](#等待直至次数)

* [工具](#工具)

* [数据驱动](#数据驱动)

特性配置
---------------------
为了能够让框架正常工作，在每个feature文件中首先需要配置一些基本信息。

### dir
为了能够在feature文件中使用文件路径找到文件，你必须使用`dir`指定当前feature文件的所在目录，最好把这种配置信息
放在`Background`中。

如果你的目录结构如下:

```
resources
└── features
    └── http
         ├── http.feature
         └── requests
            └── user.json
```

你可以使用json文件作为请求体:
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
`dir` 支持相对路径和绝对路径(相对路径是相对于classpath而言)

#### 相对路径:

```gherkin
Background:
* dir: features/http

Scenario: simple post with jsn
* uri: http://localhost:10080/users
* request body: requests/user.json
```

#### 绝对路径, 添加`classpath:`前缀:
```gherkin
* request body: classpath:user.json
```

### base uri
为了缩短uri，消除重复，你可以配置一个`base uri`，然后发送请求时使用相对于此的相对uri。

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

你也可以使用绝对路径的写法 `uri: http://host:port`, 推荐你让采用相对uri的方式。

### faker locale
Pandaria使用Java faker生成测试数据，默认生成的是英语，由`faker locale`决定，你可以在`application.properties`中设置默认的值，
在feature文件中可以使用`faker locale`覆盖，覆盖后对当前feature文件生效，如下：

```gherkin
Scenario: faker locale
  * faker locale: zh-CN
  * var: name=faker: #{name.full_name}
  * verify: ${name} matches: '\p{sc=Han}*'
```


测试HTTP(S) APIs
------------------

### 方法

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


#### 自定义Header

```gherkin
Scenario: get with http header
  * uri: /custom_header
  * header: 'Accept'='text.plain'
  * send: GET
  * status: 200
```

#### 检查response中的纯文本
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

### 查询参数
```gherkin
* uri: /users?name=jakim&age=18

* uri: /users
* query parameter: 'name'="${name}"
* query parameter: 'age'='18'


* uri: /users?name=jakim
* query parameter: 'age'='18'
```

**如果你的参数中有需要被url编码的特殊字符, 请使用`query parameter`**

```gherkin
* uri: /users
* query parameter: 'name'='jakim li'
* send: GET
```

### 请求头
```gherkin
* uri: /custom_header
* header: 'Accept'='text.plain'
* header: 'Content-Type'='text/plain'
* send: GET
* status: 200
```
**默认的Content-Type是application/json, 你可以通过设置`Content-Type`请求头来覆盖**

#### 全局的请求头信息
全局的请求头信息可以被应用到所有的http请求中，这有时候很有用，比如测试账户的认证。像下面这样在`application.properties`配置全局的
请求头信息。

application.properties
```
http.headers.Authorization=Bear Token
http.headers.global=globalHeader

http.headers.will_be_overrided=not_overrided
```

所有http请求都会自动带上这写请求头。

`header`关键字将会覆盖全局的请求头信息。

### Cookie
请求中可以添加cookie(s)
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
    * var: val="value"
    * cookie: 'key'="${val}"
    * send: get
    * response body:
    """
    cookie added
    """
```

### 请求体

请求体可以通过文件或者直接在feature文件中写docstring指定。

#### 通过文件
```gherkin
* uri: http://localhost:10080/users
* request body: requests/user.json
* send: POST
* status: 200
* verify: '$.id'='auto-generated'
* verify: '$.username'='jakim'
* verify: '$.age'=18
```

#### 直接写 docstring
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

我们约定`* request body: `后面（不换行）如果直接跟普通字符串的话就代表是文件路径，如果换行后就是docstring，就会直接被当做请求体。

```gherkin
* request body: path_to_file
* request body:
"""
request body from docstring
"""
```

### 上传文件
你可以通过附件上传文件。
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
**如果你指定了附件, 请求体(如果也指定了的话) 会被忽略**

允许上传多个附件。

### 表单
@since 0.3.2

你可以在提交表单的时候上传文件
```gherkin
  Scenario: upload file with form data
    * form: /form
    * field: name value:
    """
    lj
    """
    * field: data value:
    """
    {"name": "lj", "age", 18}
    """
    * field: user value: requests/user.json
    * field: file attachment: attachments/abc.txt
    * send: POST
    * status: 200
    * response body:
    """
    uploaded
    """
```

**`attachment`在表单提交时仍然可用, 字段名称默认为文件名.**

### HTTPS
在大多数测试环境中，都使用了非官方CA签发的证书来保证HTTPS, pandaria默认**不做**证书校验。但是你可以在`application.properties`中通过
如下配置打开校验。
```
http.ssl.verify=true
```
如果你打开了校验, 你**必须**按照标准的SSL系统配置属性指定证书信息

[JSSE](https://docs.oracle.com/javase/8/docs/technotes/guides/security/jsse/JSSERefGuide.html)


### Proxy
Java系统属性用于配置HTTP(S)代理, 例如下面是用gradle配置代理的方式。
```shell
./gradlew -Dhttp.proxyHost=127.0.0.1 -Dhttp.proxyPort=8088 build
```


Graphql测试
-------------
@since 0.3.0

Pandaria支持基于http的graphql测试，当前支持query和mutation。

### URL
与REST不同，Graphql只需要一个endpoint，所以你只需要好base uri：

```
Background:
  * dir: features/graphql
  * base uri: http://localhost:10081/graphql
```

### Query
```gherkin
  Scenario: basic query without specify operation name
    * graphql:
    """
    query bookById($id: String){
      book(id: $id) {
        title
        isbn
        author {
          name
        }
      }
    }
    """

    * variables:
    """
    {
      "id": "1"
    }
    """
    * send
    * verify: '$.data.book.title'='CSS Designer Guide'
    * verify: '$.data.book.isbn'='ISBN01123'
    * verify: '$.data.book.author.name'='someone'
```
你可以发送graphql查询并校验返回的数据。variables是可选的。

或者你也可以把query和variables放在文件中.
```gherkin
* graphql: query_book_by_id.graphql
* variables: css_designer_guide.id.json
* send
* verify: '$.data.book.title'='CSS Designer Guide'
* verify: '$.data.book.isbn'='ISBN01123'
* verify: '$.data.book.author.name'='someone'
```

### Mutation
mutation的有用法与query类似, 只需要把query替换成mutation。

### Variables
Variable is optional.
```gherkin
* variables:
"""
{
  "id": "1"
}

* variables: css_designer_guide.id.json
```

### 操作名称
如果一个请求中包含多个操作，graphql服务端要求制定操作名称。
如果一个请求中只有一个操作，操作名称则是可选的。

```gherkin
  Scenario: query with operation name
    * graphql: query_book_by_id.graphql
    * variables: css_designer_guide.id.json
    * operation: bookById
    * send
    * verify: '$.data.book.title'='CSS Designer Guide'
    * verify: '$.data.book.isbn'='ISBN01123'
    * verify: '$.data.book.author.name'='someone'
```


数据库操作
-------------------
你可以直接编写sql操作数据库，pandaria使用spring jdbc, 首先你需要在`application.properties`中配置数据源信息。

```
spring.datasource.url=jdbc:mysql://localhost:3307/pandaria?useSSL=false&allowMultiQueries=true
spring.datasource.username=root
spring.datasource.password=password
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
```

### 查询
You can write sql with select statements to query database and verify the results using the json path.
你可以编写select语句来查询数据库，并使用json path来验证查询的结果。

**所有的查询结果都以数组的形式返回, 使用json path的时候别忘了返回结果是数组**

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

### 执行SQL
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

### 多数据源
@since 0.3.4

你可以配置更多的数据源，在数据库操作中你可以选择用哪一个数据源。

首先在`application.properties`中添加数据源配置。

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/pandaria?useSSL=false&allowMultiQueries=true&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=password
spring.datasource.driver-class-name=com.mysql.jdbc.Driver

spring.datasource.additional[0].url=jdbc:mysql://localhost:3317/pandaria?useSSL=false&allowMultiQueries=true&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC
spring.datasource.additional[0].name=foo
spring.datasource.additional[0].username=root
spring.datasource.additional[0].password=
spring.datasource.additional[0].driver-class-name=com.mysql.jdbc.Driver

spring.datasource.additional[1].url=jdbc:mysql://localhost:3318/pandaria?useSSL=false&allowMultiQueries=true&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC
spring.datasource.additional[1].name=bar
spring.datasource.additional[1].username=root
spring.datasource.additional[1].password=
spring.datasource.additional[1].driver-class-name=com.mysql.jdbc.Driver
```
上述配置文件中配置了3个数据源，第一个可以通过`default`来访问，其他的可以通过各自的名字来访问。
**`default`作为数据源名字是一个保留的关键字, 如果你尝试把数据源命名为`default`将会报错。**

你可以指定sql在哪个数据源中执行。

```gherkin
* db: foo execute sql: setup_foo.sql
* db: foo query: query_foo.sql
* verify: '$[0].name'='jakim'
* verify: '$[0].age'=18

* db: bar execute sql: setup_bar.sql
* db: bar query: query_bar.sql
* verify: '$[0].name'='jakim'
* verify: '$[0].age'=18
```

未指定数据源的`execute sql` 或者 `query` 将会使用 `default` 作为数据源. 跟如下写法效果一样:
```gherkin
* db: default sql: setup_foo.sql
```


MongoDB操作
------------------
@since 0.2.0

### 插入
@since 0.2.0

在集合中插入一个document

```gherkin
* collection: 'users' insert:
"""
{"user": "jakim"}
"""
```

或者将document放在文件中

```gherkin
* collection: 'users' insert: document/alice.json
```

### 清除
@since 0.2.0

删除集合中所有的documents

```gherkin
* collection: 'users' clear
```

### 查找所有
@since 0.2.0

查找集合中所有的documents，然后校验，同数据库返回结果一样，**返回的永远是JSON数组**。

```gherkin
* collection: 'users' find all
* verify: '$[0].user'="alice"
```

### 查找
@since 0.2.0

除了查找所有documents，你可以过滤你想要的结果。

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


变量
---------

### 初始化
@since 0.2.1

你可以在`application.properties`中初始化变量。

application.properties
```
variables.environment=test
```

```gherkin
Scenario: initial value from configuration file
  * verify: ${environment}="test"
  * var: environment="production"
  * verify: ${environment}="production"
```

### 定义

**兼容性警告**:

如果你使用的pandaria版本 <= 0.2.4, 你需要在变量定义时在变量名处加上单引号. 比如 `var: 'three'=3`


#### 字面量
如果定义变量时使用了单引号，如`'${name}'`, 变量**不会**被替换。

```gherkin
Scenario: const string
  * var: name='panda'
  * verify: ${name}='panda'
```

#### 字符串
如果你是用双引号定义变量，`"${name}"`, 变量会被替换。

```gherkin
Scenario: string
  * var: name='panda'
  * var: great="hello ${name}"
  * verify: ${great}='hello panda'
  * verify: ${great}="hello ${name}"
```

#### 整形
```gherkin
Scenario: integer
  * var: age=18
  * verify: ${age}=18
```

#### 从返回体或结果中抽取

从返回体或结果中抽取变量很常用，你可以使用`<-`来做到。

`var: name<-'json path'`  通过指定的json path从http返回体中抽取值，并赋值给这里指定的变量。

```gherkin
Scenario: from json
  * uri: /not_important
  * send: GET
  * status: 200
  * var: name<-'$.name'
  * var: age<-'$.age'
  * var: iq<-'$.iq'
  * verify: ${name}='panda'
  * verify: ${age}=18
  * verify: ${iq}=double: 80.0
```

你也可以从数据库查询结果中抽取
```gherkin
* query: select.sql
* var: age<-'$[0].age'
```

#### 从返回cookie中抽取
@since 0.2.7

你可以抽取cookie值并赋值变量

```gherkin
Scenario: read response cookie value
  * uri: /mock_login
  * send: POST
  * var: jsession<-cookie:'SessionId'
  * verify: ${jsession}='ABCDEFG'
```

#### 从返回体中抽取纯文本
@since 0.2.8

```gherkin
* uri: /simple_response
* send: GET
* status: 200
* var: content<-response body
* verify: ${content}='SIMPLE_RESPONSE'
```

#### 从代码块中抽取
@since 0.2.1

你可以通过编写Javascript代码，并把执行结果赋值给变量。
```gherkin
* var: three=3

* var: six=code:
"""
${three} + 3
"""

* verify: ${six}=6

* var: zero=code: ${three} - 3
* verify: ${zero}=0

* var: ten=code file: six_add_four.js
* verify: ${ten}=10
```

### 使用变量
#### 在URI中
```gherkin
Scenario: variable being replaced in uri
  * var: path="not_important"
  * uri: /${path}
  * send: GET
  * status: 200
  * verify: '$.name'='panda'
  * verify: '$.age'=18
  * verify: '$.iq'=double: 80.0
```

#### 在文件中

requsts/someone.json
```json
{
  "name": "${name}"
}
```

```
Scenario: variable used in request file
  * var: name='someone'
  * uri: /users
  * request body: requests/someone.json
  * send: POST
  * status: 201
```

#### 在文本中
```gherkin
* response body:
"""
{"user":"${username}"}
"""
```

#### 在请求头信息中
@since 0.3.1

```gherkin
* uri: /custom_header_value_from_variable
* var: value="some_value"
* header: 'SomeName'="${value}"
* send: GET
* status: 200
```

#### 在代码块中
@since 0.2.1

```gherkin
* var: three=3
* var: six=code:
"""
${three} + 3
"""
```

### 转义
你可以通过多家一个`$`来为变量转义
```gherkin
* var: six=code:
"""
$${three} + 3
"""
```
上述写法会报错因为javascript引擎找不变量`${three}`

### Faker特殊变量
@since 0.2.2

随机的类真实的测试数据在测试时非常有用，Pandaria集成了[java-faker](https://github.com/DiUS/java-faker)来做测试数据的生成。

#### 将其定义为变量
@since 0.2.2

通过表达式`#{expression}` 你可以生成假数据，然后赋值给变量

```gherkin
* var: name=faker: #{Name.firstName}
* verify code: "${name}".length > 0

* var: full_name=faker: #{Name.fullName}
* verify code: "${full_name}".length > 0
```

#### 立即使用
@since 0.2.2

你可以在请求体，sql或者mongo的json中直接使用Faker变量, 文件中也可以。
```gherkin
* uri: /faker/users
* request body:
"""
{"name": "#{Name.fullName}", "city": "#{Address.city}"}
"""
* send: POST
* response body:
"""
success
"""
```

#### 语言
@since 0.2.2

你可以切换语言，默认是`en`英语。
```gherkin
* faker locale: zh-CN
* var: name=faker: #{Name.fullName}
* verify: ${name} matches: '\p{sc=Han}*'
```

`* verify: ${name} matches: '\p{sc=Han}*'` 验证 `${name}` 的值全都是中文字符。

#### 修改默认语言
@since 0.2.2

通过在`application.properties`中设置`faker.locale`修改默认语言。
```
faker.locale=zh-CN
```

#### 转义
@since 0.2.2

加上额外的井号转义
```gherkin
* uri: /faker/users/escape
* request body:
"""
{"name": "#{Name.fullName}", "city": "##{Address.city}"}
"""
* send: POST
* response body:
"""
success
"""
```
上述例子中，name会被设置成一个随机的名字，但是city会被设置成`#{Address.ctiy}`。

**使用faker数据赋值变量时不允许这样转义, `var: name=faker: ##{Name.fullName}`**
**请使用 `var: name='#{Name.fullName}'` 代替**


### 上次结果特殊变量
@since 0.2.3

通常我们需要将上一次请求的部分或者全部响应体作为下一次请求的请求体。你可以在下一次请求体中使用`@{<json path>}`，Pandaria会自动替换，文件中也适用。

使用上一次整个响应体做下一次请求体

```gherkin
* uri: /users/me
* send: get
* verify: '$.username'='jakim'
* verify: '$.age'=18
* verify: '$.iq'=double: 80.0

* uri: /users
* request body:
"""
@{$}
"""
* send: POST
* status: 200
* verify: '$.id'='auto-generated'
* verify: '$.username'='jakim'
* verify: '$.age'=18
```

使用上一次部分响应体做下一次请求体

```gherkin
* uri: /users/me
* send: get
* verify: '$.username'='jakim'
* verify: '$.age'=18
* verify: '$.iq'=double: 80.0

* uri: /users
* request body:
"""
{ "username": @{$.username}}
"""
* send: POST
* status: 200
* verify: '$.id'='auto-generated'
* verify: '$.username'='jakim'
* verify: '$.age'=18
```

**请注意双引号，因为Pandaria假设json path表达式也是JSON，所以会自动加上双引号，但是对于`${}`中的变量，或者faker表达式`#{}`, 你需要自己**
**在需要的地方加上双引号**


验证
-----------

### 验证http响应

#### 响应状态
```gherkin
* uri: /empty_request
* send: POST
* status: 201
```

#### 响应头
```gherkin
* uri: /users
* send: TRACE
* status: 200
* response header: 'Content-Type'='message/http'
```

#### 响应体
使用JSON PATH验证
```gherkin
* verify: '$.username'='jakim'
* verify: '$.age'=18
```

使用纯文本验证
```gherkin
* response body:
"""
success
"""
```

验证文件中的文本
```gherkin
* response body: responses/success.txt
```

与请求体的写法一样，约定`* response body:`后面的字符串为文件路径，换行的docstring中直接写期望的响应体。

### 验证数据库表
你可以编写select sql语句来查询数据库表，并验证结果。

有如下表：

| name | age |
|------|-----|
| jakim | 18 |
| panda | 28 |

其JSON数组表示:

```json
[
    { "name": "jakim", "age": 18 },
    { "name": "panda", "age": 28 }
]
```
**查询结果永远都是数组形式，即使只有一条数据**

然后使用与验证http响应同样的方式验证

```gherkin
* query: select.sql
* verify: '$[0].name'='jakim'
* verify: '$[0].age'=18
```


### 验证字符串
尽管你可以使用字符串验证非字符串类型，但是验证前会将其转换为字符串格式。

#### 相等
`=` 用于相等, `!=` 用于不等

```gherkin
Scenario: equals
  * uri: /users/me
  * send: GET
  * status: 200
  * verify: '$.username'='jakim'

  * var: kim="kim"
  * var: user='jakim'
  * verify: ${user}="ja${kim}"

  * verify: '$.username'="jakim"
  * verify: '$.username'="ja${kim}"

  * var: age=18
  * var: iq=80.0
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

#### 包含

```gherkin
Scenario: contains
  * uri: /users/me
  * send: GET
  * status: 200
  * verify: '$.username'='jakim'
  * verify: '$.username' contains: 'kim'

  * var: username="panda"
  * verify: ${username} contains: 'anda'
```

#### 开始

```gherkin
* verify: '$.username'='jakim'
* verify: '$.username' starts with: 'jak'

* var: username="jakim"
* verify: ${username} starts with: 'jak'

* var: prefix='jak'
* verify: '$.username' starts with: "${prefix}i"
* verify: ${username} starts with: "${prefix}i"
```

#### 结束
```gherkin
* verify: '$.username'='jakim'
* verify: '$.username' ends with: 'kim'

* var: username="jakim"
* verify: ${username} ends with: 'kim'

* var: suffix='kim'
* verify: '$.username' ends with: "ja${suffix}"
* verify: ${username} ends with: "ja${suffix}"
```

#### 长度
```gherkin
* verify: '$.username'='jakim'
* verify: '$.username' length: 5

* var: username="jakim"
* verify: ${username} length: 5

* var: abc=3
* verify: ${abc} length: 1
```

#### 正则表达式
```gherkin
* verify: '$.username'='jakim'
* verify: '$.username' matches: '.*'

* var: username="jakim"
* verify: ${username} matches: 'j.*im'
```

### 验证数字

#### 大于

```gherkin
* verify: '$.age'>17
* verify: '$.iq'>double: 70.0
* verify: '$.age'>=18
* verify: '$.iq'>=double: 80.0

* var: age=18
* var: iq=80.0

* verify: ${age}>17
* verify: ${iq}>double: 79.0
* verify: ${age}>=17
* verify: ${iq}>=double: 80.0
```

### 小于

```gherkin
* verify: '$.age'<19
* verify: '$.iq'<double: 90.0
* verify: '$.age'<=18
* verify: '$.iq'<=double: 80.0

* var: age=18
* var: iq=80.0

* verify: ${age}<19
* verify: ${iq}<double: 99.0
* verify: ${age}<=18
* verify: ${iq}<=double: 80.0
```

### 验证日期和时间

#### 日期或时间相等
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

#### 之前
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

#### 之后
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

### 验证JSON

#### 同一个json
* 数组允许元素不同顺序出现
* **不**允许多余的元素在数组中 
* **不**允许多余或者缺少对象

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

#### 包含json
* 数组允许元素不同顺序出现
* 允许多余的元素在数组中 
* 允许多余的对象
* **不**允许缺少字段

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

  * var: response<-'$'
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

#### 验证大小
如果json是数组大小，你可以验证其大小，如果json是对象，则验证的是其key的数量。

```gherkin
Scenario: has size for array
  * uri: /users/list
  * send: get
  * verify: '$' same json:
  """
  [
    {
      "name": "jakim",
      "friends": [
        "james", "jack"
      ]
    },
    {
      "name": "smart",
      "friends": ["sue", "lucy"]
    },
    {
      "name": "haha"
    }
  ]
  """

  * verify: '$' has size: 3
  * verify: '$.size()'=3
```

### 验证JSON Schema
[JSON schema](https://json-schema.org/) 用于描述API, 常用与契约测试. 你可以验证一个JSON文档是否满足某个JSON Schema。

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


### 验证null
```gherkin
Scenario: null check
  * uri: /users/me
  * send: GET
  * status: 200
  * verify: '$.username'='jakim'
  * verify: '$.username' is not null

  * var: username="jakim"
  * verify: ${username} is not null
  * verify: ${hello} is null

  * uri: /getnull
  * send: GET
  * status: 200
  * verify: '$.notexist' is null
```

### 验证变量
你可以验证http响应体，数据库查询结果，或者变量是否等于一个变量。

```gherkin
* verify: '$.username'=${user}
* verify: '$.username'!=${kim}

* verify: ${user}=${user}
* verify: ${user}!=${kim}
```

#### 变量嵌套引用
@since 0.3.2

```gherkin
* verify: '$[0]'=${first_user}
* verify: '$[0].name'=${first_user.name}
* verify: '$[0].friends'=${first_user.friends}
```
**当前嵌套变量引用不支持在javascript代码块，文件和docstring中使用，日后支持。**


### 验证代码
@since 0.2.1

你可以编写javascript代码来进行验证，有两种方式，验证代码执行结果，或者验证代码结果是否为true。

你可以将代码块写在一行，doc string或者单独的文件中。

**注意事项**
* 如果代码中有多行代码，只有最后一样的执行结果会被返回使用。
* 如果你需要在代码块中写很复杂的代码，首先试试自定义cucumber的step来实现。
* [Nashorn](https://docs.oracle.com/javase/8/docs/technotes/guides/scripting/nashorn/api.html)用于脚本解析和执行.
* 如果你的jdk版本低于8u40, 你可能会遇到0被转换成0.0, 你可以升级jdb版本或者考虑其为double类型。

#### 验证响应体和结果等于代码执行结果
@since 0.2.1

```gherkin
* var: age=16
* var: iq=90.0

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

#### 验证代码是否返回true
@since 0.2.1

**注意javascript中使用双等号`==`做相等比较，pandaria中javascript外使用单等号`=`**

```gherkin
* verify code: ${name} == ${iq} / 3
* verify code:
"""
${name} != ${iq} % 3
"""
* verify code file: verification.js
```

### 自定义验证
Pandaria不可能支持所有验证方式，你可以编写你自己的校验方式。
教程 [tutorial on how](custom_verification_tutorial.md)


等待
----
自动测试中经常需要等待一段时间。

### 简单等待
目前只支持毫秒和秒，我们也不推荐等待过长时间。

```gherkin
Scenario: wait
  * wait: 1000ms
  * wait: 1s
```

### 等待直至次数
等待是一个耗时的操作，很可能让测试变慢，但又是必要的。

`wait 1000ms times 3`表示等待3次，每次等待1000ms。

运行完这步后，线程并**不会**立即休眠。如果接下来的第一个验证类型的步骤失败，则会将线程休眠`1000ms`, 然后重试，这个过程会被重复`3`遍。 

#### 等待直到API返回期望的结果

`GET /sequence` 依次返回字符串

```java
server.server()
        .get(by(uri("/sequence")))
        .response(seq("1", "2", "3", "4", "5", "6", "7", "8", "9", "10"));
```

等待直到:
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

#### 等待直到数据库返回期望结果

```gherkin
* wait: 1000ms times: 3
* query:
"""
SELECT NAME, AGE FROM USERS;
"""
* verify: '$[0].name'="jakim"
* verify: '$[0].age'=18
```

**尽管在wait和第一个验证之间可以有多个动作（http请求，数据库查询等）, 之间的所有动作都会被重试，但是只有最后一个动作的结果能够被验证**
**比如:**
```gherkin
* wait: 1000ms times: 3
* uri: /sequence
* send: GET
# 次http请求不会被校验结果, 但会被重试

* query:
"""
SELECT NAME, AGE FROM USERS;
"""
* verify: '$[0].name'="jakim"
* verify: '$[0].age'=18
```
**http请求和数据库查询都会被重试，但是只有数据库查询会被验证**

工具
---------

你可以通过工具来赋值变量，通过变量的方式使用:

### 随机UUID
```gherkin
Scenario: generate random number
  * var: uuid=random uuid
  * verify: ${uuid} length: 36
```

示例uuid: `123e4567-e89b-12d3-a456-556642440000`

使用[faker expression](#special-variable-with-faker)你可以生成几乎所有随机的测试数据

### 打印
@since 0.3.5

你可以打印一些信息用于调试
```gherkin
* print: "begin"
* print: "@{$}"
* print: "@{$.username}"
* print: "<string>"
* print: "${var_id}"
* print: '${var_id}'
* print: "end"
```

数据驱动
-----------
你可以使用cucumber的scenario outline来实现数据驱动的场景

```gherkin

@outline
Feature: data driven
  data driven should work with scenario outline

  Background:
    * dir: features/outline
    * base uri: http://localhost:10080

  Scenario Outline:
    * uri: /users
    * request body:
    """
    { "username": "<username>" }
    """
    * send: POST
    * status: 200
    * verify: '$.username'='<username>'

    Examples:
      | username |
      | jakim    |
      | alice    |
      | bob      |
      | steve    |
```

使用变量, 你可以在外部文件中引用`Examples`中的值。

```gherkin

  Scenario Outline:
    * var: username='<username>'

    * uri: /users
    * request body: requests/user.json
    * send: POST
    * status: 200
    * verify: '$.username'='<username>'

    Examples:
      | username |
      | jakim    |
      | alice    |
      | bob      |
      | steve    |
```

requests/user.json
```json
{
  "username": "${username}"
}
```
