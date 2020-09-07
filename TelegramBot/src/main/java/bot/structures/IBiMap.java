package bot.structures;

public interface IBiMap<K, V> {
    int size();

    boolean isEmpty();

    void put(K key, V value);

    K getKey(V val);
    V getVal(K key);

    V removeByKey(K key);
    K removeByVal(V val);
}
