/*
Licensed to the Apache Software Foundation (ASF) under one
or more contributor license agreements. See the NOTICE file
distributed with this work for additional information
regarding copyright ownership. The ASF licenses this file
to you under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance
with the License. You may obtain a copy of the License at
http://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing,
software distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
KIND, either express or implied. See the License for the
specific language governing permissions and limitations
under the License.
*/

package org.cordova.liferay;

/**
 * Created by stejeros on 21/12/2014.
 * Modificated by Horelvis 15/04/2019
 */
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.liferay.mobile.android.auth.basic.BasicAuthentication;
import com.liferay.mobile.android.callback.Callback;
import com.liferay.mobile.android.callback.typed.JSONArrayCallback;
import com.liferay.mobile.android.callback.typed.JSONObjectCallback;
import com.liferay.mobile.android.service.BaseService;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.service.SessionImpl;
import com.liferay.mobile.android.v7.group.GroupService;
import com.liferay.mobile.android.v7.user.UserService;


public class LiferayPlugin extends CordovaPlugin {

	private static final String TAG = "LIFERAY_PLUGIN";
	private static final String ACTION_CONNECT = "connect";
	private static final String GET_CONNECT = "execute";

	private static Session session;

	@Override
	public void initialize(CordovaInterface cordova, CordovaWebView webView) {
		super.initialize(cordova, webView);
	}

