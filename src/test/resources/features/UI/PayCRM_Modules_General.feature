Feature: PayCRM: General Modules - Regression Test Scenario's

  #CPAY-695, CPAY-668 & CPAY-717
  @Regression @Smoke @PayCRMGeneral @CPAY-695 @CPAY-668 @CPAY-717
  Scenario: PayCRM Modules:General-Companies: Test Create and edit The company details
    Given User login to the "PayCRM" application using "qaDetails" credentials
    And User navigates to "Companies" Modules and verifies presence of all the column names in the Companies Grid
      | columnNames                                                                            |
      | Name#Web Address#PayNet Company ID#Phone#Parent#Origination Source#Type#Status#Actions |
    Then User create the "New" company and validate the same successful
      | Name    | Tickler | Website                          | PaynetCompanyID | SalesforceID | Phone      | Fax        | ZipCode    | Parent        | Locale | Language | OriginationSource | CompanyType | Status | Priority |
      | newAuto | Ticker1 | https://staging.vendorinroll.com |                 | 123456789    | 9900887766 | 9090909090 | 55555-5555 | DoAll Company | US     | ENGLISH  | Testing           | Prospect    | Active | Low      |
    Then User "Edit" the company details and validate the same successful
      | Name     | Tickler | Website                   | PaynetCompanyID | SalesforceID | Phone      | Fax        | ZipCode    | Parent    | Locale | Language | OriginationSource | CompanyType | Status  | Priority |
      | editAuto | Ticker1 | https://demo.vendorin.com |                 | 987654321    | 9900112233 | 9000000009 | 55555-4444 | Acculogix | UK     | English  | Automation        | Prospect    | Initial | High     |


#  #CPAY-704
#  @RegressionDeprecate @PayCRMGeneral @CPAY-704
#  Scenario: PayCRM Modules:General-Contacts: Validate Edit Contact works successful
#    Given User login to the "PayCRM" application using "qaDetails" credentials
#    And User navigates to "Contacts" Modules and verifies presence of all the column names in the Companies Grid
#      | columnNames                                                                        |
#      | Name#Phone 1#Phone 1 Type#Phone 2#Phone 2 Type#Email#Title#Status#Priority#Actions |
#    Then User perform the "Edit" contacts from General Modules
#      | Name             | Phone1     | Phone1Type | Phone2     | Phone2Type | Email                 | Title | Status | Priority |
#      | editAuto Contact | 9999888888 | Cell       | 9880000123 | Work       | asingh@corcentric.com | Mr    | Active | High     |


  #CPAY-673
  @Regression @PayCRMGeneral @CPAY-673
  Scenario: PayCRM Modules:General - Suppliers: Test the filters functionality on Supplier Page
    Given User login to the "PayCRM" application using "qaDetails" credentials
    Then User apply the "Valid" filter and search the Suppliers data using query "GeneralSupplierData"
      | SupplierName      | Type                 | Status   | Priority | PaynetCompanyID | RecordLimit | HasPaynetCompanyID | InProgramOnly |
      | VIVA RAILINGS LLC | (In Program) General | Prospect | Low      |                 | 5           | No                 | No            |
    Then User apply the "Valid" filter and search the Suppliers data using query "GeneralSupplierData"
      | SupplierName | Type | Status | Priority | PaynetCompanyID | RecordLimit | HasPaynetCompanyID | InProgramOnly |
      |              |      |        |          |                 | 10          | Yes                | No            |
