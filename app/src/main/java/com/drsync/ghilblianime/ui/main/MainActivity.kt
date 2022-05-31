package com.drsync.ghilblianime.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.drsync.ghilblianime.R
import com.drsync.ghilblianime.databinding.ActivityMainBinding
import com.drsync.ghilblianime.models.ServerResponse
import com.drsync.ghilblianime.repository.Repository
import com.drsync.ghilblianime.ui.detail.DetailActivity
import com.drsync.ghilblianime.ui.profile.ProfileActivity
import com.drsync.ghilblianime.util.Constants.DATA_TAG
import com.drsync.ghilblianime.util.Constants.POSITION_TAG
import com.drsync.ghilblianime.util.Constants.TAG
import com.drsync.ghilblianime.util.Resource

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var listMainAdapter: ListMainAdapter
    private lateinit var gridMainAdapter: GridMainAdapter
    private lateinit var cardLayoutAdapter: CardLayoutAdapter

    private var list: List<ServerResponse?> = listOf()
    private var title: String = "Mode List"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setActionBarTitle(title)

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        getDataFilm()
        setListRecycler()
    }

    private fun setCardRecycler() {
        binding.rvFilm.apply {
            cardLayoutAdapter = CardLayoutAdapter()
            cardLayoutAdapter.submitList(list)

            adapter = cardLayoutAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)

            cardLayoutAdapter.setOnItemClickCallback(object : CardLayoutAdapter.OnItemClickCallback {
                override fun onItemClicked(data: ServerResponse, position: Int) {
                    intentToDetail(data, position)
                }
            })
        }
    }

    private fun setGridRecycler() {
        binding.rvFilm.apply {
            gridMainAdapter = GridMainAdapter()
            gridMainAdapter.submitList(list)

            adapter = gridMainAdapter
            layoutManager = GridLayoutManager(this@MainActivity, 2)
            setHasFixedSize(true)

            gridMainAdapter.setOnItemClickCallback(object : GridMainAdapter.OnItemClickCallback {
                override fun onItemClicked(data: ServerResponse, position: Int) {
                    intentToDetail(data, position)
                }
            })
        }
    }

    private fun setListRecycler() {
        binding.rvFilm.apply {
            listMainAdapter = ListMainAdapter()
            listMainAdapter.submitList(list)

            adapter = listMainAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)

            listMainAdapter.setOnItemClickCallback(object : ListMainAdapter.OnItemClickCallback {
                override fun onItemClicked(data: ServerResponse, position: Int) {
                    intentToDetail(data, position)
                }
            })
        }
    }

    private fun getDataFilm() {
        with(binding) {
            viewModel.getFilm().observe(this@MainActivity) { response ->
                when (response) {
                    is Resource.Loading -> {
                        progressBar.isVisible = true
                    }
                    is Resource.Success -> {
                        progressBar.isVisible = false
                        list = response.data
                        listMainAdapter.submitList(list)

                        Log.d(TAG, "onCreate: $list")
                    }
                    is Resource.Error -> {
                        progressBar.isVisible = false
                        Toast.makeText(this@MainActivity, response.error, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun intentToDetail(data: ServerResponse, position: Int) {
        Intent(this@MainActivity, DetailActivity::class.java).also {
            it.putExtra(DATA_TAG, data)
            it.putExtra(POSITION_TAG, position)
            startActivity(it)
        }
    }

    private fun intentToProfile() {
        Intent(this@MainActivity, ProfileActivity::class.java).also {
            startActivity(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        setMode(item.itemId)
        return super.onOptionsItemSelected(item)
    }

    private fun setMode(itemId: Int) {
        when (itemId) {
            R.id.action_list -> {
                title = "Mode List"
                setListRecycler()
            }
            R.id.action_grid -> {
                title = "Mode Grid"
                setGridRecycler()
            }
            R.id.action_card_view -> {
                title = "Mode Card"
                setCardRecycler()
            }
            R.id.action_profile -> {
                intentToProfile()
            }
        }
        setActionBarTitle(title)
    }

    private fun setActionBarTitle(title: String) {
        supportActionBar?.title = title
    }
}
