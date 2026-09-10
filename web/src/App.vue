<template>
  <nav class="navbar">
    <RouterLink to="/" class="brand">EntregaSoluções</RouterLink>
    <div class="nav-links">
      <RouterLink to="/cart">Carrinho</RouterLink>
      <RouterLink to="/orders" v-if="auth.isAuthenticated">Pedidos</RouterLink>
      <RouterLink to="/admin" v-if="auth.isAdmin">Admin</RouterLink>
      <RouterLink to="/login" v-if="!auth.isAuthenticated">Entrar</RouterLink>
      <button v-else @click="auth.logout(); $router.push('/login')">Sair</button>
    </div>
  </nav>
  <main class="container">
    <RouterView />
  </main>
</template>

<script setup lang="ts">
import { useAuthStore } from './stores/auth'
const auth = useAuthStore()
</script>

<style>
* { box-sizing: border-box; margin: 0; padding: 0; }
body { font-family: system-ui, sans-serif; background: #f5f5f5; color: #222; }
.navbar { display: flex; justify-content: space-between; align-items: center;
          padding: 0.75rem 1.5rem; background: #1a1a2e; color: #fff; }
.brand { color: #e94560; font-weight: 700; text-decoration: none; font-size: 1.1rem; }
.nav-links { display: flex; gap: 1rem; align-items: center; }
.nav-links a { color: #ccc; text-decoration: none; }
.nav-links a:hover, .nav-links a.router-link-active { color: #fff; }
.nav-links button { background: none; border: 1px solid #e94560; color: #e94560;
                    padding: 0.3rem 0.75rem; border-radius: 4px; cursor: pointer; }
.container { max-width: 1100px; margin: 2rem auto; padding: 0 1rem; }
.card { background: #fff; border-radius: 8px; padding: 1.25rem; box-shadow: 0 1px 4px #0001; }
.btn { display: inline-block; padding: 0.5rem 1.25rem; border-radius: 6px;
       border: none; cursor: pointer; font-size: 0.95rem; }
.btn-primary { background: #e94560; color: #fff; }
.btn-outline { background: none; border: 1px solid #e94560; color: #e94560; }
input, select, textarea { width: 100%; padding: 0.5rem 0.75rem; border: 1px solid #ddd;
                           border-radius: 6px; font-size: 0.95rem; margin-bottom: 0.75rem; }
</style>
