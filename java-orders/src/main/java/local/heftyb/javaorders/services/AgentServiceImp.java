package local.heftyb.javaorders.services;

import local.heftyb.javaorders.models.Agent;
import local.heftyb.javaorders.repositories.AgentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Transactional
@Service
public class AgentServiceImp implements AgentService
{
    @Autowired
    AgentRepository agerepo;

    @Override
    public Agent findAgentById(long id) throws EntityNotFoundException
    {
        return agerepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Agent " + id + " Not Found"));
    }
}
