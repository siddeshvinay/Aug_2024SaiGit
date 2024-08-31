Feature: PayCRM: Partner Enrollments Module - Regression Test Scenario's

  #CPAY-661 & CPAY-662
  @Regression @Smoke @PartnerEntrollments @CPAY-661 @CPAY-662
  Scenario: Partner Enrollments - Verify user performs Reassign functionality and validate the same in Activities tab
    Given User login to the "PayCRM" application using "qaDetails" credentials
    Then User perform Reassignment of the Enrollment case for "Partner Enrollments" queue
      | AssignedToUser1      | AssignedToUser2       |
      | ggudi@corcentric.com | asingh@corcentric.com |
    And User verify that the reassignment details are displayed in "Partner Enrollments" activity tab
    And User closes Child window and switch back to Parent browser window
    And User logs out from the "PayCRM" application

  #CPAY-977 & CPAY-380
  @Regression @PartnerEntrollments @CPAY-977 @CPAY-380
  Scenario: Partner Enrollments : Validate all the filters functionality for the case grid
    Given User login to the "PayCRM" application using "qaDetails" credentials
    And User validates the Filter functionality on the "Partner Enrollments" Cases Grid

  #CPAY-383, CPAY-382, CPAY-384, CPAY-387 & CPAY-388    //CPAY-383 & 388 issue exist : https://determine.atlassian.net/browse/CPAY-2957
  @Regression @PartnerEntrollments @CPAY-383 @CPAY-382 @CPAY-384 @CPAY-387 @CPAY-388
  Scenario: Partner Enrollments : Log Activity validation
    Given User login to the "PayCRM" application using "qaDetails" credentials
    Then Verify Communities Table multiselect and Assigned To filter functionalities for "Partner Enrollments" queue
    And User validates Email and phone fields on the "Partner Enrollments" Log Activity page
    And User validates Case Status and Partner Types filter functionality for "Partner Enrollments" queue


  #CPAY-398
  @Regression @PartnerEntrollments @CPAY-398
  Scenario: Partner Enrollments : Role/Permissions Testing
    Given User login to the "PayCRM" application using "qaDetails" credentials
    And User "Enable" the following "Super Admin#Partner Case User#Partner Case Admin" roles for Partner Enrollment
    And User login to the "PayCRM" application using "qaPermissionDetails" credentials
    Then User validates the role OR permission for Partner Enrollments "Before" removing the required permissions
    And User login to the "PayCRM" application using "qaDetails" credentials
    And User "Disable" the following "Super Admin#Partner Case User#Partner Case Admin" roles for Partner Enrollment
    And User login to the "PayCRM" application using "qaPermissionDetails" credentials
    Then User validates the role OR permission for Partner Enrollments "After" removing the required permissions
    And User login to the "PayCRM" application using "qaDetails" credentials
    And User "Enable" the following "Super Admin#Partner Case User#Partner Case Admin" roles for Partner Enrollment
