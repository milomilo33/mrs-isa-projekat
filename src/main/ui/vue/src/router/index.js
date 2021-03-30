import Vue from 'vue'
import VueRouter from 'vue-router'
import PharmacyPreview from '../views/PharmacyPreview'
import SystemAdminPage from '../views/SystemAdminPage'
import PharmacyRegistration from '../components/PharmacyRegistration'
import PharmacistPage from '../views/PharmacistPage'

Vue.use(VueRouter)

const routes = [
	{
		path: "/PharmacyPreview",
		name: "PharmacyPreview",
		component: PharmacyPreview
	},
	{
		path: "/SystemAdminPage",
		name: "SystemAdminPage",
		component: SystemAdminPage,
		children: [
			{
				path: "pharmacyRegistration",
				component: PharmacyRegistration
			}
		]
	},
	{
		path: "/Pharmacistpage",
		name: "PharmacistPage",
		component: PharmacistPage/*,
		children: [
			{
				path: "*"
				component: //ime home page komponente
			}
		]*/
	}
]

const router = new VueRouter({
	mode: 'history',
	base: process.env.BASE_URL,
	routes
})

export default router