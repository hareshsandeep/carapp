package com.challenge.carapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.challenge.carapp.LandingRepository
import com.challenge.carapp.models.CarsModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LandingViewModel(
    private val repo: LandingRepository
) : ViewModel() {

    /**
     * LiveData that returns list of Cars.
     */
    val carListLiveData: LiveData<List<CarsModel>?>
        get() = _carListLiveData
    private val _carListLiveData = MutableLiveData<List<CarsModel>?>()

    /**
     * LiveData that closes keyboard.
     */
    val closeKeyBoardObserver: LiveData<Unit>
        get() = _closeKeyBoardLiveData
    private val _closeKeyBoardLiveData = MutableLiveData<Unit>()

    val expanded = MutableLiveData<Int>().apply {
        postValue(0)
    }

    val collapsed = MutableLiveData<Int>().apply {
        postValue(-1)
    }

    /**
     * Fetches list of cars.
     */
    fun fetchCarsList() {
        viewModelScope.launch(Dispatchers.IO) {
            val carList = repo.getCarsList()
            _carListLiveData.postValue(carList)
        }
    }

    /**
     * Expands an item.
     */
    fun setExpanded(position: Int) {
        val currentExpanded = expanded.value ?: 0
        if (position == currentExpanded) {
            return
        }

        expanded.postValue(position)
        collapse(currentExpanded)
    }

    /**
     * Collapses an item.
     */
    private fun collapse(position: Int) {
        collapsed.postValue(position)
    }

    fun closeKeyBoard() {
        _closeKeyBoardLiveData.postValue(Unit)
    }
}