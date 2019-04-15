cordova-liferay-plugin
======================

This plugin defines a global 'Liferay' object, which describes the interface to Liferay SDK.
Although the object is in the global scope, it is not available until after the 'deviceready' event.

If you find any bug, please create a Issue.

``` 
### Supported versions: (Authentication)

- Android: 6.2.0.22.
- IOS: 6.2.0.21.
- WP
``` 

``` 
Liferay.connect(null, null, 'http://10.0.2.2:8080', 'test@liferay.com', 'test' );
``` 
## Installation
``` 
cordova plugin add https://github.com/salvatejero/cordova-liferay-plugin.git
``` 

### Supported Platforms:

- Android.
- IOS.
- WP

##Requirements:

This plugin requires Liferay SDK.

- Add Liferay SDK to your project: [Liferay SDK dev site](https://dev.liferay.com/develop/tutorials/-/knowledge_base/6-2/mobile "Liferay SDK dev site").

- In iOS, if you use pods, you must install first all plugins that you will use in the project because then it isn't possible to include.  

- In Windows Phone 8 (WP8), you must use this sdk version [Ithildir](https://github.com/Ithildir/liferay-sdk-builder-windows).

### Quick Example

``` 

Liferay.execute(successCallback, errorCallback, 'com.liferay.portal.model.User', 'getUserByEmailAddress', ['10154', 'test@liferay.com']);


function successCallback(data){
	// .... json array or json object depends of Liferay SDK method
}


```

## iOS 
- In iOS method invoke needs a last parameter NSError. I include it and show it in errorCallback.
- In iOS methods name are different to Android and WP API. You can choose whichever.

``` 
Liferay.execute(successCallback, errorCallback, 'com.liferay.portal.model.User', 'getUserByEmailAddress', ['10154', 'test@liferay.com']);

Liferay.execute(successCallback, errorCallback, 'com.liferay.portal.model.User', 'getUserByEmailAddressWithCompanyId', ['10154', 'test@liferay.com']);

//both invoke return the same and works in any platform.

``` 
## Windows Phone 8

- In Windows Phone methods name are different to Android and IOS API. You can choose whichever

## ClassNames Implemented
``` 
-Portal
-Permission
-com.liferay.portal.model.Address
-com.liferay.portlet.asset.model.AssetCategory
-com.liferay.portlet.asset.model.AssetEntry
-com.liferay.portlet.asset.model.AssetTag
-com.liferay.portlet.asset.model.AssetVocabulary
-com.liferay.portlet.blogs.model.BlogsEntry
-com.liferay.portlet.bookmarks.model.BookmarksEntry
-com.liferay.portlet.bookmarks.model.BookmarksFolder
-com.liferay.portal.model.Company
-com.liferay.portal.model.Contact
-com.liferay.portal.model.Country
-com.liferay.portlet.dynamicdatalists.model.DDLRecord
-com.liferay.portlet.dynamicdatalists.model.DDLRecordSet
-com.liferay.portlet.dynamicdatamapping.model.DDMStructure
-com.liferay.portlet.dynamicdatamapping.model.DDMTemplate
-com.liferay.portlet.documentlibrary.model.DLFileEntry
-com.liferay.portlet.documentlibrary.model.DLFileEntryType
-com.liferay.portlet.documentlibrary.model.DLFileVersion
-com.liferay.portlet.documentlibrary.model.DLFolder
-com.liferay.portal.model.EmailAddress
-com.liferay.portlet.expando.model.ExpandoColumn
-com.liferay.portlet.expando.model.ExpandoValue
-com.liferay.portal.model.Group
-com.liferay.portal.model.Image
-com.liferay.portlet.journal.model.JournalArticle
-com.liferay.portlet.journal.model.JournalFeed
-com.liferay.portlet.journal.model.JournalFolder
-com.liferay.portal.model.Layout
-com.liferay.portal.model.LayoutBranch
-com.liferay.portal.model.LayoutPrototype
-com.liferay.portal.model.LayoutRevision
-com.liferay.portal.model.LayoutSet
-com.liferay.portal.model.LayoutSetPrototype
-com.liferay.portal.model.ListType
-com.liferay.portlet.messageboards.model.MBBan
-com.liferay.portlet.messageboards.model.MBCategory
-com.liferay.portlet.messageboards.model.MBMessage
-com.liferay.portlet.messageboards.model.MBThread
-com.liferay.portlet.mobiledevicerules.model.MDRAction
-com.liferay.portlet.mobiledevicerules.model.MDRRule
-com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup
-com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance
-com.liferay.portal.model.MembershipRequest
-com.liferay.portal.model.Organization
-com.liferay.portal.model.OrgLabor
-com.liferay.portal.model.PasswordPolicy
-com.liferay.portal.model.Phone
-com.liferay.portal.model.Portlet
-com.liferay.portal.model.PortletPreferences
-com.liferay.portal.model.Repository
-com.liferay.portal.model.ResourcePermission
-com.liferay.portal.model.Role
-com.liferay.portal.model.Team
-com.liferay.portal.model.User			
-com.liferay.portal.model.UserGroup
-com.liferay.portal.model.UserGroupGroupRole
-com.liferay.portal.model.UserGroupRole
-com.liferay.portlet.wiki.model.WikiNode
-com.liferay.portlet.wiki.model.WikiPage
``` 