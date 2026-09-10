import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import http from '../api/http'

interface AuthUser { name: string; email: string; role: string }

export const useAuthStore = defineStore('auth', () => {
  const token = ref<string | null>(localStorage.getItem('token'))
  const user = ref<AuthUser | null>(JSON.parse(localStorage.getItem('user') ?? 'null'))

  const isAuthenticated = computed(() => !!token.value)
  const isAdmin = computed(() => user.value?.role === 'ADMIN')

  async function login(email: string, password: string) {
    const { data } = await http.post('/api/auth/login', { email, password })
    token.value = data.token
    user.value = { name: data.name, email: data.email, role: data.role }
    localStorage.setItem('token', data.token)
    localStorage.setItem('user', JSON.stringify(user.value))
  }

  async function register(name: string, email: string, password: string) {
    const { data } = await http.post('/api/auth/register', { name, email, password })
    token.value = data.token
    user.value = { name: data.name, email: data.email, role: data.role }
    localStorage.setItem('token', data.token)
    localStorage.setItem('user', JSON.stringify(user.value))
  }

  function logout() {
    token.value = null
    user.value = null
    localStorage.removeItem('token')
    localStorage.removeItem('user')
  }

  return { token, user, isAuthenticated, isAdmin, login, register, logout }
})
