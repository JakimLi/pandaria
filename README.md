Pandaria
=====
Light weight API test framework based on cucumber

Introduction
------------

Pandaria is a DSL written based on cucumber to simplify the HTTP API testing. Using pandaria you don't need to learn programming

Example
-------

You can call your api and verify the response
```
* uri: http://localhost:10080/users/me
* send: GET
* status: 200
* verify: '$.username'='jakim'
* verify: '$.age'=18
```

And you can query database and verify the results

```
* query:
"""
SELECT NAME, AGE FROM USERS
"""
* verify: '$[0].name'='jakim'
* verify: '$[0].age'=18
```

Or like this:

```
* query: select.sql
* verify: '$[0].name'='jakim'
* verify: '$[0].age'=18
```

More [Usage](doc/usage.md)
