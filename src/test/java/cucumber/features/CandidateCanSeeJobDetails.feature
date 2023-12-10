Feature: Candidate can see the job's details.

    @Candidate
    Scenario Outline: Candidate opens a job's page and see its details.
      Given I see "giris" page
      Then I wait "is ariyorum" element
      Given I click "is ariyorum" element
      Then I wait for 10 seconds
     # Then I test image "Untitled"
      Then I wait "only this time" element
      Then I click "only this time" element

      Then  I sleep 10 seconds
      Examples: First and Second jobs
        | index |
        | 0     |
    @Candidate4
    Scenario Outline: Candidate opens a job's page and see its details2.
      Given I see "giris" page
      Then I wait "is ariyorum" element
      Given I click "is ariyorum" element
      Then I wait "only this time" element
      Then I click "only this time" element
      Then  I sleep 10 seconds
      Examples: First and Second jobs
        | index |
        | 0     |

  @Candidate2
  Scenario: Candidate opens a job's page and see its details2.
    Given I see "giris" page
    Then I wait "is ariyorum" element
    Given I click "is ariyorum" element
    Then I wait "only this time" element
    Then I click "only this time" element
    Then  I sleep 10 seconds



  @Candidate3
  Scenario: Candidate opens a job's page and see its details2.
    Given I see "giris" page
    Then I wait "is ariyorum" element
    Given I click "is ariyorum" element
    Then I wait "only this time" element
    Then I click "only this time" element
    Then  I sleep 10 seconds
    @Candidate7
    Scenario Outline: Candidate opens a job's page and see its details2.
      Given I see "giris" page
      Then I wait "is ariyorum" element
      Given I click "is ariyorum" element
      Then I wait "only this time" element
      Then I click "only this time" element
      Then  I sleep 10 seconds
      Examples: First and Second jobs
        | index |
        | 0     |

    @Candidate8
    Scenario Outline: Candidate opens a job's page and see its details2.
      Given I see "giris" page
      Then I wait "is ariyorum" element
      Given I click "is ariyorum" element
      Then I wait "only this time" element
      Then I click "only this time" element
      Then  I sleep 10 seconds
      Examples: First and Second jobs
        | index |
        | 0     |

    @Candidate5
    Scenario Outline: Candidate opens a job's page and see its details2.
      Given I see "giris" page
      Then I wait "is ariyorum" element
      Given I click "is ariyorum" element
      Then I wait "only this time" element
      Then I click "only this time" element
      Then  I sleep 10 seconds
      Examples: First and Second jobs
        | index |
        | 0     |