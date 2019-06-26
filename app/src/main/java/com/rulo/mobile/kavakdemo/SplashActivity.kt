package com.rulo.mobile.kavakdemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.rulo.mobile.kavakdemo.repository.local.viewModel.BrastlewarkGnomesViewModel
import timber.log.Timber

class SplashActivity : AppCompatActivity() {
    init {

    }

    private val brastlewarkGnomesViewModel by lazy {
        ViewModelProviders.of(this).get(BrastlewarkGnomesViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        setEvents()
    }

    private fun setEvents() {
        brastlewarkGnomesViewModel.mustGetRemoteGnomes.observe(this, Observer {
            when (it) {
                true -> {
                    Timber.wtf("Must get remote gnomes")
                }
                false -> {
                    Timber.wtf("Go to main")
                }
                null -> {
                    Timber.wtf("Waiting...")
                    brastlewarkGnomesViewModel.getRemoteGnomes()
                }
            }
        }
        )
    }
}
