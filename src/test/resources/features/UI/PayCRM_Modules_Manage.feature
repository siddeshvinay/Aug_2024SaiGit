Feature: PayCRM Modules:Manage - Regression Test Scenario's

  #CPAY-710, CPAY-711, CPAY-712 & CPAY-713
  @Regression @PayCRMManage @CPAY-710 @CPAY-711 @CPAY-712 @CPAY-713
  Scenario: PayCRM Modules:Manage - User Console: Create & edit the role, create the user and assign role, groups, queue, companies and views to the user
    Given User login to the "PayCRM" application using "qaDetails" credentials
    And User creates new role and validate the same
      | RoleName     | Description         |
      | AutoRoleName | AutoRoleDescription |
    Then User edit the existing role and validate the same
    And Create new user and assign role, groups, queue, companies
      | Email    | Roles                                | Groups         | AssignToQueues                                    | CompaniesCanView                                                     | CompaniesCannotView | AssignToViews |
      | autoUser | Internal Admin#Super Admin#View Only | Internal Users | Third-Party Enrollments#Inbound Email#Enrollments | American Kidney Fund - Grant Management#Apache Corporation#CP Parent | Accelerate360, LLC  | All Open      |
    And User validates the Monetization Tracking dashboard


  #CPAY-737, CPAY-738, CPAY-739, CPAY-755, CPAY-756, CPAY-757 & CPAY-758
  @Regression @Smoke @PayCRMManage @CPAY-737 @CPAY-738 @CPAY-739 @CPAY-755 @CPAY-756 @CPAY-757 @CPAY-758
  Scenario: PayCRM Modules:Manage - Duplicate Supplier Management: Validate the search and filter functionalities under Duplicate Supplier Management
    Given User login to the "PayCRM" application using "qaDetails" credentials
    And Validate search filters on Duplicate Supplier Management->Loaded page using query "DuplicateSupplierManagement"
    And Validate search filters on Duplicate Supplier Management->Completed page using query "DuplicateSupplierManagementCompleted"
    And User validates the Customized data columns display functionality for "Loaded" section using query ""
    And User validates the Customized data columns display functionality for "Completed" section using query "DuplicateSupplierManagementCompleted"
    Then User validates the column data filters functionality for "Completed" section using query "DuplicateSupplierManagementCompleted"
    Then User validates the column data filters functionality for "Loaded" section using query "DuplicateSupplierManagement"


  #CPAY-740 & CPAY-741
  @Regression @PayCRMManage @CPAY-740 @CPAY-741
  Scenario: PayCRM Modules:Manage - Duplicate Supplier Management- Loaded: Validate Merging and non-merging of duplicate Suppliers functionality
    Given User login to the "PayCRM" application using "qaDetails" credentials
    Then User perform merging of duplicate suppliers
    Then User perform donot merging of duplicate suppliers


  #CPAY-3489
  @Regression @Smoke @PayCRMDuplicateSupplierManagement @DSM @CPAY-3489
  Scenario: PayCRM Modules:DSM - Verify Duplicate Supplier Management Page features
    Given User login to the "PayCRM" application using "qaDetails" credentials
    Then User navigates to "Duplicate Supplier Modules" Modules and verifies presence of required fields on page


  #CPAY-709 & CPAY-1668
  @Regression @PayCRMManage @CPAY-709 @CPAY-1668 @CPAY-4678 @CPAY-4771 @CPAY-4772 @CPAY-5327 @CPAY-5328
  Scenario: PayCRM Modules:Manage - Reports: Validate all the report filters are working properly
    Given User login to the "PayCRM" application using "qaDetails" credentials
    And User apply filter to "All Enrolled" reports and validates the same
    And User apply filter to "Activity Report" reports and validates the same
    And User apply filter to "Agent Activity" reports and validates the same
    And User apply filter to "Bill Pay Report" reports and validates the same
    And User apply filter to "Case Outcomes Report" reports and validates the same
    And User apply filter to "High Priority Suppliers" reports and validates the same
    And User apply filter to "First Time Payments" reports and validates the same
    And User apply filter to "Enrollments by Team Member" reports and validates the same
    And User apply filter to "Enrollment Report" reports and validates the same
    And User apply filter to "Multi Swipe" reports and validates the same
    And User apply filter to "Need Invoice" reports and validates the same
    And User apply filter to "Origination Sources" reports and validates the same
    And User apply filter to "Partner Enrollments" reports and validates the same
    And User apply filter to "Switched Payment Types" reports and validates the same
    And User apply filter to "support case agent activity" reports and validates the same
    And User apply filter to "support case outcomes report" reports and validates the same
    And User apply filter to "supplier activity report" reports and validates the same
    And User apply filter to "company activity report" reports and validates the same


  #CPAY-1662, CPAY-1613 & CPAY-1614
  @Regression @PayCRMManage @CPAY-1662 @CPAY-1613 @CPAY-1614
  Scenario: PayCRM Modules:Manage - Reports: Verify PAF email template and email Domain
    Given User login to the "PayCRM" application using "qaDetails" credentials
    Then User Navigates to Reports-->Enrollment Reports-->Verify PAF mail templates
      | ContactEmail | From                       | CC | Subject | Message | FileToUpload | OfferAccepted |
      |              | validations@corcentric.com |    |         |         |              | VND           |
    And User Navigates to Reports-->Enrollment Reports-->Verify Send Mail functionality
      | To | Subject               | isFileSupported | FileToUpload                | Message                                                             |
      |    | Automation-Send Email | Yes             | CompanyAttachment_dummy.pdf | This is a Sample email body content for Automation Regression testing |