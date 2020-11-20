package com.example.visartmobile;

import android.util.Pair;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MultiValueMap<K, V> implements Iterable {
    private HashMap<K, ArrayList<V>> map = new HashMap<>();

    public void add(K key, V value) {
        if (map.containsKey(key)) {
            map.get(key).add(value);
        } else {
            ArrayList<V> storage = new ArrayList<>();
            storage.add(value);
            map.put(key, storage);
        }
    }

    public void addAll(K key, List<? extends V> values) {
        if (map.containsKey(key)) {
            map.get(key).addAll(values);
        } else {
            ArrayList<V> storage = new ArrayList<>();
            storage.addAll(values);
            map.put(key, storage);
        }
    }

    public V getFirst(K key) {
        if (map.containsKey(key)) {
            return map.get(key).get(0);
        } else {
            return null;
        }
    }

    public List<V> getAll(K key) {
        if (map.containsKey(key)) {
            return Collections.unmodifiableList(map.get(key));
        } else {
            return null;
        }
    }

    @NonNull
    @Override
    public Iterator iterator() {
        return new MultiValueMapIterator();
    }
    class MultiValueMapIterator implements Iterator<Map.Entry<K,List<V>>> {
        Iterator it = map.entrySet().iterator();
        @Override
        public boolean hasNext() {
            return it.hasNext();
        }

        @Override
        public Map.Entry<K, List<V>> next() {
            Map.Entry<K,List<V>> e = (Map.Entry<K, List<V>>) it.next();
            e.setValue(Collections.unmodifiableList(e.getValue()));
            return e;
        }
    }
}

