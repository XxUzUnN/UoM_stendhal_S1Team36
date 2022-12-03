package games.stendhal.server.maps.semos.city;

import org.junit.Test;

import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.mapstuff.spawner.FlowerGrower;
//import games.stendhal.server.entity.player.Player;
//import utilities.PlayerTestHelper;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

public class GardenTest {

@Test
// Fails because there is a plant grower on that area
public void testState()
	{
	final FlowerGrower fl = new FlowerGrower();
	// final FlowerGrower fl2= new FlowerGrower();
	
	
	final StendhalRPZone zone = new StendhalRPZone("zone");
	final Map<String, String> attribs = new HashMap<String, String>();
	attribs.put("x", "0");
	attribs.put("y", "0");
	attribs.put("width", "1");
	attribs.put("height", "1");
	// final Garden garden = new Garden(zone, attribs);
	zone.add(fl);
	assertEquals("free",fl.getState());
	fl.isOnFreeFertileGround();
	fl.setPosition(0,0);
	fl.isOnFreeFertileGround();
	assertEquals("reserved",fl.getState());
	
	}
	
	
	
	
}