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

### **Player and commands**
At the beginning of the game, the user can choose the name and the gender of the player.
In orther to interract with the game, the player as to use the command line implemented in the GUI executing specific commands:

- north (command used to change the facing direction to north wall of the room)
- south (command used to change the facing direction to south wall of the room)
- east (command used to change the facing direction to east wall of the room)
- west (command used to change the facing direction to west wall of the room)

- croos DIRECTION (command used to change the room so that you cross the door to go to the relative north room. Only aveilable if door is present in the direction wall. Instead of DIRECTION the player will write the direction north, south, east or west)

- use ITEM (command used to interract with a specific item in your inventory. After using the item, it will dissapear freeing an ammount of carriable weight equivalent to the weight of the item just used. Only executable on item existing in the player storage)

- get ITEM (command used to interract with a specific item in the room. After keeping the item, it will appear in your inventory freeing an ammount of carriable weight equivalent to the weight of the item just used. Only executable on item existing in the player storage)

- inventory (command used to display a list of the item in the inventory)

- object_name (used to get info about an object in the inventory)

- help (command used to get a short description of commands)

- save (used to save the status of the game)

- quit (used to quit the game)

### Enemy

The enemy is a NPG randomly moving in the rooms of the maps, seeking for the player, with the scope to kill him by inflicting damage.

### Purpose of the game

As in the real-life escape-room the purpose of this game is to solve all the riddles, use all the objects and complete the puzzles to unlock the exit door and gain freedom. The player must also survive to the enemy looking who will randomly move each time the player makes a move.
When both the player and the enemy are in the same room, the enemy will start inflicting some damege to the player, who's provided with 5 hearts of healt points. Heach hit provides 1 damage point.
If the player's healt drops to zero point, the game ends with loss.
The player can also find and use some special healing items like bandages and painkillers to restore some healt points.

## Project

| Name | Version | Description |
| --- | --- | --- |
| Java | 20.0.1 | IDE and programming language used in the development |
| JavaSwing | 1.0.3 | Platform used for develpment of client applications, based on Java, later on substitued with JavaFX |
| JUnit | 5.9.1 | Framework for Java class tests. |

### Compile and Execute

Before executing the game or compiling the code form source, make sure you have **Java Developer Kit 17** (or above) correctly installed on your machine, otherwise it won't be able to compile or execute the game. 

The dependency are managed with **Maven**, but there is no need to have it installed on you machine: in fact you can use the Maven Wrapper configured in the repository just replacing the keyword `mvn` with `./mvnw` where specified in the following instructions.

First of all you need to clone the project using a terminal command, you can use the following command:

```bash
git clone https://github.com/nardib/TextAdventure
```

This command will clone the repository of the project from GitHub to your local machine. 

You can also download the zip file from https://github.com/nardib/TextAdventure/archive/refs/heads/main.zip : make sure to extract the content of the zip file in order to use it.

You can now enter the directory with:

```bash
cd TextAdventure
```

Now you can just play the game with ine of the `.jar` files in the `bin` directory: the one named `text-adventure-x.x.x.jar` requires you to have all the needed dependency installed; instead the one named `original-text-adventure-x.x.x.jar` doesn't require them and will just work fine. 

**NOTE**: the execution of all the Maven commands requiers a pom.xml file, which is in the root directory, so make sure to execute the commands that starts with `mvn` there.

For compiling the program you can use Maven:

```bash
mvn compile
```

Maven will make sure to install all the dependency nedded to build correctly the project.

For creating the `.jar` executable file you can use:

```bash
mvn package
```

This command will both compile and test the code, other than creating the executable file.

You can find all the file generated by the previous commands in the `target` folder.

After the creation of the excecutable file, you can run the program with the command:

```bash
mvn exec:java
```

or with:

```bash
java -jar target/text-adventure-1.0.0.jar
```

#### Testing

For testing you can use the command:

```bash
mvn test
```

If you want a report of the test you can generate them after the command `mvn test` with the command:

```bash
mvn surefire-report:report
```

You can then find a report in `target/site/surefire-report.html`.

#### Additional Notes

If you want to delete all the file genderted by Maven in the `target` folder you can use:

```bash
mvn clean
```

With Maven you can also insert more aguments after the `mvn` keyword, e.g.

```bash
mvn clean compile package
mvn test surefire-report:report
```

## Design Patterns

### GoF

#### Memento

We used memento design pattern to implement the undo functionality in Game class. We used an inner class inside Game called GameMemento, which takes a snapshot of the state of the game manking a copy of every object relevant for the state of the game: in this way is easy to implement a list of all the snapshots, and every time the game insert "undo" (or "back") as input, it change the state of the game to previous one, removing it form the list. The list is make in order to be able to go back several time.

### GRASP

#### Information Expert

Game class has the responsability to mangate the informations about Player, Enemy, Map ...

#### High Cohesion

Each class has a single, well defined responsability ... They have been developed following a black-box mindset that allow us to maintain all the classes indipendent and distinguised

#### Low Copuling

Game class interacts with Player, Enemy and Map classes, but the relations are only related to methods ...

#### Creator

Game class creates the istances of Player, Enemy, Map classes ...

## Specifications

| Use case | Start |
| --- | --- |
| Brief description | User can start the game |
| Actor | User |
| Basic flow | User presses Start button. |
| Alternative flow | An error message is displyed. |
| Precondition | The game was succesfully installed. |
| Postcondition | Player settings page is shown. |
| Extensions points | - |

| Use case | Player configuration |
| --- | --- |
| Brief description | User can choose the name and sex of the player. |
| Actor | User |
| Basic flow | User writes the name and select the gender between the aveilable optios. |
| Alternative flow | No errors because pre-selected values are provided by default. |
| Precondition | User has started the game. |
| Postcondition | The game begins in its essence and the player finds itself in the first room, where the game will also end. |
| Extensions points | - |

| Use case | Coomands |
| --- | --- |
| Brief description | User can play the game interacting throw the command shell. |
| Actor | User |
| Basic flow | User interacts with the game, moving between the rooms, collecting objects and knowledge in the scope of solving the quizs and the riddles. |
| Alternative flow | • The player is either killed by the enemy = LOSS or the player as solved the game = WIN • |
| Precondition | Game must be correctly started |
| Postcondition | GUI updates at pretty every move |
| Extensions points | The enemy also makes a move every time the player does one |

| Use case | Win |
| --- | --- |
| Brief description | User wins the game. |
| Actor | User |
| Basic flow | User unlocks the door at in the room where all started. |
| Alternative flow | The player is killed. |
| Precondition | The player must have used all the items and must have visited all the rooms. |
| Postcondition | The enemy dissapears and a win window is shown |
| Extensions points | - |

## Design Documents


## System Test Reports
