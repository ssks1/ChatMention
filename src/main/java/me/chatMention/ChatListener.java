package me.chatMention;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.kyori.adventure.sound.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.title.Title;

import java.time.Duration;

public class ChatListener implements Listener {

    private static final Logger log = LoggerFactory.getLogger(ChatListener.class);

    public ChatListener(MentionDetector detector) {
        this.detector = detector;
    }

    public MentionDetector detector;
    @EventHandler
    public void onMessage(AsyncChatEvent event) {

        Player sender = event.getPlayer();
        String senderName = sender.getName();
        Component message = event.message();
        String plainText = PlainTextComponentSerializer.plainText().serialize(message);
        Player player = detector.findMentioned(plainText);
        if (player == null) {}
        else{
            action(player, senderName);
        }
    }

    private void action(Player player, String senderName){
        Sound xpSound = Sound.sound(
                org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP,
                Sound.Source.MASTER,
                1.0f,
                1.0f
        );
        player.playSound(xpSound);
        mentionTitle(player, senderName);

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
