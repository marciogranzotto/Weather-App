# Loadsmart Challenge

This application starts with a list of the 10 largest cities on US by population and let's the user add new ones. Then it show the cities with pictures from Flickr and current temperature from Open Weather.
This was a challenge for Loadsmart.

## Requirements
- Android Studio 3.0 or newer

## Considerations and Things to Improve
I haven't had a lot of time to invest in this project because I'm renting a new apartment and didn't had much free time.

With that in mind:
- Didn't implemented a lot of unit tests, just a few on the MainPresenter.
- Didn't styled much the app, used default colors
- Didn't created an app icon
- Didn't used Dagger 2 to inject dependencies, which would have made my life easier when unit testing
- It's been a long time since I don't code in Java, I'm just using Kotlin for all new projects for a while. Since Kotlin is integrates seamless with Java, and now is a first class language for Android, I don't see why any need code should be implemented on Java, even on an all-Java project. However, since that was the requirement, I developed all this project using Java 8
- I wanted to implement deletion of a city when long clicking it, but again, didn't had the time

## Decisions

You can check the `DECISIONS.md` file to a more in depth explanation
