Pandaria
=====
Light weight API test framework based on cucumber

Introduction
------------

Pandaria is a DSL written based on cucumber to simplify the HTTP API testing. Using pandaria you don't need to learn programming

Example
-------

```
* uri: http://localhost:10080/users/me
* send: GET
* status: 200
* verify: '$.username'='jakim'
* verify: '$.age'=18
```

More [Usage](doc/usage.md)