#    Then User apply the "Valid" filter and search the Suppliers data using query "GeneralSupplierData"
#      | SupplierName         | Type | Status | Priority | PaynetCompanyID | RecordLimit | HasPaynetCompanyID | InProgramOnly |
#      | WOMENS CANCER CENTER |      |        |          |                 | 10          | No                 | No            |
#    Then User apply the "Invalid" filter and search the Suppliers data using query "GeneralSupplierData"
#      | SupplierName | Type | Status | Priority | PaynetCompanyID | RecordLimit | HasPaynetCompanyID | InProgramOnly |
#      | wR0nG$$ D@t@ |      |        |          |                 | 10          | No                 | No            |


  #CPAY-793
  @Regression @PayCRMGeneral @CPAY-793
  Scenario: PayCRM Modules:General - Suppliers: Suppliers->Validations tab: Profile Authorization Form - Path
    Given User login to the "PayCRM" application using "qaDetails" credentials
    And User navigates and select the general suppliers using the query "GeneralSuppliers_Validations"
    Then User navigates to Suppliers page and validate the Validations tab UI elements

  @Regression @PayCRMGeneral @CPAY-795 @CPAY-796 @Test123
  Scenario: PayCRM Modules:General - Companies: Prospect Company - Solution Design - Other Providers Approach Test Functionality
    Given User login to the "PayCRM" application using "qaDetails" credentials
    Then User navigates to prospect companies & validates the Other provider approach section under Solution design tab using the query "ProspectCompany_OtherProvidersApproach"
    #And User validates the Update Duplicates From Parent under Solution design->Suppliers tab using the query "ProspectCompany_SolutionDesignSuppliers"


  #CPAY-856, CPAY-794 & CPAY-797
  @Regression @PayCRMGeneral @CPAY-856 @CPAY-794 @CPAY-797
  Scenario: PayCRM Modules:General - Suppliers: Validate Add, View, Edit, Download and Delete Supplier Attachment
    Given User login to the "PayCRM" application using "qaDetails" credentials
    And User navigates and select the general suppliers using the query "GeneralSupplierID"
    Then User creates new "Supplier" attachment and validate the same
      | Name         | Description             | AttachmentType | Status  | FileToUpload             |
      | autoSupplier | Add Supplier Attachment | Document       | Initial | SupplierAttachment_dummy |
    Then User edit the "Supplier" attachment and validate the same
      | Name         | Description              | AttachmentType | Status  | FileToUpload |
      | autoSupplier | edit Supplier Attachment | Contract       | Initial |              |
    And User exports the "Supplier" Attachment and validates the same
    And User downloads and deletes the "Supplier" attachment


  #CPAY-840 & CPAY-842
  @Regression @PayCRMGeneral @CPAY-840 @CPAY-842
  Scenario: PayCRM Modules:General - Companies: Client Company - Solution Design - Other Providers Approach & create, Edit, Show, Download, Export & delete attachment Functionality
    Given User login to the "PayCRM" application using "qaDetails" credentials
    Then User navigates to client companies, Export "Cases" and validate the same
    Then User creates new "Company" attachment and validate the same
      | Name        | Description            | AttachmentType | Status  | FileToUpload            |
      | autoCompany | Add company Attachment | Document       | Initial | CompanyAttachment_dummy |
    Then User edit the "Company" attachment and validate the same
      | Name        | Description             | AttachmentType | Status  | FileToUpload |
      | autoCompany | edit company Attachment | Contract       | Initial |              |
    Then User navigates to client companies, Export "Attachments" and validate the same
    And User downloads and deletes the "Company" attachment


  #CPAY-841
  @Regression @PayCRMGeneral @CPAY-841
  Scenario: PayCRM Modules:General - Companies: Client Company - Create, Edit, Show, Export & delete address Functionality
    Given User login to the "PayCRM" application using "qaDetails" credentials
    And User adds the "New" Address details for "Company" and validates the same
      | AddressType | Country | Address1                   | Address2 | Address3 | City      | StateProvince | PostalCode | Language |
      | Primary     | US      | 260-C North E1 Camino Real |          |          | Encinitas | California    | 92024-2852 | English  |
    Then User perform the "Edit" Address details for "Company" and validates the same
      | AddressType | Country | Address1         | Address2 | Address3 | City    | StateProvince | PostalCode | Language |
      | Mailing     | CA      | 700 Quintard Ave |          | Oxford   | Calhoun | Nova Scotia   | 36203      | English  |
    Then User navigates to client companies, Export "Company Address" and validate the same
    Then User deletes the existing "Company Address" & verify address deleted successful


  #CPAY-843
  @Regression @PayCRMGeneral @CPAY-843
  Scenario: PayCRM Modules:General - Companies: Client Company - Create, Edit, Show, Export, Import & delete Contact Functionality
    Given User login to the "Companies->Show" application using "clientCompanyDetails_CPAY843" credentials
    And User add the "New" Contacts for the client Company and validates the same
      | ContactName | Email                 | Title         | Phone1     | Phone1Extn | Phone1Type | Phone2     | Phone2Type | Status  | Priority |
      | new Contact | asingh@corcentric.com | Auto Contacts | 9988776655 | 444        | Work       | 9988888800 | Work       | Initial | Low      |
    Then User perform the "Edit" Contacts for client Company and validates the same
      | ContactName  | Email                | Title         | Phone1     | Phone1Extn | Phone1Type | Phone2     | Phone2Type | Status | Priority |
      | edit Contact | ggudi@corcentric.com | Auto Contacts | 9900888888 | 555        | Business   | 9090909090 | Cell       | Active | High     |
    Then User exports the client company Contacts and validate the same
    And User Imports the SFDC Contacts & deletes the client company Contacts and validates the same


  #CPAY-846
  @Regression @PayCRMGeneral @CPAY-846
  Scenario: PayCRM Modules:General - Companies: Client Company - Waves
    Given User login to the "PayCRM" application using "qaDetails" credentials
    And User navigates and select the General "Client" Company by using the query "General_ClientComapnies"
    Then User exports client company Waves and Open in New Tab


  #CPAY-845
  @Regression @PayCRMGeneral @CPAY-845
  Scenario: PayCRM Modules:General - Companies: Client Company - Edit, Show, Export and Open in new Tab for the Suppliers
    Given User login to the "PayCRM" application using "qaDetails" credentials
    And User navigates and select the General "Client" Company by using the query "General_ClientComapnies"
    Then User Edits the client company "Suppliers" detail & validates the same
      | VendorID | SupplierType       | Status        | Priority  |
      |          | General#Low Volume | Active#Loaded | High#High |
    Then User exports client company "Suppliers" and Open in New Tab


  #CPAY-839
  @Regression @PayCRMGeneral @CPAY-839
  Scenario: PayCRM Modules:General - Companies: Client Company - Validate Export button was removed from the case activities data
    Given User login to the "Companies->Show" application using "clientCompanyDetails" credentials
    Then User validates exports icon was removed from the client companies Case Activities


  #CPAY-844
  @Regression @PayCRMGeneral @CPAY-844
  Scenario: PayCRM Modules:General - Companies: Client Company - Edit, Show, Export and Open in new Tab for the High Priority cases
    Given User login to the "PayCRM" application using "qaDetails" credentials
    And User navigates and select the General "Client" Company by using the query "General_ClientComapnies"
    Then User Edits the client company "High Priority" detail & validates the same
      | VendorID | SupplierType       | Status        | Priority  | CheckNum | TotalSpend |
      |          | General#Low Volume | Active#Loaded | High#High | 1#2      | 5#10       |
    Then User exports client company "High Priority" and Open in New Tab


  #CPAY-805
  @Regression @PayCRMGeneral @CPAY-805
  Scenario: PayCRM Modules:General - Companies: Prospect Company - Update Global Variable from internal setup
    Given User login to the "PayCRM" application using "qaDetails" credentials
    Then User navigates to internal setup and updates the global variable


  #CPAY-835, CPAY-832, CPAY-831, CPAY-830, CPAY-823, CPAY-822
  @Regression @PayCRMGeneral @CPAY-835 @CPAY-832 @CPAY-831 @CPAY-830 @CPAY-823 @CPAY-822
  Scenario: PayCRM Modules:General - Companies: Prospect Company - Update preferences, Expected, Client Rebate Percentage, Corcentric & Current Payment Mix and Network match confidence from internal setup
    Given User login to the "PayCRM" application using "qaDetails" credentials
    Then User navigates to internal setup and updates the Preferences
      | TINValidation | BypassValidation | AutoEnroll | ProgramName | ProgramEmail                               | ClientMicrosite                                                                          |
      | true#false    | true#false       | true#false | autoProgram | asingh@corcentric.com#ggudi@corcentric.com | https://corcentric.com/enroll/accelerate360.regression#https://staging.vendorinroll.com/ |
    Then User navigates to internal setup and updates the Expected section
      | ClientRebatePercentage | ClientKeepPercentage | AchPlusDiscountPercentage | AverageCheckCost |
      | .24#.75                | 25#40                | 2.5#4.5                   | 7#10             |
    Then User navigates to internal setup and updates the Client Rebate Percentage
      | TypicalClientRebatePercentage |
      | 2.8#3.2                       |
