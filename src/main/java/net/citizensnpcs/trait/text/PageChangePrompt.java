package net.citizensnpcs.trait.text;

import net.citizensnpcs.util.Messaging;
import net.citizensnpcs.util.StringHelper;

import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.NumericPrompt;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;

public class PageChangePrompt extends NumericPrompt {
    private Text text;

    public PageChangePrompt(Text text) {
        this.text = text;
    }

    @Override
    public Prompt acceptValidatedInput(ConversationContext context, Number input) {
        Player player = (Player) context.getForWhom();
        if (!text.sendPage(player, input.intValue())) {
            Messaging.sendError(player, "Invalid page number.");
            return new StartPrompt(text);
        }
        return (Prompt) context.getSessionData("previous");
    }

    @Override
    public String getFailedValidationText(ConversationContext context, String input) {
        return ChatColor.RED + "Invalid page number.";
    }

    @Override
    public String getPromptText(ConversationContext context) {
        return StringHelper.parseColors("<a>Enter a page number to view more text entries.");
    }
}