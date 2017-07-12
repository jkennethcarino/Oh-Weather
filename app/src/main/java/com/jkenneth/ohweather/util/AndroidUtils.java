package com.jkenneth.ohweather.util;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;

import com.jkenneth.ohweather.BuildConfig;

import java.io.File;
import java.net.URI;

/**
 * Created by Jhon Kenneth Carino on 7/12/17.
 */

public class AndroidUtils {

    public static void openDownloadedFile(@NonNull Context context,
                                           @NonNull DownloadManager downloadManager,
                                           long lastDownloadId) {
        DownloadManager.Query query = new DownloadManager.Query();

        query.setFilterById(lastDownloadId);
        Cursor cursor = downloadManager.query(query);

        if (cursor != null) {
            cursor.moveToFirst();
            if (cursor.getColumnCount() == 0) {
                return;
            }

            int status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
            if (status == DownloadManager.STATUS_SUCCESSFUL) {
                String path = cursor.getString(
                        cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                File file = new File(URI.create(path));
                Uri uri = FileProvider.getUriForFile(context,
                        BuildConfig.APPLICATION_ID + ".fileprovider", file);

                Intent viewIntent = new Intent(Intent.ACTION_VIEW);
                viewIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                viewIntent.setDataAndType(uri, cursor.getString(
                        cursor.getColumnIndex(DownloadManager.COLUMN_MEDIA_TYPE)));

                if (context.getPackageManager().resolveActivity(viewIntent, 0) != null) {
                    context.startActivity(viewIntent);
                }
            }
        }
    }
}
