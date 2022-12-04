package games.stendhal.server.maps.deniran.cityinterior.weaponsshop;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import games.stendhal.server.core.config.ZoneConfigurator;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.core.pathfinder.FixedPath;
import games.stendhal.server.core.pathfinder.Node;
import games.stendhal.server.entity.npc.SpeakerNPC;

public class PipeDealerNPC implements ZoneConfigurator {

    @Override
    public void configureZone(StendhalRPZone zone, Map<String, String> attributes) {
        buildPipeDealerNPC(zone);
    }

    private void buildPipeDealerNPC(StendhalRPZone zone) {
        final SpeakerNPC npc = new SpeakerNPC("Pippa"){

            @Override
            public void createDialog() {
                addGreeting("Hello! Would you like to know something about my #job?");
                addReply("job", "Hush, just to tell you, I will be providing a new magic pipe to players later~");
                addGoodbye("Goodbye.");
            }
            @Override
            protected void createPath() {
                final List<Node> nodes = new LinkedList<Node>();
                nodes.add(new Node(29, 15));
                nodes.add(new Node(11, 15));
                setPath(new FixedPath(nodes, true));
            }
        };
        npc.setPosition(11, 15);
        npc.setEntityClass("pipedealernpc");
        npc.setDescription("You see Pippa, the pipe dealer.");
        zone.add(npc);
    }
}
