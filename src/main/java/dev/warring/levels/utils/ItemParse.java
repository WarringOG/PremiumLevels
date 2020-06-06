package dev.warring.levels.utils;

import dev.warring.core.library.ServerVersion;
import dev.warring.core.library.utils.ItemBuilder;
import dev.warring.core.library.utils.ItemUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

public class ItemParse {

    public static ItemStack parseItem(ConfigurationSection sec) {
        if (ServerVersion.isOver_V1_12()) {
            return ItemUtils.getConfigItemNonLegacy(sec);
        }
        return ItemUtils.getConfigItemLegacy(sec);
    }

    public static ItemStack parseItem(ConfigurationSection sec, ItemStack item) {
        if (ServerVersion.isOver_V1_12()) {
            return ItemUtils.getConfigItemNonLegacy(sec, item);
        }
        return ItemUtils.getConfigItemLegacy(sec, item);
    }

    public static ItemStack parseItem(ConfigurationSection sec, String[] r, String[] rs) {
        if (ServerVersion.isOver_V1_12()) {
            return ItemUtils.getConfigItemNonLegacy(sec, r, rs);
        }
        return ItemUtils.getConfigItemLegacy(sec, r , rs);
    }

    public static ItemStack parseItem(ConfigurationSection sec, ItemStack i, String[] r, String[] rs) {
        if (ServerVersion.isOver_V1_12()) {
            return ItemUtils.getConfigItemNonLegacy(sec, i, r, rs);
        }
        return ItemUtils.getConfigItemLegacy(sec, i, r , rs);
    }


    public static ItemBuilder parseMat(String mat) {
        if (ServerVersion.isOver_V1_12()) {
            return ItemUtils.parseMaterialNonLegacy(mat);
        }
        return ItemUtils.parseMaterialLegacy(mat);
    }
}
