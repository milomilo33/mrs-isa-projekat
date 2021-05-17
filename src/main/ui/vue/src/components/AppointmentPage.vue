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
        <b-button size="sm" variant="info" @click="onScheduleAnotherAppointment">Schedule another appointment</b-button>
    </div>
    <br>
    <div style="text-align:center;">
        <b-button variant="danger" @click="onPatientAbsent" style="margin:10px;">Patient is absent</b-button>
        <b-button variant="success" @click="onEndExamination" style="margin:10px;">End examination</b-button>
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

    <b-modal size="xl" ref="scheduling-modal" hide-footer title="Scheduling">
        <div class="d-block text-center">
            <h3>Existing appointments</h3>
        </div>
        <b-table striped hover :items="existingAppointments" :fields="existingAppointmentsFields">
            <template #cell(action)="row">
                <button class="btn btn-dark" @click="scheduleExistingAppointment(row)"
                    :ref="'btn' + row.index">Schedule</button>
            </template>
        </b-table>
        <b-button variant="info" @click="onCreateAndScheduleNewAppointment" style="margin:10px;">
            Create and schedule new appointment
        </b-button>
    </b-modal>

    <b-modal ref="scheduling-success-modal" hide-footer title="Success" @hide="hideAllSchedulingModals">
        <div class="d-block text-center">
            <p>{{ this.successMessage }}</p>
        </div>
        <b-button class="mt-3" variant="outline-success" block @click="hideAllSchedulingModals">Close</b-button>
    </b-modal>

    <b-modal ref="scheduling-error-modal" hide-footer title="Error">
        <div class="d-block text-center">
            <p>{{ this.schedulingErrorMessage }}</p>
        </div>
        <b-button class="mt-3" variant="outline-danger" block @click="hideSchedulingErrorModal">Close</b-button>
    </b-modal>

    <b-modal
      size="lm"
      ref="scheduling-picker-modal"
      :id="'scheduling-picker-modal'"
      :title="'Create and schedule new appointment'"
      @ok="createAndScheduleAppointment"
      @ended="showSuccessModal"
      :header-bg-variant="'success'"
      :footer-bg-variant="'success'"
    >
      <template>
        <div>
          <label></label>
          <label for="dd"> Date: </label>
          <b-form-datepicker
            :date-disabled-fn="dateDisabled"
            v-model="schedulingDate"
            locale="en"
            class="mb-2"
          ></b-form-datepicker>
        </div>
        <div>
          <label></label>
          <label for="tf"> Time from: </label>
          <b-form-timepicker v-model="timeFrom" locale="en"></b-form-timepicker>
        </div>
        <div>
          <label></label>
          <label for="tt"> Time to: </label>
          <b-form-timepicker v-model="timeTo" locale="en"></b-form-timepicker>
        </div>
      </template>
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
      ],
      existingAppointments: [],
      existingAppointmentsFields: [
            {
                key: 'dateStr',
                headerTitle: 'Date',
                label: 'Date'
            },
            {
                key: 'timeFrom',
                headerTitle: 'From',
                label: 'From'
            },
            {
                key: 'timeTo',
                headerTitle: 'To',
                label: 'To'
            },
            'action'
      ],
      schedulingDate: "",
      timeFrom: "",
      timeTo: "",
      schedulingErrorMessage: ""
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
      let reportText = this.examinationText;
      let body = {
        reportText
      };
      console.log(body);
      this.axios.post(`/api/appointments/` + this.appointment.id + `/finish`, body, {
          headers: {
            Authorization: "Bearer " + localStorage.getItem('token'),
          },
        })
      .then(() => {
          this.successMessage = `Patient ${this.appointment.patient.name} ${this.appointment.patient.lastName}'s` +
                                ` examination is now over.`;
          this.showSuccessModal();
      })
      .catch(error => {
          console.log(error);
          this.showErrorModal();
      });
    },

    onScheduleAnotherAppointment() {
        this.axios.get(`/api/dermatologist/examinations/existing`, {
            headers: {
                Authorization: "Bearer " + localStorage.getItem('token'),
            },
        })
        .then(response => {
            let returnedAppointments = response.data;

            // date conversion
            returnedAppointments.forEach((obj) => {
                obj["dateStr"] = new Date(obj["date"]).toDateString();
                obj["date"][1] -= 1;
                let timeFromArray = obj["date"].concat(obj["termFrom"]).concat([0, 0]);
                obj["timeFrom"] = new Date(...timeFromArray).toLocaleTimeString();
                let timeToArray = obj["date"].concat(obj["termTo"]).concat([0, 0]);
                obj["timeTo"] = new Date(...timeToArray).toLocaleTimeString();
            });

            this.existingAppointments = returnedAppointments;

            console.log(response);
            this.showSchedulingModal();
        })
        .catch(error => {
            console.log(error);
        });
    },

    onCreateAndScheduleNewAppointment() {
        this.showSchedulingPickerModal();
    },

    scheduleExistingAppointment(row) {
        let existingAppointmentId = row.item.id;

        let body = {
            patientEmail: this.appointment.patient.email,
            appointmentId: existingAppointmentId
        };

        this.axios.post('/api/patients/reserve_appointment', body, {
            headers: {
                Authorization: "Bearer " + localStorage.getItem('token'),
            }
        })
        .then(response => {
            console.log(response);
            this.successMessage = "Appointment successfully scheduled!";
            this.showSchedulingSuccessModal();
        })
        .catch(error => {
            console.log(error);
            this.schedulingErrorMessage = "Failed to schedule appointment";
            this.showSchedulingErrorModal();
        });
    },

    createAndScheduleAppointment() {
        if (!this.schedulingDate || !this.timeFrom || !this.timeTo) {
            this.schedulingErrorMessage = "Some fields are empty!";
            this.showSchedulingErrorModal();
            return;
        }
        let date = new Date(this.schedulingDate);
        let body = {
            patientEmail: this.appointment.patient.email,
            date,
            termFrom: this.timeFrom,
            termTo: this.timeTo
        };
        this.axios.post(`/api/dermatologist/appointments/schedule-new`, body, {
                            headers: {
                                Authorization: "Bearer " + localStorage.getItem("token"),
                            },
                        })
                        .then(response => {
                            console.log(response);
                            this.successMessage = "Successfully created and scheduled appointment!";
                            this.showSchedulingSuccessModal();
                        })
                        .catch(error => {
                            this.schedulingErrorMessage = "Failed to create and schedule appointment!";
                            this.showSchedulingErrorModal();
                            console.log(error);
                        });
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

    hideSchedulingModal() {
        this.$refs['scheduling-modal'].hide()
    },

    hideSchedulingErrorModal() {
        this.$refs['scheduling-error-modal'].hide()
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

    showSchedulingModal() {
        this.$refs['scheduling-modal'].show()
    },

    showSchedulingSuccessModal() {
        this.$refs['scheduling-success-modal'].show()
    },

    showSchedulingPickerModal() {
        this.$refs['scheduling-picker-modal'].show()
    },

    showSchedulingErrorModal() {
        this.$refs['scheduling-error-modal'].show()
    },

    hideAllSchedulingModals() {
        this.$refs['scheduling-modal'].hide();
        this.$refs['scheduling-success-modal'].hide();
        this.$refs['scheduling-picker-modal'].hide()
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
                    });
    },

    onPrescribed() {
      this.hidePrescriptionModal();
      this.loadPrescriptionTable();
    },

    dateDisabled(ymd, date) {
      const today = new Date();
      const m = today.getMonth();
      const d = today.getDate();
      const day1 = date.getDate();
      const month1 = date.getMonth();
      return (day1 <= d && month1 <= m) || (day1 >= d && month1 < m);
    },
  },

  mounted() {
      this.loadPrescriptionTable();
  }
}

</script>