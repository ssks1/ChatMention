package me.chatMention;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;


public final class ChatMention extends JavaPlugin{

    @Override
    public void onEnable() {
        MentionDetector detector = new MentionDetector();
        PlayerSettings playerSettings = new PlayerSettings(this);
        getServer().getPluginManager().registerEvents(new ChatListener(detector, playerSettings), this);
        if (getCommand("mention") != null) {
            getCommand("mention").setExecutor(new MentionCommand());
        }
    }
    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    public static class MentionCommand implements CommandExecutor {
        @Override
        public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
            if (!(sender instanceof Player player)){
                sender.sendMessage("only players can execute this command");
                return true;
            }

            MyInventory myInventory = new MyInventory(ChatMention.getPlugin(ChatMention.class), 3);
            player.openInventory(myInventory.getInventory());
            return true;
        }
    }
}
