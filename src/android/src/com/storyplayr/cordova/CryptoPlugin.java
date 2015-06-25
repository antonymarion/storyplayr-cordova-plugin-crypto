package com.storyplayr.cordova;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;

import javax.crypto.Cipher;
import java.io.File;
import java.net.URI;

public class CryptoPlugin extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray data, CallbackContext callbackContext) throws JSONException {

        String key = data.getString(1);
        if (action.equals("encrypt")) {

            try {
                String inputString = data.getString(0);
                String result = Crypto.cryptoString(Cipher.ENCRYPT_MODE, key, inputString);
                callbackContext.success(result);
                return true;
            }
            catch(CryptoException ex) {
                callbackContext.error(ex.getMessage());
                return false;
            }

        }

        if (action.equals("decrypt")) {

            try {
                String inputString = data.getString(0);
                String result = Crypto.cryptoString(Cipher.DECRYPT_MODE, key, inputString);
                callbackContext.success(result);
                return true;
            }
            catch(CryptoException ex) {
                callbackContext.error(ex.getMessage());
                return false;
            }

        }

        if (action.equals("encryptFile")) {

            try {
                String path = data.getString(0);
                Log.d("CryptoPlugin",  path);

                CordovaResourceApi resourceApi = webView.getResourceApi();
                Uri tmpSrc = Uri.parse(path);
                Uri sourceUri = resourceApi.remapUri(
                        tmpSrc.getScheme() != null ? tmpSrc : Uri.fromFile(new File(path)));

                File sourceFile = new File(sourceUri.getPath());
                File targetFile = new File(sourceUri.getPath() + "xxx");

                Crypto.cryptoFile(Cipher.ENCRYPT_MODE, key, sourceFile, targetFile);
                callbackContext.success("ok");
                return true;
            }
            catch(Exception ex) {
                Log.d("CryptoPlugin", Log.getStackTraceString(ex));
                callbackContext.error(ex.getMessage());
                return false;
            }

        }

        if (action.equals("decryptFile")) {

            try {
                String path = data.getString(0);
                Log.d("CryptoPlugin",  path);

                CordovaResourceApi resourceApi = webView.getResourceApi();
                Uri tmpSrc = Uri.parse(path);
                Uri sourceUri = resourceApi.remapUri(
                        tmpSrc.getScheme() != null ? tmpSrc : Uri.fromFile(new File(path)));

                File sourceFile = new File(sourceUri.getPath());
                File targetFile = new File(sourceUri.getPath() + "yyy");

                Crypto.cryptoFile(Cipher.DECRYPT_MODE, key, sourceFile, targetFile);
                callbackContext.success("ok");
                return true;
            }
            catch(CryptoException ex) {
                callbackContext.error(ex.getMessage());
                return false;
            }

        }
            
        return false;

    }


}