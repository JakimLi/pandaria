Feature: simple mongo insert
  insert document into mongo collection

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
