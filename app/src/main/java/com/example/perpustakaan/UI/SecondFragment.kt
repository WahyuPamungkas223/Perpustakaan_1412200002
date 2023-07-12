package com.example.perpustakaan.UI

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.perpustakaan.R
import com.example.perpustakaan.application.BookApp
import com.example.perpustakaan.databinding.FragmentSecondBinding
import com.example.perpustakaan.model.Book
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerDragListener {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!
    private lateinit var applicationContext: Context
    private val BookViewModel: BookViewModel by viewModels{
        BookViewModelFactory((applicationContext as BookApp).repository)
    }

    private val args : SecondFragmentArgs by navArgs()
    private var book: Book? = null
    private lateinit var mMap: GoogleMap
    private var currentLatLang: LatLng? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient

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

        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        checkPermission()

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
                    val book = Book(0, book_name.toString(), book_genre.toString(), borrower_name.toString(), currentLatLang?.latitude,currentLatLang?.longitude)
                    BookViewModel.insert(book)
                }else {
                    val book = Book(book?.id!!, book_name.toString(), book_genre.toString(), borrower_name.toString(), currentLatLang?.latitude,currentLatLang?.longitude)
                    BookViewModel.update(book)
                }
                findNavController().popBackStack()
            }

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

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setOnMarkerDragListener(this)

        val uiSettings = mMap.uiSettings
        uiSettings.isZoomControlsEnabled = true
    }

    override fun onMarkerDrag(p0: Marker) {}

    override fun onMarkerDragEnd(marker: Marker) {
        val newPosition = marker.position
        currentLatLang = LatLng(newPosition.latitude, newPosition.longitude)
        Toast.makeText(context, currentLatLang.toString(), Toast.LENGTH_SHORT).show()
    }

    override fun onMarkerDragStart(p0: Marker) {
    }

    private fun checkPermission() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(applicationContext)
        if (ContextCompat.checkSelfPermission(
            applicationContext,
            android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            getCurrentLocation()
        } else {
            Toast.makeText(applicationContext, "Akses Lokasi Ditolak", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getCurrentLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(applicationContext)
        if (ContextCompat.checkSelfPermission(
                applicationContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        fusedLocationClient.lastLocation
            .addOnSuccessListener {location ->
                if (location != null) {
                    var LatLang = LatLng(location.latitude, location.longitude)
                    currentLatLang = LatLang
                    var title = "Marker"

                    if (book != null) {
                        title = book?.book_name.toString()
                        val newCurrentLocation = LatLng(book?.latitude!!, book?.longitude!!)
                        LatLang = newCurrentLocation
                    }

                    val markerOptions = MarkerOptions()
                        .position(LatLang)
                        .title(title)
                        .draggable(true)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_book32))
                    mMap.addMarker(markerOptions)
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLang,15f))

                }
            }
    }
}