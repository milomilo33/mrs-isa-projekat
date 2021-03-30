import Vue from 'vue'
import VueRouter from 'vue-router'
import PharmacyPreview from '../views/PharmacyPreview'
import SystemAdminPage from '../views/SystemAdminPage'
import PharmacyRegistration from '../components/PharmacyRegistration'
import UserRegistration from '../components/UserRegistration'

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
			},
			{
				path: "userRegister/:userRole",
				component: UserRegistration
			}
	]
	}
]

const router = new VueRouter({
	mode: 'history',
	base: process.env.BASE_URL,
	routes
})

export default router