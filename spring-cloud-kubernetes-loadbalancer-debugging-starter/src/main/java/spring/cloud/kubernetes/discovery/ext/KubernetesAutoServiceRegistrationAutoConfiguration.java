package spring.cloud.kubernetes.discovery.ext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.serviceregistry.AutoServiceRegistrationConfiguration;
import org.springframework.cloud.client.serviceregistry.AutoServiceRegistrationProperties;
import org.springframework.cloud.kubernetes.commons.KubernetesClientProperties;
import org.springframework.cloud.kubernetes.commons.PodUtils;
import org.springframework.cloud.kubernetes.commons.discovery.KubernetesDiscoveryProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.UnknownHostException;

@Configuration
@ConditionalOnProperty(name = "spring.cloud.kubernetes.discovery.register", havingValue = "true")
@EnableConfigurationProperties(KubernetesRegistrationProperties.class)
@AutoConfigureAfter({AutoServiceRegistrationConfiguration.class, KubernetesServiceRegistryAutoConfiguration.class})
public class KubernetesAutoServiceRegistrationAutoConfiguration {


    @Bean
    @ConditionalOnMissingBean
    public KubernetesAutoServiceRegistration autoServiceRegistration(
            @Qualifier("serviceRegistry") KubernetesServiceRegistry registry,
            AutoServiceRegistrationProperties autoServiceRegistrationProperties,
            KubernetesClientProperties kubernetesClientProperties,
            KubernetesDiscoveryProperties properties,
            KubernetesRegistrationProperties registrationProperties,
            KubernetesRegistration registration, PodUtils podUtils) {
        return new KubernetesAutoServiceRegistration(registry,
                autoServiceRegistrationProperties, registration, properties, registrationProperties, podUtils, kubernetesClientProperties);
    }

    @Bean
    public KubernetesAutoServiceRegistrationListener listener(KubernetesAutoServiceRegistration registration) {
        return new KubernetesAutoServiceRegistrationListener(registration);
    }

    @Bean
    public KubernetesRegistration registration(KubernetesDiscoveryProperties properties) throws UnknownHostException {
        return new KubernetesRegistration(properties);
    }

}
