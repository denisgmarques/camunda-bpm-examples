package org.camunda.bpm.example.configuration;

import org.camunda.bpm.engine.impl.history.event.HistoryEvent;
import org.camunda.bpm.engine.impl.history.handler.HistoryEventHandler;

import java.util.List;
import java.util.logging.Logger;


public class CustomHistoryHandler implements HistoryEventHandler {
    private final Logger LOGGER = Logger.getLogger(CustomHistoryHandler.class.getName());
    
    private static final CustomHistoryHandler INSTANCE = new CustomHistoryHandler();
    
    public static CustomHistoryHandler getInstance(){
        return INSTANCE;
    }
    
    @Override
    public void handleEvent(HistoryEvent historyEvent) {
        
        LOGGER.info("----- HISTORY EVENT PRODUCED: "+ historyEvent.toString());
        
    }
    
    @Override
    public void handleEvents(List<HistoryEvent> historyEvents) {
        for (HistoryEvent historyEvent : historyEvents) {
            handleEvent(historyEvent);
        }
    }
    
    
}
