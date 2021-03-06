# Code changes plugin

## Description
This plugin for IntelliJ IDEA highlights the code of a Java project based on the changes made on the current commit. It also adds visual elements that, when you hover on them, they show a popup with additional information. Some of those visual elements have click actions. This plugin also overrides the icons of the IntelliJ IDEA file explorer to show with colors on the icons the amount of changes of each file.

![Screenshot](screenshot.png)

You can find a more detailed documentation [here](https://drive.google.com/file/d/10WUMjam9sBdOQWg-KSrVUyd6ac_nvbG3/view?usp=sharing), and a video demo/tutorial (a bit outdated though) [here](https://drive.google.com/file/d/165ZrBeBLZlIeIR58afpkftSMYStX3hE_/view?usp=sharing).

## Themes

On the main branch, this plugin by default works for dark theme. It also works on light theme, but there are some issues with specific colors. The code working for light theme is on the branch [light-theme](https://github.com/ronaldescobarj/highlight-plugin-poc/tree/light-theme).

## How to run

From IntelliJ IDEA, click on the Gradle side menu at the right, and on tasks, look for intellij folder and double click on "runIde" task.

## How to build

It is almost same procedure as for running, but instead of the "runIde" task, on the same gradle tasks intellij folder, double click on "buildPlugin". If the build succeeded, it will be located in build/distributions folder starting from the root of the project.

## How to install

In case you don't want to manually build the plugin, you can download the dark theme and/or the light theme installer from [this folder](https://drive.google.com/drive/folders/1BonHAqqSyg-y0ldaf2NMcShrtm6cMois?usp=sharing) on Google Drive.

To install:

1. On IntelliJ IDEA go to File -> Settings
2. Click on "Plugins" on the side menu on the left.
3. Click on the little gear icon at the top right and select "Install Plugin From Disk...".
4. Select the location of the zip of the plugin (whether you builded it manually or downloaded it).
5. Press OK.
6. Restart the IDE.