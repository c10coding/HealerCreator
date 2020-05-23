package net.dohaw.play.healercreator.commands;

import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;

public interface CommandSpecable extends CommandExecutor {

    CommandSpec base();

}
