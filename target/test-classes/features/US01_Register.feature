@Register
Feature: Registration Feature

  Scenario Outline:Register
    Given go to the "https://managementonschools.com"
    Given click on register
    When Enter name "<name>", surname "<surname>",birth_place "<birth_place>", phone_number "<phone_number>",gender "<gender>", birth_day "<birth_day>",ssn "<ssn>",username "<username>",password"<password>"
    Then click on register button
    Then close browser

    Examples:
      | username  | birth_day  | birth_place | gender | name | phone_number | ssn         | surname | password |
      | johndoe17 | 01-01-2000 | New York    | Male   | John | 377-654-3987 | 179-35-7836 | Doe     | John.123 |