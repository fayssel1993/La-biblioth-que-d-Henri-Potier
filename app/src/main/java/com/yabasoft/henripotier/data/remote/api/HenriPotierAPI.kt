package com.yabasoft.henripotier.api

import com.yabasoft.henripotier.data.entities.Book
import com.yabasoft.henripotier.data.entities.CommercialOffers
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by Fayssel Yabahddou on 4/14/22.
 */
interface HenriPotierAPI {
    @GET("books")
    fun getAllBooks(): Single<ArrayList<Book>>

    @GET("books/{ids}/commercialOffers")
    fun getCommercialOffers(@Path("ids") ids: String): Single<CommercialOffers>
}