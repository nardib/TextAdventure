# Game Manual: Text Adventure Escape Room

Welcome to the Text Adventure Escape Room! This game is a thrilling text-based adventure with an easy and clean graphical user interface (GUI), featuring a resizable screen and illustrative images to enhance your experience. Prepare to solve puzzles, outwit an enemy, and escape the room before time runs out. This manual will guide you through the gameplay, commands, and mechanics of the game.

### Table of Contents:
- Starting the Game
- Game Interface
- Gameplay Mechanics
- Commands
- Winning and Losing
- Project

----------------------------------------------------------------------------------------------------------------------------------------

### New Game: Start a fresh adventure

Since the beginning, the game can be customized by allowing you to choose your character's and enemy name and the gender. To make the game challenging the user is free to select the difficulty level, choosing between **EASY**, **MEDIUM** or **HARD**. This will directly affect the beaviour of the enemy, as later explaned. In addition you can also choose to play without the enemy, by tiping false in response to the third selection.

Because of the possible lenght of a single match, Save and Resume actions are implemented through AWS S3 Bucket service. In orther to make a correct use of the saving and resume function you need to set your own Bucket.

----------------------------------------------------------------------------------------------------------------------------------------

### Game Interface

The game interface consists of several key elements.

At first the user can see the main image box displaying one image at time and providing a great graphic experience to the user. 
It will show at first the start screen image with the title of the game **ESCAPE ROOM**. After starting to play, the image box will be resplonsable for displaying the images of the wall you are looking at, and the win screen. Or the lose one.

On the bottom side of the window there is a terminal-like text field, displaying the executed commands and the relative outputs. 
Under it, a text field-area to write and execute the appropriete commands, required for interracting with the game. 
Also, the user can use F1 to zoom the HUD out, F2 to zoom in or F11 to trigger fullscreed mode.

Moving to the far right two elements are present. On the top there is a static map showing the formation of the map, the disposal of the rooms and the passages connecting one room to each other. Each passage is open, but not all walls have doors!
Under the map, anther text area is responsible for diplaying the collected notes, items containing riddles or clues and that do not occupay inventory space.

----------------------------------------------------------------------------------------------------------------------------------------

### Gameplay Mechanics

##### Map

The game map consists of 9 rooms, each with a variable number of doors (1 to 4), connecting to other rooms through door always enabled.
You start in the central room, which is also the final room where the game ends after solving all puzzles and challenges.
Each room contains several items to interract with. Some are pickable, others are just usable as you are facing the same wall they are.
Each pickable item has a specific weight, and because of the limited carrying capabilities of the player, the user need to carfully manage the inventory space.
In order to free some space, the user can only use the item in the inventory, so that it disappears.
The maximum weight capability in 10 Kg, and even though most of the objects have a avarage weight of only 1 Kg, some items, like bandages, will consume 2 Kg of capability.

##### Player Health and Progress

The player starts with 5 health points, displayed as full red hearts in the top-left corner.
The player's progress towards the final puzzle is shown as stars in the top center. The final puzzle involves collecting all the 10 stars from each of the nine rooms and to place them in the relative holes in the central room.
If the user finds himself in the same room with the enemy, every other command, not involving the change of the room, will allow the enemy to perform an attack, with the consequence of a 1 health point decrease. Once the player finds himself with 0 hearts (no longer red), the game is over with loss.

##### Enemy Behavior

The enemy is a non-player character (NPC) that randomly moves through the rooms, seeking to inflict damage on the player.
Difficulty settings affect enemy movement:

`Easy`: Enemy moves every 5 player actions.
`Medium`: Enemy moves every 3 player actions.
`Hard`: Enemy moves with each player action.

If the player and enemy are in the same room, the enemy inflicts 1 damage point per hit.
The game ends in a loss if the player's health drops to zero.

----------------------------------------------------------------------------------------------------------------------------------------

### Commands

`Movement:`

The prime commands are those use to manage movments. In orther to win the game, the user will be asked to move along the various rooms, examinating all the walls, and to do that the following commands must be used.

- north or n: Face the north wall.
- south or s: Face the south wall.
- east or e: Face the east wall.
- west or w: Face the west wall.

- cross DIRECTION: Move to the room in the specified direction (north, south, east, or west). If no direction is specified, move in the currently faced direction.

`Interaction:`

The user will need to use some items and to interract with others though the following commans.

- look: Inspect the wall for items.
- inventory: Display the list of items in your inventory.
- take ITEM: Pick up a specific item in the room (if it is pickable).
- use ITEM: Use an item from your inventory or the room. The item will disappear after use.

- status: Show player health, inventory, and puzzle completion status.
- help: Display a short description of commands.

It is also possible to reverse the moves made, and to save and load the status of the current match.

- undo or back: Revert to the last move.
- save: Save the current game state.
- quit or exit: Close the game window. You may be prompted to save your game.

----------------------------------------------------------------------------------------------------------------------------------------

### Winn & Loss

Solve all riddles, use all objects, and complete the puzzles to unlock the exit door.
Fill the central room with stars obtained from the nine rooms to unlock the exit.

If the player's health drops to zero due to enemy attacks, the game ends in a loss.
Good luck, and enjoy your adventure! May you solve all puzzles and escape before the enemy catches you!

----------------------------------------------------------------------------------------------------------------------------------------

### Project

| Name | Version | Description |
| --- | --- | --- |
| Java | 17 | Java version used to develop the project |
| JUnit | 4.13.2 | Framework for Java class tests |
| Jackson | 2.17.1 | Framework for converting objects into JSON stringifying them, and to convert back in the original shape through get and set methods using the ObjectMapper class |
| aws-sdk-for-java | 2.26.7 | Framework from the AWS SDK for accessing AWS Services Including S3 Bucket |
| Logback Classic Module | 1.5.6 | Used for monitoring the use of the AWS services and possibly intercept |