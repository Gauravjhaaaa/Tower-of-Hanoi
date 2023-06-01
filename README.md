**Tower of Hanoi**

This is a Java project named "Tower of Hanoi" implemented using the Swing library. The project demonstrates the classic problem of solving the Tower of Hanoi puzzle using stacks, a fundamental data structure.

**Project Description**

The Tower of Hanoi puzzle consists of three towers and a set of disks of different sizes. The objective is to move all the disks from the first tower to the third tower, following a few rules:

Only one disk can be moved at a time.
Each move consists of taking the top disk from one of the stacks and placing it on top of another stack.
No disk may be placed on top of a smaller disk.
The project provides a graphical user interface (GUI) where users can interact with the Tower of Hanoi puzzle. The GUI window displays the three towers and the disks represented by rectangles of different sizes. The user can select the number of disks to start with, and the program keeps track of the number of moves made.

**Project Files**

The project consists of two Java source files:

Run_Game.java: This file contains the main class Run_Game, which sets up the GUI window, menu options, and handles user actions such as selecting the level or exiting the game.
Tower.java: This file contains the Tower class, which extends JPanel and implements the necessary interfaces to handle mouse events and painting the GUI components. It represents the tower and disk arrangement, as well as the logic for solving the puzzle.
Usage
**To run the project, follow these steps:**


Compile both Java files using the Java compiler.
Run the Run_Game class, which serves as the entry point to the application.
The GUI window will appear, displaying the towers and disks.
Use the "Options" menu to select the level (number of disks) or exit the game.
Click and drag the disks to move them between towers.
The program keeps track of the number of moves made and displays it on the screen.
Once all the disks are successfully moved to the third tower, a congratulatory message will be displayed.
Dependencies
This project requires Java and the Swing library, which is a part of the Java Standard Edition (Java SE).

**About the Author**

This Tower of Hanoi project was created by Gaurav Jha as a part of their Stack data structure project. For any inquiries or feedback, please contact Gaurav Jha at gauravkumarjha306@gmail.com
