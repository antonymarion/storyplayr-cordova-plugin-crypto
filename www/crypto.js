/*global cordova, module*/

module.exports = {
    decrypt: function (inputString, key, successCallback, errorCallback) {
        cordova.exec(successCallback, errorCallback, "Crypto", "decrypt", [inputString, key]);
    },
    encrypt: function (inputString, key, successCallback, errorCallback) {
        cordova.exec(successCallback, errorCallback, "Crypto", "encrypt", [inputString, key]);
    },
    decryptFile: function (path, key, successCallback, errorCallback) {
        cordova.exec(successCallback, errorCallback, "Crypto", "decryptFile", [path, key]);
    },
    encryptFile: function (path, key, successCallback, errorCallback) {
        cordova.exec(successCallback, errorCallback, "Crypto", "encryptFile", [path, key]);
    }
};
