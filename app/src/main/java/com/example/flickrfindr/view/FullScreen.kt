package com.example.flickrfindr.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import com.example.flickrfindr.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.flickrfindr.service.model.BookMark
import com.example.flickrfindr.viewmodel.FlickrItemViewModel
import kotlinx.android.synthetic.main.activity_full_screen.*
import org.koin.android.viewmodel.ext.android.viewModel


class FullScreen : AppCompatActivity() {

    private val flickrItemViewModel by viewModel<FlickrItemViewModel>()
    lateinit var imageId: String
    lateinit var imageUrl: String
    lateinit var imageTitle: String
    lateinit var bookMark: BookMark

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_screen)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val intent = intent
        intent.extras?.let {bundle ->
            bundle.getString("imageId")?.let {
                imageId = it
            }
            bundle.getString("imageUrl")?.let {
                imageUrl = it
            }
            bundle.getString("imageTitle")?.let {
                imageTitle = it
                supportActionBar?.title = it
            }
        }
        loadImageIntoView()
    }

    private fun loadImageIntoView() {
        Glide.with(big_image.context)
            .load(imageUrl)
            .apply(RequestOptions().override(600, 300))
            .into(big_image)
        big_image.scaleType = (ImageView.ScaleType.FIT_XY)

        bookMark = BookMark(imageId, imageTitle, imageUrl)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.single_image_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
             android.R.id.home -> {
                 this.finish()
                 return true
             }
            R.id.bookmark -> {
                flickrItemViewModel.addBookMark(bookMark)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
