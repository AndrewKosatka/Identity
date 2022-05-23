package it.myke.identity.Listeners;

import com.cryptomorin.xseries.XSound;
import it.myke.identity.utils.ConfigLoader;
import it.myke.identity.utils.CustomHeads;
import it.myke.identity.Identity;
import it.myke.identity.inventories.InventoryManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;
import java.util.List;

import static it.myke.identity.Identity.personHashMap;
import static it.myke.identity.inventories.Inventories.inventories;

public class AgeInventoryListener implements Listener {
    private final Identity main;
    private int actualAge = ConfigLoader.min_age;
    private final int minAge = ConfigLoader.min_age, maxAge = ConfigLoader.max_age;

    public AgeInventoryListener(Identity identity) {
        main = identity;
        main.getServer().getPluginManager().registerEvents(this, identity);
    }


    @EventHandler
    public void onInvOpen(InventoryOpenEvent e) {
        if(e.getView().getTitle().equals(ConfigLoader.invAge_Name)) {
            ItemStack playerHead = new CustomHeads().getCustomOrPlayerHeadByRequested(main, e.getPlayer().getName());
            main.getServer().getScheduler().runTaskLater(main, () -> e.getPlayer().getOpenInventory().setItem(13, playerHead), 1L);
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        if(event.getView().getTitle().equals(ConfigLoader.invAge_Name) && personHashMap.containsKey(player.getName())) {
            main.getServer().getScheduler().runTaskLater(main, () -> player.openInventory(inventories.get("age")), 1L);
        }
    }


    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player p = (Player) event.getWhoClicked();
        if(event.getView().getTitle().equals(ConfigLoader.invAge_Name) && event.getClickedInventory() != null && event.getClickedInventory().getType() == InventoryType.CHEST) {
            switch (event.getSlot()) {
                case 12:
                    if(!(actualAge-1 < minAge)) {
                        actualAge--;
                    } else {
                        p.sendActionBar(ConfigLoader.message_minAgeReached);
                        XSound.play(p, "BLOCK_ANVIL_USE");
                    }
                        break;

                case 13:
                    personHashMap.get(p.getName()).setAge(actualAge);
                    InventoryManager invMng = new InventoryManager();
                    p.sendMessage(ConfigLoader.message_ageConfirmed);
                    invMng.openNextInventory(p,main);
                    break;
                case 14:
                    if(!(actualAge+1 > maxAge)) {
                        actualAge++;
                    } else {
                        p.sendActionBar(ConfigLoader.message_maxAgeReached);
                        XSound.play(p, "BLOCK_ANVIL_USE");
                    }
                    break;
            }
            if(event.getSlot() == 12 || event.getSlot() == 14) {
                String centralHead_name = ConfigLoader.colorTranslate(main.getConfig().getString("inventories.Age.central-head.name")).replace("%actualage%", String.valueOf(actualAge));
                List<String> centralHead_lore = Collections.singletonList(ConfigLoader.colorTranslate(main.getConfig().getString("inventories.Age.central-head.lore")).replace("%actualage%", String.valueOf(actualAge)));
                ItemStack playerHead = p.getOpenInventory().getItem(13);
                ItemMeta newMeta = playerHead.getItemMeta();
                newMeta.setDisplayName(centralHead_name);
                newMeta.setLore(centralHead_lore);
                playerHead.setItemMeta(newMeta);

            }
            event.setCancelled(true);
        }

    }




}
