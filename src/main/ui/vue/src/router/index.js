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
import MedicamentRegistration from '../components/MedicamentRegistration'
import MedicamentListPreview from '../components/MedicamentListPreview'
import UnregisteredPage from '../views/UnregisteredPage'
import PatientRegistration from '../components/PatientRegistration'
import MedicamentTable from '../components/MedicamentTable'

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
		path: "/MedicamentList",
		name: "MedicamentListPreview",
		component: MedicamentListPreview
	},
	{
		path: "/SystemAdminPage",
		name: "SystemAdminPage",
		component: SystemAdminPage,
		children: [
			{
				path: "MedicamentList",
				component: MedicamentListPreview
			},
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
			},
			{
				path: "MedicamentRegistration",
				component: MedicamentRegistration
			}
		]
	},
	{
		path: "/UnregisteredPage",
		component: UnregisteredPage,
		children: [
			{
				path: "PharmacyList",
				component: PharmacyListPreview
			},
			{
				path: "MedicamentList",
				component: MedicamentListPreview
			},
			{
				path: "PharmacyList/:query",
				component: PharmacyListPreview,
				props: true
			},
			{
				path: "PatientRegistration",
				component: PatientRegistration
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
				component: PharmacyListPreview
			},
			{
				path: "MedicamentList",
				component: MedicamentListPreview
			},
			{
				path: "PharmacyList/:query",
				component: PharmacyListPreview,
				props: true
			}
		]
	},
	{
		path : "/PharmacyAdminPage",
		name: "PharmacyAdminPage",
		component: PharmacyAdminPage,
		children:[
			{
				path: "pharmacyList",
				component: PharmacyListPreview
			},
			{
				path: "medicamentList",
				component: MedicamentListPreview
			},
			{
				path: "MedicamentTable",
				component: MedicamentTable
			}
			,
			{
				path: "EmployeeTable",
				component:EmployeeTable
			},
			{
				path:"PharmacistRegistration",
				component:PharmacistRegistration
			},
			{
				path: "pharmacyList",
				component: PharmacyListPreview,
			},
			{
				path: "pharmacyList/:query",
				component: PharmacyListPreview,
				props: true
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