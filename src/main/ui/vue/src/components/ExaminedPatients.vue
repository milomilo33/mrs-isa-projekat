<template>
    <div>
        <h1 v-if="type === 'dermatologist'">Your examination history</h1>
        <h1 v-if="type === 'pharmacist'">Your counseling history</h1>
        <br>
        <b-form inline>
            <b-form-input id="name"
                        name="name"
                        placeholder="Patient name"
                        class="mb-2 mr-sm-2 mb-sm-0"
                        v-model="name">
            </b-form-input>

            <b-form-input id="lastName"
                        name="lastName"
                        placeholder="Patient last name"
                        class="mb-2 mr-sm-2 mb-sm-0"
                        v-model="lastName">
            </b-form-input>

            <b-button @click="onSubmit" class="mb-2 mr-sm-2 mb-sm-0">Search</b-button>
        </b-form>

        <br>
        <b-table striped hover :items="appointments" :fields="appointmentFields" :sort-compare="dateSortCompare">
        </b-table>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                appointments: [],
                name: '',
                lastName: '',
                appointmentFields: [
                    {
                        key: 'patient.name',
                        headerTitle: 'Patient Name',
                        label: 'Patient Name',
                        sortable: true
                    },
                    {
                        key: 'patient.lastName',
                        headerTitle: 'Last Name',
                        label: 'Last Name',
                        sortable: true
                    },
                    {
                        key: 'dateStr',
                        headerTitle: 'Date',
                        label: 'Date',
                        sortable: true
                    },
                    {
                        key: 'timeFrom',
                        headerTitle: 'From'
                    },
                    {
                        key: 'timeTo',
                        headerTitle: 'To'
                    }
                ],
                type: ''
            }
        },
        methods: {
            onSubmit(e) {
                if (e) {
                    e.preventDefault();
                }

                let searchParams = { name: this.name, lastName: this.lastName };
                let url = '';
                if (this.type === 'dermatologist') {
                    url = '/api/dermatologist/examinations/done/patient';
                }
                else if (this.type === 'pharmacist') {
                    url = '/api/pharmacist/examinations/done/patient';
                }
                else {
                    console.log("Invalid type in examination history.");
                    return;
                }
                this.axios.get(url,  {
                            headers: {
                                Authorization: "Bearer " + localStorage.getItem("token"),
                            },
                            })
                          .then(response => {
                            // search
                            let filteredAppointments = [];
                            for (let appointment of response.data) {
	                            if (appointment.patient.name.toLowerCase().includes(searchParams.name.toLowerCase()) && // eslint-disable-line no-mixed-spaces-and-tabs
                                    appointment.patient.lastName.toLowerCase().includes(searchParams.lastName.toLowerCase())) 
                                {
                                    filteredAppointments.push(appointment);
                                }
                            }

                            // date conversion
                            filteredAppointments.forEach((obj) => {
                                obj["dateStr"] = new Date(obj["date"]).toDateString();
                                obj["date"][1] -= 1;
                                let timeFromArray = obj["date"].concat(obj["termFrom"]).concat([0, 0]);
                                obj["timeFrom"] = new Date(...timeFromArray).toTimeString();
                                let timeToArray = obj["date"].concat(obj["termTo"]).concat([0, 0]);
                                obj["timeTo"] = new Date(...timeToArray).toTimeString();
                            });

                            this.appointments = filteredAppointments;
                           })
                           .catch(error => {
                              if (error.response.status === 404) {
                                this.appointments = [];
                              }
                              else {
                                this.appointments = []
                                console.log(error);
                              }
                           });
            },
            dateSortCompare(a, b, key) {
                if (key === 'dateStr') {
                    let dateA = new Date(a[key]);
                    let dateB = new Date(b[key]);
                    return dateA - dateB;
                } else {
                    return false;
                }
            }
        },
        mounted() {
            this.type = this.$route.query.type;
            this.onSubmit();
        }
    }
</script>
