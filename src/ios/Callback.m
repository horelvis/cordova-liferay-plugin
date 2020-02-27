//
//  Callback.m
//  Portal Zero Mobile
//
//  Created by Horelvis Castillo Mendoza on 27/02/2020.
//

#import "Callback.h"

@implementation Callback

- (id)init:(NSString *)command
        liferayPlugin:(LiferayPlugin *)liferayPlugin {

    self = [super init];

    if (self) {
        self.command = command;
        self.liferayPlugin = liferayPlugin;
    }

    return self;
}

- (void)onFailure:(NSError *)error {
    NSLog(@"Error: %@", error);
}

- (void)onSuccess:(id)result {
    [self.liferayPlugin success:result callbackId:self.command];
}

@end
