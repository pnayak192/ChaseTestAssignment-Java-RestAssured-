@Negative
Feature: Perform Negative Test cases on Posts API

  Scenario Outline: Check if are allowed Update existing data

    Given We have posts api and expect response status 201
    When  We use <jsonFile> to send a POST request to api
    Then  We check the response code returned by the response
    Then  We send a GET request to api
    And   We check the response does not contain the data passed in the payload
    Examples:
      | jsonFile          |
      | postPayload1.json |
      | postPayload2.json |

  Scenario Outline: Check if are allowed Delete existing data

    Given We have <pathParam> api and expect response status 200
    When  We send a DELETE request to api
    Then  We check the response code returned by the response
    Then  We send a GET request to api
    And   We check the response data has not been Deleted
    Examples:
      | pathParam  |
      | posts/10   |
      | posts/20   |
      | posts/30   |
      | posts/40   |
      | posts/50   |
      | posts/60   |
