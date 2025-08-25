package com.example.Contractor.cache;

import com.example.Contractor.AbstractContainer;
import com.example.Contractor.DTO.Country;
import com.example.Contractor.Service.Cached.CountryCachedService;
import com.example.Contractor.Service.CountryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootTest
public class CountryCacheTest extends AbstractContainer {

    private final String key = "countries::all";

    @Autowired
    public CountryService service;

    @Autowired
    public CountryCachedService cachedService;

    @Autowired
    public StringRedisTemplate redisTemplate;

    @BeforeAll
    public static void setup(@Autowired CountryService service) {
        service.save(new Country("DELETE", "country for deleting", true));
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
        service.save(new Country("NEW", "new country", true));
        Assertions.assertFalse(redisTemplate.hasKey(key));
    }

    @Test
    public void testRemoveCacheAfterDeleting() {
        cachedService.get();
        Assertions.assertTrue(redisTemplate.hasKey(key));
        service.delete("DELETE");
        Assertions.assertFalse(redisTemplate.hasKey(key));
    }

}
