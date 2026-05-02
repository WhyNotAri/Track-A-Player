# Minecraft Tracker Plugin
Give the player who runs the `/tracker <player>` command a compass that tracks the selected player. User can right-click the compass to update the location of the target.

## Commands

`````
/track <player>
/hello
`````

## Features
- If you want to track a player using a compass use `/track <player>` and right-click it everytime you want to see where their last position was
- The `Locator Bar` is disabled so you can track a player only by using the compass

## Requirements
- Java 21
- Paper 1.21.11
- Gradle

## Installation

1. Build the plugin `.jar`
2. Put it inside the `/plugins` folder of the Paper server
3. Run the server and check the logger

## Build from source

### Clone the repository
```
git clone https://github.com/WhyNotAri/My-Paper-Plugin.git
```

### Build with Gradle
```
./gradlew build
```

### Generated file

`.jar` will be inside `/build/libs` folder