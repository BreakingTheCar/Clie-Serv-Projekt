<template>
  <div>
    <div class="card">
      <h2>📋 Aktive Ausleihen</h2>
      <ul class="list">
        <li v-for="a in ausleihen" :key="a.ausleiheID">
          <div>
            <div class="list-info">
              {{ a.exemplar?.buch?.titel || 'Unbekanntes Buch' }}
            </div>
            <div class="list-sub">
              {{ a.mitglied?.name || 'Unbekannt' }} · seit {{ formatDatum(a.ausleihdatum) }}
            </div>
          </div>
          <button class="btn btn-success" @click="returnBook(a.ausleiheID)">Zurückgeben</button>
        </li>
      </ul>
      <p v-if="ausleihen.length === 0" style="color:#888;font-size:0.9rem">Keine aktiven Ausleihen.</p>
    </div>

    <div class="card">
      <h3>Buch ausleihen</h3>
      <div class="form-row">
        <select v-model="exemplarId" class="select">
          <option disabled value="">Exemplar auswählen</option>
          <option v-for="e in verfuegbareExemplare" :key="e.exemplarID" :value="e.exemplarID">
            {{ e.buch.titel }} · {{ e.regal.standort }}
          </option>
        </select>
        <select v-model="mitgliedId" class="select">
          <option disabled value="">Mitglied auswählen</option>
          <option v-for="m in mitglieder" :key="m.mitgliedID" :value="m.mitgliedID">
            {{ m.name }} · {{ m.email }}
          </option>
        </select>
        <button class="btn btn-primary" @click="borrow">Ausleihen</button>
      </div>
      <p v-if="message" :class="['msg', message.startsWith('Fehler') ? 'msg-error' : 'msg-success']">
        {{ message }}
      </p>
    </div>

    <div class="card">
      <h2>📦 Abgeschlossene Ausleihen</h2>
      <button class="btn btn-info" @click="toggleHistory">
        {{ zeigeHistory ? 'Ausblenden' : 'Anzeigen' }}
      </button>
      <ul class="list" style="margin-top:0.75rem" v-if="zeigeHistory">
        <li v-for="a in abgeschlossen" :key="a.ausleiheID">
          <div>
            <div class="list-info">{{ a.exemplar?.buch?.titel || 'Unbekannt' }}</div>
            <div class="list-sub">
              {{ a.mitglied?.name }} · {{ formatDatum(a.ausleihdatum) }} → {{ formatDatum(a.rueckgabedatum) }}
            </div>
          </div>
          <span class="badge badge-green">Abgeschlossen</span>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
const BASE = 'http://localhost:8080/bibliothek/api'
export default {
  data() {
    return {
      ausleihen: [],
      abgeschlossen: [],
      verfuegbareExemplare: [],
      mitglieder: [],
      exemplarId: '',
      mitgliedId: '',
      message: '',
      zeigeHistory: false
    }
  },
  async mounted() {
    await this.load()
    const [e, m] = await Promise.all([
      fetch(`${BASE}/exemplare/available`).then(r => r.json()),
      fetch(`${BASE}/mitglieder`).then(r => r.json())
    ])
    this.verfuegbareExemplare = e
    this.mitglieder = m
  },
  methods: {
    async load() {
      const res = await fetch(`${BASE}/ausleihen/active`)
      this.ausleihen = await res.json()
    },
    async toggleHistory() {
      this.zeigeHistory = !this.zeigeHistory
      if (this.zeigeHistory && this.abgeschlossen.length === 0) {
        const res = await fetch(`${BASE}/ausleihen`)
        const alle = await res.json()
        this.abgeschlossen = alle.filter(a => a.rueckgabedatum !== null)
      }
    },
    async borrow() {
      const res = await fetch(`${BASE}/ausleihen/borrow/${this.exemplarId}/${this.mitgliedId}`, {
        method: 'POST'
      })
      if (res.ok) {
        this.message = 'Erfolgreich ausgeliehen!'
        this.exemplarId = ''
        this.mitgliedId = ''
        const e = await fetch(`${BASE}/exemplare/available`).then(r => r.json())
        this.verfuegbareExemplare = e
        await this.load()
      } else {
        this.message = 'Fehler: ' + await res.text()
      }
    },
    async returnBook(id) {
      await fetch(`${BASE}/ausleihen/return/${id}`, { method: 'PUT' })
      this.abgeschlossen = []
      const e = await fetch(`${BASE}/exemplare/available`).then(r => r.json())
      this.verfuegbareExemplare = e
      await this.load()
    },
    formatDatum(datum) {
      if (Array.isArray(datum)) return `${datum[2]}.${datum[1]}.${datum[0]}`
      return datum
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