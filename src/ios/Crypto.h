#import <Foundation/Foundation.h>
#import <Security/SecRandom.h>
#import <Cordova/CDV.h>
#import <Cordova/NSData+Base64.h>
#import "RNEncryptor.h"
#import "RNDecryptor.h"


@interface Crypto : CDVPlugin {
}
- (void)encrypt:(CDVInvokedUrlCommand*)command;
- (void)decrypt:(CDVInvokedUrlCommand*)command;
- (void)encryptFile:(CDVInvokedUrlCommand*)command;
- (void)decryptFile:(CDVInvokedUrlCommand*)command;
- (NSString*)crypto:(NSString*)action command:(CDVInvokedUrlCommand*)command;
@end