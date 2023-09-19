package com.akuan.auth.activiti;


import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProcessTest {


    @Autowired
    private RepositoryService repositoryService;

    @Test
    public void deployProcess(){
        Deployment deploy = repositoryService.createDeployment()
                .addClasspathResource("process/qj_bpmn20.xml")
                .addClasspathResource("process/qj.png")
                .name("请假申请流程")
                .deploy();

        System.out.println("deploy.getId() = " + deploy.getId());
        System.out.println("deploy.getName() = " + deploy.getName());
    }
}
