package com.yabasoft.henripotier.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yabasoft.henripotier.data.entities.Book
import com.yabasoft.henripotier.databinding.ItemCartBinding
import com.yabasoft.henripotier.ui.books.BookDiffCallback

/**
 * Created by Fayssel Yabahddou on 4/15/22.
 */
class CartAdapter(
    private val deleteFromCart: (Book) -> Unit,
) : ListAdapter<Book, CartAdapter.ViewHolder>(BookDiffCallback()) {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): CartAdapter.ViewHolder {
        val from = LayoutInflater.from(viewGroup.context)
        val binding = ItemCartBinding.inflate(from, viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: CartAdapter.ViewHolder, position: Int) {
        val book = getItem(position)
        viewHolder.bind(book = book)
    }

    inner class ViewHolder(private val binding: ItemCartBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(book: Book) {
            binding.book = book
            binding.deleteBook.setOnClickListener {
                deleteFromCart(book)
            }
            binding.executePendingBindings()
        }
    }

}