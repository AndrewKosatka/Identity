package it.myke.identity.inventories;

import com.cryptomorin.xseries.XSound;
import it.myke.identity.utils.ConfigLoader;
import it.myke.identity.Identity;
import it.myke.identity.utils.Person;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import static it.myke.identity.Identity.personHashMap;
import static it.myke.identity.inventories.Inventories.inventories;

public class InventoryManager {

    public Inventory getNextInventory(Player player) {
        if(personHashMap.containsKey(player.getName())) {
            Person playerIdentity = personHashMap.get(player.getName());
            if(playerIdentity.getName() == null && ConfigLoader.name) {
                return inventories.get("name");
            } else if(playerIdentity.getGender() == null && ConfigLoader.gender) {
                return inventories.get("gender");
            } else if(playerIdentity.getAge() == 0 && ConfigLoader.age) {
                return inventories.get("age");
            }
        }
        return null;
    }


    /**
     * This is needed because you can decide to disable a feature of this plugin, so that become necessary
     * to get the new step automatically.
     * @param player Player to execute the openInventory action
     * @param main Main class
     */

    public void openNextInventory(Player player, Identity main) {
        Inventory nxtInventory = getNextInventory(player);
        if(nxtInventory == null) {
            player.sendMessage(ConfigLoader.message_stepCompleted);
            saveInConfig(player.getName(), main);
            personHashMap.remove(player.getName());
            player.closeInventory();
            XSound.play(player, "ENTITY_EXPERIENCE_ORB_PICKUP");
        } else {
            player.closeInventory();
            player.openInventory(nxtInventory);
            XSound.play(player, "BLOCK_NOTE_BLOCK_PLING");
        }
    }


    /**
     * Saving data into config by Person class (Schema) objects
     * @param playerName Player name
     * @param main Main
     */
    public void saveInConfig(String playerName, Identity main) {
        Person person = personHashMap.get(playerName);
        if(person.name != null) main.getConfig().set("data." + playerName + ".name", person.getName());
        if(person.gender != null) main.getConfig().set("data." + playerName + ".gender", person.getGender());
        if(person.age != 0) main.getConfig().set("data." + playerName + ".age", person.getAge());
        main.saveConfig();
    }

}
