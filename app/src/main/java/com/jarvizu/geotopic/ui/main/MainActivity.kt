package com.jarvizu.geotopic.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.afollestad.assent.AssentResult
import com.afollestad.assent.Permission
import com.afollestad.assent.coroutines.awaitPermissionsResult
import com.jarvizu.geotopic.R
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

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
}