package games.stendhal.server.maps.deniran.cityinterior.weaponsshop;
import java.util.Map;

import games.stendhal.server.core.config.ZoneConfigurator;
import games.stendhal.server.core.engine.StendhalRPZone;

public class PipeDealerNPC implements ZoneConfigurator {
    @Override
    public void configureZone(StendhalRPZone zone, Map<String, String> attributes) {
        buildPipeDealerNPC(zone);
    }

    private void buildPipeDealerNPC(StendhalRPZone zone) {
        // create the NPC
    }
}
