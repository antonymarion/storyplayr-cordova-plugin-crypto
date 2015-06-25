/*global cordova, module*/

module.exports = {
    decrypt: function (inputString, key, successCallback, errorCallback) {
        cordova.exec(successCallback, errorCallback, "Crypto", "decrypt", [inputString, key]);
    },
    encrypt: function (inputString, key, successCallback, errorCallback) {
        cordova.exec(successCallback, errorCallback, "Crypto", "encrypt", [inputString, key]);
    },
    decryptFile: function (sourcePath, targetPath, key, successCallback, errorCallback) {
        cordova.exec(successCallback, errorCallback, "Crypto", "decryptFile", [sourcePath, targetPath, key]);
    },
    encryptFile: function (sourcePath, targetPath, key, successCallback, errorCallback) {
        cordova.exec(successCallback, errorCallback, "Crypto", "encryptFile", [sourcePath, targetPath, key]);
    }
};
