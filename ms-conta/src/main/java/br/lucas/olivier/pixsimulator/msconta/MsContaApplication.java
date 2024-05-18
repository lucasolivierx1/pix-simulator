package br.lucas.olivier.pixsimulator.msconta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
public class MsContaApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsContaApplication.class, args);
    }

}
