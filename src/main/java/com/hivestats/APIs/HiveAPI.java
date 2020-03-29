package com.hivestats.APIs;

import net.minecraft.util.EnumChatFormatting;
import java.text.SimpleDateFormat;

public class HiveAPI extends MojangAPI {
    public final String api = "http://api.hivemc.com/v1/player/";
    private String UUID;
    private String Username;
    private String apiData;

    private String hiveRank;
    private int tokens;
    private int medals;
    private int crates;
    private String status;
    private boolean online;
    private String offlineFor;
    private String firstLogin;


    public HiveAPI(String player){
        super(player);
        this.UUID = getUUID();
        this.Username = getUSERNAME();
        try {
            apiData = JSONFetcher.readUrl(api + UUID);
        }
        catch (Exception e){
            apiData = null;
        }
        try{
            hiveRank = JSONFetcher.getValueWithID(JSONFetcher.getValueObject(apiData,"modernRank").toString(),"human");
            tokens = Integer.parseInt(JSONFetcher.getValueWithID(apiData,"tokens"));
            medals = Integer.parseInt(JSONFetcher.getValueWithID(apiData,"medals"));
            crates = Integer.parseInt(JSONFetcher.getValueWithID(apiData,"crates"));
            status = JSONFetcher.getValueWithID(JSONFetcher.getValueObject(apiData,"status").toString(),"description") +" "+
                    JSONFetcher.getValueWithID(JSONFetcher.getValueObject(apiData,"status").toString(),"game");
            SimpleDateFormat sdf = new SimpleDateFormat("MMMM d, yyyy 'at' h:mm a");
            firstLogin = sdf.format(Integer.parseInt(JSONFetcher.getValueWithID(apiData, "firstLogin"))*1000L);
            online = Integer.parseInt(JSONFetcher.getValueWithID(apiData, "lastLogout")) < Integer.parseInt(JSONFetcher.getValueWithID(apiData, "lastLogin"));
            long currentTime = System.currentTimeMillis();
            int seconds = (int)(currentTime / 1000);
            int interval = seconds- Integer.parseInt(JSONFetcher.getValueWithID(apiData, "lastLogout"));
            int weeks = interval / 604800;
            int days = (interval % 604800) / 86400;
            int hours = ((interval % 604800) % 86400) / 3600;
            int minutes = (((interval % 604800) % 86400) % 3600) / 60;
            interval = (((interval % 604800) % 86400) % 3600) % 60;
            offlineFor = "";
            if (weeks>0){
                if (weeks == 1)
                    offlineFor+= weeks + " week";
                else
                    offlineFor+= weeks + " weeks";
            }

            if (days>0) {
                if (offlineFor.contains("week")) {
                    if (days == 1)
                        offlineFor += ", " + days + " day";
                    else
                        offlineFor += ", " + hours + " days";
                }
                else{
                    if (days == 1)
                        offlineFor += days + " day";
                    else
                        offlineFor += days + " days";
                }
            }


            if (hours>0){
                if (offlineFor.contains("week") || offlineFor.contains("day")) {
                    if (hours == 1)
                        offlineFor += ", " + hours + " hour";
                    else
                        offlineFor += ", " + hours + " hours";
                }
                else{
                    if (hours == 1)
                        offlineFor += hours + " hour";
                    else
                        offlineFor += hours + " hours";
                }
            }
            if (minutes>0){
                if (offlineFor.contains("week") || offlineFor.contains("day") || offlineFor.contains("hour")) {
                    if (minutes == 1)
                        offlineFor += ", " + minutes + " minute";
                    else
                        offlineFor += ", " + minutes + " minutes";
                }
                else{
                    if (minutes == 1)
                        offlineFor += minutes + " minute";
                    else
                        offlineFor += minutes + " minutes";
                }
            }
            if (interval>0) {
                if (offlineFor.contains("week") || offlineFor.contains("day") || offlineFor.contains("hour") || offlineFor.contains("minute")) {
                    if (interval == 1)
                        offlineFor += ", " + interval + " second";
                    else
                        offlineFor += ", " + interval + " seconds";
                } else {
                    if (interval == 1)
                        offlineFor += interval + " second";
                    else
                        offlineFor += interval + " seconds";
                }
            }
        }
        catch (Exception e){

        }
    }

    public String getData(){
        return apiData;
    }


    public String seen(){
        if (UUID == null)
            return ("Username doesn't exist!");
        if (String.valueOf(offlineFor) == "null")
            return "The player has never played on The Hive!";
        if (online)
            return String.format(EnumChatFormatting.WHITE + "%s is online. " +"("+status+")", this.USERNAME).replace("online", EnumChatFormatting.GREEN + "online" + EnumChatFormatting.WHITE);
        else
            return String.format(EnumChatFormatting.WHITE + "%s has been offline for " + offlineFor+".", this.Username).replace("offline", EnumChatFormatting.RED + "offline" + EnumChatFormatting.WHITE);
    }

    public String hive(){
        if (UUID == null)
            return ("Username doesn't exist!");
        if (String.valueOf(offlineFor) == "null")
            return "The player has never played on The Hive!";
        return String.format(EnumChatFormatting.YELLOW + getUSERNAME()+ "'s Hive Data\n" +
                EnumChatFormatting.GOLD + "    [Hive Rank]: " + EnumChatFormatting.WHITE + "%s\n" +
                EnumChatFormatting.GOLD +"    [Tokens]: "+ EnumChatFormatting.WHITE + "%,d\n" +
                EnumChatFormatting.GOLD +"    [Medals]: "+ EnumChatFormatting.WHITE + "%,d\n" +
                EnumChatFormatting.GOLD +"    [Crates]: "+ EnumChatFormatting.WHITE + "%,d\n" +
                EnumChatFormatting.GOLD +"    [First Login]: " + EnumChatFormatting.WHITE +"%s\n" +
                EnumChatFormatting.GOLD +"    [Online]: " + EnumChatFormatting.WHITE +"%s\n" +
                EnumChatFormatting.GOLD +"    [Status]: "+ EnumChatFormatting.WHITE +"%s\n", hiveRank, tokens, medals, crates, firstLogin, String.valueOf(online).substring(0, 1).toUpperCase() + String.valueOf(online).substring(1), status);
    }

}
