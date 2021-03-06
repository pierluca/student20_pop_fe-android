# student20_pop: fe2-android branch
Proof-of-personhood, fall 2020: Android native front-end

## Table of contents
* [Technicalities](#technicalities)
* [Setup](#setup)
* [Github Actions](#github-actions)
* [Coding Standards](#coding-standards)

## Technicalities
* The target API is 29 and the minimum required is 26
* The [R class in Android](https://stackoverflow.com/questions/4953077/what-is-the-class-r-in-android) is an auto-generated class containing the resource IDs all the resources of res/directory.
* The keys are encoded in Base64 and stored as Strings, to acces the array of bytes one must decode the following way: `Base64.getDecoder().decode`

## Setup
For cloning, use `--recursive` as the project contains submodules.

#### Usage
Import the project to Android Studio or IntelliJ, choose an emulator in the AVD Manager and run the app. The emulator used for running and testing was a Pixel 2 API 29.

To run on an Android device connect it to the computer and run the following commands. Keep in mind that the minimum API required is 26.
```
gradle build
```
```
gradle installDebug
```
Find the app installed on the device and open it.

#### Test
Open the virtual device and run the following command:
```
./gradlew connectedCheck
```
It's also possible to run the tests using the option "Run with Coverage" from Android Studio.

## Github Actions

This project uses Github Actions as a CI, for more information go to the [workflows](https://github.com/dedis/student20_pop/blob/fe2-android/.github/workflows/fe2-android.yml) of this project.

This CI builds and runs the Unit Tests. For the Android Tests, the [reactivecircus](https://github.com/ReactiveCircus/android-emulator-runner) Android Emulator is used, which is limited. There are issues finding the resource values and checking [Toast messages](https://developer.android.com/reference/android/widget/Toast) appearance.

The Jacoco plugin can be set in the future for code coverage, this [guide](https://www.raywenderlich.com/10562143-continuous-integration-for-android#toc-anchor-013) covers how to set it.

## Coding Standards
This project follows the [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html). The [google-java-format](https://github.com/google/google-java-format) plugin allows very easy formatting.
### File Structure
The project is divided in 3 packages:
* **model**: for the classes used to model different entities
* **utility**: for the classes used to represent the application utilities
* **ui**: for the classes used to represent the fragments for the UI

The new files or packages are added into one of these packages.

The package **utility.network** follows the file structure of the branch [proto-specs](https://github.com/dedis/student20_pop/tree/proto-specs).
### Resource Values
The values used for the UI are stored in the corresponding xml
files (colors, dimens, strings or styles) in the res/values folder.

They can then be accessed using ```R.id``` or ```getResources()```.
