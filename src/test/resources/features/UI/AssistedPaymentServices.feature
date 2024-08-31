Feature: PayCRM: Assisted Payment Services Module - Regression Test Scenario's

#  @BillPayProcess
#  Scenario: Assisted Payment Services - Verify user performs Reassign functionality and validate the same in Activities tab
#    Given User login to the "PayCRM" application using "qaDetails" credentials
#    Then User Navigate to the "Assisted Payment Services" Dashboard and Select the case "466793" for the Payment
#    Then Verify Return Status is "Confirmed" before processing Bill payment for this Case
#    Then User Log an Activity type for the case
#      | ActivityType | WebAddress                        | CaseOutcome               |CaseOutcomeValidation| Notes                     |
#      | Online       | https://staging.vendorinroll.com/ | Payment Question Answered || This is Log Activity Note |
#    And User raise a payment question and validates the confirmation message for the same
#      | Issue                   | VoidReason   | PaymentMethod | PaymentDate | DueAmount | IssuedAmount | ReissuedAmount | ConfirmationMessage |
#      | Payment Void Permission | Invoice Paid | ACH           |             | 100       | 100          | 100            |                     |


  #CPAY-663, CPAY-664 & CPAY-694
  @Regression @AssistedPaymentServices @DEMO @CPAY-663 @CPAY-664 @CPAY-694
  Scenario: Assisted Payment Services - Verify user performs Reassign functionality and validate the same in Activities & Logs tab
    Given User login to the "PayCRM" application using "qaDetails" credentials
    When User perform Reassignment of the Enrollment case for "Assisted Payment Services" queue
      | AssignedToUser1      | AssignedToUser2       | workQueue |
      | ggudi@corcentric.com | asingh@corcentric.com | SMVP      |
    And User verify that the reassignment details are displayed in "Assisted Payment Services" activity tab
    And User verify that the reassignment details are displayed in "Assisted Payment Services" Logs details
    And User closes Child window and switch back to Parent browser window
    And User logs out from the "PayCRM" application


  #CPAY-690 & CPAY-691
  @Regression @Smoke @AssistedPaymentServices @CPAY-690 @CPAY-691
  Scenario: Assisted Payment Services - Verify user performs create, Edit and delete of supplier contact details
    Given User login to the "PayCRM" application using "qaDetails" credentials
    Then User adds the "New" supplier contact details for "Assisted Payment Services" and validates the same
      | ContactType | Name     | Email                | Title | Phone1     | Phone1Extn | Phone1Type | Phone2     | Phone2Type | Status | Priority | IsPrimaryContact |
      | Primary     | Auto New | ggudi@corcentric.com | Mr    | 9908877665 | 080        | Business   | 9900112233 | Cell       | Active | High     | Yes              |
    Then User perform the "Edit" supplier contact details for "Assisted Payment Services" and validates the same
      | ContactType | Name      | Email                 | Title | Phone1     | Phone1Extn | Phone1Type | Phone2     | Phone2Type | Status | Priority | IsPrimaryContact |
      | Primary     | Auto Edit | asingh@corcentric.com | Mr    | 9908765432 | 080        | Business   | 9988012345 | Cell       | Active | High     | Yes              |
    Then User deletes the existing the contact & verify contact deleted successful


  #CPAY-692 & CPAY-693
  @Regression @AssistedPaymentServices @DEMO @CPAY-692 @CPAY-693
  Scenario: Assisted Payment Services - Add-Edit-Delete attachments : Verify user adds, Edit and remove the attachment to the enrollment case
    Given User login to the "PayCRM" application using "qaDetails" credentials
    And User adds the attachment for "Assisted Payment Services" queue
      | Name    | Description                       | AttachmentType | Status | UploadFileName |
      | AutoNew | This is the new sample attachment | Document       | Active | SampleFileOne  |
    And User verify that the "Add" attachment details for "Assisted Payment Services" queue
    Then User edit the attachment for the "Assisted Payment Services" queue
      | Name     | Description                          | AttachmentType | Status | UploadFileName | AttachToSupplier |
      | AutoEdit | This is the edited sample attachment | Document       | Active | SampleFileTwo  | No               |
    Then User verify that the "Edit" attachment details for "Assisted Payment Services" queue
    Then User removes the "Edit" attachment for "Assisted Payment Services" queue


  #CPAY-688 & CPAY-689
  @Regression @AssistedPaymentServices @DEMO @CPAY-688 @CPAY-689
  Scenario: Assisted Payment Services : Verify user performs create, Edit and delete Address details
    Given User login to the "PayCRM" application using "qaDetails" credentials
    Then User adds the "New" Address details for "Assisted Payment Services" and validates the same
      | AddressType | Country | Address1                   | Address2 | Address3 | City      | StateProvince | PostalCode | Language |
      | Primary     | US      | 260-C North E1 Camino Real |          |          | Encinitas | California    | 92024-2852 | English  |
    Then User perform the "Edit" Address details for "Assisted Payment Services" and validates the same
      | AddressType | Country | Address1         | Address2 | Address3 | City    | StateProvince | PostalCode | Language |
      | Mailing     | CA      | 700 Quintard Ave |          | Oxford   | Calhoun | Nova Scotia   | 36203      | English  |
    Then User deletes the existing "Payment Service Address" & verify address deleted successful


  #CPAY-707
  @Regression @AssistedPaymentServices @DEMO @CPAY-707
  Scenario: Assisted Payment Services : Verify edit existing void functionality
    Given User login to the "PayCRM" application using "qaDetails" credentials
    Then User edit the existing voids from "Assisted Payment Services" queue and validates the same
      | Name      | CompanySupplierID | Case | PaymentID | Amount      | Currency | CheckCount | Terms | TransactionType |
      | editVoids |                   |      |           | 36000#46000 | USD#EUR  | 1#2        | 2#3   | SMVP#CMVP       |


  #CPAY-834, CPAY-837 & CPAY-978
  @Regression @AssistedPaymentServices @CPAY-834 @CPAY-837 @CPAY-978
  Scenario: Assisted Payment Services : Filters functionality for case grid, PayNext sync & Payment Data tab for the selected case
    Given User login to the "PayCRM" application using "qaDetails" credentials
    And User validates the "Assisted Payment Services" Cases Grid Filter functionality
    Then User Navigate to the "Assisted Payment Services" Dashboard and Select the case "AssistedPaymentServices_PayNETSync" for the Payment
    And User clicks on PayNext Sync button and validate the message
    And User navigates to Payment Data tab and validate the Payment and Bank Info for the case "AssistedPaymentServices_PaymentAndBankInfo"


  #CPAY-708
  @Regression @AssistedPaymentServices @CPAY-708
  Scenario: Assisted Payment Services : Validate Generate Invoice functionality UI validations
    Given User login to the "PayCRM" application using "qaDetails" credentials
    Then User Navigate to the "Assisted Payment Services" Dashboard and Select the case "AssistedPaymentServices_GenerateInvoice_Button" for the Payment
    And User clicks on Generate Invoice button and validates the Attachment tab for invoice details


  #CPAY-1095
  @Regression @AssistedPaymentServices @CPAY-1095
  Scenario: Assisted Payment Services : Validate Invoice Option under Issues dropdown and Child dropdowns
    Given User login to the "PayCRM" application using "qaDetails" credentials
    Then User Navigate to the "Assisted Payment Services" Dashboard and Select the case "AsstdPaymentServices_PaymentQuestions" for the Payment
    And User navigates Email Templates Payment Question dropdown Issue Overpaid Unable to Apply Credit
      | Issue   | InvoiceIssue | WhyOverpaid?           | DueAmount | IssuedAmount | ReissuedAmount | ConfirmationMessage |
      | Invoice | Overpaid     | Unable to Apply Credit | 100       | 100          | 100            |                     |
    And User navigates Email Templates Payment Question dropdown Issue Overpaid Unable to Partially Process
      | Issue   | InvoiceIssue | WhyOverpaid?                                           | ReissuedAmount | ConfirmationMessage |
      | Invoice | Overpaid     | Unable to Partially Process Payment Per Client Request | 100            |                     |
    And User navigates Email Templates Payment Question dropdown Issue Underpaid Pay Full Invoice Amount
      | Issue   | InvoiceIssue | WhyUnderpaid?                                     | DueAmount | IssuedAmount | ReissuedAmount | ConfirmationMessage |
      | Invoice | Underpaid    | Vendor requires client to pay full invoice amount | 100       | 100          | 100            |                     |
    And User navigates Email Templates Payment Question dropdown Issue Underpaid Turn Service Back On
      | Issue   | InvoiceIssue | WhyUnderpaid?                                                                           | ReissuedAmount | ConfirmationMessage |
      | Invoice | Underpaid    | In order to turn service back on, client will need to remit full payment on the account | 100            |                     |
    And User navigates Email Templates Payment Question dropdown Issue Unable to Locate Invoice Account
      | Issue   | InvoiceIssue                     | ConfirmationMessage |
      | Invoice | Unable to Locate Invoice/Account |                     |


  #CPAY-1096
  @Regression @AssistedPaymentServices @CPAY-1096
  Scenario: Assisted Payment Services : Validate Unable to Contact option under Issues dropdown and Child dropdowns
    Given User login to the "PayCRM" application using "qaDetails" credentials
    And User navigates Email Templates Payment Question dropdown Issue Unable to Contact Numbers Dialed
      | Issue             | Attempted Methods | Phone Numbers Attempted on Record | ConfirmationMessage |
      | Unable to Contact | Numbers Dialed    |                                   |                     |
    And User navigates Email Templates Payment Question dropdown Issue Unable to Contact Email Addresses Used
      | Issue             | Attempted Methods    | Email Addresses Attempted on Record | ConfirmationMessage |
      | Unable to Contact | Email Addresses Used |                                     |                     |


  #CPAY-752
  @Regression @AssistedPaymentServices @CPAY-752
  Scenario: Assisted Payment Services : Show Cases - Export Excel icon
    Given User login to the "PayCRM" application using "qaDetails" credentials
    Then User navigates to cases screen of the "Assisted Payment Services" queue and downloads the Excel file


  #CPAY-1785, CPAY-1786, CPAY-1805 & CPAY-1806
  @Regression @AssistedPaymentServices @CPAY-1785 @CPAY-1786 @CPAY-1805 @CPAY-1806
  Scenario: Assisted Payment Services : Last Touch- Update and Validation: Log Activity
    Given User login to the "PayCRM" application using "qaDetails" credentials
    Then User Navigate to the "Assisted Payment Services" cases, perform Log Activity and validates the activities log and Last touch column updation
      | ActivityType | WebAddress                        | CaseOutcome               | CaseOutcomeValidation | Notes                     |
      | Online       | https://staging.vendorinroll.com/ | Payment Question Answered |                       | This is Log Activity Note |
    Then User perform "Assisted Payment Services" reassign and validates the activities log and Last touch column updation
      | AssignedToUser1      | AssignedToUser2       | workQueue |
      | ggudi@corcentric.com | asingh@corcentric.com | SMVP      |
    And User validates "Assisted Payment Services" Last Touch- Update and Validation: Email
      | To                          | Subject               | isFileSupported | FileToUpload                | Message                                                               |
      | cpay-qa-auto@corcentric.com | Automation-Send Email | Yes             | CompanyAttachment_dummy.pdf | This is a Sample email body content for Automation Regression testing |



  #CPAY-1094, CPAY-1615, CPAY-1120, CPAY-1121, CPAY-1122, CPAY-4465, CPAY-4466, CPAY-4467, CPAY-4468, CPAY-4470, CPAY-4471, CPAY-4472 & CPAY-4473
  @Regression @AssistedPaymentServices @CPAY-1094 @CPAY-1615 @CPAY-1120 @CPAY-1121 @CPAY-1122 @CPAY-4465 @CPAY-4466 @CPAY-4467 @CPAY-4468 @CPAY-4470 @CPAY-4471 @CPAY-4472 @CPAY-4473
  Scenario: Assisted Payment Services : Payments Tab - Show Cases - (C)] - Email Templates - Payment Questions
    Given User login to the "PayCRM" application using "qaDetails" credentials
    Then Verify Assisted Payment Services "Payment Void Permission" options in Issue drop down -Verify Child dropdowns and Email text
      | Issue                   | VoidReason                                              | ProcessedAmount | RefundAmount | PaymentMethod | CheckNumber | DueAmount | IssuedAmount | ReIssuedAmount |
      | Payment Void Permission | Amount Discrepancy, Refund Balance After Payment Posted | 10              | 5            |               |             |           |              |                |
      | Payment Void Permission | Payment Invoice Paid                                    |                 |              | Check         |             | 10        | 5            | 5              |
    Then Verify Assisted Payment Services "Invoice" options in Issue drop down - Verify Invoice Emails
      | Issue   | InvoiceIssue                     | WhyOver_UnderPaid                                                                       | PaymentMethod | DueAmount | IssuedAmount | ReIssuedAmount | CheckNumber | ProcessingFee |
      | Invoice | Overpaid                         | Unable to Apply Credit                                                                  |               | 10        | 5            | 5              |             |               |
      | Invoice | Overpaid                         | Unable to Partially Process Payment Per Client Request                                  |               |           |              | 1              |             |               |
      | Invoice | Underpaid                        | Vendor requires client to pay full invoice amount                                       |               | 10        | 5            | 5              |             |               |
      | Invoice | Underpaid                        | In order to turn service back on, client will need to remit full payment on the account |               |           |              | 5              |             |               |
      | Invoice | Unable to Locate Invoice/Account | NA                                                                                      |               |           |              |                |             |               |
      | Invoice | Unable to Apply Credit           | Credit Already Applied                                                                  |               |           |              |                |             |               |
      | Invoice | Unable to Apply Credit           | Credit Unavailable                                                                      |               |           |              |                |             |               |
      | Invoice | Invoice Paid                     | Unable to Confirm Check #                                                               |               |           |              |                |             |               |
      | Invoice | Invoice Paid                     | Unable to Confirm Card # (Last 4)                                                       |               |           |              |                |             |               |
      | Invoice | Invoice Paid                     | Previously Paid Check #                                                                 |               |           |              |                | 123456      |               |
      | Invoice | Invoice Paid                     | Previously Paid Card # (Last 4)                                                         |               |           |              |                | 2602        |               |
      | Invoice | Invoice Paid                     | Invoice paid by ACH/wire                                                                | ACH           |           |              |                |             |               |
      | Invoice | Invoice Paid                     | Invoice paid by ACH/wire                                                                | Wire          |           |              |                |             |               |
      | Invoice | Processing Fee                   | Fee Waived (ACH Option)                                                                 |               |           |              |                |             | 10            |
      | Invoice | Processing Fee                   | Fee Waived (No ACH Option)                                                              |               |           |              |                |             | 10            |
      | Invoice | Processing Fee                   | Fee Not Waived (No ACH Option)                                                          |               |           |              |                |             | 10            |
      | Invoice | Processing Fee                   | Fee Not Waived (ACH Option)                                                             |               |           |              |                |             | 10            |
    And Verify Assisted Payment Services "Unable To Contact" options in Issue drop down -Verify Unable To Contact Email
      | Issue             | AttemptedMethods     | PhoneNumbers_EmailAddressAttemptedOnRecord |
      | Unable to Contact | Numbers Dialed       |                                            |
      | Unable to Contact | Email Addresses Used |                                            |
    And Verify Assisted Payment Services "Process" options in Issue drop down -Verify Process Email
      | Issue   | ProcessIssue             | LoginCredentials           | UserName |
      | Process | Online Login Credentials | Need Username and Password |          |
      | Process | Online Login Credentials | Need Updated Password      | ggudi    |


  #CPAY-3981
  @Regression @AssistedPaymentServices @CPAY-3981
  Scenario: Assisted Payment Services : Verify direct link to 'payment details' page from Bill Pay Cases
    Given User login to the "PayCRM" application using "qaDetails" credentials
    Then verify "Assisted Payment Services" case should have paymentID with link and redirect user to payment detail page


  #CPAY-4411
  @Regression @AssistedPaymentServices @CPAY-4411
  Scenario: Assisted Payment Services : Verify File upload in Send Email functionality
    Given User login to the "PayCRM" application using "qaDetails" credentials
    Then User validates the "Supported" file formats under "Assisted Payment Services" which should be used in uploading files
      | To                          | Subject               | isFileSupported | FileToUpload                                                                                                                                                                                                                                                                                | Message                                                                               |
      | cpay-qa-auto@corcentric.com | Automation-Send Email | Yes             | FileUpload/pdfFile.pdf#FileUpload/excelFile.xlsx#FileUpload/wordFile.docx#FileUpload/jpegFile.jpeg#FileUpload/pngFile.png#FileUpload/tifFile.tiff#FileUpload/gifFile.gif#FileUpload/xmlFile.xml#FileUpload/bmpFile.bmp#FileUpload/zipFile.zip#FileUpload/svgFile.svg#FileUpload/jpgFile.jpg | This is a Sample email body content for Regression Automation testing for File upload |
    And User validates the "UnSupported" file formats under "Assisted Payment Services" which should be used in uploading files
      | To                          | Subject               | isFileSupported | FileToUpload                                                         | Message                                                                               |
      | cpay-qa-auto@corcentric.com | Automation-Send Email | No              | FileUpload/csvFile.csv#FileUpload/exeFile.exe#FileUpload/txtFile.txt | This is a Sample email body content for Regression Automation testing for File upload |


  #CPAY-4413, CPAY-4414, CPAY-4415, CPAY-4416 & CPAY-4418
  @RegressionPending @AssistedPaymentServices @CPAY-4413 @CPAY-4414 @CPAY-4415 @CPAY-4416 @CPAY-4418
  Scenario: Assisted Payment Services : Verify update Pend Logic for Case Outcomes in Bill Pay Cases
    Given User login to the "PayCRM" application using "qaDetails" credentials
    Then Verify Pend Logic for "Assisted Payment Services" Bill Pay "CMVP#SMVP" Cases with "Open" status & "Low" priority-> Log Activity-> Case outcome
      | ActivityType | WebAddress                        | CaseOutcome   | CaseOutcomeValidation   | Notes                     |
      | Online       | https://staging.vendorinroll.com/ | Call Transfer | akriener@corcentric.com | This is Log Activity Note |
    And Verify Pend Logic for "Assisted Payment Services" Bill Pay "CMVP#SMVP" Cases with "Open" status & "Low" priority-> Log Activity-> Case outcome
      | ActivityType | WebAddress                        | CaseOutcome      | CaseOutcomeValidation   | Notes                     |
      | Online       | https://staging.vendorinroll.com/ | Team Lead Review | akriener@corcentric.com | This is Log Activity Note |
    And Verify Pend Logic for "Assisted Payment Services" Bill Pay "CMVP#SMVP" Cases with "Open" status & "Low" priority-> Log Activity-> Case outcome
      | ActivityType | WebAddress                        | CaseOutcome      | CaseOutcomeValidation | Notes                     |
      | Online       | https://staging.vendorinroll.com/ | Payment Question | ggudi#9988776655      | This is Log Activity Note |
    And Verify Pend Logic for "Assisted Payment Services" Bill Pay "CMVP#SMVP" Cases with "Open" status & "Low" priority-> Log Activity-> Case outcome
      | ActivityType | WebAddress                        | CaseOutcome         | CaseOutcomeValidation   | Notes                     |
      | Online       | https://staging.vendorinroll.com/ | Promised to Process | ggudi#9988776655#198226 | This is Log Activity Note |
    And Verify Pend Logic for "Assisted Payment Services" Bill Pay "CMVP#SMVP" Cases with "Open" status & "Low" priority-> Log Activity-> Case outcome
      | ActivityType | WebAddress                        | CaseOutcome       | CaseOutcomeValidation | Notes                     |
      | Online       | https://staging.vendorinroll.com/ | Payment Follow-up | ggudi#9988776655      | This is Log Activity Note |
    And Verify Pend Logic for "Assisted Payment Services" Bill Pay "CMVP#SMVP" Cases with "Open" status & "Low" priority-> Log Activity-> Case outcome
      | ActivityType | WebAddress                        | CaseOutcome | CaseOutcomeValidation | Notes                     |
      | Online       | https://staging.vendorinroll.com/ | No Response | ggudi#9988776655      | This is Log Activity Note |
    And Verify Pend Logic for "Assisted Payment Services" Bill Pay "CMVP#SMVP" Cases with "Open" status & "Low" priority-> Log Activity-> Case outcome
      | ActivityType | WebAddress                        | CaseOutcome     | CaseOutcomeValidation | Notes                     |
      | Online       | https://staging.vendorinroll.com/ | No Answer - LVM | ggudi#9988776655      | This is Log Activity Note |
    And Verify Pend Logic for "Assisted Payment Services" Bill Pay "CMVP#SMVP" Cases with "Hold" status & "Low" priority-> Log Activity-> Case outcome
      | ActivityType | WebAddress                        | CaseOutcome               | CaseOutcomeValidation | Notes                     |
      | Online       | https://staging.vendorinroll.com/ | Payment Question Answered |                       | This is Log Activity Note |
    And Verify Pend Logic for "Assisted Payment Services" Bill Pay "CMVP#SMVP" Cases with "Open" status & "High" priority-> Log Activity-> Case outcome
      | ActivityType | WebAddress                        | CaseOutcome               | CaseOutcomeValidation | Notes                     |
      | Online       | https://staging.vendorinroll.com/ | Payment Question Answered |                       | This is Log Activity Note |

  @Regression @CPAY-5083
  Scenario: Assistant Payment Services: User verifies payment questions for credit issue
    Given User login to the "PayCRM" application using "qaDetails" credentials
    Then User navigates to cases screen of the "Assisted Payment Services" queue and validates payment question template for Credit
      | CreditOptions | Reason                          |
      | Credit Refund | Cannot Confirm                  |
      | Credit Refund | Confirmed (will not re-process) |
      | Credit Refund | Confirmed (will not refund)     |
      | Credit ReRun  | Credit ReRun                    |

  @Regression @CPAY-5197
  Scenario: Verify user is able to see moniker icon for reopened bill pay cases
    Given User login to the "PayCRM" application using "qaDetails" credentials
    Then User navigates to cases screen of the "Assisted Payment Services" queue and validates moniker icon for bill pay cases
      | Queue Name                     | Supplier Name | Payment Range | Due Date Days | Spend Only | Reopened Case | Include Hold | First Time Payment |
      | AutomationFilter_DO Not Modify |               |               |               |            | True          |              |                    |