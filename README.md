# Escape Room (Text Adventure) 

## Project for "Elementi di Ingegneria del Software"

- [lorenzo.nardin@studenti.unipd.it](mailto:lorenzo.nardin@studenti.unipd.it)
- [elia.sandrin@studenti.unipd.it](mailto:elia.sandrin@studenti.unipd.it)
- [marco.spolverato.1@studenti.unipd.it](mailto:marco.spolverato.1@studenti.unipd.it)
- [alessio.zanco@studenti.unipd.it](mailto:alessio.zanco@studenti.unipd.it)

## Game Manual

The game is a text adventure with a easy and clean graphic user interface (GUI). The player is in an escape room with an enemy, and he has to get out before being killed. You can insert the commands in a text field, and you can see the results of the action made in a text area, but also in the image shown in the GUI, which illustrate the wall you are looking at and the items you can interact with (e.g. keys, locks, bandages, etc.). The enemy makes a move every # moves the player makes. The moves include:
- changing the wall you are facing
- changing room (based on the doors placed in the room)
- picking up items (only the pickable ones)
- using items

### **Map & Rooms**
The game is played in a 9 rooms map of fixed design and each room has a variable number of objects (up to 8 items) and doors (from 0 to 4),always enabled, connecting to other rooms.
The main room from witch the game begins is also the final room where the game ends after having solved all the riddles and quiz consisting of varius challenges, from finding the right item to using logic and memory.

Each item has its own weight (expressed in Kg) and the player can carry a maximum ammount of weight (10 Kg).
Some items, like the hammer, can occupy almost all the carrable weight, forcing the player to carfully manage the storage.
Also, once used, the item dissapears, and by the time there's no way of throwing the items, in orther to free some space the player has to use them in the storage.

### **Player**
At the beginning of the game, the user can choose the name and the gender of the player. 
In orther to interract with the game, the player as to use the command line implemented in the GUI executing specific commands:

- north (command used to change the facing direction to north wall of the room)
- south (command used to change the facing direction to south wall of the room)
- east (command used to change the facing direction to east wall of the room)
- west (command used to change the facing direction to west wall of the room)

- croos DIRECTION (command used to change the room so that you cross the door to go to the relative north room. Only aveilable if door is present in the direction wall. Instead of DIRECTION the player will write the direction north, south, east or west)

- use ITEM (command used to interract with a specific item in your inventory. After using the item, it will dissapear freeing an ammount of carriable weight equivalent to the weight of the item just used. Only executable on item existing in the player storage)

- get ITEM (command used to interract with a specific item in the room. After keeping the item, it will appear in your inventory freeing an ammount of carriable weight equivalent to the weight of the item just used. Only executable on item existing in the player storage)
## Project


## Design Patterns

### GoF

#### Memento

We used memento design pattern to implement the undo functionality in Game class. We used an inner class inside Game called GameMemento, which takes a snapshot of the state of the game manking a copy of every object relevant for the state of the game: in this way is easy to implement a list of all the snapshots, and every time the game insert "undo" (or "back") as input, it change the state of the game to previous one, removing it form the list. The list is make in order to be able to go back several time.

### GRASP

#### Information Expert

Game class has the responsability to mangate the informations about Player, Enemy, Map ...

#### High Cohesion

Each class has a single, well defined responsability ...

#### Low Copuling

Game class interacts with Player, Enemy and Map classes, but the relations are only related to methods ...

#### Creator

Game class creates the istances of Player, Enemy, Map classes ...

## Specifications


## Design Documents


## System Test Reports