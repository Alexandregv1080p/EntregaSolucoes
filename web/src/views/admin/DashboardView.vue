<template>
  <div>
    <h2 style="margin-bottom:1.5rem">Dashboard</h2>
    <div class="stats">
      <div class="stat-card" v-for="s in stats" :key="s.label">
        <p class="stat-value">{{ s.value }}</p>
        <p class="stat-label">{{ s.label }}</p>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import http from '../../api/http'

const stats = ref([
  { label: 'Produtos', value: '—' },
  { label: 'Usuários', value: '—' },
  { label: 'Pedidos', value: '—' },
])

onMounted(async () => {
  try {
    const [p, u, o] = await Promise.all([
      http.get('/api/products'),
      http.get('/api/admin/users'),
      http.get('/api/admin/orders'),
    ])
    stats.value[0].value = p.data.length
    stats.value[1].value = u.data.length
    stats.value[2].value = o.data.length
  } catch { /* admin endpoints ainda não implementados */ }
})
</script>

<style scoped>
.stats { display: grid; grid-template-columns: repeat(auto-fill, minmax(160px, 1fr)); gap: 1rem; }
.stat-card { background: #fff; border-radius: 8px; padding: 1.5rem; text-align: center;
             box-shadow: 0 1px 4px #0001; }
.stat-value { font-size: 2rem; font-weight: 700; color: #e94560; }
.stat-label { color: #888; margin-top: .25rem; font-size: .9rem; }
</style>
