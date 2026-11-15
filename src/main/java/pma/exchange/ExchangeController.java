package pma.exchange;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Contrato do serviço Exchange (câmbio entre moedas).
 *
 * - O exchange-service implementa esta interface (ExchangeResource).
 * - Os outros serviços (ex.: Order) usam esta mesma interface como cliente Feign.
 */
@FeignClient(name = "exchange", url = "${pma.exchange.url:http://exchange:8080}")
public interface ExchangeController {

  /**
   * Cotação para converter de {@code from} para {@code to} (códigos ISO 4217, ex.: USD, BRL, EUR).
   * 200 com a cotação; 400 se o código for inválido ou as moedas forem iguais;
   * 404 se o par não existe; 503 se a fonte de cotações estiver fora do ar.
   */
  @GetMapping("/exchange/{from}/{to}")
  public ResponseEntity<QuoteOut> quote(
      @PathVariable("from") String from,
      @PathVariable("to") String to);

  @GetMapping("/exchange/health-check")
  public ResponseEntity<Void> healthCheck();

}
