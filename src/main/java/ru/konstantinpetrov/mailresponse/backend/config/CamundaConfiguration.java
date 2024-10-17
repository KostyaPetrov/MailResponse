package ru.konstantinpetrov.mailresponse.backend.config;


import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.repository.DeploymentBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;

@Configuration
public class CamundaConfiguration {

    private static final String BPMN_PATH = "classpath:/bpmn/*.bpmn";

    /**
     * Автоматический деплой всех BPMN процессов при запуске приложения.
     */
    @Bean
    public void deployProcesses(RepositoryService repositoryService) throws IOException {
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