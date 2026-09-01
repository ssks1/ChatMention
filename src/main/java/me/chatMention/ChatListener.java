package me.chatMention;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.kyori.adventure.sound.Sound;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.title.Title;

import java.time.Duration;
import java.util.UUID;

public class ChatListener implements Listener {

    private static final Logger log = LoggerFactory.getLogger(ChatListener.class);
    private PlayerSettings playerSettings;

    public ChatListener(MentionDetector detector, PlayerSettings playerSettings) {
        this.detector = detector;
        this.playerSettings = playerSettings;
    }

    public MentionDetector detector;
    @EventHandler
    public void onMessage(AsyncChatEvent event) {

        Player sender = event.getPlayer();
        String senderName = sender.getName();
        Component message = event.message();
        String plainText = PlainTextComponentSerializer.plainText().serialize(message);
        Player player = detector.findMentioned(plainText);
        UUID senderUUID = sender.getUniqueId();
        Boolean enabled = (Boolean) playerSettings.getSetting(senderUUID, "notification-enabled");
        
        if (player != null && enabled != null && enabled == true) {
            action(player, senderName, 1);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory().getHolder() instanceof MyInventory) {
            event.setCancelled(true);
            
            if (event.getSlot() == 13 && 
                event.getCurrentItem() != null && 
                event.getCurrentItem().getType() == Material.PAPER &&
                event.getClick().isLeftClick()) {
                
                Player player = (Player) event.getWhoClicked();
                UUID playerUUID = player.getUniqueId();
                Boolean enabled = (Boolean) playerSettings.getSetting(playerUUID, "notification-enabled");
                
                if (enabled == null || enabled == false) {
                    playerSettings.setSetting(playerUUID, "notification-enabled", true);
                    player.sendMessage("§aNotifications enabled!");
                    action(player, null, 0);
                } else {
                    playerSettings.setSetting(playerUUID, "notification-enabled", false);
                    player.sendMessage("§cNotifications disabled!");
                    player.playSound(player, org.bukkit.Sound.UI_BUTTON_CLICK, 1f, 1f);
                }
            }
        }
    }

    private void action(Player player, String senderName, int option){
        Sound xpSound = Sound.sound(
                org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP,
                Sound.Source.MASTER,
                1.0f,
                1.0f
        );
        player.playSound(xpSound);
        if (option == 1) {
            mentionTitle(player, senderName);
        }
    }
    private void mentionTitle(Player player, String senderName){


        Component mainTitle = Component.text("Mentioned!", NamedTextColor.YELLOW);
        Component subTitle = Component.text(senderName + " mentioned you", NamedTextColor.GOLD);
        Title.Times time = Title.Times.times(
                Duration.ofMillis(250), //5 ticks
                Duration.ofSeconds(2), //40 ticks
                Duration.ofMillis(250) //5 ticks
        );
        Title title = Title.title(mainTitle, subTitle, time);
        player.showTitle(title);
    }
}
