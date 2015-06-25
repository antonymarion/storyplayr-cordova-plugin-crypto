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

        if (action.equals("encrypt")) {

            try {
                String inputString = data.getString(0);
                String key = data.getString(1);
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
                String key = data.getString(1);
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
                String sourcePath = data.getString(0);
                String targetPath = data.getString(1);
                String key = data.getString(2);

                CordovaResourceApi resourceApi = webView.getResourceApi();
                Uri tmpSrc = Uri.parse(sourcePath);
                Uri sourceUri = resourceApi.remapUri(
                  tmpSrc.getScheme() != null ? tmpSrc : Uri.fromFile(new File(sourcePath)));
                File sourceFile = new File(sourceUri.getPath());
                
                tmpSrc = Uri.parse(targetPath);
                Uri targetUri = resourceApi.remapUri(
                        tmpSrc.getScheme() != null ? tmpSrc : Uri.fromFile(new File(targetPath)));
                File targetFile = new File(targetUri.getPath());

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
                String sourcePath = data.getString(0);
                String targetPath = data.getString(1);
                String key = data.getString(2);

                CordovaResourceApi resourceApi = webView.getResourceApi();
                Uri tmpSrc = Uri.parse(sourcePath);
                Uri sourceUri = resourceApi.remapUri(
                  tmpSrc.getScheme() != null ? tmpSrc : Uri.fromFile(new File(sourcePath)));
                File sourceFile = new File(sourceUri.getPath());
                
                tmpSrc = Uri.parse(targetPath);
                Uri targetUri = resourceApi.remapUri(
                        tmpSrc.getScheme() != null ? tmpSrc : Uri.fromFile(new File(targetPath)));
                File targetFile = new File(targetUri.getPath());

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
