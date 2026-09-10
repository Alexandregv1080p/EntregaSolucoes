# EntregaSoluções

Marketplace de entrega e serviços — monorepo com três apps.

## Estrutura

```
/backend    → API REST   (Java 21, Spring Boot 3.2, PostgreSQL, JWT, WebSocket)
/web        → Painel web (Vue 3, Vite, Pinia, Vue Router, Axios)
/android    → App móvel  (Kotlin, Jetpack Compose, Hilt, Retrofit)
```

## Quickstart (dev)

### 1. Banco + API

```bash
docker-compose up -d
```

API em `http://localhost:8080` | Swagger em `http://localhost:8080/swagger-ui.html`

Admin padrão: `admin@fasttrade.com` / `admin123`

### 2. Web

```bash
cd web
cp .env.example .env
npm install
npm run dev
```

Acesse `http://localhost:5173`

### 3. Android

Abra `/android` no Android Studio (Ladybug+) e rode no emulador.  
O backend em `:8080` já é mapeado para `10.0.2.2:8080` no emulador.

## Variáveis de ambiente (produção)

| Variável         | Descrição                     |
|------------------|-------------------------------|
| `JWT_SECRET`     | Segredo HMAC ≥ 32 bytes       |
| `ADMIN_PASSWORD` | Senha do admin (reset on boot)|

## Próximos passos

- [ ] Integração de pagamento (PSP a definir — webhook já pronto em `POST /api/payment/webhook`)
- [ ] Telas de carrinho e pedido no Android
- [ ] Chat realtime (WebSocket STOMP — backend já configurado)
- [ ] Endpoints admin (`GET /api/admin/users`, `GET /api/admin/orders`)
