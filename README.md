# Project

For this project, you have to make an android app, which fetch the Github API to show all user. 
You must follow all principles we saw during the _Clean Coding_ course.

This project is the template of the project. You don't have to add gradle dependencies to add feature (there is one exception, we will explain it later)

If I suspect cheat in your project, you will have a 0. 

## Wording

As said during the course, there is **3** level of difficulty.

### All level

 - You must follow Material theme. Everything we saw in course is in this project. For more information, you can fid the documentation on [Material](https://material.io) website.
 - You have to integrate the design made by Valentine Carreau (Appsolute Team). You can check it [here](https://zpl.io/bzRnQvA)
 - You must add a Continuous Integration solution to your project. We saw **Bitrise** during the course, you can use this one or another but is it's another one, send me a message.
 - **Everything** you make in the package `data` must be tested (Unit Test or Instrumented Test) 
 - You must follow the Git Flow. For each point, you must create a new feature branch. To change level, you must create a new Release   
 
### Level 1

- [ ] _1_: Make the call to the API to fetch the first page of `GitHubUser`. 
- [ ] _2_: Display all instance in the list
- [ ] _3_: On click on a item in the list, go to the details view
- [ ] _4_: The details view must contains user information. Choose what you want in the object to display 

### Level 2

- [ ] Make all the _Level 1_
- [ ] _5_: Create the pagination for the user list
- [ ] _6_: In the CI, make a publication with Github Release
- [ ] _7_: On long click on a item in the list, display an AlertDialog which ask the user if he wants to save the user in the database. If yes, save the user in the database.
- [ ] _8_: Setup Crashlytics only for build variant `release`. you can find documentation [here](https://firebase.google.com/products/crashlytics/) 

### Level 3 

- [ ] Make all the _Level 2_
- [ ] _9_: Make a Search feature with the API (documentation [here](https://developer.github.com/v3/search/#search-users))
- [ ] _10_: When you click on a item in the list, put it in a "cache" to fetch it on the details view
- [ ] _11_: Add a menu to the list view, to sort the list (API Feature, documentation [here](https://developer.github.com/v3/search/#search-users))

## Resources

- [GitHub Api Documentation](https://developer.github.com/v3/users/) 
- [Get all users from API](https://developer.github.com/v3/users/#get-all-users)
- [Zeplin Design](https://app.zeplin.io/project/5de41a4846820f7c2771213a/dashboard)

## Marking-scheme

### All level 

|  Level     |  Feature Name          |  Mark  |  Description                                                                                                               |
|------------|----------------------|--------|----------------------------------------------------------------------------------------------------------------------|
|  All Level |  Naming/File/Package   |  2     |  Follow the naming convention of XML and Kotlin                                                                            |
|  All Level |  Architecture          |  2     |  Follow the MVVM from Android Architecture Components (same as [Template](https://github.com/aearphen/clean-coding-tp)) |
|  All Level |  CI - Test Workflow    |  2     |  Make a workflow triggered all time you push on a feature branch                                                           |
|  All Level |  Git Flow              |  2     |  Follow the git flow, with clean commit message                                                                            |
|  All Level |  Unit test             |  2     |  Make all unit test in the package `data`                                                                                  |
|  Level 1   |  _1_                   |  1     |  See the Description above                                                                                                 |
|  Level 1   |  _2_                   |  0.5   |  See the Description above                                                                                                 |
|  Level 1   |  _3_                   |  0.5   |  See the Description above                                                                                                 |
|  Level 1   |  _4_                   |  1     |  See the Description above                                                                                                 |
|  Level 2   |  _5_                   |  1     |  See the Description above                                                                                                 |
|  Level 2   |  _6_                   |  1     |  See the Description above                                                                                                 |
|  Level 2   |  _7_                   |  1     |  See the Description above                                                                                                 |
|  Level 2   |  _8_                   |  1     |  See the Description above                                                                                                 |
|  Level 3   |  _9_                   |  1     |  See the Description above                                                                                                 |
|  Level 3   |  _10_                  |  1     |  See the Description above                                                                                                 |
|  Level 3   |  _11_                  |  1     |  See the Description above                                                                                                 |


## Material Theme

### Color

| Name                     | Color Day | Color Night  |
| ----------------------- | -------- | ------------ |
| color_primary            |  #0034de  |  #000bab     |
| color_primary_variant    |  #000bab  |  #0034de     |
| color_secondary          |  #b00020  |  #f43821     |
| color_secondary_variant  |  #f43821  |  #b00020     |
| color_background         |  #dfdfdf  |  #141414     |
| color_surface            |  #efefef  |  #262626     |
| color_error              |  #daa53b  |  #daa53b     |
| color_on_primary         |  #ffffff  |  #ffffff     |
| color_on_secondary       |  #ffffff  |  #ffffff     |
| color_on_background      |  #121212  |  #ffffff     |
| color_on_surface         |  #2b2b2b  |  #ffffff     |
| color_on_error           |  #000000  |  #000000     |

### Shape 

|  Attributes     | SmallComponent  | MediumComponent  | LargeComponent  |
|----------------|----------------|-----------------|-----------------|
|  cornerSize     |  8dp            |  10dp            |  12dp           |
|  cornerFamily   |  rounded        |  rounded         |  rounded        |

### Fonts

| Name                     |  Fonts                   |
| ----------------------- | ---------------------- |
| Headline1                |  Open Sans Bold          |
| Headline2                |  Open Sans Bold          |
| Headline3                |  Open Sans Bold          |
| Headline4                |  Open Sans Heavy         |
| Headline5                |  Open Sans Heavy         |
| Headline6                |  Open Sans Heavy         |
| Subtitle1                |  Open Sans Medium        |
| Subtitle2                |  Open Sans Medium        |
| Body1                    |  Open Sans Book          |
| Body2                    |  Open Sans Book          |
| Button                   |  Open Sans Medium        |
| Button2                  |  Open Sans Medium        |
| Caption                  |  Open Sans Heavy         |
| Overline                 |  Open Sans Bold Oblique  |


## Tips

Your best friend during this project is the official documentation of Android.
You can also check the Specify documentation for specific libraries

Make the app user friendly, put some loaders, transition, animation etc ... (You can have *2* bonus mark for that 😉)

## Contact

You can contact me by mail or directly on Slack. 

Your object in the mail must be prefixed by `[LP IEM]`


