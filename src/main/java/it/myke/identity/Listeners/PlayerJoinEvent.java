package it.myke.identity.Listeners;

import it.myke.identity.utils.ConfigLoader;
import it.myke.identity.Identity;
import it.myke.identity.utils.Person;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import static it.myke.identity.inventories.Inventories.inventories;

public class PlayerJoinEvent implements Listener {
    private final Identity main;
    public PlayerJoinEvent(Identity identity) {
        identity.getServer().getPluginManager().registerEvents(this, identity);
        this.main = identity;
    }


    @EventHandler
    public void onPlayerJoin(org.bukkit.event.player.PlayerJoinEvent event) {
        if(getFirstInventoryToOpen() != null) {
            if (main.getConfig().getConfigurationSection("data") != null && !main.getConfig().getConfigurationSection("data").contains(event.getPlayer().getName())) {
                event.getPlayer().openInventory(inventories.get(getFirstInventoryToOpen()));
                Identity.personHashMap.put(event.getPlayer().getName(), new Person(null, null, 0));
            } else if(main.getConfig().getConfigurationSection("data") == null) {
                event.getPlayer().openInventory(inventories.get(getFirstInventoryToOpen()));
                Identity.personHashMap.put(event.getPlayer().getName(), new Person(null, null, 0));
            }
        }
    }


    public String getFirstInventoryToOpen() {
        if(ConfigLoader.name) {
            return "name";
        } else if(ConfigLoader.gender) {
            return "gender";
        } else if(ConfigLoader.age) {
            return "age";
        }
        return null;
    }








}
