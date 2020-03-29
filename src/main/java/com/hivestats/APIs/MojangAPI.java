package com.hivestats.APIs;


public class MojangAPI {
    private static String api = "https://api.mojang.com/users/profiles/minecraft/";
    public String UUID;
    public String USERNAME;
    public MojangAPI(String username) {
        try {
            String data = JSONFetcher.readUrl(api + username);
            UUID = JSONFetcher.getValueWithID(data, "id");
            USERNAME = JSONFetcher.getValueWithID(data, "name");
        }
        catch (Exception e) {
            UUID = null;
            USERNAME = null;
        }
    }

    public String getUUID(){
        return UUID;
    }
    public String getUSERNAME(){
        return USERNAME;
    }
}
