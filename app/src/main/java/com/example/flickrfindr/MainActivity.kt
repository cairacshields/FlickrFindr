package com.example.flickrfindr

import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flickrfindr.view.FlickrItemAdapter
import com.example.flickrfindr.viewmodel.FlickrItemViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel
import android.content.Intent
import android.widget.SearchView
import com.example.flickrfindr.view.FullScreen
import android.provider.SearchRecentSuggestions
import android.view.Menu
import com.example.flickrfindr.utils.SuggestionProvider
import android.view.MenuItem
import com.example.flickrfindr.utils.LoadingState
import com.example.flickrfindr.view.Bookmarks


class MainActivity : AppCompatActivity() {

    private val flickrItemViewModel by viewModel<FlickrItemViewModel>()
    lateinit var adapter: FlickrItemAdapter
    lateinit var searchManager: SearchManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler.layoutManager = LinearLayoutManager(this)
        adapter = FlickrItemAdapter(this)
        adapter.setClickListener(::onItemClick)
        recycler.adapter = adapter

        searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        search_query.apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
        }

        flickrItemViewModel.data.observe(this, Observer {
            adapter.setData(it)
        })

        flickrItemViewModel.preferredData.observe(this, Observer {
            adapter.setData(it)
        })

        flickrItemViewModel.currentSearchAmount.observe(this, Observer {
            flickrItemViewModel.getPreferredItems(flickrItemViewModel.currentlySearchingWord)
            result_text.text = "Displaying ${it} results"
        })

        flickrItemViewModel.loadingState.observe(this, Observer {
            when(it.status.name) {
                LoadingState.LOADED.status.name -> {
                    recycler.visibility = View.VISIBLE
                    loading.visibility = View.GONE
                }
                LoadingState.LOADING.status.name -> {
                    recycler.visibility = View.GONE
                    loading.visibility = View.VISIBLE
                }
            }
        })

        search_query.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(text: String?): Boolean {
               text?.let {
                   search_query.setQuery("", false)
                   search_query.isIconified = true
                   val suggestions = SearchRecentSuggestions(
                       search_query.context,
                       SuggestionProvider.AUTHORITY, SuggestionProvider.MODE
                   )
                   flickrItemViewModel.currentlySearchingWord = it
                   suggestions.saveRecentQuery(it, null)
                   flickrItemViewModel.getPreferredItems(it)
               }
                return true
            }

            override fun onQueryTextChange(text: String?): Boolean {
                return true
            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true;
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_clear_history -> {
                val suggestions = SearchRecentSuggestions(
                    this,
                    SuggestionProvider.AUTHORITY, SuggestionProvider.MODE
                )
                suggestions.clearHistory()
                return true
            }
            R.id.view_bookmarks -> {
                val intent = Intent(this, Bookmarks::class.java)
                startActivity(intent)
            }
            R.id.menuSortDefault -> {
                flickrItemViewModel.currentSearchAmount.postValue(25)
                return true
            }
            R.id.menuSort50 -> {
                flickrItemViewModel.currentSearchAmount.postValue(50)
                return true
            }
            R.id.menuSort100 -> {
                flickrItemViewModel.currentSearchAmount.postValue(100)
                return true
            }

        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNewIntent(intent: Intent?) {
        setIntent(intent)
        handleIntent(intent)
        super.onNewIntent(intent)
    }

    private fun handleIntent(intent: Intent?) {
        // Verify the action and get the query
        if (Intent.ACTION_SEARCH == intent?.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                search_query.setQuery(query, true)
            }
        }
    }

    private fun onItemClick(view: View, position: Int) {
        val flickrItem = adapter.getItem(position)
        val intent = Intent(this, FullScreen::class.java)
        intent.putExtra("imageId", flickrItem.id)
        intent.putExtra("imageUrl",
            "https://farm${flickrItem.farm}.staticflickr.com/${flickrItem.server}/${flickrItem.id}_${flickrItem.secret}.jpg")
        intent.putExtra("imageTitle", flickrItem.title)
        startActivity(intent)
    }
}
