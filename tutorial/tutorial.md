# Tutorial

This tutorial explains how to load the projects with the specific commits in order to replicate the examples, and load the plugin in order to have the tool working on those examples.

The repositories used on the main experiment are [Apache Drill](http://github.com/apache/drill) and [Apache Giraph](http://github.com/apache/giraph), and the repository used on the pilot is [Apache Commons IO](https://github.com/apache/commons-io). The commits are:

- Apache Drill commit [c1b847](https://github.com/apache/drill/commit/c1b847acdc8cb90a1498b236b3bb5c81ca75c044)
- Apache Giraph commit [03ade](https://github.com/apache/giraph/commit/03ade425dd5a65d3a713d5e7d85aa7605956fbd2)
- Apache Commons IO Commit [4a514](https://github.com/apache/commons-io/commit/4a514d3306b55b3667d1449ebd4cbe5f19dd7af0)
## Steps
On the following steps we are going to use Apache Giraph's commit, but the same steps apply for the Drill and Commons IO commits:

The experiment has been made with IntelliJ IDEA 2021.1. There have been problems trying to build for the latest IntelliJ IDEA version, but using the 2021.1 version works and it will also replicate with the exact version the participants used on the experiment.

### 1. Download IntelliJ IDEA 2021.1
If you're using Windows, you can directly click into [this link](https://download.jetbrains.com/idea/ideaIC-2021.1.exe?_gl=1*7niu09*_ga*MTU5NjE1NzI0Ny4xNjM4MzI2NjE1*_ga_9J976DJZ68*MTY2MDcwMzg0NS4xMi4wLjE2NjA3MDM4NDUuMC4wLjA.&_ga=2.16027126.1302933131.1660703846-1596157247.1638326615) and your download of IntelliJ IDEA 2021.1 will start automatically. Or you can follow these steps:
1. Open this link https://www.jetbrains.com/es-es/idea/download/other.html
2. Scroll down until finding "Version 2021.1"
![Download general](/tutorial/01_downloadGeneral.png)
3. Click on the dropdown box and select "2021.1"
![Version dropdown.png](/tutorial/02_versionDropdown.png)
4. Click on one of the options according to your opoerative system. The download will start automatically
![Versions for OS](/tutorial/03_20211version.png)
5. Once the download finishes, run the installer and follow the steps to install IntelliJ IDEA 2021.1 (The wizard/steps/configuration may vary between operative systems.)
### 2. Clone Apache Giraph repository
On your terminal, execute the following command to clone the Apache Giraph repository.

` git clone https://github.com/apache/giraph.git `
### 3. Open the IntelliJ IDEA 2021.1 that was just installed on step 1
### 4. Open Apache Giraph from IntelliJ IDEA
1. Within your IntelliJ IDEA, click on File -> Open
![Open](/tutorial/04_open.png)
2. Select the folder where you cloned apache giraph and click OK
![Giraph folder.png](/tutorial/05_selectGiraph.png)
3. On the following dialog, select "New Window" since you will need to restart IntelliJ IDEA later anyways.
![Open on new window](/tutorial/06_newWindow.png)
3. Now you have an IntelliJ IDEA instance with the Apache Giraph project opened. Click on "Terminal" on the bottom bar.
![Terminal](/tutorial/07_terminal.png)
4. Now on the terminal, execute the following command that will take you to the desired commit

`git checkout 03ade425dd5a65d3a713d5e7d85aa7605956fbd2`

You should see an output on the terminal like on the following screenshot:
![git checkout](/tutorial/08_gitCheckout.png)

### 5. Verify themes and other plugins
Before installing the plugin, is important to check if you're using the right IntelliJ IDEA theme and if you don't have other plugins that might interfere with the highlight plugin. To do so:

1. Within your IntelliJ IDEA, click on File -> Settings
![File settings](/tutorial/09_fileSettings.png)
2. Click on Appearance, under Appearance & Behavior.
![Appearance](/tutorial/18_appearance.png)
3. On the Theme dropdown, verify that the theme is either IntelliJ Light or Darcula. This is important because it's only tested with those two themes. For this tutorial we are using IntelliJ Light.
![Themes](/tutorial/19_themes.png)
4. To verify the plugins, click on the Plugins option on the left menu
![Plugins](/tutorial/10_plugins.png)
5. Click on "Installed" at the right side of Marketplace, to see the installed plugins
![Installed plugins](/tutorial/20_installed.png)
6. On the list of installed plugins, verify if you don't have installed any plugin that might interfere with the highlight plugin. Preferably, just leave the plugins that have "bundled" below the name, and the themes, since, if you followed the previous steps, you should have either IntelliJ Light or Darcula set as a theme so no need to worry about the other themes installed on plugins. In the screenshot, RefactorInsight is the only plugin which is not a theme or bundled, but it does not interfere with the plugin. This is just a heads up to be careful, and to know that if for some reason the plugin doesn't work correctly, might be because of other plugin(s) interfering.
![Installed plugins list](/tutorial/21_pluginsInstalled.png)
7. Once done, just click on Apply and then click on OK.
![Apply and OK](/tutorial/22_applyOk.png)
### 6. Download and install the plugin
1. Download the plugin installer from [this Google Drive folder](https://drive.google.com/drive/folders/1BonHAqqSyg-y0ldaf2NMcShrtm6cMois). Keep in mind a couple of things:
   - On the folder, there are different installers for light theme and dark theme. Download the installer you need for your theme of preference.
   - Do not unzip the installer, since the plugin needs to be installed as a whole zip.
2. Within your IntelliJ IDEA, click on File -> Settings
![File settings](/tutorial/09_fileSettings.png)
3. Click on "Plugins" on the side menu on the left.
![Plugins](/tutorial/10_plugins.png)
4. Click on the little gear icon at the top right and select "Install Plugin From Disk...".
![Install from disk](/tutorial/11_installFromDisk.png)
5. Select the location of the zip of the plugin (whether you builded it manually or downloaded it) and click OK.
 ![Select installer](/tutorial/12_selectInstaller.png)
6. The highlight plugin should now appear among all your other plugins. Click on it and you should see the title and description of the plugin at the right side.
![Plugin installed](/tutorial/13_pluginInstalled.png)
7. Click on Apply to apply the plugin to IntelliJ IDEA.
![Apply](/tutorial/14_apply.png)
8. Once applied, click on OK to close the settings.
![OK](/tutorial/15_ok.png)
9. After closing settings, you might notice an error message. Don't worry, you need to restart the IDE so that the plugin actually applies and for the error message to disappear. Close IntelliJ IDEA and on the prompt, click on Exit.
![Exit](/tutorial/16_exit.png)
10. Re open IntelliJ IDEA.
11. You should now have the plugin installed in your IntelliJ IDEA instance. You should be able to browse through the files, see the updated icons on the files and if you open an updated file, you should be able to see the new highlighting, the tags and when hovering the tags you should be able to see the infobox like on the screenshot.
![Restarted and applied](/tutorial/17_restartedAndApplied.png)
