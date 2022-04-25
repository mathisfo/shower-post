# README

1. [Running the game](https://github.com/mathisfo/shower-post#running-the-game)
    *  [Quick Start](https://github.com/mathisfo/shower-post#quck-start)
    * [Compile and run through source code](https://github.com/mathisfo/shower-post#compile-and-run-through-source-code)
2. [Code Structure](https://github.com/mathisfo/shower-post#compile-and-run-through-source-code)
    * [Model](https://github.com/mathisfo/shower-post#model)
    * [Observers](https://github.com/mathisfo/shower-post#observers)
    * [Storage](https://github.com/mathisfo/shower-post#storage)
    * [View](https://github.com/mathisfo/shower-post#view)
    * [ViewModel](https://github.com/mathisfo/shower-post#viewmodel)

## Running the game
### Quck Start

The easiest way to run the game, is by dragging the provided `wordwar.apk` file located in the `dist` folder into your android emulator. The game has been tested on a Pixel 2 emulator with API level 29.

It’s also possible to download the apk file to your Android device if applicable. OnePlus 9 and OnePlus 8 on Android 11 have been tried and confirmed working.

### Compile and run through source code

1. Clone this repo.
2. Open the repo by opening the `build.gradle` file as a project in Android Studio
3. Gradle will sync the dependencies. This might take a while.
4. Run the game as an Android application configuration.
5. The game will open in your emulator.

## Code structure


The code structure follows the Model-View-ViewModel (MVVM). The code that is associated to MVVM  is found in `app/src/main/java` facilitates all the business logic relevant to the app. The details of each package and how it is relevant to the different design and architectural patterns are elaborated further in the report. However, a quick summary follows:

### model


[model](https://github.com/mathisfo/shower-post/tree/main/app/src/main/java/com/progark/gameofwits/model)

The model package includes all the objects that are used within the game. Since Firebase is not object oriented, the repository pattern maps between database entities and objects useful in the game. The objects that are associated to their related entities are located in the model package.

### Observers


[observers](https://github.com/mathisfo/shower-post/tree/main/app/src/main/java/com/progark/gameofwits/observers)

The observers package follows the observer pattern. The content of this package acts as abservers to notify whenever a user changes its local state of the game. For example, the `PlayerEventSource` notifies the `PlayerObserver` interface when all users have submitted their word for each round. Finally, the host is notifed through the interface and is allowed to continue to the next round.

### Storage


[storage](https://github.com/mathisfo/shower-post/tree/main/app/src/main/java/com/progark/gameofwits/storage)

The storage package is part of the repository pattern and translates database entities to game objects in `model` . Since the database entity and game object do not always strictly inherit all fields, a data transferable object (DTO) is created. For example, when a new game is created, the id of the lobby is generated back-end. The game dto ([GameDoc.kt](https://github.com/mathisfo/shower-post/blob/main/app/src/main/java/com/progark/gameofwits/storage/documents/GameDoc.kt) in this case) is sent to the server without an id field. In the next step, when fetching the same game, the Repository interface translates the game back to a game object.

Storage also includes the logic for interacting with observers from [observers](https://github.com/mathisfo/shower-post/tree/main/app/src/main/java/com/progark/gameofwits/observers)

### View


[View](https://github.com/mathisfo/shower-post/tree/main/app/src/main/java/com/progark/gameofwits/view)

The view package contains all views that are displayed to the user. These are implemented through the Fragment class of the Android SDK. The views of the view package ties the user interaction into business logic. Since the GUI is drawn from the xml files in the [res](https://github.com/mathisfo/shower-post/tree/main/app/src/main/res/layout) folder, the views creates a binding between user interactable objects and business logic. For example, [GameRoundFragment.kt - Line 85](https://github.com/mathisfo/shower-post/blob/main/app/src/main/java/com/progark/gameofwits/view/GameRoundFragment.kt#L85) is invoked when the player presses the “Submit Word" button in a round. 

### ViewModel


The viewmodel package contains the layer between view and model layer. When clicking the button from the example above, the `GameViewModel` operates as the binding between UI elements to its corresponding controls. For the “Submit Word" button case, the `GameViewModel` invokes the repository to update the user's answer.
