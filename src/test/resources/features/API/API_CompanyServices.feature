Feature: PayCRM: API Automation - Company Services API Automation Regression Test Scenario's

  #CPAY-3110, CPAY-3111, CPAY-3112, CPAY-3113 & CPAY-3114 (Completed)
  @RegressionAPI @CPAY-3110 @CPAY-3111 @CPAY-3112 @CPAY-3113 @CPAY-3114
  Scenario: API Test - Verify POST, PUT and GET methods for Company Service Marketing positive and negative scenarios
    Given User validates Company Services Marketing Create, Update and Get operations
      |InputFileForPOST                 |InputFileForPUT                 |
      |companyServicesMarketing_POST.txt|companyServicesMarketing_PUT.txt|


  #CPAY-3318    (Completed)
  @RegressionAPI @CPAY-3318
  Scenario: API Test - Verify GET methods for Company Service Contact for both positive and negative scenarios
    Given User validates Company Services contact Get operations
      |validType |invalidType|
      |validation|payCRM     |


  #CPAY-3029  (Automated)
  @RegressionAPI @CPAY-3029
  Scenario: API Test - Verify GET methods for Company Service Support-Case for both positive and negative scenarios
    Given User validates Company Services Support-Case Get operations
      |validCompanyID |invalidCompanyID|
      |               |                |


  #CPAY-3322, CPAY-3326, CPAY-3328, CPAY-3329, CPAY-3350, & CPAY-3351   (Completed) Defect Exist for CPAY-3322, CPAY-3326, CPAY-3329
  @RegressionAPI @CPAY-3322 @CPAY-3326 @CPAY-3328 @CPAY-3329 @CPAY-3350 @CPAY-3351
  Scenario: API Test - Verify GET & PUT methods for Company Management Service SupplierLinks for both positive and negative scenarios
    Given User validates Company Management Services SupplierLinks Get and PUT operations
      |invalidLinkID1|invalidLinkID2|invalidClientCompanyID|invalidSupplierCompanyID|invalidEntityIdentifier|inputFileForPUT                      |
      |111112        | abc          |123456789             |123456789               |null                   |companyServicesSupplierLinkID_PUT.txt|



  #CPAY-3321  (Completed)
  @RegressionAPI @CPAY-3321
  Scenario: API Test - Verify - GET - SupplierSearch - Supplier Search Endpoint
    Given User validates Supplier Search API using the Get operations
      |InvalidSupplierName|
      |testAutoSupplier123|


  #CPAY-3319  (Completed)
  @RegressionAPI @CPAY-3319
  Scenario: API Test - Verify GET - Campaign - GetCampaignByID
    Given User validates GET Campaign By ID API
      |InvalidCampaignID |
      |70170000000ACKgAA0|


  #CPAY-2622 & CPAY-2623 (completed)
  @RegressionAPI @CPAY-2622 @CPAY-2623
  Scenario: API Test - Verify Get PayNet user information based on valid PayNetID using GET method
    Given User validates Get PayNet user information based on valid PayNetID using GET method
      |InvalidCompanyID |
      |12               |


  #CPAY-3476 (Asked Query)
  #Need a DB query to get the input params for json request body
  #DB query to validate the response
  @RegressionAPI @CPAY-3476
  Scenario: API Test - Verify Company services Supplier Link Clone API by using POST method
    Given User validates Company services-Supplier Link Clone API by using POST method
      |InputFileForPOST                          |
      |CompanyServices_SupplierLinkClone_POST.txt|

