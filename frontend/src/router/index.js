import { createRouter, createWebHistory } from 'vue-router'
import BuecherView from '../views/BuecherView.vue'
import MitgliederView from '../views/MitgliederView.vue'
import RegaleView from '../views/RegaleView.vue'
import AusleiheView from '../views/AusleiheView.vue'
import ExemplarView from '../views/ExemplarView.vue'

const router = createRouter({
    history: createWebHistory(),
    routes: [
        { path: '/', component: BuecherView },
        { path: '/mitglieder', component: MitgliederView },
        { path: '/regale', component: RegaleView },
        { path: '/ausleihen', component: AusleiheView },
        { path: '/exemplare', component: ExemplarView }
    ]
})

export default router