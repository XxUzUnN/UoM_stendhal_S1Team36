/* $Id$ */
/***************************************************************************
 *                   (C) Copyright 2003-2011 - Stendhal                    *
 ***************************************************************************
 ***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/
package games.stendhal.server.entity.npc.behaviour.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static utilities.SpeakerNPCTestHelper.getReply;

import java.util.HashMap;
import java.util.Map;

import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.behaviour.adder.SellerAdder;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.entity.npc.behaviour.impl.OutfitChangerBehaviour.ExpireOutfit;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.MockStendlRPWorld;
import utilities.PlayerTestHelper;

/**
 * tests for OutfitChangerBehaviour
 */
public class OutfitChangerBehaviourTest {

	/**
	 * prepare tests
	 */
	@BeforeClass
	public static void setupBeforeClass() {

		MockStendlRPWorld.get();
	}

	/**
	 * cleanup after tests
	 */
	@AfterClass
	public static void teardownAfterClass() {
		MockStendlRPWorld.reset();
	}

	/**
	 * Tests for onWornOff.
	 */
	@Test
	public void testOnWornOff() {
		Map<String, Integer> pricelist = new HashMap<String, Integer>();
		pricelist.put("trunks", Integer.valueOf(50));
		Player player = PlayerTestHelper.createPlayer("bob");
		ExpireOutfit cloth = new ExpireOutfit(player.getName());
		ExpireOutfit cloth2 = new ExpireOutfit(player.getName());
		assertTrue(cloth.equals(cloth2));
		assertTrue(cloth2.equals(cloth));

		ExpireOutfit cloth3 = new ExpireOutfit(PlayerTestHelper.createPlayer("bob").getName());

		assertTrue(cloth.equals(cloth3));
		assertTrue(cloth3.equals(cloth));

	}

	/**
	 * Tests for selling masks.
	 */
	@Test
	public void testMasks(){
		final Map<String, Integer> pricelist = new HashMap<String, Integer>();
		pricelist.put("bear mask", 20);
		pricelist.put("frog mask", 20);
		pricelist.put("penguin mask", 20);
		pricelist.put("monkey mask", 20);
		pricelist.put("dog mask", 20);
		pricelist.put("squirrel mask", 20);
		pricelist.put("random mask", 20);
		final SellerBehaviour sb = new SellerBehaviour(pricelist);
		final SpeakerNPC npc = new SpeakerNPC("npc");
		npc.addGreeting("Hi, there. Do you need #help with anything?");
		new SellerAdder().addSeller(npc, sb);
		final Player player = PlayerTestHelper.createPlayer("bob");

		npc.getEngine().step(player, "hi");
		assertEquals("Hi, there. Do you need #help with anything?", getReply(npc));

		npc.getEngine().step(player, "buy random mask");
		assertEquals("To buy a random mask will cost 20. Do you want to buy it?", getReply(npc));
		npc.getEngine().step(player, "no");
		assertEquals("Ok, how else may I help you?", getReply(npc));

		npc.getEngine().step(player, "buy bear mask");
		assertEquals("To buy a bear mask will cost 20. Do you want to buy it?", getReply(npc));
		npc.getEngine().step(player, "no");
		assertEquals("Ok, how else may I help you?", getReply(npc));

		npc.getEngine().step(player, "buy mask");
		assertEquals("There is more than one mask. Please specify which sort of mask you want to buy.", getReply(npc));

		npc.getEngine().step(player, "buy");
		assertEquals("Please tell me what you want to buy.", getReply(npc));

		npc.getEngine().step(player, "buy anything-else");
		assertEquals("Sorry, I don't sell anything-elses.", getReply(npc));
	}

	/**
	 * Tests for selling swimsuits.
	 */
	@Test
	public void testSwimsuits(){
		final Map<String, Integer> pricelist = new HashMap<String, Integer>();
		pricelist.put("pink swimsuit", 5);
		pricelist.put("cyan swimsuit", 5);
		pricelist.put("yellow swimsuit", 5);
		pricelist.put("red swimsuit", 5);
		pricelist.put("random swimsuit", 5);
		final SellerBehaviour sb = new SellerBehaviour(pricelist);
		final SpeakerNPC npc = new SpeakerNPC("npc");
		npc.addGreeting("Hallo!");
		new SellerAdder().addSeller(npc, sb);
		final Player player = PlayerTestHelper.createPlayer("bob");

		npc.getEngine().step(player, "hi");
		assertEquals("Hallo!", getReply(npc));

		npc.getEngine().step(player, "borrow a random swimsuit");
		assertEquals("To borrow a random swimsuit will cost 5. Do you want to borrow it?", getReply(npc));
		npc.getEngine().step(player, "no");
		assertEquals("Ok, how else may I help you?", getReply(npc));

		npc.getEngine().step(player, "borrow a pink swimsuit");
		assertEquals("To borrow a pink swimsuit will cost 5. Do you want to borrow it?", getReply(npc));
		npc.getEngine().step(player, "no");
		assertEquals("Ok, how else may I help you?", getReply(npc));

		npc.getEngine().step(player, "borrow swimsuit");
		assertEquals("There is more than one swimsuit. Please specify which sort of swimsuit you want to buy.", getReply(npc));

		npc.getEngine().step(player, "buy anything-else");
		assertEquals("Sorry, I don't sell anything-elses.", getReply(npc));
	}

	/**
	 * Tests for selling trunks.
	 */
	@Test
	public void testTrunks(){
		final Map<String, Integer> pricelist = new HashMap<String, Integer>();
		pricelist.put("black trunks", 5);
		pricelist.put("blue trunks", 5);
		pricelist.put("green trunks", 5);
		pricelist.put("yellow trunks", 5);
		pricelist.put("random trunks", 5);
		final SellerBehaviour sb = new SellerBehaviour(pricelist);
		final SpeakerNPC npc = new SpeakerNPC("npc");
		npc.addGreeting("Hallo!");
		new SellerAdder().addSeller(npc, sb);
		final Player player = PlayerTestHelper.createPlayer("bob");

		npc.getEngine().step(player, "hi");
		assertEquals("Hallo!", getReply(npc));

		npc.getEngine().step(player, "borrow a random trunks");
		assertEquals("To borrow a random trunks will cost 5. Do you want to borrow it?", getReply(npc));
		npc.getEngine().step(player, "no");
		assertEquals("Ok, how else may I help you?", getReply(npc));

		npc.getEngine().step(player, "borrow a black trunks");
		assertEquals("To borrow a black trunks will cost 5. Do you want to borrow it?", getReply(npc));
		npc.getEngine().step(player, "no");
		assertEquals("Ok, how else may I help you?", getReply(npc));

		npc.getEngine().step(player, "borrow trunks");
		assertEquals("There is more than one trunks. Please specify which sort of trunks you want to buy.", getReply(npc));

		npc.getEngine().step(player, "buy anything-else");
		assertEquals("Sorry, I don't sell anything-elses.", getReply(npc));
	}

}
