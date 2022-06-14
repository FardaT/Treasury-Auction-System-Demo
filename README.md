# name-MS-Treasury-Auction-System

## Dependencies

- Java Development Kit - JDK 11

## Development rules:

- Do not use Lombok
- Place contents into classes accoring to this order:
  - Final fields
  - Fields
  - Default constructor
  - Constructors with parameters
  - Getters & Setters
  - Public methods
  - Private methods
- For testing use this syntax wherever possible: 
  - [MethodName_StateUnderTest_ExpectedBehavior]
  - like: `checkCredentialValidity_WithValidCredentials_ReturnsTrue()`
  - in case of endpoints, instead of method name, use descriptive name for the endopint
- Use `this` keyword only to avoid variable name conflicts
- After global exception handler is present, create and throw custom exceptions in error scenarios
- Create branch names including very short description of the task
- Create (final) commits including story ID and name
- Use plurals for database table names
- Use 2 spaces for indentation
- Have at least 80% test coverage regarding services (unit test) and controllers (integration tests)
- Use google-java-format as a style-guide

## Processes:
- Push only when all tests and style checks have passed
- On mentor time present new technologies if you have a relevant PR accepted
- Provided you met the need of a tech Task for something, feel free to create it and assign it to Riel

## Useful links


Jira board:

- https://greenfoxacademy.atlassian.net/jira/software/projects/NMTAS/boards/89

API specification:

- Swagger link here

Contribution:

- https://github.com/green-fox-academy/Microtis-Iberis/blob/master/CONTRIBUTING.md

Circle CI:

- Coming soon

Commit messages:

- https://chris.beams.io/posts/git-commit/

`If applied, this commit will ... [commit message]`

Git cheat sheet

https://docs.google.com/spreadsheets/d/1Y6ylJLSbkUqLzn9kN_rQSgDlssRpR-ZFresF46apFWY/edit?usp=sharing

## Git Workflow

### Day Start

Use `git fetch` in order to retrieve the most recent commits from GitHub.

### Start New Feature/Bugfix

In order to minimize merge conflicts later always open a new feature branch from the most recent state of the `development` branch on GitHub.

- `git fetch`
- `git checkout -b <branch_name> origin/development`

### Update Feature Branch

While you're working on your own feature/bugfix other developers make changes on `development` and it's required to update your branch to keep consistency of the codebase. You can do this in 2 ways.

[`git merge` vs `git rebase`](https://www.atlassian.com/git/tutorials/merging-vs-rebasing)

#### Rebase

[`git rebase`](https://www.atlassian.com/git/tutorials/rewriting-history/git-rebase)

Rebase rewrites commit history; therefore, do not use rebase on the `master` and `development` branches.
On the other hand feel free to use rebase on your own branches.

Use `git rebase development` while on your branch.

#### Merge

[`git merge`](https://www.atlassian.com/git/tutorials/using-branches/git-merge)

This creates a new commit (so called merge commit) containing changes from both your branch and the development branch.

Use `git merge development` while on your branch.

### Commit and Push

You can work on your feature/bugfix separately but sometimes you may need to merge another branch into your branch (i.e. to try out your feature). In order to have clean workflow (and pull requests) always commit only feature related modifications. This is harder to reset files or hunks later.
