package games.stendhal.server.entity.item;
import games.stendhal.server.entity.RPEntity;
import games.stendhal.server.entity.creature.Creature;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Pipe extends Item {

    public Pipe(final String name, final String clazz, final String subclass,
                final Map<String, String> attributes) {
        super(name, clazz, subclass, attributes);

        setPersistent(true);
    }

    public Pipe(final Pipe pipe) {
        super(pipe);
    }

    @Override
    public String describe() {
        return "You see a pipe. It prevents the player from being attacked by creatures.";
    }

    @Override
    public String getName() {
        return "pipe";
    }

    //add the attribute "charming" to the creature when the player has pipe
    private RPEntity equipper;
    @Override
    public boolean onEquipped(RPEntity equipper, String slot) {
        if (slot.equals("lhand") || slot.equals("rhand")) {
            List<RPEntity> attackers = equipper.getAttackingRPEntities();
            for (RPEntity attacker : attackers) {
                if (attacker instanceof Creature) {
                    Map<String, String> aiProfiles = new HashMap<String, String>(((Creature) attacker).getAIProfiles());
                    if (!aiProfiles.containsKey("charming")) {
                        aiProfiles.put("charming", "");
                    }
                    ((Creature) attacker).setAIProfiles(aiProfiles);
                }
            }
            this.equipper = equipper;
            return true;
        }
        return false;
    }

    //remove the attribute "charming" to the creature when the player doesn't have pipe
    @Override
    public boolean onUnequipped() {
        if (equipper != null) {
            List<RPEntity> attackers = this.equipper.getAttackingRPEntities();
            for (RPEntity attacker : attackers) {
                if (attacker instanceof Creature) {
                    Map<String, String> aiProfiles = new HashMap<String, String>(((Creature) attacker).getAIProfiles());
                    if (aiProfiles.containsKey("charming")) {
                        aiProfiles.remove("charming", "");
                    }
                    ((Creature) attacker).setAIProfiles(aiProfiles);
                }
            }
            this.equipper = null;
            return true;
        }
        return false;
    }
}
