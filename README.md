# RestAssuredBDDFramework - ToolsQA BookStore API

RestAssured + Cucumber (BDD) + TestNG framework for the ToolsQA BookStore API,
covering both the **Account** and **BookStore** modules.

## What's implemented against your requirements

| Requirement | Where |
|---|---|
| No hardcoded values | `config.properties` + `ConfigReader` (URLs/ISBNs) + `TestContext` (runtime-chained token/userId/username) + `RandomDataGenerator` (unique creds per run) |
| Separate feature file per CRUD operation | `src/test/resources/features/account/*.feature` and `.../bookstore/*.feature` - one file per Create/Read/Update/Delete operation, not one giant Account.feature/BookStore.feature |
| Cucumber report | `reports/cucumber-report/cucumber.html` (native HTML) + `.json` + `.xml`, generated automatically by `TestRunner`'s `@CucumberOptions(plugin = ...)` |
| Extent report | `reports/spark/ExtentSparkReport.html`, auto-generated from the same Cucumber run via the `extentreports-cucumber7-adapter` dependency + `extent.properties` |
| JSON schema validation | `utilities/JsonSchemaValidatorUtil.java` + schemas in `src/test/resources/testdata/schemas/*.json` |
| Request chaining | `utilities/TestContext.java`, injected via Cucumber's PicoContainer into `Hooks` and both StepDefinitions classes - token/userId flow from Account steps into BookStore steps within a scenario |
| Reusable components | `api/BaseApi.java` (shared RequestSpecification), `api/AccountApi.java` / `api/BookStoreApi.java` (one method per endpoint, called from any feature), `payloads/Payloads.java` (shared body builders) |

## Prerequisites

- JDK 11+
- Maven 3.6+
- Internet access to Maven Central (to download RestAssured/Cucumber/TestNG/Extent dependencies on first build)

## One-time setup

Nothing required — `live.baseUrl` in `src/main/resources/config.properties` is already
set to `https://bookstore.toolsqa.com`. Just run `mvn clean test`.

## Running the suite

```bash
# Runs against the LIVE API (config.properties: default.env=live, live.baseUrl already set)
mvn clean test
```

## Running a subset by tag

Every scenario is tagged (`@Positive`, `@Negative`, `@Edge`, `@Boundary`, `@Smoke`,
`@Chaining`, `@Account`, `@BookStore`, plus `@Create`/`@Read`/`@Update`/`@Delete`).

```bash
mvn clean test -Dcucumber.filter.tags="@Smoke"
mvn clean test -Dcucumber.filter.tags="@BookStore and @Negative"
```

## Where reports land after a run

```
reports/
├── cucumber-report/
│   ├── cucumber.html      <- native Cucumber HTML report
│   ├── cucumber.json
│   └── cucumber.xml
└── spark/
    └── ExtentSparkReport.html   <- Extent report (open this one for the polished dashboard)
```

## Extending the framework

- **New endpoint**: add the path to `endpoints/Routes.java`, add a method to the
  matching `api/*Api.java` class, add a body builder to `payloads/Payloads.java`
  if it needs a request body.
- **New feature file**: one file per CRUD operation, saved under
  `src/test/resources/features/<module>/`. Reuse existing step phrasing where
  possible so you don't duplicate step definitions.
- **New schema**: drop a `.json` file into `src/test/resources/testdata/schemas/`
  and reference it from a feature file step: `the response should match the "your-schema.json" schema`.


# CHUBB – CONTRACTOR ONBOARDING PROCESS

**Document Type:** Standard Operating Procedure (SOP)  
**Process:** Contractor Onboarding  
**Version:** 1.0  
**Prepared By:** [Your Name]  
**Reviewed By:** [Manager / Team Lead]  
**Last Updated:** [DD/MM/YYYY]

---

## 1. Purpose

This document outlines the onboarding process for contractors joining Chubb projects through partner organizations. It provides step-by-step guidance on recruitment, project allocation, team introduction, office access, VDI setup, application access and technical support.

The objective is to ensure that contractors understand the onboarding requirements and can complete the necessary formalities and technical setup before starting their assigned project responsibilities.

## 2. Scope

This process applies to contractors joining Chubb teams through partner organizations. It covers the activities from the interview and project allocation stages through initial onboarding, technical setup, knowledge transfer and project readiness.

---

## 3. Recruitment and Project Allocation Process

### 3.1 Interview Process

An interview will be conducted by a Chubb project representative from the partner organization. The interview typically focuses on technical knowledge and relevant project requirements.

The areas covered during the interview may include:

- Java
- SQL
- Coding and programming concepts
- Basic insurance domain knowledge, where applicable

### 3.2 Interview Outcome

Upon successful completion of the interview, the interviewer will communicate the outcome and connect the candidate with the designated Point of Contact (POC) for further onboarding activities.

The POC may arrange an in-person meeting to discuss project requirements, team expectations and onboarding formalities.

### 3.3 Project Allocation

