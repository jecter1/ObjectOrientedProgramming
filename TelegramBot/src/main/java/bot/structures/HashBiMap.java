package bot.structures;

import java.util.HashMap;
import java.util.Map;

public class HashBiMap<K, V> implements IBiMap<K, V> {
    Map<K, V> unturnedMap;
    Map<V, K> invertedMap;

    public HashBiMap() {
        unturnedMap = new HashMap<K, V>();
        invertedMap = new HashMap<V, K>();
    }

    @Override
    public int size() {
        return unturnedMap.size();
    }

    @Override
    public boolean isEmpty() {
        return unturnedMap.isEmpty();
    }

    @Override
    public void put(K key, V value) {
        unturnedMap.put(key, value);
        invertedMap.put(value, key);
    }

    @Override
    public K getKey(V val) {
        return invertedMap.get(val);
    }

    @Override
    public V getVal(K key) {
        return unturnedMap.get(key);
    }

    @Override
    public V removeByKey(K key) {
        return unturnedMap.remove(key);
    }

    @Override
    public K removeByVal(V val) {
        return invertedMap.remove(val);
    }
}
