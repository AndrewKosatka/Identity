package it.myke.identity.papi;

import it.myke.identity.Identity;
import it.myke.identity.utils.ConfigLoader;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CustomNamesExpansion extends PlaceholderExpansion {
    private final Identity plugin;

    public CustomNamesExpansion(Identity identity) {
        this.plugin = identity;
    }


    @Override
    public @NotNull String getIdentifier() {
        return "Identity";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Drago903";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public boolean persist() {
        return true;
    }



    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        if (player != null && plugin.getConfig().getConfigurationSection("data") != null && plugin.getConfig().getConfigurationSection("data").contains(player.getName())) {
            switch (params) {
                case "name":
                    System.out.println("NAME");
                    return plugin.getConfig().getString("data." + player.getName() + ".name").split(" ")[0] != null ? plugin.getConfig().getString("data." + player.getName() + ".name").split(" ")[0] : "";
                case "surname":
                    return plugin.getConfig().getString("data." + player.getName() + ".name").split(" ")[1] != null ? plugin.getConfig().getString("data." + player.getName() + ".name").split(" ")[1] : "";
                case "fullname":
                    return plugin.getConfig().getString("data." + player.getName() + ".name") != null ? plugin.getConfig().getString("data." + player.getName() + ".name") : "";
                case "gender":
                    if (plugin.getConfig().getString("data." + player.getName() + ".gender").equals("male")) {
                        ConfigLoader.colorTranslate(plugin.getConfig().getString("placeholders.male"));
                    } else {
                        ConfigLoader.colorTranslate(plugin.getConfig().getString("placeholders.female"));
                    }
                    return plugin.getConfig().getString("data." + player.getName() + ".gender").equals("male") ? ConfigLoader.colorTranslate(plugin.getConfig().getString("placeholders.male")) : ConfigLoader.colorTranslate(plugin.getConfig().getString("placeholders.female"));
                case "age":
                    return plugin.getConfig().getString("data." + player.getName() + ".age") != null ? plugin.getConfig().getString("data." + player.getName() + ".age") : "";
            }
        }
        return "";
    }
}
