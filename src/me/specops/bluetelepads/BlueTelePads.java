package me.specops.bluetelepads;

import org.bukkit.util.config.Configuration;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.PluginManager;
import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;
import org.bukkit.plugin.Plugin;

public class BlueTelePads extends JavaPlugin{
    private final BlueTelePadsPlayerListener playerListener = new BlueTelePadsPlayerListener(this);
    
    public static PermissionHandler Permissions;

    public int MAX_DISTANCE = 0;
    public boolean USE_PERMISSIONS = false;
    public boolean OP_ONLY = false;
    public boolean DISABLE_TELEPORT_WAIT = false;
    public boolean DISABLE_TELEPORT_MESSAGE = false;
    public int TELEPAD_CENTER_ID = 22;
    public int TELEPAD_SURROUNDING_ID = 43;



    public void onDisable(){
        System.out.println("[BlueTelePads] Disabled");
    }

    public void onEnable(){
        Configuration config = getConfiguration();

        //set default values if necessary
        if(config.getInt("max_telepad_distance",-1) == -1){
            System.out.println("[BlueTelepads] Creating default config file...");

            config.setProperty("max_telepad_distance",MAX_DISTANCE);
            config.setProperty("use_permissions",USE_PERMISSIONS);
            config.setProperty("op_only",OP_ONLY);
            config.setProperty("disable_teleport_wait",DISABLE_TELEPORT_WAIT);
            config.setProperty("disable_teleport_message", DISABLE_TELEPORT_MESSAGE);
            config.setProperty("telepad_center",TELEPAD_CENTER_ID);
            config.setProperty("telepad_surrounding",TELEPAD_SURROUNDING_ID);

            config.save();
        }

        MAX_DISTANCE = config.getInt("max_telepad_distance",MAX_DISTANCE);
        USE_PERMISSIONS = config.getBoolean("use_permissions",USE_PERMISSIONS);
        OP_ONLY = config.getBoolean("op_only",OP_ONLY);
        DISABLE_TELEPORT_WAIT = config.getBoolean("disable_teleport_wait",DISABLE_TELEPORT_WAIT);
        DISABLE_TELEPORT_MESSAGE = config.getBoolean("disable_teleport_message",DISABLE_TELEPORT_MESSAGE);
        TELEPAD_CENTER_ID = config.getInt("telepad_center",TELEPAD_CENTER_ID);
        TELEPAD_SURROUNDING_ID = config.getInt("telepad_surrounding",TELEPAD_SURROUNDING_ID);

        if(USE_PERMISSIONS){
            Plugin perm = this.getServer().getPluginManager().getPlugin("Permissions");
            if(perm != null){
                System.out.println("[BlueTelePads] Permissions integration enabled");
                this.Permissions = ((Permissions) perm).getHandler();
            }else{
                System.out.println("[BlueTelePads] Permissions integration could not be enabled!");
            }
        }

        PluginManager pm = getServer().getPluginManager();
        pm.registerEvent(Event.Type.PLAYER_INTERACT, playerListener, Priority.Normal, this);

        PluginDescriptionFile pdfFile = this.getDescription();
        System.out.println("["+pdfFile.getName() + "] version " + pdfFile.getVersion() + " ENABLED" );
    }
}