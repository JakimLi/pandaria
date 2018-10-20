Feature: mongo related features
  including insert, and find

  Background:
    * dir: features/mongo

  Scenario: insert a simple document as doc string
    * collection: 'users' clear
    * collection: 'users' insert:
    """
    {"user": "jakim"}
    """
    * collection: 'users' find all
    * verify: '$[0].user'="jakim"

  Scenario: insert a simple document from file
    * collection: 'users' clear

    * collection: 'users' insert: document/alice.json
    * collection: 'users' find all
    * verify: '$[0].user'="alice"

  Scenario: find by a filter
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
