package org.camunda.bpm.example;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CamundaSpringBootExampleApplication.class)
public class CamundaSpringBootExampleApplicationTest {

  @Autowired
  private RuntimeService runtimeService;

  @Autowired
  private TaskService taskService;

	@Test
	public void verifyProcessInstanceStarted() {
//	  // process instance is started by the application and waits on the user task
//	  Task task = taskService.createTaskQuery().taskName("Approve Loan").singleResult();
//	  assertThat(task, is(notNullValue()));
//
//	  // complete the user task and verify that the process ends
//	  taskService.complete(task.getId());
//
//	  assertThat(runtimeService.createProcessInstanceQuery().count(), is(0L));
	}

}
