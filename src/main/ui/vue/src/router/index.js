import Vue from 'vue'
import VueRouter from 'vue-router'
import PharmacyPreview from '../views/PharmacyPreview'
import SystemAdminPage from '../views/SystemAdminPage'
import PharmacyRegistration from '../components/PharmacyRegistration'
import UserRegistration from '../components/UserRegistration'
import PharmacistPage from '../views/PharmacistPage'
import PharmacyListPreview from '../views/PharmacyListPreview'
import PharmacistRegistration from '../components/PharmacistRegistration'
import PharmacyAdminPage from '../views/PharmacyAdminPage'
import EmployeeTable from '../components/EmployeeTable'
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
	},
	{
		path: "/PharmacyList",
		name: "PharmacistListPreview",
		component: PharmacyListPreview,
	},
	{
		path : "/PharmacyAdminPage",
		name: "PharmacyAdminPage",
		component: PharmacyAdminPage,
		children:[
			{
				path: "EmployeeTable",
				component:EmployeeTable
			},
			{
				path:"PharmacistRegistration",
				component:PharmacistRegistration
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