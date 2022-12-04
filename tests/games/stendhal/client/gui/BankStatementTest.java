package games.stendhal.server.maps.quests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;

import games.stendhal.client.entity.Item;
import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.mapstuff.chest.Chest;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.fsm.Engine;
import games.stendhal.server.maps.fado.bank.TellerNPC;
import utilities.PlayerTestHelper;
import utilities.QuestHelper;

public class BankStatementTest {
	private static final String Re = null;
	private games.stendhal.server.entity.player.Player player;
	private SpeakerNPC npc;
	private Engine en;
	
	public static void setUpBeforeClass() throws Exception {
		QuestHelper.setUpBeforeClass();
	}
	
	public void setUp() {
		StendhalRPZone zone = new StendhalRPZone("int_fado_bank");
		new TellerNPC().configureZone(zone, null);
		npc = SingletonRepository.getNPCList().get("Yance");
		en = npc.getEngine();
		
		player = PlayerTestHelper.createPlayer("player");
	}
	
	public void testQuest() {
		final Chest chest = new Chest();
		final Item mo = new Item("names", "class", "subclass", new HashMap<String, String>());
		mo.getDescription();
		assertFalse(chest.isOpen());
		chest.open();
		chest.add(mo);
		assertTrue(chest.isOpen());
		chest.close();
		assertFalse(chest.isOpen());
		en.step(player, "M");
		assertEquals("Welcome to the Fado Bank! Do you need #help?", getReply(npc));
		en.step(player, "help;");
		assertEquals("Just to the left, you can see a few chests. Open one and you can store your belongings within", getReply(npc));
		en.step(player, "bank chest");
		assertEquals("If you want to access you personal chest alone, you can view it in your #travel log.", getReply(npc));
		en.step(player, "yes");
		assertEquals("Your statement has been updated, please chehck it in your #travel log.", getReply(npc));
		en.step(player, "bye");
		assertEquals("Have a nice day.", getReply(npc));
		
		List<String> RecentList = RecentChest.GetList();
		List<String> ActualList = TellerAccess.getBankStatement();
		assertEquals(RecentList, ActualList);
		
	}

	private Object getReply(SpeakerNPC npc2) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
