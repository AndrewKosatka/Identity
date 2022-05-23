package it.myke.identity;

import it.myke.identity.Listeners.*;
import it.myke.identity.inventories.Inventories;
import it.myke.identity.papi.CustomNamesExpansion;
import it.myke.identity.papi.cmds.InfoCommands;
import it.myke.identity.utils.ConfigLoader;
import it.myke.identity.utils.Person;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public final class Identity extends JavaPlugin {
    public static HashMap<String, Person> personHashMap;

    @Override
    public void onEnable() {
        this.getLogger().info("Plugin started loading config...");
        this.saveDefaultConfig();
        new ConfigLoader(this);
        new Inventories();
        loadListeners();
        personHashMap = new HashMap<>();
        new InfoCommands(this);
        this.getLogger().info("Plugin loaded configs and listeners successfully");


        //Add PlaceHolderApi support
        if(Bukkit.getPluginManager().isPluginEnabled("PlaceHolderAPI")) {
            this.getLogger().info("PlaceholderAPI plugin detected. You can use our Expansion! (Name: identity)");
            this.getLogger().warning("Server reloads can break the Placeholders System! Restart only.");
            new CustomNamesExpansion(this).register();
        }

    }


    public void loadListeners() {
        new PlayerJoinEvent(this);
        new NameInventoryListener(this);
        new PlayerQuitListener(this);
        new GenderInventoryListener(this);
        new AgeInventoryListener(this);
    }




    @Override
    public void onDisable() {
        for(Player p : Bukkit.getOnlinePlayers()) p.closeInventory();
    }
}
