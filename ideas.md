# Security
- password hashing (by/during phase 3)
- anti SQL injection

# Classes
- Main: main function

## User
- Student
- Admin
- Student and admin
- Instructor
- Admin and Instructor?
- Student and Instructor?
- Student, Instructor, and  Admin?

### Data
- username
- password
- email
- role(s)

#### Name
- first
- middle
- last
- preferred

### LoginUser
An established user logging in
- username
- salts and hashes password, before querying database
- checks if new/unestablished user?

#### Salt generation
use username and raw password to set seed, and generate "random" salt

### SetupUser
A user finishing the setup of the account
- Takes in name data
- Takes in and verifies email

### ActiveUser
An active user
- user ID

#### Role
Turn role into boolean indicating ability to perform a given action


### Admin Actions
- delete account (requires confirmation/"are you sure?")
- add/remove roles
- log out


#### Invite
- one time code
- Admin specify roles

#### Account reset
- one time password
- must make new password
- check against deadline
- has deadline
- redirect to login properly

#### List accounts
- username
- name
- role codes

### User Actions
- Finish account setup
- Redirect to home page post login

#### Login
- one time invitation code
- username and password
- account creation
- Redirect to login

# Deliverables
## Phase 1
- Cover Page
- Project Overview
- GitHub Url

### Architecture/Design
- UML
- Class Diagrams
- Context Diagram

### Code
- Account creation
- Testing

#### Input verification
- anti-SQL injection
- well formed input

### Screencasts
- Technical
- How to
