<template>
  <div>
    <h1>Examination of patient {{ appointment.patient.name }} {{ appointment.patient.lastName }}</h1>
    <br>

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
  </div>
</template>

<script>

export default {
  name: 'AppointmentPage',
  data() {
    return {
      successMessage: "",
      examinationText: ""
    }
  },
  props: {
    appointment: Object  
  },
  methods: {
    onPatientAbsent() {
        this.axios.get(`/api/appointments/` + this.appointment.id + `/absent`)
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

    hideErrorModal() {
        this.$refs['error-modal'].hide()
    },

    // hideSuccessModal() {
    //     this.$refs['success-modal'].hide()
    // },

    showErrorModal() {
        this.$refs['error-modal'].show()
    },

    showSuccessModal() {
        this.$refs['success-modal'].show()
    },

    goToHomePage() {
        this.$router.push({ name: 'DermatologistPagePharmacyList' });
    }
  }
}

</script>