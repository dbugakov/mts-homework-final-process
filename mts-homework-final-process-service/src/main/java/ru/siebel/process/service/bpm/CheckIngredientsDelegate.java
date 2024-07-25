package ru.siebel.process.service.bpm;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import ru.siebel.order.dto.Order;
import ru.siebel.process.service.service.ProcessService;

@Component
@RequiredArgsConstructor
public class CheckIngredientsDelegate implements JavaDelegate {

    private final ProcessService processService;

    @Override
    public void execute(DelegateExecution execution) {
        Order order = (Order) execution.getVariableTyped("order").getValue();
        execution.setVariable("checkIngredients", processService.checkIngredients(order));
    }

}