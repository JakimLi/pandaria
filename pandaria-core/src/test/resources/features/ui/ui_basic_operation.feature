Feature: ui operations
  basic ui operations, like navigation, clicking

  Background:
    * base uri: http://localhost:12306/local

  Scenario: navigate and refresh
    * open: /index.html
    * verify: #current text: 'index.html'

    * click: #users
    * verify: #current text: 'users.html'

    * go back
    * verify: #current text: 'index.html'

    * go forward
    * verify: #current text: 'users.html'

    * refresh
    * verify: #current text: 'users.html'

    * close

  Scenario: fill in text input
    * open: pages/register.html
    * verify: input[name='email'] attribute: 'value' equals: ''

    * input: input[name='email'] text: 'lj@gmail.com'
    * verify: input[name='email'] attribute: 'value' equals: 'lj@gmail.com'

    * close

  Scenario: submit form
    * open: pages/register.html
    * input: input[name='email'] text: 'lj@gmail.com'
    * input: input[name='psw'] text: 'password'
    * input: input[name='psw-repeat'] text: 'password'
    * submit: form

    * verify uri: 'http://localhost:12306/local/pages/action_page.php?email=lj%40gmail.com&psw=password&psw-repeat=password'

    * go back
    * input: input[name='email'] clear
    * input: input[name='psw'] clear
    * input: input[name='psw-repeat'] clear

    * input: input[name='email'] text: 'lj@gmail.com'
    * input: input[name='psw'] text: 'password'
    * input: input[name='psw-repeat'] text: 'password'

    * submit: input[name='email']

    * verify uri: 'http://localhost:12306/local/pages/action_page.php?email=lj%40gmail.com&psw=password&psw-repeat=password'


    * go back
    * input: input[name='email'] clear
    * input: input[name='psw'] clear
    * input: input[name='psw-repeat'] clear

    * input: input[name='email'] text: 'lj@gmail.com'
    * input: input[name='psw'] text: 'password'
    * input: input[name='psw-repeat'] text: 'password'

    * click: .registerbtn
    * verify uri: 'http://localhost:12306/local/pages/action_page.php?email=lj%40gmail.com&psw=password&psw-repeat=password'

  Scenario: select dropdown
    * open: pages/select.html
    * verify: #vehicles selected value: 'audi'

    * select: #vehicles  value: 'volvo'
    * verify: #vehicles selected value: 'volvo'

    * verify: #vehicles contains items:
      | value | innerText |
      | volvo | Volvo     |
      | saab  | Saab      |
      | vw    | VW        |

    * verify: #vehicles has items:
      | value | innerText |
      | volvo | Volvo     |
      | saab  | Saab      |
      | vw    | VW        |
      | audi  | Audi      |
