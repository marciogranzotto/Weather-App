# Loadsmart Challenge

This application starts with a list of the 10 largest cities on US by population and let's the user add new ones, then it shows the cities with pictures from Flickr and displays the current temperature from Open Weather.
This was a challenge for Loadsmart.

## Requirements
- Android Studio 3.0 or newer

## Considerations and Things to Improve
I haven't had a lot of time to invest in this project because I'm in the process of renting a new apartment and didn't had much free time.

With that in mind:
- Didn't implement a lot of unit tests, just a few on the MainPresenter.
- Didn't style much the app, used android default colors
- Didn't create an app icon
- Didn't use Dagger 2 to inject dependencies, which would have made my life easier when unit testing
- It's been a long time since I don't code in Java, I've been using Kotlin for all new projects in a while. Since Kotlin integrates seamlessly with Java, and now is a first class language for Android, I don't see why any new code should be implemented in Java, even on an all-Java project. However, since that was a requirement, I developed this project using Java 8
- I wanted to implement deletion of a city when long clicking it, but again, didn't had the time

## Decisions

You can check the `DECISIONS.md` file for a more in depth explanation
