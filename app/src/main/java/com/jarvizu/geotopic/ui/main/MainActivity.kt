package com.jarvizu.geotopic.ui.main

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.afollestad.assent.AssentResult
import com.afollestad.assent.Permission
import com.afollestad.assent.coroutines.awaitPermissionsResult
import com.google.android.material.snackbar.Snackbar
import com.jarvizu.geotopic.R
import com.jarvizu.geotopic.utils.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        lifecycleScope.launchWhenCreated {
            getPermissions()
        }
    }

    // Get permissions
    private suspend fun getPermissions() {
        val result: AssentResult = awaitPermissionsResult(
            Permission.ACCESS_FINE_LOCATION,
        )
    }

    override fun onStart() {
        super.onStart()

    }
}