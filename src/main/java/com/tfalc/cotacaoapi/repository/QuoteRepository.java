package com.tfalc.cotacaoapi.repository;

import com.tfalc.cotacaoapi.Quote;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuoteRepository extends PagingAndSortingRepository<Quote, Long> {

    @RestResource(rel = "quotes")
    public List<Quote> findByAllSymbol(@Param("symbol") String symbol, Pageable pageable);

    Optional<Quote> findFirstBySymbolOrderByTimestampDesc(String teste);
}
