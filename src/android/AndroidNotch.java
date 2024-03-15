package com.tobspr.androidnotch;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.view.DisplayCutout;
import android.view.View;
import android.view.WindowInsets;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaArgs;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONException;
import org.json.JSONObject;


public class AndroidNotch extends CordovaPlugin {
    private static final String TAG = "AndroidNotch";

    private static DisplayCutout cutout = null;
    
    /**
     * Executes the request and returns PluginResult.
     *
     * @param action            The action to execute.
     * @param args              JSONArry of arguments for the plugin.
     * @param callbackContext   The callback id used when calling back into JavaScript.
     * @return                  True if the action was valid, false otherwise.
     */
    @Override
    public boolean execute(final String action, final CordovaArgs args, final CallbackContext callbackContext) throws JSONException {
        final Activity activity = this.cordova.getActivity();

        if ("setLayout".equals(action)) {
            this.webView.getView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            return true;
        }

        final WindowInsets insets = getInsets();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            cutout = insets.getDisplayCutout();
        }

        float density = activity.getResources().getDisplayMetrics().density;

        if ("hasCutout".equals(action)) {
            callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, cutout != null));
            return true;
        }

        if( Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
            callbackContext.error("DisplayCutout is not available on api < 28");
            return false;
        }

        if ("getInsetsTop".equals(action)) {
            activity.runOnUiThread(() -> callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, cutout != null ? (cutout.getSafeInsetTop() / density) : 0)));
            return true;
        }
        
        if ("getInsetsRight".equals(action)) {
            activity.runOnUiThread(() -> callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, cutout != null ? (cutout.getSafeInsetRight() / density) : 0)));
            return true;
        }

        if ("getInsetsBottom".equals(action)) {
            activity.runOnUiThread(() -> callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, cutout != null ? (cutout.getSafeInsetBottom() / density) : 0)));
            return true;
        }

        if ("getInsetsLeft".equals(action)) {
            activity.runOnUiThread(() -> callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, cutout != null ? (cutout.getSafeInsetLeft() / density) : 0)));
            return true;
        }

        if ("getInsets".equals(action)) {
            activity.runOnUiThread(() -> {
                try{
                    JSONObject json = new JSONObject();
                    json.put("left", cutout != null ? (cutout.getSafeInsetLeft() / density) : 0);
                    json.put("top", cutout != null ? (cutout.getSafeInsetTop() / density) : 0);
                    json.put("right", cutout != null ? (cutout.getSafeInsetRight() / density) : 0);
                    json.put("bottom", cutout != null ? (cutout.getSafeInsetBottom() / density) : 0);

                    callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, json));
                }catch (Exception e){
                    callbackContext.error(e.getMessage());
                }
            });
            return true;
        }

        return false;
    }

    @TargetApi(23)
    private WindowInsets getInsets() {
        return this.webView.getView().getRootWindowInsets();
    }
}
