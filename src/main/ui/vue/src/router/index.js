import Vue from 'vue'
import VueRouter from 'vue-router'
import PharmacyPreview from '../views/PharmacyPreview'
import SystemAdminPage from '../views/SystemAdminPage'
import PharmacyRegistration from '../components/PharmacyRegistration'
import UserRegistration from '../components/UserRegistration'
import PharmacistPage from '../views/PharmacistPage'
import PharmacistRegistration from '../views/PharmacistRegistration'
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
		path: "/PharmacyAdminPage",
		name: "PharmacyAdminPage",
		component: PharmacyAdminPage,
		children:[
			{
				path:"EmployeeTable",
				component:EmployeeTable
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