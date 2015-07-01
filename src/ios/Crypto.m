#import "Crypto.h"
#import "CDVFile.h"

@implementation Crypto


/**
 *  encrypt
 *
 *  @param command An array of arguments passed from javascript
 */
- (void)encrypt:(CDVInvokedUrlCommand *)command {
    
    CDVPluginResult *pluginResult = nil;
    
    NSString *path = [self crypto:@"encrypt" command:command];
    
    if (path != nil) {
        pluginResult =
        [CDVPluginResult resultWithStatus:CDVCommandStatus_OK
                          messageAsString:path];
    } else {
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
    }
    
    [self.commandDelegate sendPluginResult:pluginResult
                                callbackId:command.callbackId];
    
}

/**
 *  decrypt
 *
 *  @param command An array of arguments passed from javascript
 */
- (void)decrypt:(CDVInvokedUrlCommand *)command {
    
    CDVPluginResult *pluginResult = nil;
    
    NSString *path = [self crypto:@"decrypt" command:command];
    
    if (path != nil) {
        pluginResult =
        [CDVPluginResult resultWithStatus:CDVCommandStatus_OK
                          messageAsString:path];
    } else {
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
    }
    
    [self.commandDelegate sendPluginResult:pluginResult
                                callbackId:command.callbackId];
    
}

/**
 *  encrypt
 *
 *  @param command An array of arguments passed from javascript
 */
- (void)encryptFile:(CDVInvokedUrlCommand *)command {

  CDVPluginResult *pluginResult = nil;

  NSString *path = [self crypto:@"encryptfile" command:command];

  if (path != nil) {
    pluginResult =
        [CDVPluginResult resultWithStatus:CDVCommandStatus_OK
                          messageAsString:path];
  } else {
    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
  }

  [self.commandDelegate sendPluginResult:pluginResult
                              callbackId:command.callbackId];
                              
}

/**
 *  decrypt
 *
 *  @param command An array of arguments passed from javascript
 */
- (void)decryptFile:(CDVInvokedUrlCommand *)command {

  CDVPluginResult *pluginResult = nil;

  NSString *path = [self crypto:@"decryptfile" command:command];

  if (path != nil) {
    pluginResult =
        [CDVPluginResult resultWithStatus:CDVCommandStatus_OK
                          messageAsString:path];
  } else {
    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
  }

  [self.commandDelegate sendPluginResult:pluginResult
                              callbackId:command.callbackId];
                              
}

/**
 *  Encrypts or decrypts file at given URI.
 *
 *
 *  @param action  Cryptographic operation
 *  @param command Cordova arguments
 *
 *  @return Boolean value representing success or failure
 */
- (NSString*)crypto:(NSString *)action command:(CDVInvokedUrlCommand *)command {

  NSData *data = nil;
  NSData *inputData = nil;
  NSString *outputString = nil;

      // ****************** FILE ENCRYPTION OR DECRYPTION ******************************************
      if (([action isEqualToString:@"encryptfile"]) || ([action isEqualToString:@"decryptfile"])) {
          
          NSString *sourcePath = [command.arguments objectAtIndex:0];
          NSString *targetPath = [command.arguments objectAtIndex:1];
          NSString *password = [command.arguments objectAtIndex:2];
 
          if ([[NSFileManager defaultManager] fileExistsAtPath:sourcePath]) {
              
              // get file data
              NSData *fileData = [NSData dataWithContentsOfFile:sourcePath];
              
              NSError *error;
              if ([action isEqualToString:@"encryptfile"]) {
                  // encrypt data
                  data = [RNEncryptor encryptData:fileData
                                     withSettings:kRNCryptorAES256Settings
                                         password:password
                                            error:&error];
                  
              } else if ([action isEqualToString:@"decryptfile"]) {
                  // decrypt data
                  data = [RNDecryptor decryptData:fileData
                                     withPassword:password
                                            error:&error];
              }
              
              // write to generated path
              [data writeToFile:targetPath atomically:YES];
          }
          return targetPath;
      }
      
      // ****************** STRING ENCRYPTION OR DECRYPTION ******************************************
      if (([action isEqualToString:@"encrypt"]) || ([action isEqualToString:@"decrypt"])) {
          
          NSString *sourceString = [command.arguments objectAtIndex:0];
          NSString *password = [command.arguments objectAtIndex:1];
          
          
              NSError *error;
              if ([action isEqualToString:@"encrypt"]) {
                inputData = [sourceString dataUsingEncoding:NSUTF8StringEncoding];
                  // encrypt data
                  data = [RNEncryptor encryptData:inputData
                                     withSettings:kRNCryptorAES256Settings
                                         password:password
                                            error:&error];
                  outputString = [data base64Encoding];
                  
              } else if ([action isEqualToString:@"decrypt"]) {
                  inputData = [[NSData alloc] initWithBase64EncodedString:sourceString options:0];
                  //NSString *decodedString = [[NSString alloc] initWithData:decodedData encoding:NSUTF8StringEncoding];
                  
                  // decrypt data
                  data = [RNDecryptor decryptData:inputData
                                     withPassword:password
                                            error:&error];
                  outputString = [[NSString alloc] initWithData:data encoding:NSUTF8StringEncoding];

              }
          
          return outputString;
      }
  
}

@end