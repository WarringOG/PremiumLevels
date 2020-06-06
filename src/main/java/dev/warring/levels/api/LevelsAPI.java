package dev.warring.levels.api;

import dev.warring.core.library.storage.MapStorage;
import dev.warring.levels.event.LevelUpEvent;
import org.bukkit.entity.Player;

import java.util.UUID;

public class LevelsAPI {

    private MapStorage<UUID, Integer> mapStorage;

    public LevelsAPI() {
        mapStorage = new MapStorage<>();
    }

    public MapStorage<UUID, Integer> getMapStorage() {
        return mapStorage;
    }

    public void setLevel(Player p, int level) {
        mapStorage.set(p.getUniqueId(), level);
    }

    public void setLevel(UUID p, int level) {
        mapStorage.set(p, level);
    }

    public int getLevel(Player p) {
        if (!mapStorage.containsKey(p.getUniqueId())) return 0;
        return mapStorage.get(p.getUniqueId());
    }

    public void addLevel(Player p, int amount) {
        setLevel(p, getLevel(p) + amount);
    }

    public void resetLevel(Player p) {
        setLevel(p, 0);
    }
}
