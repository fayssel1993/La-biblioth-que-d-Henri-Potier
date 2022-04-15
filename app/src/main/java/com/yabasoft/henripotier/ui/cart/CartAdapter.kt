package com.yabasoft.henripotier.ui.books

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yabasoft.henripotier.data.entities.Book
import com.yabasoft.henripotier.databinding.ItemBookBinding

/**
 * Created by Fayssel Yabahddou on 4/15/22.
 */
class BookAdapter(
    private val addToCart: (Book) -> Unit,
) : ListAdapter<Book, BookAdapter.ViewHolder>(BookDiffCallback()) {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): BookAdapter.ViewHolder {
        val from = LayoutInflater.from(viewGroup.context)
        val binding = ItemBookBinding.inflate(from, viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: BookAdapter.ViewHolder, position: Int) {
        val book = getItem(position)
        viewHolder.bind(book = book)
    }

    inner class ViewHolder(private val binding: ItemBookBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(book: Book) {
            binding.book = book
            binding.buttonAddToCart.setOnClickListener {
                addToCart
            }
            binding.executePendingBindings()
        }
    }

}

class BookDiffCallback : DiffUtil.ItemCallback<Book>() {
    override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
        return oldItem == newItem
    }
}