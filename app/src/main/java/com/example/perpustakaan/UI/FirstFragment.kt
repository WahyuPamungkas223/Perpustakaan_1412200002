package com.example.perpustakaan.UI

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.perpustakaan.R
import com.example.perpustakaan.application.BookApp
import com.example.perpustakaan.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    private lateinit var applicationContext: Context
    private val BookViewModel: BookViewModel by viewModels{
        BookViewModelFactory((applicationContext as BookApp).repository)
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        applicationContext = requireContext().applicationContext
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = BookListAdapter {book ->
            val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(book)
            findNavController().navigate(action)
        }
        binding.datarecyclerView.adapter = adapter
        binding.datarecyclerView.layoutManager = LinearLayoutManager(context)
        BookViewModel.allBook.observe(viewLifecycleOwner) {books ->
            books.let {
                if (books.isEmpty()) {
                    binding.EmptytextView.visibility = View.VISIBLE
                    binding.imageView.visibility = View.VISIBLE
                } else {
                    binding.EmptytextView.visibility = View.GONE
                    binding.imageView.visibility = View.GONE
                }
                adapter.submitList(books)
            }
        }

        binding.CatButton.setOnClickListener {
            val action = FirstFragmentDirections.actionFirstFragmentToThirdFragment()
            findNavController().navigate(action)
        }

        binding.addFAB.setOnClickListener {
           val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(null)
           findNavController().navigate(action)
       }

       binding.MemberButton.setOnClickListener {
           findNavController().navigate(R.id.action_FirstFragment_to_FourthFragment)
       }

        binding.ProgrammerButton.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_fifthFragment)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}