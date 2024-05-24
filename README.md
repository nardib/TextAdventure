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