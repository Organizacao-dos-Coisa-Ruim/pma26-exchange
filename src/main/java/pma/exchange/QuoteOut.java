package pma.exchange;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;

/**
 * Cotação de um par de moedas, no formato do enunciado do curso:
 * {"sell": 5.668, "buy": 5.6666, "date": "2026-11-13 18:30:13", "id-account": "..."}
 *
 * BigDecimal (e não double) porque é dinheiro: evita erro de arredondamento.
 */
@Builder
public record QuoteOut(

    /** Preço de venda (ask): quanto custa, em "to", comprar 1 unidade de "from". */
    BigDecimal sell,
    /** Preço de compra (bid): quanto se recebe, em "to", vendendo 1 unidade de "from". */
    BigDecimal buy,
    /** Data/hora da cotação na fonte (yyyy-MM-dd HH:mm:ss). */
    String date,
    /** Conta que pediu a cotação (header id-account do gateway); null em chamadas internas. */
    @JsonProperty("id-account") String idAccount

) {
}
