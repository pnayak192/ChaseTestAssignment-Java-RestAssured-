@Positive
Feature: Feature: Perform Positive Test cases on Posts API response

  Scenario: Check if the API returns validatable response

    Given We have posts api and expect response status 200
    When  We send a GET request to api
    Then  We check the response code returned by the response
    And   We check the response is not empty

  Scenario: Check the number of posts returned by the API

    Given We have posts api and expect response status 200
    When  We send a GET request to api
    Then  We check the response code returned by the response
    And   We check the number of posts returned in the response are 100

  Scenario: Check the number of distinct users returned by the API

    Given We have posts api and expect response status 200
    When  We send a GET request to api
    Then  We check the response code returned by the response
    And   We check the number of distinct users returned in the response are 10

  Scenario Outline: Check the every user has 10 posts returned by the API

    Given We have posts api and expect response status 200
    When  We send a GET request to api for '<userId>' using only userId query parameter
    Then  We check the response code returned by the response
    And   We check the number of posts returned in the response are 10
    Examples:
      | userId  |
      |   1     |
      |   2     |
      |   3     |
      |   4     |
      |   5     |
      |   6     |
      |   7     |
      |   8     |
      |   9     |
      |   10    |



