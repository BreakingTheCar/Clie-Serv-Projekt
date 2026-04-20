<template>
  <div>
    <div class="card">
      <h2>📄 Alle Exemplare</h2>
      <ul class="list">
        <li v-for="e in exemplare" :key="e.exemplarID">
          <div>
            <div class="list-info">{{ e.buch.titel }}</div>
            <div class="list-sub">{{ e.buch.autor }} · {{ e.regal.standort }}</div>
          </div>
          <div class="btn-group">
            <button class="btn btn-info" @click="zeigeUmzug(e.exemplarID)">Umziehen</button>
            <button class="btn btn-danger" @click="deleteExemplar(e.exemplarID)">Löschen</button>
          </div>
        </li>
      </ul>

      <div v-if="umzugId" class="card" style="margin-top:0.75rem">
        <h3>Exemplar umziehen</h3>
        <div class="form-row">
          <select v-model="neuesRegal" class="select">
            <option disabled value="">Regal auswählen</option>
            <option v-for="r in regale" :key="r.regalID" :value="r.regalID">
              {{ r.standort }}
            </option>
          </select>
          <button class="btn btn-primary" @click="moveToRegal(umzugId)">Bestätigen</button>
          <button class="btn btn-danger" @click="umzugId = null">Abbrechen</button>
        </div>
      </div>
    </div>

    <div class="card">
      <h3>Verfügbare Exemplare</h3>
      <button class="btn btn-info" @click="ladeVerfuegbare">Anzeigen</button>
      <ul class="list" style="margin-top:0.75rem" v-if="verfuegbare.length > 0">
        <li v-for="e in verfuegbare" :key="e.exemplarID">
          <div>
            <div class="list-info">{{ e.buch.titel }}</div>
            <div class="list-sub">{{ e.buch.autor }} · {{ e.regal.standort }}</div>
          </div>
          <span class="badge badge-green">Verfügbar</span>
        </li>
      </ul>
    </div>

    <div class="card">
      <h3>Neues Exemplar hinzufügen</h3>
      <div class="form-row">
        <select v-model="neuesBuch" class="select">
          <option disabled value="">Buch auswählen</option>
          <option v-for="b in buecher" :key="b.isbn" :value="b.isbn">
            {{ b.titel }} – {{ b.autor }}
          </option>
        </select>
        <select v-model="neuesRegalAdd" class="select">
          <option disabled value="">Regal auswählen</option>
          <option v-for="r in regale" :key="r.regalID" :value="r.regalID">
            {{ r.standort }}
          </option>
        </select>
        <button class="btn btn-primary" @click="addExemplar">Hinzufügen</button>
      </div>
    </div>
  </div>
</template>

<script>
const BASE = 'http://localhost:8080/bibliothek/api'
export default {
  data() {
    return {
      exemplare: [],
      verfuegbare: [],
      regale: [],
      buecher: [],
      umzugId: null,
      neuesRegal: '',
      neuesBuch: '',
      neuesRegalAdd: ''
    }
  },
  async mounted() {
    await this.load()
    const [r, b] = await Promise.all([
      fetch(`${BASE}/regale`).then(r => r.json()),
      fetch(`${BASE}/buecher`).then(r => r.json())
    ])
    this.regale = r
    this.buecher = b
  },
  methods: {
    async load() {
      const res = await fetch(`${BASE}/exemplare`)
      this.exemplare = await res.json()
    },
    async addExemplar() {
      await fetch(`${BASE}/exemplare`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          buch: { isbn: this.neuesBuch },
          regal: { regalID: this.neuesRegalAdd }
        })
      })
      this.neuesBuch = ''
      this.neuesRegalAdd = ''
      await this.load()
    },
    async deleteExemplar(id) {
      await fetch(`${BASE}/exemplare/${id}`, { method: 'DELETE' })
      await this.load()
    },
    zeigeUmzug(id) {
      this.umzugId = this.umzugId === id ? null : id
    },
    async moveToRegal(exemplarId) {
      await fetch(`${BASE}/exemplare/move/${exemplarId}/${this.neuesRegal}`, { method: 'PUT' })
      this.umzugId = null
      this.neuesRegal = ''
      await this.load()
    },
    async ladeVerfuegbare() {
      const res = await fetch(`${BASE}/exemplare/available`)
      this.verfuegbare = await res.json()
    }
  }
}
</script>

<style scoped>
.select {
  padding: 0.5rem 0.85rem;
  border: 1.5px solid #e2e8f0;
  border-radius: 8px;
  font-size: 0.88rem;
  background: #f8f9ff;
  outline: none;
  cursor: pointer;
}
.select:focus { border-color: #4f46e5; background: #fff; }
</style>