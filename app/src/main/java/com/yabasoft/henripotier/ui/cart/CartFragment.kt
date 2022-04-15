package com.yabasoft.henripotier.ui.cart

import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.yabasoft.henripotier.R
import com.yabasoft.henripotier.data.entities.Book
import com.yabasoft.henripotier.data.entities.Offer
import com.yabasoft.henripotier.databinding.CartFragmentBinding
import com.yabasoft.henripotier.utils.NetworkResult
import com.yabasoft.henripotier.utils.Utils
import org.koin.androidx.viewmodel.ext.android.viewModel

class CartFragment : Fragment(R.layout.cart_fragment) {

    private val viewModel: CartViewModel by viewModel()

    private lateinit var binding: CartFragmentBinding

    private lateinit var adapter: CartAdapter

    private var totalPrice = -1
    private var discount = -1

    //Added after deadline
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = CartFragmentBinding.bind(view)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        adapter = CartAdapter {
            viewModel.deleteFromCart(it)
        }

        binding.cartList.adapter = adapter

        viewModel.cartBooks.observe(viewLifecycleOwner) {
            if (it.isNullOrEmpty()) {
                binding.listEmpty.isVisible = true
                binding.listItem.isVisible = false
            } else {
                binding.listEmpty.isVisible = false
                binding.listItem.isVisible = true
                adapter.submitList(it)
                getCommercialOffer(it)
                getTotalPrice(it)
            }
        }

        viewModel.commercialOffersResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Error -> Unit
                is NetworkResult.Loading -> Unit
                is NetworkResult.Success -> getDiscount(response.data?.offers)
            }
        }

    }

    //Added after deadline
    private fun getCommercialOffer(books: List<Book>) {
        viewModel.getCommercialOffers(viewModel.getIds(books))
    }

    //Added after deadline
    private fun getTotalPrice(books: List<Book>) {
        val totalPrice = Utils.calculateTotal(books)
        viewModel.setTotalPrice(totalPrice)
        this.totalPrice = totalPrice
    }

    //Added after deadline
    private fun getDiscount(offers: List<Offer>?) {
        val reduction = Utils.calculateMaxReduction(totalPrice, offers)
        viewModel.setDiscount(reduction)
        this.discount = reduction
        getTotalPriceAfterDiscount()
    }

    //Added after deadline
    private fun getTotalPriceAfterDiscount() {
        viewModel.setPriceAfterDiscount(Utils.calculateTotalAfterDiscount(totalPrice, discount))
    }

    //Added after deadline
    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.clear()
    }

}