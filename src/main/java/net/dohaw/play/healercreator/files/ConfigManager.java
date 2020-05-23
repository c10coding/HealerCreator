package net.dohaw.play.healercreator.files;

import net.dohaw.play.healercreator.HealerCreator;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.io.File;
import java.io.IOException;

public class ConfigManager {

    private ConfigurationLoader<CommentedConfigurationNode> configLoader;
    private CommentedConfigurationNode config;
    private File configFile;

    public ConfigManager(File configFile, ConfigurationLoader<CommentedConfigurationNode> configLoader){
        this.configFile = configFile;
        this.configLoader = configLoader;
    }

    public void setup(){

        if(!configFile.exists()){
            try {
                configFile.createNewFile();
                loadConfig();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            loadConfig();
        }

    }

    public CommentedConfigurationNode getConfig(){
        return config;
    }

    public void saveConfig(){
        try{
            configLoader.save(config);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void loadConfig(){
        try {
            config = configLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addHealerToConfig(String healerName, Location<World> healerLocation){
        config.getNode("Villager", healerName, "Location", "World").setValue(healerLocation.getExtent().getName());
        config.getNode("Villager", healerName, "Location", "X").setValue(healerLocation.getBlockX());
        config.getNode("Villager", healerName, "Location", "Y").setValue(healerLocation.getBlockY());
        config.getNode("Villager", healerName, "Location", "Z").setValue(healerLocation.getBlockZ());
        saveConfig();
    }

    public boolean isAHealer(String healerName){
        return config.getNode("Villager", healerName).getValue() != null;
    }



}
