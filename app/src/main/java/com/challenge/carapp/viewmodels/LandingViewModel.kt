package com.challenge.carapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.challenge.carapp.LandingRepository
import com.challenge.carapp.models.CarsModel
import kotlinx.coroutines.launch

class LandingViewModel(
    private val repo: LandingRepository
) : ViewModel() {

    /**
     * LiveData that returns list of Cars.
     */
    val carListLiveData:LiveData<List<CarsModel>?>
        get() = _carListLiveData
    private val _carListLiveData = MutableLiveData<List<CarsModel>?>()

    fun fetchCarsList() {
        viewModelScope.launch {
            val carList = repo.fetchCarsList()
            _carListLiveData.postValue(carList)
        }
    }
}