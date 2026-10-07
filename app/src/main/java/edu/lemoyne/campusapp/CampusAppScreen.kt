package edu.lemoyne.campusapp

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.lemoyne.campusapp.ui.theme.CampusAppTheme

// --- Class 7: Step 1: a counter that remembers
@Composable
fun CounterDemo() {
    var count by remember { mutableStateOf(0) }

    Button(
        onClick = { count++ }
    ) {
        Text(text = "Tapped $count times")
    }
}

//--- Class 9: Step 2: one owner for the data ---
@Composable
fun CampusAppScreen(modifier: Modifier = Modifier) {
    val movies = remember {
        mutableStateListOf(
            "Cinderella Man",
            "Die Hard",
            "2001: A Space Odyssey",
            "Revenge of the Sith"
        )
    }
    // --- Class 9: Step 4: which screen is showing is just state ---
    var currentScreen by rememberSaveable { mutableStateOf(value = "home")}

    when (currentScreen) {
        "home" -> HomeScreen(
            movies = movies,
            onAddMovie = {movies.add(it)},
            onSeeAll = {currentScreen = "list"}
        )
        "List" -> ListScreen(
            movies = movies,
            onBack = {currentScreen = "home"},
            modifier = modifier
        )

    }
}

// --- Class 6: Step 1: My own screen ---
@Composable
fun HomeScreen(
    movies: MutableList<String>,
    onAddMovie: (String) -> Unit,
    onSeeAll: () -> Unit,
    modifier: Modifier = Modifier) {
    // --- Class 7: Step 3: what is typed lives in state---
    var newMovie by remember { mutableStateOf("") }
    //---Class 8: Step 2: the error message lives in state too ---
    var error by remember { mutableStateOf<String?>(null) }

    //--- Class 6: Step 3: a column so things stack ---
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(24.dp)
    ) {
        //CounterDemo()
        // --- Lab 6: Task 1: Make the screen properly yours ---
        Text(
            text = "Movie List",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))


        Text(
            text = "Movies I have watched",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        // --- Lab 7: Task 2: singular or plural ---
        Text(
            text = if (movies.size == 1) "1 movie" else "${movies.size} movies",
            fontWeight = FontWeight.Bold
        )

        // --- Lab 6: Task 3: a picture of my own ---
        Image(
            painter = painterResource(id = R.drawable.header),
            contentDescription = "A bucket of popcorn",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // --- Class 7: Step 3: the text field ---
        OutlinedTextField(
            value = newMovie,
            // --- Class 8: Step 3: the field itself pushes back ---
            onValueChange = {
                newMovie = it.take(n = MAX_NAME_LENGTH)
                error = null
            },
            label = {Text("Movie Name")},
            singleLine = true,
            isError = error != null,
            modifier = Modifier.fillMaxWidth()
        )

        error?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                fontSize = 14.sp
            )
        }

        // --- Lab 7: Task 4: a live character counter ---
        Text(
            text = "${newMovie.length} / 40",
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        // --- Class 7: Step 4: the button that changes the state ---
        Button(onClick = {
            //--- Class 8: Step 3: check before you add  ---
            val problem = validateMovieName(input= newMovie, existingMovies = movies)
            if (problem == null) {
                // --- Class 9: Step 2: ask the owner to add it ---
                onAddMovie(newMovie.trim())
                newMovie = ""
            } else {
                error = problem
            }
        },
            // --- Class 8: Step 4: the sign on the door, not the lock ---
            enabled = newMovie.isNotBlank()
        ) {
            Text("Add movie")
        }

        Spacer(modifier = Modifier.height(8.dp))


        // --- Class 7: Step 3: draw whatever is in the list---
        Text(
            text = "${movies.size} movies",
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        // --- Class 9: Step 5: a way to the second screen ---
        Button(
            onClick = onSeeAll
        ) {
            Text(text = "See all movies")
        }


        // --- Lab 6: Task 2: footer
        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Last updated September 2026",
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )


    }
}

// --- Class 9: Step 3: the second screen ---
@Composable
fun ListScreen(
    movies: List<String>,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    //--- Class 9: Step 5: the phones back button goes home too ---
    BackHandler() {onBack }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(24.dp)
    ) {
        TextButton(onClick = onBack) {
            Text(text = "Back")
        }

        Text(
            text = "All Movies",
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        for (movie in movies) {
            Text(text = movie, fontSize = 18.sp)
        }
    }

}

const val MAX_NAME_LENGTH = 40

// --- Class 8: Step 1: one rule book for movie names ---
fun validateMovieName(input: String, existingMovies: List<String>): String? {
    val name = input.trim()
    return when {
        name.isEmpty() -> "Enter a movie name"
        // --- Lab 8: Task 1: minimum length ---
        name.length < 3 -> "Too short -- at least 3 characters"
        name.length > MAX_NAME_LENGTH -> "Keep it to $MAX_NAME_LENGTH characters or fewer"
        existingMovies.any { it.equals(other = name, ignoreCase = true)} -> "$name is already on the list"
        // --- Lab 8: Task 2: my own rule ---
        name.any {it in "<>"} -> "No < or > please"
        else -> null
    }

}

@Preview
@Composable
fun HomeScreenPreview() {
    CampusAppTheme {
        HomeScreen(
            movies = remember {
                mutableStateListOf("")
            },
            onAddMovie = {},
            onSeeAll = {}
        )
    }
}

// --- Lab 6: Task 4: dark more preview
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HomeScreenDarkPreview() {
    CampusAppTheme {
        Surface() {
            HomeScreen(
                movies = remember {
                    mutableStateListOf(
                        "Cinderella Man",
                        "Die Hard",
                        "2001: A Space Odyssey",
                        "Revenge of the Sith")
                },
                onAddMovie = {},
                onSeeAll = {}
            )
        }
    }
}

@Preview
@Composable
fun ListScreenPreview() {
    CampusAppTheme{
        ListScreen(
            movies = remember {
                mutableStateListOf(
                    "Cinderella Man",
                    "Die Hard",
                    "2001: A Space Odyssey",
                    "Revenge of the Sith")
            },
            onBack = {}
        )
    }
}