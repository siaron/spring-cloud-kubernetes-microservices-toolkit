package spring.cloud.kubernetes.coordinator.gateway.apisix;

import io.github.api7.A6.HTTPReqCall.Req;
import org.apache.apisix.plugin.runner.HttpRequest;
import org.apache.apisix.plugin.runner.HttpResponse;
import org.apache.apisix.plugin.runner.filter.PluginFilterChain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;

/**
 * @author wxl
 */
@Component
@ConditionalOnProperty(value = "filter.enabled", havingValue = "true", matchIfMissing = false)
public class FilterTest implements CommandLineRunner {

    @Autowired
    private KubernetesServiceChooseFilter kubernetesServiceChooseFilter;

    @Override
    public void run(String... args) throws Exception {
        HttpResponse httpResponse = new HttpResponse(1);
        HttpRequest httpRequest = new HttpRequest(new Req());
        httpRequest.initCtx(httpResponse, Map.of(kubernetesServiceChooseFilter.name(), """
                {"namespace":"wxl-k8s-service","service":"account"}
                """));
        PluginFilterChain filterChain = new PluginFilterChain(Collections.singletonList(kubernetesServiceChooseFilter));
        filterChain.filter(httpRequest, httpResponse);
    }
}
