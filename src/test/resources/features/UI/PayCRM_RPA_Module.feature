Feature: PayCRM RPA Module Regression scenarios

    #CPAY-2223 CPAY-2224
  @RegressionNo @RPA @CPAY-2223 @CPAY-2224
  Scenario: PayCRM Modules:RPA - RPA Tile, Grid and Reassign functionality verification
    Given User login to the "PayCRM" application using "qaDetails" credentials
    Then User navigates to RPA Case grid
    And User verify Reassign functionality

  #Robot
  @RegressionNo @RPA
  Scenario: Robot : User perform validations for RPA tile
    Given User login to the "PayCRM" application using "qaDetails1" credentials
    And RPA will validate the case status and based on the same it perform Success or Fail activities

  #CPAY-2187
  @RegressionNo @RPA @CPAY-2187
  Scenario: RPA-Cases-Fail button structure and functionality
    Given User login to the "PayCRM" application using "qaDetails1" credentials
    Then Navigate to RPA case number and validate the Fail button structure and functionality

  #CPAY-2270
  @RegressionNo @RPA @CPAY-2270
  Scenario: RPA - Testing report and permissions around report
    Given User login to the "PayCRM" application using "qaDetails1" credentials
    Then User navigates to reports and validates the permissions around the report
    And User exports the RPA enabled Suppliers report and validate the same

  #CPAY-2264 #CPAY-2179 #CPAY-2178
  @RegressionNo @RPA @CPAY-2264 @CPAY-2179 @CPAY-2178
  Scenario: PayCRM Modules:RPA - Verify RPA Case permission & combination
    Given User login to the "PayCRM" application using "qaPermissionDetails" credentials
    Then User navigates to edit profile page then update permission and verify grid should "be" visible
      | RPA Admin      | true |
      | Internal Admin | true |
    Then User navigates to edit profile page then update permission and verify grid should "not be" visible
      | RPA Admin      | false |
      | Internal Admin | true  |
    And User navigates to edit profile page then update permission and verify only RPA grid should visible
      | RPA Admin      | true  |
      | Internal Admin | false |
      | Super Admin    | false |
      | Normal User    | false |

  #CPAY-2221 #CPAY-2225
  @RegressionNo @RPA @CPAY-2221 @CPAY-2225
  Scenario: PayCRM Modules:RPA - Verify Success with reassign functionality
    Given User login to the "PayCRM" application using "qaDetails" credentials
    Then User navigates to RPA Case grid and perform success flow with reassign validation

  #CPAY-2180 #CPAY-2183 #CPAY-2184 #CPAY-2190
  @RegressionNo @RPA @Bug_CPAY-2584
  Scenario: PayCRM Modules:RPA - Verify RPA Section under Client Link Information tab from supplier
    Given User login to the "PayCRM" application using "qaDetails" credentials
    Then User update rpa permission for clientLinkInformation and verify RPA enable section should "be" visible
      | RPA Admin | true |
    Then User verify RPA Enable section change should saved properly
    Then User edit "VendorID=Blank" to the existing Client Link Information
    Then User edit "PayNetLinkID=Blank" to the existing Client Link Information
    Then User edit "Status=Inactive" to the existing Client Link Information
    Then User edit "SupplierType=Duplicate" to the existing Client Link Information
    Then User update rpa permission for clientLinkInformation and verify RPA enable section should "not be" visible
      | RPA Admin | false |