	public boolean execute(String action, JSONArray args,
			CallbackContext callbackContext) {

		Log.i(TAG, action);
		try {
			if (ACTION_CONNECT.equals(action)) {
				String serverIp = args.getString(0);
				String userName = args.getString(1);
				String password = args.getString(2);

				doConnect(callbackContext, serverIp, userName, password);
				return true;

			} else if(GET_CONNECT.equals(action)) {
				String classNameId = args.getString(0);
				getObjectModel(callbackContext, classNameId , args.getString(1), args.getJSONArray(2));
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			callbackContext.error("Params error");
			Log.e(TAG, "Params error");
			return false;
		}
	}

	private void getObjectModel(final CallbackContext callbackContext, String className, String methodName, JSONArray values) throws Exception{

		JSONArray jsonArrayInstance = new JSONArray();
		JSONObject jsonObjectInstance = new JSONObject();

		Callback callbackJSONArray = new JSONArrayCallback() {

			@Override
			public void onFailure(Exception exception) {
				callbackContext.error(exception.getMessage());
			}

			@Override
			public void onSuccess(JSONArray result) {
				// TODO Auto-generated method stub
				PluginResult pluginResult = new PluginResult(
						PluginResult.Status.OK, result);
				pluginResult.setKeepCallback(true);
				callbackContext.success(result);
			}
		};

		Callback callBackJSONObject = new JSONObjectCallback() {

			@Override
			public void onFailure(Exception arg0) {
				callbackContext.error(arg0.getMessage());
			}

			@Override
			public void onSuccess(JSONObject result) {
				// TODO Auto-generated method stub
				PluginResult pluginResult = new PluginResult(
						PluginResult.Status.OK, result);
				pluginResult.setKeepCallback(true);
				callbackContext.success(result);
			}

		};



		Method methodToExecute = null;
		Object[] params = null;
		BaseService service = getService(className);

		if(service == null){
			throw new LiferayPluginException("Service not implemented");
		}
		Method[] methods = service.getClass().getMethods();
		for(Method m: methods){
			if(m.getName().toLowerCase().equals(methodName.toLowerCase())){

				if(values.length() != m.getParameterTypes().length){
					throw new LiferayPluginException("Number of params error for the method " + methodName);
				}
				params = getListOfParam(m, values);
				if(m.getReturnType().isInstance(jsonArrayInstance)){
					session.setCallback(callbackJSONArray);
				}else if (m.getReturnType().isInstance(jsonObjectInstance)){
					session.setCallback(callBackJSONObject);
				}else if( m.getReturnType().equals(Void.TYPE)){
					callbackContext.success();
				}

				methodToExecute = m;
				break;
			}
		}
        if(methodToExecute == null) {
            for (Method m : methods) {
                if (methodName.indexOf(m.getName().toLowerCase()) >= 0) {

                    if (values.length() != m.getParameterTypes().length) {
                        throw new LiferayPluginException("Number of params error for the method " + methodName);
                    }
                    params = getListOfParam(m, values);
                    if (m.getReturnType().isInstance(jsonArrayInstance)) {
                        session.setCallback(callbackJSONArray);
                    } else if (m.getReturnType().isInstance(jsonObjectInstance)) {
                        session.setCallback(callBackJSONObject);
                    } else if (m.getReturnType().equals(Void.TYPE)) {
                        callbackContext.success();
                    }

                    methodToExecute = m;
                    break;
                }
            }
        }
		if(methodToExecute == null){
			throw new LiferayPluginException("Method " +methodName+ "not found");
		}

		try {
			methodToExecute.invoke(service, params);
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			throw new LiferayPluginException("Error invoking -- "+e.getMessage());
		}

	}



	private Object[] getListOfParam(Method m, JSONArray values) throws JSONException{
		List<Object> listOfParams = new ArrayList<Object>();
		for(int i=0; i< m.getParameterTypes().length; i++){
			@SuppressWarnings("rawtypes")
			Class c = m.getParameterTypes()[i];
			if(c.getName().equals("java.lang.String")){
				listOfParams.add(values.getString(i));
			}else if(c.getName().equals("java.lang.Long")){
				listOfParams.add(values.getLong(i));
			}else if(c.getName().equals("java.lang.Integer")){
				listOfParams.add(values.getInt(i));
			}else if(c.getName().equals("long")){
				listOfParams.add(values.getLong(i));
			}else if(c.getName().equals("int")){
				listOfParams.add(values.getInt(i));
			}else if(c.getName().equals("org.json.JSONObject")){
				listOfParams.add(values.getJSONObject(i));
			}
		}
		Object[] paramsA = new Object[listOfParams.size()];
		return listOfParams.toArray(paramsA);
	}

	private void doConnect(final CallbackContext callbackContext,
			final String urlServer, final String userName, final String password) {

		cordova.getThreadPool().execute(new Runnable() {
			public void run() {
				session = new SessionImpl(urlServer, new BasicAuthentication(userName, password));
				try {
					JSONObject user = getUser(session, userName);
					PluginResult pluginResult = new PluginResult(PluginResult.Status.OK, user);
					pluginResult.setKeepCallback(true);
					callbackContext.success(user);
				} catch (Exception e) {
					callbackContext.error(e.getMessage());

				}

			}
		});

	}


	protected JSONObject getUser(Session session, String username) throws Exception {
		UserService userService = new UserService(session);

		JSONObject user = userService.getUserByEmailAddress(preferences.getInteger("liferay-company-default", 20116), username);
		return user;
	}

	protected JSONObject getGuestGroupId(Session session) throws Exception {
		long groupId = -1;
		GroupService groupService = new GroupService(session);
		JSONArray groups = groupService.getUserSitesGroups();
		for (int i = 0; i < groups.length(); i++) {
			JSONObject group = groups.getJSONObject(i);
			String name = group.getString("name");
			if (!name.equals("Guest")) {
				continue;
			}
			return group;
		}
		if (groupId == -1) {
			throw new Exception("Couldn't find Guest group.");
		}
		return null;
	}

	private BaseService getService(String className) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

		// creating an object by getting Constructor object (with parameters) and calling newInstance (with parameters) on it
		Class<?> goatClass = Class.forName(className);

		Constructor constructor = goatClass.getConstructor(new Class[] {Session.class});

    BaseService service  = (BaseService) constructor.newInstance(new Object[] { session });

		return service;
	}

	public class LiferayPluginException extends Exception {

		/**
		 *
		 */
		private static final long serialVersionUID = 1L;

		private String message;

		public LiferayPluginException(String message){
			this.message = message;
		}

		public String getMessage() {
			return message;
		}

	}
}
