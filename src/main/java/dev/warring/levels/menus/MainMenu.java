package dev.warring.levels.menus;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import dev.warring.core.library.menus.CustomMenu;
import dev.warring.core.library.menus.api.InventoryClickType;
import dev.warring.core.library.menus.api.Menu;
import dev.warring.core.library.menus.api.MenuAPI;
import dev.warring.core.library.menus.api.MenuItem;
import dev.warring.core.library.utils.ItemUtils;
import dev.warring.core.library.utils.SoundUtils;
import dev.warring.core.library.utils.Utils;
import dev.warring.levels.ExpLevels;
import dev.warring.levels.event.LevelUpEvent;
import dev.warring.levels.utils.ItemParse;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;

public class MainMenu extends CustomMenu {

    public MainMenu(Player p) {
        super(ExpLevels.getInstance().getConfig().getConfigurationSection("MainMenu"));
        Menu m = getMenu();

        List<MenuItem> items = Lists.newArrayList();
        ConfigurationSection statusSec = ExpLevels.getInstance().getConfig().getConfigurationSection("MenuOptions.StatusItems");
        ExpLevels.getInstance().getLevelMapStorage().getValues().forEach(level -> {
            items.add(new MenuItem() {
                @Override
                public void onClick(Player player, InventoryClickType inventoryClickType) {
                    int current = ExpLevels.getInstance().getApi().getLevel(p);
                    if (current >= level.getLevel()) {
                        SoundUtils.playSound(p, "Error");
                        setTemporaryIcon(ItemParse.parseItem(statusSec.getConfigurationSection("AlreadyUnlocked")), 20L);
                        return;
                    }
                    if (current + 1 != level.getLevel()) {
                        SoundUtils.playSound(p, "Error");
                        setTemporaryIcon(ItemParse.parseItem(statusSec.getConfigurationSection("HighLevel")), 20L);
                        return;
                    }
                    if (Utils.getTotalExperience(p) >= level.getCost()) {
                        LevelUpEvent e = new LevelUpEvent(p, level);
                        if (!e.isCancelled()) {
                            int newExp = Utils.getTotalExperience(p) - level.getCost();
                            p.setTotalExperience(0);
                            p.setLevel(0);
                            p.setExp(0f);
                            p.setTotalExperience(newExp);
                            ExpLevels.getInstance().getApi().setLevel(p, current + 1);
                            setTemporaryIcon(ItemParse.parseItem(statusSec.getConfigurationSection("PurchasedLevel")), 20L);
                            SoundUtils.playSound(p, "PurchasedLevel");
                        }
                    } else {
                        setTemporaryIcon(ItemParse.parseItem(statusSec.getConfigurationSection("NotEnoughExp")), 20L);
                    }
                }

                @Override
                public ItemStack getItemStack() {
                    return level.getDisplay(p);
                }
            });
        });

        m.setupPages(items, getSec().getIntegerList("Slots"));

        m.addMenuItem(new MenuItem() {
            @Override
            public void onClick(Player player, InventoryClickType inventoryClickType) {
                if (m.getCurrentPage() == 1) {
                    SoundUtils.playSound(p, "Error");
                    setTemporaryIcon(ItemParse.parseItem(statusSec.getConfigurationSection("InvalidPage")), 20L);
                    return;
                }
                SoundUtils.playSound(p, "SwitchPage");
                m.previousPage(p);
            }

            @Override
            public ItemStack getItemStack() {
                return ItemParse.parseItem(getSec().getConfigurationSection("PreviousPage"));
            }
        }, getSec().getInt("PreviousPage.Slot"));

        m.addMenuItem(new MenuItem() {
            @Override
            public void onClick(Player player, InventoryClickType inventoryClickType) {
                if (m.getCurrentPage() == m.getMaxPage()) {
                    SoundUtils.playSound(p, "Error");
                    setTemporaryIcon(ItemParse.parseItem(statusSec.getConfigurationSection("InvalidPage")), 20L);
                    return;
                }
                SoundUtils.playSound(p, "SwitchPage");
                m.nextPage(p);
            }

            @Override
            public ItemStack getItemStack() {
                return ItemParse.parseItem(getSec().getConfigurationSection("NextPage"));
            }
        }, getSec().getInt("NextPage.Slot"));

        getInfoItems(getSec(), p).forEach((integer, menuItem) -> {
            m.addMenuItem(menuItem, integer);
        });

        m.setMenuCloseBehaviour(new MenuAPI.MenuCloseBehaviour() {
            @Override
            public void onClose(Player player, Menu menu, boolean b) {
                SoundUtils.playSound(p, "CloseMenu");
            }
        });

        m.openMenu(p);
    }

    public Map<Integer, MenuItem> getInfoItems(ConfigurationSection sec, Player p) {
        Map<Integer, MenuItem> menuItems = Maps.newHashMap();
        sec.getConfigurationSection("InformationItems").getKeys(false).forEach(key -> {
            ConfigurationSection s = sec.getConfigurationSection("InformationItems." + key);
            menuItems.put(s.getInt("Slot"), new MenuItem.UnclickableMenuItem() {
                @Override
                public ItemStack getItemStack() {
                    int level = ExpLevels.getInstance().getApi().getLevel(p);
                    return ItemUtils.getConfigItemLegacy(s.getConfigurationSection("Item"), new String[]{"%level%", "%nextlevel%"}, new String[]{ level+ "", (level + 1) + ""});
                }
            });
        });
        return menuItems;
    }

}
