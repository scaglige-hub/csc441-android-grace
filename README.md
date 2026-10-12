# Movie List

Movie List is designed to keep track of the movies I have watched.

## Screens

- **Home** -- the user can enter movie titles, add the movies to the list, view the amount of movies in the list, and navigate to the other screens.
- **List** -- the user can see each individual movie, as well as remove each of them or all of them. can also return to home screen. When the list is empty, the empty list message and home button show up.
- **About** -- the user can see information about the app, and a button to return home.

## Input rules

| Empty | Enter a movie name |
| Fewer than 3 characters | Too short -- at least three characters |
| More than 40 characters | Keep it to 40 characters or fewer|
| Movie that is already on the list | The movie is already on the list |
|'<' or '>'| No < or > please |

## How to run
1. Clone this repo
2. Open the cloned folder in Android Studio
3. Wait for the Gradle sync, then run

## Known limitations
Changes to the movie list do not save when the app is closed and re-opened.
