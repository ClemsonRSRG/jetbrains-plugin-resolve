# Contributing

Simply cloning then trying to open the plugin in IntelliJ for the first time
is a somewhat involved (read: arduous) process. This document describes the way
I typically go about it (Note: I'm assuming you already have a copy of
IntelliJ installed).

*Keep in mind throughout this that I might be doing something outright wrong -- so it's 
kind of a case of the blind leading the blind here. That being said,
it seems to have worked pretty well for me so far :)*

## Step 1: shallow clone IntelliJ CE

To get started, you'll need to have a copy of the IntelliJ community edition (CE)
sourcecode readily available on your harddrive. So navigate to a directory of
your chosing, (for these instructions I'm using `/Documents/`) and run the
following clone command:

```
git clone --depth 1 https://github.com/JetBrains/intellij-community.git
```

**Note:** The above command does a *shallow* clone of the IntelliJ source code. The 
`--depth 1` part truncates history to a depth of 1; excluding this will result in your 
clone taking several hours (or more!) to complete.

## Step 2: clone RESOLVE plugin sources

Now that you have the IntelliJ sources, open the terminal, navigate to your preferred 
working directory (once again, I'm just using `Documents`) and run the following git 
command to retrieve a up to date copy of the RESOLVE plugin sourcecode:

```
git clone https://github.com/Welchd1/resolve-intellij-plugin.git
```

## Step 3: create an IntelliJ plugin module

With the above steps complete, fire up IntelliJ and click
`File > New > Project...` then you should be greeted with a window like this:

![new project](https://github.com/Welchd1/resolve-intellij-plugin/blob/master/doc/images/new-project.png)

Make sure you select `IntelliJ Platform Plugin` in the left sidebar, then click
the `new` button near the project SDK button and navigate to the folder
containing the IntelliJ sources cloned in step 1.

Once you see the IntelliJ sdk appear
(similar to the way it displayed in the above picture), clicking next should
bring up a page that looks something like:

![new project2](https://github.com/Welchd1/resolve-intellij-plugin/blob/master/doc/images/new-project2.png)

Click the button with three dots to the right of `Project Location` and navigate
to `resolve-intellij-plugin` folder cloned in step 2, the fields in the window
should then automatically update and look something like this:

![new project3](https://github.com/Welchd1/resolve-intellij-plugin/blob/master/doc/images/new-project3.png)

Finally, click finish.

## Step 4: mark module content roots

Todo


