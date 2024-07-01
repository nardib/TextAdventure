# Game Manual: Text Adventure Escape Room

Welcome to the Text Adventure Escape Room! This game is a thrilling text-based adventure with an easy and clean graphical user interface (GUI), featuring a resizable screen and illustrative images to enhance your experience. Prepare to solve puzzles, outwit an enemy, and escape the room before they find you. This manual will guide you through the gameplay, commands, and mechanics of the game.

### Table of Contents:

- Starting the Game
- Game Interface
- Gameplay Mechanics
- Commands
- Winning and Losing
- Project
- Execution Environments and Constraints
- Libraries Used
- Compile & Execute
- Testing
- Save & Load Usage

----------------------------------------------------------------------------------------------------------------------------------------

### New Game: Start a fresh adventure

Since the beginning, the game can be customized by allowing you to choose your character's and enemy's name and gender. To make the game challenging, the user is free to select the difficulty level, choosing between **EASY**, **MEDIUM**, or **HARD**. This will directly affect the behavior of the enemy, as explained later. Additionally, you can choose to play without the enemy by typing "false" in response to the third selection.

Due to the potential length of a single match, Save and Resume actions are implemented through the AWS S3 Bucket service. To correctly utilize the saving and resume function, you need to set up your own Bucket.

----------------------------------------------------------------------------------------------------------------------------------------

### Game Interface

The game interface consists of several key elements.

Firstly, the user can see the main image box displaying one image at a time, providing a great graphic experience. It will initially show the start screen image with the title of the game **ESCAPE ROOM**. After starting to play, the image box will display the images of the wall you are looking at, as well as the win screen or the lose screen.

On the bottom side of the window, there is a terminal-like text field that displays the executed commands and their respective outputs. Below it, there is a input field area where you can write and execute the appropriate commands required for interacting with the game. Additionally, the user can use F1 to zoom the HUD out, F2 to zoom in, or F11 to trigger fullscreen mode.

Moving to the far right, there are two elements. At the top, there is a static map showing the layout of the rooms and the passages connecting them. Each passage is open, but not all walls have doors! Below the map, there is another text area that displays the collected items and notes. The notes do not occupy inventory space.

----------------------------------------------------------------------------------------------------------------------------------------

### Gameplay Mechanics

##### Map

The game map consists of 9 rooms, each with a variable number of doors (1 to 4), connecting to other rooms through doors that are always enabled.
You start in the central room, which is also the final room where the game ends after solving all puzzles and challenges.
Each room contains several items to interact with. Some are pickable, while others are only usable when you are facing the same wall they are on.
Each pickable item has a specific weight, and due to the limited carrying capacity of the player, you need to carefully manage your inventory space.
To free up space, you can only use items in your inventory, causing them to disappear.
The maximum weight capacity is 10 Kg, and although most objects have an average weight of only 1 Kg, some items, like bandages, will consume 2 Kg of capacity.

##### Player Health and Progress

The player starts with 5 health points, displayed as full red hearts in the top-left corner.
The player's progress towards the final puzzle is shown as stars in the top center. The final puzzle involves collecting all 10 stars from each of the nine rooms and placing them in the corresponding holes in the central room.
If the player finds themselves in the same room as the enemy, any command that doesn't involve changing rooms will allow the enemy to perform an attack, resulting in a decrease of 1 health point. Once the player's hearts reach 0 (no longer red), the game is over with a loss.

##### Enemy Behavior

The enemy is a non-player character (NPC) that randomly moves through the rooms, seeking to inflict damage on the player.
Difficulty settings affect enemy movement:

- `Easy`: The enemy moves every 5 player actions.
- `Medium`: The enemy moves every 3 player actions.
- `Hard`: The enemy moves with each player action.

If the player and enemy are in the same room, the enemy inflicts 1 damage point per hit.
The game ends in a loss if the player's health drops to zero.

----------------------------------------------------------------------------------------------------------------------------------------

### Commands

**Movement:**
The primary commands are used to manage movements. To win the game, the user must move along the various rooms and examine all the walls; to do that, it is necessary to use the following commands:

- `north` or `n`: Face the north wall.
- `south` or `s`: Face the south wall.
- `east` or `e`: Face the east wall.
- `west` or `w`: Face the west wall.

- `cross DIRECTION`: Move to the room in the specified direction (north, south, east, or west). If no direction is specified, move in the currently faced direction.

**Interaction:**

The user needs to use items and interact with others using the following commands:

- `look`: Inspect the wall for items.
- `inventory`: Display the list of items in your inventory.
- `take ITEM`: Pick up a specific item in the room (if it is pickable).
- `use ITEM`: Use an item from your inventory or the room. The item will disappear after use.

- `status`: Show player health, inventory, and puzzle completion status.
- `help`: Display a short description of commands.

It is also possible to reverse moves made and save and load the status of the current match:

- `undo` or `back`: Revert to the last move.
- `save`: Save the current game state.
- `quit` or `exit`: Close the game window. You may be prompted to save your game.

----------------------------------------------------------------------------------------------------------------------------------------

### Win & Loss

Solve all riddles, use all items, and complete the puzzles to unlock the exit door.
Fill the central room with stars obtained from the nine rooms to unlock the exit.

If the player's health drops to zero due to enemy attacks, the game ends in a loss.
Good luck, and enjoy your adventure! May you solve all puzzles and escape before the enemy catches you!

----------------------------------------------------------------------------------------------------------------------------------------

