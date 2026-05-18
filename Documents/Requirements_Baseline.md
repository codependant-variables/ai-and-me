## Problem Statement
As AI systems become deeply integrated into everyday tasks, users are more likely to increasingly delegate cognitive, creative, and analytical work to to these tools. While this improves efficiency, it has raised concerns about skill atrophy. Skill atrophy, in this context, can be defined as the reduced independent problem-solving ability, and a growing dependency on AI in multiple facets of life.
Currently, there is no widely accepted, accessible way for individuals to understand how their reliance on AI impacts their long-term skills, habits, or learning outcomes. This project aims to address this gap by providing tools to assist with skill retention, track and analyse AI usage, identify patterns of over-reliance, and encourage intentional, balanced engagement with AI technologies.
https://news.harvard.edu/gazette/story/2025/11/is-ai-dulling-our-minds/

The goal of our app, AI&Me, is to encourage users to be critical and discerning with how they use AI tools and understand how it impacts their skills and learning.
## Project Goals and Objectives
### Primary Goals
1. Measure skill retention: Provide users with quantifiable insights into how their skills can change over time when they rely on AI.
2. Track AI usage: Categorise and analyse reported user interaction with AI to identify patterns of dependency.
3. Promote skill preservation: Offer challenges, suggestions, and activities which encourage users to maintain or rebuild crucial skills.
4. Educate users: Provide AI related news and environmental impact estimates to encourage informed and responsible usage.
5. Create a personalised, ongoing journey through user management and persistent storage.
### Secondary Goals
1. Create an intuitive, accessible, and visually clear dashboard interface.
2. Create a scalable system design for additional analytics or features as the project develops.
## Project Scope / Key Features
### In-Scope
- Account creation, possibly user authentication.
- Dashboard with usage insights
- Skill-related quizzes/puzzles and automated scoring
- Skill retention analytics
- Pomodoro style timer for avoiding AI usage during tasks
- AI usage tracking and categorisation based on user reporting
- AI dependency insights
- AI news aggregation
- Environmental impact estimation
- Account System (Login, sign up, profile and profile management)
- Persistent data storage
### Out-of-Scope
- Real-time AI usage monitoring across external apps (OS-level tracking)
- Advanced, personalised AI tutoring
- Third-party integration beyond AI news, or anything in the in-scope category

## Feature Requirements
### Skill Atrophy Measurement
**Description:** Track changes in user skill levels over time by administering repeatable quizzes.
**Functional Requirements**
- Users choose a subject area for a quiz
- System provides a pre-made quiz
- Users answer questions and receive auto-generated scores
- System stores quiz results with timestamps.
- Users retake quizzes after a set interval.
- System compares past and current results to show:
    - Skill retention
    - Skill degradation
    - Skill improvement
- Insights displayed on the dashboard.
  **Non-Functional Requirements**
- Quick quiz generation
- Accessible question formats (multiple choice, short answer, etc.).
- Clear, readable results visualisation.
### AI Usage Tracking & Dependency Dashboard
**Description:** Track how the user interacts with AI tools and analyse patterns of reliance.
**Functional Requirements**
- Log user AI interactions within the application.
- Possibly log the users thoughts and feelings about the AI usage for the period of time.
- Categorise usage (e.g., writing, coding, research, planning).
- Generate trends, e.g.:
    - “You used AI to summarise 7 articles this week.”
    - “Math problems solved by AI increased 15%.”
- Suggest AI-free challenges to reduce over-reliance.
  **Non-Functional Requirements**
- Dashboard loads quickly
- Visualisations must be intuitive and desktop-friendly.
### AI News Aggregation
**Description:** A dedicated dashboard section displaying current AI-related news.
**Functional Requirements**
- Fetch articles via third-party APIs (e.g., NewsAPI, NewsData.io). API better than scraping as we cannot predict how a website will change over time which will impact the scraping software.
- Display:
    - Headlines
    - Thumbnails
    - Source name and icon
- Clicking an article opens it in a new browser tab (?) Or copies URL for user to paste themselves (?)
- Refresh or auto-update at set intervals.
  **Non-Functional Requirements**
- Quick news retrieval
- Handle API downtime gracefully
### Estimate Environmental Impact
**Description:** Use research to estimate carbon, energy, and water usage linked to user AI interactions.
**Functional Requirements**
- Approximate energy/water use from activity logs.
- Use published research values for calculations.
- Display impact metrics via dashboard visualisations.
- Show weekly or monthly totals.
  **Non-Functional Requirements**
- Calculations should be efficient and approximated within reasonable accuracy ranges.
- Provide simple explanations for estimates for transparency.
### Account System
**Description:** Secure login, signup, and profile management.
**Functional Requirements**
- User registration with email and password.
- Email validation.
-  Login and logout.
- Password recovery/reset.
- Support for possible multi-factor authentication (optional).
- Persistent user data across sessions.
- Role-based access (optional).
  **Non-Functional Requirements**
- Passwords hashed and stored securely.
- Session timeout for security.
### Profile Management
**Functional Requirements**
- Edit personal data (name, preferences, quiz subjects).
- View account activity logs.
- View stored data for transparency.
- Delete account or data.
- Manage notification preferences.
### Dashboard
**Functional Requirements**
- Display:
    - AI usage analytics
    - Skill retention metrics
    - Latest AI news
    - Environmental impact
- Clear navigation sidebars or top menus.
- Error messages for failed API calls, login issues, etc.
- Suitable colour palette.
  **Non-Functional Requirements**
- Quick load times for all sections of dashboard and navigation.
- Responsive layout for desktop
- Readable fonts and accessible colour contrast.
## Data Storage
Store:
- User profiles
- Quiz/Questionaire results
- AI usage logs
- Environmental impact summaries
- Preferences and settings