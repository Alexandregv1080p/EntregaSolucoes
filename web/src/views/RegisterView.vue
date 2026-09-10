<template>
  <div style="max-width:400px;margin:4rem auto">
    <div class="card">
      <h2 style="margin-bottom:1.25rem">Criar conta</h2>
      <form @submit.prevent="submit">
        <input v-model="name" placeholder="Nome" required />
        <input v-model="email" type="email" placeholder="E-mail" required />
        <input v-model="password" type="password" placeholder="Senha (mín. 6 caracteres)" required minlength="6" />
        <p v-if="error" style="color:red;margin-bottom:.75rem">{{ error }}</p>
        <button class="btn btn-primary" style="width:100%" :disabled="loading">
          {{ loading ? 'Cadastrando…' : 'Cadastrar' }}
        </button>
      </form>
      <p style="margin-top:1rem;text-align:center">
        Já tem conta? <RouterLink to="/login">Entrar</RouterLink>
      </p>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '../stores/auth'

const auth = useAuthStore()
const router = useRouter()
const name = ref(''), email = ref(''), password = ref('')
const error = ref(''), loading = ref(false)

async function submit() {
  error.value = ''
  loading.value = true
  try {
    await auth.register(name.value, email.value, password.value)
    router.push('/')
  } catch (e: any) {
    error.value = e.response?.data?.message ?? 'Erro ao criar conta.'
  } finally {
    loading.value = false
  }
}
</script>
