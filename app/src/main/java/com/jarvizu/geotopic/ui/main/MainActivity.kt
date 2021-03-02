package com.jarvizu.geotopic.ui.main

import android.Manifest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jarvizu.geotopic.R
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.RuntimePermissions


@RuntimePermissions
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
    }


    @NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    fun needsPermission() {
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, grantResults)
    }
}