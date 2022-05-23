package it.myke.identity.Listeners;

import it.myke.identity.utils.ConfigLoader;
import it.myke.identity.Identity;
import it.myke.identity.inventories.InventoryManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;

import static it.myke.identity.Identity.personHashMap;
import static it.myke.identity.inventories.Inventories.inventories;

public class GenderInventoryListener implements Listener {
    private final Identity main;
    public GenderInventoryListener(Identity identity) {
        identity.getServer().getPluginManager().registerEvents(this, identity);
        main = identity;
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        if(e.getView().getTitle().equals(ConfigLoader.invGender_Name) && personHashMap.get(e.getPlayer().getName()).getGender() == null) {
            main.getServer().getScheduler().runTaskLater(main, () -> e.getPlayer().openInventory(inventories.get("gender")),1L);
        }
    }


    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if(e.getView().getTitle().equals(ConfigLoader.invGender_Name) && e.getClickedInventory() != null && e.getClickedInventory().getType() == InventoryType.CHEST) {
            Player p = (Player) e.getWhoClicked();
            InventoryManager invMng = new InventoryManager();
            switch (e.getSlot()) {
                case 12:
                    personHashMap.get(p.getName()).setGender("female");
                    p.sendMessage(ConfigLoader.message_femaleClicked);
                    invMng.openNextInventory(p, main);
                    break;
                case 14:
                    personHashMap.get(p.getName()).setGender("male");
                    p.sendMessage(ConfigLoader.message_maleClicked);
                    invMng.openNextInventory(p, main);
                    break;
            }
            e.setCancelled(true);
        }
    }




}
