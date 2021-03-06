package local.heftyb.javaorders.controllers;

import local.heftyb.javaorders.models.Agent;
import local.heftyb.javaorders.services.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/agents")
public class AgentController
{

    @Autowired
    AgentService agentService;

    @GetMapping(value = "/agent/{id}", produces = {"application/json"})
    public ResponseEntity<?> listAgentById (@PathVariable long id)
    {
        Agent agent = agentService.findAgentById(id);
        return new ResponseEntity<>(agent, HttpStatus.OK);
    }
}
