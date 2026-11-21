package edu.lemoyne.campusapp

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ShowcaseRepository(private val api: ShowcaseAPI = ShowcaseService.api) {

    suspend fun loadItems(token: String): List<ShowcaseItem> =
        withContext(Dispatchers.IO) {
            api.getShowcaseItems(ShowcaseService.API_KEY, "Bearer $token")
        }

    suspend fun signIn(email: String, password: String): String =
        withContext(Dispatchers.IO) {
            api.signIn(
                ShowcaseService.API_KEY,
                LoginRequest(email, password)
            ).accessToken
        }
}