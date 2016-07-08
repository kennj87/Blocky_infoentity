package infoentity.blockynights;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class Listeners implements Listener {

	static public Map<Player, Long> spam = new HashMap<Player, Long>();
	
	Promote promote;
	Main main;
	public Listeners(Main main, Promote promote) {
		this.main = main;
		this.promote = promote;
	}
	
	@EventHandler
	public void entityDamage(EntityDamageByEntityEvent event) {
		if (event.getEntity().getName().contains("bPromotion Puppy") && !event.getDamager().isOp()) {
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void entityClick(PlayerInteractEntityEvent event) {
		Entity entity = event.getRightClicked();
		if (entity instanceof Wolf && event.getRightClicked().getName().contains("bPromotion Puppy")) {
			long unixTime = System.currentTimeMillis() / 1000L;
			if (spam.get(event.getPlayer()) == null || spam.get(event.getPlayer()) < unixTime) {
				if (event.isCancelled()) { event.setCancelled(false); }
				spam.put(event.getPlayer(), unixTime+600);
				promote.handlePromote(event.getPlayer(), Main.permission.getPrimaryGroup(event.getPlayer()));
			} else { event.getPlayer().sendMessage("§bsorry, you can only check for a promotion every 10 minutes."); }
		}
	}
}
