package br.lucas.olivier.pixsimulator.msconta.infra.kafka;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Log4j2
@AllArgsConstructor
@Component
public class PixSimulatorKafkaProducer {
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendEvent(String topic, String message) {
        try {
            log.info("Message sent to topic: {} with data {}", topic, message);
            kafkaTemplate.send(topic, message);
        } catch (Exception e) {
            log.error("Error sending message to topic: {} with data {}", topic, message, e);
        }
    }
}
