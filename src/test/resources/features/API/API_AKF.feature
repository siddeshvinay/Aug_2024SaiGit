Feature: PayCRM: API Automation - AFK API Regression Test Scenario's

  #CPAY-2981, CPAY-2982, CPAY-2983 & CPAY-2984 (Completed)
  @RegressionAPI @CPAY-2981 @CPAY-2982 @CPAY-2983 @CPAY-2984
  Scenario: API Test - Verify AKF MSV scenario for GET payment info, create Supplier, update payment info and cancel the payment
    Given User validates AKF MSV scenario for create supplier, update payment info and cancel the payment using GET, POST & PUT methods
      |InvalidPaymentID|InputFileForPOST               |InputFileForPOSTWithInvalidZopPostal               |InputFileForPUT                  |InvalidClientCompanyID|InvalidZipPostal|InvalidCampaignID|InvalidTaxID|
      |267270162-304ss |AKF_MSV_CreateSupplier_POST.txt|AKF_MSV_CreateSupplier_POST _WithInvalidZipCode.txt|AKF_MSV_UpdatePaymentInfo_PUT.txt|10000000              |28262ABCDEF     |1111111111       |            |
