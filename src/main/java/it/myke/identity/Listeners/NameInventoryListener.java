package it.myke.identity.Listeners;

import it.myke.identity.utils.ConfigLoader;
import it.myke.identity.Identity;
import it.myke.identity.inventories.InventoryManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;

import static it.myke.identity.Identity.personHashMap;
import static it.myke.identity.inventories.Inventories.inventories;


public class NameInventoryListener implements Listener {
    private final Identity main;
    private final ArrayList<String> processStarted;
    public NameInventoryListener(Identity identity) {
        identity.getServer().getPluginManager().registerEvents(this, identity);
        main = identity;
        this.processStarted = new ArrayList<>();
    }


    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        if(e.getView().getTitle().equals(ConfigLoader.invName_Name) && personHashMap.get(e.getPlayer().getName()).getName() == null && !processStarted.contains(e.getPlayer().getName())) {
            main.getServer().getScheduler().runTaskLater(main, () -> e.getPlayer().openInventory(inventories.get("name")),1L);

        }
    }

    @EventHandler
    public void onInvClick(InventoryClickEvent e) {
        if (e.getView().getTitle().equals(ConfigLoader.invName_Name) && e.getClickedInventory() != null && e.getClickedInventory().getType() == InventoryType.CHEST) {
            if(e.getSlot() == 13) {

                processStarted.add(e.getWhoClicked().getName());
                e.getWhoClicked().closeInventory();
                e.getWhoClicked().sendMessage(ConfigLoader.message_HeadClicked_Name_ChatMessage);
                if(ConfigLoader.invName_titleBar_enabled) {
                    Player p = (Player) e.getWhoClicked();
                    p.sendTitle(ConfigLoader.message_title_Title, ConfigLoader.message_title_Subtitle);
                }
            }
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        if(processStarted.contains(event.getPlayer().getName())) {
            String name;

            if(ConfigLoader.invName_OnlyName_WithoutSurname) {
                name = event.getMessage().replace(" ", "");
                addName(event.getPlayer(), firstUppercase(name));
                //Opening nextInventory
                Bukkit.getScheduler().runTask(main, () -> new InventoryManager().openNextInventory(event.getPlayer(), main));
                processStarted.remove(event.getPlayer().getName());
            } else {
                if(event.getMessage().split(" ").length == 2) {
                    String[] surname_and_name = event.getMessage().split(" ");
                    addName(event.getPlayer(), firstUppercase(surname_and_name[0]) + " " + firstUppercase(surname_and_name[1]));
                    //Opening nextInventory
                    Bukkit.getScheduler().runTask(main, () -> new InventoryManager().openNextInventory(event.getPlayer(), main));
                    processStarted.remove(event.getPlayer().getName());
                } else {
                    event.getPlayer().sendMessage(ConfigLoader.message_surname_needed);
                }
            }
            event.setCancelled(true);

        }
    }


    public void addName(Player player, String name) {
        personHashMap.get(player.getName()).setName(name);
    }

    public String firstUppercase(String s) {
        char first = Character.toUpperCase(s.charAt(0));
        return first + s.substring(1);
    }



}
