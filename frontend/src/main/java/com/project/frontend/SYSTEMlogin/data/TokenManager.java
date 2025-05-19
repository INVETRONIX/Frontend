package com.project.frontend.SYSTEMlogin.data;

public class TokenManager {
    private static TokenManager instance;
    private static String token;

    private TokenManager(){}

    public static TokenManager getInstance(){
        if(instance == null){
            instance = new TokenManager();
        }
        return instance;
    }

    public void saveToken(String token){
        this.token = token;
    }

    public String getToken(){
        return token;
    }
    
}