package com.puddingkc;

import com.puddingkc.utils.AnimPlaceholder;
import com.puddingkc.utils.AnimationData;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KcAnimPAPI extends JavaPlugin {

    // 懒得写了

    public final Map<String, AnimationData> animations = new HashMap<>();

    @Override
    public void onEnable() {
        saveDefaultConfig();
        loadConfig();
        new AnimPlaceholder(this).register();
    }

    public void loadConfig() {
        reloadConfig();
        FileConfiguration config = getConfig();

        animations.clear();

        for (String name : Objects.requireNonNull(config.getConfigurationSection("placeholders")).getKeys(false)) {
            String path = "placeholders." + name;

            List<String> texts = config.getStringList(path + ".text");
            int speed = config.getInt(path + ".speed", 100);
            String type = config.getString(path + ".type", "cycle");
            animations.put(name, new AnimationData(texts, speed, type));
        }
    }

    public String translateHexColorCodes(String message) {
        final Pattern hexPattern = Pattern.compile("&#([A-Fa-f0-9]{6})");
        Matcher matcher = hexPattern.matcher(message);
        StringBuffer buffer = new StringBuffer();
        while (matcher.find()) {
            String hexCode = matcher.group(1);
            StringBuilder replacement = new StringBuilder(ChatColor.COLOR_CHAR + "x");
            for (char c : hexCode.toCharArray()) {
                replacement.append(ChatColor.COLOR_CHAR).append(c);
            }
            matcher.appendReplacement(buffer, replacement.toString());
        }
        return ChatColor.translateAlternateColorCodes('&', matcher.appendTail(buffer).toString());
    }
}