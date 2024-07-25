package ru.siebel.process.service.bpm;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import ru.siebel.order.dto.Order;
import ru.siebel.order.dto.enums.ShippingEnum;
import ru.siebel.order.dto.enums.StatusEnum;
import ru.siebel.process.service.service.ProcessService;

@Component
@RequiredArgsConstructor
public class SubmitOrderToShippingDelegate implements JavaDelegate {

    private final ProcessService processService;

    @Override
    public void execute(DelegateExecution execution) {
        Order order = (Order) execution.getVariableTyped("order").getValue();
        String orderShipping = order.getShipping();
        if (ShippingEnum.DELIVERY.getValue().equals(orderShipping)) {
            order.setStatus(StatusEnum.SUBMIT_FOR_DELIVERY.getValue());
        } else {
            order.setStatus(StatusEnum.SUBMIT_FOR_SERVE.getValue());
        }
        execution.setVariable("orderShipping", orderShipping);
        processService.updateOrder(order);
    }
}