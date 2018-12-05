Feature: html form
  operations about form

  Background:
    * base uri: http://localhost:12306/local

  Scenario: submit form
    * open: pages/register.html
    * input: 'input[name='email']' text: 'lj@gmail.com'
    * input: 'input[name='psw']' text: 'password'
    * input: 'input[name='psw-repeat']' text: 'password'
    * submit: 'form'

    * verify uri: 'http://localhost:12306/local/pages/action_page.php?email=lj%40gmail.com&psw=password&psw-repeat=password'

    * go back
    * input: 'input[name='email']' clear
    * input: 'input[name='psw']' clear
    * input: 'input[name='psw-repeat']' clear

    * input: 'input[name='email']' text: 'lj@gmail.com'
    * input: 'input[name='psw']' text: 'password'
    * input: 'input[name='psw-repeat']' text: 'password'

    * submit: 'input[name='email']'

    * verify uri: 'http://localhost:12306/local/pages/action_page.php?email=lj%40gmail.com&psw=password&psw-repeat=password'


    * go back
    * input: 'input[name='email']' clear
    * input: 'input[name='psw']' clear
    * input: 'input[name='psw-repeat']' clear

    * input: 'input[name='email']' text: 'lj@gmail.com'
    * input: 'input[name='psw']' text: 'password'
    * input: 'input[name='psw-repeat']' text: 'password'

    * click: '.registerbtn'
    * verify uri: 'http://localhost:12306/local/pages/action_page.php?email=lj%40gmail.com&psw=password&psw-repeat=password'

  Scenario: input form with data table
    * open: pages/register.html

    * form: '#register'
      | input[name="email"]      | lj@gmail.com |
      | input[name="psw"]        | password     |
      | input[name="psw-repeat"] | password     |
    * submit: 'form'

    * verify uri: 'http://localhost:12306/local/pages/action_page.php?email=lj%40gmail.com&psw=password&psw-repeat=password'
