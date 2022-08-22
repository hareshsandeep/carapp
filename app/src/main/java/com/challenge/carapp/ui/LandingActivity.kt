package com.challenge.carapp.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.challenge.carapp.LandingRepository
import com.challenge.carapp.R
import com.challenge.carapp.adapters.LandingAdapter
import com.challenge.carapp.components.setDivider
import com.challenge.carapp.viewmodels.LandingViewModel
import com.challenge.carapp.viewmodels.LandingViewModelFactory

class LandingActivity : AppCompatActivity() {

    private val viewModel: LandingViewModel by viewModels {
        LandingViewModelFactory(LandingRepository(application))
    }

    private lateinit var landingAdapter: LandingAdapter

    private var carsRecyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        carsRecyclerView = findViewById(R.id.carsRecyclerView)
        landingAdapter = LandingAdapter(viewModel = viewModel)
        carsRecyclerView?.apply {
            layoutManager = LinearLayoutManager(context)
            setDivider(R.drawable.recycler_view_divider)
            carsRecyclerView?.adapter = landingAdapter
        }
        viewModel.carListLiveData.observe(this) {
            landingAdapter.refresh()
        }
        viewModel.fetchCarsList()
    }
}