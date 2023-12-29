package dev.drtheo.npr.integration;

import dev.drtheo.npr.NoChatReports;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class Hook<T extends JavaPlugin> {

    protected final NoChatReports reports;
    protected final T plugin;

    public Hook(NoChatReports reports) {
        this.reports = reports;
        this.plugin = this.getPlugin();

        if (this.isEnabled())
            this.init();
    }

    protected void init() { }

    @SuppressWarnings("unchecked")
    protected T getPlugin(String id) {
        return (T) this.reports.getServer().getPluginManager().getPlugin(id);
    }

    public abstract void onMessage(AsyncPlayerChatEvent event);

    protected abstract T getPlugin();

    public boolean isEnabled() {
        return this.plugin != null;
    }
}
