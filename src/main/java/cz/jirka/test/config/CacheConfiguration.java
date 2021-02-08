package cz.jirka.test.config;

import io.github.jhipster.config.JHipsterProperties;
import io.github.jhipster.config.cache.PrefixedKeyGenerator;
import java.time.Duration;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {
    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration =
            Eh107Configuration.fromEhcacheCacheConfiguration(
                CacheConfigurationBuilder
                    .newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                    .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                    .build()
            );
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, cz.jirka.test.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, cz.jirka.test.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, cz.jirka.test.domain.User.class.getName());
            createCache(cm, cz.jirka.test.domain.Authority.class.getName());
            createCache(cm, cz.jirka.test.domain.User.class.getName() + ".authorities");
            createCache(cm, cz.jirka.test.domain.Place.class.getName());
            createCache(cm, cz.jirka.test.domain.Place.class.getName() + ".webUsers");
            createCache(cm, cz.jirka.test.domain.Device.class.getName());
            createCache(cm, cz.jirka.test.domain.Device.class.getName() + ".deviceProfiles");
            createCache(cm, cz.jirka.test.domain.Temperature.class.getName());
            createCache(cm, cz.jirka.test.domain.Temperature.class.getName() + ".temperatureValues");
            createCache(cm, cz.jirka.test.domain.TemperatureValues.class.getName());
            createCache(cm, cz.jirka.test.domain.TemperatureValues.class.getName() + ".temperatures");
            createCache(cm, cz.jirka.test.domain.WebUser.class.getName());
            createCache(cm, cz.jirka.test.domain.WebUser.class.getName() + ".pushNotificationTokens");
            createCache(cm, cz.jirka.test.domain.WebUser.class.getName() + ".roles");
            createCache(cm, cz.jirka.test.domain.WebUser.class.getName() + ".places");
            createCache(cm, cz.jirka.test.domain.Role.class.getName());
            createCache(cm, cz.jirka.test.domain.Role.class.getName() + ".webUsers");
            createCache(cm, cz.jirka.test.domain.PushNotificationToken.class.getName());
            createCache(cm, cz.jirka.test.domain.PushNotificationToken.class.getName() + ".webUsers");
            createCache(cm, cz.jirka.test.domain.Application.class.getName());
            createCache(cm, cz.jirka.test.domain.DeviceProfile.class.getName());
            createCache(cm, cz.jirka.test.domain.DeviceProfile.class.getName() + ".devices");
            createCache(cm, cz.jirka.test.domain.DeviceConfiguration.class.getName());
            createCache(cm, cz.jirka.test.domain.SMSNotification.class.getName());
            createCache(cm, cz.jirka.test.domain.PushNotificationToken.class.getName() + ".users");
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache == null) {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
