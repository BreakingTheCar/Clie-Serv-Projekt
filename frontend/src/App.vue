<template>
  <div id="app">
    <header class="header">
      <div class="header-inner">
        <div class="logo">📚 Bibliothek</div>
        <nav class="nav">
          <router-link to="/">Bücher</router-link>
          <router-link to="/exemplare">Exemplare</router-link>
          <router-link to="/mitglieder">Mitglieder</router-link>
          <router-link to="/regale">Regale</router-link>
          <router-link to="/ausleihen">Ausleihen</router-link>
        </nav>
      </div>
    </header>
    <main class="main">
      <router-view />
    </main>
  </div>
</template>

<script>
export default {}
</script>

<style>
* { margin: 0; padding: 0; box-sizing: border-box; }

body {
  font-family: 'Segoe UI', sans-serif;
  background: #f4f6fb;
  color: #1a1a2e;
}

.header {
  background: #1a1a2e;
  padding: 0 2rem;
  position: sticky;
  top: 0;
  z-index: 100;
  box-shadow: 0 2px 12px rgba(0,0,0,0.15);
}

.header-inner {
  max-width: 1100px;
  margin: 0 auto;
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 64px;
}

.logo {
  font-size: 1.4rem;
  font-weight: 700;
  color: #fff;
  letter-spacing: 0.5px;
}

.nav {
  display: flex;
  gap: 0.5rem;
}

.nav a {
  color: #a0aec0;
  text-decoration: none;
  padding: 0.5rem 1rem;
  border-radius: 8px;
  font-size: 0.9rem;
  transition: all 0.2s;
}

.nav a:hover { background: rgba(255,255,255,0.1); color: #fff; }
.nav a.router-link-active { background: #4f46e5; color: #fff; }

.main {
  max-width: 1100px;
  margin: 2rem auto;
  padding: 0 1.5rem;
}

.card {
  background: #fff;
  border-radius: 16px;
  padding: 1.5rem;
  margin-bottom: 1.5rem;
  box-shadow: 0 1px 4px rgba(0,0,0,0.06);
}

.card h2 { font-size: 1.2rem; font-weight: 600; margin-bottom: 1rem; color: #1a1a2e; }
.card h3 { font-size: 1rem; font-weight: 600; margin-bottom: 0.75rem; color: #4f46e5; }

.list { list-style: none; }
.list li {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0.75rem 1rem;
  border-radius: 10px;
  margin-bottom: 0.5rem;
  background: #f8f9ff;
  transition: background 0.2s;
}
.list li:hover { background: #eef0ff; }

.list-info { font-size: 0.9rem; color: #333; }
.list-sub { font-size: 0.78rem; color: #888; margin-top: 2px; }

.btn {
  padding: 0.45rem 1rem;
  border: none;
  border-radius: 8px;
  font-size: 0.82rem;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.2s;
}

.btn-primary { background: #4f46e5; color: #fff; }
.btn-primary:hover { background: #4338ca; }
.btn-danger { background: #fee2e2; color: #dc2626; }
.btn-danger:hover { background: #fecaca; }
.btn-success { background: #d1fae5; color: #059669; }
.btn-success:hover { background: #a7f3d0; }
.btn-info { background: #e0f2fe; color: #0284c7; }
.btn-info:hover { background: #bae6fd; }

.btn-group { display: flex; gap: 0.4rem; }

.form-row {
  display: flex;
  gap: 0.75rem;
  flex-wrap: wrap;
  align-items: center;
}

input {
  padding: 0.5rem 0.85rem;
  border: 1.5px solid #e2e8f0;
  border-radius: 8px;
  font-size: 0.88rem;
  outline: none;
  transition: border 0.2s;
  background: #f8f9ff;
}

input:focus { border-color: #4f46e5; background: #fff; }

.badge {
  display: inline-block;
  padding: 0.2rem 0.6rem;
  border-radius: 20px;
  font-size: 0.75rem;
  font-weight: 600;
}

.badge-blue { background: #dbeafe; color: #1d4ed8; }
.badge-green { background: #d1fae5; color: #065f46; }
.badge-orange { background: #ffedd5; color: #c2410c; }

.msg { padding: 0.6rem 1rem; border-radius: 8px; font-size: 0.85rem; margin-top: 0.5rem; }
.msg-success { background: #d1fae5; color: #065f46; }
.msg-error { background: #fee2e2; color: #dc2626; }

.divider { border: none; border-top: 1.5px solid #f0f0f0; margin: 1.2rem 0; }
</style>