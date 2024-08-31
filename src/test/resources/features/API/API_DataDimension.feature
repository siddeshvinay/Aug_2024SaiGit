Feature: PayCRM: API Automation - Data Dimension API Regression Test Scenario's

  #CPAY-3866, CPAY-3867 & CPAY-3893 (Query Asked)
  #Need a PaymentID which doesnot belongs to VCA And status should be Voided, Partially Voided, Expired, Processed cards
  @RegressionAPI @CPAY-3866 @CPAY-3867 @CPAY-3893
  Scenario: API Test - Verify MSV - Data Dimension - Block & unblock the Payment API
    Given User validates MSV - Data Dimension - Block & unblock Payment API by using PUT method and also validate Poll & Poll By Date API for the same using GET method
      |InputFileForPost_Block|InputFileForPost_Unblock|InvalidPaymentID|
      |blockPayments_PUT.txt |unblockPayments_PUT.txt |120000000-123   |


  #CPAY-3986 (Completed)
  @RegressionAPI @CPAY-3986 @CPAY-4871
  Scenario: API Test - Data Dimension - Get Transaction History API by using GET Method
    Given User validates Data Dimension - Get Transaction History API by using GET methods
      |InvalidPaymentID|
      |000000000-000   |


  #CPAY-3970 (Completed)
  @RegressionAPI @CPAY-3970
  Scenario: API Test - Data Dimension - Block-Refund Payment API by using PUT Method
    Given User validates Data Dimension - Block-Refund Payment API by using PUT methods
      |InputFileForPUT       |InvalidPaymentID|
      |blockAndRefund_PUT.txt|000000000-000   |


  #CPAY-3949 (Completed)
  @RegressionAPI @CPAY-3949
  Scenario: API Test - Data Dimension - Enable multi-swipe API by using PUT Method
    Given User validates Data Dimension - Enable multi-swipe API by using PUT methods
      |InputFileForPUT         |
      |enableMultiSwipe_PUT.txt|


  #CPAY-4870
  @RegressionAPI @CPAY-4870
  Scenario: API Test - Data Dimension - Verify CardInfo API using GET Method
    Given User validates Data Dimension - CardInfo API by using PUT methods
      | InvalidPaymentID |
      | 00000000-100     |