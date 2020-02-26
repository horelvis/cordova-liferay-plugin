//
//  LiferayPlugin.m
//  PhonegapLiferay
//
//  Created by Salvador Tejero Silva on 15/1/15.
//  Modificated by Horelvis 15/04/2019
//

#import "LiferayPlugin.h"
#import "LRBasicAuthentication.h"
#import "LRGoogleAuthentication.h"
#import "LRCallback.h"
#import "LRCredentialStorage.h"

@implementation LiferayPlugin

@synthesize callbackId;

- (void)connect:(CDVInvokedUrlCommand*)command
{
    NSArray *params = command.arguments;
    callbackId = command.callbackId;
    LRSession * session = [[LRSession alloc] initWithServer:params[0]
                                             authentication:[[LRBasicAuthentication alloc] initWithUsername:params[1] password:params[2]]];

    [self getUser: params[1] withLRSession: session];

}

- (void)authentication:(CDVInvokedUrlCommand*)command
{
    NSArray *params = command.arguments;
    int timeout = 6000;
    callbackId = command.callbackId;
    LRSession * session = [[LRSession alloc] initWithServer:params[0]
                                             authentication:[[LRGoogleAuthentication alloc] initWithAuthToken:params[1] authToken:params[2]]
                                             ];

    [self getUser: params[1] withLRSession: session];

}


- (void)execute:(CDVInvokedUrlCommand*)command
{

    callbackId = command.callbackId;
    NSArray *params = command.arguments;
    LRSession *session = [LRCredentialStorage getSession];
    [session setCallback: self];
    if(session != nil && session.authentication != nil){

        [self objectModelWithClassName:params[0] withMethodName:params[1] withParams:params[2]];
    }else{

        [self sendPluginResult:nil withErrorMessage: @"No session actived"];
    }
}

-(void) objectModelWithClassName:(NSString*)className withMethodName:(NSString*) methodName withParams: (NSArray*) jsonArray
{
    LRBaseService *service = [self serviceWithClassName:className];

    unsigned int methodCount = 0;
    Method *methods = class_copyMethodList([service class], &methodCount);

    Method *methodToExecute = nil;
    NSString *methodNameString = nil;
    UInt32 numberParams = 0;
    for (unsigned int i = 0; i < methodCount; i++) {
        Method method = methods[i];
        methodNameString = [NSString stringWithCString: sel_getName(method_getName(method))
                                              encoding: NSASCIIStringEncoding];

        NSString * methodName2 = [methodNameString stringByReplacingOccurrencesOfString: @ "Async" withString: @ ""];
        if([methodNameString hasPrefix:methodName] || [[methodName2 lowercaseString] hasPrefix:methodName])
        {
            numberParams = method_getNumberOfArguments(method) - 2;  // Count only the method's parameters
            if(numberParams == [jsonArray count] + 1){

                methodToExecute = &method;
                break;
            }else{
                NSLog(@"the method '%@' is similar but params is incorrect, is it the method that you are looking for? ",methodNameString);
            }


        }

    }
    free(methods);
    if(methodToExecute != nil){

        NSLog(@"%s", method_getTypeEncoding(*methodToExecute));

        SEL sel = NSSelectorFromString(methodNameString);
        NSMethodSignature *sig = [[service class] instanceMethodSignatureForSelector:sel];
        NSInvocation *invocation = [NSInvocation invocationWithMethodSignature:sig];
        [invocation setSelector:sel];
        [invocation setTarget:service];
        int i = 2;
        for(int j=0; j< [jsonArray count]; j++){
            NSString* arg1 = jsonArray[j];

            const char* argType = [[invocation methodSignature] getArgumentTypeAtIndex:i];

            if(!strcmp(argType, @encode(id))) {
                [invocation setArgument:&arg1 atIndex:i];
            } else if(!strcmp(argType, @encode(int))) {
                int arg = [arg1 intValue];
                [invocation setArgument:&arg atIndex:i];
            } else if(!strcmp(argType, @encode(bool))) {
                bool arg = [arg1 boolValue];
                [invocation setArgument:&arg atIndex:i];
            } else if(!strcmp(argType, @encode(NSString))) {
                [invocation setArgument:&arg1 atIndex:i];
            } else if(!strcmp(argType, @encode(BOOL))) {
                BOOL arg = [arg1 boolValue];
                [invocation setArgument:&arg atIndex:i];
            } else if(!strcmp(argType, @encode(short))) {
                short arg = [arg1 intValue];
                [invocation setArgument:&arg atIndex:i];
            } else if(!strcmp(argType, @encode(float))) {
                float arg = [arg1 floatValue];
                [invocation setArgument:&arg atIndex:i];
            } else if(!strcmp(argType, @encode(double))) {
                double arg = [arg1 doubleValue];
                [invocation setArgument:&arg atIndex:i];
            } else if(!strcmp(argType, @encode(long))) {
                long arg = [arg1 longLongValue];
                [invocation setArgument:&arg atIndex:i];
            } else if(!strcmp(argType, @encode(long long))) {
                long long arg = [arg1 longLongValue];
                [invocation setArgument:&arg atIndex:i];
            } else {
                NSAssert1(NO, @"-- Unhandled type: %s", argType);
            }
            i ++;
        }

        __autoreleasing NSError *error;
        __autoreleasing NSError **errorPointer = &error;
        [invocation setArgument:&errorPointer atIndex:i];
        [invocation invoke];
        __strong NSError *getError = *errorPointer;
        NSLog(@"%@", getError);
    } else {
      NSLog(@"the method '%@' is no found",methodName);
      [self sendPluginResult:nil withErrorMessage: @"the method is no found"];
    }
}



