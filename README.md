# pma26-exchange

Interface do serviço **Exchange** (câmbio entre moedas): `@FeignClient` + DTOs. É uma lib, não roda sozinha.
A implementação fica em [`pma26-exchange-service`](https://github.com/Organizacao-dos-Coisa-Ruim/pma26-exchange-service).

## Instalar no repositório Maven local

Precisa ser feito **antes** de buildar o `exchange-service` (ou qualquer serviço que chame o Exchange):

```bash
./mvnw clean install
```

## Endpoints

| Método | Rota | Resposta |
|--------|------|----------|
| `GET` | `/exchange/{from}/{to}` | `200` + `QuoteOut` · `400` código inválido ou moedas iguais · `404` par inexistente · `503` fonte de cotações fora do ar |
| `GET` | `/exchange/health-check` | `200` |

`from` e `to` são códigos ISO 4217 de 3 letras (`USD`, `BRL`, `EUR`...), em maiúsculas ou minúsculas.

```json
{ "sell": 5.668, "buy": 5.6666, "date": "2026-11-13 18:30:13", "id-account": "..." }
```

Pelo gateway, a rota exige token (JWT). `id-account` é a conta que pediu a cotação.

## Usando como cliente Feign (ex.: no Order)

```xml
<dependency>
  <groupId>pma</groupId>
  <artifactId>exchange</artifactId>
  <version>0.0.1-SNAPSHOT</version>
</dependency>
```

```java
@EnableFeignClients(clients = ExchangeController.class)
...
BigDecimal rate = exchangeController.quote("BRL", "USD").getBody().sell();
```

A URL padrão é `http://exchange:8080`; sobrescreva com a propriedade `pma.exchange.url`.
