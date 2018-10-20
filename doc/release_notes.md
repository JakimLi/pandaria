Release 0.2.0 (2018-10-21) Latest
=================================
Mongo DB
--------
* Insert
* Clear
* Find all
* Find

Issue fixed
----
* Execute sql not repeatable
* Variable cannot verify josn schema


Release 0.1.2 (2018-10-15)
=================================
HTTP(S)
-------
* Global request headers

Wait
----
* wait and retry multiple actions

Verification
------------
* verify json schema

Release 0.1.1 (2018-10-8)
================================
HTTP(S)
-------
* Custom content-type
* Cookie
* Ignore SSL host verification
* SSL configuration
* Upload file

Verification
------------
* Verify null
* Verify variable
* Verify JSON
    * same json
    * contains json

Utitlies
--------
* Random UUID


Release 0.1.0 (2018-10-5)
========

HTTP
----
* Methods
    * GET
    * POST
    * PUT
    * DELETE
    * PATCH
    * HEAD
    * OPTIONS
    * TRACE
* request header
* request body
* response header
* response status
* response body

Database Operations
-------------------
* Queries
* Execute SQL

Variables
---------
* Defintion
    * Literal string
    * String
    * Integer
    * Extract from response body
* Use Variables
    * In URI
    * In File
    * In Text

Verfiation
----------
* Verify http response
    * response status
    * response header
    * response body
* Verify database tables
    * String
    * BigDecimal
    * Boolean
    * Integer
    * Long
    * Float
    * Double
    * Datetime
* Verify String
    * Equals
    * Contains
    * Starts With
    * Ends With
    * Length
    * Regex Match
* Verify numbers
    * Greater than
    * Less than
    * Other types
* Verify datetime
    * Equals
    * Before
    * After
* Write your own


Wait
----
* Simple Wait
* Wait Until
