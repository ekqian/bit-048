=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 1200 Game Project README
PennKey: 40780694
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. 2D Arrays
  2D arrays were used to represent the board and the position/number of each tile.
  This representation made it easier to implement functions such as combining numbers,
  shifting tiles, moving the board, and checking whether the game was over.

  2. Collections
  The List Collection was used to store all game states, which was used for the
  undo button. Each time the board changed, the board would be stored in a LinkedList
  which made it easy to iterate through and load the previous game state.

  3. File I/O
  File I/O was used in the save and load functions. The player could save their current
  game by writing to board.csv and reload it by reading the file.

  4. JUnit Testing
  JUnit Testing was used to test the internal game model. Testing the functionalities
  of the game only required GameModel class and not the GUI application.

===============================
=: File Structure Screenshot :=
===============================
- Include a screenshot of your project's file structure. This should include
  all of the files in your project, and the folders they are in. You can
  upload this screenshot in your homework submission to Gradescope, named
  "file_structure.png".

=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.

  The GameModel class implemented the internal game model. The board was represented
  with a 2D array and all functionalities of the game were written in this class.
  This GameBoard class instantiates a game model for 2048 and serves as both the
  controller and the view since the player can make a move by pressing an
  arrow key or pressing one of the buttons. The board repaints itself everytime
  the board changes. Finally, Run2048 sets up the top-level frame and widgets for
  the GUI and connects the buttons with their functionalities. Game then initializes
  the view and instantiates a GameBoard.


- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?

  One of the issues I encountered was implementing the undo function, since pressing
  the button did not change the game state. I found through testing that the problem
  was in the getBoard function as board was not properly encapsulated which is why
  the game state was not changing.


- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?

  I think the model does a good job of implementing the model-view-controller design
  since the model is independent of GameBoard class, which controls most of the view
  and controller. The private state is also well encapsulated since functions that
  require the game state copy the state. If given more time, I would try to make my
  code cleaner since a lot of the iterations were redundant.


========================
=: External Resources :=
========================

- Cite any external resources (images, tutorials, etc.) that you may have used 
  while implementing your game.
