package me.deecaad.core.mechanics;

import me.deecaad.core.file.inline.InlineSerializer;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public final class Registry<T extends InlineSerializer<T>> {

    private final Map<String, T> registry;

    public Registry() {
        this.registry = new HashMap<>();
    }

    /**
     * Adds the given serializer to this registry. Keys are not case-sensitive,
     * so be careful to avoid duplicate keys.
     *
     * @param item The non-null item to add.
     * @return A non-null reference to this (builder-pattern).
     * @throws IllegalArgumentException If a duplicate key is found.
     */
    public Registry<T> add(T item) {
        String key = toKey(item.getInlineKeyword());
        T existing = registry.get(key);

        if (existing != null)
            throw new IllegalArgumentException("Attempted override of '" + existing + "' with '" + item + "'");

        registry.put(key, item);
        return this;
    }

    /**
     * Returns the serializer associated with the given key, or
     * <code>null</code>.
     *
     * @param key The non-null key to check.
     * @return The nullable serializer associated with the key.
     */
    public T get(String key) {
        return registry.get(toKey(key));
    }

    /**
     * Returns the options available in the registry. This can be passed to a
     * {@link me.deecaad.core.file.SerializerOptionsException} to tell the
     * admin which options are registered and available for usage.
     *
     * @return The non-null set of options.
     */
    public Set<String> getOptions() {
        return registry.keySet();
    }

    /**
     * Keys are use lowercase english letters, and do not include spaces or
     * underscores. This method converts a normal string into a key for a
     * registry.
     *
     * @param key The non-null string to convert.
     * @return The non-null converted key.
     */
    public static String toKey(String key) {
        return key.toLowerCase(Locale.ROOT).replace(" ", "").replace("_", "");
    }
}