import { createRouter, createWebHistory } from 'vue-router'
import { useAuthStore } from '../stores/auth'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    { path: '/', component: () => import('../views/HomeView.vue') },
    { path: '/login', component: () => import('../views/LoginView.vue') },
    { path: '/register', component: () => import('../views/RegisterView.vue') },
    { path: '/products/:id', component: () => import('../views/ProductView.vue') },
    {
      path: '/cart',
      component: () => import('../views/CartView.vue'),
      meta: { requiresAuth: true }
    },
    {
      path: '/orders',
      component: () => import('../views/OrdersView.vue'),
      meta: { requiresAuth: true }
    },
    {
      path: '/admin',
      component: () => import('../views/admin/AdminLayout.vue'),
      meta: { requiresAuth: true, requiresAdmin: true },
      children: [
        { path: '', redirect: '/admin/dashboard' },
        { path: 'dashboard', component: () => import('../views/admin/DashboardView.vue') },
        { path: 'products', component: () => import('../views/admin/ProductsView.vue') },
        { path: 'orders', component: () => import('../views/admin/OrdersView.vue') },
        { path: 'users', component: () => import('../views/admin/UsersView.vue') },
      ]
    },
  ]
})

router.beforeEach((to, _from, next) => {
  const auth = useAuthStore()
  if (to.meta.requiresAuth && !auth.isAuthenticated) return next('/login')
  if (to.meta.requiresAdmin && !auth.isAdmin) return next('/')
  next()
})

export default router
