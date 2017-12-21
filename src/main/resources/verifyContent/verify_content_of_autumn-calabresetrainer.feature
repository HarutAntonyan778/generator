@r2 @trainers(v2) @positive @smoke @autumn-calabrese @validate_static_data
Feature: GET "v2/trainers" API test cases based on user scenarios
  Description: All the test cases :
  - Make request to "v2/trainers" API with trainer slug to get trainer.
  - validate response JSON static data


  @verify_content
  Scenario Outline: Verify GET Trainers API with trainer slug(autumn-calabrese)
    Given I send HTTP "GET" request to "v2/trainers/autumn-calabrese" API with headers
      | Accept | application/json |

    Then Read "resources/trainersContent/autumn-calabrese.properties" properties file
    And Verify content of "<trainers>.<key>"
    Examples:
      | trainers       | key                  |
      | $.items[0]     | displayName          |
      | $.items[0]     | slug                 |
      | $.items[0]     | description.raw      |
      | $.items[0]     | description.rendered |
      | $.items[0]     | purchaseRegion       |
      | $.items[0]     | firstName            |
      | $.items[0]     | lastName             |
      | $.items[0].url | twitterUrl           |
      | $.items[0].url | facebookUrl          |
      | $.items[0].url | instagramUrl         |
      | $.items[0].url | profilePageUrl       |



