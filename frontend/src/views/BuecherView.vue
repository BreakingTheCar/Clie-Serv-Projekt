<template>
  <div>
    <div class="card">
      <h2>📖 Alle Bücher</h2>
      <input v-model="suche" placeholder="Suche nach Titel oder Autor..." style="width:100%;margin-bottom:0.75rem"/>
      <ul class="list">
        <li v-for="b in gefilterteBuecherListe" :key="b.isbn">
          <div>
            <div class="list-info">{{ b.titel }}</div>
            <div class="list-sub">{{ b.autor }} · <span class="badge badge-blue">{{ b.kategorie }}</span></div>
          </div>
          <button class="btn btn-danger" @click="deleteBuch(b.isbn)">Löschen</button>
        </li>
      </ul>
    </div>

    <div class="card">
      <h3>Verfügbare Bücher nach Regal</h3>
      <div class="form-row">
        <select v-model="regalFilter" class="select">
          <option disabled value="">Regal auswählen</option>
          <option v-for="r in regale" :key="r.regalID" :value="r.regalID">
            {{ r.standort }}
          </option>
        </select>
        <button class="btn btn-info" @click="filterByRegal">Filtern</button>
      </div>
      <ul class="list" style="margin-top:0.75rem" v-if="gefilterteBuecher.length > 0">
        <li v-for="b in gefilterteBuecher" :key="b.isbn">
          <div>
            <div class="list-info">{{ b.titel }}</div>
            <div class="list-sub">{{ b.autor }}</div>
          </div>
        </li>
      </ul>
    </div>

    <div class="card">
      <h3>Neues Buch hinzufügen</h3>
      <div class="form-row">
        <input v-model="neu.isbn" placeholder="ISBN" type="number"/>
        <input v-model="neu.titel" placeholder="Titel"/>
        <input v-model="neu.autor" placeholder="Autor"/>
        <input v-model="neu.kategorie" placeholder="Kategorie"/>
        <button class="btn btn-primary" @click="addBuch">Hinzufügen</button>
      </div>
    </div>
  </div>
</template>

<script>
const BASE = 'http://localhost:8080/bibliothek/api'
export default {
  data() {
    return {
      buecher: [],
      gefilterteBuecher: [],
      regale: [],
      regalFilter: '',
      suche: '',
      neu: { isbn: '', titel: '', autor: '', kategorie: '' }
    }
  },
  computed: {
    gefilterteBuecherListe() {
      if (!this.suche) return this.buecher
      const s = this.suche.toLowerCase()
      return this.buecher.filter(b =>
          b.titel.toLowerCase().includes(s) || b.autor.toLowerCase().includes(s)
      )
    }
  },
  async mounted() {
    await this.load()
    const res = await fetch(`${BASE}/regale`)
    this.regale = await res.json()
  },
  methods: {
    async load() {
      const res = await fetch(`${BASE}/buecher`)
      this.buecher = await res.json()
    },
    async addBuch() {
      await fetch(`${BASE}/buecher`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ ...this.neu, isbn: Number(this.neu.isbn) })
      })
      this.neu = { isbn: '', titel: '', autor: '', kategorie: '' }
      await this.load()
    },
    async deleteBuch(isbn) {
      await fetch(`${BASE}/buecher/${isbn}`, { method: 'DELETE' })
      await this.load()
    },
    async filterByRegal() {
      const res = await fetch(`${BASE}/buecher/regal/${this.regalFilter}`)
      this.gefilterteBuecher = await res.json()
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