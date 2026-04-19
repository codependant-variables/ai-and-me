Derived from Ai&Me Project Management page [here](https://github.com/orgs/codependant-variables/projects/3) 

# Table of contents
- [[#Abstract]]
- [[#Sprint 2]]
	- [[#Objectives]]
		- [[#Priority 1 App Shell & UI Foundation]]
		- [[#Priority 2 Basic User Experience]]
		- [[#Priority 3 Authentication & User Identity]]
		- [[#Priority 4 User Home & Dashboard]]
- [[#What I NEED from the team today/sunday]]
- [[#Stories that weren't considered yet.]]
- [[#Housekeeping]]
- [[#Conclusion]]

## Abstract:
Derived from Atlassian's article [here](https://www.atlassian.com/agile/scrum/sprint-planning), Sprint planning is a preparation process for an upcoming event called a sprint. Process involves identifying what tasks will be completed in the sprint and how that work will be achieved.

Benefits of this process include the team fully understands what need's to be accomplished during sprints. Increasing focus, helping the team break stuff down in to smaller, manageable tasks. and align on priorities.

Recommendations for this plan include:
- Agreed start and finish dates for the proper "Sprint 2".
- What are we focusing our attention on in the program and how we are splitting up the work.
- Stepping stones for what work should be done first.

If people are worried of not meeting the time allotment requirement,
## Sprint 2

### Objectives:
Starting the Development of the Project; Front-end, Back-end.

Total for this sprint is 16.
#### Epic 1: ==App Shell & UI Foundation==

| Issue # | User Story                                                                                       | Reason                                                                                   |
| ------- | ------------------------------------------------------------------------------------------------ | ---------------------------------------------------------------------------------------- |
| 30      | [**Consistent UI Layout**](https://github.com/codependant-variables/ai-and-me/issues/52)         | Standardise and Implement consistency (i.e. spacing, components, placement etc.)         |
| 50      | [**Clear and Readable Interface**](https://github.com/codependant-variables/ai-and-me/issues/50) | Typography, spacing, color standards set early *(Very similar if not the same as above)* |
| 53      | [**Navigation Menu**](https://github.com/codependant-variables/ai-and-me/issues/53)              | Support user finding what they need efficiently                                          |
| 51      | [**Navigation Menu Structure**](https://github.com/codependant-variables/ai-and-me/issues/51)    | The user needs to see a well structured nav menu for the app (kinda similar to #53)      |
| 66      | [**Nav Menu Items**](https://github.com/codependant-variables/ai-and-me/issues/66)               | Establish the menu with actual routes/links                                              |
| 57      | [**Interface Theming**](https://github.com/codependant-variables/ai-and-me/issues/57)            | Light/dark mode or themes to meet our accessibility objective                            |
#### Epic 2: ==Basic User Experience==

| Issue # | User Story                                                                                    | Reason                                                                                 |
| ------- | --------------------------------------------------------------------------------------------- | -------------------------------------------------------------------------------------- |
| 38      | [**First Time Visitor**](https://github.com/codependant-variables/ai-and-me/issues/38)        | non-registered user flow the very first thing a new user sees                          |
| 41      | [**Guest App Use**](https://github.com/codependant-variables/ai-and-me/issues/41)             | Let users explore without committing to sign-up (Establish what they can and cant do.) |
| 39      | [**Unintrusive Signup Option**](https://github.com/codependant-variables/ai-and-me/issues/39) | Gentle nudge to register without blocking the experience                               |
| 40      | [**Quick Access**](https://github.com/codependant-variables/ai-and-me/issues/40)              | Reduces friction for returning users                                                   |

#### Epic 3: ==Authentication & User Identity==

| Issue # | User Story                                                                           | Reason                                               |
| ------- | ------------------------------------------------------------------------------------ | ---------------------------------------------------- |
| 48      | [**Register Details**](https://github.com/codependant-variables/ai-and-me/issues/48) | Account creation flow (Incl or Spread out an option) |
| 49      | [**Log In**](https://github.com/codependant-variables/ai-and-me/issues/49)           | Account access                                       |
| 47      | [**Log Out**](https://github.com/codependant-variables/ai-and-me/issues/47)          | Session management                                   |
| 54      | [**Password View**](https://github.com/codependant-variables/ai-and-me/issues/54)    | Essential UX details for auth forms                  |


#### Epic 4: ==User Home & Dashboard==

| Issue # | User Story                                                                          | Reason                                                           |
| ------- | ----------------------------------------------------------------------------------- | ---------------------------------------------------------------- |
| 43      | [**User Dashboard**](https://github.com/codependant-variables/ai-and-me/issues/43)  | The "home base" after login                                      |
| 44      | [**Recent Activity**](https://github.com/codependant-variables/ai-and-me/issues/44) | Give the dashboard immediate value show users what they last did |



## ==What I NEED from the team Today/Sunday:==

**1 of 3 things from each of you:**
==Endorsement?== - *You are happy with this.*
==Conditional Endorsement?== - *You require some changes to be happy with this.*
==No Endorsement?== - You can't decide or are not happy with this.

Majority Rules.

**Endorsement Table**:

| #   | **Objective**                                  | ==Emma?== | Reason? | ==Euan?==      | Reason?                        | ==Lewis?== | Reason? | ==Will?==      | Reason? | ==Daniel?== | Reason? | ==Jonte?== | Reason? |
| --- | ---------------------------------------------- | --------- | ------- | -------------- | ------------------------------ | ---------- | ------- | -------------- | ------- | ----------- | ------- | ---------- | ------- |
| 1   | [[#Priority 1 App Shell & UI Foundation]]      |           |         | Fully Endorsed | Happy with all of this so far. |            |         | Fully Endorsed |         |             |         |            |         |
| 2   | [[#Priority 2 Basic User Experience]]          |           |         | Fully Endorsed | as above                       |            |         | Fully Endorsed |         |             |         |            |         |
| 3   | [[#Priority 3 Authentication & User Identity]] |           |         | Fully Endorsed | as above                       |            |         | Fully Endorsed |         |             |         |            |         |
| 4   | [[#Priority 4 User Home & Dashboard]]          |           |         | Fully Endorsed | as above                       |            |         | Fully Endorsed |         |             |         |            |         |
| 5?  | If applicable                                  |           |         |                |                                |            |         |                |         |             |         |            |         |
|     |                                                |           |         |                |                                |            |         |                |         |             |         |            |         |

Are their any preferences?
## Stories that weren't considered yet.

| User Story                         | Sprint | Why Defer?                                             |
| ---------------------------------- | ------ | ------------------------------------------------------ |
| **User Profile**                   | 2      | Needs auth system complete                             |
| **Profile Deletion**               | 2      | Needs profile to exist first                           |
| **User Data Transparency**         | 2      | Needs user data being stored first                     |
| **Settings Menu**                  | 2      | Needs UI foundation + auth                             |
| **Layout Customisation**           | 2      | Needs settings infrastructure                          |
| **Setting Rollback**               | 2–3    | Needs settings to exist first                          |
| **Persistent Personal Data**       | 2      | Backend data layer needs core features generating data |
| **Fast App (Database)**            | 2–3    | Optimization premature before features exist           |
| **Consistent UI Across Screens**   | 2      | Refinement pass once more screens exist                |
| **Account Security**               | 2      | Enhanced security after basic auth works               |
| **Daily Quizzes**                  | 3      | Core content needs foundation                          |
| **Daily Puzzles**                  | 3      | Core content needs foundation                          |
| **Quick Daily Skill Exercises**    | 3      | Content feature needs dashboard + content engine       |
| **Insight Cards**                  | 3–4    | Needs user activity data                               |
| **Skill Practice Recommendations** | 3–4    | Needs usage patterns                                   |
| **Notify Over-Reliance on AI**     | 4      | Advanced needs significant usage data                  |
## Housekeeping
Housekeeping for Jonte in case he forgets friday.
- !If a user story has been majority endorsed for sprint 1, re-tag status in projects from "Planning" to "Todo" as a backlog for everyone to see what to focus on.
- !As is was preparing this sprint plan draft, I had added another bar called "Created By" in the [Projects] board and added everyone's name to their respective user story they created. ==(Probs explain this more simply for everyone.)==, and Freed up assignee's tab.
	- Reason was because when we use the swim lanes. we can see what we have picked and what we are working on. as opposed to having a large list of things we may not be doing ourselves ==(EXPLAIN THIS BETTER)==.
	- Assigning people to tasks so they aren't gonna be worried about having stuff for the deadline?
- !Happy with it in its own folder? or another spot?
## Conclusion
Preferences for User story tasks?

Assignments via [Sprint x]()
