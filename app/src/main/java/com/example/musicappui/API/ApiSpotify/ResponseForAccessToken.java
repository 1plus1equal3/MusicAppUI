package com.example.musicappui.API.ApiSpotify;

public class ResponseForAccessToken {
    private final String access_token;
    private final String token_type;

    public ResponseForAccessToken(String access_token, String token_type) {
        this.access_token = access_token;
        this.token_type = token_type;
    }

    public String getAccess_token() {
        return access_token;
    }

    public String getToken_type() {
        return token_type;
    }

}
