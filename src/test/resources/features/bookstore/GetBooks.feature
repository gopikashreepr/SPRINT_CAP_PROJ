Feature: BookStore - Get All Books
  As an API consumer
  I want to retrieve the full catalog of books
  So that I can browse what is available

  Background:
    Given the BookStore API base URL is configured

  @BookStore @Read @Positive @Smoke
  Scenario: Get all books from the catalog
    When I request all books from the catalog
    Then the response status code should be 200
    And the response should match the "books-response-schema.json" schema
