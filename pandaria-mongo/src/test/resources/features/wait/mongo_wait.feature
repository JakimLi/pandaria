Feature: wait for mongo
  wait for mongo has result

  Background:
    * dir: features/wait

  Scenario: wait works for mongo
    * collection: 'users' clear
    * collection: 'users' insert:
    """
    {"user": "jakim"}
    """
    
    * wait: 1000ms times: 3
    * collection: 'users' find all
    * verify: '$[0].user'="jakim"

