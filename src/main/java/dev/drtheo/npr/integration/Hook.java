package dev.drtheo.npr.integration;

import org.bukkit.event.player.AsyncPlayerChatEvent;

public abstract class Hook {

    public abstract void onProcessed(AsyncPlayerChatEvent event);
}
