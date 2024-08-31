Feature: PayCRM: API Automation - Manage Payment API Regression Test Scenario's

  #CPAY-3134, CPAY-3136, CPAY-3137    (Completed)
  @RegressionAPI @CPAY-3134 @CPAY-3136 @CPAY-3137
  Scenario: API Test - Verify POST, PUT and GET methods for Manage Payments scenario
    Given User validates Manage Payments Create, Update and Get operations
      | fileToUpload                | inputFileForPUT        | validTypeID | invalidTypeID | InvalidPaymentID |
      | CompanyAttachment_dummy.pdf | managePayments_PUT.txt | 256         | 111           | 1000000004-0001  |

  #CPAY-1194   (Completed)
  @RegressionAPI @CPAY-1194
  Scenario: API Test - Verify Managed Payments - Update proxy number PUT method
    Given User validates Update Proxy Number API using PUT method
      | inputFileForPUT           |
      | updateProxyNumber_PUT.txt |

  #CPAY-1208   (Completed)
  @RegressionAPI @CPAY-1208
  Scenario: API Test - Verify Managed Payments - Return Payment statuses by GET method
    Given User validates Return Payment Status by GET method
      | paymentTypeID | statusID | InputFileForPUT             |
      | 4             | 13       | returnPaymentStatus_PUT.txt |

  #CPAY-1245   (Completed)
  @RegressionAPI @CPAY-1245
  Scenario: API Test - Verify Managed Payments - Update MultiSwipe using PUT method
    Given User validates Update Multi-swipe API by using PUT method
      | inputFileForPUT          |
      | updateMultiSwipe_PUT.txt |

  #CPAY-1264   (Completed)
  @RegressionAPI @CPAY-1264
  Scenario: API Test - Verify Managed Payments - View Transaction History using GET method
    Given User validates View Transaction History API by using GET method
      | invalidPaymentId |
      | 121323           |

  #CPAY-1110   (Completed)
  @RegressionAPI @CPAY-1110
  Scenario: API Test - Verify Managed Payments - Poll by date & Poll using GET method
    Given User validates Payments Poll and Poll By Date API by using GET method
      | inputFileForPUT |
      | noData          |

  #CPAY-1076 & CPAY-1077 (completed)
  @RegressionAPI @CPAY-1076 @CPAY-1077
  Scenario: API Test - Verify Managed Payments - Search Payment ID & Get Payment Details by using GET method
    Given User validates Search payments ID & Get Payments Details API by using GET method
      | inputFileForPUT |
      | noData          |

  #CPAY-1080 & CPAY-1083 (Completed)
  @RegressionAPI @CPAY-1080 @CPAY-1083
  Scenario: API Test - Verify Managed Payments - PaymentFIle ID & Bank File ID for download scenario by using GET method
    Given User validates PaymentFIle ID & Bank File ID for download scenario API by using GET method
      | inputFileForPUT |
      | noData          |

  #CPAY-1081 & CPAY-1084 (completed)
  @RegressionAPI @CPAY-1081 @CPAY-1084
  Scenario: API Test - Verify Managed Payments - Bank File And Bank File Receipts API by using POST method
    Given User validates Bank File And Bank File Receipts API by using POST method
      | inputFileForPOST_BankFileReceipt | inputFileForPOST_BankFile |
      | bankFileReceipts_POST.txt        | getBankFiles_POST.txt     |

  #CPAY-1078 & CPAY-1079 (Completed)
  @RegressionAPI @CPAY-1078 @CPAY-1079
  Scenario: API Test - Verify Managed Payments - Get Payment Files Details & List by Bank ID by using GET method
    Given User validates Payment Files Details & Payment Files List by Bank ID API by using GET method
      | inputFileForPUT |
      | noData          |

  #CPAY-1309 (Completed)
  @RegressionAPI @CPAY-1309
  Scenario: API Test - Verify Managed Payments - StopCheck API by using PUT method
    Given User validates Payment - StopCheck API by using PUT method
      | inputFileForPUT      |
      | stopCheckAPI_PUT.txt |

  #CPAY-1182 (Completed)
  @RegressionAPI @CPAY-1182
  Scenario: API Test - Verify Managed Payments - Update Fees API by using PUT method
    Given User validates Managed Payment - Update Fees API by using PUT method
      | inputFileForPUT                  |
      | managePayments_UpdateFee_PUT.txt |

  #CPAY-1299 (Completed)
  @RegressionAPI @CPAY-1299
  Scenario: API Test - Verify Managed Payments - Defund API by using PUT method
    Given User validates Managed Payment - Defund API by using PUT method
      | inputFileForPUT                |
      | managedPayments_Defund_PUT.txt |

  #CPAY-2091 (Completed)
  @RegressionAPI @CPAY-2091
  Scenario: API Test - Verify Managed Payments - Bank Files Filter by statuses API by using POST method
    Given User validates Managed Payment - Bank Files Filter by statuses API by using POST method
      | inputFileForPOST                    |
      | bankFiles_FilterByStatuses_POST.txt |

  #CPAY-3891 (Completed)      Defect: https://determine.atlassian.net/browse/CPAY-4377
  @RegressionAPI @CPAY-3891
  Scenario: API Test - Verify Managed Payments - MSV - Pending Authorization Date by using GET method
    Given User validates MSV Pending Authorization Date scenario API by using GET method
      | invalidPaymentID |
      | 000000000-000    |

  #CPAY-2097   (Completed)
  @RegressionAPI @CPAY-2097
  Scenario: API Test - Verify Managed Payments - Get Bank File - Filter-By-PaymentFiles API by using GET method
    Given User validates Get Bank Files - Filter By Payment File ID API by using GET method
      | invalidPaymentId |
      | 121323           |

  #CPAY-1259   (Completed)
  @RegressionAPI @CPAY-1259
  Scenario: API Test - Verify Managed Payments - Reissue API by using PUT method
    Given User validates Manage Payments Reissue API by using PUT method
      | inputFileForPUT                | invalidPaymentId |
      | managePayments_Reissue_PUT.txt | 000000000-000    |

  #CPAY-603   (Completed)
  @RegressionAPI @CPAY-603
  Scenario: API Test - Managed Payments - Verify API to Get a list of bank files that match the given criteria
    Given User validates Manage Payments Get a list of bank files API by using POST method
      | inputFileForPOST             |
      | getListofBankFiles_POST .txt |

  #CPAY-4872
  @RegressionAPI @CPAY-4872
  Scenario: API Test - Managed Payments - Verify Poll By Date Time API for BankFile Receipts by using GET method
    Given User validates Manage Payments Get Poll By Date Time API for BankFile Receipts API by using GET method

  @RegressionAPI @CPAY-5053
  Scenario: API Test - Manage Payments - Verify user is able to receive notes inside remittance section for paymentId endpoint
    Given User validates notes section using paymentId endpoint

  @RegressionAPI @CPAY-5184
  Scenario: API Test - Manage Payments - Verify user is able to add payments in exception table
    Given User validates except endpoint using paymentId