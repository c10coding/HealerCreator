package net.dohaw.play.healercreator;

import com.google.inject.Inject;
import net.dohaw.play.healercreator.commands.Base;
import net.dohaw.play.healercreator.files.ConfigManager;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Text;

import java.io.File;

@Plugin(
        id = "healercreator",
        name = "HealerCreator",
        version = "1.0-SNAPSHOT"
)
public class HealerCreator {

    @Inject
    private Logger logger;

    @Inject @DefaultConfig(sharedRoot = true)
    private File configFile;

    @Inject @DefaultConfig(sharedRoot = true)
    ConfigurationLoader<CommentedConfigurationNode> configManager;

    private ConfigManager cm;

    @Listener
    public void onServerStart(GameStartedServerEvent event) {
        this.cm = new ConfigManager(configFile, configManager);
        cm.setup();
        registerCommand();
    }

    private void registerCommand(){
        Sponge.getCommandManager().register(this, new Base(this).base(), "healcreator", "hc", "healc");
    }

    public ConfigManager getConfigManager(){
        return cm;
    }



}
