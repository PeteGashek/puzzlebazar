# Introduction #

Reviewing tools

# Details #

## Features ##

  * Build a reputation system
  * Allow puzzles to have different visibility: private, limited to a group, public
    * This is useful for reviewing
    * Reviewers can comment
    * Comments visibility can be controlled
    * This makes it possible to release a puzzle only when its quality is good
  * Use an semi-automatic tagging system for puzzle quality
    * Incomplete: The system has automatically detected that the puzzle is incomplete
    * Unsolved: Nobody was able to solve the puzzle yet
    * Solved: At least one person was able to solve it
    * Non-unique: At least two different solutionms were found
    * Rejected: Enough people voted to reject it
  * Puzzles keep an history of their important changes, which include
    * Edits
    * Tags changed
    * Visibility changed: public, private,
  * Grade puzzle quality
    * Can be based on number of "like", number of solver, author's reputation, tags
    * Should increase if the puzzle spent time being reviewed
  * Grade puzzle complexity
    * Based on average time-to-solve, user's difficulty rating

## Issues ##

  * [All issues for this milestone](http://code.google.com/p/puzzlebazar/issues/list?can=1&q=label:Milestone-V1-4&cells=tiles)
  * [All open issues for this milestone](http://code.google.com/p/puzzlebazar/issues/list?can=2&q=label:Milestone-V1-4&cells=tiles)