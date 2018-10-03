Write your own verificaiton
===========================

It's impossible for pandaria to provide all the verificaitons, you can always write your own verifications.

Pandaria keywords just cucumber steps, so extends pandaria is just write your own cucumber steps.

But in order to get the data that you want to verify against, we need to understand how pandaria stores them.

Verification Context
--------------------

Currently there are two types of data that needs to be verified, first the http response, second the result that queried
from database. Pandaria puts these data into `VerificationContext`. So just get the bean of `VerificationContext`.

Example
-------

Let's say we want to add to test if the something in the response is not null. and we want to it to be used in feature
file like this:

```
* verify '$.username' not null
```

All you need to do is to create a java class for cucumber step definition, let's call it NotNullSteps

```
package com.github.extension;

import com.github.jakimli.pandaria.domain.VerificationContext;
import cucumber.api.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;

public class NotNullSteps {

    @Autowired
    VerificationContext actual;

    @Then("^verify: '([^\"]*)' not null$")
    public void verifyNotNull(String path) throws IOException {
        assertNotNull(actual.json(path)); //actual.json(path) to retrive the data by json path
    }
}
```

And then you can use it in feature file
```
Feature: verify numbers
  verify numbers

  Scenario: integer equals
    * uri: http://localhost:10080/users/me
    * send: GET
    * status: 200
    * verify: '$.username' not null
```

