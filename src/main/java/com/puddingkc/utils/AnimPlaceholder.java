package com.puddingkc.utils;

import com.puddingkc.KcAnimPAPI;
import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class AnimPlaceholder extends PlaceholderExpansion {

    private final Random random = new Random();

    private final KcAnimPAPI plugin;
    public AnimPlaceholder(KcAnimPAPI plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "animpapi";
    }

    @Override
    public @NotNull String getAuthor() {
        return "PuddingKC";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.1";
    }

    @Override
    public String onPlaceholderRequest(Player player, @NotNull String identifier) {
        AnimationData data = plugin.animations.get(identifier);
        if (data == null) return null;
        long currentTime = System.currentTimeMillis();
        if (currentTime - data.lastUpdate >= data.speed) {
            updateAnimation(data);
            data.lastUpdate = currentTime;
        }
        String text = data.texts.get(data.currentIndex);
        return plugin.translateHexColorCodes(addPlaceholder(player, text));
    }

    private void updateAnimation(AnimationData data) {
        switch (data.type.toLowerCase()) {
            case "cycle":
                data.currentIndex = (data.currentIndex + 1) % data.texts.size();
                break;
            case "random":
                data.currentIndex = random.nextInt(data.texts.size());
                break;
            case "pingpong":
                if (data.texts.isEmpty()) {
                    break;
                }
                if (data.currentIndex <= 0 || data.currentIndex >= data.texts.size() - 1) {
                    data.pingPongDirection = -data.pingPongDirection;
                }
                data.currentIndex += data.pingPongDirection;
                data.currentIndex = Math.max(0, Math.min(data.currentIndex, data.texts.size() - 1));
                break;
            case "reverse":
                data.currentIndex = (data.currentIndex - 1 + data.texts.size()) % data.texts.size();
                break;
        }
    }

    private String addPlaceholder(Player player, String message) {
        return PlaceholderAPI.setPlaceholders(player, message);
    }
}
