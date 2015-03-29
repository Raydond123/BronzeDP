package me.raydond123.bronze;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.logging.Logger;

public class Bronze extends JavaPlugin {

    public Logger logger = Logger.getLogger("Minecraft");

    public void onEnable() {

        logger.info("[Bronze] The plugin has been enabled!");

        saveDefaultConfig();
        saveConfig();

    }

    public void onDisable() {

        logger.info("[Bronze] The plugin has been disabled!");

    }

    String prefix = ChatColor.LIGHT_PURPLE + "[" + ChatColor.GREEN + "BronzeDP" + ChatColor.LIGHT_PURPLE + "] ";

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {

        if(cmd.getLabel().equalsIgnoreCase("bronzedp")) {

            if (!(sender instanceof Player)) {

                logger.info("[Bronze] The command can only be used by a player!");
                return false;

            } else {

                Player player = (Player) sender;

                if(player.hasPermission("bronzedp.use")) {

                    player.sendMessage(prefix + ChatColor.GREEN + "You just sent out a drop party!");

                    List<String> items = getConfig().getStringList("items");
                    List<String> commands = getConfig().getStringList("commands");

                    for(Player player1 : Bukkit.getOnlinePlayers()) {

                        if(player1 != player) {

                            for (String rawData : items) {

                                String[] data = rawData.split(":");
                                String materialRaw = data[0];
                                int materialId = Integer.valueOf(materialRaw);
                                String amountRaw = data[1];
                                int amount = Integer.valueOf(amountRaw);

                                ItemStack item = new ItemStack(Material.getMaterial(materialId), amount);

                                player1.getInventory().addItem(item);

                            }

                            for(int i = 0; i < commands.size(); i++) {

                                String command = commands.get(i);

                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("%player%", player1.getName()));

                            }

                            player1.sendMessage(prefix + ChatColor.GREEN + "You just recieved items from a drop party!");

                        }

                    }

                } else {

                    player.sendMessage(prefix + ChatColor.GREEN + "You do not have permission to use this command!");

                }

            }

        }

        return false;

    }

}
