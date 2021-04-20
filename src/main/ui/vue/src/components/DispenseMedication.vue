<template>
    <div>
        <b-form inline>
            <b-form-input id="reservationId"
                        name="reservationId"
                        placeholder="Reservation ID"
                        class="mb-2 mr-sm-2 mb-sm-0"
                        v-model="reservationId">
            </b-form-input>

            <b-button @click="onSearch" class="mb-2 mr-sm-2 mb-sm-0">Search</b-button>
        </b-form>

        <br>
        <template v-if="Object.keys(this.ePrescription).length !== 0">
            <h5><b>Reservation ID: </b>{{ this.ePrescription.id }}</h5>
            <h5><b>Patient E-mail: </b>{{ this.ePrescription.patientEmail }}</h5>
            <b-table striped hover :items="medicineQuantity" :fields="fields"></b-table>
            <b-button block variant="outline-primary" @click="onDispense">Dispense</b-button>
        </template>
        <br>

        <b-modal ref="error-modal" hide-footer title="Error">
            <div class="d-block text-center">
                <p>Reservation ID is invalid!</p>
            </div>
            <b-button class="mt-3" variant="outline-danger" block @click="hideErrorModal">Close</b-button>
        </b-modal>

        <b-modal ref="success-modal" hide-footer title="Success">
            <div class="d-block text-center">
                <p>Medicine has successfully been dispensed!</p>
            </div>
            <b-button class="mt-3" variant="outline-success" block @click="hideSuccessModal">Close</b-button>
        </b-modal>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                ePrescription: {},
                reservationId: '',
                medicineQuantity: [],
                fields: [
                    {
                        key: 'medicineName',
                        headerTitle: 'Medicine'
                    },
                    {
                        key: 'quantity',
                        headerTitle: 'Quantity'
                    }
                ]
            }
        },
        methods: {
            onSearch(e) {
                e.preventDefault();

                this.axios.get(`/api/eprescriptions/` + this.reservationId + `/dispensable`,  {
                            headers: {
                                Authorization: "Bearer " + localStorage.getItem('token'),
                            },
                        })
                          .then(response => {
                            this.ePrescription = response.data;
                            this.medicineQuantity = Object.keys(this.ePrescription.medicineQuantity)
                                                    .map(key => ({medicineName: key, quantity: this.ePrescription.medicineQuantity[key]}));
                           })
                           .catch(error => {
                            this.showErrorModal();
                            this.ePrescription = {};
                            this.medicineQuantity = [];
                            console.log(error);
                           });
            },
            onDispense(e) {
                e.preventDefault();

                this.axios.get(`/api/eprescriptions/` + this.ePrescription.id + `/dispense`,  {
                        headers: {
                            Authorization: "Bearer " + localStorage.getItem('token'),
                        },
                        })
                          .then(response => {
                            this.showSuccessModal();
                            this.ePrescription = {};
                            this.medicineQuantity = [];
                            console.log(response);
                           })
                           .catch(error => {
                            this.showErrorModal();
                            this.ePrescription = {};
                            this.medicineQuantity = [];
                            console.log(error);
                           });
            },
            hideErrorModal() {
                this.$refs['error-modal'].hide()
            },
            hideSuccessModal() {
                this.$refs['success-modal'].hide()
            },
            showErrorModal() {
                this.$refs['error-modal'].show()
            },
            showSuccessModal() {
                this.$refs['success-modal'].show()
            }
        }
    }
</script>
