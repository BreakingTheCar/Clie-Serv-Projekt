<template>
  <div>
    <div class="card">
      <h2>👤 Alle Mitglieder</h2>
      <input v-model="suche" placeholder="Suche nach Name oder Email..." style="width:100%;margin-bottom:0.75rem"/>
      <ul class="list">
        <li v-for="m in gefilterteMitglieder" :key="m.mitgliedID">
          <div>
            <div class="list-info">{{ m.name }}</div>
            <div class="list-sub">{{ m.email }}</div>
          </div>
        </li>
      </ul>
    </div>

    <div class="card">
      <h3>Mitglieder mit aktiven Ausleihen</h3>
      <button class="btn btn-info" @click="ladeAktive">Anzeigen</button>
      <ul class="list" style="margin-top:0.75rem" v-if="aktiveMitglieder.length > 0">
        <li v-for="m in aktiveMitglieder" :key="m.mitgliedID">
          <div>
            <div class="list-info">{{ m.name }}</div>
            <div class="list-sub">{{ m.email }}</div>
          </div>
          <span class="badge badge-orange">Aktive Ausleihe</span>
        </li>
      </ul>
    </div>

    <div class="card">
      <h3>Neues Mitglied</h3>
      <div class="form-row">
        <input v-model="neu.name" placeholder="Name"/>
        <input v-model="neu.email" placeholder="Email"/>
        <button class="btn btn-primary" @click="addMitglied">Hinzufügen</button>
      </div>
    </div>
  </div>
</template>

<script>
const BASE = 'http://localhost:8080/bibliothek/api'
export default {
  data() {
    return {
      mitglieder: [],
      aktiveMitglieder: [],
      suche: '',
      neu: { name: '', email: '' }
    }
  },
  computed: {
    gefilterteMitglieder() {
      if (!this.suche) return this.mitglieder
      const s = this.suche.toLowerCase()
      return this.mitglieder.filter(m =>
          m.name.toLowerCase().includes(s) || m.email.toLowerCase().includes(s)
      )
    }
  },
  async mounted() { await this.load() },
  methods: {
    async load() {
      const res = await fetch(`${BASE}/mitglieder`)
      this.mitglieder = await res.json()
    },
    async addMitglied() {
      await fetch(`${BASE}/mitglieder`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(this.neu)
      })
      this.neu = { name: '', email: '' }
      await this.load()
    },
    async ladeAktive() {
      const res = await fetch(`${BASE}/mitglieder/loans`)
      this.aktiveMitglieder = await res.json()
    }
  }
}
</script>