Feature: PayNET: API Automation - PayNET API Regression Test Scenario's

  #CPAY-1628
  @RegressionAPIPending @CPAY-1628
  Scenario: API Test - Verify Update offer for Pending and Approved Links, ACH to VCA & VCA to ACH using POST Method
    Given User validates Update offer for "Pending" Links form "ACH" to "VCA" using POST Method
      |InputFileForPOST_Pending_ACH_VCA           |
      |updateOfferForPendingLinks_ACH_VCA_POST.txt|
    And User validates Update offer for "Pending" Links form "VCA" to "ACH" using POST Method
      |InputFileForPOST_Pending_VCA_ACH           |
      |updateOfferForPendingLinks_VCA_ACH_POST.txt|
    And User validates Update offer for "Approved" Links form "VCA" to "ACH" using POST Method
      |InputFileForPOST_Approved_VCA_ACH           |
      |updateOfferForApprovedLinks_VCA_ACH_POST.txt|
    And User validates Update offer for "Approved" Links form "ACH" to "VCA" using POST Method
      |InputFileForPOST_Approved_ACH_VCA            |
      |updateOffersForApprovedLinks_ACH_VCA_POST.txt|
