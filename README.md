# World of Warcraft dungeon finder notification system
Waiting for the dugeon to be found may be long and tiresome, but some World of Warcraft versions don't notify you in any way if the game is minimized. As a person who got annoyed by a constant alt-tabbing between web browser and the game, I came up with this helful solution.

## Prerequisites
* Java 8 or later
* World of warcraft configured to display in a borderless window or windowed mode, as on my system the game stops event processing if it's minimized from a fullscreen mode

## How to use
Notifier system consists of two parts: notifier application and a World of Warcraft addon. To start getting notifications, install LFGNotifier addon (don't forget to enable it), launch notifier application, select your World of Warcraft screenshots directory (more about this later), optionally configure it to your taste (by default it only shows warning dialog window when the dungeon is found), start monitoring for an LFG proposal and fire the dungeon finder in the game (the order of the last two steps may be changed).

## How it works
World of Warcraft addons run in sandboxed environment and aren't allowed to communicate with you operating system in real-time, but I've found a way to bypass it: taking a screenshot. So the basic principle is as follows: the moment the LFG proposal is shown, the addon takes a screenshot, which is saved to the appropriate directory. The application watches for the directory updates and if there's a new file, it perceives it as a trigger to show the notification.

## FAQ
### Why java?
I want this project to run on windows, but I'm currently on Linux, so I decided to pick Java in order to achieve my goal

### Why java 8?
I checked the version of Java insalled on my friends' systems, and the oldest one was version 8. It's quite old, but I want users to be able to run this software without requiring to update anything

### Why Swing and not JavaFX / other more modern GUI frameworks?
It's already included in Java. I tried using JavaFX, but I didn't like the application size being increased from 3mb to 12
