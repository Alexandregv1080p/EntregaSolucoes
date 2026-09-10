<template>
  <div>
    <h1 style="margin-bottom:1.5rem">Meus Pedidos</h1>
    <p v-if="loading">Carregando…</p>
    <p v-else-if="orders.length === 0">Nenhum pedido ainda.</p>
    <div v-for="o in orders" :key="o.id" class="card" style="margin-bottom:.75rem">
      <strong>#{{ o.id }}</strong> — {{ o.status }}
      <span style="float:right">R$ {{ Number(o.total).toFixed(2) }}</span>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import http from '../api/http'

const orders = ref<any[]>([])
const loading = ref(true)

onMounted(async () => {
  try {
    const { data } = await http.get('/api/orders')
    orders.value = data
  } finally {
    loading.value = false
  }
})
</script>
