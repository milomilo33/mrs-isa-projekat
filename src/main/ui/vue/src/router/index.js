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
import PharmacistTable from '../components/PharmacistTable'
import MedicamentRegistration from '../components/MedicamentRegistration'
import MedicamentListPreview from '../components/MedicamentListPreview'
import UnregisteredPage from '../views/UnregisteredPage'
import PatientRegistration from '../components/PatientRegistration'
import MedicamentTable from '../components/MedicamentTable'
import DermatologistTable from '../components/DermatologistTable'
import SearchPatients from '../components/SearchPatients'
import DispenseMedication from '../components/DispenseMedication'
import PricelistTable from '../components/PricelistTable'
import DermatologistPage from '../views/DermatologistPage'
import ExaminationSearch from '../components/ExaminationSearch'
import AppointmentPage from '../components/AppointmentPage'

Vue.use(VueRouter)

const routes = [
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
			},
			{
				path: "SearchPatients",
				component: SearchPatients
			},
			{
				path: "DispenseMedication",
				component: DispenseMedication
			}
		]
	},
	{
		path: "/DermatologistPage",
		name: "DermatologistPage",
		component: DermatologistPage,
		children: [
			{
				path: "PharmacyList",
				name: "DermatologistPagePharmacyList",
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
				path: "SearchPatients",
				component: SearchPatients
			},
			{
				path: "ExaminationSearch",
				component: ExaminationSearch
			},
			{
				path: "AppointmentPage",
				name: "DermatologistPageAppointmentPage",
				component: AppointmentPage,
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
				path: "PharmacistTable",
				component:PharmacistTable
			},
			{	
				path : "DermatologistTable",
				component: DermatologistTable
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
			},
			{
				path: "PricelistTable",
				component: PricelistTable,
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