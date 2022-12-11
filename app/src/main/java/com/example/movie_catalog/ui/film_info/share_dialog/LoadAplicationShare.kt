package com.example.movie_catalog.ui.film_info.share_dialog

import android.content.Intent
import android.content.pm.ResolveInfo
import androidx.core.content.res.ResourcesCompat
import com.example.movie_catalog.R

private val availableApps: MutableList<DialogItemEntity> = ArrayList()
private lateinit var sendIntent: Intent

private fun LoadAplicationShare() {

//    val activity = requireActivity()
//    sendIntent = Intent().apply {
//        action = Intent.ACTION_SEND
//        putExtra(Intent.EXTRA_TEXT, textToShare)
//        type = "text/plain"
//    }
//
//    val activities: List<ResolveInfo> = activity.packageManager.queryIntentActivities(
//        sendIntent,
//        0
//    )
//
//    for (info in activities) {
//        availableApps.add(
//            DialogItemEntity(
//                info.loadLabel(activity.packageManager).toString(),
//                info.loadIcon(activity.packageManager),
//                info.activityInfo.packageName
//            )
//        )
//    }
//
//    val moreDescription = resources.getString(R.string.share_more_options)
//    val moreIcon = ResourcesCompat.getDrawable(
//        resources,
//        R.drawable.shape_action_more,
//        null
//    )
//    moreIcon?.let { drawable ->
//        availableApps.add(
//            DialogItemEntity(
//                moreDescription,
//                drawable,
//                MORE_TAG
//            )
//        )
//    }
//    adapter.addItems(availableApps)
}