package org.ax4987.jbsupertime;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import java.io.File;

public class PlayerListener extends JbSuperTime implements Listener {
    //玩家进入后再data创建  玩家名.yml
    @EventHandler(priority = EventPriority.HIGHEST)
    public void join(PlayerJoinEvent e){
        PlayerData data = PlayerData.DATA_MAP.getOrDefault(e.getPlayer().getName(),new PlayerData(e.getPlayer().getName()));
        File file = new File(new JbSuperTime().getDataFolder() + "/data" , e.getPlayer().getName() + ".yml");
        if (file.exists()){
            data.load(file);
        }
    }
    //玩家退出后保存数据,如果没有数据创建后再保存
    @EventHandler(priority = EventPriority.LOWEST)
    public void quit(PlayerQuitEvent e){
        PlayerData data = PlayerData.DATA_MAP.getOrDefault(e.getPlayer().getName(),new PlayerData(e.getPlayer().getName()));
        File file = new File(new JbSuperTime().getDataFolder() + "/data" , e.getPlayer().getName() + ".yml");
        data.save(file);
        PlayerData.DATA_MAP.remove(e.getPlayer().getName());
    }
}
