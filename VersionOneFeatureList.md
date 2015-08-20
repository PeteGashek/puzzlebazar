# Introduction #

Version 1.0 is meant to be released quickly but yet to offer a rich experience to users. As such, it compromises on some features but makes sure the basic elements that distinguish jsPuzzle are all incorporated

# Details #

## Feature List ##
  * Long list of supported puzzle genres, all based on a square grid, including :
    * Number puzzles (sudoku, kakuro)
    * Cell state determination puzzles (heyawake, nurikabe, nonograms, magnetic field, hitori, akari, kuromasu)
    * Edge state determination puzzles (slitherlink, masyu)
    * Area determination puzzles (shikaku)
    * Word puzzles (crossword)
    * Others (hashiwokakero)
  * Puzzle solving
  * Puzzle creation, editing
  * Puzzle reviewing
  * Simple wiki pages for genre description, tutorials
  * Simple news system
  * Limited support for RSS feed (global news system only)
  * Simple puzzle verification tools
  * Simple search-based navigation
    * Navigation starts from search box, featured links or user links
    * A search can return a mix of wiki pages and puzzles
  * Basic user profile
    * A list of favorite links
    * Solving statistics
  * Simple browser-based administration
  * Integration with Facebook
  * Basic competitive statistics (Per-user, Per-Friends, Per-genre, Per-puzzle)
  * English and French languages (and Japanese?)

## System Information ##
  * Browser-based efficient and intuitive UI for playing puzzles
  * Google App Engine Java back-end
  * Google Web Toolkit (GWT) for client and server side
  * Model-View-Presenter (MVP) architecture, inspired by gwt-presenter
  * Uses command pattern for client-server exchanges, inspired by gwt-dispatch
  * Uses GWT UIBinder to describe page layout
  * Uses static string internationalization and UIBinder internationalization support
  * Uses dependency injection through GIN/Guice
  * Uses GWT code splitting to lazily load puzzle genres as needed