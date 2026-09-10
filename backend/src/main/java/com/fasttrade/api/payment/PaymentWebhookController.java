package com.fasttrade.api.payment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Ponto de integração com PSP (ex: Mercado Pago).
 * ponytail: corpo bruto como Map enquanto PSP não está definido; trocar por DTO quando integrar.
 */
@RestController
@RequestMapping("/api/payment")
public class PaymentWebhookController {

    @PostMapping("/webhook")
    public ResponseEntity<Void> webhook(@RequestBody Map<String, Object> payload) {
        // TODO(fase-pagamento): verificar assinatura, resolver status e atualizar pedido
        return ResponseEntity.ok().build();
    }
}
