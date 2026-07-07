<template>
  <div>
    <div class="card">
      <h2>🗄️ Regale</h2>
      <ul class="list">
        <li v-for="r in regale" :key="r.regalID">
          <div>
            <div class="list-info">{{ r.standort }}</div>
          </div>
          <div class="btn-group">
            <button class="btn btn-info" @click="zeigeExemplare(r.regalID)">Exemplare</button>
            <button class="btn btn-info" @click="bearbeiten(r)">Bearbeiten</button>
            <button class="btn btn-danger" @click="deleteRegal(r.regalID)">Löschen</button>
          </div>
        </li>
      </ul>
      <div v-for="(exemplare, regalId) in exemplareProRegal" :key="regalId" style="margin-top:0.75rem">
        <h3>Exemplare in {{ regalNameFuer(regalId) }}</h3>
        <ul class="list">
          <li v-for="e in exemplare" :key="e.exemplarID">
            <div>
              <div class="list-info">{{ e.buch.titel }}</div>
              <div class="list-sub">{{ e.buch.autor }}</div>
            </div>
          </li>
        </ul>
      </div>
    </div>

    <div v-if="editRegal" class="card">
      <h3>Regal bearbeiten</h3>
      <div class="form-row">
        <input v-model="editRegal.standort" placeholder="Standort"/>
        <button class="btn btn-primary" @click="updateRegal">Speichern</button>
        <button class="btn btn-danger" @click="editRegal = null">Abbrechen</button>
      </div>
    </div>

    <div class="card">
      <h3>Neues Regal</h3>
      <div class="form-row">
        <input v-model="neu.standort" placeholder="Standort"/>
        <button class="btn btn-primary" @click="addRegal">Hinzufügen</button>
      </div>
    </div>
  </div>
</template>

<script>
const BASE = 'http://localhost:8080/bibliothek/api'
export default {
  data() {
    return {
      regale: [],
      exemplareProRegal: {},
      editRegal: null,
      neu: { standort: '' }
    }
  },
  async mounted() { await this.load() },
  methods: {
    async load() {
      const res = await fetch(`${BASE}/regale`)
      this.regale = await res.json()
    },
    regalNameFuer(regalId) {
      const r = this.regale.find(r => r.regalID == regalId)
      return r ? r.standort : `Regal #${regalId}`
    },
    bearbeiten(r) {
      this.editRegal = { ...r }
    },
    async updateRegal() {
      await fetch(`${BASE}/regale`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(this.editRegal)
      })
      this.editRegal = null
      await this.load()
    },
    async addRegal() {
      await fetch(`${BASE}/regale`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(this.neu)
      })
      this.neu = { standort: '' }
      await this.load()
    },
    async deleteRegal(id) {
      await fetch(`${BASE}/regale/${id}`, { method: 'DELETE' })
      await this.load()
    },
    async zeigeExemplare(regalId) {
      if (this.exemplareProRegal[regalId]) {
        const copy = { ...this.exemplareProRegal }
        delete copy[regalId]
        this.exemplareProRegal = copy
      } else {
        const res = await fetch(`${BASE}/exemplare/regal/${regalId}`)
        this.exemplareProRegal = { ...this.exemplareProRegal, [regalId]: await res.json() }
      }
    }
  }
}
</script>