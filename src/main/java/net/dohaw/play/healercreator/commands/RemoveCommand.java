package net.dohaw.play.healercreator.commands;

import net.dohaw.play.healercreator.HealerCreator;
import net.dohaw.play.healercreator.files.ConfigManager;
import net.dohaw.play.healercreator.utils.Chat;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.*;

public class RemoveCommand implements CommandSpecable{

    private HealerCreator plugin;
    private ConfigManager cm;

    public RemoveCommand(HealerCreator plugin){
        this.plugin = plugin;
        this.cm = plugin.getConfigManager();
    }

    @Override
    public CommandSpec base() {
        return CommandSpec.builder()
                .permission("healercreator.remove")
                .arguments(GenericArguments.remainingJoinedStrings(Text.of("Healer name")))
                .executor(this)
                .build();
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) {
        if(src instanceof Player){
            if(args.<String>getOne(Text.of("Healer name")).isPresent()){
                String nameOfHealer = args.<String>getOne(Text.of("Healer name")).get();
                if(cm.isAHealer(nameOfHealer)){

                    Location<World> healerLocation = cm.getHealerLocation(nameOfHealer);
                    Optional<UUID> healerUUID = cm.getHealerUUID(nameOfHealer);
                    Optional<Entity> entity;

                    if(healerUUID.isPresent()){

                        entity = healerLocation.getExtent().getEntity(healerUUID.get());

                        if(entity.isPresent()){
                            entity.get().remove();
                            cm.removeHealerFromConfig(nameOfHealer);
                            src.sendMessage(Text.of("Deleted wandering healer " + nameOfHealer));
                        }else{
                            src.sendMessage(Chat.colorMsg("There was an error removing the healer from the plugin's memory. Please contact the plugin developer!"));
                        }

                    }else{
                        src.sendMessage(Chat.colorMsg("There has been an error trying to identify the healer. Please contact the plugin developer!"));
                        return CommandResult.success();
                    }

                }else{
                    src.sendMessage(Chat.colorMsg("This is not a valid healer! (Healer names are case-sensitive)"));
                }
            }

        }
        return CommandResult.success();
    }
}
