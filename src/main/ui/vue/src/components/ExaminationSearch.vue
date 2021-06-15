<template>
    <div>
        <h1 v-if="type === 'dermatologist'">Your upcoming examinations</h1>
        <h1 v-if="type === 'pharmacist'">Your upcoming counselings</h1>
        <br>
        <b-form inline>
            <b-form-input id="name"
                        name="name"
                        placeholder="Name"
                        class="mb-2 mr-sm-2 mb-sm-0"
                        v-model="name">
            </b-form-input>

            <b-form-input id="lastName"
                        name="lastName"
                        placeholder="Last name"
                        class="mb-2 mr-sm-2 mb-sm-0"
                        v-model="lastName">
            </b-form-input>

            <b-button @click="onSubmit" class="mb-2 mr-sm-2 mb-sm-0">Search</b-button>
        </b-form>

        <br>
        <b-table striped hover :items="appointments" :fields="appointmentFields">
            <template #cell(action)="row">
                <div v-if="row.item.chosenEmployee.currentlyInAppointment">
                    <button class="btn btn-dark" @click="startAppointment(row)"
                        :ref="'btn' + row.index" v-if="row.item.medicalReport">Resume appointment</button>
                </div>
                <div v-if="!row.item.chosenEmployee.currentlyInAppointment">
                    <button class="btn btn-dark" @click="startAppointment(row)"
                        :ref="'btn' + row.index">Start appointment</button>
                </div>
            </template>
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
                        headerTitle: 'Name',
                        label: 'Name',
                        sortable: true
                    },
                    {
                        key: 'patient.lastName',
                        headerTitle: 'Last Name',
                        label: 'Last Name',
                        sortable: true
                    },
                    {
                        key: 'patient.phoneNumber',
                        headerTitle: 'Phone Number',
                        label: 'Phone Number'
                    },
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
                type: ''
            }
        },
        methods: {
            onSubmit(e) {
                if (e) {
                    e.preventDefault();
                }

                let searchParams = { name: this.name, lastName: this.lastName };
                let targetApi = this.type === 'dermatologist' ? 'dermatologist/examinations' : 'pharmacist/counselings'
                this.axios.get(process.env.VUE_APP_API_URL + `/${targetApi}`,  {
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
                            // response.data = response.data.filter(appointment => {
                            //     return 
                            //         appointment.patient.name.toLowerCase().includes(searchParams.name) &&
                            //         appointment.patient.lastName.toLowerCase().includes(searchParams.lastName);
                            // });

                            // date conversion
                            filteredAppointments.forEach((obj) => {
                                obj["dateStr"] = new Date(obj["date"]).toDateString();
                                obj["date"][1] -= 1;
                                let timeFromArray = obj["date"].concat(obj["termFrom"]).concat([0, 0]);
                                obj["timeFrom"] = new Date(...timeFromArray).toLocaleTimeString();
                                let timeToArray = obj["date"].concat(obj["termTo"]).concat([0, 0]);
                                obj["timeTo"] = new Date(...timeToArray).toLocaleTimeString();
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

            startAppointment(row) {
                let appointmentId = row.item.id;
                this.axios.get(process.env.VUE_APP_API_URL + `/appointments/${appointmentId}/start`, {
                                    headers: {
                                        Authorization: "Bearer " + localStorage.getItem("token"),
                                    },
                                })
                                .then(response => {
                                    let medicalReportId = response.data;
                                    row.item.medicalReportId = medicalReportId;

                                    // otvoriti stranicu pregleda
                                    let name = this.type === 'dermatologist' ? 'DermatologistPageAppointmentPage' :
                                                                               'PharmacistPageAppointmentPage';
                                    this.$router.push({ name,
                                                        params: { appointment: row.item,
                                                                  medicalReportId,
                                                                  type: this.type } });
                                })
                                .catch(error => {
                                    console.log(error);
                                })
            }
        },
        mounted() {
            this.onSubmit();
        },
        created() {
            this.type = this.$route.query.type;
        }
    }
</script>
