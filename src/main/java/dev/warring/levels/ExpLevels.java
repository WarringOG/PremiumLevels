package dev.warring.levels;

import dev.warring.core.library.WarringPlugin;
import dev.warring.core.library.menus.api.MenuAPI;
import dev.warring.core.library.storage.MapStorage;
import dev.warring.levels.api.LevelsAPI;
import dev.warring.levels.commands.LevelsCommand;
import dev.warring.levels.files.DataFile;
import dev.warring.levels.files.MessagesFile;
import dev.warring.levels.models.Level;
import dev.warring.levels.papi.PlaceholderAPI;
import dev.warring.levels.utils.MessageUtils;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

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
        if (!Bukkit.getPluginManager().getPlugin("PremiumCore").isEnabled()) {
            this.getLogger().info("=====================================");
            this.getLogger().info("PREMIUMLEVELS DEPENDS ON PREMIUMCORE");
            this.getLogger().info("CONTACT WARRING FOR IT!");
            this.getLogger().info("=====================================");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
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
        setNoPermission(messagesFile.getString("NO-PERMISSION"));
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI").isEnabled())
            new PlaceholderAPI().register();

        startSaveTask();
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
        if (api.getMapStorage().getMap() == null) return;
        if (api.getMapStorage().getMap().isEmpty()) return;
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

    public void startSaveTask() {
        new BukkitRunnable() {
            @Override
            public void run() {
                dumpToFile();

                dumpToRAM();
            }
        }.runTaskTimer(this, 20 * 3600, 20 * 3600);
    }

    public static ExpLevels getInstance() {
        return instance;
    }
}
