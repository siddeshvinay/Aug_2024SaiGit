Feature: PayCRM: Enrollments Manager Module - Regression Test Scenario's

  #CPAY-686 & CPAY-687
  @Regression @EnrollmentManagers @CPAY-686 @CPAY-687
  Scenario: Enrollments Manager : Add-Edit-Delete attachments : Verify user adds, Edit and remove the attachment to the enrollment case
    Given User login to the "PayCRM" application using "qaDetails" credentials
    And User adds the attachment for "Enrollments Manager" queue
      | Name    | Description                       | AttachmentType | Status | UploadFileName |
      | AutoNew | This is the new sample attachment | Document       | Active | SampleFileOne  |
    And User verify that the "Add" attachment details for "Enrollments Manager" queue
    Then User edit the attachment for the "Enrollments Manager" queue
      | Name     | Description                          | AttachmentType | Status | UploadFileName | AttachToSupplier |
      | AutoEdit | This is the edited sample attachment | Document       | Active | SampleFileTwo  | No               |
    Then User verify that the "Edit" attachment details for "Enrollments Manager" queue
    Then User removes the "Edit" attachment for "Enrollments Manager" queue


  #CPAY-390, CPAY-658, CPAY-660 & CPAY-681
  @Regression @EnrollmentManagers @DEMO @CPAY-390 @CPAY-658 @CPAY-660 @CPAY-681
  Scenario: Enrollments Manager : Verify user performs Reassign functionality and validate the same in Activities and Logs tab
    Given User login to the "PayCRM" application using "qaDetails" credentials
    When User perform Reassignment of the Enrollment case for "Enrollments Manager" queue
      | AssignedToUser1      | AssignedToUser2       | queueName   |
      | ggudi@corcentric.com | asingh@corcentric.com | Enrollments |
    And User verify that the reassignment details are displayed in "Enrollments Manager" activity tab
    And User verify that the reassignment details are displayed in "Enrollments Manager" Logs details


  #CPAY-682 & CPAY-683
  @Regression @Smoke @EnrollmentManagers @CPAY-682 @CPAY-683
  Scenario: Enrollments Manager : Verify user performs create, Edit and delete of supplier contact details
    Given User login to the "PayCRM" application using "qaDetails" credentials
    Then User adds the "New" supplier contact details for "Enrollments Manager" and validates the same
      | ContactType | Name     | Email                | Title | Phone1     | Phone1Extn | Phone1Type | Phone2     | Phone2Type | Status | Priority | IsPrimaryContact |
      | Primary     | Auto New | ggudi@corcentric.com | Mr    | 9908877665 | 080        | Business   | 9900112233 | Cell       | Active | High     | Yes              |
    Then User perform the "Edit" supplier contact details for "Enrollments Manager" and validates the same
      | ContactType | Name      | Email                 | Title | Phone1     | Phone1Extn | Phone1Type | Phone2     | Phone2Type | Status | Priority | IsPrimaryContact |
      | Primary     | Auto Edit | asingh@corcentric.com | Mr    | 9908765432 | 080        | Business   | 9988012345 | Cell       | Active | High     | Yes              |
    Then User deletes the existing the contact & verify contact deleted successful


  #CPAY-684 & CPAY-685
  @Regression @EnrollmentManagers @CPAY-684 @CPAY-685
  Scenario: Enrollments Manager : Verify user performs create, Edit and delete Address details
    Given User login to the "PayCRM" application using "qaDetails" credentials
    Then User adds the "New" Address details for "Enrollments Manager" and validates the same
      | AddressType | Country | Address1                   | Address2 | Address3 | City      | StateProvince | PostalCode | Language |
      | Primary     | US      | 260-C North E1 Camino Real |          |          | Encinitas | California    | 92024-2852 | English  |
    Then User perform the "Edit" Address details for "Enrollments Manager" and validates the same
      | AddressType | Country | Address1         | Address2 | Address3 | City    | StateProvince | PostalCode | Language |
      | Mailing     | CA      | 700 Quintard Ave |          | Oxford   | Calhoun | Nova Scotia   | 36203      | English  |
    Then User deletes the existing "Address" & verify address deleted successful


  #CPAY-976
  @Regression @EnrollmentManagers @CPAY-976 @CPAY-4735 @CPAY-5016 @CPAY-5017
  Scenario: Enrollments Manager : Verify Case grid column filter functionalities
    Given User login to the "PayCRM" application using "qaDetails" credentials
    And User validates the Column search functionality on the "Enrollments Manager" Cases Grid
    And User validates the column filter functionality on the Enrollment Cases Grid


  #CPAY-790
  @Regression @EnrollmentManagers @CPAY-790
  Scenario: AWS EXTERNAL - CloudWatch Error logs : Re-run Single Payment Poll
    Given User login to the "awsExternal" application using "AWSDetails" credentials
    And User Re-run Single Payment Poll
      | RerunSinglePaymentPoll_Hash                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   |
      | {"PaymentID"=>39355, "ClientVNetID"=>641426845, "ClientName"=>"Fenwick & West LLP", "FileID"=>3798730, "BFileID"=>251471, "PayeeName"=>"Solarwinds", "PayeeCompanyID"=>683714062, "PayeeCompanyName"=>"SolarWinds North America, Inc.", "PayeeEntityIdentifier"=>"1030565", "PaymentIdentifier"=>"5477464", "RemitTo"=>"1030565", "CreditBankCountryISO"=>"US", "CurrencyCode"=>"USD", "Amount"=>6343.0, "AmountPosted"=>nil, "AmountVoided"=>nil, "ValueDateAssigned"=>"2022-06-03T00:00:00", "LinkID"=>164518, "RemittanceCount"=>1, "DebitAccountNumber"=>nil, "ReturnStatusID"=>5, "PICID"=>20557, "CardAccountNumber"=>nil, "CardAccountID"=>432, "PIID"=>80431, "VirtualCardProcessedAt"=>nil, "CardNumber"=>"4865260081503332", "Expiry"=>"09/22", "CVC"=>"177", "CardIssueDate"=>"2022-06-02T06:04:08.91", "ProviderName"=>"DXC", "PaymentCreatedAt"=>"2022-05-26T16:32:42.16", "ApprovedAt"=>"2022-05-26T16:34:30.623", "ReleasedAt"=>"2022-05-27T09:10:22.82", "DeliveredAt"=>"2022-05-27T09:23:23.27", "LastStatusUpdateAt"=>"2022-06-02T06:03:52.227", "DueDate"=>"2022-06-03T00:00:00", "RequestedAmount"=>6343.0, "AdjustmentAmount"=>nil, "PaymentAmount"=>6343.0, "ReferenceNumber"=>nil, "BuyerID"=>"10007", "VCardEmailAddress"=>"accountsreceivable@solarwinds.com", "IsVPay"=>0, "PaymentIDFormatted"=>"641426845-39355"} |


  #CPAY-798 & CPAY-799
  @Regression @EnrollmentManagers @CPAY-798 @CPAY-799
  Scenario: Dev Settings - Create and edit the application setting
    Given User login to the "PayCRM" application using "qaDetails" credentials
    And User creates the new application setting & edits the same
      | Name              | Value           |
      | AutoNewAppSetting | Automation Test |


  #CPAY-838
  @Regression @EnrollmentManagers @CPAY-838
  Scenario: Enrollments Manager : Create and edit the Payment process
    Given User login to the "PayCRM" application using "qaDetails" credentials
    And User creates the "New" payment process for "Enrollments Manager" and validate the same
      | Supplier | AccountNumber | Method  | Address                    | ServiceInformation                                                              | AdditionalInformation                         | Steps                                    | Notes                            |SecurityQuestions                   |
      | Green    |               | Website | 123 Main Street California | Security_Answer_1#Answer of security Q1,Security_Answer_2#Answer of security Q2 | Test1#Additional Info1,Test2#Additional Info2 | Open Browser#Login with valid Credential | Payment Process automation notes |Question1#Answer1,Question2#Answer2 |
    And User "Edit" the payment process for "Enrollments Manager" and validate the same
      | Supplier | AccountNumber | Method  | Address                    | ServiceInformation                                                                 | AdditionalInformation                         | Steps                                    | Notes                            |SecurityQuestions                   |
      | Green    |               | Website | 123 Main Street California | RPAWorkflowName#RPAWorkflow_UIAutomation,Security_Question_3#Answer to security Q3 | Test1#Additional Info1,Test2#Additional Info2 | Open Browser#Login with valid Credential | Payment Process automation notes |Question1#Answer1,Question2#Answer2 |
    Then User deletes the Payment process for "Enrollments Manager" queue


  #CPAY-806
  @Regression @EnrollmentManagers @CPAY-806
  Scenario: Search Box: Enter a search phrase
    Given User login to the "PayCRM" application using "qaDetails" credentials
    Then Enter a search value and validate the search output
      | searchValue  |
      | Gordon foods |


  #CPAY-836 & CPAY-833
  @Regression @EnrollmentManagers @CPAY-836 @CPAY-833
  Scenario: External Enrollment Site: Test Duplicate Creation
    Given User navigates to the "EnrollmentSite" application using "enrollmentSiteURL" URL and perform Test Duplicate creation
      | ActivationCode | Company_IndividualName | TaxID_SSN | PhysicalAddress      | City     | State      | ZipCode    | TaxClarification           | FirstName | LastName | EmailAddress     | PhoneNumber | Extn | PaymentNotificationEmail | PaymentTerm                        | EmailForVisaCreditCard |
      | WrwTBx49Yn2p   | Latlux                 | 240964125 | 495 Grove Street, NY | New York | California | 55555-5555 | Individual/Sole Proprietor | Reg       | Test     | regtest@test.com | 5779689550  |      |                          | Visa Card at Current Payment Terms | regtest@test.com       |
    Given User login to the "PayCRM" application using "qaDetails" credentials
    Then Validate the Enrollment Site Registration details under Duplicate Supplier Management and search enrolled records


  #CPAY-777 & CPAY-776
  @Regression @EnrollmentManagers @DEMO @CPAY-777 @CPAY-776
  Scenario: Manual API Polling- Link: Pull the latest Company and Supplier Link data from PayNet to PayCRM
    Given User login to the "PayCRM" application using "qaDetails" credentials
    Then User pulls the latest Company and Supplier Link data from PayNet to PayCRM


  #CPAY-1807 & CPAY-1808
  @Regression @EnrollmentManagers @CPAY-1807 @CPAY-1808
  Scenario: Enrollments Manager :Validate Parent Company column in Enrollment Manager and Assisted Payment Services dashboard and Validating companies with 0 open cases
    Given User login to the "PayCRM" application using "qaDetails" credentials
    Then User navigates to "Enrollments Manager" queue and validates the companies dashboard
    Then User navigates to "Assisted Payment Services" queue and validates the companies dashboard


  #CPAY-3406, CPAY-3416 & CPAY-3418 - New issue - https://determine.atlassian.net/browse/CPAY-5013
  @Regression @EnrollmentManagers @CPAY-3406 @CPAY-3416 @CPAY-3418
  Scenario: External Enrollment Microsite :Verify Duplicate Supplier and email functionality from Microsites
    Given User navigates to "EnrollmentSite" application using the "EnrollmentMicrosite" URL and validate Duplicate suppliers with Unique records
      | ActivationCode     | Company_IndividualName | TaxID_SSN | PhysicalAddress      | City       | State      | ZipCode    | TaxClarification           | FirstName  | LastName | EmailAddress     | PhoneNumber | From |
      | ELSEVIERUS_staging | autoCompany            |           | 495 Grove Street, NY | California | California | 55555-5555 | Individual/Sole Proprietor | Regression | Test     | regtest@test.com |             |      |
    Given User navigates to "EnrollmentSite" application using the "EnrollmentMicrosite" URL and validate Duplicate suppliers with duplicate records
      | ActivationCode     | Company_IndividualName | TaxID_SSN | PhysicalAddress      | City       | State      | ZipCode    | TaxClarification           | FirstName  | LastName | EmailAddress     | PhoneNumber | From                         |
      | ELSEVIERUS_staging | autoCompany            |           | 495 Grove Street, NY | California | California | 55555-5555 | Individual/Sole Proprietor | Regression | Test     | regtest@test.com | 9538005624  | notifications@corcentric.com |


  #CCPAY-5575
  @Regression @PartnerEntrollments @CPAY-5575
  Scenario: Enrollments : non-admins agent case view to display 60 cases
    Given User login to the "PayCRM" application using "qaDetails" credentials
    And User "Enable" the following "Super Admin#Partner Case User#Partner Case Admin" roles for Partner Enrollment
    And User login to the "PayCRM" application using "qaPermissionDetails" credentials
    Then User validates the role OR permission for Partner Enrollments "Before" removing the required permissions
    And User login to the "PayCRM" application using "qaDetails" credentials
    And User "Disable" the following "Super Admin#Partner Case User#Partner Case Admin" roles for Partner Enrollment