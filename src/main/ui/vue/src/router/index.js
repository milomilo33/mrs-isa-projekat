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
import Login from '../views/Login'
import Logout from '../views/Logout'
import PatientPage from '../views/PatientPage'
import SuccessActivation from '../views/SuccessActivation'
import ReservedMedicamentsTable from '../components/ReservedMedicamentsTable'
// import PatientInfo from '../components/PatientInfo'
import UnansweredComplaint from '../components/AllUnansweredComplaints'
import ReservedAppointmentsTable from '../components/ReservedAppointmentsTable'
import WriteComplaint from '../components/WriteComplaint'
import FailedActivation from '../views/FailedActivation'
import ExaminationSearch from '../components/ExaminationSearch'
import AppointmentPage from '../components/AppointmentPage'
import ExaminedPatients from '../components/ExaminedPatients'
import PharmacyAdminRegistration from '../components/PharmacyAdminRegistration'
import AppointmentTable from '../components/AppointmentTable'
import MedicamentInPharmacy from '../components/MedicamentInPharmacy'
import Profile from '../components/Profile'
import RequestMedicaments from '../components/RequestMedicaments'
import PricelistAppointments from '../components/PricelistAppointments'
import SupplierOfferList from '../components/SupplierOfferList'
import SupplierOrderList from '../components/SupplierOrderList'
import SupplierPage from '../views/SupplierPage'
import Orders from '../components/Orders'
import ChangePassword from '../components/ChangePassword'
import PastAppointmentsPage from '../components/PastAppointmentsPage'
//import ePrescriptionPreview from '../components/ePrescriptionPreview.vue'

//import ePrescriptionPreview from '../components/ePrescriptionPreview.vue'

import ReportAppointments from '../components/ReportAppointments'
import ReportMedicaments from '../components/ReportMedicaments'
//import { component } from 'vue/types/umd'

