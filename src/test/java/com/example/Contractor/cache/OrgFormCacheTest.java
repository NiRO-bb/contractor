package com.example.Contractor.cache;

import com.example.Contractor.AbstractContainer;
import com.example.Contractor.DTO.OrgForm;
import com.example.Contractor.Service.Cached.OrgFormCachedService;
import com.example.Contractor.Service.OrgFormService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootTest
public class OrgFormCacheTest extends AbstractContainer {

    private final String key = "org_forms::all";

    @Autowired
    public OrgFormService service;

    @Autowired
    public OrgFormCachedService cachedService;

    @Autowired
    public StringRedisTemplate redisTemplate;

    @BeforeAll
    public static void setup(@Autowired OrgFormService service) {
        service.save(new OrgForm(999, "org form for deleting", true));
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
        service.save(new OrgForm(998, "new org form", true));
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
