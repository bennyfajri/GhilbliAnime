package com.drsync.ghilblianime.ui.main

import androidx.lifecycle.ViewModel
import com.drsync.ghilblianime.repository.Repository

class MainViewModel(
    private val repository: Repository
) : ViewModel() {

    fun getFilm() = repository.getFilm()

}