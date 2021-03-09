package com.jarvizu.geotopic.utils

import android.content.Context
import android.graphics.Typeface
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.jarvizu.geotopic.R
import com.skydoves.needs.*

// Small utility to easily build a permission request
class PermissionFactory : Needs.Factory() {

    override fun create(context: Context, lifecycle: LifecycleOwner): Needs {
        val titleForm = textForm {
            textSize = 18
            textStyle = Typeface.BOLD
            textColor = ContextCompat.getColor(context, R.color.white)
        }

        val theme = needsTheme(context) {
            backgroundColor = ContextCompat.getColor(context, R.color.dracula_background)
            titleTextForm = textForm {
                textSize = 18
                textColor = ContextCompat.getColor(context, R.color.white)
            }
            descriptionTextForm = textForm {
                textSize = 12
                textColor = ContextCompat.getColor(context, R.color.dracula_comment)
            }
        }

        val itemTheme = needsItemTheme(context) {
            backgroundColor = ContextCompat.getColor(context, R.color.dracula_background)
            titleTextForm = textForm {
                textColor = ContextCompat.getColor(context, R.color.white)
                textSize = 16
            }
            descriptionTextForm = textForm {
                textColor = ContextCompat.getColor(context, R.color.dracula_comment)
            }
        }

        // Permission Builder
        return createNeeds(context) {
            setTitleIcon(ContextCompat.getDrawable(context, R.drawable.ic_baseline_perm_device_information_24))
            setTitle("Permission instructions \nfor using this Android app.")
            setTitleTextForm(titleForm)
            setDescription(
                "The above permissions are needed in order for the app to perform as intended."
            )
            setConfirm("Confirm")
            setBackgroundAlpha(0.7f)
            setLifecycleOwner(lifecycle)
            setNeedsTheme(theme)
            setNeedsItemTheme(itemTheme)
            setNeedsAnimation(NeedsAnimation.FADE)
            setDividerHeight(0.5f)
            addNeedsItem(
                NeedsItem(
                    ContextCompat.getDrawable(context, R.drawable.ic_baseline_location_searching_24),
                    "Location",
                    "(Required)",
                    "Access this device's location.",
                )
            )
        }
    }
}