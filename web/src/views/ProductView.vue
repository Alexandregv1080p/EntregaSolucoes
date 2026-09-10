<template>
  <div v-if="loading">Carregando…</div>
  <div v-else-if="product" class="card" style="max-width:700px">
    <div class="kind-badge" :class="product.kind.toLowerCase()">{{ product.kind }}</div>
    <h1 style="margin:.5rem 0 1rem">{{ product.title }}</h1>
    <p style="color:#555;margin-bottom:1rem">{{ product.description }}</p>
    <p class="price">R$ {{ Number(product.price).toFixed(2) }}</p>
    <button class="btn btn-primary" style="margin-top:1.25rem" @click="addToCart">
      Adicionar ao carrinho
    </button>
    <p v-if="added" style="color:green;margin-top:.5rem">✓ Adicionado!</p>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import http from '../api/http'

const route = useRoute()
const product = ref<any>(null)
const loading = ref(true)
const added = ref(false)

onMounted(async () => {
  const { data } = await http.get(`/api/products/${route.params.id}`)
  product.value = data
  loading.value = false
})

function addToCart() {
  const cart: any[] = JSON.parse(localStorage.getItem('cart') ?? '[]')
  const existing = cart.find(i => i.id === product.value.id)
  if (existing) existing.qty++
  else cart.push({ ...product.value, qty: 1 })
  localStorage.setItem('cart', JSON.stringify(cart))
  added.value = true
  setTimeout(() => added.value = false, 2000)
}
</script>

<style scoped>
.kind-badge { display: inline-block; font-size: 0.7rem; padding: 2px 8px;
              border-radius: 12px; text-transform: uppercase; }
.kind-badge.sale { background: #d4edda; color: #155724; }
.kind-badge.trade { background: #cce5ff; color: #004085; }
.price { font-size: 1.5rem; font-weight: 700; color: #e94560; }
</style>
