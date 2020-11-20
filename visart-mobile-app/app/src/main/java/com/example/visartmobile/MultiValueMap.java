package com.example.visartmobile;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

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
        return map.entrySet().iterator();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public Stream<Map.Entry<K, ArrayList<V>>> stream() {
        return map.entrySet().stream();
    }
}

