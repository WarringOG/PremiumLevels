package dev.warring.levels.utils;

import dev.warring.core.library.utils.Utils;
import dev.warring.levels.ExpLevels;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MessageUtils {

    private static MessageUtils instance;

    public static MessageUtils getInstance() {
        if (MessageUtils.instance == null) {
            synchronized (MessageUtils.class) {
                if (MessageUtils.instance == null) {
                    MessageUtils.instance = new MessageUtils();
                }
            }
        }
        return MessageUtils.instance;
    }

    public void sendMessage(Player player, String configLocation) {
        player.sendMessage(Utils.toColor(ExpLevels.getInstance().getMessagesFile().getCreator().getYmlFile().getString(configLocation)));
    }

    public void sendMessageList(Player player, String configLocation) {
        for (String message : ExpLevels.getInstance().getMessagesFile().getCreator().getYmlFile().getStringList(configLocation)) {
            player.sendMessage(Utils.toColor(message));
        }
    }

    public void sendMessage(CommandSender player, String configLocation) {
        player.sendMessage(Utils.toColor(ExpLevels.getInstance().getMessagesFile().getCreator().getYmlFile().getString(configLocation)));
    }

    public void sendMessageList(CommandSender player, String configLocation) {
        for (String message : ExpLevels.getInstance().getMessagesFile().getCreator().getYmlFile().getStringList(configLocation)) {
            player.sendMessage(Utils.toColor(message));
        }
    }

    public void sendMessage(Player player, String configLocation, String replace, String replacement) {
        player.sendMessage(Utils.toColor(ExpLevels.getInstance().getMessagesFile().getCreator().getYmlFile().getString(configLocation).replaceAll(replace, replacement)));
    }

    public void sendMessage(Player player, String configLocation, String[] replace, String[] replacement) {
        String message = Utils.toColor(ExpLevels.getInstance().getMessagesFile().getCreator().getYmlFile().getString(configLocation));
        for (int i = 0; i < replace.length; i++) {
            message = message.replaceAll(replace[i], replacement[i]);
        }
        player.sendMessage(message);
    }

    public void sendMessage(CommandSender sender, String configLocation, String[] replace, String[] replacement) {
        String message = Utils.toColor(ExpLevels.getInstance().getMessagesFile().getCreator().getYmlFile().getString(configLocation));
        for (int i = 0; i < replace.length; i++) {
            message = message.replaceAll(replace[i], replacement[i]);
        }
        sender.sendMessage(message);
    }

    public void sendMessageList(Player player, String configLocation, String replace, String replacement) {
        for (String message : ExpLevels.getInstance().getMessagesFile().getCreator().getYmlFile().getStringList(configLocation)) {
            message = message.replaceAll(replace, replacement);
            player.sendMessage(Utils.toColor(message));
        }
    }

    public void sendMessage(CommandSender player, String configLocation, String replace, String replacement) {
        player.sendMessage(Utils.toColor(ExpLevels.getInstance().getMessagesFile().getCreator().getYmlFile().getString(configLocation).replaceAll(replace, replacement)));
    }

    public void sendMessageList(CommandSender player, String configLocation, String replace, String replacement) {
        for (String message : ExpLevels.getInstance().getMessagesFile().getCreator().getYmlFile().getStringList(configLocation)) {
            message = message.replaceAll(replace, replacement);
            player.sendMessage(Utils.toColor(message));
        }
    }

    public void sendMessageList(CommandSender player, String configLocation, String[] replace, String[] replacement) {
        for (String message : ExpLevels.getInstance().getMessagesFile().getCreator().getYmlFile().getStringList(configLocation)) {
            for (int i = 0; i < replace.length; i++) {
                message = message.replaceAll(replace[i], replacement[i]);
            }
            player.sendMessage(Utils.toColor(message));
        }
    }

    public void sendMessageList(Player player, String configLocation, String[] replace, String[] replacement) {
        for (String message : ExpLevels.getInstance().getMessagesFile().getCreator().getYmlFile().getStringList(configLocation)) {
            for (int i = 0; i < replace.length; i++) {
                message = message.replaceAll(replace[i], replacement[i]);
            }
            player.sendMessage(Utils.toColor(message));
        }
    }

    public void broadcastList(String configSection) {
        for (String message : ExpLevels.getInstance().getMessagesFile().getCreator().getYmlFile().getStringList(configSection)) {
            Bukkit.broadcastMessage(Utils.toColor(message));
        }
    }

}

