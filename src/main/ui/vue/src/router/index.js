import Vue from 'vue'
import VueRouter from 'vue-router'
import PharmacyPreview from '../views/PharmacyPreview'
import SystemAdminPage from '../views/SystemAdminPage'
import PharmacyRegistration from '../components/PharmacyRegistration'
import UserRegistration from '../components/UserRegistration'
import PharmacistPage from '../views/PharmacistPage'
import PharmacyListPreview from '../views/PharmacyListPreview'
import PharmacistRegistration from '../views/PharmacistRegistration'

Vue.use(VueRouter)

const routes = [
	{
		path: "/PharmacistRegistration",
		name: "PharmacistRegistration",
		component: PharmacistRegistration
	},
	{
		path: "/PharmacyPreview/:id",
		name: "PharmacyPreview",
		component: PharmacyPreview,
		props: true
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
			},
			{
				path: "PharmacyList",
				component: PharmacyListPreview,
			},
			{
				path: "PharmacyList/:query",
				component: PharmacyListPreview,
				props: true
			}
		]
	},
	{
		path: "/PharmacistPage",
		name: "PharmacistPage",
		component: PharmacistPage,
		children: [
			{
				path: "PharmacyList",
				component: PharmacyListPreview,
			},
			{
				path: "PharmacyList/:query",
				component: PharmacyListPreview,
				props: true
			}
		]
	},
	{
		path: "/PharmacyList/:query",
		name: "PharmacyList",
		component: PharmacyListPreview,
		props: true
	},

]

const router = new VueRouter({
	mode: 'history',
	base: process.env.BASE_URL,
	routes
})

export default router