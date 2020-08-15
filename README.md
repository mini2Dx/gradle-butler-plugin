# gradle-butler-plugin
[![Build Status](https://travis-ci.org/mini2Dx/gradle-butler-plugin.svg?branch=master)](https://travis-ci.org/mini2Dx/gradle-butler-plugin)

A gradle plugin for automatically installing, updating and running the [itch.io butler command line tool](https://docs.itch.ovh/butler/master/) as part of your build.


## How to use

Add the following buildscript configuration to the top of your `build.gradle`

```gradle
buildscript {
    repositories {
        mavenLocal()
        jcenter()
        mavenCentral()
    }
    dependencies {
        classpath group: 'org.mini2Dx', name: 'butler', version: '2.0.0'
    }
}
```

Then add the plugin configuration to your project.

```gradle
project(":projectName") {
   apply plugin: "org.mini2Dx.butler"

   ........

   butler {
      user = "your-itchio-user"
      game = "your-itchio-game"
   }
}
```

Then configure tasks with the channels you want to push:

```gradle
project(":projectName") {

    ........

    task butlerPush(type: org.mini2Dx.butler.task.PushTask) {
        binDirectory = "/path/to/game/bin/directory"
        channel = "example"
    }
}
```

The plugin will add the following tasks to your project.

| Task  | Description |
| ------------- | ------------- |
| butlerUpdate  | Updates butler to latest stable version or installs it if it is not present. All other tasks depend on this task so you do not need to call it explicitly. |
| butlerLogin  | Calls ```butler login``` |
| butlerLogout  | Calls ```butler logout``` |

As well as providing a task template with `org.mini2Dx.butler.task.PushTask` which can be used to create tasks which [push builds](https://docs.itch.ovh/butler/master/pushing.html) using butler.

You can configure multiple push tasks, only the binDirectory and channel have to be provided for each.

| Push Task Property  | Description |
| ------------- | ------------- |
| binDirectory  | (**required**) The directory containing your game's build, which will be pushed by butler |
| channel  | (**required**) The [channel](https://docs.itch.ovh/butler/master/pushing.html#channel-names) to release your build to |

## Advanced Configuration

There are several optional configuration parameters available.

| Option  | Type | Default | Description |
| ------------- | ------------- | ------------- | ------------- |
| user  | String | _blank_ | (**required**) Your itch.io username |
| game  | String | _blank_ | (**required**) The itch.io game id |
| updateButler  | boolean | true | Set to false to disable butler updates |
| userVersion  | String | null | Set this if you want to override itch.io's version number |
| allChannelsPostfix  | String | null | Set this to append the given string to every channel name |
| butlerInstallDirectory  | String | null | Set if you want to override the automatic Butler install directory |

The following example shows all options in use.

```gradle
project(":projectName") {
   apply plugin: "org.mini2Dx.butler"

   ........

   butler {
      user = "your-itchio-user"
      game = "your-itchio-game"
      updateButler = true
      allChannelsPostfix = "-beta"
      userVersion = project.version
      butlerInstallDirectory = "C:\\path\to\butler\directory"
   }
}
```

## Depending on your project build task
You will most likely want your `pushTask` to depend on the gradle task that builds your game binaries. This will ensure the project is automatically build when calling the task.
This example uses the `build` task, but it might be different for your project configuration.

```gradle
task butlerPush(type: org.mini2Dx.butler.task.PushTask) {
    dependsOn build
    binDirectory = "C:\\path\to\game\bin\directory"
    channel = "windows"
}
```

## Upgrading from version 1.1.3 or earlier

If you've used the plugin before and are upgrading from 1.1.3 or an earlier version, these steps will help you port your gradle code:

**1. convert any pre-defined channels (windows/osx/linux/anyOs) you used into tasks**
  * the binDirectory stays the same
  * the channel name now always needs to be specified explicitly
  * butlerInstallDirectory is now a property of the "butler" config directly

As an example:
```gradle
butler {
    ...
    windows {
        butlerInstallDirectory = "C:\\path\to\butler\directory"
        binDirectory = "C:\\path\to\game\bin\directory"
    }
}
```
This would need to be changed to the following (note `butlerPushWindows` is an arbitrary name, you can call your task however you like):
```gradle
butler {
    ...
    butlerInstallDirectory = "C:\\path\to\butler\directory"
}

task butlerPushWindows(type: org.mini2Dx.butler.task.PushTask) {
    binDirectory = "C:\\path\to\game\bin\directory"
    channel = "windows"
}
```

**2. If you want to have just a single task to push multiple channels, you can have a task that depends on the other tasks.**

E.g.
```gradle
task butlerPush() {
    dependsOn butlerPushWindows, butlerPushOsx, butlerPushLinux, butlerPushAnyOs
}
```

Given those tasks exist, the gradle task "butlerPush" will execute them all.

**3. If you've previously made use of the "alphaChannel" or "betaChannel" property, you can now set the "allChannelsPostfix" property to "-alpha" or "-beta" to achieve the same effect.**
```gradle
butler {
    ...
    allChannelsPostfix = "-alpha"
}
```

**4. If you need to configure a different `butlerInstallDirectory` on different machines, you can use a separate script file that you can keep outside of version control.**
E.g. like so:

**localproperties.gradle**
```gradle
ext {
    BUTLER_INSTALL_DIR = '/path/to/butler'
}
```

**build.gradle**
```gradle
apply from: 'localproperties.gradle'

...

butler {
    ...
    butlerInstallDir = BUTLER_INSTALL_DIR
}
```

## License

The code is released under the [MIT License](https://github.com/mini2Dx/gradle-butler-plugin/blob/master/LICENSE).

## Contributing

Pull requests are welcome :) Any issues found please add them to the [Issue Tracker](https://github.com/mini2Dx/gradle-butler-plugin/issues).

Gradle wrapper is included in the code. The following tools were used for development:
* Gradle 5.6.4