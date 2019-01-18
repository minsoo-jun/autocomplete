package com.minsoo.autocomplete.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static com.minsoo.autocomplete.constants.Constants.REDIS_EXPIRE_MIN;

@Service
public class AutocompleteRedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    public List<?> getFromRedis(String key){
        ValueOperations<String, Object> values = redisTemplate.opsForValue();
        System.out.println("*** getFromRedis:" + key);
        return (List)values.get(key);
    }

    public void setToReids(String key, List<?> result){

        redisTemplate.opsForValue().set(key, result);
        redisTemplate.expire(key,REDIS_EXPIRE_MIN, TimeUnit.MINUTES);
        System.out.println("*** setFromRedis:" + key);
    }

    public void deleteAllkey(){
        Set<String> redisKeys = redisTemplate.keys("*");
        for(String redisKey :redisKeys ){
            System.out.println("*** delete redis key:" + redisKey);
            redisTemplate.delete(redisKey);

        }
    }

}
