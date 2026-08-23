Feature: Account - Create User
  As an API consumer
  I want to create a new BookStore user
  So that I can authenticate and manage a book collection

  Background:
    Given the BookStore API base URL is configured

  @Account @Create @Positive @Smoke
  Scenario: Create a user with valid unique credentials
    Given I generate a unique username and a strong password
    When I send a request to create the user
    Then the response status code should be 201
    And the response should match the "user-schema.json" schema
    And the created userId should be saved for later steps

  @Account @Create @Negative
  Scenario: Create a user with a weak password
    Given I generate a unique username
    And I set the password to "123"
    When I send a request to create the user
    Then the response status code should be 400
    And the response should match the "error-schema.json" schema

  @Account @Create @Boundary
  Scenario: Create a user with an empty username
    Given I set the username to ""
    And I generate a strong password
    When I send a request to create the user
    Then the response status code should be 400
