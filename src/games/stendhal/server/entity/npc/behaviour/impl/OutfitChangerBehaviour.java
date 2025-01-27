/***************************************************************************
 *                   (C) Copyright 2003-2011 - Marauroa                    *
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

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import games.stendhal.common.Rand;
import games.stendhal.common.grammar.ItemParserResult;
import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.events.TurnListener;
import games.stendhal.server.entity.Outfit;
import games.stendhal.server.entity.npc.EventRaiser;
import games.stendhal.server.entity.player.Player;

/**
 * Represents the behaviour of a NPC who is able to sell outfits to a player.
 */
public class OutfitChangerBehaviour extends MerchantBehaviour {
	public static final int NEVER_WEARS_OFF = -1;

	/** outfit expiry in minutes */
	private int endurance;

	private final String wearOffMessage;

	/** if <code>true</code>, return player to original outfit before setting new temp outfit */
	protected boolean resetBeforeChange = false;

	// all available outfit types are predefined here.
	private static Map<String, Outfit> outfitTypes = new HashMap<String, Outfit>();
	static {
		// In each line, there is one possible outfit of this
		// outfit type, in the order: hair, head, dress, base.
		// One of these outfit will be chosen randomly.

		// swimsuits for men
		outfitTypes.put("black trunks", new Outfit("dress=995"));
		outfitTypes.put("blue trunks", new Outfit("dress=996"));
		outfitTypes.put("green trunks", new Outfit("dress=997"));
		outfitTypes.put("yellow trunks", new Outfit("dress=998"));

		// swimsuits for women
		outfitTypes.put("pink swimsuit", new Outfit("dress=991"));
		outfitTypes.put("cyan swimsuit", new Outfit("dress=992"));
		outfitTypes.put("yellow swimsuit", new Outfit("dress=993"));
		outfitTypes.put("red swimsuit", new Outfit("dress=994"));

		// hair & hat are set to "-1" so that they are not drawn over the mask
		outfitTypes.put("bear mask", new Outfit("mask=994,hair=-1,hat=-1"));
		outfitTypes.put("frog mask", new Outfit("mask=995,hair=-1,hat=-1"));
		outfitTypes.put("penguin mask", new Outfit("mask=996,hair=-1,hat=-1"));
		outfitTypes.put("monkey mask", new Outfit("mask=997,hair=-1,hat=-1"));
		outfitTypes.put("dog mask", new Outfit("mask=998,hair=-1,hat=-1"));
		outfitTypes.put("squirrel mask", new Outfit("mask=999,hair=-1,hat=-1"));

		// wedding dress for brides
		// it seems this must be an array as list even though it's only one item
		outfitTypes.put("gown", new Outfit("dress=988,hat=991"));

		// // wedding suit for grooms
		// it seems this must be an array as list even though it's only one item
		outfitTypes.put("suit", new Outfit("dress=987"));
	}

	private static Map<String, List<Outfit>> RandomoutfitTypes = new HashMap<String, List<Outfit>>();
	static {
		// In each line, there is one possible outfit of this
		// outfit type, in the order: hair, head, dress, base.
		// One of these outfit will be chosen randomly.

		// swimsuits for men
		RandomoutfitTypes.put("random trunks", Arrays.asList(
				new Outfit("dress=995"),
				new Outfit("dress=996"),
				new Outfit("dress=997"),
				new Outfit("dress=998")));

		// swimsuits for women
		RandomoutfitTypes.put("random swimsuit", Arrays.asList(
				new Outfit("dress=991"),
				new Outfit("dress=992"),
				new Outfit("dress=993"),
				new Outfit("dress=994")));

		RandomoutfitTypes.put("random mask", Arrays.asList(
				// hair & hat are set to "-1" so that they are not drawn over the mask
				new Outfit("mask=994,hair=-1,hat=-1"),
				new Outfit("mask=995,hair=-1,hat=-1"),
				new Outfit("mask=996,hair=-1,hat=-1"),
				new Outfit("mask=997,hair=-1,hat=-1"),
				new Outfit("mask=998,hair=-1,hat=-1"),
				new Outfit("mask=999,hair=-1,hat=-1")));

		// wedding dress for brides
		// it seems this must be an array as list even though it's only one item
		RandomoutfitTypes.put("gown", Arrays.asList(new Outfit("dress=988,hat=991")));

		// // wedding suit for grooms
		// it seems this must be an array as list even though it's only one item
		RandomoutfitTypes.put("suit", Arrays.asList(new Outfit("dress=987")));
	}

