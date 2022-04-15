package com.yabasoft.henripotier.di

import com.yabasoft.henripotier.ui.books.BooksViewModel
import com.yabasoft.henripotier.ui.cart.CartViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

/**
 * Created by Fayssel Yabahddou on 4/14/22.
 */

val viewModelModule = module {
    viewModel { BooksViewModel(get()) }
    viewModel { CartViewModel(get()) }
}