package com.example.Contractor.cache;

import com.example.Contractor.AbstractContainer;
import com.example.Contractor.DTO.Industry;
import com.example.Contractor.Service.Cached.IndustryCachedService;
import com.example.Contractor.Service.IndustryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootTest
public class IndustryCacheTest extends AbstractContainer {

    private final String key = "industries::all";

    @Autowired
    public IndustryService service;

    @Autowired
    public IndustryCachedService cachedService;

    @Autowired
    public StringRedisTemplate redisTemplate;

    @BeforeAll
    public static void setup(@Autowired IndustryService service) {
        service.save(new Industry(999, "industry for deleting", true));
    }

    @Test
    public void testCachedAfterFirstCall() {
        Assertions.assertFalse(redisTemplate.hasKey(key));
        cachedService.get();
        Assertions.assertTrue(redisTemplate.hasKey(key));
    }

    @Test
    public void testRemoveCacheAfterSaving() {
        cachedService.get();
        Assertions.assertTrue(redisTemplate.hasKey(key));
        service.save(new Industry(998, "new industry", true));
        Assertions.assertFalse(redisTemplate.hasKey(key));
    }

    @Test
    public void testRemoveCacheAfterDeleting() {
        cachedService.get();
        Assertions.assertTrue(redisTemplate.hasKey(key));
        service.delete(999);
        Assertions.assertFalse(redisTemplate.hasKey(key));
    }

}