## Project

| Name | Version | Description |
| --- | --- | --- |
| Java | 17 | Java version used to develop the project |
| JUnit | 4.13.2 | Framework for Java class tests |
| Jackson | 2.17.1 | Framework for converting objects into JSON stringifying them, and to convert back in the original shape through get and set methods using the ObjectMapper class |
| aws-sdk-for-java | 2.26.7 | Framework from the AWS SDK for accessing AWS Services Including S3 Bucket |
| Logback Classic Module | 1.5.6 | Used for monitoring the use of the AWS services and possibly intercept |

----------------------------------------------------------------------------------------------------------------------------------------

### Execution Environments and Constraints

The entire project was developed using Java, so it can be executed on any machine with a correctly installed JVM (either Windows, Linux or MacOS).

We have tested the project with JDK 17 (and above), so we suggest using this version, otherwise we don't guarantee that the game will work properly. To check the version of Java installed on your machine use this command in your OS's terminal:

```bash
java --version
```

----------------------------------------------------------------------------------------------------------------------------------------

### Libraries Used

During the development of this game, we used the following libraries:

- **JUnit** as a testing framework, to ensure that all the main functionalities of the game work properly with specific tests for the main classes.

- **Jackson** to serialize the game state in a string format and to deserialize a string back into a game object. This was used to implement a save and load functionality, allowing the user to continue the game later by saving his current progress.

- **Amazon SDK for Java** to upload and download game states in an AWS S3 Bucket, enabling the user to save multiple states of more games online with his personal AWS credentials.

----------------------------------------------------------------------------------------------------------------------------------------

### Compile & Execute

Before executing the game or compiling the code form source, make sure you have **Java Developer Kit 17** (or above) correctly installed on your machine, otherwise it won't be able to compile or execute the game.

The dependencies are managed with **Maven**, but there is no need to have it installed on you machine: you can use the Maven Wrapper configured in the repository just replacing the keyword `mvn` with `./mvnw` where specified in the following instructions.

First, you need to clone the project using a terminal command:

```bash
git clone https://github.com/nardib/TextAdventure
```

This command will clone the repository of the project from GitHub to your local machine.

You can also download the zip file from [GitHub](https://github.com/nardib/TextAdventure/archive/refs/heads/main.zip): make sure to extract the content of the zip file in order to use it.

You can now enter the directory with:

```bash
cd TextAdventure
```

or

```bash
cd TextAdventure-main
```

if you have downloaded and unzipped the zip file.

Now you can play the game with the `text-adventure-x.x.x.jar` file in the `bin` directory clicking on the icon in your operating system or typing this command from the root directory:

```bash
java -jar bin/text.adventure-x.x.x.jar
```

Remember to change `x-x-x` with the current version of the game.

**NOTE**: the execution of all the Maven commands requires a `pom.xml` file, which is in the root directory, so make sure to execute the commands that starts with `mvn` there.

For compiling the program you can use Maven:

```bash
mvn compile
```

Now you can run the program with the command:

```bash
mvn exec:java
```

Maven will make sure to install all the dependecies needed to build the project correctly.

For creating the `.jar` executable file you can use:

```bash
mvn package
```

This command will both compile and test the code, ad well as create the executable file.

You can find all the file generated by the previous commands in the `target` folder.

To execute the `.jar` file just created in the target directory use:

```bash
java -jar target/text-adventure-x.x.x.jar
```
----------------------------------------------------------------------------------------------------------------------------------------

### Testing

For testing you can use the command:

```bash
mvn test
```

If you want a report of the test you can generate it with the command:

```bash
mvn site
```

This command also generates the javadoc documentation in `target/site/apidocs`

You can then find the report in `target/site/surefire-report.html`.

----------------------------------------------------------------------------------------------------------------------------------------

### Save & Load Usage

The game is configured to save and load game progress in an Amazon S3 bucket. To use this functionality make sure to use your personal AWS account to save your progresses.
First of all you need to set your AWS credentials to access the bucket in the `%UserProfile$\.aws\credentials` file on Windows or `~/.aws/credentials` on Linux and MacOS. The file must have this structure:

```
[default]
aws_access_key_id = YOUR_ACCESS_KEY_ID
aws_secret_access_key = YOUR_SECRET_ACCESS_KEY
```

where `YOUR_ACCESS_KEY_ID` and `YOUR_SECRET_ACCESS_KEY` are the credentials you need to generate from you AWS account.

The second thing you need to do is to modify `src/main/resources/config.properties` file:

```
bucketName=bucket-name
keyName=file-name.json
region=region
```

where `bucket-name` is the name of the S3 bucket you created, `file-name.json` is the name of the file to save in (or resume from) the bucket and `region` is the region of your bucket.

If you leave the `file-name.json` field void, the game will automatically ask during the game configuration if you want to save the game in the bucket with a specified name ([check this link for all the valid formats](https://docs.aws.amazon.com/AmazonS3/latest/userguide/object-keys.html)), with a defualt name (`game-status.json`) or if you don't want to save the game at all: note that if you specify the name, the game will automatically add the `.json` extension, so you don't have to. Also, when resuming a game it will print a list of games saved in the bucket and will ask it's name to make you select one (in this case you need to specify the extension).

**Note**: saving the game status won't keep track of all the previous moves, so when restoring the game will just go back to the last move, and the back function will work from the restored state.

----------------------------------------------------------------------------------------------------------------------------------------
