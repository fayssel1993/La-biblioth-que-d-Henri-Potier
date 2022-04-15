package com.yabasoft.henripotier.ui.books

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yabasoft.henripotier.data.entities.Book
import com.yabasoft.henripotier.data.entities.CommercialOffers
import com.yabasoft.henripotier.data.repositories.BooksRepository
import com.yabasoft.henripotier.utils.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class BooksViewModel(
    private val repository: BooksRepository
) : ViewModel() {

    private val _booksResponse = MutableLiveData<NetworkResult<List<Book>>>()
    val booksResponse: LiveData<NetworkResult<List<Book>>> get() = _booksResponse

    private val _commercialOffersResponse = MutableLiveData<NetworkResult<CommercialOffers>>()
    val commercialOffersResponse: LiveData<NetworkResult<CommercialOffers>> get() = _commercialOffersResponse

    init {
        getBooks()
    }

    private fun getBooks() = viewModelScope.launch {
        repository.getAllBooks()
            .onStart { emit(NetworkResult.Loading()) }
            .collect { value -> _booksResponse.value = value }
    }

    fun addBookToCart(book: Book) = viewModelScope.launch(Dispatchers.IO) {
        repository.save(book)
    }

}