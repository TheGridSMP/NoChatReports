package dev.drtheo.npr.integration;

import org.bukkit.Bukkit;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class DiscordSRV extends Hook {

    public DiscordSRV() {
        AsyncPlayerChatEvent.getHandlerList().unregister(Bukkit.getPluginManager().getPlugin("DiscordSRV"));
    }

    @Override
    public void onProcessed(AsyncPlayerChatEvent event) {
        Bukkit.getScheduler().runTaskAsynchronously(github.scarsz.discordsrv.DiscordSRV.getPlugin(), () ->
                github.scarsz.discordsrv.DiscordSRV.getPlugin().processChatMessage(
                        event.getPlayer(),
                        event.getMessage(),
                        github.scarsz.discordsrv.DiscordSRV.getPlugin().getOptionalChannel("global"),
                        false,
                        event
                )
        );
    }
}
