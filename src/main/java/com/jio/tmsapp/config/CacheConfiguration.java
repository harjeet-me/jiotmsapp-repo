package com.jio.tmsapp.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import org.hibernate.cache.jcache.ConfigSettings;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.jio.tmsapp.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.jio.tmsapp.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.jio.tmsapp.domain.User.class.getName());
            createCache(cm, com.jio.tmsapp.domain.Authority.class.getName());
            createCache(cm, com.jio.tmsapp.domain.User.class.getName() + ".authorities");
            createCache(cm, com.jio.tmsapp.domain.Booking.class.getName());
            createCache(cm, com.jio.tmsapp.domain.Booking.class.getName() + ".bookingItems");
            createCache(cm, com.jio.tmsapp.domain.Invoice.class.getName());
            createCache(cm, com.jio.tmsapp.domain.InvoiceItem.class.getName());
            createCache(cm, com.jio.tmsapp.domain.Insurance.class.getName());
            createCache(cm, com.jio.tmsapp.domain.Contact.class.getName());
            createCache(cm, com.jio.tmsapp.domain.BookingItem.class.getName());
            createCache(cm, com.jio.tmsapp.domain.Equipment.class.getName());
            createCache(cm, com.jio.tmsapp.domain.Customer.class.getName());
            createCache(cm, com.jio.tmsapp.domain.Customer.class.getName() + ".bookings");
            createCache(cm, com.jio.tmsapp.domain.Vendor.class.getName());
            createCache(cm, com.jio.tmsapp.domain.Container.class.getName());
            createCache(cm, com.jio.tmsapp.domain.Driver.class.getName());
            createCache(cm, com.jio.tmsapp.domain.Driver.class.getName() + ".bookingItems");
            createCache(cm, com.jio.tmsapp.domain.Location.class.getName());
            createCache(cm, com.jio.tmsapp.domain.Region.class.getName());
            createCache(cm, com.jio.tmsapp.domain.Country.class.getName());
            createCache(cm, com.jio.tmsapp.domain.Department.class.getName());
            createCache(cm, com.jio.tmsapp.domain.Department.class.getName() + ".employees");
            createCache(cm, com.jio.tmsapp.domain.Task.class.getName());
            createCache(cm, com.jio.tmsapp.domain.Task.class.getName() + ".jobs");
            createCache(cm, com.jio.tmsapp.domain.Employee.class.getName());
            createCache(cm, com.jio.tmsapp.domain.Employee.class.getName() + ".jobs");
            createCache(cm, com.jio.tmsapp.domain.Job.class.getName());
            createCache(cm, com.jio.tmsapp.domain.Job.class.getName() + ".tasks");
            createCache(cm, com.jio.tmsapp.domain.JobHistory.class.getName());
            createCache(cm, com.jio.tmsapp.domain.LoadOrder.class.getName());
            createCache(cm, com.jio.tmsapp.domain.LoadOrder.class.getName() + ".bookingItems");
            createCache(cm, com.jio.tmsapp.domain.BookingItem.class.getName() + ".equipment");
            createCache(cm, com.jio.tmsapp.domain.BookingItem.class.getName() + ".drivers");
            createCache(cm, com.jio.tmsapp.domain.Customer.class.getName() + ".loadOrders");
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cm.destroyCache(cacheName);
        }
        cm.createCache(cacheName, jcacheConfiguration);
    }

}
