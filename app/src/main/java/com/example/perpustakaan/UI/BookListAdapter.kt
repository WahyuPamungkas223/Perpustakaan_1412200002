package com.example.perpustakaan.UI

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.perpustakaan.R
import com.example.perpustakaan.model.Book

class BookListAdapter(
    private val OnItemClickListener: (Book) -> Unit
): ListAdapter<Book, BookListAdapter.BookViewHolder>(WORDS_COMPARATOR) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        return BookViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = getItem(position)
        holder.bind(book)
        holder.itemView.setOnClickListener {
            OnItemClickListener(book)
        }
    }


    class BookViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val booktextView: TextView = itemView.findViewById(R.id.booktextView)
        private val genretextView: TextView = itemView.findViewById(R.id.genretextView)
        private val borrowertextView: TextView = itemView.findViewById(R.id.borrowertextView)

        fun bind(book: Book?) {
            booktextView.text = book?.book_name
            genretextView.text = book?.book_genre
            borrowertextView.text = book?.borrower_name
        }

        companion object {
            fun create(parent: ViewGroup): BookListAdapter.BookViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_book, parent, false)
                return BookViewHolder(view)

            }
        }

    }

    companion object {
        private val WORDS_COMPARATOR = object : DiffUtil.ItemCallback<Book>() {
            override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}