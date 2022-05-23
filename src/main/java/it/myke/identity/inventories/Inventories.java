package it.myke.identity.inventories;

import com.cryptomorin.xseries.XMaterial;
import it.myke.identity.utils.ConfigLoader;
import it.myke.identity.utils.CustomHeads;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Inventories {
    public static HashMap<String, Inventory> inventories;

    public Inventories() {
        inventories = new HashMap<>();
        if(ConfigLoader.name) inventories.put("name", getNameInventory());
        if(ConfigLoader.gender) inventories.put("gender", getGenderInventory());
        if(ConfigLoader.age) inventories.put("age", getAgeInventory());
    }


    public Inventory getNameInventory() {
        Inventory inventory = Bukkit.createInventory(null, 27, ConfigLoader.invName_Name);
        fillEmptyByRequested(inventory);
        inventory.setItem(13, CustomHeads.getSkullHead(ConfigLoader.invName_Head_Texture, ConfigLoader.invName_Head_Name, Collections.singletonList(ConfigLoader.invName_Head_Lore)));
        return inventory;

    }

    public Inventory getGenderInventory() {
        Inventory inventory = Bukkit.createInventory(null, 27, ConfigLoader.invGender_Name);
        fillEmptyByRequested(inventory);
        inventory.setItem(12, CustomHeads.getSkullHead(ConfigLoader.invGender_HeadFemale_Texture, ConfigLoader.invGender_HeadFemale_Name, Collections.singletonList(ConfigLoader.invGender_HeadFemale_Lore)));
        inventory.setItem(14, CustomHeads.getSkullHead(ConfigLoader.invGender_HeadMale_Texture, ConfigLoader.invGender_HeadMale_Name, Collections.singletonList(ConfigLoader.invGender_HeadMale_Lore)));
        return inventory;
    }

    public Inventory getAgeInventory() {
        Inventory inventory = Bukkit.createInventory(null, 27, ConfigLoader.invAge_Name);
        fillEmptyByRequested(inventory);
        inventory.setItem(12, CustomHeads.getSkullHead(ConfigLoader.invAge_HeadMinus_Texture, ConfigLoader.invAge_HeadMinus_Name, Collections.singletonList(ConfigLoader.invAge_HeadMinus_Lore)));
        inventory.setItem(13, XMaterial.BARRIER.parseItem());
        inventory.setItem(14, CustomHeads.getSkullHead(ConfigLoader.invAge_HeadPlus_Texture, ConfigLoader.invAge_HeadPlus_Name, Collections.singletonList(ConfigLoader.invAge_HeadPlus_Lore)));
        return inventory;
    }




    private void fillEmptyByRequested(Inventory inv) {
        for(int emptySlots : getEmptyItemSlots(inv)) {
            inv.setItem(emptySlots, getRandomGlassColor());
        }

    }

    public List<Integer> getEmptyItemSlots(Inventory inv) {
        List<Integer> emptySlots = new ArrayList<>();
        for(int i = 0; i < inv.getSize(); i++) {
            if(inv.getItem(i) == null) {
                emptySlots.add(i);
            }
        }
        return emptySlots;

    }

    public ItemStack getRandomGlassColor() {
        String[] rainbowColorIds = {"LIME", "RED", "YELLOW", "LIGHT_BLUE", "ORANGE"};
        int a = (int) (Math.random()*(rainbowColorIds.length-1+1)+1)-1;
        ItemStack glass = XMaterial.matchXMaterial(rainbowColorIds[a] + "_STAINED_GLASS_PANE").isPresent() ? XMaterial.matchXMaterial(rainbowColorIds[a] + "_STAINED_GLASS_PANE").get().parseItem() : XMaterial.BARRIER.parseItem();
        ItemMeta glassmeta = glass.getItemMeta();
        glassmeta.setDisplayName(" ");
        glass.setItemMeta(glassmeta);
        return glass;
    }










}
