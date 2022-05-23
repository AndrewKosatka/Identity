package it.myke.identity.utils;

import java.lang.reflect.Field;

import it.myke.identity.Identity;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.GameProfile;

import java.nio.charset.StandardCharsets;
import java.util.*;

import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class CustomHeads {



        public static ItemStack getSkullHead(final String val, final String name, final List<String> lore) {
                final ItemStack head = new ItemStack(Material.LEGACY_SKULL_ITEM, 1, (short) 3);
                if (!val.isEmpty()) {
                    final SkullMeta skullMeta = (SkullMeta) head.getItemMeta();
                    final GameProfile gameProfile = new GameProfile(UUID.randomUUID(), null);
                    gameProfile.getProperties().put("textures", new Property("textures", translateValue(val)));
                    try {
                        final Field profileField = skullMeta.getClass().getDeclaredField("profile");
                        profileField.setAccessible(true);
                        profileField.set(skullMeta, gameProfile);
                    } catch (IllegalArgumentException | NoSuchFieldException | SecurityException | IllegalAccessException ex3) {
                        ex3.printStackTrace();
                    }
                    skullMeta.setDisplayName(name);
                    skullMeta.setLore(lore);
                    head.setItemMeta(skullMeta);
                }
                return head;
            }

        //In the age section, I wanted to implement the possibility to get the "player-head" in the center instead of the custom head if wanted
        //this method allow to get the player head IF REQUESTED
        public ItemStack getCustomOrPlayerHeadByRequested(Identity main, String playerName) {
            String centralHead_name = ConfigLoader.colorTranslate(main.getConfig().getString("inventories.Age.central-head.name")).replace("%actualage%", String.valueOf(ConfigLoader.min_age));
            List<String> centralHead_lore = Collections.singletonList(ConfigLoader.colorTranslate(main.getConfig().getString("inventories.Age.central-head.lore")).replace("%actualage%", String.valueOf(ConfigLoader.min_age)));
            if(Objects.equals(main.getConfig().getString("inventories.Age.central-head.texture"), "%playerhead%")) {
                ItemStack item = new ItemStack(Material.LEGACY_SKULL_ITEM, 1 , (short) 3);
                SkullMeta meta = (SkullMeta) item.getItemMeta();
                meta.setOwner(playerName);
                meta.setDisplayName(centralHead_name);
                meta.setLore(centralHead_lore);
                item.setItemMeta(meta);
                return item;
            } else {
                return getSkullHead(main.getConfig().getString("inventories.Age.central-head.texture"), centralHead_name, centralHead_lore);
            }
        }



        public static String translateValue(String val) {
            return Base64.getEncoder().encodeToString(("{\"textures\":{\"SKIN\":{\"url\":\"http://textures.minecraft.net/texture/"+val+"\"}}}").getBytes(StandardCharsets.UTF_8));
        }
}
