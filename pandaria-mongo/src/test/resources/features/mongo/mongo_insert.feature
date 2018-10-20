Feature: simple mongo insert
  insert document into mongo collection

  Background:
    * dir: features/mongo

  Scenario: insert a simple document

    * collection: 'users' insert:
    """
    {"user": "jakim"}
    """