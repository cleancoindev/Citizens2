package net.citizensnpcs.util;

import java.util.Arrays;
import java.util.logging.Level;

import net.citizensnpcs.Settings.Setting;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.trait.trait.Owner;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.google.common.base.Joiner;

public class Messaging {
    private static final Joiner SPACE = Joiner.on(" ").useForNull("null");

    public static void debug(Object... msg) {
        if (Setting.DEBUG_MODE.asBoolean())
            log(msg);
    }

    private static String getFormatted(Object[] msg) {
        String toFormat = msg[0].toString();
        Object[] args = msg.length > 1 ? Arrays.copyOfRange(msg, 1, msg.length) : new Object[] {};
        return String.format(toFormat, args);
    }

    public static void log(Level level, Object... msg) {
        Bukkit.getLogger().log(level, "[Citizens] " + SPACE.join(msg));
    }

    public static void log(Object... msg) {
        log(Level.INFO, msg);
    }

    public static void log(Throwable ex) {
        if (ex.getCause() != null)
            ex = ex.getCause();
        ex.printStackTrace();
    }

    public static void logF(Object... msg) {
        log(getFormatted(msg));
    }

    public static void logTr(Messages key) {
        log(Level.INFO, Translator.tr(key));
    }

    public static void logTr(Messages key, Object... msg) {
        log(Level.INFO, Translator.tr(key, msg));
    }

    public static void send(CommandSender sender, Object... msg) {
        sendMessageTo(sender, SPACE.join(msg));
    }

    public static void sendError(CommandSender sender, Object... msg) {
        send(sender, ChatColor.RED.toString() + SPACE.join(msg));
    }

    public static void sendErrorF(CommandSender sender, Object... msg) {
        sendF(sender, ChatColor.RED.toString() + SPACE.join(msg));
    }

    public static void sendF(CommandSender sender, Object... msg) {
        sendMessageTo(sender, getFormatted(msg));
    }

    private static void sendMessageTo(CommandSender sender, String msg) {
        msg = StringHelper.parseColors(msg);
        sender.sendMessage(msg);
    }

    public static void sendTr(CommandSender sender, Messages key) {
        sendMessageTo(sender, Translator.tr(key));
    }

    public static void sendTr(CommandSender sender, Messages key, Object... msg) {
        sendMessageTo(sender, Translator.tr(key, msg));
    }

    public static void sendWithNPC(CommandSender sender, Object msg, NPC npc) {
        String send = msg.toString();

        if (sender instanceof Player) {
            Player player = (Player) sender;
            send = send.replace("<player>", player.getName());
            send = send.replace("<world>", player.getWorld().getName());
        }
        send = send.replace("<owner>", npc.getTrait(Owner.class).getOwner());
        send = send.replace("<npc>", npc.getName());
        send = send.replace("<id>", Integer.toString(npc.getId()));

        send(sender, send);
    }

    public static void severe(Object... messages) {
        log(Level.SEVERE, messages);
    }

    public static void severeF(Object... messages) {
        log(Level.SEVERE, getFormatted(messages));
    }

    public static void severeTr(Messages key) {
        log(Level.SEVERE, Translator.tr(key));
    }
}