Feature: PayCRM: API Automation - Enrollment Services API Regression Test Scenario's

  #CPAY-3381 & CPAY-3382 (completed)
  @RegressionAPI @CPAY-3381 @CPAY-3382
  Scenario: API Test - Verify Enrollment Services API for Search Global and Company Specific Suppliers
    Given User validates Enrollment Services API Search Global and Company Specific Suppliers functionalities
      |InvalidSupplierName|
      |invalidSupplier_   |


  #CPAY-3362, CPAY-3383, CPAY-3510, CPAY-3392, CPAY-3394, CPAY-3320 & CPAY-3443 (Asked the query)           defect: CPAY-4062 & CPAY-4104
  @RegressionAPI @CPAY-3362 @CPAY-3383 @CPAY-3510 @CPAY-3392 @CPAY-3394 @CPAY-3320 @CPAY-3443
  Scenario: API Test - Verify Enrollment Services API for create and Update Enrollment Cases for VCA & ACH
    Given User validates Enrollment Services API for creating and updating new Enrollment cases for VCA & ACH
      |InputFileForPOST_VCA           |InputFileForPOST_ACH           |InputFileForPUT_UpdateEnrollmentVCA |InputFileForPUT_UpdateEnrollmentACH |InputFileForPUT_EnrollCase           |InputFileForPOST_createAndSearch       |
      |enrollmentServices_VCA_POST.txt|enrollmentServices_ACH_POST.txt|updateEnrollmentServices_VCA_PUT.txt|updateEnrollmentServices_ACH_PUT.txt|enrollmentServices_EnrollCase_PUT.txt|supplierSearchCreateEnrollment_POST.txt|


  #CPAY-3883 (asked query)      //Parent JIRA Ticket: https://determine.atlassian.net/browse/CPAY-3807
  #Need DB query to validate the API response
  @RegressionAPI @CPAY-3883
  Scenario: API Test - Verify Enrollment Services - Notifications API by using POST method
    Given User validates Enrollment Services Notifications API by using POST method
      |InputFileForPOST                        |
      |enrollmentServicesNotifications_POST.txt|



  #CPAY-1319 (Completed)
  @RegressionAPI @CPAY-1319
  Scenario: API Test - Verify receive additional links to existing suppliers and new clients API by using POST method
    Given User validates receive additional links to existing suppliers and new clients API by using POST method
      |InputFileForPOST                          |
      |additionalLinks_ExistingSuppliers_POST.txt|