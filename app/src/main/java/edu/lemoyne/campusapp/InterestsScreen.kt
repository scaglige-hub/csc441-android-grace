package edu.lemoyne.campusapp

//Step 4: Compose Screen
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun InterestsScreen(
    state: ShowcaseUIState,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    BackHandler {onBack()}
    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
        TextButton(onClick = onBack) {Text("Back")}
        when (state) {
            ShowcaseUIState.Loading -> Text("Loading from Supabase...")
            is ShowcaseUIState.Error -> Column {
                Text("Could not load: " + state.message)
            }
            is ShowcaseUIState.Success -> LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(state.items) { item ->
                    Column(Modifier.padding(8.dp)) {
                        item.title?.let {Text(it)}
                        item.category?.let {Text(it)}
                        item.description?.let {Text(it)}
                    }
                }
            }
        }
    }
}