-(LRBaseService*) serviceWithClassName:(NSString*) className
{
    LRSession *session = [LRCredentialStorage getSession];
    [session setCallback: self];
    LRBaseService *service = nil;

    //Added by Horelvis Castillo
    Class serviceClass = NSClassFromString (className);
    service = [[serviceClass alloc]initWithSession:session];

    return service;
}

-(void)getUser:(NSString*)username withLRSession: (LRSession*) session
{

    NSDictionary *infoDict = [[NSBundle mainBundle] infoDictionary];
    long long defaultCompanyId = [[infoDict objectForKey:@"default-company-id"] longLongValue];
    NSError *error;
    LRUserService_v7 *service = [[LRUserService_v7 alloc] initWithSession:session];
    NSDictionary *user = [service getUserByEmailAddressWithCompanyId:defaultCompanyId emailAddress: username error:&error];
    NSLog(@"%@", error);

    if(user != nil){
      
      if ([session.authentication isKindOfClass:[LRBasicAuthentication class]]) {
          LRBasicAuthentication *basicAuthentication = (LRBasicAuthentication *)session.authentication;
          [LRCredentialStorage storeCredentialForServer: session.server
                                               username: basicAuthentication.username password: basicAuthentication.password];
      } else {
          LRGoogleAuthentication *googleAuthentication = (LRGoogleAuthentication *)session.authentication;
          [LRCredentialStorage removeCredential];
          [LRCredentialStorage storeCredentialForOauth: session.server
                                               username: googleAuthentication.username token: googleAuthentication.authToken];
      }

    }
    [self sendPluginResult:user withErrorMessage: [error localizedDescription]];
}


- (void)sendPluginResult:(NSDictionary*)dict withErrorMessage:(NSString*)errorMessage
{

    CDVPluginResult* result = nil;

    if(dict == nil)
    {
        result = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:errorMessage];
    }else
    {
        result = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsDictionary:dict];
    }


    [result setKeepCallbackAsBool:YES];
    [self.commandDelegate sendPluginResult:result callbackId:callbackId];
}


- (void)onFailure:(NSError *)error {
    // Implement error handling code
    NSLog(@"%@", error);
    CDVPluginResult* result = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:[error localizedDescription]];
    [result setKeepCallbackAsBool:YES];
    [self.commandDelegate sendPluginResult:result callbackId:callbackId];
}

- (void)onSuccess:(id)result {
    // Called after request has finished successfully
    CDVPluginResult* cvResult = nil;

    if ([result isKindOfClass:[NSDictionary class]])
    {
        cvResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsDictionary:result];
    }
    else
    {
        cvResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsArray:result];

    }
    [cvResult setKeepCallbackAsBool:YES];
    [self.commandDelegate sendPluginResult:cvResult callbackId:callbackId];

}

@end
