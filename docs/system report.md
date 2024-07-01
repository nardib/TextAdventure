## System Test Reports

All the system tests were executed on Windows 10, Windows 11 and Fedora 40

### Start

| Summary | The game starts and let the player configure a new game or restore a previous one. |
| --- | --- |
| Test Case Design | - Test of game start without errors <br> - Test of correct restore of the previous game saved |
| Pre-Condition | The game was successfully installed |
| Post-Condition | Game configuration page is shown |
| Test Scripts | **Test of game start without errors:** <br> + Use "New Game" command on the main screen <br> + Verify that the game configuration screen is shown without errors <br> **Test of correct intial configuration:** <br> + Use "Resume" command on the main screen <br> + Verify that it shows a list of previous savings (if the `config.property` file and the credentials where set up correctly) <br> + Verify that the selected game starts in the same state it was in the last save |
| Test Case Execution Report | - Test of game start without errors: ✅ Passed <br> - Test of correct restore of the previous game saved: ✅ Passed |

### Game Configuration

| Summary | After using "New Game" command in the main menu the user configure the Player and Enemy with a proper name and gender and set the other setting. |
| --- | --- |
| Test Case Design | - Test a configuration and check if the name and pronoun are set correctly and displayed during the game; check also if the other settings are set corrected. |
| Pre-Condition |  User has started the game with "new game" command. |
| Post-Condition | The game begins in its essence and the player finds itself in the first room, which is also the room where the game will end. |
| Test Scripts | **Test a configuration and check if the name and pronoun are set correctly and displayed during the game:** <br> + Use "new game" command in the main menu <br> + Compile the required fields to configure the game <br> + Check during the game if the name and gender are displayed correctly |
| Test Case Execution Report | - Test a configuration and check if the name and pronoun are set correctly and displayed during the game: ✅ Passed |

### Commands

| Summary | Given a specific command the game returns an output that matches with the inserted input |
| --- | --- |
| Test Case Design | - Test of all the commands for moving the player <br> - Test for all the commands to interact with the items on the map <br> - Test for invalid commands |
| Pre-Condition | Game must be correctly started and with a valid configuration. |
| Post-Condition | GUI updates after every move and the items are modified following the given command. |
| Test Scripts | **Test of all the commands for moving the player:** <br> + Start a new game and select a valid configuration <br> + Move the player on the map changing the facing direction and the room making them cross the doors <br> + Verify that the player can cross only the walls with a door and the GUI is updated showing the current room and facing direction <br> **Test for all the commands to interact with the items in the map:** <br> + Start a new game and select a valid configuration <br> + Interact with all the items in a proper way according to the type of object <br> + Verify that all the items work as intended and there are no errors, and show the correct usage of the item when the commands are not used properly <br> **Test for invalid commands:** <br> + Start a new game and select a valid configuration <br> + Test a bunch of invalid commands <br> + Verify that after each invalid command an error message is displayed to show the correct usage of the command or it suggests to type `help` to get a list of valid commands |
| Test Case Execution Report | - Test of all the commands for moving the player: ✅ Passed <br> - Test for all the commands to interact with the items in the map: ✅ Passed <br> - Test for invalid commands: ✅ Passed |

### Win

| Summary | After having solved the final puzzle the game shows the win screen |
| --- | --- |
| Test Case Design | - Test win <br> - Test lose |
| Pre-Condition | The player must have used all the items and must have visited all the rooms. |
| Post-Condition | The enemy dissapears and a win screen is shown. |
| Test Scripts | **Test win:** <br> + Start the game with a valid configuration <br> + Play through the game and collect all the items for the final puzzle <br> + Complete the final puzzle <br> + Verify that after having completed the final puzzle a win screen is shown and you can close the game <br> **Test lose:** <br> + Start the game with a valid configuration <br> + Move to the same room as the enemy <br> + Let the enemy attack you until your health points drop to zero <br> + Verify that the game end and the lose screen is shown and you can close the game |
| Test Case Execution Report | - Test win: ✅ Passed <br> - Test lose : ✅ Passed |


### Back

| Summary | When inserted `back` (or `undo`) as command the game returns to the previous state |
| --- | --- |
| Test Case Design | - Test back function with no previous moves to undo <br> - Test back function with a previous move to undo <br> - Test back function several times with several previous moves to undo |
| Pre-Condition | The user must have done at least one move. |
| Post-Condition | The game is restored one move back. |
| Test Scripts | **Test back function with no previous moves to undo:** <br> + Start the game with a valid configuration <br> + Use `back` command <br> + Verify that a message that says that there are no previous moves to undo is shown and the state of the game doesn't change <br> **Test back function with a previous move to undo:** <br> + Start the game with a correct configuration <br> + Make at least one move to restore <br> + Use `back` command <br> + Verify that the previous move is restored and displayed correctly <br> **Test back function several times with several previous moves to undo:** <br> + Start the game with a valid configuration <br> + Make a ceratain number of moves <br> + Use `back` command several times <br> + Verify that after each `back` the game is restored to its previous state until the first move (inital configuration) |
| Test Case Execution Report | - Test back function with no previous moves to undo: ✅ Passed <br> - Test back function with a previous move to undo: ✅ Passed <br> - Test back function several times with several previous moves to undo: ✅ Passed |


### Save and Exit/Quit

| Summary | When `save` command is inserted the game saves its actual state and when pressing `exit` it closes the game and asks to save the game first if the last state was not saved |
| --- | --- |
| Test Case Design | - Test save <br> - Test exit |
| Pre-Condition | The game must have been started correctly |
| Post-Condition | The game is saved and the user can leave and resume the game later. |
| Test Scripts | **Test save:** <br> + Start a new game with a valid configuration <br> + Do some moves in the game <br> + Use `save` command and exit the game <br> + Try to log back in the game using `resume` command <br> + Verify that you are in the same state of when you saved the game <br> **Test exit:** <br> + Start a new game with a valid configuration <br> + Do some moves in the game <br> + Try to `exit` the game with the proper command <br> + Verify that if the last state of the game was not saved the game asks to save it <br> + Verify that if the game closes properly (either saving or not saving the game based on the choice made in the previous step) |
| Test Case Execution Report | - Test save: ✅ Passed <br> - Test exit: ✅ Passed |