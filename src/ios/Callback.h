//
//  Callback.h
//  Portal Zero Mobile
//
//  Created by Horelvis Castillo Mendoza on 27/02/2020.
//

#import "LiferayPlugin.h"
#import "LRCallback.h"

@interface Callback : NSObject <LRCallback>

@property (nonatomic, retain) LiferayPlugin *liferayPlugin;
@property (nonatomic, retain) NSString *command;

- (id)init:(NSString *)command
    liferayPlugin:(LiferayPlugin *)liferayPlugin;

@end
