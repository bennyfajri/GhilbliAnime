package com.drsync.ghilblianime.ui.detail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.drsync.ghilblianime.databinding.ActivityDetailBinding
import com.drsync.ghilblianime.models.ServerResponse
import com.drsync.ghilblianime.repository.Repository
import com.drsync.ghilblianime.ui.main.CardLayoutAdapter
import com.drsync.ghilblianime.ui.main.MainViewModel
import com.drsync.ghilblianime.ui.main.MainViewModelFactory
import com.drsync.ghilblianime.util.Constants
import com.drsync.ghilblianime.util.Constants.DATA_TAG
import com.drsync.ghilblianime.util.Constants.POSITION_TAG
import com.drsync.ghilblianime.util.Constants.createProgress
import com.drsync.ghilblianime.util.Resource

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var recyclerAdapter: CardLayoutAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = intent.getParcelableExtra<ServerResponse>(DATA_TAG)
        val position = intent.getIntExtra(POSITION_TAG, 0)

        val score = "${data?.rt_score}/100"
        val duration = "${data?.running_time} min"

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        with(binding) {
            //setView
            tvTitle.text = data?.title
            tvDurasi.text = duration
            tvScore.text = score
            tvProducer.text = data?.producer
            tvDirector.text = data?.director
            tvYear.text = data?.release_date
            tvDescription.text = data?.description
            tvOriginalTitle.text = data?.original_title
            tvTitleRomanised.text = data?.original_title_romanised
            Glide.with(this@DetailActivity)
                .load(data?.movie_banner)
                .placeholder(this@DetailActivity.createProgress())
                .error(android.R.color.darker_gray)
                .into(imgBanner)

            setActionBar(data?.title ?: "")
        }

        getDataFilm(position)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        recyclerAdapter = CardLayoutAdapter()
        binding.rvFilm.apply {
            adapter = recyclerAdapter
            layoutManager = LinearLayoutManager(this@DetailActivity)
        }

        recyclerAdapter.setOnItemClickCallback(object : CardLayoutAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ServerResponse, position: Int) {
                Intent(this@DetailActivity, DetailActivity::class.java).also {
                    it.putExtra(DATA_TAG, data)
                    it.putExtra(POSITION_TAG, position)
                    startActivity(it)
                }
            }
        })
    }

    private fun getDataFilm(position: Int) {
        with(binding) {
            viewModel.getFilm().observe(this@DetailActivity) { response ->
                when (response) {
                    is Resource.Loading -> {
                        progressBar.isVisible = true
                    }
                    is Resource.Success -> {
                        progressBar.isVisible = false
                        val data = response.data as MutableList
                        data.removeAt(position)
                        recyclerAdapter.submitList(data)

                        Log.d(Constants.TAG, "onCreate: ${response.data}")
                    }
                    is Resource.Error -> {
                        progressBar.isVisible = false
                        Toast.makeText(this@DetailActivity, response.error, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun setActionBar(
        title: String
    ) {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = title
        supportActionBar?.elevation = 0f
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }
}