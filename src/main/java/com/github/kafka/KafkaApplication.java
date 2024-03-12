package com.github.kafka;

import com.github.kafka.producer.MyKafkaProducerPessoa;
import com.github.kafka.producer.MyKafkaProducerProduto;
import example.avro.Pessoa;
import example.avro.Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class KafkaApplication {
    @Autowired
    private MyKafkaProducerPessoa producerPessoa;
    @Autowired
    private MyKafkaProducerProduto producerProduto;

    public static void main(String[] args) {
        SpringApplication.run(KafkaApplication.class, args);
    }

    @Bean
    public CommandLineRunner run() {
        return args -> {
            producerPessoa.sendMessage(Pessoa.newBuilder()
                    .setName("Alerson")
                    .setSurname("Rigo")
                    .build());

            producerProduto.sendMessage(Produto.newBuilder()
                    .setName("JBL")
                    .setValor(2.700)
                    .build());
        };
    }

}
