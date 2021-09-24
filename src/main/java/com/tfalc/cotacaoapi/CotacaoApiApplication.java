package com.tfalc.cotacaoapi;

import com.tfalc.cotacaoapi.repository.QuoteRepository;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.math3.random.RandomDataGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

@Log4j2
@EnableScheduling
@SpringBootApplication
public class CotacaoApiApplication {

    @Autowired
    private QuoteRepository quoteRepository;

    public static void main(String[] args) {
        SpringApplication.run(CotacaoApiApplication.class, args);
    }

    @Scheduled(fixedDelay = 1000)
    public void generationData() {
        log.info(quoteRepository.findFirstBySymbolOrderByTimestampDesc("Teste")
                .map(this::generateNewData)
                .orElseGet(this::initializeData));
    }

    private Quote initializeData() {
        return quoteRepository.save(Quote
                .builder()
                .symbol("TESTE")
                .openValue(0.2222)
                .closeValue(0.2222)
                .build());
    }

    private Quote generateNewData(Quote quote) {
        return quoteRepository.save(Quote
                .builder()
                .symbol(quote.getSymbol())
                .openValue(quote.getOpenValue() * new RandomDataGenerator().nextUniform(-0.10, 0.10))
                .closeValue(quote.getCloseValue() * new RandomDataGenerator().nextUniform(-0.10, 0.10))
                .timestamp(new Date())
                .build());
    }
}
