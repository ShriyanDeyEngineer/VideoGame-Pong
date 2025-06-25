Author: Shriyan Dey
Purpose of this project: Made to develop a coding portfolio and to have fun



Project Description

A classic PONG game remake built in Java to practice and demonstrate my programming skills. Developed in Eclipse, this project includes both .jar and macOS .app versions use.
All the credits and reflections can be found in the commit for this repository. In the commit, click "src", then click "Credits_Learnings_Reflections". Alternatively, you can download and run the game instead and open the credits there on the main menu.


LISCENSE
This project is released under the MIT License, which allows free use, modification, and distribution with proper credit.


---

Features

- Single (Left/Right arrow keys to move the paddle) and 2-player (2 people use same device but different key binds: W/S keys vs. Up/Down arrow keys) modes 
- Sound effects and custom soundtrack made in garage band along with other sound tracks that were generated via Riffusion AI   
- Settings and Credits screens  
- Pause/Resume game feature
- Clean UI

---

How to Download, Install, and Run It

For macOS:
1. Go to the releases for this repository and click on "RunPONG.app.zip" and let it download.
2. Unzip it and move RunPONG.app to your Applications or Desktop.
3. On first launch, macOS may warn that it can't verify the developer. To bypass:
   - Right-click the app → Click **Open** → Confirm in the popup.
   - After the first time, it will open normally.
   - Otherwise, after the file has downloaded, open terminal (command + space -> type "terminal" and then hit Return/Enter) then copy, paste, and enter the next 2 lines below all at once:
     xattr -cr ~/Downloads/RunPONG.app
     open ~/Downloads/RunPONG.app

For Java Users (Any OS):
1. Ensure you have [Java 17+](https://adoptopenjdk.net/) installed.
2. Go to the releases for this repository and click on "RunPONG.jar" and let it download.
3. Open a terminal then copy, paste, and enter the next 2 lines below all at once:
cd ~/Downloads
java -jar RunPONG.jar
