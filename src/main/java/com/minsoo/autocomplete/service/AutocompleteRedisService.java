package com.minsoo.autocomplete.service;

import com.minsoo.autocomplete.domain.response.EnDomain;
import com.minsoo.autocomplete.domain.response.JaDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.minsoo.autocomplete.constants.Constants.REDIS_EXPIRE_MIN;

@Service
public class AutocompleteRedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    public List<?> getFromRedis(String key){
        ValueOperations<String, Object> values = redisTemplate.opsForValue();
        //System.out.println("*** getFromRedis:" + values.get(key));
        return (List)values.get(key);
    }

    public void setToReids(String key, List<?> result){
        // expire 3 mins
        redisTemplate.expire(key,REDIS_EXPIRE_MIN, TimeUnit.MINUTES);
        ValueOperations<String, Object> values = redisTemplate.opsForValue();
        values.set(key, result);
        //System.out.println("*** setFromRedis:" + values.get(key).toString());
    }

}
