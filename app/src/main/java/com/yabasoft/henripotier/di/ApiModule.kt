package com.yabasoft.henripotier.di

import com.yabasoft.henripotier.data.remote.api.HenriPotierAPI
import org.koin.dsl.module.module
import retrofit2.Retrofit

/**
 * Created by Fayssel Yabahddou on 4/14/22.
 */
val apiModule = module {
    single(createOnStart = false) { get<Retrofit>().create(HenriPotierAPI::class.java) }
}