package edu.lemoyne.campusapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class ShowcaseUIState {
    data object Loading: ShowcaseUIState()
    data class Success(val items: List<ShowcaseItem>): ShowcaseUIState()
    data class Error(val message: String): ShowcaseUIState()
}

class ShowcaseViewModel: ViewModel() {
    private val repository = ShowcaseRepository()

    private val _uiState = MutableStateFlow<ShowcaseUIState>(ShowcaseUIState.Loading)
    val uiState: StateFlow<ShowcaseUIState> = _uiState
    private var accessToken: String? = null

    fun setAccessToken(token: String) {
        accessToken = token
    }

    fun refresh() {
        val token = accessToken?: run {
            _uiState.value = ShowcaseUIState.Error("Please log in first")
            return@run
        }
        viewModelScope.launch {
            _uiState.value = ShowcaseUIState.Loading
            try {
                val items = repository.loadItems()
                _uiState.value = ShowcaseUIState.Success(items)

            } catch (error: Exception) {
                _uiState.value = ShowcaseUIState.Error(error.message ?: "Network Error")
            }
        }
    }
}