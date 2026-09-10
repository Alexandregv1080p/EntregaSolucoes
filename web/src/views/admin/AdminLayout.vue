<template>
  <div class="admin-layout">
    <aside class="sidebar">
      <p class="sidebar-title">Admin</p>
      <RouterLink to="/admin/dashboard">Dashboard</RouterLink>
      <RouterLink to="/admin/products">Produtos</RouterLink>
      <RouterLink to="/admin/orders">Pedidos</RouterLink>
      <RouterLink to="/admin/users">Usuários</RouterLink>
    </aside>
    <div class="admin-content">
      <RouterView />
    </div>
  </div>
</template>

<style scoped>
.admin-layout { display: flex; min-height: calc(100vh - 56px); margin: -2rem -1rem; }
.sidebar { width: 200px; background: #1a1a2e; padding: 1.5rem 1rem; display: flex; flex-direction: column; gap: .75rem; flex-shrink: 0; }
.sidebar-title { color: #888; font-size: .75rem; text-transform: uppercase; margin-bottom: .25rem; }
.sidebar a { color: #ccc; text-decoration: none; padding: .4rem .75rem; border-radius: 6px; }
.sidebar a:hover, .sidebar a.router-link-active { background: #e9456022; color: #e94560; }
.admin-content { flex: 1; padding: 2rem; }
</style>
