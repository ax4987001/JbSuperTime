package org.ax4987.jbsupertime;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
public class MyPlaceholderExpansion extends PlaceholderExpansion {
    @Override
    public boolean canRegister() {
        return true;
    }
    @Override
    public String getIdentifier() {
        return "myplugin";
    }
    @Override
    public String getAuthor() {
        return "ax4987";
    }
    @Override
    public String getVersion() {
        return "1.0.0";
    }
    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        for (String s : JbSuperTime.time_group_name){
            if (identifier.equals(s)) {
                return JbSuperTime.timer_group_timer.get(s);
            }
        }

        return null;
    }
}