	/**
	 * Creates a new OutfitChangerBehaviour for outfits that never wear off
	 * automatically.
	 *
	 * @param priceList
	 *            list of outfit types and their prices
	 */
	public OutfitChangerBehaviour(final Map<String, Integer> priceList) {
		this(priceList, NEVER_WEARS_OFF, null);
	}

	/**
	 * Creates a new OutfitChangerBehaviour for outfits that never wear off
	 * automatically.
	 *
	 * @param priceList
	 * 		List of outfit types and their prices.
	 * @param reset
	 * 		If <code>true</code>, player's original outfit will be restored before setting
	 * 		setting the new one.
	 */
	public OutfitChangerBehaviour(final Map<String, Integer> priceList, final boolean reset) {
		this(priceList, NEVER_WEARS_OFF, null, reset);
	}

	/**
	 * Creates a new OutfitChangerBehaviour for outfits that wear off
	 * automatically after some time.
	 *
	 * @param priceList
	 * 		List of outfit types and their prices.
	 * @param endurance
	 * 		The time (in turns) the outfit will stay, or NEVER_WEARS_OFF if the outfit
	 * 		should never disappear automatically.
	 * @param wearOffMessage
	 * 		the message that the player should receive after the outfit has worn off,
	 * 		or null if no message should be sent.
	 * @param reset
	 * 		If <code>true</code>, player's original outfit will be restored before setting
	 * 		setting the new one.
	 */
	public OutfitChangerBehaviour(final Map<String, Integer> priceList, final int endurance,
			final String wearOffMessage, final boolean reset) {
		this(priceList, endurance, wearOffMessage);
		resetBeforeChange = reset;
	}

	/**
	 * Creates a new OutfitChangerBehaviour for outfits that wear off
	 * automatically after some time.
	 *
	 * @param priceList
	 *            list of outfit types and their prices
	 * @param endurance
	 *            the time (in turns) the outfit will stay, or NEVER_WEARS_OFF
	 *            if the outfit should never disappear automatically.
	 * @param wearOffMessage
	 *            the message that the player should receive after the outfit
	 *            has worn off, or null if no message should be sent.
	 */
	public OutfitChangerBehaviour(final Map<String, Integer> priceList,
			final int endurance, final String wearOffMessage) {
		super(priceList);
		this.endurance = endurance;
		this.wearOffMessage = wearOffMessage;
	}

	/**
	 * Transacts the sale that has been agreed on earlier via setChosenItem()
	 * and setAmount().
	 *
	 * @param seller
	 *            The NPC who sells
	 * @param player
	 *            The player who buys
	 * @return true iff the transaction was successful, that is when the player
	 *         was able to equip the item(s).
	 */
	@Override
	public boolean transactAgreedDeal(ItemParserResult res, final EventRaiser seller, final Player player) {
		final String outfitType = res.getChosenItemName();

		if (!player.getOutfit().isCompatibleWithClothes()) {
			// if the player is wearing a non standard player base
			// then swimsuits, masks and many other outfits wouldn't look good mixed with it
			seller.say("You already have a magic outfit on which just wouldn't look good with another - could you please put yourself in something more conventional and ask again? Thanks!");
			return false;
		}

		int charge = getCharge(res, player);

		if (player.isEquipped("money", charge)) {
			player.drop("money", charge);
			putOnOutfit(player, outfitType);
			return true;
		} else {
			seller.say("Sorry, you don't have enough money!");
			return false;
		}
	}

	/**
	 * removes the special outfit after it outwore.
	 */
	public static class ExpireOutfit implements TurnListener {
		private String name;

		/**
		 * creates a OutwearClothes turn listener
		 *
		 * @param playerName of player
		 */
		public ExpireOutfit(String playerName) {
			name = playerName;
		}

