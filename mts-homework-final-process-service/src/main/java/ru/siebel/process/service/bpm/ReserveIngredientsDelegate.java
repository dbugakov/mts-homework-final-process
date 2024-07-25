package ru.siebel.process.service.bpm;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import ru.siebel.order.dto.Order;
import ru.siebel.order.dto.enums.StatusEnum;
import ru.siebel.process.service.service.ProcessService;

@Component
@RequiredArgsConstructor
public class ReserveIngredientsDelegate implements JavaDelegate {

    private final ProcessService processService;

    @Override
    public void execute(DelegateExecution delegateExecution) {
        Order order = (Order) delegateExecution.getVariableTyped("order").getValue();
        processService.reserveIngredients(order);
        order.setStatus(StatusEnum.RESERVE_INGRS.getValue());
        processService.updateOrder(order);
        delegateExecution.setVariable("order", order);
    }
}
