//
//  LiferayPlugin.h
//  PhonegapLiferay
//
//  Created by Salvador Tejero Silva on 15/1/15.
//  Modificated by Horelvis 15/04/2019
//

#import <Cordova/CDVPlugin.h>
#import <objc/runtime.h>
#import "LRSession.h"
#import "LRUserService_v7.h"
#import "LRCallback.h"
#import "LRBaseService.h"


@interface LiferayPlugin :  CDVPlugin{
}

@property NSString* callbackId;

- (void)connect:(CDVInvokedUrlCommand*)command;

- (void)authentication:(CDVInvokedUrlCommand*)command;

- (void)execute:(CDVInvokedUrlCommand*)command;

- (void)success:(id)result callbackId:(NSString *)callbackId;

@end
