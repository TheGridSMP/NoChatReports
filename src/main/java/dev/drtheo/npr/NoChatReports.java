package dev.drtheo.npr;

import dev.drtheo.npr.integration.DiscordSRV;
import dev.drtheo.npr.integration.Hook;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;

public final class NoChatReports extends JavaPlugin implements Listener {

    private final Set<Hook> hooks = new HashSet<>();

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(this, this);

        if (this.getServer().getPluginManager().isPluginEnabled("DiscordSRV")) {
            hooks.add(new DiscordSRV());
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onMessage(AsyncPlayerChatEvent event) {
        if (event.isCancelled())
            return;

        if (event.getRecipients().isEmpty())
            return;

        Player sender = event.getPlayer();
        String consoleMessage = String.format(
                event.getFormat(), sender.getDisplayName()
                        + " (aka " + sender.getName() + ")", event.getMessage()
        );

        String publicMessage = String.format(
                event.getFormat(), sender.getDisplayName(), event.getMessage()
        );

        Bukkit.getConsoleSender().sendMessage(consoleMessage);

        for (Player player : event.getRecipients()) {
            player.sendMessage(sender.getUniqueId(), publicMessage);
        }

        for (Hook hook : this.hooks) {
            hook.onProcessed(event);
        }

        event.setCancelled(true);
        Bukkit.getServer().getPluginManager().callEvent(
                new AsyncPlayerChatEvent(true, sender, event.getMessage(), Set.of())
        );
    }
}
