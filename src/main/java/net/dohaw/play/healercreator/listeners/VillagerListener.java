package net.dohaw.play.healercreator.listeners;

import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.ai.GoalType;
import org.spongepowered.api.entity.ai.GoalTypes;
import org.spongepowered.api.entity.living.Agent;
import org.spongepowered.api.entity.living.Villager;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.DamageEntityEvent;

public class VillagerListener {

    @Listener
    public void onVillagerTakeDamage(DamageEntityEvent e){
        if(e.getTargetEntity() instanceof Villager){
            Entity villager = e.getTargetEntity();
            Agent villagerAgent = (Agent) villager;
            if(!villagerAgent.getGoal(GoalTypes.NORMAL).isPresent()){
                e.setCancelled(true);
            }
        }
    }

}
