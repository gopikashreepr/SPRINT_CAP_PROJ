Feature: BookStore - Delete a Book
  As an authenticated user
  I want to remove a single book from my collection
  So that my collection stays accurate

  Background:
    Given the BookStore API base URL is configured
    And a user already exists with a unique username and a strong password
    And I have generated a valid token for that user
    And ISBN "isbn1" has been added to the user's collection

  @BookStore @Delete @Positive @Smoke @Chaining
  Scenario: Delete a book that exists in the user's collection
    When I delete ISBN "isbn1" from the user's collection using the saved token
    Then the response status code should be 204

  @BookStore @Delete @Negative
  Scenario: Delete a book that is not in the user's collection
    When I delete ISBN "invalidIsbn" from the user's collection using the saved token
    Then the response status code should be 400
