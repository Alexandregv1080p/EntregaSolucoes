<template>
  <div style="max-width:400px;margin:4rem auto">
    <div class="card">
      <h2 style="margin-bottom:1.25rem">Entrar</h2>
      <form @submit.prevent="submit">
        <input v-model="email" type="email" placeholder="E-mail" required />
        <input v-model="password" type="password" placeholder="Senha" required />
        <p v-if="error" style="color:red;margin-bottom:.75rem">{{ error }}</p>
        <button class="btn btn-primary" style="width:100%" :disabled="loading">
          {{ loading ? 'Entrando…' : 'Entrar' }}
        </button>
      </form>
      <p style="margin-top:1rem;text-align:center">
        Não tem conta? <RouterLink to="/register">Cadastre-se</RouterLink>
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
const email = ref(''), password = ref(''), error = ref(''), loading = ref(false)

async function submit() {
  error.value = ''
  loading.value = true
  try {
    await auth.login(email.value, password.value)
    router.push(auth.isAdmin ? '/admin' : '/')
  } catch (e: any) {
    error.value = e.response?.data?.message ?? 'Credenciais inválidas.'
  } finally {
    loading.value = false
  }
}
</script>
