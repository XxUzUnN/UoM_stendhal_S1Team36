package games.stendhal.server.entity.creature.impl.attack;

import games.stendhal.server.entity.RPEntity;
import games.stendhal.server.entity.creature.Creature;

public class Charming extends HandToHand{

    @Override
    public boolean canAttackNow(Creature attacker, RPEntity target) {
        if(target.isEquippedItemInSlot("lhand", "pipe") || target.isEquippedItemInSlot("rhand", "pipe")){
            return false;
        }
        return super.canAttackNow(attacker, target);
    }
}
