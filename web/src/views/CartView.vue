<template>
  <div>
    <h1 style="margin-bottom:1.5rem">Carrinho</h1>
    <p v-if="items.length === 0">Seu carrinho está vazio.</p>
    <div v-else>
      <div v-for="item in items" :key="item.id" class="card" style="display:flex;justify-content:space-between;align-items:center;margin-bottom:.75rem">
        <div>
          <strong>{{ item.title }}</strong>
          <p>R$ {{ Number(item.price).toFixed(2) }} × {{ item.qty }}</p>
        </div>
        <div style="display:flex;gap:.5rem;align-items:center">
          <button class="btn btn-outline" @click="dec(item)">−</button>
          <span>{{ item.qty }}</span>
          <button class="btn btn-outline" @click="item.qty++; save()">+</button>
          <button class="btn" style="color:red;border-color:red" @click="remove(item)">✕</button>
        </div>
      </div>
      <div class="card" style="text-align:right;margin-top:1rem">
        <strong>Total: R$ {{ total }}</strong>
        <br><button class="btn btn-primary" style="margin-top:.75rem">Finalizar pedido</button>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue'

const items = ref<any[]>(JSON.parse(localStorage.getItem('cart') ?? '[]'))

const total = computed(() =>
  items.value.reduce((s, i) => s + Number(i.price) * i.qty, 0).toFixed(2)
)

function save() { localStorage.setItem('cart', JSON.stringify(items.value)) }

function dec(item: any) {
  if (item.qty > 1) { item.qty--; save() } else remove(item)
}

function remove(item: any) {
  items.value = items.value.filter(i => i.id !== item.id)
  save()
}
</script>