#    Then User navigates to internal setup and updates the Corcentric Payment Mix
#      | AchBasic | AchBasicRevenue | AchPlus | AchPlusRevenue | Check | CheckRevenue | VirtualCard | VirualCardRevenue |
#      | 35#40    | true#false      | 10#15   | true#false     | 15#20 | true#false   | 25#30       | true#false        |
#    Then User navigates to internal setup and updates the Current Payment Mix
#      | Check      | Wire | Ach        | Mixed      | ForeignExchange | VirtualCard | EnhancedAch |
#      | true#false |      | true#false | true#false |                 |             |             |
    Then User navigates to internal setup and updates the Card Network Match Confidence
      | High  | Medium | Low   | Blank |
      | 55#60 | 35#40  | 10#15 | 0     |


  #CPAY-824, CPAY-825, CPAY-826, CPAY-754
  @Regression @PayCRMGeneral  @CPAY-824 @CPAY-825 @CPAY-826 @CPAY-754
  Scenario: PayCRM Modules:General - Companies: Prospect Company - Validate Test, Hover over Cost & GoLive Functionalities
    Given User login to the "companies->Prospect" application using "prospectDetails_CPAY824" credentials
    Then User validates the "Solution" page Test functionality of Prospect company
    Then User validates the "Solution Design->Results With Corcentric" page Test functionality of Prospect company
    Then User validates the hover over total cost column values


  #CPAY-829
  @Regression @PayCRMGeneral @CPAY-829 @CPAY-5880
  Scenario: PayCRM Modules:General - Suppliers: Test the Client Link Information on Supplier Page
    Given User login to the "PayCRM" application using "qaDetails" credentials
    And User navigates and select the general suppliers using the query "Supplier_ClientLinkInformation"
    Then Click on View PayNet Link and verify data pulled from PayNet
    And User validates Client Link Information data
      | VendorID | SupplierType       | Wave   |Status        | Priority   | TotalSpend | NumberChecks | Link          | PaymentType           |
      |          | General#Low Volume |        |Active#Loaded | Low#Medium | 2#4        | 1#2          | 998877#778899 | Virtual Card#ACH Plus |


  #CPAY-847
  @Regression @PayCRMGeneral @CPAY-847
  Scenario: PayCRM Modules:General - Companies: Client Company - validate Activation code and Users Functionality
    Given User login to the "Companies->Show" application using "clientCompanyDetails_CPAY847" credentials
    And User "Add" new access code and validates the same
      | ActivationCode | AssignedTo            | AdditionalInfo       | ExpiresAt | IsActivationCode |
      | Access         | asingh@corcentric.com | Creating Access code |           | true             |
    And User "Edit" new access code and validates the same
      | ActivationCode | AssignedTo           | AdditionalInfo      | ExpiresAt | IsActivationCode |
      |                | ggudi@corcentric.com | Editing Access code |           | true             |
    Then User perform assign and remove the work queue
      | QueueName   | AddUsers | removeUsers |
      | Enrollments |          |             |


  #CPAY-807 & CPAY-808
  @Regression @PayCRMGeneral @CPAY-807 @CPAY-808
  Scenario: PayCRM Modules:General - Companies: Prospect Company - Update Tab Visibility, Current State & Other Providers Approach from internal setup
    Given User login to the "PayCRM" application using "qaDetails" credentials
    Then User navigates to internal setup and updates the Tab Visibility
      | PrimaryTabs                                                      | SolutionDesignTabs                                                                                                      |
      | Account Setup#Solution Design#Pricing#Solution#Preview & Go Live | Current State#Other Providers Approach#Suppliers#MCC Matches#Supplier Type Matches#Access Codes with Corcentric Results |
    Then User navigates to internal setup and updates the Current State
      | UtilizationPercentage | EffectiveRebateOffset | RiskScorePercentage | CurrentClientRebatePercentage | CurrentEffortPercentage | CurrentEnhancedACHDiscountPercentage | CurrentEnhancedACHKeepPercentage |
      | 10#15                 | 10#15                 | 70#75               | 10#15                         | 100                     | 2.5                                  | 25                               |


  #CPAY-813 & CPAY-812
  @Regression @PayCRMGeneral  @CPAY-813 @CPAY-812
  Scenario: PayCRM Modules:General - Companies: Prospect Company - Solution Design-> Test Supplier Type Matches
    Given User login to the "PayCRM" application using "qaDetails" credentials
    Then User validates Supplier Type Matches under Solution Design section of prospect company using the query "ProspectCompany_Solution_Supplier_Type_Matches"
    Then User validates MCC Matches under Solution Design Section of the prospect company using the query "ProspectCompany_Solution_MCC_Matches"


  #CPAY-810 & CPAY-811
  @Regression @PayCRMGeneral @CPAY-810 @CPAY-811
  Scenario: PayCRM Modules:General - Companies: Prospect Company - Update Corcentric Analysis from internal setup
    Given User login to the "companies->Prospect" application using "prospectDetails_CPAY810" credentials
    Then User navigates to internal setup and updates the Corcentric Analysis
      | CorcentricEffortPercentage | Other                                | Corcentric                          |
      | 70#75                      | true#true#true#true#true#false#false | true#true#true#true#true#false#true |
    And User navigates to internal setup and updates Program Costs
      | YearRange | TotalCosts                                                              | PriceReduce                                                  | WaiveOff                      |
      | 3         | Systems Integration Fee#Check Production & Positive Pay Set-Up with FBO | 150000#100000,155000#105000,1000#100$5500#1500,100#90,100#50 | ACH Basic Transaction Fee#On  |
      | 2         | Systems Integration Fee#Check Production & Positive Pay Set-Up with FBO | 100000#90000,1200#300$6500#2500,200#90                       | ACH Basic Transaction Fee#Off |


  #CPAY-821
  @Regression @PayCRMGeneral @CPAY-821
  Scenario: PayCRM Modules:General - Suppliers: Validate Case Activities, Cases, Address, Contacts, Payment List, Payment Notes and Additional Information
    Given User login to the "PayCRM" application using "qaDetails" credentials
    And User validates tabs under selected suppliers by using query "Supplier_AllRequiredTabs"

  @Regression @PayCRMGeneral @CPAY-793
  Scenario: PayCRM Modules:General - Suppliers: Suppliers->Validations tab:Profile Authorization Form - Path
    Given User login to the "PayCRM" application using "qaDetails" credentials
    Then User navigates and select the general suppliers using the query "GeneralSuppliers_Validations"
    And User creates "New" Universal Payment Process and validates the same
      | Supplier | AccountNumber | Method  | Address                    | ServiceInformation                    | AdditionalInformation                         | Steps                                                            | Notes                            | SecurityQuestions                   |
      | Green    |               | Website | 123 Main Street California | Visa#3216549877412,RuPay#987654321234 | Test1#Additional Info1,Test2#Additional Info2 | Open Browser#Login with valid Credential#Perform Payment process | Payment Process automation notes | Question1#Answer1,Question2#Answer2 |


  #CPAY-827
  @Regression @PayCRMGeneral @CPAY-827
  Scenario: PayCRM Modules:General - Companies: Prospect Company - Solution Design - Attachments: Test all attachments
    Given User login to the "PayCRM" application using "qaDetails" credentials
    Then User Validates Generate Provider Files
    And User perform Import Suppliers and validates the same
      | Name         | Wave | Version | Status  | Priority | FileToUpload             |
      | AutoSupplier |      | 1       | Initial | Low      | ImportSuppliersFile.xlsx |
    And User perform Import Matches and validates the same
      | Name        | Version | Status | Priority | FileToUpload           |
      | AutoMatches | 1       | Active | High     | ImportMatchesFile.xlsx |
    Then User perform "Add" attachment under other attachments section
      | Name    | Description      | AttachmentType | Status | FileToUpload                 |
      | AutoNew | Other Attachment | Document       | Active | OtherAttachmentDocument.xlsx |
    Then User perform "Edit" attachment under other attachments section
      | Name     | Description      | AttachmentType       | Status | FileToUpload                   |
      | AutoEdit | Other Attachment | Promotional Material | Active | OtherAttachmentPromotional.png |
    And User deletes the attachment from Other attachment section


  #CPAY-753
  @Regression @PayCRMGeneral @CPAY-753
  Scenario: PayCRM Modules:General - Companies: Prospect Company - Test Account Setup Form
    Given User login to the "PayCRM" application using "qaDetails" credentials
    Then User enters "General" Information details
      | CompanyName | Industry                                         | NumberOfLocations | BriefHistory                                                                                                      | CurrentStage          | Date |
      |             | Customer supp Industry#Customer Company Industry | 7#10              | This is the brief history created at the regression time#This is the brief history created at the automation time | Final Meeting#Pricing |      |
    Then User enters "contact" information details
      | ContactName | Title      | ContactSigner | DecisionMaker |
      | AutoContact | Automation | true          | true          |
    Then User enters "Current Payment Landscape" details
      | PayablesVolume | SpendFrequency | VirtualCardProgram | FinancialInstitution                   | CurrentRebate | RebateFrequency | CheckPercentage | ACHPercentage | EnhancedACHPercentage | WirePercentage | PaymentRunFrequency | PaymentRunDays | FullTimeEmployeeAccountsPayable | TradeDiscounts | ParticularTradeDiscount |
      |                | Annual         | No                 | Ally Bank#BMO Harris#Deutsche Bank USA | 1             | 1               | 70              | 15            | 1                     | 1              | Weekly              | Friday         | 1000                            | No             | 2.5%                    |
    Then User enters "Additional Details" details
      | BankingGroupMember                  | DisbursementAccounts | ERP | BusinessLifecycle | TreasurySolution | PurchasingCards | CulturalDisposition | Notes              | NextDetails                       |
      | Ally Bank#BB&T Corp.#East West Bank | 4                    | 1   | Test              | No               | Yes             | Yes                 | Automation Testing | Automation notes for Next Meeting |


   #CPAY-767
  @Regression @PayCRMGeneral @CPAY-767
  Scenario: [General - Suppliers - Supplier - Payment Process] Universal Payment Process
    Given User login to the "PayCRM" application using "qaDetails" credentials
    And User navigates and select the general suppliers using the query "GeneralSupplierID"
    Then User creates new payment process record from payment process tab
      | Universal | Client-VendorID | Account_Number | Method | Address | ServiceName | ServiceValue | AdditionalName | AdditionalValue | AdditionalStep | AdditionalNotes |
      | yes       |                 | 12345          |        |         |             | Testing      |                |                 |                | Testing         |
    And User verify that "Payment Processes" tab grid first record is present
      | Client                    | Vendor IDs | Method | Account Numbers | Address |
      | Universal Payment Process |            | IVR    | 12345           | Testing |


  #CPAY-1783, CPAY-1782
  @Regression @PayCRMGeneral @CPAY-1783 @CPAY-1782
  Scenario: Verify Go-Live- Generate Preview and Show Preview functionality
    Given User login to the "PayCRM" application using "qaDetails" credentials
    And User navigate to "Preview Go Live" ribbon from prospect companies
    Then User click on generate and show preview and verify cards data


  #CPAY-1734 #CPAY-1735 #CPAY-1748 #CPAY-1749 #CPAY-1750 #CPAY-1751
  @Regression @PayCRMGeneral @CPAY-1734 @CPAY-1735 @CPAY-1748 @CPAY-1749 @CPAY-1750 @CPAY-1751
  Scenario: Verify Go-Live- cards and general functionality
    Given User login to the "PayCRM" application using "qaDetails" credentials
    And User navigate to "Preview Go Live" ribbon from prospect companies
    Then User click on each cards and verify each cards


  #CPAY-849 & CPAY-848
  @Regression @PayCRMGeneral @CPAY-849 @CPAY-848
  Scenario: PayCRM Modules:General - Companies: [General] Companies - Client Company - Preferences
    Given User login to the "PayCRM" application using "qaDetails" credentials
    And User updates the Preferences tab values for the client company
      | RequiresTINValidation | BypassVirtualCard | AutoEnrollVirtualCard | ProgramName    | ProgramEmail                               | ClientMicrosite                                        | EmailTemplate | PaymentVoidContactEmail | DefaultPaymentQuestionEmails |
      | True#False            | True#False        | True#False            | TestAutomation | ggudi@corcentric.com#asingh@corcentric.com | https://corcentric.com/enroll/accelerate360.regression |               |                         |                              |
    Then User perform assign and remove the work queue
      | QueueName   | AddUsers | removeUsers |
      | Enrollments |          |             |


  #CPAY-1690, CPAY-1669 & CPAY-1801
  @Regression @PayCRMGeneral @CPAY-1690 @CPAY-1801
  Scenario: PayCRM Modules:General - Companies: [Prospect] Go-Live UI and Post Results validations
    Given User login to the "PayCRM" application using "qaDetails" credentials
    Then User validates the GoLive UI Functionality and post result validations


  #CPAY-3359 & CPAY-3404
  @Regression @Smoke @PayCRMGeneral @CPAY-3359 @CPAY-3404
  Scenario: PayCRM Modules:General-Companies: Verify Merged Supplier tab and Show/Hide Merged Supplier tab for Prospect company
    Given User login to the "PayCRM" application using "qaDetails" credentials
    Then User create the New Prospect company and validate that Merged Suppliers tab is available under Solution Design
      | Name    | Tickler | Website                          | PaynetCompanyID | SalesforceID | Phone      | Fax        | ZipCode    | Parent        | Locale | Language | OriginationSource | CompanyType | Status | Priority |
      | newAuto |         | https://staging.vendorinroll.com |                 |              | 9900887766 | 9090909090 | 55555-5555 | DoAll Company | US     | ENGLISH  | Testing           | Prospect    | Active | Low      |
    And User validates Merged Suppliers tab can be shown or hidden via the internal setup tab


  #CPAY-3448 & CPAY-3459
  @Regression @Smoke @PayCRMGeneral @CPAY-3448 @CPAY-3459
  Scenario: PayCRM Modules:General-Companies: Verify the functionality of automatic Merge Supplier & undo merge suppliers functionality for Prospect company
    Given User login to the "PayCRM" application using "qaDetails" credentials
    Then User create the New Prospect company to validate the automatic Merge Suppliers functionality
      | Name            | Tickler | Website                          | PaynetCompanyID | SalesforceID | Phone      | Fax        | ZipCode    | Parent        | Locale | Language | OriginationSource | CompanyType | Status | Priority |
      | newAutoProspect |         | https://staging.vendorinroll.com |                 |              | 9900887766 | 9090909090 | 55555-5555 | DoAll Company | US     | ENGLISH  | Testing           | Prospect    | Active | Low      |
    And User imports the suppliers which are having duplicate supplier details, validate the automatic merge supplier and Undo merged suppliers functionality
      | Name         | Wave | Version | Status  | Priority | FileToUpload                          |
      | AutoSupplier |      | 1       | Initial | Low      | ProspectMergeTestingDuplicateMVF.xlsx |
    Then User create the New Prospect company to validate the automatic Merge Suppliers functionality
      | Name            | Tickler | Website                          | PaynetCompanyID | SalesforceID | Phone      | Fax        | ZipCode    | Parent        | Locale | Language | OriginationSource | CompanyType | Status | Priority |
      | newAutoProspect |         | https://staging.vendorinroll.com |                 |              | 9900887766 | 9090909090 | 55555-5555 | DoAll Company | US     | ENGLISH  | Testing           | Prospect    | Active | Low      |
    And User imports the suppliers which are having unique supplier details, validate that the automatic merging of suppliers should not happen
      | Name         | Wave | Version | Status  | Priority | FileToUpload                       |
      | AutoSupplier |      | 1       | Initial | Low      | ProspectMergeTestingUniqueMVF.xlsx |


  #CPAY-4063
  @Regression @PayCRMGeneral @CPAY-4063
  Scenario: PayCRM Modules:General-Supplier-SupportCase: Validate support cases tab in supplier
    Given User login to the "PayCRM" application using "qaDetails" credentials
    Then User verify support case grid and related data from supplier detail page


  #CPAY-4268, CPAY-4269 & CPAY-4270
  @Regression @PayCRMGeneral @CPAY-4268 @CPAY-4269 @CPAY-4270
  Scenario: PayCRM Modules:General-Companies: Validate hide/un-hide the Payment Score, Risk Score tiles & Corcentric Features table in the Prospect Module
    Given User login to the "PayCRM" application using "qaDetails" credentials
    Then User opens "New" prospect company and Validates the preferences option to show or hide Payment Score, Risk Score tiles & Corcentric Features tiles and tables
      | Name            | Tickler | Website                          | PaynetCompanyID | SalesforceID | Phone      | Fax        | ZipCode    | Parent        | Locale | Language | OriginationSource | CompanyType | Status | Priority |
      | newAutoProspect |         | https://staging.vendorinroll.com |                 |              | 9900887766 | 9090909090 | 55555-5555 | DoAll Company | US     | ENGLISH  | Testing           | Prospect    | Active | Low      |
    Then User opens "Existing" prospect company and Validates the preferences option to show or hide Payment Score, Risk Score tiles & Corcentric Features tiles and tables
      | Name            | Tickler | Website                          | PaynetCompanyID | SalesforceID | Phone      | Fax        | ZipCode    | Parent        | Locale | Language | OriginationSource | CompanyType | Status | Priority |
      | newAutoProspect |         | https://staging.vendorinroll.com |                 |              | 9900887766 | 9090909090 | 55555-5555 | DoAll Company | US     | ENGLISH  | Testing           | Prospect    | Active | Low      |


  #CPAY-4286
  @Regression @PayCRMGeneral @CPAY-4286
  Scenario: PayCRM Modules:General-Companies: Verify default benchmark values in the Prospect Module → Internal Setup tab → Expected section
    Given User login to the "PayCRM" application using "qaDetails" credentials
    Then User creates new prospect company and validates the default benchmark values under Internal setup-->Expected section of Prospect company
      | Name            | Tickler | Website                          | PaynetCompanyID | SalesforceID | Phone      | Fax        | ZipCode    | Parent        | Locale | Language | OriginationSource | CompanyType | Status | Priority |
      | newAutoProspect |         | https://staging.vendorinroll.com |                 |              | 9900887766 | 9090909090 | 55555-5555 | DoAll Company | US     | ENGLISH  | Testing           | Prospect    | Active | Low      |


  #CPAY-4263, CPAY-4319 & CPAY-4320
  @Regression @PayCRMGeneral @CPAY-4263 @CPAY-4319 @CPAY-4320
  Scenario: PayCRM Modules:General-Companies: Validate hide/un-hide the Total Cost & Net benefits from the Optimized Program Cost table in the Prospect Module
    Given User login to the "PayCRM" application using "qaDetails" credentials
    Then User opens "New" prospect company and Validates the preferences option to hide or un-hide the Total Cost & Net benefits from the Optimized Program Cost table
      | Name            | Tickler | Website                          | PaynetCompanyID | SalesforceID | Phone      | Fax        | ZipCode    | Parent        | Locale | Language | OriginationSource | CompanyType | Status | Priority |
      | newAutoProspect |         | https://staging.vendorinroll.com |                 |              | 9900887766 | 9090909090 | 55555-5555 | DoAll Company | US     | ENGLISH  | Testing           | Prospect    | Active | Low      |
    Then User opens "Existing" prospect company and Validates the preferences option to hide or un-hide the Total Cost & Net benefits from the Optimized Program Cost table
      | Name            | Tickler | Website                          | PaynetCompanyID | SalesforceID | Phone      | Fax        | ZipCode    | Parent        | Locale | Language | OriginationSource | CompanyType | Status | Priority |
      | newAutoProspect |         | https://staging.vendorinroll.com |                 |              | 9900887766 | 9090909090 | 55555-5555 | DoAll Company | US     | ENGLISH  | Testing           | Prospect    | Active | Low      |
    

  #CPAY-4323
  @Regression @PayCRMGeneral @CPAY-4323
  Scenario: PayCRM Modules:General-Companies: Validate update/replace the Network Name & TIN Match Calculations
    Given User login to the "PayCRM" application using "qaDetails" credentials
    Then User create new prospect company and prepare the MVF file as required
      | Name            | Tickler | Website                          | PaynetCompanyID | SalesforceID | Phone      | Fax        | ZipCode    | Parent        | Locale | Language | OriginationSource | CompanyType | Status | Priority |
      | newAutoProspect |         | https://staging.vendorinroll.com |                 |              | 9900887766 | 9090909090 | 55555-5555 | DoAll Company | US     | ENGLISH  | Testing           | Prospect    | Active | Low      |
    And User imports the suppliers and Validates the update or replace the Network Name & TIN Match Calculations
      | Name         | Wave | Version | Status  | Priority | FileToUpload                        |
      | AutoSupplier |      | 1       | Initial | Low      | Test_4055_NetworkName_TINMatch.xlsx |

  @RegressionTest
  Scenario: Login to application
    Given User launch the browser
#    Then User login to the {string} application using {string} credentials
#    Then Navigate to General Page