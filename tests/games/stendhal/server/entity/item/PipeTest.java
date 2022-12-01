package games.stendhal.server.entity.item;

import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.entity.creature.Creature;
import games.stendhal.server.entity.creature.impl.attack.Charming;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.MockStendlRPWorld;
import org.junit.BeforeClass;
import org.junit.Test;
import utilities.PlayerTestHelper;

import static org.junit.Assert.*;

public class PipeTest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        MockStendlRPWorld.get();
    }

    @Test
    public void testCreation() {
        final Item pipe = SingletonRepository.getEntityManager().getItem("pipe");
        assertNotNull("Generated item is null", pipe);
        assertEquals("You see a pipe. It prevents the player from being attacked by creatures.", pipe.getDescription());
        assertTrue("The pipe is not persistent", pipe.isPersistent());

    }

    //test when player doesn't have pipe
    @Test
    public void notCharmed() {
        final Player player = PlayerTestHelper.createPlayer("bob");
        final Creature creature = new Creature();
        player.put("id", "123456789");
        player.setHP(100);
        creature.setHP(50);
        creature.setTarget(player);
        assertFalse("Player can't be attacked", creature.getAttackStrategy() instanceof Charming);
    }

    //test when player has pipe on left hand or right hand
    @Test
    public void charmedByPipe() {
        final Player player = PlayerTestHelper.createPlayer("bob");
        final Player player2 = PlayerTestHelper.createPlayer("bob2");
        final Creature creature = new Creature();
        final Creature creature2 = new Creature();
        player.put("id", "123456789");
        player2.put("id", "123456789");
        player.setHP(100);
        player2.setHP(100);
        creature.setHP(50);
        creature2.setHP(50);
        creature.setTarget(player);
        creature2.setTarget(player2);
        player.getEquippedItemClass("lhand", "pipe");
        player2.getEquippedItemClass("rhand", "pipe");
        assertTrue("Player can be attacked when has pipe in left hand", creature.getAttackStrategy() instanceof Charming);
        assertTrue("Player can be attacked when has pipe in right hand", creature2.getAttackStrategy() instanceof Charming);
        player.drop("pipe");
        player2.drop("pipe");
        assertFalse("Player can't be attacked after removing the pipe", creature.getAttackStrategy() instanceof Charming);
        assertFalse("Player can't be attacked after removing the pipe", creature2.getAttackStrategy() instanceof Charming);
    }

}
