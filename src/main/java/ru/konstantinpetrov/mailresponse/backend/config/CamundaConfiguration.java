package ru.konstantinpetrov.mailresponse.backend.config;

import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.repository.DeploymentBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Configuration
public class CamundaConfiguration {

    private static final String BPMN_PATH = "classpath:/bpmn/*.bpmn";

    private final RepositoryService repositoryService;

    // Constructor injection of RepositoryService
    public CamundaConfiguration(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    /**
     * Automatically deploy all BPMN processes at application startup.
     */
    @PostConstruct
    public void deployProcesses() throws IOException {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources(BPMN_PATH);

        DeploymentBuilder deploymentBuilder = repositoryService.createDeployment()
                .name("Auto-deployment");

        for (Resource resource : resources) {
            deploymentBuilder.addInputStream(resource.getFilename(), resource.getInputStream());
        }

        deploymentBuilder.deploy();
    }
}