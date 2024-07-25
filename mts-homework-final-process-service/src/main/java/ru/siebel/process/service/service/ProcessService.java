package ru.siebel.process.service.service;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;
import org.springframework.stereotype.Service;
import ru.siebel.delivery.api.DeliveryApi;
import ru.siebel.kitchen.api.KitchenApi;
import ru.siebel.order.api.OrderApi;
import ru.siebel.order.dto.Order;
import ru.siebel.serve.api.ServeApi;
import ru.siebel.stock.api.StockApi;

@Service
@RequiredArgsConstructor
public class ProcessService {

    private final RuntimeService runtimeService;

    private final OrderApi orderApi;

    private final StockApi stockApi;

    private final KitchenApi kitchenApi;

    private final DeliveryApi deliveryApi;

    private final ServeApi serveApi;

    public void updateOrder(Order dto) {
        orderApi.updateOrder(dto);
    }

    public boolean checkIngredients(Order dto) {
        return Boolean.TRUE.equals(stockApi.checkIngredients(dto).getBody());
    }

    public void startProcess(Order dto) {
        VariableMap variableMap = Variables.createVariables();
        ObjectValue typedObjectValue = Variables.objectValue(dto).create();
        variableMap.put("order", typedObjectValue);
        String processInstanceId = runtimeService
                .startProcessInstanceByKey("OrderProcess", variableMap)
                .getProcessInstanceId();
    }

    public void cookOrder(Order dto) {
        kitchenApi.cookOrder(dto);
    }

    public void reserveIngredients(Order dto) {
        kitchenApi.reserveIngredients(dto);
    }

    public void serveOrder(Order dto) {
        serveApi.serveOrder(dto);
    }

    public void deliveryOrder(Order dto) {
        deliveryApi.deliveryOrder(dto);
    }
}
