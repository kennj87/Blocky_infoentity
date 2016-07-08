package infoentity.blockynights;

import net.minecraft.server.v1_8_R3.NBTTagCompound;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;

public class EntityHandler {
	
	
	public void spawnWolf(Player player) {
	 Wolf wolf = (Wolf) player.getLocation().getWorld().spawn(player.getLocation(), Wolf.class);
	 wolf.setCustomName("§bPromotion Puppy");
	 wolf.setCustomNameVisible(true);
	 wolf.setBaby();	 
	 wolf.setAgeLock(true);
	 noAI(wolf);
	}
	
	public void spawnWolf1(Player player) {
		 Wolf wolf = (Wolf) player.getLocation().getWorld().spawn(player.getLocation(), Wolf.class);
		 wolf.setCustomName("§2Info Puppy");
		 wolf.setCustomNameVisible(true);
		 wolf.setBaby();	 
		 wolf.setAgeLock(true);
		 noAI(wolf);
		}
	
	void noAI(Entity bukkitEntity) {
	    net.minecraft.server.v1_8_R3.Entity nmsEntity = ((CraftEntity) bukkitEntity).getHandle();
	    NBTTagCompound tag = nmsEntity.getNBTTag();
	    if (tag == null) {
	        tag = new NBTTagCompound();
	    }
	    nmsEntity.c(tag);
	    tag.setInt("NoAI", 1);
	    nmsEntity.f(tag);
	}
}
