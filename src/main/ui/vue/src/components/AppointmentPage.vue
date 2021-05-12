<template>
  <div>
    <h1>Examination of patient {{ appointment.patient.name }} {{ appointment.patient.lastName }}</h1>
    <br>

    <h3>Prescribed medicines</h3>
    <b-table striped hover :items="prescribedMedicines" :fields="prescribedMedicineFields">
    </b-table>

    <div style="text-align:center;">
        <b-button variant="secondary" @click="onPrescribeMore">Prescribe more</b-button>
    </div>

    <div style="margin:25px;">
        <b-form-textarea
            id="textarea"
            v-model="examinationText"
            placeholder="Enter examination info..."
            rows="5"
            max-rows="10"
        ></b-form-textarea>
    </div>

    <div style="text-align:center;">
        <b-button variant="danger" @click="onPatientAbsent">Patient is absent</b-button>
    </div>
    <br>
    <div style="text-align:center;">
        <b-button variant="success" @click="onEndExamination">End examination</b-button>
    </div>
    <br>

    <b-modal ref="error-modal" hide-footer title="Error">
        <div class="d-block text-center">
            <p>An unexpected error has occurred.</p>
        </div>
        <b-button class="mt-3" variant="outline-danger" block @click="hideErrorModal">Close</b-button>
    </b-modal>
    
    <b-modal ref="success-modal" hide-footer title="Success" @hide="goToHomePage">
        <div class="d-block text-center">
            <p>{{ this.successMessage }}</p>
        </div>
        <b-button class="mt-3" variant="outline-success" block @click="goToHomePage">Close</b-button>
    </b-modal>

    <b-modal size="xl" ref="prescription-modal" hide-footer title="Prescription">
        <MedicamentListPreview :prescriptionMode=true :appointment="appointment" @prescribed="onPrescribed"></MedicamentListPreview>
        <br>
        <b-button class="mt-3" style="margin-top: 5rem;" variant="outline-success" block @click="hidePrescriptionModal">Done</b-button>
    </b-modal>
  </div>
</template>

<script>
import MedicamentListPreview from '../components/MedicamentListPreview';

export default {
  name: 'AppointmentPage',
  components: {
    MedicamentListPreview
  },
  data() {
    return {
      successMessage: "",
      examinationText: "",
      prescribedMedicines: [],
      prescribedMedicineFields: [
          {
              key: 'medicament.name',
              headerTitle: 'Name',
              label: 'Name',
              sortable: true
          },
          {
              key: 'quantity',
              headerTitle: 'Quantity',
              label: 'Quantity',
              sortable: true
          },
      ]
    }
  },
  props: {
    appointment: Object,
    medicalReportId: Number
  },
  methods: {
    onPatientAbsent() {
        this.axios.get(`/api/appointments/` + this.appointment.id + `/absent`,  {
            headers: {
              Authorization: "Bearer " + localStorage.getItem('token'),
            },
          })
        .then(() => {
            this.successMessage = `Patient ${this.appointment.patient.name} ${this.appointment.patient.lastName}` +
                                  ` has successfully been marked as absent and has been added a penalty point.` +
                                  ` The examination is now finished.`;
            this.showSuccessModal();
        })
        .catch(error => {
            console.log(error);
            this.showErrorModal();
        });
    },

    onEndExamination() {

    },

    onPrescribeMore() {
        this.showPrescriptionModal();
    },

    hideErrorModal() {
        this.$refs['error-modal'].hide()
    },

    // hideSuccessModal() {
    //     this.$refs['success-modal'].hide()
    // },

    hidePrescriptionModal() {
        this.$refs['prescription-modal'].hide()
    },

    showErrorModal() {
        this.$refs['error-modal'].show()
    },

    showSuccessModal() {
        this.$refs['success-modal'].show()
    },

    showPrescriptionModal() {
        this.$refs['prescription-modal'].show()
    },

    goToHomePage() {
        this.$router.push({ name: 'DermatologistPagePharmacyList' });
    },

    loadPrescriptionTable() {
        this.axios.get(`/api/eprescriptions/medical-report/${this.medicalReportId}/prescription-medicaments`, {
                    headers: {
                        Authorization: "Bearer " + localStorage.getItem("token"),
                    },
                })
                .then(response => {
                    this.prescribedMedicines = response.data;
                    console.log(response.data);
                })
                .catch(error => {
                    console.log(error);
                })
    },

    onPrescribed() {
      this.hidePrescriptionModal();
      this.loadPrescriptionTable();
    }
  },

  mounted() {
      this.loadPrescriptionTable();
  }
}

</script>