# Comp_Sci_Final
Final Project for Comp Sci II

To run this project, first clone the repository.
 - Copy the https clone URI from Github
 - In eclipse, navigate File->Import->Git->Projects from git (with smart import)
 - Select the Clone URI option. 
 - In the first window, paste the clone uri into the URI field and fill out your user info.
 - In the second window, branch selection, make sure only main breanch is selected  with a check mark.
 - In the third window, make sure the clone submodules box is checked.
 - Proceed to the fourth window and select finish.

Or, you can open it directly from a file.
 - In eclipse, navigate File->Open Project from File System
 - Click directory and navigate to the root folder "Comp_Sci_Final".
 - Select the folder and then click the "Select Folder" button
 - Click Finish

Next, you will need to import Gson and Javafx.

To import Gson:
 - Go to https://mvnrepository.com/artifact/com.google.code.gson/gson/2.9.0
 - Next to files, select "jar" to download
 - Put the Gson jar file into a folder you chose.
 - In eclipse, right click the project and navigate properties->Java Buildpath->Libraries
 - Under Module Path, remove gson.
 - Apply
 - Select Module Path and then Add External Jars.
 - Navigate to where you left the gson jar, select it, and click open.

To import Javafx:
 - Go to https://gluonhq.com/products/javafx/
 - Find the SDK file for your system and click download.
 - Extract the sdk folder into a folder you chose.
 - In eclipse, go to Window->Preferences->Java->Buildpath->User Libraries->New
 - Make a library named JavaFx
 - Select the library and then "Add External Jars"
 - Navigate to your sdk folder and open it. Open lib, select all jars, and then open.
 - Apply and close.
 - Right click the project and navigate properties->Java Buildpath->Libraries
 - Under Module Path, remove JavaFx
 - Apply.
 - Then, Select module path, navigate Library->User Library and select JavaFx
 - Click finish and Apply and Close.

NOTE: Sometimes eclipse gets confused during this process and will throw a ton of errors
      in regards to the imports. In this case, try removing them from the buildpath and
      adding them back in again. This does not mean redownloading them, just reference the 
      previous instructions when ading to the buildpath.
      
Now that everything is downloaded, there should be no errors. To run:
 - In the Left navigation menu, open Comp_Sci_Final->src->application
 - select Game->Run As->Java Application

Congrats! The game should now be running properly!

INSTRUCTIONS:

Retype a map from the list and enter it. At this point a screen will open with the map displayed. The first player can move their units while other players do not look. Then, when the player has used all their moves, they click continue to show the transition screen. The next player can then continue again with no one else looking and play their turn, and so on. During a turn, a player can select and move / attack by clicking the tiles and selecting the move and attack icons. The selected tile will be blue and then the tile to move / attack will be red with buttons on it representing those actions. Actions will happen immediately after the button is pressed. The game currently does not have win detection, so when only one team remains, the other players should be kind enough to inform them they have won. The console will output if the attempted action is unable to be executed for whatever reason (i.e. out of range, out of moves, etc).
