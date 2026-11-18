package edu.lemoyne.campusapp

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header

interface ShowcaseAPI {
    @GET("/rest/v1/showcase_items")
    suspend fun getShowcaseItems(
        @Header("apikey") apiKey: String
    ): List<ShowcaseItem>
}

object ShowcaseService {
    const val API_KEY = "sb_publishable_HU-4RJc4hsbTjKCn-yRA-Q_F0_cPpWX"
    const val BASE_URL = "https://wwrzfgafaosbnxgvxxxs.supabase.co"

    val api: ShowcaseAPI = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ShowcaseAPI::class.java)
}