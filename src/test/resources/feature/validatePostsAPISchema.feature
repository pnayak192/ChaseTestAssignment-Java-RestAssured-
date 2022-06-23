@SchemaValidation
Feature: Perform schema validation on Posts API response

Scenario Outline: Check the number of posts returned by the API

Given We have posts api and expect response status 200
When  We send a GET request to api
Then  We check the response code returned by the response
And   We validate the schema of the response from <schema File>
  Examples:
    |        schema File       |
    | postsResponseSchema.json |
