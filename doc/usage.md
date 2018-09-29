Usage
=====

Panda just abstract DSL for API testing, all usages just cucumber gherkin steps

This page demonstrates the usage of the DSLs


BASIC HTTP REQUEST
------------------

```gherkin
* url: http://localhost:3000/user/123
* send: GET
* verify: '$.name'="jakim"
```
