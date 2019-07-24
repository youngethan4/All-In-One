package com.example.ethanman04.allone;

public class Endpoints {

    private static Endpoints instance;

    public static synchronized Endpoints getInstance() {
        if (instance == null){
            instance = new Endpoints();
        }
        return instance;
    }

    private Endpoints(){}

    private String url = "http://173.22.77.190:3000/api/";
    private String createUserEndpoint = url + "create/user";
    private String loginUserEndpoint = url + "login/user";

    final public String getCreateUserEndpoint() {
        return createUserEndpoint;
    }

    final public String getLoginUserEndpoint(){
        return loginUserEndpoint;
    }
}
