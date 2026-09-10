<template>
  <div>
    <h2 style="margin-bottom:1.5rem">Produtos</h2>

    <form class="card" style="margin-bottom:1.5rem" @submit.prevent="create">
      <h3 style="margin-bottom:1rem">Novo produto</h3>
      <input v-model="form.title" placeholder="Título" required />
      <textarea v-model="form.description" placeholder="Descrição" rows="3" style="width:100%;padding:.5rem .75rem;border:1px solid #ddd;border-radius:6px;margin-bottom:.75rem" />
      <input v-model="form.price" type="number" step="0.01" placeholder="Preço" required />
      <select v-model="form.kind" style="width:100%;padding:.5rem .75rem;border:1px solid #ddd;border-radius:6px;margin-bottom:.75rem">
        <option value="SALE">Venda</option>
        <option value="TRADE">Troca</option>
      </select>
      <button class="btn btn-primary" :disabled="saving">{{ saving ? 'Salvando…' : 'Criar' }}</button>
      <span v-if="createError" style="color:red;margin-left:.75rem">{{ createError }}</span>
    </form>

    <p v-if="loading">Carregando…</p>
    <table v-else class="card" style="width:100%;border-collapse:collapse">
      <thead>
        <tr style="border-bottom:1px solid #eee;text-align:left">
          <th style="padding:.5rem">ID</th><th>Título</th><th>Tipo</th><th>Preço</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="p in products" :key="p.id" style="border-bottom:1px solid #f5f5f5">
          <td style="padding:.5rem">{{ p.id }}</td>
          <td>{{ p.title }}</td>
          <td>{{ p.kind }}</td>
          <td>R$ {{ Number(p.price).toFixed(2) }}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, reactive } from 'vue'
import http from '../../api/http'

const products = ref<any[]>([])
const loading = ref(true)
const saving = ref(false)
const createError = ref('')
const form = reactive({ title: '', description: '', price: '', kind: 'SALE' })

onMounted(async () => {
  const { data } = await http.get('/api/products')
  products.value = data
  loading.value = false
})

async function create() {
  saving.value = true; createError.value = ''
  try {
    const { data } = await http.post('/api/products', { ...form, price: Number(form.price) })
    products.value.unshift(data)
    form.title = ''; form.description = ''; form.price = ''; form.kind = 'SALE'
  } catch (e: any) {
    createError.value = 'Erro ao criar produto.'
  } finally {
    saving.value = false
  }
}
</script>
