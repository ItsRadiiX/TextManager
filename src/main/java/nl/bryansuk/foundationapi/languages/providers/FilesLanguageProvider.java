package nl.bryansuk.foundationapi.languages.providers;
import nl.bryansuk.foundationapi.converter.YAMLConverter;
import nl.bryansuk.foundationapi.handlers.FolderHandler;
import nl.bryansuk.foundationapi.languages.Language;
import nl.bryansuk.foundationapi.textmanager.MessagesManager;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class FilesLanguageProvider implements LanguageProvider {
    private final FolderHandler<Map<String, Object>> locales;

    public FilesLanguageProvider(String folder) {
        locales = new FolderHandler<>(folder, new YAMLConverter<>(),true);
    }

    @Override
    public Map<Locale, Language> getLanguagesByLocale() {
        Map<Locale, Language> languagesByLocale = new HashMap<>();
        List<Map<String, Object>> languages = locales.getObjects();

        for (Map<String, Object> language : languages) {
            try {
                Locale locale = Locale.of((String) language.get("locale"));
                languagesByLocale.put(locale, new Language(language));
            } catch (Exception e){
                MessagesManager.getPlugin().getComponentLogger().warn("Could not identify locale: {}", language.get("locale"));
            }
        }
        return languagesByLocale;

    }
}
