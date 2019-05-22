var argscheck = require('cordova/argscheck'),
    exec = require('cordova/exec'),
    channel = require('cordova/channel'),
    cordova = require('cordova');

function Liferay () { console.log('Liferay loaded');}


Liferay.prototype.connect = function( ipServer, userName, password,successCallback, errorCallback) {

      //if error is null then replace with empty function to silence warnings
      if(!errorCallback){
        errorCallback = function(){};
      }

    	exec(successCallback, errorCallback, "Liferay", "connect", [ipServer, userName, password]);
}

Liferay.prototype.authentication = function( ipServer, username, token, successCallback, errorCallback) {

      //if error is null then replace with empty function to silence warnings
      if(!errorCallback){
        errorCallback = function(){};
      }

    	exec(successCallback, errorCallback, "Liferay", "authentication", [ipServer, username, token ]);
}

Liferay.prototype.execute = function(className, method, params, successCallback, errorCallback) {

      //if error is null then replace with empty function to silence warnings
      if(!errorCallback){
        errorCallback = function(){};
      }

    	exec(successCallback, errorCallback, "Liferay", "execute", [className, method, params]);
}

var Liferay = new Liferay();

module.exports = Liferay;