Following the interview and initial discussion, the contractor will receive a project allocation email from **Kachhi Shivam**, confirming the assignment to a Chubb project.

In some cases, the project allocation email may be received before the in-person discussion.

The allocation communication will provide information regarding the assigned project and further coordination requirements.

---

## 4. Team Introduction and Knowledge Transfer (KT)

After project allocation, the contractor will be introduced to the assigned team and informed about the reporting Team Lead.

The POC or Team Lead will explain the project requirements, team structure, responsibilities and expectations. Knowledge Transfer (KT) sessions will then be arranged to help the contractor understand the application and project workflow. These sessions may be conducted virtually or in person, depending on the team's availability.

Contractors are expected to attend the scheduled KT sessions, maintain notes and actively participate in discussions. Any questions or clarifications regarding the application or project process should be discussed with the respective KT provider.

During KT, the following areas may be covered:

- Project overview and business requirements.
- Application functionality and workflows.
- Tools and technologies used by the team.
- Project-related processes and responsibilities.
- Relevant documentation and reference materials.

### 4.1 Reverse Knowledge Transfer (Reverse KT)

After completing the KT sessions, contractors may be required to conduct Reverse KT to demonstrate their understanding of the application and the processes explained during the knowledge transfer.

During Reverse KT, the contractor will explain the learned concepts and demonstrate the relevant application functionality or workflow to the KT provider or Team Lead.

This helps the team assess the contractor's understanding and identify any areas that require further clarification or training.

---

## 5. Chubb Office Visit and Access Process

### 5.1 Office Location

The Chubb office is located at:

**Chubb Office**

Nexity Building  
Sattva Knowledge City  
Hyderabad, Telangana

### 5.2 Office Visit Authorization

Your POC or Team Lead will inform you when an in-person office visit is required.

Before visiting the office, an OTP-based visitor access request must be arranged. For OTP access arrangements, contact the designated representative.

**Contact Person:**

**Name:** Vasanth Kumar R  
**Email:** vasanth.r-kumar@capgemini.com

Coordinate with the contact person to arrange the required OTP-based office entry.

### 5.3 Office Entry Procedure

Once the visitor access request has been arranged, you will receive an OTP on your registered mobile number.

Follow the below procedure when visiting the office:

1. Enter through **Sattva Knowledge City – Gate 2 only**.
2. Show the received OTP to the security personnel at the entrance gate.
3. Ensure that your entry is recorded in the visitor register.
4. Proceed to **Octave-3, 16th Floor**.

### 5.4 Security Verification

At the Chubb reception or security desk, you may be required to provide the following details for verification:

- Government-issued identification proof.
- Company-assigned laptop for asset verification.
- Assigned POC name.
- Team name.
- Project details, if requested.

A digital copy of your Government-issued identification proof available on your mobile phone is sufficient; carrying the original document is also acceptable.

Keep your laptop available so that the representative can note down its laptop number or asset tag.

**Important:** Make sure you know your assigned POC name and team name before visiting the office.

---

## 6. VDI (Virtual Desktop Infrastructure) Access Request Process

### 6.1 VDI Request Initiation

The VDI access request will be initiated by your assigned Team Lead. Once the request has been raised, keep the VDI ticket number available for future reference and follow-ups.

### 6.2 VDI Request Follow-up and Approval

For VDI request status, approval updates and further coordination, contact the designated representative.

**VDI Follow-up Contact**

**Name:** Suneel Vuba  
**Email:** suneel.vuba@capgemini.com

Follow up with Suneel Vuba regarding the progress of your VDI request and any pending approval or access-related updates.

### 6.3 Additional Information for Account Setup

During the account creation process, a member of the Chubb Onshore team may contact you to confirm your personal details.

You may be asked to provide the expanded spelling of your last name. Ensure that you provide the correct details as requested to avoid delays in account creation and VDI access.

---

## 7. VDI Setup and Login Process

### 7.1 Required Credentials

Before attending the scheduled VDI setup assistance call, ensure that you have received the following credentials:

- Chubb Email ID.
- LAN ID.
- Temporary Password.
- VDI login instructions, if provided.

It is recommended to keep the email containing these credentials easily accessible. You can pin the email in your inbox for quick reference during the setup session. Alternatively, if a credentials document is provided, save it in your approved company OneDrive location.

**Security Note:** Keep your login credentials confidential. Do not share your temporary password or store it in an unsecured location.

### 7.2 Omnissa Horizon Client Installation

Before the scheduled VDI setup call, it is recommended to download and install **Omnissa Horizon Client** on your assigned laptop.

Follow the installation procedure below:

1. Open the Company Portal on your laptop.
2. Search for Omnissa Horizon Client.
3. Select the application and proceed with the download and installation.
4. Follow the on-screen instructions to complete the installation.

Complete the installation before the scheduled setup call to avoid unnecessary delays during the configuration process.

### 7.3 VDI Setup Assistance Through Webex

Once the required credentials are received and the Omnissa Horizon Client is installed, a representative from Chubb will contact you to assist with the initial VDI setup and login process.

