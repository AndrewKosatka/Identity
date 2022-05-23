package it.myke.identity.Listeners;

import it.myke.identity.Identity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    public PlayerQuitListener(Identity identity) {
        identity.getServer().getPluginManager().registerEvents(this, identity);
    }


    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Identity.personHashMap.remove(event.getPlayer().getName());
    }


}
