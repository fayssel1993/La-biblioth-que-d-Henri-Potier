package com.yabasoft.henripotier.ui.cart

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.yabasoft.henripotier.R
import com.yabasoft.henripotier.databinding.CartFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class CartFragment : Fragment(R.layout.cart_fragment) {

    private val viewModel: CartViewModel by viewModel()

    private lateinit var binding: CartFragmentBinding

    private lateinit var adapter: CartAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = CartFragmentBinding.bind(view)
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
            }
        }

    }

}