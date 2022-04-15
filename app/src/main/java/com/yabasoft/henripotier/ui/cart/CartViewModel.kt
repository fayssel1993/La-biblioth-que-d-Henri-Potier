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

    private val _totalPriceAfterDiscount: MutableLiveData<Int> = MutableLiveData(0)
    val totalPriceAfterDiscount: LiveData<Int>
        get() = _totalPriceAfterDiscount


    init {
        getBooksFromCart()
    }

    // Updated after deadline
    fun getCommercialOffers(ids: String) = viewModelScope.launch {
        repository.getCommercialOffers(ids)
            .onStart { emit(NetworkResult.Loading()) }
            .collect { value -> _commercialOffersResponse.value = value }
    }

    private fun getBooksFromCart() = viewModelScope.launch {
        repository.getAllBooksInCart()
            .collect { value ->
                _cartBooks.value = value
            }
    }

    // not used after deadline
    @Deprecated("not used after deadline")
    fun getIsbn() = viewModelScope.launch {
    }

    fun getBestOffer() {
        kotlin.run {
            _totalPrice.value = _cartBooks.value?.let { Utils.calculateTotal(it) }
            _discount.value = _commercialOffersResponse.value?.data?.offers?.let {
                _totalPrice.value?.let { it1 ->
                    Utils.calculateMaxReduction(
                        it1,
                        it
                    )
                }
            }
            _totalPriceAfterDiscount.value =
                _totalPrice.value?.let {
                    _discount.value?.let { it1 ->
                        Utils.calculateTotalAfterDiscount(
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

    // Updated after deadline
    fun getIds(books: List<Book>): String {
        var tempString = ""
        books.map {
            tempString += ",${it.isbn}"
        }

        return tempString
    }

    //Added after deadline
    fun setTotalPrice(totalPrice: Int) {
        _totalPrice.value = totalPrice
    }

    //Added after deadline
    fun setDiscount(reduction: Int) {
        _discount.value = reduction
    }

    //Added after deadline
    fun setPriceAfterDiscount(priceAfterDiscount: Int) {
        _totalPriceAfterDiscount.value = priceAfterDiscount
    }

}