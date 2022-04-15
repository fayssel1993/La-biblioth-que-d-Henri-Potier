package com.yabasoft.henripotier.ui.books

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.yabasoft.henripotier.R
import com.yabasoft.henripotier.data.entities.Book
import com.yabasoft.henripotier.databinding.BooksFragmentBinding
import com.yabasoft.henripotier.utils.NetworkResult
import org.koin.androidx.viewmodel.ext.android.viewModel

class BooksFragment : Fragment(R.layout.books_fragment) {

    private val viewModel: BooksViewModel by viewModel()

    private lateinit var binding: BooksFragmentBinding

    private lateinit var adapter: BookAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = BooksFragmentBinding.bind(view)
        adapter = BookAdapter {
            viewModel.addBookToCart(it)
        }
        binding.booksList.adapter = adapter

        viewModel.booksResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> onSuccess(response.data)
                is NetworkResult.Loading -> loading(true)
                is NetworkResult.Error -> onError(response.message)
            }
        }
    }

    private fun loading(show: Boolean) {
        binding.progressBar.isVisible = show
    }

    private fun onSuccess(data: List<Book>?) {
        loading(false)
        adapter.submitList(data)
    }

    private fun onError(error: String?) {
        loading(false)
        Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
    }

}