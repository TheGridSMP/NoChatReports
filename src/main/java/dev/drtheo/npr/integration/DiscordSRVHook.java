package dev.drtheo.npr.integration;

import dev.drtheo.npr.NoChatReports;
import github.scarsz.discordsrv.DiscordSRV;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class DiscordSRVHook extends Hook<DiscordSRV> {

    public DiscordSRVHook(NoChatReports reports) {
        super(reports);
    }

    @Override
    protected void init() {
        AsyncPlayerChatEvent.getHandlerList().unregister(this.plugin);
    }

    @Override
    public void onMessage(AsyncPlayerChatEvent event) {
        this.reports.getServer().getScheduler().runTaskAsynchronously(this.plugin, () ->
                this.plugin.processChatMessage(
                        event.getPlayer(), event.getMessage(),
                        this.plugin.getOptionalChannel("global"),
                        false, event
                )
        );
    }

    @Override
    protected DiscordSRV getPlugin() {
        return this.getPlugin("DiscordSRV");
    }
}
