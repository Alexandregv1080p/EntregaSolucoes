import axios from 'axios'

const http = axios.create({ baseURL: import.meta.env.VITE_API_URL ?? 'http://localhost:8080' })

// Injeta JWT em toda requisição autenticada
http.interceptors.request.use(cfg => {
  const token = localStorage.getItem('token')
  if (token) cfg.headers.Authorization = `Bearer ${token}`
  return cfg
})

// 401 → limpa sessão e redireciona para login
http.interceptors.response.use(
  r => r,
  err => {
    if (err.response?.status === 401) {
      localStorage.removeItem('token')
      window.location.href = '/login'
    }
    return Promise.reject(err)
  }
)

export default http
