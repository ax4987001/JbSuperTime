package org.ax4987.jbsupertime;

import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import java.io.File;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class PlayerData {
    public static final Map<String, PlayerData> DATA_MAP = new HashMap<>();
    private final String playerName;
    private static Map<String,Double> commands = new HashMap<>();

    public PlayerData(String playerName) {
        this.playerName = playerName;
    }
    public String getPlayerName() {
        return playerName;
    }
    public void setCommands(Map<String,Double> commands){
        PlayerData.commands = commands;
    }
    public Map<String,Double> getCommands(){
        return commands;
    }

    //数据载入
    public void load(File file) {
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        commands = (Map<String, Double>) config.getMapList("commands");
    }

    //保存数据文件
    public void save(File file) {
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        config.set("commands",commands);
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
