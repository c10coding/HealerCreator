package net.dohaw.play.healercreator.commands;

import net.dohaw.play.healercreator.HealerCreator;
import net.dohaw.play.healercreator.files.ConfigManager;
import net.dohaw.play.healercreator.utils.Chat;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.DataManipulator;
import org.spongepowered.api.data.manipulator.mutable.DisplayNameData;
import org.spongepowered.api.data.manipulator.mutable.entity.InvisibilityData;
import org.spongepowered.api.data.manipulator.mutable.entity.TargetedEntityData;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.ai.Goal;
import org.spongepowered.api.entity.ai.GoalTypes;
import org.spongepowered.api.entity.living.Agent;
import org.spongepowered.api.entity.living.ArmorStand;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.CauseStackManager;
import org.spongepowered.api.event.cause.EventContextKeys;
import org.spongepowered.api.event.cause.entity.spawn.SpawnTypes;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.Optional;

public class CreateCommand implements CommandSpecable {

    final String NAME_ARG = "Name for healer";
    private ConfigManager cm;
    private HealerCreator plugin;

    public CreateCommand(HealerCreator plugin){
        this.plugin = plugin;
        this.cm = plugin.getConfigManager();
    }

    @Override
    public CommandSpec base() {
        return CommandSpec.builder()
            .permission("healcreator.create")
            .arguments(GenericArguments.remainingJoinedStrings(Text.of(NAME_ARG)))
            .executor(this)
            .build();
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

        if(args.<String>getOne(NAME_ARG).isPresent()){
            String nameOfVillager = args.<String>getOne(Text.of(NAME_ARG)).get();
            if(src instanceof Player){
                if(!cm.isAHealer(nameOfVillager)){
                    Player p = (Player) src;
                    createVillager(p.getLocation(), EntityTypes.VILLAGER, nameOfVillager);
                }else{
                    src.sendMessage(Text.of("This is already a healer name!"));
                }
            }else{
                src.sendMessage(Chat.colorMsg("Only players can send this command!"));
            }
        }else{
            src.sendMessage(Chat.colorMsg("You forgot to put the name of the healer!"));
        }
        return CommandResult.success();
    }


    public ArmorStand createVillager(Location<World> playerLocation, EntityType entityType, String nameOfEntity) {
        World world = playerLocation.getExtent();

        Entity entity = world.createEntity(entityType, playerLocation.getPosition());

        try (CauseStackManager.StackFrame frame = Sponge.getCauseStackManager().pushCauseFrame()) {
            frame.addContext(EventContextKeys.SPAWN_TYPE, SpawnTypes.PLUGIN);
            world.spawnEntity(entity);
        }

        removeNormalGoal(entity);
        setVillagerData(entity, nameOfEntity);
        cm.addHealerToConfig(nameOfEntity, playerLocation, entity.getUniqueId());

        return null;
    }

    private void removeNormalGoal(Entity villager){
        Agent entityAgent = (Agent) villager;
        Optional<Goal<Agent>> normalGoal = entityAgent.getGoal(GoalTypes.NORMAL);
        normalGoal.ifPresent(Goal::clear);
    }

    private void setVillagerData(Entity villager, String healerName){
        villager.offer(Keys.AI_ENABLED, false);
        villager.offer(Keys.HAS_GRAVITY, false);
        villager.offer(Keys.DECAYABLE, false);
        villager.offer(Keys.INFINITE_DESPAWN_DELAY, true);
        villager.offer(Keys.VANISH_IGNORES_COLLISION, true);
        villager.offer(Keys.DISPLAY_NAME, Text.of(Chat.colorMsg("&7[&eWandering Healer&7] &r" + healerName)));
        villager.offer(Keys.CUSTOM_NAME_VISIBLE, true);
        villager.offer(Keys.INVULNERABLE, true);
        villager.offer(Keys.IS_SILENT, true);
        villager.offer(Keys.INFINITE_DESPAWN_DELAY, true);

    }

}
