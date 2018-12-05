<a href="https://github.com/jakimli/pandaria">
  <img src="pandaria.png?raw=true" width="100px">
</a>

Pandaria
========

简介
---
Pandaria是基于Cucumber JVM, 尝试简化HTTP API自动化测试的DSL。Pandaria与Cucumber完全兼容。使用Pandaria不要求编码能力。


例子
---

你可以调用API并验证返回结果:

```gherkin
* uri: http://localhost:10080/users/me
* send: GET
* status: 200
* verify: '$.username'='jakim'
* verify: '$.age'=18
```

你也可以查询数据库，并验证查询结果:

```gherkin
* query:
"""
SELECT NAME, AGE FROM USERS
"""
* verify: '$[0].name'='jakim'
* verify: '$[0].age'=18
```

或者将查询条件放到文件中:

```gherkin
* query: select.sql
* verify: '$[0].name'='jakim'
* verify: '$[0].age'=18
```
[支持Mongo DB](doc/usage.md#mongodb-operations)

等待并重试:

```gherkin
* wait: 1000ms times: 3
* uri: /sequence
* send: GET
* response body:
"""
3
"""
```
上述代码发送GET请求到`/sequence`并期望返回结果报文等于文件3, 如果失败，则等待1000毫秒并重试，直到成功通过，或者超过最大重试次数3, 然后失败。

```gherkin
* wait: 1000ms times: 3
* query: select.sql
* verify: '$[0].name'='jakim'
* verify: '$[0].age'=18
```

验证返回结果是否遵循Json schema:
```gherkin
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
```


