package spring.cloud.kubernetes.loadbalancer.debugging;

import io.fabric8.kubernetes.api.model.Pod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.cloud.kubernetes.commons.PodUtils;
import org.springframework.cloud.loadbalancer.core.ReactorLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

/**
 * @author wxl
 * 自定义负载均衡和服务实例列表
 */
@Slf4j
public class LoadBalanceConfig {

    @Bean
    public ReactorLoadBalancer<ServiceInstance> reactorServiceInstanceLoadBalancer(Environment environment, LoadBalancerClientFactory loadBalancerClientFactory,
                                                                                   PodUtils<Pod> podUtils, ProxyProperties proxyProperties) {
        String name = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
        return new NetSegmentLoadBalancer(loadBalancerClientFactory.getLazyProvider(name, ServiceInstanceListSupplier.class), name,
                environment.getProperty("spring.cloud.kubernetes.discovery.register.enabled", "false"), podUtils, proxyProperties);
    }

    @Bean
    public ServiceInstanceListSupplier discoveryClientServiceInstanceListSupplier(
            ConfigurableApplicationContext context, ObjectProvider<InetUtils> inetUtils, ProxyProperties proxyProperties) {
        ServiceInstanceListSupplier supplier = ServiceInstanceListSupplier
                .builder()
                .withBlockingDiscoveryClient()
                .build(context);
        return new NetSegmentServiceInstanceListSupplier(supplier, inetUtils, proxyProperties);
    }
}
