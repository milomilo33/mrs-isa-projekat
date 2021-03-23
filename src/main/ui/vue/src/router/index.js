import Vue from 'vue'
import VueRouter from 'vue-router'
import PharmacyRegistration from '../views/PharmacyRegistration'

Vue.use(VueRouter)

const routes = [
	{
		path: "/registerPharmacy",
		name: 'PharmacyRegistration',
		component: PharmacyRegistration
	}
]

const router = new VueRouter({
	mode: 'history',
	base: process.env.BASE_URL,
	routes
})

export default router