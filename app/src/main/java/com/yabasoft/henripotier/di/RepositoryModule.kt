package com.yabasoft.henripotier.di

import com.yabasoft.henripotier.data.repositories.BooksRepository
import org.koin.dsl.module.module

/**
 * Created by Fayssel Yabahddou on 4/14/22.
 */
val repositoryModule = module {
    single { BooksRepository(get(), get()) }
}