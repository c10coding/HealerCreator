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
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

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

    /**
     * Callback for the execution of a command.
     *
     * @param src  The commander who is executing this command
     * @param args The parsed command arguments for this command
     * @return the result of executing this command
     * @throws CommandException If a user-facing error occurs while
     *                          executing this command
     */
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) {
        if(src instanceof Player){
            if(args.<String>getOne(Text.of("Healer name")).isPresent()){
                String nameOfHealer = args.<String>getOne(Text.of("Healer name")).get();
                if(cm.isAHealer(nameOfHealer)){

                }else{
                    src.sendMessage(Chat.colorMsg("This is not a valid healer! (Healer names are case-sensitive)"));
                }
            }

        }
        return null;
    }
}
