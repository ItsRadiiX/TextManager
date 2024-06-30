package nl.bryansuk.foundationapi.textmanager;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Locale;
import java.util.Map;

import static nl.bryansuk.foundationapi.textmanager.TextCreator.parseObjectToString;

@SuppressWarnings("unused")
public class MessagesManager {
    private static JavaPlugin plugin;
    private static MessagesManager instance;
    private static Map<Locale, Language> languages;
    private static Locale defaultLocale;

    private LanguageProvider provider;

    public MessagesManager(JavaPlugin plugin, LanguageProvider provider, Locale defaultLocale){
        if (instance != null) throw new RuntimeException("Instance of MessagesManager already exists");
        this.provider = provider;

        MessagesManager.instance = this;
        MessagesManager.plugin = plugin;
        MessagesManager.defaultLocale = defaultLocale;
        MessagesManager.languages = provider.getLanguagesByLocale();
    }

    public Component getMessage(Language language, String message) {
        return TextCreator.create(getRawMessage(language, message));
    }

    public Component getMessage(Language language, String message, OfflinePlayer player, TagResolver... tagResolvers) {
        return TextCreator.create(getRawMessage(language, message), player, tagResolvers);
    }

    public Component getMessage(Language language, String message, TagResolver... tagResolvers) {
        return TextCreator.create(getRawMessage(language, message), null, tagResolvers);
    }

    public String getRawMessage(Language language, String message) {
        try {
            Object object = language.messages().get(message);
            if (object == null) object = getLanguage(defaultLocale).messages().get(message);
            return parseObjectToString(object);
        } catch (Exception e){
            plugin.getComponentLogger().warn("Cannot get message with key: {}", message);
            return "{" + message + "}";
        }
    }

    public static Language getLanguage(Locale locale){
        if (languages.containsKey(locale)){
            return languages.get(locale);
        }
        return languages.get(defaultLocale);
    }

    public static MessagesManager getInstance() {
        return instance;
    }

    public void reload(){
        languages.clear();
        languages = provider.getLanguagesByLocale();
    }

    public void setProvider(LanguageProvider provider) {
        this.provider = provider;
        reload();
    }

    public void updateMessage(Language language, String messageKey, String message) {
        language.messages().put(messageKey, message);
    }
}
