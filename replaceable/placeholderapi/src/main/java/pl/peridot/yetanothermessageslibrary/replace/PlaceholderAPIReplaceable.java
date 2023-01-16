package pl.peridot.yetanothermessageslibrary.replace;

import java.lang.ref.WeakReference;
import java.util.Locale;
import java.util.regex.Pattern;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.peridot.yetanothermessageslibrary.adventure.AdventureHelper;

public class PlaceholderAPIReplaceable implements Replaceable {

    private static final Pattern PLACEHOLDER_PATTERN = Pattern.compile("[%]([^%]+)[%]");

    private final WeakReference<Player> playerRef;

    public PlaceholderAPIReplaceable(@Nullable Player player) {
        this.playerRef = new WeakReference<>(player);
    }

    @Override
    public @NotNull String replace(@Nullable Locale locale, @NotNull String text) {
        return this.replacePlaceholder(text);
    }

    @Override
    public @NotNull Component replace(@Nullable Locale locale, @NotNull Component text) {
        TextReplacementConfig replacement = TextReplacementConfig.builder()
                .match(PLACEHOLDER_PATTERN)
                .replacement((result, input) -> AdventureHelper.legacyToComponent(this.replacePlaceholder(result.group())))
                .build();
        return text.replaceText(replacement);
    }

    private String replacePlaceholder(String text) {
        Player player = this.playerRef.get();
        if (player == null) {
            return text;
        }
        return PlaceholderAPI.setPlaceholders(player, text);
    }

}
