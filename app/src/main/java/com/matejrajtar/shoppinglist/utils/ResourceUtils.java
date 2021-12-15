package com.matejrajtar.shoppinglist.utils;

import android.content.Context;
import android.net.Uri;

import androidx.core.content.FileProvider;

import com.matejrajtar.shoppinglist.BuildConfig;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ResourceUtils {
    private static final String AUTHORITY = BuildConfig.APPLICATION_ID + ".provider";

    public static Uri uri(Context context) throws Exception {
        return FileProvider.getUriForFile(context, AUTHORITY, newFile(context.getCacheDir()));
    }

    public static File fileFromUri(Context context, Uri uri) throws Exception {
        File file = newFile(context.getFilesDir());
        InputStream inputStream = context.getContentResolver().openInputStream(uri);
        OutputStream outputStream = new FileOutputStream(file);

        if (inputStream != null) {
            copy(inputStream, outputStream);
        }

        return file;
    }

    public static File createFile(Context context, byte[] content) throws Exception {
        File file = newFile(context.getFilesDir());

        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(content);
            fos.close();
        }

        return file;
    }

    private static File newFile(File parent) throws Exception {
        return File.createTempFile("image", "", parent);
    }


    public static void copy(InputStream inputStream, OutputStream outputStream) throws IOException {
        try {
            int read;
            byte[] bytes = new byte[1024];

            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }

            outputStream.flush();
        } finally {
            close(inputStream);
            close(outputStream);
        }
    }

    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                // ignore
            }
        }
    }
}