package ru.siebel.process.service.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import ru.siebel.order.dto.kafka.OrderMessage;
import ru.siebel.process.service.service.ProcessService;

import java.util.function.Consumer;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class KafkaConfiguration {

    private final ProcessService processService;

    @Bean
    public Consumer<Message<OrderMessage>> consumeOrderMessage() {
        return message -> {
            Acknowledgment acknowledgment = message.getHeaders()
                    .get(KafkaHeaders.ACKNOWLEDGMENT, Acknowledgment.class);
            OrderMessage payload = message.getPayload();

            processService.startProcess(payload.getOrder());

            if (acknowledgment != null) {
                acknowledgment.acknowledge();
            }
        };
    }
}