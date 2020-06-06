package dev.warring.levels.event;

import dev.warring.core.library.events.custom.CustomCancelledEvent;
import dev.warring.levels.models.Level;
import lombok.Getter;
import org.bukkit.entity.Player;

@Getter
public class LevelUpEvent extends CustomCancelledEvent {

    private Player player;
    private Level level;

    public LevelUpEvent(Player p, Level level) {
        this.player = p;
        this.level = level;
    }

}
