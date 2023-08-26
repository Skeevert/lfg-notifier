# World of Warcraft dungeon finder notification system
Waiting for the dungeon to be found may be long and tiresome, but some World of Warcraft versions don't notify you in any way if the game is minimized. As a person who got annoyed by constant alt-tabbing between a web browser and the game, I came up with this helpful solution.

## Prerequisites
* Java 8 or later
* World of Warcraft configured to display in a borderless window or windowed mode, as on my system the game stops event processing if it's minimized from a full-screen mode

## How to use
The notifier system consists of two parts: the notifier application and a World of Warcraft add-on. To start getting notifications, install the LFGNotifier add-on (don't forget to enable it), launch the notifier application, select your World of Warcraft screenshots directory (more about this later), optionally configure it to your taste (by default it only shows a warning dialog window when the dungeon is found), start monitoring for an LFG proposal, and fire the dungeon finder in the game (the order of the last two steps may be changed).

## How it works
World of Warcraft add-ons run in a sandboxed environment and aren't allowed to communicate with your operating system in real-time, but I've found a way to bypass it: taking a screenshot. So the basic principle is as follows: the moment the LFG proposal is shown, the add-on takes a screenshot, which is saved to the appropriate directory. The application watches for directory updates, and if there's a new file, it perceives it as a trigger to show the notification.

## FAQ
### Why java?
I want this project to run on Windows, but I'm currently on Linux, so I decided to pick Java in order to achieve my goal.

### Why java 8?
I checked the version of Java installed on my friends' systems, and the oldest one was version 8. It's quite old, but I want users to be able to run this software without requiring them to update anything.

### Why Swing and not JavaFX / other more modern GUI frameworks?
It's already included in Java. I tried using JavaFX, but I didn't like the application size being increased from 3 MB to 12 MB.
