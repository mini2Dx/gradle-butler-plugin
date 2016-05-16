# gradle-butler-plugin
A gradle plugin for the [itch.io butler command line tool](https://docs.itch.ovh/butler/master/)


## How to use

Add the following buildscript configuration to the top of your build.gradle

```gradle
buildscript {
    repositories {
        mavenLocal()
        jcenter()
        mavenCentral()
        maven { url "https://mini2dx.org/maven/content/repositories/releases" }
    }
    dependencies {
        classpath group: 'org.mini2Dx', name: 'butler', version: '1.0.0'
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
   
      windows {
         binDirectory = "C:\\path\to\game\bin\directory"
      }
      osx {
         binDirectory = "/path/to/game/bin/directory"
      }
      linux {
         binDirectory = "/path/to/game/bin/directory"
      }
   }
}
```

The plugin will add the following tasks to your project.

| Task  | Description |
| ------------- | ------------- | 
| butlerUpdate  | Updates butler to latest stable version or installs it if it is not present. All other tasks depend on this task so you do not need to call it explicitly. |
| butlerLogin  | Calls ```butler login``` |
| butlerLogout  | Calls ```butler logout``` |
| butlerPush  | [Pushes the build](https://docs.itch.ovh/butler/master/pushing.html) using butler |

The [channel](https://docs.itch.ovh/butler/master/pushing.html#channel-names) is chosen based on the current OS. To push a release of your game for a platform you must run the task on that platform, i.e. You must be on Mac OS X to push a Mac game release.

## Advanced Configuration

There are several optional configuration parameters available.

| Option  | Type | Default | Description |
| ------------- | ------------- | ------------- | ------------- |
| user  | String | _blank_ | (**required**) Your itch.io username |
| game  | String | _blank_ | (**required**) The itch.io game id |
| updateButler  | boolean | true | Set to false to disable butler updates |
| alphaChannel  | boolean | false | When true appends -alpha to the channel name |
| betaChannel  | boolean | false |  When true appends -beta to channel name |
| userVersion  | String | null | Set this if you want to override itch.io's version number |
| windows.butlerInstallDirectory  | String | null | Set if you want to override the automatic Butler install directory on Windows |
| windows.binDirectory  | String | _blank_ | (**required**) The directory of your game's Windows build |
| osx.butlerInstallDirectory  | String | null | Set if you want to override the automatic Butler install directory on OS X |
| osx.binDirectory  | String | _blank_ | (**required**) The directory of your game's OS X build |
| linux.butlerInstallDirectory  | String | null | Set if you want to override the automatic Butler install directory on Linux |
| linux.binDirectory  | String | _blank_ | (**required**) The directory of your game's Linux build |

The following example shows all options in use.

```gradle
project(":projectName") {
   apply plugin: "org.mini2Dx.butler"
   
   ........

   butler {
      user = "your-itchio-user"
      game = "your-itchio-game"
      updateButler = true
      alphaChannel = false
      betaChannel = false
      userVersion = project.version
   
      windows {
      	 butlerInstallDirectory = "C:\\path\to\butler\directory"
         binDirectory = "C:\\path\to\game\bin\directory"
      }
      osx {
         butlerInstallDirectory = "/path/to/butler/directory"
         binDirectory = "/path/to/game/bin/directory"
      }
      linux {
         butlerInstallDirectory = "/path/to/butler/directory"
         binDirectory = "/path/to/game/bin/directory"
      }
   }
}
```

## License

The code is released under the [MIT License](https://github.com/mini2Dx/gradle-butler-plugin/blob/master/LICENSE).

## Contributing

Pull requests are welcome :) Any issues found please add them to the [Issue Tracker](https://github.com/mini2Dx/gradle-butler-plugin/issues).

Gradle wrapper is included in the code. The following tools were used for development:
* Gradle 2.13