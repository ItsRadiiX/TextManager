package nl.bryansuk.foundationapi.languages.providers;

import nl.bryansuk.foundationapi.languages.Language;

import java.util.Locale;
import java.util.Map;

public interface LanguageProvider {
    Map<Locale, Language> getLanguagesByLocale();
}
