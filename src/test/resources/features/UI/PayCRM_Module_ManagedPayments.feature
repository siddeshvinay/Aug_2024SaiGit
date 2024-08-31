Feature: PayCRM Modules:Managed Payments - Regression Test Scenario's

  #CPAY-2292
  @Regression @PayCRMGeneralManagedPayments @CPAY-2292
  Scenario: PayCRM Modules : Managed Payments - Pending bank file grid and Bank file details page verification
    Given User login to the "PayCRM" application using "qaDetails" credentials
    And User navigates to Managed Payments->pending Bank Files and validate the Bank File Details
    And User navigates to Managed Payments->pending Bank Files and validate the Payment File ID Details


  #CPAY-1295, CPAY-1297, CPAY-1298 & CPAY-718, CPAY-4065, CPAY-4066
  @Regression @PayCRMGeneralManagedPayments @CPAY-1295 @CPAY-1297 @CPAY-1298 @CPAY-718 @CPAY-4065 @CPAY-4066
  Scenario: PayCRM Modules : Managed Payment - Actionable Void Request modal in PayCRM
    Given User login to the "PayCRM" application using "qaDetails" credentials
    And Managed Payment - Verify Payment Search with both valid and invalid payment ID
    Then User validates the Void Request Modal functionality for "Managed Payments" page
      | paymentID | AmountToVoid | ActionAfterVoid | LinkRevoked | Notes                         |
      |           | 1            | Other           | Yes         | Void Request Automation Notes |
    And User validates the Void Request Modal functionality for "Assisted Payment Services" page
      | paymentID | AmountToVoid | ActionAfterVoid | LinkRevoked | Notes                         |
      |           | 1            | Other           | Yes         | Void Request Automation Notes |


  #CPAY-1301, CPAY-1300 & CPAY-1303
  @Regression @PayCRMGeneralManagedPayments @CPAY-1301 @CPAY-1300 @CPAY-1303
  Scenario: PayCRM Modules : Managed Payments - Verify Payment Void Case Display functionality
    Given User login to the "PayCRM" application using "qaDetails" credentials
    Then User validates Payment Void Cases on Payment Details
      | paymentID     | AmountToVoid | ActionAfterVoid | LinkRevoked | Notes                         |
      | 213725395-300 | 1            | Other           | Yes         | Void Request Automation Notes |
    And Validate the Payment Void Case - Edit Case Details


  #CPAY-604
  @Regression @PayCRMGeneralManagedPayments @CPAY-604
  Scenario:  PayCRM Modules : Managed Payments - Verify Pending Bank Files Functionality
    Given User login to the "PayCRM" application using "qaDetails" credentials
    And User navigates to Manage Payment Pending Bank Files Page from module
    And User navigates to Bank File ID and verify new tab open with file download
    And User navigates to Payment File ID and verify new tab open with file download


  #CPAY-605
  @Regression @PayCRMGeneralManagedPayments @CPAY-605
  Scenario:  PayCRM Modules : Managed Payments - Verify Payment File Details and functionality
    Given User login to the "PayCRM" application using "qaDetails" credentials
    And User navigates to Manage Payment Pending Bank Files Page from module
    Then User navigates to Payment File ID and verify new tab with required field and download functionality


 #CPAY-606
  @Regression @PayCRMGeneralManagedPayments @CPAY-606
  Scenario:  PayCRM Modules : Managed Payments - [Managed Payments - Pending Bank Files] Verify Bank File Details page and functionality
    Given User login to the "PayCRM" application using "qaDetails" credentials
    And User navigates to Manage Payment Pending Bank Files Page from module
    Then User navigates to Bank File ID and verify new tab with required field, download and attachment functionality


  #CPAY-1305 & CPAY-1306
  @Regression @PayCRMGeneralManagedPayments @CPAY-1305 @CPAY-1306
  Scenario: PayCRM Modules : Managed Payments - Voids: Validate Void data grid and Void UI functionalities
    Given User login to the "PayCRM" application using "qaDetails" credentials
    Then User validates the void page data grid
    And User validates the Voids page data grid action icons


  #CPAY-742, CPAY-749 & CPAY-750
  @Regression @PayCRMGeneralManagedPayments @CPAY-742 @CPAY-749 @CPAY-750
  Scenario: PayCRM Modules : Managed Payment - Verify Payment details Page - Tab Section - Verify Activities & Attachments Activity
    Given User login to the "PayCRM" application using "qaDetails" credentials
    And User navigates to Managed Payments->Payment Search page, Create Log Activity & validate it under Activities tab
      | paymentID | Notes                         |
      |           | Log Activity Automation Notes |
    And User navigates to Managed Payments->Payment Search page, create & Delete the attachments under Attachments tab
      | Name          | Description                | AttachmentType | FileToUpload       |
      | AddAttachment | Managed Payment Attachment | Document       | SampleFileOne.docx |


  #CPAY-1168, CPAY-1169, CPAY-1170, CPAY-1171, CPAY-1172
  @Regression @PayCRMGeneralManagedPayments @CPAY-1168 @CPAY-1169 @CPAY-1170 @CPAY-1171 @CPAY-1172
  Scenario: PayCRM Modules : Managed Payment - Verify Proxy Button & Proxy Update Features
    Given User login to the "PayCRM" application using "qaDetails" credentials
    Then User search for PaymentID and verify Proxy Button should "be" visible
    Then User search for PaymentID and verify Proxy Button should "not be" visible


  #CPAY-699, CPAY-700, CPAY-702, CPAY-783 & CPAY-787, CPAY-3964, CPAY-3971
  @Regression @PayCRMGeneralManagedPayments @CPAY-699 @CPAY-700 @CPAY-702 @CPAY-783 @CPAY-787 @CPAY-3964 @CPAY-3971
  Scenario: PayCRM Modules : Managed Payment - AKF-Add a New transmission record -Buyer Account ID and Payee Account ID
    Given User login to the "PayCRM" application using "qaDetails" credentials
    And Managed Payment - Verify Transmission Files grid details
    Then User perform filed level validations for the mandatory fields under the Transmission form
      | ChooseProvider       | TransmissionTemplate | TransmissionType | AssociateWithClient | TemplateName | ChooseDrawdownAccount | DA_Account_BuyerID | DA_Account_ClientCode | DrawdownMethod | BuyerAccountID | PayeeAccountID | ChooseDisbursementAccount | DI_Account_BuyerID | DI_Account_ClientCode | DisbursementMethod           |
      | Cass Commercial Bank | 0150                 | Transfer         | Acculogix           | TemplateName | VPEN                  | 11111111           | 22222222              | 01 - Wire      |                |                | VPEN                      | 33333333           | 44444444              | 01 – Wire (initiated by CIS) |
    And AKF-Add a New Transmission record-Buyer Account ID and Payee Account ID Toggle "Off" and "no data" entered
      | ChooseProvider       | TransmissionTemplate | TransmissionType | AssociateWithClient | TemplateName | ChooseDrawdownAccount | DA_Account_BuyerID | DA_Account_ClientCode | DrawdownMethod | BuyerAccountID | PayeeAccountID | ChooseDisbursementAccount | DI_Account_BuyerID | DI_Account_ClientCode | DisbursementMethod           |
      | Cass Commercial Bank | 0150                 | Transfer         | Acculogix           | TemplateName | VPEN                  | 11111111           | 22222222              | 01 - Wire      | Off#NA         | Off#NA         | VPEN                      | 33333333           | 44444444              | 01 – Wire (initiated by CIS) |
    And AKF-Add a New Transmission record-Buyer Account ID and Payee Account ID Toggle "Off" and "data" entered
      | ChooseProvider       | TransmissionTemplate | TransmissionType | AssociateWithClient | TemplateName | ChooseDrawdownAccount | DA_Account_BuyerID | DA_Account_ClientCode | DrawdownMethod    | BuyerAccountID | PayeeAccountID | ChooseDisbursementAccount | DI_Account_BuyerID | DI_Account_ClientCode | DisbursementMethod           |
      | Cass Commercial Bank | 0150                 | Transfer         | Acculogix           | TemplateName | VPEN                  | 11111111           | 22222222              | 02 – Reverse Wire | Off#Buyer123   | Off#Payee123   | VPEN                      | 33333333           | 44444444              | 01 – Wire (initiated by CIS) |
    And AKF-Add a New Transmission record-Buyer Account ID and Payee Account ID Toggle "On" and "no data" entered
      | ChooseProvider       | TransmissionTemplate | TransmissionType | AssociateWithClient | TemplateName | ChooseDrawdownAccount | DA_Account_BuyerID | DA_Account_ClientCode | DrawdownMethod | BuyerAccountID | PayeeAccountID | ChooseDisbursementAccount | DI_Account_BuyerID | DI_Account_ClientCode | DisbursementMethod |
      | Cass Commercial Bank | 0150                 | Transfer         | Acculogix           | TemplateName | VPEN                  | 11111111           | 22222222              | 01 - Wire      | On#NA          | On#NA          | VPEN                      | 33333333           | 44444444              | 05 – Check         |
    Then User perform Create, Download and Delete the "Transmission Files" attachment under General tab
      | Name       | Description           | AttachmentType | FileToUpload                |
      | attachment | Adding new Attachment | Document       | CompanyAttachment_dummy.pdf |


  #CPAY-642 & CPAY-643
  @Regression @PayCRMGeneralManagedPayments @CPAY-642 @CPAY-643
  Scenario: PayCRM Modules : Managed Payment - Bank File Receipts- Archive data from Open Bank FIle Receipts and verify in Archived Bank File receipts
    Given User login to the "PayCRM" application using "qaDetails" credentials
    Then Verify Bank File Receipts menu option on treasury dashboard card and details of the grid
    And User Archives the data from Open Bank FIle Receipts and verify in Archived Bank File receipts


  #CPAY-1199, CPAY-1201, CPAY-1202, CPAY-1203 & CPAY-1204
  @Regression @PayCRMGeneralManagedPayments @CPAY-1199 @CPAY-1201 @CPAY-1202 @CPAY-1203 @CPAY-1204
  Scenario: PayCRM Modules : Managed Payment - Actionable validation for Defund Button in PayCRM
    Given User login to the "PayCRM" application using "qaDetails" credentials
    And User validates "Show" Defund button functionality using queries "ManagedPayment_Defund_DXC_Show#ManagedPayment_Defund_TSYS_Show"
    And User validates "Hide" Defund button functionality using queries "ManagedPayment_Defund_DXC_Hide#ManagedPayment_Defund_TSYS_Hide"
    Then User perform the actionable validation for Defund Button


  #CPAY-1283, CPAY-1284 & CPAY-1285
  @Regression @PayCRMGeneralManagedPayments @CPAY-1283 @CPAY-1284 @CPAY-1285
  Scenario: PayCRM Modules : Managed Payment - Transaction History - Columns and payment status
    Given User login to the "PayCRM" application using "qaDetails" credentials
    Then User validates columns and payment status for Transaction History tab


  #CPAY-1292, CPAY-1293 & CPAY-1294             //Issue Exist: https/determine.atlassian.net/browse/CPAY-2956
  @Regression @PayCRMGeneralManagedPayments @CPAY-1292 @CPAY-1293 @CPAY-1294
  Scenario: PayCRM Modules : Managed Payment - Stop Check Model and Validation and Payment Type, Payment Status for Stop Check in PayCRM
    Given User login to the "PayCRM" application using "qaDetails" credentials
    Then User validates Stop Check model validation in PayCRM


  #CPAY-1190, CPAY-1191, CPAY-1192, CPAY-1193, CPAY-1177 & CPAY-1178
  @Regression @PayCRMGeneralManagedPayments @CPAY-1190 @CPAY-1191 @CPAY-1192 @CPAY-1193 @CPAY-1177 @CPAY-1178
  Scenario: PayCRM Modules : Managed Payment - Conditions to show specific payment status options for Payment Types
    Given User login to the "PayCRM" application using "qaDetails" credentials
    And User validates the conditions to show specific payment status options for Payment Type: Check
    And User validates the conditions to show specific payment status options for Payment Type: ACH or ACH Plus
    And User validates the conditions to show specific payment status options for Payment Type: Virtual Card
    Then User validates the conditions to show specific payment status options for Payment Type: Prepaid or Debit Cards
    And User validates negative scenario for not showing Edit Payment Status button


  #CPAY-1256, CPAY-1258, CPAY-1262 & CPAY-1263
  @Regression @PayCRMGeneralManagedPayments @CPAY-1256 @CPAY-1258 @CPAY-1262 @CPAY-1263
  Scenario: PayCRM Modules : Managed Payment - Validations for Re-Issue Actionable Button in PayCRM
    Given User login to the "PayCRM" application using "qaDetails" credentials
    And User validates the fields based on the Payment Type selected in Re-Issue
    And User Validates Re-Issue Actionable Button in PayCRM
      | PaymentType | PayeeName | PayeeAddress1      | PayeeAddress2 | PayeeCity | PayeeState | PayeeZip | PayeeCountry   | Notes                |
      | Check       | AutoPayee | 123 N. Mian Street | Apt 101       | Denver    | CO         | 80202    | Denver Country | Check - ReIssue Note |
    Then User validates Negative scenarios for Re-Issue Actionable Button in PayCRM


  #CPAY-1248, CPAY-1249 & CPAY-1250
  @Regression @PayCRMGeneralManagedPayments  @CPAY-1248 @CPAY-1249 @CPAY-1250
  Scenario: PayCRM Modules : Managed Payment - Multi-Swipe Button functionality in PayCRM
    Given User login to the "PayCRM" application using "qaDetails" credentials
    And User validates when the Multi-Swipe button should be show or Hide based on the payment Status
    Then User validates Enable Multi Swipe functionality and validates the same under Activities tab


  #CPAY-680
  @Regression @PayCRMGeneralManagedPayments @CPAY-680
  Scenario: Managed Payments- Role/Permission Testing
    Given User login to the "PayCRM" application using "qaDetails" credentials
    And User "Enable" the Managed Payments permission for the PayCRM user and "Yes" logout
    And User login to the "PayCRM" application using "qaPermissionDetails" credentials
    Then User validates the permission for Managed Payments "Before" removing the required permissions
    And User login to the "PayCRM" application using "qaDetails" credentials
    And User "Disable" the Managed Payments permission for the PayCRM user and "Yes" logout
    And User login to the "PayCRM" application using "qaPermissionDetails" credentials
    Then User validates the permission for Managed Payments "After" removing the required permissions
    And User login to the "PayCRM" application using "qaDetails" credentials
    And User "Enable" the Managed Payments permission for the PayCRM user and "Yes" logout


  #CPAY-1183 & CPAY-1197
  @Regression @PayCRMGeneralManagedPayments @CPAY-1183 @CPAY-1197
  Scenario: PayCRM Modules : Managed Payment - Update fee button - New Fees validation and Logic to Show/Hide Update Fee button
    Given User login to the "PayCRM" application using "qaDetails" credentials
    Then User validates "Show" status for Update Fee button by using query "ManagedPayment_UpdateFee_Show"
    Then User validates "Hide" status for Update Fee button by using query "ManagedPayment_UpdateFee_Hide"
    And User validates Update fee button - Modal fields and new Fees validation


  #CPAY-1313
  @Regression @PayCRMGeneralManagedPayments @CPAY-1313
  Scenario: PayCRM Modules : Managed Payment - Payment Details - Resend Void Notification
    Given User login to the "PayCRM" application using "qaDetails" credentials
    And User perform Resend Void Notification and validate the Email notification


 #CPAY-3945, //CPAY-3856--> (Not automated)
  @Regression @PayCRMGeneralManagedPayments @CPAY-3945
  Scenario: PayCRM Modules : Managed Payment - verify when there is a pending authorization on the DXC defunded cards
    Given User login to the "PayCRM" application using "qaDetails" credentials
    Then User validates Authorization Pending Date functionality


  #CPAY-3963
  @Regression @PayCRMGeneralManagedPayments @CPAY-3963
  Scenario: PayCRM Modules : Managed Payment - clone and edit functionality on transmission file grid
    Given User login to the "PayCRM" application using "qaDetails" credentials
    And Managed Payment - Verify Transmission Files grid details
    Then Verify edit and Clone functionality on transmission file grid