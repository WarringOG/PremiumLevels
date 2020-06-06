package dev.warring.levels.commands;

import dev.warring.core.library.commands.backend.CommandManager;
import dev.warring.levels.commands.args.ReloadArg;
import dev.warring.levels.menus.MainMenu;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LevelsCommand extends CommandManager<CommandSender> {

    public LevelsCommand() {
        super("levels", "premiumlevels.admin");
        addAliases("level");
        addArgs(new ReloadArg());
    }

    @Override
    public void base(CommandSender sender) {
        new MainMenu((Player) sender);
    }
}
