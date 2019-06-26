package com.rulo.mobile.kavakdemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.rulo.mobile.kavakdemo.repository.local.viewModel.BrastlewarkGnomesViewModel

class MainActivity : AppCompatActivity() {

    private val brastlewarkGnomesViewModel by lazy {
        ViewModelProviders.of(this).get(BrastlewarkGnomesViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        brastlewarkGnomesViewModel.getGnomes()
        brastlewarkGnomesViewModel.getRemoteGnomes()
    }
}
