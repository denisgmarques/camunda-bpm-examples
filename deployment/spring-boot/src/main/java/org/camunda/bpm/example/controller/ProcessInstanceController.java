package org.camunda.bpm.example.controller;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.runtime.Execution;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.runtime.VariableInstance;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@RestController
public class ProcessInstanceController extends BaseApiRestController {
  private static String PROCESS_DEFINITION_KEY = "loanRequest";
  
  @Autowired ProcessEngine engine;
  
  @RequestMapping(value = "/process/start", method = RequestMethod.POST,
          consumes = {"application/json", "application/xml"})
//          produces = {"application/json", "application/xml"})
  public @ResponseBody String startProcess(@RequestBody List<Map<String, String>> data, @RequestHeader(value="Referer", required = false) final String referer) {
    String uiBaseUrl="";
    if (referer != null) {
        uiBaseUrl = referer.substring(0, referer.lastIndexOf('/')) + "/";
    }

    Map<String, Object> variables = new HashMap<>();
    variables.put("base_uri", uiBaseUrl);

    data.forEach(obj -> {
        variables.put(obj.get("name"), obj.get("value"));
    });

    ProcessInstance processInstance = engine
            .getRuntimeService()
            .startProcessInstanceByKey(PROCESS_DEFINITION_KEY, String.valueOf(new Random().nextInt(9999999)),
        Variables.fromMap(variables));
    
    return processInstance.getProcessInstanceId();
  }
  
  @RequestMapping(value = "/process/list", method = RequestMethod.GET)
  public @ResponseBody Map<String, Object> getApplications() {

     Map<String, Object> processes = new HashMap<>();
    
     // Get all executions
     List<Execution> list = engine
            .getRuntimeService()
            .createExecutionQuery()
            .processDefinitionKey(PROCESS_DEFINITION_KEY)
            .list();
     
     // Get all variable from each execution
     list.forEach(pi -> {
        List<VariableInstance> variables = engine
              .getRuntimeService()
              .createVariableInstanceQuery()
              .processInstanceIdIn(pi.getId())
              .list();
    
         Map<String, Object> variablesMap = new HashMap<>();

         variables.forEach(var -> {
            Map<String, Object> variable = new HashMap<>();
            variable.put("id", var.getId());
            variable.put("name", var.getName());
            variable.put("value", var.getValue());
    
            variablesMap.put(var.getName(), variable);
         });
        
         processes.put(pi.getId(), variablesMap);
     });
  
    return processes;
    
  }
  
}
