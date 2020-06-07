package net.dohaw.play.healercreator.commands;

import net.dohaw.play.healercreator.HealerCreator;
import net.dohaw.play.healercreator.files.ConfigManager;
import net.dohaw.play.healercreator.utils.Chat;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.Map;

public class ListHealersCommand implements CommandSpecable{

    private HealerCreator plugin;

    public ListHealersCommand(HealerCreator plugin){
        this.plugin = plugin;
    }

    @Override
    public CommandSpec base() {
        return CommandSpec.builder()
                .executor(this)
                .build();
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

        ConfigManager cm = plugin.getConfigManager();

        Map<Object, ? extends CommentedConfigurationNode> section = cm.getConfig().getNode("Healers").getChildrenMap();

        if(!section.keySet().isEmpty()){
            src.sendMessage(Chat.colorMsg("&7List of Healers: "));
            for(Object healerNameObj : section.keySet()){
                String healerName = (String) healerNameObj;
                Location<World> healerLocation = cm.getHealerLocation(healerName);
                src.sendMessage(Chat.colorMsg("&eHealer: " + healerName + " &f| Location: " + " -X: " + healerLocation.getBlockX() + " -Y: " + healerLocation.getBlockY() + " -Z: " + healerLocation.getBlockZ()));
            }
        }else{
            src.sendMessage(Chat.colorMsg("&cYou haven't created any healers yet!"));
        }


        return CommandResult.success();
    }
}
