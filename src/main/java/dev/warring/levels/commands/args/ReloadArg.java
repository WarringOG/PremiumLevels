package dev.warring.levels.commands.args;

import dev.warring.core.library.commands.backend.CommandInfo;
import dev.warring.core.library.commands.backend.CommandPost;
import dev.warring.levels.ExpLevels;
import dev.warring.levels.utils.MessageUtils;
import org.bukkit.command.CommandSender;

@CommandInfo(aliases = {"rl", "reload"}, permission = "levels.admin")
public class ReloadArg implements CommandPost<CommandSender> {

    @Override
    public void execute(CommandSender sender, String[] strings) {
        MessageUtils.getInstance().sendMessage(sender, "RELOADED-PLUGIN");
        ExpLevels.getInstance().reloadPlugin();
    }
}
