Feature: html select
  operations about html select and options
  
  Background:
    * base uri: http://localhost:12306/local

  Scenario: select dropdown
    * open: pages/select.html
    * verify: '#vehicles' selected value: 'audi'

    * select: '#vehicles' value: 'volvo'
    * verify: '#vehicles' selected value: 'volvo'

    * select: '#vehicles' index: 3
    * verify: '#vehicles' selected value: 'audi'

    * verify: '#vehicles' contains items:
      | value | innerText |
      | volvo | Volvo     |
      | saab  | Saab      |
      | vw    | VW        |

    * verify: '#vehicles' has items:
      | value | innerText |
      | volvo | Volvo     |
      | saab  | Saab      |
      | vw    | VW        |
      | audi  | Audi      |
