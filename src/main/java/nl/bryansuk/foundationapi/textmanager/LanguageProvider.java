package nl.bryansuk.foundationapi.textmanager;

import java.util.Locale;
import java.util.Map;

public interface LanguageProvider {
    Map<Locale, Language> getLanguagesByLocale();
}
