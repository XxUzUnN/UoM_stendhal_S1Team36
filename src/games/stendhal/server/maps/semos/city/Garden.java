package games.stendhal.server.maps.semos.city;

import java.util.Map;

import org.apache.log4j.Logger;

import games.stendhal.server.core.engine.StendhalRPZone;
//import games.stendhal.server.entity.Entity;
import games.stendhal.server.entity.mapstuff.area.Allotment;
//import games.stendhal.server.entity.mapstuff.area.FertileGround;
//import games.stendhal.server.entity.mapstuff.spawner.FlowerGrower;
import games.stendhal.server.entity.player.Player;


public class Garden extends FertileGrounds{
// StendhalRPZone zone1=null;

	public Garden(){
	// zone1=zone;
	}
	public Garden(final StendhalRPZone zone, final Map<String, String> attributes ){
	this.configureZone(zone, attributes);
	}
	
	@Override
	public void configureZone(final StendhalRPZone zone, final Map<String, String> attributes) {
		if (zone != null) {
			if (isValid(attributes)) {
				try {
				final Allotment all = new Allotment();
				all.setPosition(Integer.parseInt(attributes.get("x")), Integer.parseInt(attributes.get("y")));
				all.setSize(Integer.parseInt(attributes.get("width")), Integer.parseInt(attributes.get("height")));
				
				if (attributes.containsKey("name")) {
					all.setName(attributes.get("name"));
				}
	
				all.hide();
				zone.add(all);
				} 
				catch (final NumberFormatException e) {
					Logger.getLogger(FertileGrounds.class).error(
						"cannot create allotment in " + zone.getName() + ": " + e);
				}
			}
		}
	}
	
	private boolean isValid(final Map<String, String> attributes) {
		return attributes.containsKey("x") && attributes.containsKey("y") && attributes.containsKey("width")
				&& attributes.containsKey("height");
	}
	
	
	public boolean equipmentAvilable(Player player){
		// player=new PlayerHasItemWithHimCondition("shovel",1);
		if(player.isEquipped("shovel", 1)){
			return true;
	}
	return false;
	}



}