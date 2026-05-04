Derived from Ai&Me Project Management page [here](https://github.com/orgs/codependant-variables/projects/3) 

# Table of contents
- [[#Abstract]]
- [[#Sprint 1]]
	- [[#Objectives]]
	- [[#Sprint 2 Hopeful Outcome]]
		- [[#Epic 1 Consistency & Polish - Assigned to Lewis and Euan]]
		- [[#Epic 2 Basic User Experience]]
		- [[#Epic 3 Authentication, User Identity]]
		- [[#Epic 4 DB Implementation]]
- [[#What I NEED from the team today/sunday]]
- [[#Stories that weren't considered yet.]]
- [[#Housekeeping before Jonte tangents ( KMS]]
- [[#Conclusion]]
	- [[#Retro]]

## Abstract:
Derived from Atlassian's article [here](https://www.atlassian.com/agile/scrum/sprint-planning), Sprint planning is a preparation process for an upcoming event called a sprint. Process involves identifying what tasks will be completed in the sprint and how that work will be achieved.

Benefits of this process include the team fully understands what need's to be accomplished during sprints. Increasing focus, helping the team break stuff down in to smaller, manageable tasks. and align on priorities.

Recommendations for this plan include:
- Agreed start and finish dates for the proper "Sprint 2".
- What are we focusing our attention on in the program and how we are splitting up the work.
- Stepping stones for what work should be done first.
## Sprint 2

### Objectives:
- Starting the Development of the Project; Front-end, Back-end.
- App stores data in a database.
- Test suite with evidence of Red–Green–Refactor
- Full-Stack prototype

### Sprint 2 Hopeful Outcome:
To have a working demo: UI, login and persistent database.
#### Epic 1: Consistency & Polish - Assigned to Lewis and Euan
Objective: Setup MVC Structure

TLDR - Create pages from [Figma](https://www.figma.com/design/j7xM3oUzS0EVGIzsvNk1FY/AI-ME?node-id=40-41&t=QjJdZDdwvEZ6RoJL-1) in our program.

Resources referenced:
- [Figma](https://www.figma.com/design/j7xM3oUzS0EVGIzsvNk1FY/AI-ME?node-id=40-41&t=QjJdZDdwvEZ6RoJL-1)

| Issue # | Features                                                                                         | Reason                                                                                   | Status (As of 20/4) |
| ------- | ------------------------------------------------------------------------------------------------ | ---------------------------------------------------------------------------------------- | ------------------- |
| 43      | [**User Dashboard**](https://github.com/codependant-variables/ai-and-me/issues/43)               | The "home base" after login                                                              | 🏎️                 |
| 44      | [**Recent Activity**](https://github.com/codependant-variables/ai-and-me/issues/44)              | Give the dashboard immediate value show users what they last did                         | 🏎️                 |
| **50**  | [**Clear and Readable Interface**](https://github.com/codependant-variables/ai-and-me/issues/50) | Typography, spacing, color standards set early *(Very similar if not the same as above)* | 🏎️                 |
| **51**  | [**Navigation Menu Structure**](https://github.com/codependant-variables/ai-and-me/issues/51)    | The user needs to see a well structured nav menu for the app (kinda similar to #53)      | 🏎️                 |
| 57      | [**Interface Theming**](https://github.com/codependant-variables/ai-and-me/issues/57)            | Light/dark mode or themes to meet our accessibility objective                            | ✅                   |
| 125     | [Create pages and controllers](https://github.com/codependant-variables/ai-and-me/issues/125)    | Pages that are not made yet                                                              | ✅                   |
| 61      | [Layout Orientation](https://github.com/codependant-variables/ai-and-me/issues/61)               | Vertical/horizontal for device diversity.                                                | 🏎️                 |
| 67      | [Consistency across screens](https://github.com/codependant-variables/ai-and-me/issues/67)       | Harmonize the style sheet, font, typography program wide                                 | ✅                   |

#### Epic 2: Basic User Experience
Objectives: Implement Functionality relating to the user.

| Issue # | Features                                                                                       | Reason                                                                                 | Status (As of 20/4) |
| ------- | ---------------------------------------------------------------------------------------------- | -------------------------------------------------------------------------------------- | ------------------- |
| 38      | [**First Time Visitor**](https://github.com/codependant-variables/ai-and-me/issues/38)         | non-registered user flow the very first thing a new user sees                          | 🏎️                 |
| 39      | [**Unintrusive Signup Option**](https://github.com/codependant-variables/ai-and-me/issues/39)  | Gentle nudge to register without blocking the experience                               | ✅                   |
| 41      | [**Guest App Use**](https://github.com/codependant-variables/ai-and-me/issues/41)              | Let users explore without committing to sign-up (Establish what they can and cant do.) | ✅                   |
| 40      | [**Quick Access**](https://github.com/codependant-variables/ai-and-me/issues/40)               | Reduces friction for returning users                                                   | 🏎️                 |
| 56      | [Setting rollback](https://github.com/codependant-variables/ai-and-me/issues/56)               | Reset to default settings                                                              | 🏎️                 |
| 58      | [Daily Quizzes](https://github.com/codependant-variables/ai-and-me/issues/58)                  | Table and functionality for quizzes                                                    | 🏎️                 |
| 59      | [Daily Puzzles](https://github.com/codependant-variables/ai-and-me/issues/59)                  | Implement a puzzle related functionality                                               | 🏎️                 |
| 68      | [Notify Over-Reliance on AI](https://github.com/codependant-variables/ai-and-me/issues/68)     | Implement a feature to check over reliance on AI                                       | 🏎️                 |
| 60      | [Settings Menu](https://github.com/codependant-variables/ai-and-me/issues/60)                  |                                                                                        | 🏎️                 |
| 64      | [Fast App](https://github.com/codependant-variables/ai-and-me/issues/64)                       | Make the app faster???                                                                 | 🏎️                 |
| 69      | [Skill Practice Recommendations](https://github.com/codependant-variables/ai-and-me/issues/69) | Implement an algorithm that recommends quizzes                                         | 🏎️                 |
| 70      | [Quick Regular AI Checkin](https://github.com/codependant-variables/ai-and-me/issues/70)       | Easy to answer questions to uplift skill practice recommendations (69)                 | 🏎️                 |
| 71      | [Insight Cards](https://github.com/codependant-variables/ai-and-me/issues/71)                  | news/useful info about subject matter.                                                 | 🏎️                 |
| 93      | [Error Handling](https://github.com/codependant-variables/ai-and-me/issues/93)                 | Human readable messages for possible errors.                                           | ✅                   |
| 94      | [Input Validation](https://github.com/codependant-variables/ai-and-me/issues/94)               | Confirming whether we have caught abnormal data entered.                               | 🏎️                 |
| 115     | [Quiz Creation](https://github.com/codependant-variables/ai-and-me/issues/115)                 | Creation of a Template using UI and edge functionality                                 | 🏎️                 |
#### Epic 3: Authentication, User Identity
Objectives:
- Implementation for Database.
- Write unit tests for authentication
- Write tests for core features

| Issue # | Features                                                                                 | Reason                                                                                   | Status (As of 20/4) |
| ------- | ---------------------------------------------------------------------------------------- | ---------------------------------------------------------------------------------------- | ------------------- |
| 45      | [User Profile](https://github.com/codependant-variables/ai-and-me/issues/45)             | Implement distinct profile related material                                              | ✅                   |
| 46      | [User Data Transparency](https://github.com/codependant-variables/ai-and-me/issues/46)   | Express to the user what specifically is what their data being used for.                 | 🏎️                 |
| 48      | [**Register Details**](https://github.com/codependant-variables/ai-and-me/issues/48)     | Account creation flow (Incl database option)                                             | ✅                   |
| 49      | [**Log In**](https://github.com/codependant-variables/ai-and-me/issues/49)               | Account access (Incl database option)                                                    | ✅                   |
| 47      | [**Log Out**](https://github.com/codependant-variables/ai-and-me/issues/47)              | Session management                                                                       | ✅                   |
| 54      | [**Password View**](https://github.com/codependant-variables/ai-and-me/issues/54)        | Essential UX details for auth forms                                                      | ✅                   |
| 55      | [Profile Deletion](https://github.com/codependant-variables/ai-and-me/issues/55)         | User may delete their profile along with all their data.                                 | 🏎️                 |
| 63      | [Persistent Personal Data](https://github.com/codependant-variables/ai-and-me/issues/63) | Algorithmic data about the user is retained until inactivity expiry or request deletion. | ✅                   |
| 72      | [Account Security](https://github.com/codependant-variables/ai-and-me/issues/72)         | Password related material for security uplift                                            | ✅                   |
| 95      | [One time Auth Code](https://github.com/codependant-variables/ai-and-me/issues/95)       | Assist with security...                                                                  | 🏎️                 |

#### Epic 4: DB Implementation
Objectives:
- Implement SQLite Database in-line with [our agreed diagram](https://drive.google.com/file/d/1NfJZajXvnGH1qWkBwXtud5ZD9HLEKT0y/view) 
- Create model classes
- Implement persistence layer (DAO)

| Issue # | DB Task              | Reason                                                                                                 | Status (As of 20/4) |
| ------- | -------------------- | ------------------------------------------------------------------------------------------------------ | ------------------- |
| 100     | Quiz Templates table | Uplifted with [58 - Daily Quizzes](https://github.com/codependant-variables/ai-and-me/issues/58)       | ✅                   |
| 86      | Categories table     | Uplifted with [86 - Quiz Categories](https://github.com/codependant-variables/ai-and-me/issues/86)     | ✅                   |
| 34      | User table           | Uplifted with [Daniel Work Week 6](https://github.com/codependant-variables/ai-and-me/pull/34)         | ✅                   |
| 107     | QuizAttempts table   | Uplifted with [View past quiz attempts](https://github.com/codependant-variables/ai-and-me/issues/107) | 🏎️                 |

## Endorsements:

**1 of 3 things from each of you:**
==Endorsement?== - *You are happy with this.*
==Conditional Endorsement?== - *You require some changes to be happy with this.*
==No Endorsement?== - You can't decide or are not happy with this.

Majority Rules.

**Endorsement Table**:

| #   | **Objective**                                                 | ==Emma?==      | Reason? | ==Euan?==      | Reason?                        | ==Lewis?==     | Reason? | ==Will?==      | Reason? | ==Daniel?==    | Reason? | ==Jonte?==     | Reason? |
| --- | ------------------------------------------------------------- | -------------- | ------- | -------------- | ------------------------------ | -------------- | ------- | -------------- | ------- | -------------- | ------- | -------------- | ------- |
| 1   | [[#Epic 1 Consistency & Polish - Assigned to Lewis and Euan]] | Fully Endorsed |         | Fully Endorsed | Happy with all of this so far. | Fully Endorsed |         | Fully Endorsed |         | Fully Endorsed |         | Fully Endorsed |         |
| 2   | [[#Epic 2 Basic User Experience]]                             | Fully Endorsed |         | Fully Endorsed | as above                       | Fully Endorsed |         | Fully Endorsed |         | Fully Endorsed |         | Fully Endorsed |         |
| 3   | [[#Epic 3 Authentication, User Identity]]                     | Fully Endorsed |         | Fully Endorsed | as above                       | Fully Endorsed |         | Fully Endorsed |         | Fully Endorsed |         | Fully Endorsed |         |
| 4   | [[#Epic 4 DB Implementation]]                                 | Fully Endorsed |         | Fully Endorsed | as above                       | Fully Endorsed |         | Fully Endorsed |         | Fully Endorsed |         | Fully Endorsed |         |
| 5?  | If applicable                                                 |                |         |                |                                |                |         |                |         |                |         |                |         |
|     |                                                               |                |         |                |                                |                |         |                |         |                |         |                |         |

## Conclusion
Sprint 2's total user stories - 34
Archived - 16 (13 from this sprint) - well done! 🎉
Outstanding - 25 (for this sprint) will be carried over to sprint 3
### Retro day
**What went well with this sprint:**
- Managing branches and Pull requests being conducted really well.

**What didn't go well:**
- Seemed to be too many User stories for everyone to handle. (Shared by few people)
- Inconsistency on sprint planning

**What could have worked better?**
- Sprint planning process shouldn't just be Emma and I, because we can't always keep track of what's happening with ever ticket.


**What we could do better in Sprint 3?**
- Better transparency with the team on what user stories to focus our attention on. We as a tea


