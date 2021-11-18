package com.winterchen.provider.store;

import com.winterchen.provider.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author CENTURY
 * @version 1.0
 * @date 2021/11/17 13:59
 **/
@Component
public class UserInfoCache {

    private static final Map<String, User> cache = new ConcurrentHashMap<>();

    public boolean add(User user) {
        if (user == null) {
            return false;
        }
        String id = UUID.randomUUID().toString().replace("-", "");
        user.setId(id);
        cache.put(id, user);
        return true;
    }

    public boolean delete(String id) {
        if (StringUtils.isEmpty(id)) {
            return false;
        }
        if (!cache.containsKey(id)) {
            return false;
        }
        cache.remove(id);
        return true;
    }

    public boolean update(User user) {
        if (user == null || StringUtils.isEmpty(user.getId())) {
            return false;
        }
        if (!cache.containsKey(user.getId())) {
            return false;
        }
        cache.put(user.getId(), user);
        return true;
    }

    public User findById(String id) {
        return cache.get(id);
    }

    public List<User> list() {
        return cache.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList());
    }

}