package com.yabasoft.henripotier.data.remote.api

import com.yabasoft.henripotier.data.entities.Book
import com.yabasoft.henripotier.data.entities.CommercialOffers
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by Fayssel Yabahddou on 4/14/22.
 */
interface HenriPotierAPI {

    @GET("books")
    suspend fun getAllBooks(): Response<List<Book>>

    @GET("books/{ids}/commercialOffers")
    suspend fun getCommercialOffers(@Path("ids") ids: String): Response<CommercialOffers>
}