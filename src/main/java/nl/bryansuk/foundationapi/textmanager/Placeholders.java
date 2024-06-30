package nl.bryansuk.foundationapi.textmanager;

import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public final class Placeholders {

    private static Placeholders instance;
    private final List<BiFunction<@Nullable OfflinePlayer, String, String>> parsers = new ArrayList<>();

    public Placeholders(){
        if (instance == null) instance = this;
    }

    public static String parsePlaceholder(@Nullable OfflinePlayer offlinePlayer, String payload){
        for (BiFunction<OfflinePlayer, String, String> parser : instance.parsers) {
            try {
                String parsedPayload = parser.apply(offlinePlayer, payload);
                if (parsedPayload != null) return parsedPayload;
            } catch (Exception ignored) {}
        }
        return (payload);
    }

    public static void addParser(BiFunction<@Nullable OfflinePlayer, String, String> parser){
        instance.parsers.add(parser);
    }

    public static Placeholders getInstance() {
        return instance;
    }
}
