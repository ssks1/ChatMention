package me.chatMention;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class MentionDetector {
    private static final Logger log = LogManager.getLogger(MentionDetector.class);

    public String getTargetName(String message) {
        int atIndex = message.indexOf("@");

        // If there's no @, return null
        if (atIndex == -1) {
            return null;
        }

        int spaceIndex = message.indexOf(" ", atIndex);

        if (spaceIndex == -1) {
            return message.substring(atIndex + 1);
        } else {
            return message.substring(atIndex + 1, spaceIndex);
        }
    }

    public Player findMentioned(String message) {
        String targetName = getTargetName(message);

        if (targetName == null || targetName.isEmpty()) {
            return null;
        }

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getName().equalsIgnoreCase(targetName)) {
                return player;
            }
        }

        return null; // no mention in loop
    }
}