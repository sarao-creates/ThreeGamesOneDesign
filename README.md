# Three Games, One Design ~ A COMP 401 Assignment Transformed.

In this assignment, I made a few single widget games building on top of a SpotBoard component.

Interfaces for a Spot and a 2D field of Spots called SpotBoard:
* Spot
* SpotBoard

Those interfaces are implemented as Java Swing components that extend JPanel by:
* JSpot
* JSpotBoard

The SpotListener interface can be used to observer enter/exit/click events on a Spot.

ExampleWidet is a user inteface widget that implements a simple game where two players
take turns trying to find a secret spot. ExampleGame simply sets up a top-level window 
with a single ExampleWidget component in its layout. In this game, a blue player
and a green player take turns either setting or clearing spots on the board. The goal is to
find the secret spot. When one of them does, the game is over and it calculates a "score". The
score is simply the number of spots on the board plus one for every spot that is set to your
opponent's color and minus one for every spot on the board that is set to your color. 

# Tic Tac Toe (Game One)

Created a TicTacToe game following the pattern of the example game. In particular, I made sure to call the main game class `TicTacToeGame`
and the widget class `TicTacToeWidget`. The game has the following features:

* Players are black and white.
* Background of board is uniform.
* Spots are highlighted when entered only if clicking on them is a legal move (i.e., spot not already selected).
* Start of game has a welcome message and indicates that white goes first.
* After a game winning move, message indicates who won and spot highlighting stops.
* After a game drawing move, message indicates that game is a draw.
* After a move that neither wins or draws, message indicates who goes next.

See here for a demonstration: https://youtu.be/--5YJ4g1Pik

# Connect Four (Game Two)

Created a ConnectFour game. Features include:

* Players are red and black
* Board is 7 columns and 6 rows.
* Background is set up as alternating column stripes.
* All empty spots of a column are highlighted when the cursor enters any spot in the column.
* Clicking on any spot in a column that contains an empty spot sets the bottommost empty spot and switch turns.
* Clicking on a spot in a column that does not contain an empty spot does nothing.
* Welcome message that indicates red to play.
* After a game winning move, message indicates who won and highlights winning spots. Column highlighting stops.
* If a game draws, message indicates that game is a draw.
* After a move that neither wins or draws, message indicates who goes next.

See here for a demonstration: https://youtu.be/0lKWkFVp6-Q

# Othello (Game Three)

Created an Othello game. The rules for Othello can be found here: https://www.ultraboardgames.com/othello/game-rules.php

Features include:
* Players are black and white.
* Board is 8x8 with a checkerboard background pattern.
* The game starts with the middle 2x2 spots set up with alternating white and black pieces set.
* Welcome message indicates black to play.
* Spot highlighting only works on spots that are valid moves for player whose turn is next.
* Clicking on a spot that is a valid move sets that spot to the player's color, flips any flanked spots in any direction as appropriate, and sets the message to indicate whose turn is next.
* If a player has no valid move, their turn is skipped.
* If there are no more valid moves available to either player, the game is over and the message is set to indicate who won and by what score.

See here for a demonstration: https://youtu.be/GT-OG7ovHnA

