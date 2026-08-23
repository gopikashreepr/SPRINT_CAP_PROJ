Feature: BookStore - Add Books to User Collection
  As an authenticated user
  I want to add books to my personal collection
  So that I can track which books I own

  Background:
    Given the BookStore API base URL is configured
    And a user already exists with a unique username and a strong password
    And I have generated a valid token for that user

  @BookStore @Create @Positive @Smoke @Chaining
  Scenario: Add a valid ISBN to the user's collection
    When I add ISBN "isbn1" to the user's collection using the saved token
    Then the response status code should be 201

  @BookStore @Create @Negative
  Scenario: Add an invalid ISBN to the user's collection
    When I add ISBN "invalidIsbn" to the user's collection using the saved token
    Then the response status code should be 400
    And the response should match the "error-schema.json" schema

  @BookStore @Create @Edge
  Scenario: Add an empty list of ISBNs to the user's collection
    When I add an empty list of ISBNs to the user's collection using the saved token
    Then the response status code should be 400
