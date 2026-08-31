package me.chatMention;
import org.bukkit.plugin.java.JavaPlugin;

public final class ChatMention extends JavaPlugin{

    @Override
    public void onEnable() {
        MentionDetector detector = new MentionDetector();
        getServer().getPluginManager().registerEvents(new ChatListener(detector), this);
    }
@Override
public void onDisable() {
    // Plugin shutdown logic
}
    }
