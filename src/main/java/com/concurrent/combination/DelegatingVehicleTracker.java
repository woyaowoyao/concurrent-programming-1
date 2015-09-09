package com.concurrent.combination;

import com.concurrent.model.Point;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 将线程安全委托给ConcurrentHashMap
 * <p/>
 * User : krisibm@163.com
 * Date: 2015/9/9
 * Time: 22:44
 */
public class DelegatingVehicleTracker {

    private final ConcurrentMap<String, Point> locations;

    private final Map<String, Point> unmodifyableMap;

    /**
     * 构造函数初始化，保证线程的安全性。
     *
     * @param map
     */
    public DelegatingVehicleTracker(Map<String, Point> map) {
        locations = new ConcurrentHashMap<String, Point>(map);
        unmodifyableMap = Collections.unmodifiableMap(locations);
    }

    public Map<String, Point> getLocations() {
        return unmodifyableMap;
    }

    public Point getLocation(String id) {
        return locations.get(id);
    }

    public void setLocaion(String id, int x, int y) {
        if (locations.replace(id, new Point(x, y)) == null) {
            throw new IllegalArgumentException("invalid vehicle name : " + id);
        }

    }

}
