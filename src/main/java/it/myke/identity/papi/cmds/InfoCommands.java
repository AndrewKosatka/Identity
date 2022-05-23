package it.myke.identity.papi.cmds;

import it.myke.identity.Identity;
import it.myke.identity.inventories.Inventories;
import it.myke.identity.utils.ConfigLoader;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class InfoCommands implements CommandExecutor {
    private final Identity main;
    public InfoCommands(Identity main) {
        main.getServer().getPluginCommand("identity").setExecutor(this);
        this.main = main;
    }


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
            if(command.getName().equals("identity")) {
                if (args.length == 0) {
                    sender.sendMessage(ConfigLoader.colorTranslate("&bPlug-in made by Drago903 &3(26/04/2022) &bin &31.16.5\n&bUse &3/identity papi &3for the Placeholder list."));
                } else if(args.length == 1) {
                    if(args[0].equalsIgnoreCase("papi") && hasPermissionWithMessage("identity.papi", sender)) {
                        sender.sendMessage(ConfigLoader.colorTranslate("\n&7&m-------------------\n&bAvailable placeholders\n&7&m-------------------\n&3- &b%identity_name%\n&3- &b%identity_surname%\n&3- &b%identity_fullname%\n&3- &b%identity_age%\n&3- &b%identity_gender%"));
                    }
                    if(args[0].equalsIgnoreCase("reload") && hasPermissionWithMessage("identity.reload", sender)) {
                        sender.sendMessage(ConfigLoader.colorTranslate("&b[!] Reloading Inventories & Config..."));
                        main.reloadConfig();
                        new ConfigLoader(main);
                        new Inventories();
                        sender.sendMessage(ConfigLoader.colorTranslate("&a[!] Reload complete!"));
                    }
                } else if(args.length == 2) {
                    if(args[0].equalsIgnoreCase("removePlayer") && sender.hasPermission("identity.removePlayer")) {
                        if(main.getConfig().isConfigurationSection("data") && main.getConfig().getConfigurationSection("data").contains(args[1])) {
                            main.getConfig().set("data." + args[1], null);
                            main.saveConfig();
                            main.reloadConfig();

                            sender.sendMessage(ConfigLoader.colorTranslate("&a(!) Player " + args[1] + " removed successfully!"));
                            if(Bukkit.getPlayer(args[1]) != null && Bukkit.getPlayer(args[1]).isOnline()) {
                                Bukkit.getPlayer(args[1]).kickPlayer(ConfigLoader.message_removedIdentityRejoin);
                            }
                        }
                    }
                }
            }

        return false;
    }



    private boolean hasPermissionWithMessage(String permission, CommandSender player) {
        if(player.hasPermission(permission)) {
            return true;
        } else {
            player.sendMessage(ConfigLoader.message_noPerms);
            return false;
        }



    }
}
