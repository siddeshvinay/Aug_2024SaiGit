Feature: PayCRM Modules:Imports - Regression Test Scenario's

  #CPAY-714, CPAY-715 & CPAY-884
  @Regression @PayCRMImports @CPAY-714 @CPAY-715 @CPAY-884
  Scenario: PayCRM Modules:Imports - Master Supplier Files: Test the create, edit & download Master Supplier Files functionality
    Given User login to the "PayCRM" application using "qaDetails" credentials
    And User creates new Master Supplier File and validate the same
      | Name       | Company    | Wave | Version | Status  | Priority | FileToUpload          | OverrideHistoricalValues |
      | NewAutoMSF | 4Catalyzer |      | 1       | Initial | Low      | MasterSupplierFileOne | No                       |
    Then User edit the existing Master Supplier File and validate the same
      | Name        | Company   | Wave | Version | Status | Priority | FileToUpload | OverrideHistoricalValues |
      | EditAutoMSF | Acculogix |      | 2       | Active | High     |              |                          |
    And User downloads the Master Supplier File and checks download was successful


  #CPAY-719 & CPAY-722
  @Regression @Smoke @PayCRMImports @CPAY-719 @CPAY-722
  Scenario: PayCRM Modules:Imports - Match Files: Test the create and edit Match Files functionality
    Given User login to the "PayCRM" application using "qaDetails" credentials
    And User creates new Match File and validate the same
      | Name      | Company    | MasterSupplierFile | Version | Status  | Priority | FileToUpload |
      | NewAutoMF | 4Catalyzer |                    | 1       | Initial | Low      | MatchFileOne |
    Then User edit the existing Match File and validate the same
      | Name       | Company            | MasterSupplierFile | Version | Status | Priority | FileToUpload |
      | EditAutoMF | Accelerate360, LLC |                    | 2       | Active | High     |              |


 #CPAY-761, CPAY-774, CPAY-775, CPAY-759 & CPAY-760 #Demo
  @Regression @PayCRMImports @CPAY-761 @CPAY-774 @CPAY-775 @CPAY-759 @CPAY-760
  Scenario: PayCRM Modules:Imports - Payment Based Enrollments: Test the create, edit and validate Payment Based Enrollments functionality
    Given User login to the "PayCRM" application using "qaDetails" credentials
    And User creates new Payment Based Enrollments and validate the same
      | Name       | Company            | Wave      | Version | Status  | Priority | EmailMunchResultsTo | FileToUpload            |
      | NewAutoPBE | Accelerate360, LLC |           | 1       | Initial | Low      |                     | PaymentBasedEnrollments |
    Then User edit the existing Payment Based Enrollments and validate the same
      | Name        | Company            | Wave                             | Version | Status | Priority | EmailMunchResultsTo | FileToUpload |
      | EditAutoPBE | Accelerate360, LLC | Accelerate360 - Card First - USD | 2       | Active | High     |                     |              |
    And User validates the filter & Payment Based Enrollments details


  #CPAY-746 & CPAY-747
  @Regression @PayCRMImports @CPAY-746 @CPAY-747
  Scenario: PayCRM Modules:Imports - Report Files: Validate Create & edit Report Files functionality
    Given User login to the "PayCRM" application using "qaDetails" credentials
    And User creates new report file and validate the same
      | Name          | Company | Version | ReportType       | Status  | Priority | FileToUpload  |
      | newReportFile |         | 1       | External Payment | Initial | High     | ReportFileOne |
    Then User edit the existing Report Files and validate the same
      | Name           | Company | Version | ReportType                        | Status | Priority |
      | editReportFile |         | 2       | Third Party Enrollment - Enrolled | Active | Low      |


  #CPAY-3357 & CPAY-3358 #Demo
  @Regression @PayCRMImports @CPAY-3357 @CPAY-3358
  Scenario: PayCRM Modules:Imports - Verify user permissions in Master Supplier files with OR without Super Admin Role
    Given User login to the "PayCRM" application using "qaDetails" credentials
    And User "Enable" the following "Super Admin" roles for Master Supplier file
    Then User login to the "PayCRM" application using "qaPermissionDetails" credentials
    Then User validates the role OR permission for Master Supplier file "Before" removing the required permissions
    Then User login to the "PayCRM" application using "qaDetails" credentials
    And User "Disable" the following "Super Admin" roles for Master Supplier file
    Then User login to the "PayCRM" application using "qaPermissionDetails" credentials
    Then User validates the role OR permission for Master Supplier file "After" removing the required permissions
    Then User login to the "PayCRM" application using "qaDetails" credentials
    And User "Enable" the following "Super Admin" roles for Master Supplier file