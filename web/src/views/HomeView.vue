<template>
  <div>
    <h1 style="margin-bottom:1.5rem">Produtos</h1>
    <p v-if="loading">Carregando…</p>
    <p v-else-if="error" style="color:red">{{ error }}</p>
    <div v-else class="grid">
      <RouterLink
        v-for="p in products" :key="p.id"
        :to="`/products/${p.id}`"
        class="card product-card"
      >
        <div class="kind-badge" :class="p.kind.toLowerCase()">{{ p.kind }}</div>
        <h3>{{ p.title }}</h3>
        <p class="price">R$ {{ Number(p.price).toFixed(2) }}</p>
      </RouterLink>
    </div>
    <p v-if="!loading && !error && products.length === 0">Nenhum produto cadastrado ainda.</p>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import http from '../api/http'

const products = ref<any[]>([])
const loading = ref(true)
const error = ref('')

onMounted(async () => {
  try {
    const { data } = await http.get('/api/products')
    products.value = data
  } catch {
    error.value = 'Não foi possível carregar os produtos.'
  } finally {
    loading.value = false
  }
})
</script>

<style scoped>
.grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(220px, 1fr)); gap: 1rem; }
.product-card { text-decoration: none; color: inherit; transition: box-shadow .2s; }
.product-card:hover { box-shadow: 0 4px 12px #0002; }
.kind-badge { display: inline-block; font-size: 0.7rem; padding: 2px 8px;
              border-radius: 12px; margin-bottom: 0.5rem; text-transform: uppercase; }
.kind-badge.sale { background: #d4edda; color: #155724; }
.kind-badge.trade { background: #cce5ff; color: #004085; }
.price { margin-top: 0.5rem; font-weight: 700; color: #e94560; }
</style>
