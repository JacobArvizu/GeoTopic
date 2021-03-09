package com.jarvizu.geotopic.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.jarvizu.geotopic.R
import com.jarvizu.geotopic.databinding.MainActivityBinding
import com.jarvizu.geotopic.utils.Constants
import com.jarvizu.geotopic.utils.PermissionFactory
import com.skydoves.needs.needs
import dagger.hilt.android.AndroidEntryPoint
import es.dmoral.toasty.Toasty
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.PermissionRequest
import timber.log.Timber


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {

    private val RC_ACCESS_LOCATION = 500
    private val permissionUtility by needs<PermissionFactory>()
    private val viewModel: MainViewModel by viewModels()

    // Safe binding using viewbinding
    private var _binding: MainActivityBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Only show full dialog on first startup
        permissionUtility.show(binding.root)
    }

    override fun onStart() {
        super.onStart()
        setOnConfirmListener()

        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.listBottomIcon -> binding.navHostFragment.findNavController().navigate(R.id.topics)
                R.id.mapBottomIcon -> binding.navHostFragment.findNavController().navigate(R.id.mapFragment)
            }
            true
        }
    }

    private fun setOnConfirmListener() {
        permissionUtility.setOnConfirmListener {
            EasyPermissions.requestPermissions(
                PermissionRequest.Builder(this, RC_ACCESS_LOCATION, Constants.PERMISSIONS)
                    .setRationale("Access device location")
                    .setPositiveButtonText("Confirm")
                    .setTheme(R.style.ThemeOverlay_MaterialComponents_Dark)
                    .build()
            )
        }
    }

    override fun onBackPressed() {
        if (permissionUtility.isShowing) {
            permissionUtility.dismiss()
            Toasty.error(this, "Please enable location permissions in the app settings", Toast.LENGTH_LONG).show()
            this.finish()
        } else {
            super.onBackPressed()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        if (permissionUtility.isShowing) {
            Timber.d("Permissions Accepted")
            permissionUtility.dismiss()
        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        Timber.d("Permissions Denied")
        this.finish()
    }

}