package games.stendhal.server.maps.deniran.cityinterior.weaponsshop;

import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.player.Player;
import org.junit.Test;
import utilities.PlayerTestHelper;
import utilities.ZonePlayerAndNPCTestImpl;

import static org.junit.Assert.*;
import static utilities.SpeakerNPCTestHelper.getReply;


public class PipeDealerNPCTest extends ZonePlayerAndNPCTestImpl {

    private static final String ZONE_NAME = "testzone";

    private static final String npcName = "Pippa";

    public PipeDealerNPCTest() {
        setNpcNames(npcName);
        setZoneForPlayer(ZONE_NAME);
        addZoneConfigurator(new PipeDealerNPC(), ZONE_NAME);
    }


    /**
     * Tests for PipeDealerNPC.
     */
    @Test
    public void testPipeDealerNpc(){
        final SpeakerNPC npc = getNPC(npcName);
        final Player player = PlayerTestHelper.createPlayer("bob");
        assertEquals("You see Pippa, the pipe dealer.", npc.getDescription());

        npc.getEngine().step(player, "hi");
        assertEquals("Hello! Would you like to know something about my #job?", getReply(npc));

        npc.getEngine().step(player, "job");
        assertEquals("Hush, just to tell you, I will be providing a new magic pipe to players later~", getReply(npc));
    }
}
