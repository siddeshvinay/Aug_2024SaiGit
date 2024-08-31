Feature: PayCRM Modules:Daily Grind - Regression Test Scenario's

  @Regression @Smoke @PayCRMDailyGrind @CPAY-696 @CPAY-744
  Scenario: PayCRM Modules:Daily Grind - Program Management: Test Create & edit The Wave Types successful
    Given User login to the "PayCRM" application using "qaDetails" credentials
    Then User creates the "New" wave Types and validate the same
      | Name    | Company    | StartDate | EndDate | WaveType | Status | Priority |
      | newWave | 4Catalyzer |           |         | Sprint   | Open   | High     |
    Then User perform "Edit" wave Types and validate the same
      | Name     | Company    | StartDate | EndDate | WaveType | Status      | Priority |
      | editWave | 4Catalyzer |           |         | Sprint   | In Progress | Low      |


  @Regression @PayCRMDailyGrind @CPAY-698
  Scenario: PayCRM Modules:Daily Grind - Queues: Test edit Queues functionality works successful
    Given User login to the "PayCRM" application using "qaDetails" credentials
    Then User perform "Edit" queues and validate the same
      | Name      | Tag     | Owner                                      | WorkQueueType                  | Status          | Priority | Notes                               |
      | editQueue | editTag | ggudi@corcentric.com#asingh@corcentric.com | Card Enablement#ACH Enablement | Active#Inactive | Low      | This is sample note for edit queues |

  @Regression @PayCRMDailyGrind @CPAY-665
  Scenario: PayCRM Modules:Daily Grind - Cases: Test the filters functionality on Cases Page
    Given User login to the "PayCRM" application using "qaDetails" credentials
    Then User apply the "Valid" filter and search the Cases data using query "DailyGrindCasesDetails"
      | SupplierName | CompanyName                             | CaseType   | Status | Priority | RecordLimit | TotalSpend | Amount |
      |              | Domino's National Advertising Fund, Inc | Enrollment | New    | Low      | 10          | =          | 7000   |
    Then User apply the "Valid" filter and search the Cases data using query "DailyGrindCasesDetails"
      | SupplierName | CompanyName | CaseType | Status | Priority | RecordLimit | TotalSpend | Amount |
      | FedEx Custom |             |          |        | High     | 5           | <          | 1000   |
    Then User apply the "Valid" filter and search the Cases data using query "DailyGrindCasesDetails"
      | SupplierName | CompanyName | CaseType | Status | Priority | RecordLimit | TotalSpend | Amount |
      |              |             |          |        | High     | 5           | >          | 1000   |
    Then User apply the "Invalid" filter and search the Cases data using query "DailyGrindCasesDetails"
      | SupplierName | CompanyName | CaseType | Status | Priority | RecordLimit | TotalSpend | Amount |
      | Wr0ng D@t@   |             |          |        | High     | 5           |            |        |
    Then User apply the "FilterWithNoValidation" filter and search the Cases data using query "DailyGrind_EnrollCases"
      | SupplierName | CompanyName | CaseType   | Status | Priority | RecordLimit | TotalSpend | Amount |
      |              |             | Enrollment | Open   |          |             |            |        |

  @Regression @PayCRMDailyGrind @CPAY-723 @CPAY-724 @CPAY-725 @CPAY-727 @CPAY-728 @CPAY-731 @CPAY-735 @CPAY-736
  Scenario: PayCRM Modules:Daily Grind - Cases: Test the case functionality
    Given User login to the "PayCRM" application using "qaDetails" credentials
    Then User navigates and select the Daily Grind Case using query "DailyGrindCases"
    And User perform Wave & Offers- Go to Wave
    And User create, edit and validate the activities
      | Notes | CaseOutCome         | ActivityType    |
      | Note  | Updated Information | Enrollment Call |
    And User validates Logs, Related cases, Address, Contacts and Transactions tab columns

  @Regression @PayCRMDailyGrind @CPAY-802
  Scenario: PayCRM Modules:Daily Grind - Cases: Test the re-open case functionality
    Given User login to the "PayCRM" application using "qaDetails" credentials
    Then User navigates and select the Daily Grind Case using query "DailyGrind_Cases_RecordedEnrollment_AdditionalInfo"
    And User reopen the closed cases and validate the same
      | ReopenReason               | ResumeOnOffer |
      | Re-opening the closed case |               |

  @Regression @PayCRMDailyGrind @CPAY-751 @CPAY-771
  Scenario: PayCRM Modules:Daily Grind - Program Management: Test the Information & Suppliers tabs of Wave & Offer
    Given User login to the "PayCRM" application using "qaDetails" credentials
    Then Navigate to Information tab of the selected cases Wave & Offers section
    And click on Suppliers tab and validate the grid columns

  @Regression @PayCRMDailyGrind @CPAY-773 @CPAY-778
  Scenario: PayCRM Modules:Daily Grind - Program Management: Test the create and edit Wave & Offer functionality
    Given User login to the "PayCRM" application using "qaDetails" credentials
    And Navigate to offers tab of wave offer, create new wave offer and validate the same
      | OfferNumber | OfferType | CardPlatform | CardNetwork | CardCampainType | VirtualCardDeliverMethod | Currency | AmountFrom | AmountTo | ACHLimits | Terms | Percentage | VendorinNetworkDiscount | AgentIntervention | BypassACH | OfferEnabled |
      | 5           | ACH       | U.S. Bank    | Visa        | Card First      | Email                    | USD      |            |          |           | Terms |            |                         | No                | No        | Yes          |
    Then Edit the existing wave offer and validate the same
      | OfferNumber | OfferType | CardPlatform | CardNetwork | CardCampainType | VirtualCardDeliverMethod | Currency | AmountFrom | AmountTo | ACHLimits | Terms | Percentage | VendorinNetworkDiscount | AgentIntervention | BypassACH | OfferEnabled |
      | 5           | VND       | HP           | MasterCard  | Card Only       | Email                    | USD      |            |          |           | Terms |            |                         | No                | No        | No           |

  @Regression @PayCRMDailyGrind @CPAY-780 @CPAY-781
  Scenario: PayCRM Modules:Daily Grind - Program Management: Test the create and edit scripts functionality
    Given User login to the "PayCRM" application using "qaDetails" credentials
    And Navigate to Scripts tab of wave offer, create new scripts and validate the same
      | ScriptName | ScriptText                 | ScriptType   | WaveOffer |
      | NewScript  | Sample text for the Script | Introduction |           |
    Then Edit the existing scripts and validate the same
      | ScriptName | ScriptText                      | ScriptType | WaveOffer |
      | EditScript | Sample text for the edit script | Acceptance |           |

  @Regression @PayCRMDailyGrind @CPAY-733 @CPAY-734
  Scenario: PayCRM Modules:Daily Grind - Cases: Recorded Enrollment and Additional information
    Given User login to the "PayCRM" application using "qaDetails" credentials
    Then User navigates and select the Daily Grind Case using query "DailyGrind_Cases_RecordedEnrollment_AdditionalInfo"
    And User validates Recorded Enrollments and Additional Information Tab fields

  @Regression @PayCRMDailyGrind @CPAY-726 @CPAY-818 @CPAY-816 @CPAY-785
  Scenario: PayCRM Modules:Daily Grind - Cases: Validate Related Case Tab functionality
    Given User login to the "PayCRM" application using "qaDetails" credentials
    Then User navigates and select the Daily Grind Case using query "DailyGrind_Cases_RelatedCaseTab"
    And User validates Related Cases and Activities Tab fields
    And Validate Send Email functionality from Daily Grind cases
      | To                          | Subject               | isFileSupported | FileToUpload                | Message                                                               |
      | cpay-qa-auto@corcentric.com | Automation-Send Email | Yes             | CompanyAttachment_dummy.pdf | This is a Sample email body content for Automation Regression testing |
    Then User apply the "FilterWithNoValidation" filter and search the Cases data using query ""
      | SupplierName | CompanyName | CaseType   | Status | Priority | RecordLimit | TotalSpend | Amount |
      |              |             | Enrollment | Closed |          |             |            |        |
    And Validate Generate PAF Send Email functionality from Daily Grind cases
      | ContactEmail                | From                       | CC | Subject | Message | FileToUpload | OfferAccepted |
      | cpay-qa-auto@corcentric.com | validations@corcentric.com |    |         |         |              | VND           |

  @Regression @PayCRMDailyGrind @CPAY-706 @CPAY-908
  Scenario: PayCRM Modules: - Daily Grind - StopFraud™: Validate StopFraud validations and Validation lookup functionalities
    Given User login to the "PayCRM" application using "qaDetails" credentials
    Then User perform check on the StopFraud-Validations page UI components
    Then User validates the filter functionality on StopFraud-Validations lookup page
      | SupplierName | PayNetID  | TIN       |
      | Pepsi inc    | 861026215 | 999999901 |

  @Regression @PayCRMDailyGrind @CPAY-792
  Scenario: PayCRM Modules:Manage - Daily Grind - Cases: Case Pend Logic
    Given User login to the "Enrollment Cases" application using "CaseDetails" credentials
    Then User fills out Enroll Details and validate the Case Pend Logic in Activities Tab
      | VCardEmail         | VCardPhone     | Country | Currency | OfferAccepted | Activity | PhoneNumber    | PhoneType | CaseOutCome     | Notes                  |
      | abc@corcentric.com | (998) 877-6655 | US      | USD      | Not Yet       | Phone    | (998) 877-6655 | Outbound  | No Answer - LVM | Automation: Test Notes |

  @Regression @PayCRMDailyGrind @CPAY-768
  Scenario: PayCRM Modules:Daily Grind - Program Management: Validate Activities Tab under Waves
    Given User login to the "PayCRM" application using "qaDetails" credentials
    And User navigates to Daily grind program management waves using the query "DailyGrind_Waves_ActivitiesTab"
    Then User navigates to Waves grid and validates Activities tab functionality

  @Regression @PayCRMDailyGrind @CPAY-772
  Scenario: PayCRM Modules:Daily Grind - Program Management: Create Missing Enrollment Cases
    Given User login to the "PayCRM" application using "qaDetails" credentials
    Then User navigates to Suppliers and create missing enrollment cases using query "ProgramManagement_SuppliersWithEnrollments"

  @Regression @PayCRMDailyGrind @CPAY-782
  Scenario: Daily Grind - Program Management - Waves(W) - Cases - (WC) Open Case in New Browser Tab
    Given User login to the "PayCRM" application using "qaDetails" credentials
    And User navigates to cases tab of the selected waves type

  @Regression @PayCRMDailyGrind @CPAY-779
  Scenario: Enrollments Dashboard - Wave & Offer] Offers - Test Delete Wave offer
    Given User login to the "PayCRM" application using "qaDetails" credentials
    And User navigates to offer tab of the selected waves type
    Then User verify that the trashcan icon "should not" visible

  @Regression @PayCRMDailyGrind @CPAY-729
  Scenario: [Daily Grind - Cases - Case] Attachments
    Given User login to the "PayCRM" application using "qaDetails" credentials
    Then User navigates and select the Daily Grind Case using query "DailyGrindCases_Attachments"
    And User verify that tab grid column heading is present
      | Name            |
      | Description     |
      | Attachment Type |
      | Attached To     |

  @Regression @PayCRMDailyGrind @CPAY-791
  Scenario: [Daily Grind] User Case Assignment
    Given User login to the "PayCRM" application using "qaDetails" credentials
    And user apply filters on "Enrollments Manager" page and goto Show Cases page
      | Case Statuses   | New     |
      | Assigned To     | Default |
      | Offer Type      |         |
      | Case Priorities |         |
    Then User validates the columns after apply filter on the Enrollment Cases Grid
      | Status      | New     |
      | Assigned To | Default |

  @Regression @PayCRMDailyGrind @CPAY-1600 @CPAY-1601 @CPAY-1602 @CPAY-1700 @CPAY-1701 @CPAY-1702 @CPAY-1703
  Scenario: Modules : Daily Grind - Cases: Validate Payment switch functionalities
    Given User login to the "PayCRM" application using "qaDetails" credentials
    Then User perform Payment switch activities for "None->Virtual Card" combination
      | ToggleforCMVP | MerchantAcquirer | VCardMail            |  | VCardPhone | RemittanceAdviceEmailRecipient | Country | Currency |
      | No            | No               | ggudi@corcentric.com |  | 9988776655 | ggudi@corcentric.com           | US      | USD      |
    Then User perform Payment switch activities for "Virtual Card->ACH Plus" combination
      | MaintenanceFeeMethod | PaymentNotificationEmailRecipient | RemittanceAdviceEmailRecipient | NetworkMaintenanceFeeAdviceEmailRecipient | AccountType | BankName | AccountName | ABARoutingNumber | AccountNumber | Country | Currency |
      | Monthly Withdrawal   | ggudi@corcentric.com              | ggudi@corcentric.com           | ggudi@corcentric.com                      | Savings     | CitiBank | TestAuto    | 063100277        | 112233445566  | US      | USD      |
    Then User perform Payment switch activities for "ACH Plus->ACH Basic" combination
      | MaintenanceFeeMethod | PaymentNotificationEmailRecipient | RemittanceAdviceEmailRecipient | NetworkMaintenanceFeeAdviceEmailRecipient | AccountType | BankName        | AccountName | ABARoutingNumber | AccountNumber | Country | Currency |
      | Monthly Withdrawal   | ggudi@corcentric.com              | ggudi@corcentric.com           | ggudi@corcentric.com                      | Savings     | Bank of America | TestAuto    | 063100277        | 123456789     | US      | USD      |
    Then User perform Payment switch activities for "ACH Basic->Virtual Card" combination
      | ToggleforCMVP | MerchantAcquirer | VCardMail            |  | VCardPhone | RemittanceAdviceEmailRecipient | Country | Currency |
      | No            | No               | ggudi@corcentric.com |  | 9988776655 | ggudi@corcentric.com           | CA      | CAD      |
    Then User perform Payment switch activities for "Virtual Card->ACH Plus" combination
      | MaintenanceFeeMethod | PaymentNotificationEmailRecipient | RemittanceAdviceEmailRecipient | NetworkMaintenanceFeeAdviceEmailRecipient | AccountType | BankName        | AccountName | ABARoutingNumber | AccountNumber | Country | Currency |
      | Monthly Withdrawal   | ggudi@corcentric.com              | ggudi@corcentric.com           | ggudi@corcentric.com                      | Savings     | Bank of America | TestAuto    | 063100277        | 987654321     | US      | USD      |
    Then User perform Payment switch activities for "ACH Plus->Virtual Card" combination
      | ToggleforCMVP | MerchantAcquirer | VCardMail            |  | VCardPhone | RemittanceAdviceEmailRecipient | Country | Currency |
      | No            | No               | ggudi@corcentric.com |  | 9988776655 | ggudi@corcentric.com           | CA      | CAD      |
    Then User perform Payment switch activities for "Virtual Card->Virtual Card" combination
      | ToggleforCMVP | MerchantAcquirer | VCardMail            |  | VCardPhone | RemittanceAdviceEmailRecipient | Country | Currency |
      | No            | No               | ggudi@corcentric.com |  | 9988776655 | ggudi@corcentric.com           | CA      | CAD      |
    Then User perform Payment switch activities for "Virtual Card->ACH Basic" combination
      | MaintenanceFeeMethod | PaymentNotificationEmailRecipient | RemittanceAdviceEmailRecipient | NetworkMaintenanceFeeAdviceEmailRecipient | AccountType | BankName | AccountName | ABARoutingNumber | AccountNumber | Country | Currency |
      | Monthly Withdrawal   | ggudi@corcentric.com              | ggudi@corcentric.com           | ggudi@corcentric.com                      | Savings     | CitiBank | TestAuto    | 063100277        | 112233445566  | US      | USD      |
    Then User perform Payment switch activities for "ACH Basic->ACH Basic" combination
      | MaintenanceFeeMethod | PaymentNotificationEmailRecipient | RemittanceAdviceEmailRecipient | NetworkMaintenanceFeeAdviceEmailRecipient | AccountType | BankName | AccountName | ABARoutingNumber | AccountNumber | Country | Currency |
      | Monthly Withdrawal   | ggudi@corcentric.com              | ggudi@corcentric.com           | ggudi@corcentric.com                      | Savings     | CitiBank | TestAuto    | 063100277        | 112233445566  | US      | USD      |
    Then User perform Payment switch activities for "None->ACH Plus" combination
      | MaintenanceFeeMethod | PaymentNotificationEmailRecipient | RemittanceAdviceEmailRecipient | NetworkMaintenanceFeeAdviceEmailRecipient | AccountType | BankName | AccountName | ABARoutingNumber | AccountNumber | Country | Currency |
      | Monthly Withdrawal   | ggudi@corcentric.com              | ggudi@corcentric.com           | ggudi@corcentric.com                      | Savings     | CitiBank | TestAuto    | 063100277        | 112233445566  | US      | USD      |
    Then User perform Payment switch activities for "ACH Plus->ACH Plus" combination
      | MaintenanceFeeMethod | PaymentNotificationEmailRecipient | RemittanceAdviceEmailRecipient | NetworkMaintenanceFeeAdviceEmailRecipient | AccountType | BankName | AccountName | ABARoutingNumber | AccountNumber | Country | Currency |
      | Monthly Withdrawal   | ggudi@corcentric.com              | ggudi@corcentric.com           | ggudi@corcentric.com                      | Savings     | CitiBank | TestAuto    | 063100277        | 112233445566  | US      | USD      |

  @Regression @PayCRMDailyGrind @PAF @CPAY-2108 @CPAY-2109 @CPAY-2111 @CPAY-2126 @CPAY-784
  Scenario: PayCRM Modules:Daily Grind - Program Management: Validate Waves Activities Tab
    Given User login to the "PayCRM" application using "qaDetails" credentials
    And User apply company level setting as "True" and wave level settings as "True"
    Then User creates new supplier from supplier tab and enroll new case for "non virtual card"
    Then User verify PAF validation as
      | Address Validation         | Pending |
      | OFAC Scan                  | Pending |
      | Profile Authorization Form | Pending |
      | TIN Matching               | Pending |
    And User generate multiple validations from PayNet and verify validations from support case
    And User apply company level setting as "True" and wave level settings as "True"
    Then User creates new supplier from supplier tab and enroll new case for "virtual card"
    Then User verify PAF validation as
      | Address Validation         | Complete |
      | OFAC Scan                  | Complete |
      | Profile Authorization Form | Complete |
      | TIN Matching               | Complete |
    And User apply company level setting as "True" and wave level settings as "False"
    Then User creates new supplier from supplier tab and enroll new case for "virtual card"
    Then User verify PAF validation as
      | Address Validation         | Complete |
      | OFAC Scan                  | Complete |
      | Profile Authorization Form | Complete |
      | TIN Matching               | Complete |
    And User apply company level setting as "False" and wave level settings as "False"
    Then User creates new supplier from supplier tab and enroll new case for "virtual card"
    Then User verify PAF validation as
      | Address Validation         | Pending |
      | OFAC Scan                  | Pending |
      | Profile Authorization Form | Pending |
      | TIN Matching               | Pending |
    And User apply company level setting as "False" and wave level settings as "True"
    Then User creates new supplier from supplier tab and enroll new case for "virtual card"
    Then User verify PAF validation as
      | Address Validation         | Pending  |
      | OFAC Scan                  | Pending  |
      | Profile Authorization Form | Complete |
      | TIN Matching               | Pending  |
    And User verify Re-Open functionality of enroll case

  @Regression @PayCRMDailyGrind @CPAY-2093 @CPAY-2104 @CPAY-2150
  Scenario: PayCRM Modules:Manage - Daily Grind - StopFraud™: Validate StopFraud Payment Hold, Release and clear validation
    Given User login to the "PayCRM" application using "qaDetails" credentials
    Then User search for PayNetID as "API_ManagedPayment_HoldAndRelease_PUTInput" then validates Hold and release functionality from StopFraud validation lookup page

  @Regression @PayCRMDailyGrind @CPAY-1706 @CPAY-1686 @CPAY-1685
  Scenario: PayCRM Modules:Manage - Daily Grind - Tin Validation- Verify Manual and Auto Validate Button
    Given User login to the "PayCRM" application using "qaDetails" credentials
    Then User navigates to Daily Grind-->StopFraud and validates the "Manual validate" button functionality
      | TINValidationNote          | FileToUpload                |
      | Tin Manual Validation note | CompanyAttachment_dummy.pdf |
    And User navigates to Daily Grind-->StopFraud and validates the "Auto validate" button functionality
      | TINValidationNote          | FileToUpload                |
      | Tin Manual Validation note | CompanyAttachment_dummy.pdf |

  @Regression @PayCRMDailyGrind @CPAY-1692
  Scenario: Modules : Daily Grind - Cases: Perform Switched Payment action and verify in the report
    Given User login to the "PayCRM" application using "qaDetails" credentials
    Then User perform Payment switch activities for "None->Virtual Card" combination
      | ToggleforCMVP | MerchantAcquirer | VCardMail            |  | VCardPhone | RemittanceAdviceEmailRecipient | Country | Currency |
      | No            | No               | ggudi@corcentric.com |  | 9988776655 | ggudi@corcentric.com           | US      | USD      |
    Then User perform Payment switch activities for "Virtual Card->ACH Plus" combination
      | MaintenanceFeeMethod | PaymentNotificationEmailRecipient | RemittanceAdviceEmailRecipient | NetworkMaintenanceFeeAdviceEmailRecipient | AccountType | BankName | AccountName | ABARoutingNumber | AccountNumber | Country | Currency |
      | Monthly Withdrawal   | ggudi@corcentric.com              | ggudi@corcentric.com           | ggudi@corcentric.com                      | Savings     | CitiBank | TestAuto    | 063100277        | 112233445566  | US      | USD      |
    And Navigate to Switch Payment Type report and validate the payment switch entry

  @Regression @PayCRMDailyGrind @SupportCases @CPAY-2576 @CPAY-2577 @CPAY-2721
  Scenario: Modules : Daily Grind - Verify Support cases grid tabs and columns headers
    Given User login to the "PayCRM" application using "qaDetails" credentials
    Then Verify support case link based on permission
      | Support Admin | false |
    Then User verify support case grid

  @Regression @PayCRMDailyGrind @SupportCases @CPAY-2746 @CPAY-2615 @CPAY-2617 @CPAY-2646 @CPAY-2720 @CPAY-2770 @CPAY-2592 @CPAY-2722 @CPAY-2625 @CPAY-2910 @CPAY-2879
  Scenario: Modules : Daily Grind - Support cases- Change Validation case type and verify grid
    Given User login to the "PayCRM" application using "qaDetails" credentials
    Then User validates different types of support cases
      | Website Validation    |
      | Verbal Validation     |
      | New User Request      |
      | Pre-note Failure      |
      | ACH Return/Reject/NOC |
      | Merger/Acquisition    |

  @Regression @PayCRMDailyGrind @SupportCases @CPAY-3035
  Scenario: Modules : Daily Grind - Support cases- List of PayNet Users on a PayCRM support case
    Given User login to the "PayCRM" application using "qaDetails" credentials
    Then User validate automatic support cases type information tiles based on different area name

  @Regression @PayCRMDailyGrind @SupportCases @CPAY-2911 @CPAY-2912 @CPAY-2913 @CPAY-2914 @CPAY-2916 @CPAY-2917 @CPAY-2940
  Scenario: Modules : Daily Grind - Verify Support cases - Email Log Activity Create For Different Types and field validations
    Given User login to the "PayCRM" application using "qaDetails" credentials
    Then User validates Support cases - Email Log Activity Fields
    And User validates Support cases - Email Log Activity Create For Different Types
      | SupportCaseType | UnableToValidate | ValidateCompleted | ActivityType             | ActivityTypeSpecificData                    | CaseOutcome        | CaseOutcomeReason | FileToUpload                | Notes                 |
      | Automatic       | Off              | Off               | Email                    | ggudi@corcentric.com#ggudi#9988776655#12345 | Validation Pending |                   | CompanyAttachment_dummy.pdf | Email - Hold Status   |
      | Any             | Off              | On                | Email                    | ggudi@corcentric.com#ggudi#9988776655#12345 | Validation Closed  |                   |                             | Email - Closed Status |
      | Any             | Off              | Off               | Fax                      | 1234567891#12345                            | Validation Pending |                   |                             | Fax - Hold Status     |
      | Any             | Off              | On                | Fax                      | 1234567891#12345                            | Validation Closed  |                   |                             | Fax - Closed Status   |
      | Any             | Off              | Off               | Phone (Inbound/Outbound) | 9988776655#ggudi#title#12345                | Validation Pending |                   |                             | Phone - Hold Status   |
      | Manual          | Off              | On                | Phone (Inbound/Outbound) | 9988776655#ggudi#title#12345                | Validation Closed  |                   |                             | Phone - Closed Status |

  @Regression @PayCRMDailyGrind @SupportCases @CPAY-3026 @CPAY-2881 @CPAY-2879
  Scenario: Modules : Daily Grind - Support cases - Login to PayNet and validate add, Edit Internal Attachments
    Given User login to the "PayCRM" application using "qaDetails" credentials
    Then Validate user can Login to PayNet from Support cases - Show page
    And User validates Support cases "Add" Internal Attachment and validate download attachment from show dialog
      | Name                  | Description                           | AttachmentType | FileToUpload                |
      | AddInternalAttachment | Adding Automation internal attachment | Document       | CompanyAttachment_dummy.pdf |
    And User validates Support cases "Edit" Internal Attachment and validate download attachment from show dialog
      | Name                   | Description                            | AttachmentType | FileToUpload                 |
      | EditInternalAttachment | Editing Automation internal attachment | Other          | SupplierAttachment_dummy.pdf |
    Then User validates reassign functionality for the Support Case

  @Regression @PayCRMDailyGrind @SupportCases @CPAY-2960 @CPAY-2978 @CPAY-2939 @CPAY-2987
  Scenario: Modules : Daily Grind - support case - Unable to validate case for manual support case and Reports of Support Case
    Given User login to the "PayCRM" application using "qaDetails" credentials
    Then User validates the Unable to validate case for "Automatic" support case
      | SupportCaseType | UnableToValidate | ValidateCompleted | ActivityType | ActivityTypeSpecificData | CaseOutcome | CaseOutcomeReason | FileToUpload                | Notes                       |
      | Automatic       | On               | Off               | Website      |                          |             |                   | CompanyAttachment_dummy.pdf | Unable to validate scenario |
    Then User validates the Unable to validate case for "Manual" support case
      | SupportCaseType | UnableToValidate | ValidateCompleted | ActivityType | ActivityTypeSpecificData | CaseOutcome | CaseOutcomeReason | FileToUpload | Notes                       |
      | Manual          | On               | Off               | Website      |                          |             |                   |              | Unable to validate scenario |
    And User validates Reports of Support Case

  @Regression @PayCRMDailyGrind @SupportCases @CPAY-2966 @CPAY-2967 @CPAY-2968 @CPAY-2969 @CPAY-2970 @CPAY-2971 @CPAY-2987
  Scenario: Modules : Daily Grind - Support Case- StopFraud validations to close Different Support Case Types
    Given User login to the "PayCRM" application using "qaDetails" credentials
    Then User validates the "Automatic" support case for case type as "Account Validation" and area name as "Payment Information"
      | SupportCaseType | UnableToValidate | ValidateCompleted | ActivityType | ActivityTypeSpecificData | CaseOutcome       | CaseOutcomeReason | FileToUpload                | Notes                        |
      | Automatic       | off              | on                | Website      | qaURL                    | Validation Closed |                   | CompanyAttachment_dummy.pdf | validation complete scenario |
    Then User validates the "Automatic" support case for case type as "Address Validation" and area name as "Company Information"
      | SupportCaseType | UnableToValidate | ValidateCompleted | ActivityType | ActivityTypeSpecificData | CaseOutcome       | CaseOutcomeReason | FileToUpload                | Notes                        |
      | Automatic       | off              | on                | Website      | qaURL                    | Validation Closed |                   | CompanyAttachment_dummy.pdf | validation complete scenario |
    Then User validates the "Automatic" support case for case type as "PAF" and area name as "Company Information"
      | SupportCaseType | UnableToValidate | ValidateCompleted | ActivityType | ActivityTypeSpecificData | CaseOutcome       | CaseOutcomeReason | FileToUpload                | Notes                        |
      | Automatic       | off              | on                | Website      | qaURL                    | Validation Closed |                   | CompanyAttachment_dummy.pdf | validation complete scenario |
    Then User validates the "Automatic" support case for case type as "Dual Approval" and area name as "Payment Information"
      | SupportCaseType | UnableToValidate | ValidateCompleted | ActivityType | ActivityTypeSpecificData | CaseOutcome       | CaseOutcomeReason | FileToUpload                | Notes                        |
      | Automatic       | off              | on                | Website      | qaURL                    | Validation Closed |                   | CompanyAttachment_dummy.pdf | validation complete scenario |
    Then User validates the "Automatic" support case for case type as "OFAC Validation" and area name as "Company Information"
      | SupportCaseType | UnableToValidate | ValidateCompleted | ActivityType | ActivityTypeSpecificData | CaseOutcome       | CaseOutcomeReason | FileToUpload                | Notes                        |
      | Automatic       | off              | on                | Website      | qaURL                    | Validation Closed |                   | CompanyAttachment_dummy.pdf | validation complete scenario |
    Then User validates the "Automatic" support case for case type as "TIN Validation" and area name as "Company Information"
      | SupportCaseType | UnableToValidate | ValidateCompleted | ActivityType | ActivityTypeSpecificData | CaseOutcome       | CaseOutcomeReason | FileToUpload                | Notes                        |
      | Automatic       | off              | on                | Website      | qaURL                    | Validation Closed |                   | CompanyAttachment_dummy.pdf | validation complete scenario |

  @Regression @PayCRMDailyGrind @CPAY-2766
  Scenario: Support cases- Pre-Note failure case when an account validation is marked Unable to Validate
    Given User login to the "PayCRM" application using "qaDetails" credentials
    Then User navigate to stopFraud and mark TGBR as UnableToValidate and create Pre-Note Failure case in support case

  @Regression @PayCRMDailyGrind @SupportCases @CPAY-2972 @CPAY-2973
  Scenario: Modules : Daily Grind - Support case should be created when TIN validation case is marked Completed OR Unable to validate from stop fraud
    Given User login to the "PayCRM" application using "qaDetails" credentials
    Then User validates Support case should be created when the case is marked "Unable to Validate" from stop fraud
      | ValidationType     | ValidationMode | Notes                                         |
      | TIN Matching       |                | TIN Validation Notes - Unable to Validate     |
      | OFAC Scan          |                | OFAC Scan Notes - Unable to Validate          |
      | Address Validation |                | Address Validation Notes - Unable to Validate |
    Then User validates Support case should be created when the case is marked "Validate" from stop fraud
      | ValidationType     | ValidationMode  | FileToUpload                | Notes                                      |
      | TIN Matching       | Manual Validate | CompanyAttachment_dummy.pdf | TIN Validation Notes - Manual Validate     |
      | OFAC Scan          | Manual Validate | CompanyAttachment_dummy.pdf | OFAC Scan Notes - Manual Validate          |
      | Address Validation | Manual Validate | CompanyAttachment_dummy.pdf | Address Validation Notes - Manual Validate |

  @Regression @PayCRMDailyGrind @SupportCases @CPAY-3847 @CPAY-3855 @CPAY-3987
  Scenario: Modules : Daily Grind - Support case grid should have "Action Date" & "Country Code" columns
    Given User login to the "PayCRM" application using "qaDetails" credentials
    Then User validate support case grid columns with searching, sorting and filtering ActionDate and Country code column
    And User validate Closed tab functionality

  @Regression @PayCRMDailyGrind @SupportCases @CPAY-3864
  Scenario: Modules : Daily Grind - support case - log notes on a closed support case
    Given User login to the "PayCRM" application using "qaDetails" credentials
    And User validates Reports of Support Case
    Then open Closed support case and verify Log notes functionality

  @RegressionPending @PayCRMDailyGrind @CPAY-4387 @CPAY-4390 @CPAY-4391
  Scenario: Modules : Daily Grind - Verify Case Assignment for Enrollment case with offer type as VCA and with "Was this offer accepted?" as Yes->Enroll
    Given User login to the "PayCRM" application using "qaDetails" credentials
    And User "Disable" the following "Super Admin" roles And perform Link OR unlink Users To selected "Enrollments#Add,CMVP#Add,SMVP#Add" Work Queue
    Then User opens "New" enrollment Case from Program Management And User Enroll the cases for Offer type "Virtual Card" and Was this offer accepted as "Yes"
      | supplierName  | TIN | WebAddress | Phone | OriginationSource | ContactName | ContactEmail         | ContactPhone | ContactType | AddressType | Country | Address1 | City   | State      | PostalCode | Amount | CheckCount | offerTypeOptions                           | OfferAcceptedOptions                                            | Notes            |
      | autoSupplier_ |     |            |       | Other             | Auto User   | ggudi@corcentric.com | 9876234543   | Cell        | Primary     | US      | Street1  | Alaska | California | 55555-5555 | 5      | 2          | ggudi@corcentric.com#(998) 877-6655#US#USD | Email Address#ggudi#ggudi@corcentric.com#Agent Enrollment#No#No | Automation Notes |
    Then User opens "New" enrollment Case from Program Management And User Enroll the cases for Offer type "Virtual Card" and Was this offer accepted as "No"
      | supplierName  | TIN | WebAddress | Phone | OriginationSource | ContactName | ContactEmail         | ContactPhone | ContactType | AddressType | Country | Address1 | City   | State      | PostalCode | Amount | CheckCount | offerTypeOptions                                               | OfferAcceptedOptions                                            | Notes            |
      | autoSupplier_ |     |            |       | Other             | Auto User   | ggudi@corcentric.com | 9876234543   | Cell        | Primary     | US      | Street1  | Alaska | California | 55555-5555 | 5      | 2          | ggudi@corcentric.com#(998) 877-6655#US#USD                     | Email Address#ggudi#ggudi@corcentric.com#Agent Enrollment#No#No | Automation Notes |
      | autoSupplier_ |     |            |       | Other             | Auto User   | ggudi@corcentric.com | 9876234543   | Cell        | Primary     | US      | Street1  | Alaska | California | 55555-5555 | 5      | 2          | ggudi@corcentric.com###Savings#CitiBank#ggudi##12345678#US#USD | Email Address#ggudi#ggudi@corcentric.com#Agent Enrollment#No#No | Automation Notes |
      | autoSupplier_ |     |            |       | Other             | Auto User   | ggudi@corcentric.com | 9876234543   | Cell        | Primary     | US      | Street1  | Alaska | California | 55555-5555 | 5      | 2          | ggudi@corcentric.com###Savings#CitiBank#ggudi##12345678#US#USD | Email Address#ggudi#ggudi@corcentric.com#Agent Enrollment#No#No | Automation Notes |
      | autoSupplier_ |     |            |       | Other             | Auto User   | ggudi@corcentric.com | 9876234543   | Cell        | Primary     | US      | Street1  | Alaska | California | 55555-5555 | 5      | 2          | ggudi@corcentric.com###Savings#CitiBank#ggudi##12345678#US#USD | Email Address#ggudi#ggudi@corcentric.com#Agent Enrollment#No#No | Automation Notes |
    Then User opens "New" enrollment Case from Program Management And User Enroll the cases for Offer type "Virtual Card" and Was this offer accepted as "Not Yet"
      | supplierName  | TIN | WebAddress | Phone | OriginationSource | ContactName | ContactEmail         | ContactPhone | ContactType | AddressType | Country | Address1 | City   | State      | PostalCode | Amount | CheckCount | offerTypeOptions                           | OfferAcceptedOptions                              | Notes            |
      | autoSupplier_ |     |            |       | Other             | Auto User   | ggudi@corcentric.com | 9876234543   | Cell        | Primary     | US      | Street1  | Alaska | California | 55555-5555 | 5      | 2          | ggudi@corcentric.com#(998) 877-6655#US#USD | Phone#(987) 654-3219#Inbound#Information Provided | Automation Notes |
    And User "Enable" the following "Super Admin" roles And perform Link OR unlink Users To selected "" Work Queue

  @Regression @CPAY-4716
  Scenario:  Verify Audit info tab data under table for stopfraud and support cases
    Given User login to the "PayCRM" application using "qaDetails" credentials
    And Create supplier and enroll it with offer type ACH Plus
      | supplierName  | OriginationSource | ContactName | ContactEmail         | ContactPhone | ContactType | Country | Address1 | City   | State      | PostalCode | Amount | CheckCount | Notes            |
      | autoSupplier_ | Other             | Auto User   | ggudi@corcentric.com | 9876234543   | Cell        | US      | Street1  | Alaska | California | 55555-5555 | 5      | 2          | Automation Notes |
    And Navigate to StopFraud and SupportCases and verify Audit Info table details

  @Regression @CPAY-4681 @CPAY-4680
  Scenario: Verify Clear Unable to Validate for closed case
    Given User login to the "PayCRM" application using "qaDetails" credentials
    Then User performs Clear Unable to Validate and verifies against stop fraud

  @Regression @CPAY-4694
  Scenario: Verify Case outcome reason for support case
    Given User login to the "PayCRM" application using "qaDetails" credentials
    Then User verifies case outcome reason using "automatic" case for support case
    Then User verifies case outcome reason using "manual" case for support case

  @Regression @CPAY-4819 @Test123Shid
  Scenario: Verify related cases tab in Support Cases
    Given User login to the "PayCRM" application using "qaDetails" credentials
    Then User verifies all related cases inside Related Case tab

  @Regression13
  Scenario: Edit company details
    Given user login to the "PayCRM" application using "qaDetails" credentials
    Then Click on companies and edit the details
      | Name     | Tickler | Website                   | PaynetCompanyID | SalesforceID | Phone      | Fax        | ZipCode    | Parent    | Locale | Language | OriginationSource | CompanyType | Status  | Priority |
      | editAuto | Ticker1 | https://demo.vendorin.com |                 | 987654321    | 9900112233 | 9000000009 | 55555-4444 | Acculogix | UK     | English  | Automation        | Prospect    | Initial | High     |
