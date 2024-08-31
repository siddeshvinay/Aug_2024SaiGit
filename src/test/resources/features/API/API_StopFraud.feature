Feature: PayCRM: API Automation - Stop Fraud API Regression Test Scenario's
  PayCRM: API Automation - Enrollment Services API Regression Test Scenario's

  #CPAY-3020    (Completed)
  @RegressionAPI @CPAY-3020 @APITest123
  Scenario: API Test - Verify Stop Fraud - GET Company View
    Given User validates StopFraud GET Company View API
      | BlankCompanyID |
      | SG             |

  #CPAY-2878    (Completed)
  @RegressionAPI @CPAY-2878
  Scenario: API Test - Verify Stop Fraud- GET - Poll and poll by date
    Given User validates StopFraud Poll and poll by date GET API
      | InvalidStartDate |
      | xyz              |

  #CPAY-2055    (Completed)
  @RegressionAPI @CPAY-2055
  Scenario: API Test - Verify Attach File API using POST method
    Given User validates Attach File API using POST method
      | fileToAttach                |
      | CompanyAttachment_dummy.pdf |

  #CPAY-2067    (Completed)
  @RegressionAPI @CPAY-2067
  Scenario: API Test - Verify Stop Fraud get Details By Section API using GET method
    Given User validates Stop Fraud get Details By Section API using GET method
      | fileToAttach                |
      | CompanyAttachment_dummy.pdf |

  #CPAY-3024 & CPAY-1716 (completed)
  @RegressionAPI @CPAY-3024 @CPAY-1716
  Scenario: API Test - Verify Stop Fraud auto validate and OFAC auto validate API by using PUT method
    Given User validates Stop Fraud auto validate and OFAC auto validate API using PUT method
      | InputFileForPUT                    | InputFileForPut_AutoValidate   |
      | stopFraud_OFACAutoValidate_PUT.txt | stopFraud_AutoValidate_PUT.txt |

  #CPAY-1674 (Completed)
  @RegressionAPI @CPAY-1674
  Scenario: API Test - Verify Stop Fraud validate-tin-manual API by using PUT method
    Given User validates Stop Fraud validate-tin-manual API using PUT method
      | InputFileForPUT                     |
      | stopFraud_ManualTINValidate_PUT.txt |

  #CPAY-3023 (completed)
  @RegressionAPI @CPAY-3023
  Scenario: API Test - Verify Stop Fraud Auto Validate Address API by using PUT method
    Given User validates Stop Fraud Auto Validate Address API using PUT method
      | InputFileForPUT                       |
      | stopFraud_AddressAutoValidate_PUT.txt |

  #CPAY-2048 & CPAY-2050 (Completed)
  @RegressionAPI @CPAY-2048 @CPAY-2050
  Scenario: API Test - Verify Stop Fraud Payment Info Hold and Release API by using PUT method
    Given User validates Stop Fraud Payment Info Hold & Release API using PUT method
      | InputFileForPUT                             |
      | stopFraud_PaymentInfoHoldAndRelease_PUT.txt |

  @RegressionAPI @CPAY-1681
  Scenario: API Test - Verify Stop Fraud validate-tin-auto API by using PUT method
    Given User validates Stop Fraud validate-tin-auto API using PUT method
      | InputFileForPUT                   | InvalidCompanyID |
      | stopFraud_ValidateTinAuto_PUT.txt | 001000011        |

  @RegressionAPI @CPAY-4674
  Scenario: API Test - Verify Stop Fraud Get Audio Info API by using GET method
    Given User validates Stop Fraud Get Audio Info API using GET method
      | fileToAttach                |
      | CompanyAttachment_dummy.pdf |

  @RegressionAPI @CPAY-4683
  Scenario: API Test - Verify Stop Fraud polling endpoints with notes added API by using GET method
    Given User validates Stop Fraud polling endpoints with notes added API using GET method
      | InputParamsForGET |
      |                   |

  @RegressionAPI @CPAY-4692
  Scenario: API Test - Verify Stop Fraud Clear Unable To Validate API by using PUT method
    Given User validates Stop Fraud Clear Unable To Validate API using PUT method
      | InputFileForPUT                         |
      | stopFraud_ClearUnableToValidate_PUT.txt |