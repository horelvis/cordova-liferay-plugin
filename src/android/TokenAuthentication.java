package org.cordova.liferay;

import com.liferay.mobile.android.auth.Authentication;
import com.liferay.mobile.android.http.Request;



/**
 * @author Bruno Farache
 */
public class TokenAuthentication implements Authentication {

    private String token = "";
    private String GOOOGLE_TOKEN = "GOOGLE-TOKEN";
    public TokenAuthentication(String token) {
        this.setToken(token);
    }

    @Override
    public void authenticate(Request request) {

        request.getHeaders().put( GOOOGLE_TOKEN, this.getToken());
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
