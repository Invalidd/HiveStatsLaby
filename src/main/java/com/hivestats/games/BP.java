package com.hivestats.games;


import com.hivestats.APIs.JSONFetcher;
import com.hivestats.APIs.MojangAPI;
import net.minecraft.util.EnumChatFormatting;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class BP extends MojangAPI {

    private String api = "http://thehivestats.herokuapp.com/";

    private String bpData;

    private String rank;
    private String next_rank;
    private int points;
    private int next_rank_points;
    private int games;
    private int eliminations;
    private int placings;
    private int wins;
    private double win_loss;
    private double win_rate;
    private double points_per_game;
    private double placing_rate;
    private int leaderboard;
    private String period;

    private int top_player_points;
    private HashMap<Integer, String> RANKS = new HashMap<Integer, String>() {{
        put(0, EnumChatFormatting.GRAY + "First Step");
        put(100, EnumChatFormatting.GOLD + "Party Animal");
        put(500, EnumChatFormatting.DARK_AQUA +"Ballerina");
        put(1000, EnumChatFormatting.LIGHT_PURPLE +"Raver");
        put(2500, EnumChatFormatting.AQUA +"Freestyler");
        put(5000,EnumChatFormatting.DARK_PURPLE +"Breakdancer");
        put(10000,EnumChatFormatting.YELLOW +"Star");
        put(20000, EnumChatFormatting.GREEN + "MC Hammer");
        put(35000, EnumChatFormatting.RED +"Carlton");
        put(50000, EnumChatFormatting.BLUE +"Destroyer");
        put(75000,EnumChatFormatting.LIGHT_PURPLE + "Famous");
        put(100000,EnumChatFormatting.DARK_PURPLE +"Dominator");
        put(150000, EnumChatFormatting.DARK_AQUA +"Fabulous");
        put(200000,String.format("%s%sKing of Dance",EnumChatFormatting.BOLD,EnumChatFormatting.GOLD));
        put(300000,String.format("%s%sChoreographer",EnumChatFormatting.BOLD,EnumChatFormatting.AQUA));
        put(400000,String.format("%s%sHappy Feet",EnumChatFormatting.BOLD,EnumChatFormatting.RED));
        put(500000,String.format("%s%sJackson",EnumChatFormatting.BOLD,EnumChatFormatting.YELLOW));
        put(625000,String.format("%s%sAstaire",EnumChatFormatting.BOLD,EnumChatFormatting.BLUE));
        put(750000,String.format("%s%sSwayze",EnumChatFormatting.BOLD,EnumChatFormatting.GREEN));
        put(1000000,String.format("%s%sLegendary",EnumChatFormatting.BOLD,EnumChatFormatting.DARK_PURPLE));
    }};

    public BP(String player, String period) {
        super(player);
        this.period = period;
        api += period + "/?uuid=" + getUUID();
        try {
           bpData = JSONFetcher.readUrl(this.api);
        }
        catch (Exception e){
            bpData = null;
        }
        try {
            points = Integer.parseInt(JSONFetcher.getValueWithID(bpData,"total_points"));
            games = Integer.parseInt(JSONFetcher.getValueWithID(bpData,"games_played"));
            eliminations = Integer.parseInt(JSONFetcher.getValueWithID(bpData,"total_eliminations"));
            placings = Integer.parseInt(JSONFetcher.getValueWithID(bpData,"total_placing"));
            wins = Integer.parseInt(JSONFetcher.getValueWithID(bpData,"victories"));
            DecimalFormat df = new DecimalFormat("##.##");
            win_loss = Double.parseDouble(df.format( (double)wins/(games-wins)));
            win_rate = Double.parseDouble(df.format(Double.parseDouble(df.format(Double.parseDouble(JSONFetcher.getValueWithID(bpData, "win_rate"))*100))));
            points_per_game = Double.parseDouble(df.format(Double.parseDouble(df.format(Double.parseDouble(JSONFetcher.getValueWithID(bpData, "points_per_game"))))));
            placing_rate = Double.parseDouble(df.format(Double.parseDouble(df.format(Double.parseDouble(JSONFetcher.getValueWithID(bpData, "placing_rate"))*100))));
            leaderboard = Integer.parseInt(JSONFetcher.getValueWithID(bpData, "position"));


            top_player_points = topPoint();
            RANKS.put(top_player_points,String.format("%s%sBilly Elliot",EnumChatFormatting.BOLD,EnumChatFormatting.LIGHT_PURPLE));

            next_rank_points = nextRankPoints() - points;

        }
        catch (Exception e){

        }
    }

    private int nextRankPoints() {
        if (points == top_player_points) {
            next_rank = "Billy Elliot";
            rank = RANKS.get(points);
            return top_player_points;
        }
        ArrayList<Integer> keys = new ArrayList<Integer>(RANKS.keySet());
        Collections.sort(keys);
        for(int i=0; i<keys.size();i++){
            if (points < keys.get(i)) {
                next_rank = RANKS.get(keys.get(i));
                rank = RANKS.get(keys.get(i-1));
                return keys.get(i);
            }
        }
        return -1;
    }

    private int topPoint(){
        try{
            String top_player_data = JSONFetcher.readUrl("http://api.hivemc.com/v1/game/BP/leaderboard/0/1").replace("[","").replace("]","");
            String top_player_game_data = JSONFetcher.getValueObject(top_player_data,"leaderboard").toString();
            return Integer.parseInt(JSONFetcher.getValueWithID(top_player_game_data,"total_points"));
        }
        catch (Exception E){
            return -1;
        }
    }



    public String getGameData(){
        return bpData;
    }

    public String stats() {
        if (UUID == null)
            return ("Username doesn't exist!");
        if (bpData == null)
            return ("The player doesn't have " + period + "stats!");
        if (String.valueOf(rank) == "null" && period == "all")
            return "The player does not have block party stats!";
        if (period == "all") {
            return String.format(EnumChatFormatting.YELLOW + getUSERNAME() + "'s BlockParty Stats\n" +
                    EnumChatFormatting.GOLD + "    [Rank]: %s" + EnumChatFormatting.WHITE + " (%,d points to %s" + EnumChatFormatting.WHITE + ")\n" +
                    EnumChatFormatting.GOLD + "    [Leaderboard]: " + EnumChatFormatting.WHITE + "%,d\n" +
                    EnumChatFormatting.GOLD + "    [Points]: " + EnumChatFormatting.WHITE + "%,d\n" +
                    EnumChatFormatting.GOLD + "    [Games]: " + EnumChatFormatting.WHITE + "%,d\n" +
                    EnumChatFormatting.GOLD + "    [Wins]: " + EnumChatFormatting.WHITE + "%,d\n" +
                    EnumChatFormatting.GOLD + "    [Eliminations]: " + EnumChatFormatting.WHITE + "%,d\n" +
                    EnumChatFormatting.GOLD + "    [Points Per Game]: " + EnumChatFormatting.WHITE + "%s\n" +
                    EnumChatFormatting.GOLD + "    [Placings]: " + EnumChatFormatting.WHITE + "%,d\n" +
                    EnumChatFormatting.GOLD + "    [W/L]: " + EnumChatFormatting.WHITE + "%s\n" +
                    EnumChatFormatting.GOLD + "    [Win Rate]: " + EnumChatFormatting.WHITE + "%s\n" +
                    EnumChatFormatting.GOLD + "    [Placing Rate]: " + EnumChatFormatting.WHITE + "%s\n", rank, next_rank_points, next_rank, leaderboard, points, games, wins, eliminations, String.valueOf(points_per_game), placings, String.valueOf(win_loss), String.valueOf(win_rate) + "%", String.valueOf(placing_rate) + "%");
        }
        else
            return String.format(EnumChatFormatting.YELLOW + getUSERNAME() + "'s " + period.toLowerCase()+ " BlockParty Stats\n" +
                    EnumChatFormatting.GOLD + "    [Leaderboard]: " + EnumChatFormatting.WHITE + "%,d\n" +
                    EnumChatFormatting.GOLD + "    [Points]: " + EnumChatFormatting.WHITE + "%,d\n" +
                    EnumChatFormatting.GOLD + "    [Games]: " + EnumChatFormatting.WHITE + "%,d\n" +
                    EnumChatFormatting.GOLD + "    [Wins]: " + EnumChatFormatting.WHITE + "%,d\n" +
                    EnumChatFormatting.GOLD + "    [Eliminations]: " + EnumChatFormatting.WHITE + "%,d\n" +
                    EnumChatFormatting.GOLD + "    [Points Per Game]: " + EnumChatFormatting.WHITE + "%s\n" +
                    EnumChatFormatting.GOLD + "    [Placings]: " + EnumChatFormatting.WHITE + "%,d\n" +
                    EnumChatFormatting.GOLD + "    [W/L]: " + EnumChatFormatting.WHITE + "%s\n" +
                    EnumChatFormatting.GOLD + "    [Win Rate]: " + EnumChatFormatting.WHITE + "%s\n" +
                    EnumChatFormatting.GOLD + "    [Placing Rate]: " + EnumChatFormatting.WHITE + "%s\n", leaderboard, points, games, wins, eliminations, String.valueOf(points_per_game), placings, String.valueOf(win_loss), String.valueOf(win_rate) + "%", String.valueOf(placing_rate) + "%");

    }
}
