package dev.warring.levels.models;

import com.google.common.collect.Lists;
import dev.warring.core.library.utils.ItemBuilder;
import dev.warring.core.library.utils.Utils;
import dev.warring.levels.ExpLevels;
import dev.warring.levels.utils.ItemParse;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Getter
public class Level {

    private int level;
    private int cost;
    private List<String> commands;
    private List<String> rewards;
    private ItemBuilder material;

    public Level(int index) {
        ConfigurationSection sec = ExpLevels.getInstance().getConfig().getConfigurationSection("Levels." + index);
        level = index;
        cost = sec.getInt("ExpRequired");
        commands = sec.getStringList("Commands");
        rewards = sec.getStringList("Rewards");
        material = ItemParse.parseMat(sec.getString("Material"));
    }

    public ItemStack getDisplay(Player p) {
        ItemBuilder builder = new ItemBuilder(ItemParse.parseItem(ExpLevels.getInstance().getConfig().getConfigurationSection("MainMenu.LevelItem"), material.getStack(), new String[]{"%level%"}, new String[]{level + ""}));
        List<String> lore = Lists.newArrayList();

        for (String s : ExpLevels.getInstance().getConfig().getConfigurationSection("MainMenu.LevelItem").getStringList("Lore")) {
            s = s.replaceAll("%level%", level + "");
            s = s.replaceAll("%expcost%", cost + "");
            s = s.replaceAll("%currentexp%", Utils.getTotalExperience(p) + "");
            s = s.replaceAll("%neededexp%", (cost - Utils.getTotalExperience(p) > 0 ? cost - Utils.getTotalExperience(p) + "" : "Reached"));
            if (s.equalsIgnoreCase("[rewards]")) {
                for (String r : rewards) {
                    lore.add(ExpLevels.getInstance().getConfig().getString("MainMenu.LevelItem.RewardLore").replaceAll("%reward%", r));
                }
                continue;
            }
            lore.add(s);
        }

        builder.setLore(lore);

        return builder.getStack();
    }
}
