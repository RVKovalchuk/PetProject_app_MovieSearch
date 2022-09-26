package com.example.moviesearch.view.fragments

import android.Manifest
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.domain.ApiConstants
import com.example.domain.Film
import com.example.moviesearch.R
import com.example.moviesearch.data.entity.FilmConstants
import com.example.moviesearch.viewmodel.DetailsFragmentViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.*

class DetailsFragment : Fragment() {
    private lateinit var film: Film

    private val viewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(DetailsFragmentViewModel::class.java)
    }

    private val scope = CoroutineScope(Dispatchers.IO)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setDetails()
        pressDownload()
    }

    private fun checkPermission(): Boolean {
        val result = ContextCompat.checkSelfPermission(
            requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        return result == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(), arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1
        )
    }

    private fun saveToGallery(bitmap: Bitmap) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val contentValues = ContentValues().apply {
                put(MediaStore.Images.Media.TITLE, film.title?.handleSingleQuote())
                put(
                    MediaStore.Images.Media.DISPLAY_NAME,
                    film.title?.handleSingleQuote()
                )
                put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                put(
                    MediaStore.Images.Media.DATE_ADDED,
                    System.currentTimeMillis() / 1000
                )
                put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis())
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/FilmsSearchApp")
            }
            val contentResolver = requireActivity().contentResolver
            val uri = contentResolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues
            )
            val outputSteam = contentResolver.openOutputStream(uri!!)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputSteam)
            outputSteam?.close()
        } else {
            @Suppress("DEPRECATION")
            MediaStore.Images.Media.insertImage(
                requireActivity().contentResolver,
                bitmap,
                film.title?.handleSingleQuote(),
                film.description?.handleSingleQuote()
            )
        }
    }

    private fun String.handleSingleQuote(): String {
        return this.replace("'", "")
    }

    private fun performAsyncLoadOfPoster() {
        if (!checkPermission()) {
            requestPermission()
            return
        }
        MainScope().launch {
            val progressBarOfDownloading =
                view?.findViewById<ProgressBar>(R.id.fragment_details_progress_bar)
            progressBarOfDownloading?.isVisible = true

            val job = scope.async {
                viewModel.loadPoster(ApiConstants.IMAGE_URL + "original" + film.poster)
            }

            saveToGallery(job.await())

            progressBarOfDownloading?.isVisible = false
        }
    }

    private fun pressDownload(){
       val downloadButton =  view?.findViewById<FloatingActionButton>(R.id.fragment_details_fab_load_poster)
        downloadButton?.setOnClickListener {
            performAsyncLoadOfPoster()
        }
    }

    private fun setDetails() {
        film = arguments?.get(FilmConstants.FILM_BUNDLE_KEY) as Film
        val fragmentDetailsButtonFavorites =
            view?.findViewById<FloatingActionButton>(R.id.fragment_details_fab_favorites)
        val fragmentDetailsButtonShare =
            view?.findViewById<FloatingActionButton>(R.id.fragment_details_fab_share)
        val imageView = view?.findViewById<AppCompatImageView>(R.id.activity_details_poster)
        val toolbar = view?.findViewById<Toolbar>(R.id.activity_details_toolbar)
        val textView = view?.findViewById<TextView>(R.id.activity_details_textview)
        toolbar?.title = film.title

        if (imageView != null) {
            Glide.with(this)
                .load(ApiConstants.IMAGE_URL + "w780" + film.poster)
                .centerCrop()
                .into(imageView)
        }

        textView?.text = film.description

        fragmentDetailsButtonFavorites?.setImageResource(
            if (film.isInFavorites) R.drawable.ic_fragment_details_favorite_yes
            else R.drawable.ic_fragment_details_favorite_no
        )

        fragmentDetailsButtonFavorites?.setOnClickListener {
            if (!film.isInFavorites) {
                fragmentDetailsButtonFavorites.setImageResource(R.drawable.ic_fragment_details_favorite_yes)
                film.isInFavorites = true
            } else {
                fragmentDetailsButtonFavorites.setImageResource(R.drawable.ic_fragment_details_favorite_no)
                film.isInFavorites = false
            }
        }

        fragmentDetailsButtonShare?.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.putExtra(
                Intent.EXTRA_TEXT,
                "Делюсь описанием фильма: \n${film.title} \n${film.description}"
            )
            intent.type = "text/plain"
            startActivity(Intent.createChooser(intent, "Поделиться с: "))
        }
    }

}