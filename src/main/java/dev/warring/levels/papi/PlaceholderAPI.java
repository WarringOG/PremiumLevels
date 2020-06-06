package dev.warring.levels.papi;

import dev.warring.core.features.notes.types.Exp;
import dev.warring.levels.ExpLevels;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

public class PlaceholderAPI extends PlaceholderExpansion {

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public String getAuthor() {
        return "Warring";
    }

    @Override
    public String getIdentifier() {
        return "explevels";
    }

    @Override
    public String getVersion() {
        return ExpLevels.getInstance().getDescription().getVersion();
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        if (identifier.equalsIgnoreCase("level")) {
            return ExpLevels.getInstance().getApi().getLevel(player) + "";
        }
        return null;
    }
}
