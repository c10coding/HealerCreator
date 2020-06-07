package net.dohaw.play.healercreator.commands;

import net.dohaw.play.healercreator.HealerCreator;
import net.dohaw.play.healercreator.utils.Chat;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.serializer.TextSerializers;

public class Base implements CommandSpecable {

    private HealerCreator plugin;

    public Base(HealerCreator plugin){
        this.plugin = plugin;
    }

    @Override
    public CommandSpec base(){
        return CommandSpec.builder()
                .permission("healercreator.base")
                .child(new CreateCommand(plugin).base(), "create")
                .child(new RemoveCommand(plugin).base(), "remove")
                .child(new ListHealersCommand(plugin).base(), "list")
                .executor(this)
                .build();
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) {
        if(src instanceof Player){
            src.sendMessage(Chat.colorMsg("I think you mean't to do &6/hc create"));
        }else{
            src.sendMessage(Chat.colorMsg("Only players can use this command!"));
        }
        return CommandResult.success();
    }
}
