package com.yabasoft.henripotier.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yabasoft.henripotier.data.entities.Book
import com.yabasoft.henripotier.data.entities.CommercialOffers
import com.yabasoft.henripotier.data.repositories.BooksRepository
import com.yabasoft.henripotier.utils.NetworkResult
import com.yabasoft.henripotier.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class CartViewModel(
    private val repository: BooksRepository
) : ViewModel() {

    private val _commercialOffersResponse = MutableLiveData<NetworkResult<CommercialOffers>>()
    val commercialOffersResponse: LiveData<NetworkResult<CommercialOffers>> get() = _commercialOffersResponse

    private val _cartBooks = MutableLiveData<List<Book>>()
    val cartBooks: LiveData<List<Book>> get() = _cartBooks

    private val _totalPrice: MutableLiveData<Int> = MutableLiveData(0)
    val totalPrice: LiveData<Int>
        get() = _totalPrice

    private val _discount: MutableLiveData<Int> = MutableLiveData(0)
    val discount: LiveData<Int>
        get() = _discount

    private val _totalPriceDiscount: MutableLiveData<Int> = MutableLiveData(0)
    val totalPriceDiscount: LiveData<Int>
        get() = _totalPriceDiscount


    var isbnList: List<String> = listOf()

    init {
        getBooksFromCart()
    }

    private fun getCommercialOffers() = viewModelScope.launch {
        repository.getCommercialOffers(getIds())
            .onStart { emit(NetworkResult.Loading()) }
            .collect { value -> _commercialOffersResponse.value = value }
    }

    private fun getBooksFromCart() = viewModelScope.launch {
        repository.getAllBooksInCart()
            .collect { value -> _cartBooks.value = value }
    }

    fun getIsbn() = viewModelScope.launch {
        isbnList = repository.findAllIsbnCode()
    }

    fun getBestOffer() {
        kotlin.run {
            _totalPrice.value = _cartBooks.value?.let { Utils.calculateTotal(it) }
            _discount.value = _commercialOffersResponse.value?.data?.offers?.let {
                _totalPrice.value?.let { it1 ->
                    Utils.calculateMaxOffer(
                        it1,
                        it
                    )
                }
            }
            _totalPriceDiscount.value =
                _totalPrice.value?.let {
                    _discount.value?.let { it1 ->
                        Utils.calculateTotalDiscount(
                            it,
                            it1
                        )
                    }
                }
        }
    }

    fun deleteFromCart(book: Book) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(book)
    }

    fun getIds(): String {
        var tempString = ""
        for (id in isbnList) tempString += ",$id"
        return tempString
    }
}