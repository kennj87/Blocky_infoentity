package infoentity.blockynights;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Promote {
	
	PlayerMovement playermovement;
	Main main;
	
	public Promote(Main main, PlayerMovement playermovement) {
		this.main = main;
		this.playermovement = playermovement;
	}
	
	   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	   static final String DB_URL_PLAYED = "jdbc:mysql://localhost/zperm";
	   static final String DB_URL_PLAYTIME = "jdbc:mysql://localhost/playtime";
	   static final String USER = "user";
	   static final String PASS = "password";
	
	public void handlePromote(final Player player,final String group) {
		final Long played = checkPlayed(player);
		final double playtime = checkPlaytime(player);
		final boolean eligibleplayed = checkPlayerEligiblePlayed(played,group);
		final boolean eligibleplaytime = checkPlayerEligiblePlaytime(playtime,group);
		player.sendMessage("§bPromotion Puppy says:§e Checking your played time! give me just a moment to find your files");
		new BukkitRunnable() {
	        @Override
	        public void run() {
	        	player.sendMessage("§bPromotion Puppy says:§e Okay found it! ohhh.... nevermind wrong file, give me just a second here!");
	        }
		}.runTaskLater(main, 80);
		new BukkitRunnable() {
	        @Override
	        public void run() {
	        	player.sendMessage("§bPromotion Puppy says:§e Ahhh here we go your played time is: "+played+" day(s).");
	        	if (eligibleplayed) { 
		        	player.sendMessage("§bPromotion Puppy says:§e Wuhuu lets promote you, hang tight!§5 *Puts on wizard hat*");
		    		Bukkit.getServer().dispatchCommand(player, "warp promotionz");
		        	playermovement.promote(player); 
	        		} 
	        }
		}.runTaskLater(main, 160);
		if (!eligibleplayed) { 
			new BukkitRunnable() {
			        @Override
			        public void run() {
			        	player.sendMessage("§bPromotion Puppy says:§e You are not eligible for a promotion via your played time, lets check your playtime!");
			        }
			}.runTaskLater(main, 200);
			new BukkitRunnable() {
			        @Override
			        public void run() {
			        	player.sendMessage("§bPromotion Puppy says:§e Hmm, where did i leave that file now....");
			        }
			}.runTaskLater(main, 280);
			new BukkitRunnable() {
		        @Override
		        public void run() {
		        	player.sendMessage("§bPromotion Puppy says:§e Here we go! your played time is: "+playtime+" hour(s)");
		        }
			}.runTaskLater(main, 360);
			if (!eligibleplaytime) {
			new BukkitRunnable() {
		        @Override
		        public void run() {
		        	player.sendMessage("§bPromotion Puppy says:§e Sorry you are not yet eligible for a promotion, try again later!");
		        }
			}.runTaskLater(main, 400);
			}
			else {
				new BukkitRunnable() {
			        @Override
			        public void run() {
			        	player.sendMessage("§bPromotion Puppy says:§e Wuhuu lets promote you, hang tight!§5 *Puts on wizard hat*");
			    		Bukkit.getServer().dispatchCommand(player, "warp promotionz");
			        	playermovement.promote(player);
			        }
				}.runTaskLater(main, 400);
			}
		}
	}
	
	public void playedEligible(Player player,String group) {
		player.sendMessage("eligible");
	}

	public boolean checkPlayerEligiblePlayed(long played,String group) {
		if(group.equalsIgnoreCase("solar")) {
			if (played > 0) { return true; }
		}
		if(group.equalsIgnoreCase("lunar")) {
			if (played > 2) { return true; }
		}
		if(group.equalsIgnoreCase("astral")) {
			if (played > 9) { return true; }
		}
		if(group.equalsIgnoreCase("celestial")) {
			if (played > 30) { return true; }
		}
		if(group.equalsIgnoreCase("stellar")) {
			if (played > 90) { return true; }
		}
		if(group.equalsIgnoreCase("interstellar")) {
			if (played > 180) { return true; }
		}
		if(group.equalsIgnoreCase("galactic")) {
			if (played > 364) { return true; }
		}
		return false;
	}
	
	public boolean checkPlayerEligiblePlaytime(double played,String group) {
		if(group.equalsIgnoreCase("solar")) {
			if (played > 1) { return true; }
		}
		if(group.equalsIgnoreCase("lunar")) {
			if (played > 3) { return true; }
		}
		if(group.equalsIgnoreCase("astral")) {
			if (played > 20) { return true; }
		}
		if(group.equalsIgnoreCase("celestial")) {
			if (played > 50) { return true; }
		}
		if(group.equalsIgnoreCase("stellar")) {
			if (played > 300) { return true; }
		}
		if(group.equalsIgnoreCase("interstellar")) {
			if (played > 600) { return true; }
		}
		if(group.equalsIgnoreCase("galactic")) {
			if (played > 1000) { return true; }
		}
		return false;
	}
	
	public void playtimeMessage(Player player) {
		
	}
	
	public Long checkPlayed(Player player) {
		String user = player.getUniqueId().toString();
		Connection conn = null;
		Statement stmt = null;
		   try{
			      Class.forName("com.mysql.jdbc.Driver");
			      conn = DriverManager.getConnection(DB_URL_PLAYED,USER,PASS);
			      stmt = conn.createStatement();
			      String sql;
			      sql = "SELECT create_date FROM users where uiid='" + user + "'";
			      ResultSet rs = stmt.executeQuery(sql);
			      if (rs.next()) {
			    	  Date createdate = new Date ();
			    	  createdate.setTime((long)rs.getInt("create_date")*1000);
			    	  Date currentDate = new Date ();
			    	  currentDate .setTime((long)(System.currentTimeMillis() / 1000L)*1000);
			    	  long diffInDays = (long)(currentDate.getTime() - createdate.getTime()) / (1000 * 60 * 60 * 24) +1;
			    	  return diffInDays;
			      } else { player.sendMessage("Database connection lost or you are not in it? Try relogging."); }
			      rs.close();stmt.close();conn.close();
		   }catch(SQLException se){se.printStackTrace();}catch(Exception e){e.printStackTrace();}
	   		finally{try{if(stmt!=null)stmt.close();}catch(SQLException se2){}try{if(conn!=null)conn.close();}catch(SQLException se){se.printStackTrace();}}
		
		return 0L;
	}
	
	public double checkPlaytime(Player player) {
		Connection conn = null;
		Statement stmt = null;
		double playtime = 0;
		String p = player.getName();
		try{
		      Class.forName("com.mysql.jdbc.Driver");
		      conn = DriverManager.getConnection(DB_URL_PLAYTIME,USER,PASS);
		      stmt = conn.createStatement();
		      String sql;
		      sql = "SELECT playtime FROM info WHERE username='"+p+"';";
		      ResultSet rs = stmt.executeQuery(sql);
		      if (rs.next()) {
		    	 playtime = Math.round((rs.getDouble("playtime")/60/60) *100);
		    	 playtime = playtime/100;
		      }
		      rs.close();stmt.close();conn.close(); 
	   }catch(SQLException se){se.printStackTrace();}catch(Exception e){e.printStackTrace();}
		finally{try{if(stmt!=null)stmt.close();}catch(SQLException se2){}try{if(conn!=null)conn.close();}catch(SQLException se){se.printStackTrace();}}
		return playtime;
	}
	
}
