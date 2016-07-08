package infoentity.blockynights;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {

	Main main;
	PlayerMovement playermovement;
	EntityHandler entityhandler;
	public Commands(Main main,PlayerMovement playermovement,EntityHandler entityhandler) { 
		this.main = main;
		this.playermovement = playermovement;
		this.entityhandler = entityhandler;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		if (player.isOp()) {
			if (args.length == 1 && args[0].equalsIgnoreCase("ppuppy")) {
			entityhandler.spawnWolf(player);
			}
			if (args.length == 1 && args[0].equalsIgnoreCase("ipuppy")) {
			entityhandler.spawnWolf1(player);
			}
			if (args.length == 1 && args[0].equalsIgnoreCase("loc")) {
				int x = player.getLocation().getBlockX();
				int y = player.getLocation().getBlockY();
				int z = player.getLocation().getBlockZ();
				String world = player.getWorld().getName();
				main.getConfig().set("loc.x", x);
				main.getConfig().set("loc.y", y);
				main.getConfig().set("loc.z", z);
				main.getConfig().set("loc.world", world);
				main.saveConfig();
			}
		}
		return true;
	}
}
