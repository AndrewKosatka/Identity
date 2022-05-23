package it.myke.identity.utils;

import it.myke.identity.Identity;
import org.bukkit.ChatColor;

public class ConfigLoader {
    public static boolean name,age,gender,invName_OnlyName_WithoutSurname,invName_titleBar_enabled;
    public static String message_stepCompleted, message_minAgeReached, message_maxAgeReached, message_ageConfirmed, message_removedIdentityRejoin, message_noPerms;
    public static int min_age,max_age;
    public static String invName_Name,invAge_Name,invGender_Name,invName_Head_Texture,invName_Head_Name,invName_Head_Lore, message_HeadClicked_Name_ChatMessage,message_title_Title,message_title_Subtitle,message_surname_needed;
    public static String invGender_HeadFemale_Texture, invGender_HeadFemale_Lore, invGender_HeadFemale_Name, invGender_HeadMale_Texture, invGender_HeadMale_Lore, invGender_HeadMale_Name, message_femaleClicked, message_maleClicked;
    public static String invAge_HeadMinus_Texture,invAge_HeadMinus_Lore,invAge_HeadMinus_Name,invAge_HeadPlus_Texture,invAge_HeadPlus_Lore,invAge_HeadPlus_Name;


    public ConfigLoader(Identity identity) {
        name = identity.getConfig().getBoolean("options.name");
        age = identity.getConfig().getBoolean("options.age");
        gender = identity.getConfig().getBoolean("options.gender");
        invName_Name = colorTranslate(identity.getConfig().getString("inventories.Name.inv-name"));
        invName_Head_Texture = identity.getConfig().getString("inventories.Name.central-head.texture");
        invName_Head_Lore = colorTranslate(identity.getConfig().getString("inventories.Name.central-head.lore"));
        invName_Head_Name = colorTranslate(identity.getConfig().getString("inventories.Name.central-head.name"));
        invName_OnlyName_WithoutSurname = identity.getConfig().getBoolean("inventories.Name.central-head.onClicked.onlyName");
        invName_OnlyName_WithoutSurname = identity.getConfig().getBoolean("inventories.Name.central-head.onClicked.onlyName");
        invName_titleBar_enabled = identity.getConfig().getBoolean("inventories.Name.central-head.onClicked.titleBar.enabled");


        invGender_Name = colorTranslate(identity.getConfig().getString("inventories.Gender.inv-name"));
        invGender_HeadFemale_Texture = identity.getConfig().getString("inventories.Gender.female-head.texture");
        invGender_HeadFemale_Lore = colorTranslate(identity.getConfig().getString("inventories.Gender.female-head.lore"));
        invGender_HeadFemale_Name = colorTranslate(identity.getConfig().getString("inventories.Gender.female-head.name"));
        invGender_HeadMale_Texture = identity.getConfig().getString("inventories.Gender.male-head.texture");
        invGender_HeadMale_Lore = colorTranslate(identity.getConfig().getString("inventories.Gender.male-head.lore"));
        invGender_HeadMale_Name = colorTranslate(identity.getConfig().getString("inventories.Gender.male-head.name"));

        invAge_Name = colorTranslate(identity.getConfig().getString("inventories.Age.inv-name"));
        invAge_HeadMinus_Texture = identity.getConfig().getString("inventories.Age.remove-head.texture");
        invAge_HeadMinus_Name = colorTranslate(identity.getConfig().getString("inventories.Age.remove-head.name"));
        invAge_HeadMinus_Lore = colorTranslate(identity.getConfig().getString("inventories.Age.remove-head.lore"));
        invAge_HeadPlus_Texture = identity.getConfig().getString("inventories.Age.add-head.texture");
        invAge_HeadPlus_Name = colorTranslate(identity.getConfig().getString("inventories.Age.add-head.name"));
        invAge_HeadPlus_Lore = colorTranslate(identity.getConfig().getString("inventories.Age.add-head.lore"));
        min_age = identity.getConfig().getInt("inventories.Age.options.min-age");
        max_age = identity.getConfig().getInt("inventories.Age.options.max-age");

        //Message -- Commands
        message_removedIdentityRejoin = colorTranslate(identity.getConfig().getString("messages.identity-reset-rejoin"));
        message_noPerms = colorTranslate(identity.getConfig().getString("messages.no-perms"));

        //Message -- Process
        message_stepCompleted = colorTranslate(identity.getConfig().getString("messages.process-finished"));

        //Message -- Age
        message_maxAgeReached = colorTranslate(identity.getConfig().getString("messages.max-age-reached"));
        message_minAgeReached = colorTranslate(identity.getConfig().getString("messages.min-age-reached"));
        message_ageConfirmed = colorTranslate(identity.getConfig().getString("messages.age-confirmed"));



        //Messages -- Name
        message_HeadClicked_Name_ChatMessage = colorTranslate(identity.getConfig().getString("inventories.Name.central-head.onClicked.chatMessage"));
        message_title_Title = colorTranslate(identity.getConfig().getString("inventories.Name.central-head.onClicked.titleBar.title"));
        message_title_Subtitle = colorTranslate(identity.getConfig().getString("inventories.Name.central-head.onClicked.titleBar.subtitle"));
        message_surname_needed = colorTranslate(identity.getConfig().getString("inventories.Name.central-head.onClicked.alsoSurnameIsNeededMessage"));

        //Messages -- Gender
        message_femaleClicked = colorTranslate(identity.getConfig().getString("inventories.Gender.female-head.message"));
        message_maleClicked = colorTranslate(identity.getConfig().getString("inventories.Gender.male-head.message"));



    }


    public static String colorTranslate(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }








}
