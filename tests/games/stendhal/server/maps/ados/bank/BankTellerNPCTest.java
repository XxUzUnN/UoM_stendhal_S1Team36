/* $Id$ */
/***************************************************************************
 *                   (C) Copyright 2003-2010 - Stendhal                    *
 ***************************************************************************
 ***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/
package games.stendhal.server.maps.ados.bank;

import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.npc.NPC;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.ados.bank.BankNPC;

import org.junit.Test;
import utilities.PlayerTestHelper;
import utilities.ZonePlayerAndNPCTestImpl;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static utilities.SpeakerNPCTestHelper.getReply;


public class BankTellerNPCTest extends ZonePlayerAndNPCTestImpl {

    private static final String ZONE_NAME = "testzone";

    private static final String npcName = "Yance";

    public BankTellerNPCTest() {
        setNpcNames(npcName);
        setZoneForPlayer(ZONE_NAME);
        addZoneConfigurator(new BankNPC(), ZONE_NAME);
    }


    @Test
    public void testPipeDealerNpc(){
        final SpeakerNPC npc = getNPC(npcName);
        final Player player = PlayerTestHelper.createPlayer("bob");
        assertEquals("Yance is the Ados bank teller. He can help you get your bank statements.", npc.getDescription());

        npc.getEngine().step(player, "statements");
        assertEquals("this feature's functionality has not been implemented yet.", getReply(npc));
        
    }
    
    @Test
	public void testConfigureZone() {

		SingletonRepository.getRPWorld();
		final BankNPC bankTellerNPC = new BankNPC();

		final StendhalRPZone zone = new StendhalRPZone("testzone");
		bankTellerNPC.configureZone(zone, null);
		assertFalse(zone.getNPCList().isEmpty());
		final NPC bankTeller = zone.getNPCList().get(1);
		assertThat(bankTeller.getName(), is("Yance"));
		assertThat(bankTeller.getDescription(), is("Yance is the Ados bank teller. He can help you get your bank statements."));
	}
    
    
}

	