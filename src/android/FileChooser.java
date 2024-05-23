package com.megster.cordova;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.content.ClipData;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FileChooser extends CordovaPlugin {

    private static final String TAG = "FileChooser";
    private static final String ACTION_OPEN = "open";
    private static final int PICK_FILE_REQUEST = 1;

    public static final String MIME = "mime";

    CallbackContext callback;

    @Override
    public boolean execute(String action, JSONArray inputs, CallbackContext callbackContext) throws JSONException {

        if (action.equals(ACTION_OPEN)) {
            JSONObject filters = inputs.optJSONObject(0);
            chooseFile(filters, callbackContext);
            return true;
        }

        return false;
    }

public void chooseFile(JSONObject filter, CallbackContext callbackContext) {
    String uri_filter = filter.has(MIME) ? filter.optString(MIME) : "*/*";

    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
    intent.setType(uri_filter);
    intent.addCategory(Intent.CATEGORY_OPENABLE);
    intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true); // Permitir selección múltiple

    Intent chooser = Intent.createChooser(intent, "Select File");
    cordova.startActivityForResult(this, chooser, PICK_FILE_REQUEST);

    PluginResult pluginResult = new PluginResult(PluginResult.Status.NO_RESULT);
    pluginResult.setKeepCallback(true);
    callback = callbackContext;
    callbackContext.sendPluginResult(pluginResult);
}


  @Override
public void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == PICK_FILE_REQUEST && callback != null) {
        if (resultCode == Activity.RESULT_OK) {
            if (data != null) {
                ClipData clipData = data.getClipData();
                if (clipData != null) {
                    // Múltiples archivos seleccionados
                    JSONArray uris = new JSONArray();
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        Uri uri = clipData.getItemAt(i).getUri();
                        uris.put(uri.toString());
                    }
                    Log.w(TAG, uris.toString());
                    callback.success(uris);
                } else {
                    // Un solo archivo seleccionado
                    Uri uri = data.getData();
                    if (uri != null) {
                        Log.w(TAG, uri.toString());
                        callback.success(uri.toString());
                    } else {
                        callback.error("File uri was null");
                    }
                }
            } else {
                callback.error("Data was null");
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            callback.error("User canceled.");
        } else {
            callback.error(resultCode);
        }
    }
}

}
