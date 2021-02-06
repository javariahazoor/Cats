package com.javaria.cats.util

import android.Manifest
import android.annotation.TargetApi
import android.app.DownloadManager
import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import java.io.File

@TargetApi(Build.VERSION_CODES.M)
fun askPermissions(
    requireActivity: FragmentActivity,
    requireContext: Context,
    imageUrl: String
) {

    // This will return the current Status
    val permissionExternalMemory =
        ActivityCompat.checkSelfPermission(
            requireContext,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    if (permissionExternalMemory != PackageManager.PERMISSION_GRANTED) {
        val STORAGE_PERMISSIONS = arrayOf<String>(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        // If permission not granted then ask for permission real time.
        ActivityCompat.requestPermissions(requireActivity, STORAGE_PERMISSIONS, 1)
    } else {
        // Permission has already been granted
        downloadImage(imageUrl, requireContext, requireActivity)
    }
}

private var msg: String? = ""
private var lastMsg = ""

fun downloadImage(url: String, requireContext: Context, activity: FragmentActivity?) {
    val directory = File(Environment.DIRECTORY_PICTURES)

    if (!directory.exists()) {
        directory.mkdirs()
    }

    val downloadManager = activity?.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

    val downloadUri = Uri.parse(url)

    val request = DownloadManager.Request(downloadUri).apply {
        setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
            .setAllowedOverRoaming(false)
            .setTitle(url.substring(url.lastIndexOf("/") + 1))
            .setDescription("")
            .setDestinationInExternalPublicDir(
                directory.toString(),
                url.substring(url.lastIndexOf("/") + 1)
            )
    }

    val downloadId = downloadManager.enqueue(request)
    val query = DownloadManager.Query().setFilterById(downloadId)
    Thread(Runnable {
        var downloading = true
        while (downloading) {
            val cursor: Cursor = downloadManager.query(query)
            cursor.moveToFirst()
            if (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_SUCCESSFUL) {
                downloading = false
            }
            val status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
            msg = statusMessage(url, directory, status)
            if (msg != lastMsg) {
                activity!!.runOnUiThread {
                    Toast.makeText(requireContext, msg, Toast.LENGTH_SHORT).show()
                }
                lastMsg = msg ?: ""
            }
            cursor.close()
        }
    }).start()
}

private fun statusMessage(url: String, directory: File, status: Int): String? {
    var msg = ""
    msg = when (status) {
        DownloadManager.STATUS_FAILED -> "Download has been failed, please try again"
        DownloadManager.STATUS_PAUSED -> "Paused"
        DownloadManager.STATUS_PENDING -> "Pending"
        DownloadManager.STATUS_RUNNING -> "Downloading..."
        DownloadManager.STATUS_SUCCESSFUL -> "Image downloaded successfully in $directory" + File.separator + url.substring(
            url.lastIndexOf("/") + 1
        )
        else -> "There's nothing to download"
    }
    return msg
}