		@Override
		public boolean equals(final Object obj) {
			if (obj instanceof ExpireOutfit) {
				ExpireOutfit other = (ExpireOutfit) obj;
				return name.equals(other.name);
			} else {
				return false;
			}

		}

		@Override
		public int hashCode() {
			return name.hashCode();
		}

		@Override
		public void onTurnReached(final int currentTurn) {
			Player player = SingletonRepository.getRuleProcessor().getPlayer(name);
			if ((player == null) || player.isDisconnected()) {
				return;
			}
			player.sendPrivateText("Your costume has worn off");
			player.returnToOriginalOutfit();
		}
	}

	/**
	 * Tries to get back the bought/lent outfit and give the player his original
	 * outfit back. This will only be successful if the player is wearing an
	 * outfit he got here, and if the original outfit has been stored.
	 *
	 * @param player
	 *            The player.
	 * @param outfitType the outfit to wear
	 */
	public void putOnOutfit(final Player player, final String outfitType) {
		if (resetBeforeChange) {
			// cannot use OutfitChangerBehaviour.returnToOriginalOutfit(player) as it checks if the outfit was rented from here
			player.returnToOriginalOutfit();
		}
		final Outfit newOutfit;
		if (outfitType.equals("random mask")) {
			final List<Outfit> possibleNewOutfits = RandomoutfitTypes.get("random mask");
			newOutfit = Rand.rand(possibleNewOutfits);
		}
		else if (outfitType.equals("random swimsuit")) {
			final List<Outfit> possibleNewOutfits = RandomoutfitTypes.get("random swimsuit");
			newOutfit = Rand.rand(possibleNewOutfits);
		}
		else if (outfitType.equals("random trunks")) {
			final List<Outfit> possibleNewOutfits = RandomoutfitTypes.get("random trunks");
			newOutfit = Rand.rand(possibleNewOutfits);
		}
		else {
			newOutfit = outfitTypes.get(outfitType);
		}
		player.setOutfit(newOutfit.putOver(player.getOutfit()), true);
		player.registerOutfitExpireTime(endurance);
	}

	/**
	 * Checks whether or not the given player is currently wearing an outfit
	 * that may have been bought/lent from an NPC with this behaviour.
	 *
	 * @param player
	 *            The player.
	 * @return true iff the player wears an outfit from here.
	 */
	public boolean wearsOutfitFromHere(final Player player) {
		final Outfit currentOutfit = player.getOutfit();
		for (final String outfitType : priceCalculator.dealtItems()) {
			if (outfitType.equals("random mask")) {
				if (currentOutfit.toString().contains(RandomoutfitTypes.get(outfitType).toString())) {
					return true;
				}
			}
			else if (outfitType.equals("random swimsuit")) {
				if (currentOutfit.toString().contains(RandomoutfitTypes.get(outfitType).toString())) {
					return true;
				}
			}
			else if (outfitType.equals("random trunks")) {
				if (currentOutfit.toString().contains(RandomoutfitTypes.get(outfitType).toString())) {
					return true;
				}
			}
			else if (currentOutfit.toString().contains(outfitTypes.get(outfitType).toString())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Tries to get back the bought/lent outfit and give the player his original
	 * outfit back. This will only be successful if the player is wearing an
	 * outfit he got here, and if the original outfit has been stored.
	 *
	 * @param player
	 *            The player.
	 * @return true iff returning was successful.
	 */
	public boolean returnToOriginalOutfit(final Player player) {
		if (wearsOutfitFromHere(player)) {
			return player.returnToOriginalOutfit();
		}
		return false;
	}

	/**
	 * Puts the outfit off, but only if the player hasn't taken it off himself
	 * already.
	 * @param player who wears the outfit
	 */
	protected void onWornOff(final Player player) {
		if (wearsOutfitFromHere(player)) {
			player.sendPrivateText(wearOffMessage);
			returnToOriginalOutfit(player);
		}
	}
	/**
	 * Outfit expiry period in minutes
	 * @return endurance in minutes
	 */
	public int getEndurance() {
		return endurance;
	}
}
