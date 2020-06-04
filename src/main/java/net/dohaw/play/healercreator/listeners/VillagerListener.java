package net.dohaw.play.healercreator.listeners;

import net.dohaw.play.healercreator.HealerCreator;
import net.dohaw.play.healercreator.files.ConfigManager;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.mutable.entity.AgentData;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.Agent;
import org.spongepowered.api.entity.living.Villager;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.DamageEntityEvent;
import org.spongepowered.api.event.entity.HarvestEntityEvent;
import org.spongepowered.api.event.entity.TargetEntityEvent;
import org.spongepowered.api.event.entity.ai.SetAITargetEvent;
import org.spongepowered.api.event.entity.living.TargetAgentEvent;
import org.spongepowered.api.event.entity.living.TargetLivingEvent;
import org.spongepowered.api.text.Text;

public class VillagerListener {

    private HealerCreator plugin;
    private ConfigManager cm;

    public VillagerListener(HealerCreator plugin){
        this.plugin = plugin;
        this.cm = plugin.getConfigManager();
    }

    @Listener
    public void onVillagerTakeDamage(DamageEntityEvent e){
        if(e.getTargetEntity() instanceof Villager){
            Villager villager = (Villager) e.getTargetEntity();
            if(cm.getHealerLocation(villager.getUniqueId()) != null){
                e.setCancelled(true);
            }
        }
    }

    @Listener
    public void onTargetVillager(SetAITargetEvent e){
        Entity entityTarget = e.getTarget().get();
        if(entityTarget instanceof Villager){
            Villager villager = (Villager) entityTarget;
            if(cm.getHealerLocation(villager.getUniqueId()) != null){
                e.setCancelled(true);
            }
        }
    }

}
