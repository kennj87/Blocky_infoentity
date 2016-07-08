package infoentity.blockynights;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;


public class PlayerMovement {
	
	Main main;
	public PlayerMovement(Main main) {
		this.main = main;
	}
	public void promote(Player player) {
		floatyPlayers(player);
	}
	public void floatyPlayers(final Player player) {
		final Location loca = new Location(Bukkit.getServer().getWorld(main.getConfig().getString("loc.world")), main.getConfig().getDouble("loc.x"), main.getConfig().getDouble("loc.y"), main.getConfig().getDouble("loc.z"));
		int location = loca.getBlockX() + loca.getBlockY() + loca.getBlockZ();
		int locationplayer = player.getLocation().getBlockX() + player.getLocation().getBlockY() + player.getLocation().getBlockZ();
		int total = Math.abs(locationplayer) - Math.abs(location);
		if (total > 1 || total < -1) {
			new BukkitRunnable() {
		        @Override
		        public void run() {
		    		Location location = loca;
					double speed = 0.2;
					Vector dir = location.toVector().subtract(player.getLocation().toVector()).normalize();
					player.setVelocity(dir.multiply(speed));
					ParticleEffect.CLOUD.display(0F,0F,0F,0F,0,player.getLocation(),1000D);
					floatyPlayers(player);
						        	}
			}.runTaskLater(main, 1);
		}
		if (total == 1 || total == 0 || total == -1) {
			freezePlayer(player);
		}
	}
	public void freezePlayer(final Player player) {
	    player.getWorld().playSound(player.getLocation(), Sound.WITHER_SPAWN, 3, 1);
		player.setAllowFlight(true);
		player.setFlying(true);
		player.setFlySpeed(0);
		particle1(player);
		new BukkitRunnable() {
	        @Override
	        public void run() {
	    		player.setFlying(false);
	    		player.setFlySpeed(0.1F);
	    		player.setAllowFlight(false);
	    		particle(player);
	    		particle2(player);
	    	    player.getWorld().playSound(player.getLocation(), Sound.WITHER_DEATH, 3, 1);
	        }
		}.runTaskLater(main, 90);
	}
	
	public void particle(final Player player) {
		new BukkitRunnable() {
			double t = 0;
			@Override
	        public void run() {
				Location loc = player.getLocation();
				t = t + 0.1*Math.PI;
				for (double vec = 0; vec <= 2*Math.PI; vec = vec + Math.PI/32){
					double x = t*Math.cos(vec);
					double y = Math.exp(-0.1*t) * Math.sin(t) + 1.5;
					double z = t*Math.sin(vec);
					loc.add(x,y,z);
					ParticleEffect.FLAME.display(0F,0F,0F,0F,0,loc,1000D);
					loc.subtract(x,y,z);
					
				}
				if (t > 30) {
					this.cancel();
				}
	        }
		}.runTaskTimer(main, 0,1);
	}
	public void particle1(final Player player) {
		new BukkitRunnable() {
			double t = 0;
			@Override
	        public void run() {
				t += Math.PI/32;
				Location loc = player.getLocation();
				for (double phi = 0; phi <= 2*Math.PI; phi += Math.PI/2){
					double x = 0.3*(4*Math.PI - t) * Math.cos(t+phi);
					double y = 0.2*t;
					double z = 0.3*(4*Math.PI - t) * Math.sin(t + phi);
					loc.add(x,y,z);
					ParticleEffect.FIREWORKS_SPARK.display(0F,0F,0F,0F,0,loc,1000D);
					loc.subtract(x,y,z);
					if (t >= 4*Math.PI) {
						loc.add(x,y,z);
						ParticleEffect.SNOWBALL.display(0F,0F,0F,0F,110,loc,1000D);
						this.cancel();
					}
				}
	        }
		}.runTaskTimer(main, 0,1);
	}
	public void particle2(final Player player) {
		Bukkit.getServer().broadcastMessage("§bPromotion Puppy yells:§e Congratulations "+player.getName()+" on your new rank!");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "promote "+player.getName());
		new BukkitRunnable() {
			double t = 0;
			@Override
	        public void run() {
				t += Math.PI/16;
				Location loc = player.getLocation();
				for (double phi = 0; phi <= 2*Math.PI; phi += Math.PI/2){
					double x = 0.3*(4*Math.PI - t) * Math.cos(t+phi);
					double y = 0.2*t;
					double z = 0.3*(4*Math.PI - t) * Math.sin(t + phi);
					loc.add(x,y,z);
					ParticleEffect.SPELL_WITCH.display(0F,0F,0F,0F,0,loc,1000D);
					loc.subtract(x,y,z);
					if (t >= 4*Math.PI) {
						loc.add(x,y,z);
						ParticleEffect.FIREWORKS_SPARK.display(0F,0F,0F,0F,110,loc,1000D);
						this.cancel();
					}
				}
	        }
		}.runTaskTimer(main, 0,60);
	}
}
