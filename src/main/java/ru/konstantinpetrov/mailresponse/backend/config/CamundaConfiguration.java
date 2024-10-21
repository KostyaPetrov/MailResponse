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

    private static final String BPMN_PATH = "classpath:/*.bpmn";
    private static final String FORM_PATH = "classpath:/*.form"; // Добавили путь для форм

    private final RepositoryService repositoryService;

    // Constructor injection of RepositoryService
    public CamundaConfiguration(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    /**
     * Automatically deploy all BPMN processes and forms at application startup.
     */
    @PostConstruct
    public void deployProcessesAndForms() throws IOException {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] bpmnResources = resolver.getResources(BPMN_PATH);
        Resource[] formResources = resolver.getResources(FORM_PATH); // Добавили поиск форм

        DeploymentBuilder deploymentBuilder = repositoryService.createDeployment()
                .name("Auto-deployment");

        // Добавляем все BPMN файлы в деплой
        for (Resource resource : bpmnResources) {
            deploymentBuilder.addInputStream(resource.getFilename(), resource.getInputStream());
        }

        // Добавляем все формы в деплой
        for (Resource resource : formResources) {
            deploymentBuilder.addInputStream(resource.getFilename(), resource.getInputStream());
        }

        // Выполняем деплой
        deploymentBuilder.deploy();
    }
}
