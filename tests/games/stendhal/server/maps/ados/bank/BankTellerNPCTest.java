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
import games.stendhal.server.entity.npc.SpeakerNPC;
import utilities.ZonePlayerAndNPCTestImpl;
import static org.junit.Assert.*;
import utilities.QuestHelper;
import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.Before;


public class BankTellerNPCTest extends ZonePlayerAndNPCTestImpl {

    private static final String ZONE_NAME = "int_ados_bank_";

    private static final String npcName = "Yance";
    
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		QuestHelper.setUpBeforeClass();
		setupZone(ZONE_NAME);
	}

	
    public BankTellerNPCTest() {
    	super(ZONE_NAME, npcName);
    }
    
	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		new BankNPC().configureZone(zone, null);
	}


    @Test
    public void TellerNPCTest(){
    	SpeakerNPC teller = SingletonRepository.getNPCList().get(npcName);
        assertEquals("Yance is the Ados bank teller. He can help you get your bank statements.", teller.getDescription());
        
    }  
}

	