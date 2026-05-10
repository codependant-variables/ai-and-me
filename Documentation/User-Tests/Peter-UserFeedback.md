## Area of Exploration
**Check-In Flow**

**Date:**  10/5/26
**User:**  Peter Erbeni
**Relation to Team:** Emily (Emma) Robinson
**Device:**  Windows 11
**Version / Build:**  W11 Checkpoint, latest PR: #262

---
## Purpose of User Testing / Task
Tests whether user can confidently create, edit, and manage quizzes using the quiz template structure.
The focus areas are:
- Complete a daily AI usage check-in
- Reflect on AI usage habits
- Review previous check-in history

---
## Pre-Task (First Impressions)
- What do you think the purpose of the check-in feature is?
  To try and personalise the AI behaviour based off the user's dependency so the AI can shift its behaviour to match its user
- How would you expect to start a check-in?

- What stands out visually or functionally?
  big green daily check-in and puzzles
- Do you understand how this relates to AI usage reflection?
  nup
- Does this remind you of any habit-tracking or wellbeing apps?
  Duolingo

---
## During Task
### Completing a Check-In
- Was it easy to find where to begin?
  yes
- How long did it take to complete the check-in?
  a min cause I couldnt do it on the guest account and needed to create an account to complete it
- Did the flow feel smooth and fast?
  unsure
- Were the prompts easy to understand?
  yes
- Did any questions feel repetitive or unnecessary?
  no
- Did you hesitate or feel unsure at any point?
  no
### Prompt Relevance
- Did the prompts feel relevant to your AI usage habits?
  yes
- Did the questions encourage honest reflection?
  no not really
- Were any prompts confusing or unclear?
  the dependent on AI question confused me with "independent" being on the opposite end than it should be.
- Did the prompts feel too broad, too personal, or too generic?
  kind of a bit generic/ broad
### Navigation & Usability
- Was moving between steps intuitive?
  yes
- Were buttons/actions clear?
  yes
- Did anything behave unexpectedly?
    - AI dependence value is set to 100 despite I went to complete independence
    - you can't click on news & reporting
    - creating a template causes buttons to go off screen when entering a new category
    - pressing "going back to quiz library" just goes to an empty page
- Were there any delays, loading issues, or errors?
  unable to submit check in as a guest or at least an unhelpful error as to why I couldnt submit
### Check-In History
- Was it easy to locate previous check-ins?
  had to do some digging to find it
- Did the history view make sense?
  yes
- Could you easily identify trends or past responses?
  not really, a graph could help visualize this
- Did the history feel useful or meaningful?
  eh not really
- Was there enough information shown in history entries?

---
## Post-Task Feedback
### Task Experience
- How easy was it to complete the check-in overall?
  pretty easy
- Did the process feel quick enough for regular use?
  yes
- Would you realistically complete this daily or weekly?
  no
- Did the feature behave as expected?
  yes
- What difficulties or frustrations did you experience?
    - if I click out mid check-in to review something else, the progress of the check-in is not saved which would be inconvienient if I had to redo the quiz on a much larger scale
---
### Reflection & Value
- Did the check-in make you think differently about your AI use?
  no
- Did reflecting on AI usage feel valuable?
  not really
- Did any insights feel meaningful or actionable?
  not really
- Would you continue using this feature over time?
  no because the data does not feel valuable enough, through being difficult to read and make sense of it, to want to checkin
- What would make the reflection experience more useful?
  some visualizations of the data to make it digestible and easy to understand like graphs instead of showing the raw data


---
## Breaking / Edge Case Feedback
User to try one or two edge cases if appropriate.
- What happens if you skip prompts or enter incomplete answers?
  the app will still let me submit the form
- Were validation messages clear and helpful?
  most were except when trying to submit a checkin as a guest. The error did not describe why I couldnt submit
- Could you accidentally lose progress?
  yes technically as while deleting your account is a two button step, it's still possible to accidently delete it when not paying attention
- Did anything feel inconsistent or easy to “break”?
  the light mode icon looks like a settings icon

---
## Overall Experience
- What did you enjoy most about the check-in flow?
  I can review my previous history to see the data
- What did you enjoy least?
  the check-in form felt static
- Did this feature help increase awareness of AI usage habits?
  not really
- What improvements would you suggest?
  making the form a bit more lively and engaging to input and easier to digest the history of your data
- Would this feature motivate long-term reflection or behaviour change?
  no

---
## Additional Observations
- Any confusion around terminology or scoring?
- Any features you expected but didn’t see?
- Any suggestions for improving check-in history or insights?
---
## Developer Notes
(For Emma to fill out after)
Key issues found:

Guest users cannot submit check-ins and the error message does not clearly explain why.
AI dependence slider/scoring appears reversed or incorrect (set to 100 despite selecting independence).
“News & Reporting” section is not clickable.
Creating a new category causes buttons to go off-screen.
“Back to Quiz Library” button leads to an empty page.
Check-in progress is not saved if the user leaves mid-flow.
Previous check-in history is difficult to locate.
History data is hard to interpret and lacks meaningful visualisation.
Validation allows incomplete check-ins to be submitted.
Light mode icon resembles a settings icon, causing confusion.
Check-in feature felt static and not engaging enough for repeated use.

Positive feedback:

Check-in flow was easy to begin and navigate.
Prompts were generally easy to understand.
Buttons and navigation mostly felt intuitive.
History view itself was logically structured once found.
User liked being able to review previous check-in data.
Overall completion process was quick and straightforward.

Suggested improvements:

Improve guest account validation/error messaging.
Fix AI dependence scale/scoring logic.
Add graphs or visual analytics to make trends easier to understand.
Improve discoverability of check-in history.
Add autosave/progress saving during check-ins.
Make the check-in process more interactive or dynamic.
Improve engagement/value proposition so users feel motivated to continue using the feature long-term.
Fix navigation bugs such as empty pages and unclickable sections.
Ensure category creation modals/buttons remain visible on screen.
Clarify iconography, especially the light mode/settings icon confusion.
Add stronger validation for incomplete responses.

Severity / Priority:
Medium–High — Core functionality works, but several usability bugs, navigation issues, and unclear value in the reflection/history experience reduce long-term engagement and trust in the feature. The AI dependence scoring issue and guest submission issue should be prioritised.