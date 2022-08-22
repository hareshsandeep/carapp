package com.challenge.carapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.challenge.carapp.LandingRepository


class LandingViewModelFactory(
    private val landingRepo: LandingRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LandingViewModel(landingRepo) as T
    }
}