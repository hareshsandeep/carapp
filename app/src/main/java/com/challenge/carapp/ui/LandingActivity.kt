package com.challenge.carapp.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.challenge.carapp.LandingRepository
import com.challenge.carapp.R
import com.challenge.carapp.adapters.LandingAdapter
import com.challenge.carapp.components.FilterManager
import com.challenge.carapp.components.setDivider
import com.challenge.carapp.util.closeKeyboard
import com.challenge.carapp.viewmodels.LandingViewModel
import com.challenge.carapp.viewmodels.LandingViewModelFactory

class LandingActivity : AppCompatActivity() {

    private val viewModel: LandingViewModel by viewModels {
        LandingViewModelFactory(LandingRepository(application))
    }

    private lateinit var landingAdapter: LandingAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)
        setupTitle()
        landingAdapter = LandingAdapter(viewModel = viewModel).apply {
            FilterManager(
                makeView = findViewById(R.id.make),
                modelView = findViewById(R.id.model),
                this
            )
        }

        findViewById<RecyclerView>(R.id.carsRecyclerView).apply {
            layoutManager = LinearLayoutManager(context)
            setDivider(R.drawable.recycler_view_divider)
            adapter = landingAdapter
        }

        setObservers()
        viewModel.fetchCarsList()
    }

    private fun setupTitle() {
        this.supportActionBar?.hide()
    }

    private fun setObservers() {
        viewModel.carListLiveData.observe(this) {
            landingAdapter.refresh()
        }

        viewModel.collapsed.observe(this) {
            landingAdapter.notifyItemChanged(it)
        }

        viewModel.expanded.observe(this) {
            landingAdapter.notifyItemChanged(it)
        }

        viewModel.closeKeyBoardObserver.observe(this) {
            this.closeKeyboard()
        }
    }
}