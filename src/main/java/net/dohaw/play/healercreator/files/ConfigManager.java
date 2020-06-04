package net.dohaw.play.healercreator.files;

import net.dohaw.play.healercreator.HealerCreator;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.io.File;
import java.io.IOException;
import java.util.*;

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

    public void addHealerToConfig(String healerName, Location<World> healerLocation, UUID entityUUID){
        config.getNode("Healers", healerName, "Location", "World").setValue(healerLocation.getExtent().getName());
        config.getNode("Healers", healerName, "Location", "X").setValue(healerLocation.getBlockX());
        config.getNode("Healers", healerName, "Location", "Y").setValue(healerLocation.getBlockY());
        config.getNode("Healers", healerName, "Location", "Z").setValue(healerLocation.getBlockZ());
        config.getNode("Healers", healerName, "UUID").setValue(entityUUID.toString());
        saveConfig();
    }


    public List<Location<World>> getAllHealerLocations(){
        Map<Object,? extends CommentedConfigurationNode> section = config.getNode("Healers").getChildrenMap();
        List<Location<World>> healerLocations = new ArrayList<>();
        for(Object key : section.keySet()){
            healerLocations.add(getHealerLocation((String) key));
        }
        return healerLocations;
    }

    public Location<World> getHealerLocation(String healerName){
        World world = Sponge.getServer().getWorld(config.getNode("Healers", healerName, "Location", "World").getString()).get();
        int x = config.getNode("Healers", healerName, "Location", "X").getInt();
        int y = config.getNode("Healers", healerName, "Location", "Y").getInt();
        int z = config.getNode("Healers", healerName, "Location", "Z").getInt();
        return new Location(world, x, y, z);
    }

    public Location<World> getHealerLocation(UUID entityUUID){
        List<Location<World>> allHealerLocations = getAllHealerLocations();
        for(Location loc : allHealerLocations){
            Collection<Entity> nearbyEntities = loc.getExtent().getNearbyEntities(loc.getPosition(), 2);
            for(Entity e : nearbyEntities){
                if(e.getUniqueId().equals(entityUUID)){
                    return e.getLocation();
                }
            }
        }
        return null;
    }

    public boolean isAHealer(String healerName){
        return config.getNode("Healers", healerName).getValue() != null;
    }



}
