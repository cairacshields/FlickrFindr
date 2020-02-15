package com.example.flickrfindr.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flickrfindr.R
import com.example.flickrfindr.viewmodel.FlickrItemViewModel
import kotlinx.android.synthetic.main.activity_bookmarks.*
import org.koin.android.viewmodel.ext.android.viewModel

class Bookmarks : AppCompatActivity() {

    private val flickrItemViewModel by viewModel<FlickrItemViewModel>()
    lateinit var adapter: BookmarksAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bookmarks)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        recycler_bookmarks.layoutManager = LinearLayoutManager(this)
        adapter = BookmarksAdapter(this)
        adapter.setClickListener(::onItemClick)
        recycler_bookmarks.adapter = adapter

        flickrItemViewModel.bookmarks.observe(this, Observer {
            adapter.setData(it)
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                this.finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun onItemClick(position: Int) {
        val bookmark = adapter.getItem(position)
        val intent = Intent(this, FullScreen::class.java)
        intent.putExtra("imageId", bookmark.id)
        intent.putExtra("imageUrl", bookmark.url)
        intent.putExtra("imageTitle", bookmark.title)
        startActivity(intent)
    }
}
