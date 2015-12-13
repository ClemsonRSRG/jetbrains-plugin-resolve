# Cutting a Release of the RESOLVE Plugin

Assuming you've read and succesfully carried out the instructions [specified](https://github.com/Welchd1/resolve-intellij-plugin/blob/master/doc/contributing-instructions.md)
(and subsequently changed the plugin in some way), the following describes some of the
basic steps you will need to go through to deploy a new version of the plugin.

## Testing
Before deploying you should use JUnit to execute plugin tests. This can be done
natively in IntelliJ without the use of complex build tools like Maven by 
imply right clicking on the green `tests` folder and then selecting `Run All Tests`
from the menu:

![running tests](https://github.com/Welchd1/resolve-intellij-plugin/blob/master/doc/images/running-tests.png)

If you see a bunch of green checkmarks appear in the bottom window, chances are that all 
is well. If your looking to add some tests for a specific feature, go ahead and give 
[this](https://github.com/Welchd1/resolve-intellij-plugin/blob/master/doc/writing-tests.md) 
a read through for more details on working with IntelliJ specific test fixtures.

## Deploying

After adding and/or executing tests, you can package up the plugin for release by 
clicking the `Prepare Plugin Module ... for Deployment` button under `Build`:

![deploying](https://github.com/Welchd1/resolve-intellij-plugin/blob/master/doc/images/deploying.png)

### Github

Create a release candidate tag 0.x-rc-1 or full 0.x tag. For example,

```bash
git tag -a 0.2 -m 'RESOLVE plugin release 0.2'
git push origin 0.2
```

Create a pre-release or full release at github; [Example 0.1-rc-1](https://github.com/Welchd1/resolve-intellij-plugin/blob/master/releases/tag/0.2-rc-1).

## Submitting to Jetbrains

After all is said and done, to make the updated version of your plugin
available from the Jetbrains plugin repo, create an a jetbrains account and
submit the generated `resolve-intellij-plugin.zip`. Todo ...