The setup assistance will be provided through Webex using a web browser. Join the session and follow the instructions provided by the Chubb representative.

During the session, the representative will guide you through the required login and configuration steps.

### 7.4 Successful VDI Login

After completing the setup instructions, launch Omnissa Horizon Client and follow the login procedure explained during the assistance session.

Use your assigned credentials as instructed and verify that you can successfully access your VDI environment.

---

## 8. VDI Login Issues and Chubb Helpdesk Support

### 8.1 Unable to Access VDI

If your VDI request has been raised and you have received the VDI ticket number but are still unable to log in, contact the Chubb Helpdesk for assistance.

Provide your VDI ticket number and explain the issue you are facing. You can also request an update on the existing ticket if the request is still pending.

### 8.2 Chubb Business Services India (CBSI) Helpdesk

**Helpdesk Number:** 1800-572-2274  
**Helpdesk PIN:** 1708

The above helpdesk contact procedure is applicable to all contractors.

### 8.3 Helpdesk Calling Procedure

Follow the below steps to connect with the US Helpdesk:

1. Call the Chubb Helpdesk number: **1800-572-2274**.
2. Enter the Helpdesk PIN: **1708** when prompted.
3. After entering the PIN, the initial call will disconnect.
4. You will receive a callback from another number.
5. Answer the incoming callback.
6. Select **Option 4** using your phone keypad to connect to the US Helpdesk.
7. Explain your issue to the support representative and provide your VDI ticket number, if applicable.
8. Request the necessary assistance or an update on your existing ticket.

---

## 9. Application Access and Software Requests

After successfully accessing your VDI environment, verify whether all the applications required for your daily work are available.

Some of the commonly used applications include:

- Microsoft Teams
- Microsoft OneNote
- Microsoft Word
- Microsoft Outlook
- Other project-specific tools and applications

If any required application is missing or access has not been provided, raise an IT support ticket through the designated Chubb Helpdesk.

While raising the request, mention the application name, the reason for requiring access and your relevant LAN ID or VDI details, if requested.

Follow up on the ticket until the requested application is installed or access is provided.

---

## 10. Important Contacts

The following contacts can be referred to for assistance during the contractor onboarding and initial setup process.

| Contact | Responsibility | Contact Details |
|---|---|---|
| Assigned POC | Initial onboarding coordination and guidance | As assigned |
| Team Lead | Project onboarding, KT coordination and VDI request initiation | As assigned |
| Kachhi Shivam | Project allocation communication | As assigned |
| Vasanth Kumar R | OTP-based office access arrangements | vasanth.r-kumar@capgemini.com |
| Suneel Vuba | VDI request follow-up, approval updates and coordination | suneel.vuba@capgemini.com |
| CBSI Helpdesk | VDI technical support and ticket updates | 1800-572-2274 |

---

## 11. Final Onboarding and Project Readiness

Once the initial onboarding formalities, office access requirements and technical setup are completed, contractors should ensure that they have the necessary project information, system access and application availability.

The contractor should also complete the KT sessions and Reverse KT activities as coordinated by the assigned team.

The Team Lead or designated POC can confirm whether any additional access, training or project-specific activities are pending before the contractor proceeds with regular project responsibilities.

---

## 12. Contractor Onboarding Checklist

The following checklist can be used to track the completion of the major onboarding activities.

**A. Recruitment and Project Allocation**

- [ ] Interview successfully completed.
- [ ] POC details received.
- [ ] Project allocation email received.
- [ ] Assigned team and Team Lead confirmed.

**B. Team Introduction and Office Access**

- [ ] KT sessions scheduled or completed.
- [ ] Government identification proof available.
- [ ] Laptop number recorded.
- [ ] POC name and team name confirmed.
- [ ] OTP-based office access arranged, if an office visit is required.
- [ ] Office entry formalities completed.

**C. VDI Request and Account Setup**

- [ ] VDI request initiated by Team Lead.
- [ ] VDI ticket number received.
- [ ] Follow-up completed with Suneel Vuba.
- [ ] Last name confirmation completed, if requested.
- [ ] Chubb Email ID received.
- [ ] LAN ID received.
- [ ] Temporary Password received.
- [ ] Credentials email pinned or document saved securely.

**D. VDI Installation and Login**

- [ ] Omnissa Horizon Client downloaded and installed.
- [ ] Webex setup assistance completed.
- [ ] VDI login successfully verified.

**E. Application Access and Knowledge Transfer**

- [ ] Required applications checked.
- [ ] Missing application requests raised, if necessary.
- [ ] KT sessions completed.
- [ ] Reverse KT completed, if required.
- [ ] Pending access and training activities followed up.
- [ ] Project readiness confirmed by the designated team.

---

**End of Document**

**Document Note:** This SOP has been prepared based on the contractor onboarding information and process details collected so far. Office entry instructions, contact information, helpdesk procedures and role responsibilities should be validated with the respective Capgemini and Chubb representatives before official circulation. Any process changes should be updated in the document to keep the information current.
