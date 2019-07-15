package com.example.ethanman04.allone;

public class Endpoints {

    private static Endpoints instance = new Endpoints();

    public static Endpoints getInstance() {
        return instance;
    }

    private String url = "http://173.22.77.190:3000/";
    private String createUserEndpoint = url + "api/create";

    final public String getCreateUserEndpoint() {
        return createUserEndpoint;
    }
}
