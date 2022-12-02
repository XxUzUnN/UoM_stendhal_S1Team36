package games.stendhal.server.entity.item;
import games.stendhal.server.entity.RPEntity;
import games.stendhal.server.entity.creature.Creature;
import games.stendhal.server.entity.player.Player;
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

    //player doesn't have pipe
    public boolean notCharmed(final RPEntity user) {
        if (user instanceof Player) {
            final Player player = (Player) user;
            if (player.has("pipe")) {
                return false;
            }
        }
        return true;
    }

    //player has pipe
    //make sure player can't be targeted by creatures
    public boolean charmedByPipe(final RPEntity user) {
        if (user instanceof Player) {
            final Player player = (Player) user;
            if (player.getEquippedItemClass("lhand", "pipe") != null) {
                //uncompleted
                return true;
            }
        }
        return false;
    }

}
