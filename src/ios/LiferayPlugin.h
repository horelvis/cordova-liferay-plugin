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
#import "LRAddressService_v7.h"
#import "LRAssetCategoryService_v7.h"
#import "LRWikiPageService_v7.h"
#import "LROrganizationService_v7.h"
#import "LROrgLaborService_v7.h"
#import "LRPasswordPolicyService_v7.h"
#import "LRPermissionService_v7.h"
#import "LRPhoneService_v7.h"
#import "LRPortalService_v7.h"
#import "LRPortletPreferencesService_v7.h"
#import "LRRepositoryService_v7.h"
#import "LRResourcePermissionService_v7.h"
#import "LRRoleService_v7.h"
#import "LRTeamService_v7.h"
#import "LRUserGroupService_v7.h"
#import "LRUserGroupGroupRoleService_v7.h"
#import "LRUserGroupRoleService_v7.h"
#import "LRWikiNodeService_v7.h"
#import "LRAssetEntryService_v7.h"
#import "LRAssetTagService_v7.h"
#import "LRAssetVocabularyService_v7.h"
#import "LRBlogsEntryService_v7.h"
#import "LRBookmarksEntryService_v7.h"
#import "LRBookmarksFolderService_v7.h"
#import "LRCompanyService_v7.h"
#import "LRContactService_v7.h"
#import "LRCountryService_v7.h"
#import "LRDDLRecordService_v7.h"
#import "LRDDLRecordSetService_v7.h"
#import "LRDDMStructureService_v7.h"
#import "LRDDMTemplateService_v7.h"
#import "LRDLFileEntryService_v7.h"
#import "LRDLFileEntryTypeService_v7.h"
#import "LRDLFileVersionService_v7.h"
#import "LRDLFolderService_v7.h"
#import "LREmailAddressService_v7.h"
#import "LRExpandoColumnService_v7.h"
#import "LRExpandoValueService_v7.h"
#import "LRGroupService_v7.h"
#import "LRImageService_v7.h"
#import "LRJournalArticleService_v7.h"
#import "LRJournalFeedService_v7.h"
#import "LRJournalFolderService_v7.h"
#import "LRLayoutService_v7.h"
#import "LRLayoutBranchService_v7.h"
#import "LRLayoutPrototypeService_v7.h"
#import "LRLayoutRevisionService_v7.h"
#import "LRLayoutSetService_v7.h"
#import "LRLayoutSetPrototypeService_v7.h"
#import "LRListTypeService_v7.h"
#import "LRMBBanService_v7.h"
#import "LRMBCategoryService_v7.h"
#import "LRMBMessageService_v7.h"
#import "LRMBThreadService_v7.h"
#import "LRMDRActionService_v7.h"
#import "LRMDRRuleService_v7.h"
#import "LRMDRRuleGroupService_v7.h"
#import "LRMDRRuleGroupInstanceService_v7.h"
#import "LRMembershipRequestService_v7.h"
#import "LROrganizationService_v7.h"
#import "LROrgLaborService_v7.h"
#import "LRPasswordPolicyService_v7.h"
#import "LRPermissionService_v7.h"
#import "LRPhoneService_v7.h"
#import "LRPortletService_v7.h"
#import "LRPortletPreferencesService_v7.h"
#import "LRRepositoryService_v7.h"
#import "LRResourcePermissionService_v7.h"
#import "LRUserGroupService_v7.h"
#import "LRUserGroupGroupRoleService_v7.h"

@interface LiferayPlugin :  CDVPlugin{
}

@property (copy) NSString* callbackId;


- (void)connect:(CDVInvokedUrlCommand*)command;


- (void)execute:(CDVInvokedUrlCommand*)command;


@end
