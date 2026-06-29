package com.sales.ops.common.until;


import com.sales.ops.common.opsexception.OpsException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author C12961
 * @date 2023/4/17 12:20
 */
public class MapContainer<K, V> {
    private Map<K, List<V>> map = new HashMap<>();

    public MapContainer() {
    }

    public void put(K key, V e) {
        if (!map.containsKey(key)) {
            map.put(key, new ArrayList<>());
        }
        map.get(key).add(e);
    }

    public void forEach(BiConsumer<K, List<V>> consumer) throws OpsException {
        for (Map.Entry<K, List<V>> entry : this.map.entrySet()) {
            consumer.accept(entry.getKey(), entry.getValue());
        }
    }


}
