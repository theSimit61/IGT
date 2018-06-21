package de.hsmannheim.igt.exercise.util;

import java.util.HashMap;

/**
 * This class implements a specific Hashmap which returns a value if the key is found,
 * otherwise a default value (defined with the constructor). It does not throw an exception
 * like other hashmaps do in these situations.
 *
 * @param <K> The key of the map
 * @param <V> The value assigned to the key
 */
public class DefaultHashMap<K,V> extends HashMap<K,V> {
    protected V defaultValue;
    public DefaultHashMap(V defaultValue) {
        this.defaultValue = defaultValue;
    }
    @Override
    public V get(Object k) {
        return containsKey(k) ? super.get(k) : defaultValue;
    }
}