Vue.use(VueRouter)
const Role = {
	AdminPharmacy: 'ROLE_PHARMACY_ADMIN',
	Dermatologist: 'ROLE_DERMATOLOGIST',
	Pharmacist: 'ROLE_PHARMACIST',
	SystemAdmin: 'ROLE_SYSTEM_ADMIN',
	Supplier: 'ROLE_SUPPLIER',
	Patient:'ROLE_PATIENT'

}
const routes = [

	{
		path: "/",
		name: UnregisteredPage,
		component: UnregisteredPage
	},
	{
		path: "/Login",
		name: "Login",
		component: Login
	},
	{
		path: "/SuccessActivation",
		name: "SuccessActivation",
		component: SuccessActivation
	},
	{
		path: "/FailedActivation",
		name: "FailedActivation",
		component: FailedActivation
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
		component: MedicamentListPreview,
	},
	{
		path: "/PatientPage",
		name: "PatientPage",
		component: PatientPage,
		children: [
			// {
			// 	path: "",
			// 	component: PatientInfo,
			// },
			{
				path: "MedicamentList",
				component: MedicamentListPreview,
			},
			{
				path: "MedicamentInPharmacy/:id",
				component: MedicamentInPharmacy
			},
			{
				path: "PharmacyList",
				component: PharmacyListPreview
			},
			{
				path: "ChangePassword",
				name: "ChangePassword",
				component: ChangePassword,
				meta: {
					roles: [Role.Patient]
				},
			},
		]
	},
	{
		path: "/SystemAdminPage",
		name: "SystemAdminPage",
		component: SystemAdminPage,
		children: [
			{
				path: "MedicamentList",
				component: MedicamentListPreview,
			},
			{
				path: "MedicamentInPharmacy/:id",
				component: MedicamentInPharmacy
			},
			{
				path: "pharmacyRegistration",
				component: PharmacyRegistration,
				meta: {
					roles: [Role.SystemAdmin]
				},
			},
			{
				path: "unansweredComplaints",
				component: UnansweredComplaint
			},
			{
				path: "userRegister/:userRole",
				component: UserRegistration,
				meta: {
					roles: [Role.SystemAdmin]	
				},
			},
			{
				path:"PharmacyAdminRegistration",
				component: PharmacyAdminRegistration,
				meta: {
					roles: [Role.SystemAdmin]
				}
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
				component: MedicamentRegistration,
				meta: {
					roles: [Role.SystemAdmin]
				},
			},
			{
				path: "ChangePassword",
				name: "ChangePassword",
				component: ChangePassword,
				meta: {
					roles: [Role.SystemAdmin]
				},
			},
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
				path: "MedicamentInPharmacy/:id",
				component: MedicamentInPharmacy
			},
			{
				path: "PharmacyList/:query",
				component: PharmacyListPreview,
				props: true
			},
			{
				path: "PatientRegistration",
				component: PatientRegistration
			},
			
		]
	},
	{
		path: "/Logout",
		name: "Logout",
		component: Logout
	},
	{
		path: "/PharmacistPage",
		name: "PharmacistPage",
		redirect: "/PharmacistPage/MedicamentList",
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
				path: "MedicamentInPharmacy/:id",
				component: MedicamentInPharmacy
			},
			{
				path: "PharmacyList/:query",
				component: PharmacyListPreview,
				props: true
			},
			{
				path: "SearchPatients",
				component: SearchPatients,
				meta: {
					roles: [Role.Pharmacist]
				},
			},
			{
				path: "DispenseMedication",
				component: DispenseMedication,
				meta: {
					roles: [Role.Pharmacist]
					
				},
			},
			{
				path: "ExaminedPatients",
				component: ExaminedPatients,
				props: true,
				meta: {
					roles: [Role.Pharmacist]
				}
			},
			{
				path: "ChangePassword",
				name: "ChangePassword",
				component: ChangePassword,
				meta: {
					roles: [Role.Pharmacist]
				},
			},
		]
	},
	{
		path: "/DermatologistPage",
		name: "DermatologistPage",
		redirect: "/DermatologistPage/MedicamentList",
		component: DermatologistPage,
		children: [
			{
				path: "PharmacyList",
				name: "DermatologistPagePharmacyList",
				component: PharmacyListPreview
			},
			{
				path: "MedicamentInPharmacy/:id",
				component: MedicamentInPharmacy
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
				component: SearchPatients,
				meta: {
					roles: [Role.Dermatologist]
				},
			},
			{
				path: "ExaminationSearch",
				component: ExaminationSearch,
				meta: {
					roles: [Role.Dermatologist]
				
				},
			},
			{
				path: "AppointmentPage",
				name: "DermatologistPageAppointmentPage",
				component: AppointmentPage,
				props: true,
				meta: {
					roles: [Role.Dermatologist]
				},
			},
			{
				path: "ExaminedPatients",
				component: ExaminedPatients,
				props: true,
				meta: {
					roles: [Role.Dermatologist]
				}
			},
			{
				path: "ChangePassword",
				name: "ChangePassword",
				component: ChangePassword,
				meta: {
					roles: [Role.Dermatologist]
				},
			},
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
				path: "MedicamentInPharmacy/:id",
				component: MedicamentInPharmacy
			},
			{
				path: "MedicamentTable",
				component: MedicamentTable,
				meta: {
					roles: [Role.AdminPharmacy]
				},
			}
			,
			{
				path: "PharmacistTable",
				component:PharmacistTable,
				meta: {
					roles: [Role.AdminPharmacy]
				},
			},
			{	
				path : "DermatologistTable",
				component: DermatologistTable,
				meta: {
					roles: [Role.AdminPharmacy]
				},
			},
			{
				path:"PharmacistRegistration",
				component:PharmacistRegistration,
				meta: {
					roles: [Role.AdminPharmacy]
				},
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
				meta: {
					roles: [Role.AdminPharmacy]
				},
			},
			{
				path: "AppointmentTable",
				component: AppointmentTable,
				meta: {
					roles: [Role.AdminPharmacy]
				},
			}, 
			{
				path: "PharmacyPreview",
				component: PharmacyPreview,
				meta: {
					roles: [Role.AdminPharmacy]
				},

			},
			{
				path: "Profile",
				component: Profile,
				meta: {
					roles: [Role.AdminPharmacy]
				},

			},{
				path: "RequestMedicaments",
				component: RequestMedicaments,
				meta: {
					roles: [Role.AdminPharmacy]
				},
			},
			{
				path: "PricelistAppointments",
				component: PricelistAppointments,
				meta: {
					roles: [Role.AdminPharmacy]
				},
			},
			{
				path: "Orders",
				component: Orders,
				meta: {
					roles: [Role.AdminPharmacy]
				},
			},
			{
				path: "ChangePassword",
				name: "ChangePassword",
				component: ChangePassword,
				meta: {
					roles: [Role.AdminPharmacy]
				},
			},
			{
				path: "ReportAppointments",
				component: ReportAppointments,
				meta: {
					roles: [Role.AdminPharmacy]
				},
			},
				{
				path: "ReportMedicaments",
				component: ReportMedicaments,
				meta: {
					roles: [Role.AdminPharmacy]
				},
			},

		]
	},
	{
		path: '/SupplierPage',
		name: SupplierPage,
		component: SupplierPage,
		children: [
			{
				path: "PharmacyList",
				component: PharmacyListPreview
			},
			{
				path: "MedicamentList",
				meta: {
					roles: [ Role.Supplier ]
				},
				component: MedicamentListPreview
			},
			{
				path: "MedicamentInPharmacy/:id",
				component: MedicamentInPharmacy
			},
			{
				path: "OrderList",
				component: SupplierOrderList,
			},
			{

				path: "ChangePassword",
				name: "ChangePassword",
				component: ChangePassword,
				meta: {
					roles: [Role.Supplier]
				},
			},
			{

				path: "OfferList",
				component: SupplierOfferList,
			}

		]
	},
	{
		path: '/PatientPage',
		name: PatientPage,
		component: PatientPage,
		children: [
			{
				path: "PharmacyList",
				component: PharmacyListPreview
			},
			{
				path: "MedicamentList",
				meta: {
					roles: [Role.Patient]
				},
				component: MedicamentListPreview
			},
			{
				path: "WriteComplaint",
				meta: {
					roles: [Role.Patient]
				},
				component: WriteComplaint
			},
			{
				path: "PharmacyList/:query",
				component: PharmacyListPreview,
				props: true
			},
			{
				path: "ReservedAppointmentsTable",
				component: ReservedAppointmentsTable
			},
			{
				path: "PastAppointments",
				component: PastAppointmentsPage
			},
			{
				path: "ReservedMedicamentsTable",
				component: ReservedMedicamentsTable
			},
			/*
			{
				path: "ePrescriptions",
				component: ePrescriptionPreview
			}*/
		]
	},
	{
		path: '*',
		redirect: "/Login"
	}

]

const router = new VueRouter({
	mode: 'history',
	base: process.env.BASE_URL,
	routes
})

export default router
router.beforeEach((to, from, next) => {
	const { roles} = to.meta;
	if(roles){
		const userRole = JSON.parse(atob(localStorage.getItem('token').split('.')[1])).role;
		if(roles.length && !roles.includes(userRole)){
			return next({path: 'Login'});
		}

	}
	next();
	});
