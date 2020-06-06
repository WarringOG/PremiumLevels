package dev.warring.levels;

import dev.warring.core.library.WarringPlugin;
import dev.warring.core.library.menus.api.MenuAPI;
import dev.warring.core.library.storage.MapStorage;
import dev.warring.levels.api.LevelsAPI;
import dev.warring.levels.commands.LevelsCommand;
import dev.warring.levels.files.DataFile;
import dev.warring.levels.files.MessagesFile;
import dev.warring.levels.models.Level;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.security.Key;
import java.util.UUID;

public class ExpLevels extends WarringPlugin {

    private static ExpLevels instance;

    @Getter
    private LevelsAPI api;

    @Getter
    private DataFile dataFile;

    @Getter
    private MapStorage<Integer, Level> levelMapStorage;

    @Getter
    private MessagesFile messagesFile;

    @Override
    public void enable() {
        saveDefaultConfig();
        instance = this;

        dataFile = new DataFile();
        messagesFile = new MessagesFile();
        api = new LevelsAPI();

        registerListeners(MenuAPI.getInstance());
        levelMapStorage = new MapStorage<>();
        loadLevels();
        dumpToRAM();

        registerCommands(new LevelsCommand());
    }

    @Override
    public void disable() {
        dumpToFile();
    }

    public void reloadPlugin() {
        messagesFile.reload();
        reloadConfig();
    }

    public void dumpToFile(){
        api.getMapStorage().getMap().forEach((uuid, inte) -> {
            dataFile.getCreator().getYmlFile().set("Data." + uuid, inte);
        });
        dataFile.save();
        dataFile.reload();
    }

    public void dumpToRAM() {
        if (dataFile.getCreator().getYmlFile().getConfigurationSection("Data") != null) {
            dataFile.getCreator().getYmlFile().getConfigurationSection("Data").getValues(false).keySet().forEach((key) -> {
                int level = dataFile.getCreator().getYmlFile().getInt("Data." + key);
                api.setLevel(UUID.fromString(key), level);
                System.out.println(level);
            });
        }
    }

    public void loadLevels() {
        if (getConfig().getConfigurationSection("Levels") == null) return;
        getConfig().getConfigurationSection("Levels").getKeys(false).forEach(key -> {
            levelMapStorage.set(Integer.parseInt(key), new Level(Integer.parseInt(key)));
        });
    }

    public static ExpLevels getInstance() {
        return instance;
    }
}
