package com.example.perpustakaan.UI

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.perpustakaan.application.BookApp
import com.example.perpustakaan.databinding.FragmentSecondBinding
import com.example.perpustakaan.model.Book

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!
    private lateinit var applicationContext: Context
    private val BookViewModel: BookViewModel by viewModels{
        BookViewModelFactory((applicationContext as BookApp).repository)
    }

    private val args : SecondFragmentArgs by navArgs()
    private var book: Book? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        applicationContext = requireContext().applicationContext
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        book = args.book
        if (book != null){
            binding.deleteButton.visibility = View.VISIBLE
            binding.saveButton.text = "Ubah"
            binding.booknameeditText.setText(book?.book_name)
            binding.genreeditText.setText(book?.book_genre)
            binding.borrowereditText.setText(book?.borrower_name)
        }
        val book_name = binding.booknameeditText.text
        val book_genre = binding.genreeditText.text
        val borrower_name = binding.borrowereditText.text
        binding.saveButton.setOnClickListener {
            if (book_name.isEmpty()){
                Toast.makeText(context, "Judul Buku Tidak Ditemukan!", Toast.LENGTH_SHORT).show()
            } else if (book_genre.isEmpty()){
            Toast.makeText(context, "Genre Tidak Ditemukan!", Toast.LENGTH_SHORT).show()
            } else if (borrower_name.isEmpty()){
            Toast.makeText(context, "Nama Peminjam Tidak Ditemukan!", Toast.LENGTH_SHORT).show()
            } else{
                if (book == null){
                    val book = Book(0, book_name.toString(), book_genre.toString(), borrower_name.toString())
                    BookViewModel.insert(book)
                }else {
                    val book = Book(book?.id!!, book_name.toString(), book_genre.toString(), borrower_name.toString())
                    BookViewModel.update(book)
                }
                findNavController().popBackStack()
            }
              //Untuk Dismiss Halaman ini
        }

        binding.deleteButton.setOnClickListener {
            book?.let { it1 -> BookViewModel.delete(it1) }
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}