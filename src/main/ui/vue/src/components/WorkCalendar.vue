<template>
    <div style="margin-bottom: 20px;">
        <div>
            <b-row class="text-center">
                <b-col></b-col>
                <b-col cols="10">
                    <div class='work-calendar'>
                        <FullCalendar
                        class='demo-app-calendar'
                        :options='calendarOptions'
                        >
                        <!-- <template v-slot:eventContent='arg'>
                            <b>{{ arg.timeText }}</b>
                            <i>{{ arg.event.title }}</i>
                        </template> -->
                        </FullCalendar>
                    </div>
                </b-col>
                <b-col></b-col>
            </b-row>
        </div>

        <b-modal ref="info-modal" hide-footer title="Details">
            <div class="d-block text-center">
                <h3>Appointment info</h3>
                <p>Type: {{ appointmentStatusToText(chosenAppointment.status) }}</p>
                <p v-if="type === 'dermatologist'">Pharmacy: {{ chosenAppointment.pharmacyName }}</p>
                <p v-if="chosenAppointment.status !== 'slot'">Patient: {{ chosenAppointment.patientName }} {{ chosenAppointment.patientLastName }}</p>
            </div>
            <b-button v-if="chosenAppointment.status === 'done'" class="mt-3" variant="outline-primary" block @click="showReportDetails">Show details</b-button>
            <b-button v-if="scheduledAppointmentIsUpcoming || chosenAppointment.status === 'started'" class="mt-3" variant="dark" block @click="startAppointment">Start appointment</b-button>
            <b-button class="mt-3" variant="outline-danger" block @click="hideInfoModal">Close</b-button>
        </b-modal>

        <b-modal ref="report-info-modal" hide-footer title="Report Details">
            <div class="d-block text-center">
                <h3>Report text</h3>
                <p>{{ this.chosenAppointmentInfo }}</p>
            </div>
            <div v-if="Object.keys(chosenAppointmentPrescribedMedicine).length > 0" class="d-block text-center">
                <h3>Prescribed medicines</h3>
                <b-list-group>
                    <b-list-group-item v-for="(quantity, name) in chosenAppointmentPrescribedMedicine" :key="name"
                                    class="d-flex justify-content-between align-items-center">
                        {{ name }}
                        <b-badge variant="primary" pill>{{ quantity }}</b-badge>
                    </b-list-group-item>
                </b-list-group>
            </div>
            <b-button class="mt-3" variant="outline-primary" block @click="hideReportInfoModal">Close</b-button>
        </b-modal>
    </div>
</template>

<script>
    import FullCalendar from '@fullcalendar/vue'
    import dayGridPlugin from '@fullcalendar/daygrid'
    import timeGridPlugin from '@fullcalendar/timegrid'
    import listPlugin from '@fullcalendar/list'

    export default {
        components: {
            FullCalendar
        },

        data() {
            return {
                calendarOptions: {
                    plugins: [
                        dayGridPlugin,
                        timeGridPlugin,
                        listPlugin
                    ],
                    headerToolbar: {
                        left: 'prev,next today',
                        center: 'title',
                        right: 'dayGridMonth,timeGridWeek,timeGridDay,listYear'
                    },
                    initialView: 'dayGridMonth',
                    editable: true,
                    // selectable: true,
                    selectMirror: true,
                    dayMaxEvents: true,
                    weekends: true,
                    eventClick: this.handleEventClick,
                    events: this.loadEvents,
                    height: '70vh'
                },
                chosenAppointment: {},
                chosenAppointmentInfo: '',
                chosenAppointmentPrescribedMedicine: [],
                scheduledAppointmentIsUpcoming: false,
                type: ''
            }
        },

        methods: {
            handleEventClick(clickInfo) {
                console.log(clickInfo.event.extendedProps);
                this.chosenAppointment = clickInfo.event.extendedProps.data;
                this.scheduledAppointmentIsUpcoming = false;
                if (this.chosenAppointment.status === 'scheduled') {
                    let endDate = new Date(this.chosenAppointment.dateEndStr);
                    let now = new Date();
                    if (now < endDate) {
                        this.scheduledAppointmentIsUpcoming = true;
                    }
                }
                this.showInfoModal();
            },

            loadEvents(dateInfo, success, failure) {
                let startDate = dateInfo.startStr;
                let endDate = dateInfo.endStr;
                this.axios.get(`/api/${this.type}/appointments/calendar?startDateStr=${encodeURIComponent(startDate)}&endDateStr=${encodeURIComponent(endDate)}`, {
                    headers: {
                        Authorization: "Bearer " + localStorage.getItem("token"),
                    },
                })
                .then(response => {
                    let calendarAppointments = response.data;

                    let eventObjects = calendarAppointments.map(appointment => {
                        let color = '';
                        switch (appointment.status) {
                            case 'slot':
                                color = 'green';
                                break;
                            case 'done':
                                color = 'red';
                                break;
                            case 'started':
                                color = 'orange';
                                break;
                            case 'scheduled':
                                color = 'blue';
                                break;
                            default:
                                color = 'black';
                                break;
                        }
                        let title = this.appointmentStatusToText(appointment.status);

                        let eventObj = {
                            title,
                            start: appointment.dateStartStr,
                            end: appointment.dateEndStr,
                            data: appointment,
                            color
                        };

                        return eventObj;
                    });

                    success(eventObjects).map(eventElement => {
                        return eventElement;
                    });
                })
                .catch(error => {
                    failure(error);
                    console.log(error);
                })
            },

            appointmentStatusToText(status) {
                switch (status) {
                    case 'slot':
                        return 'Appointment slot ';
                    case 'done':
                        return 'Finished appointment ';
                    case 'started':
                        return 'Appointment in progress ';
                    case 'scheduled':
                        return 'Scheduled appointment ';
                    default:
                        return 'ERROR';
                }
            },

            showReportDetails() {
                this.axios.get(`/api/appointments/${this.chosenAppointment.appointmentId}/details`, {
                            headers: {
                                Authorization: "Bearer " + localStorage.getItem("token"),
                            },
                            })
                            .then(response => {
                                this.chosenAppointmentInfo = response.data.text;
                                this.chosenAppointmentPrescribedMedicine = response.data.medicineQuantity;
                                this.showReportInfoModal();
                            })
                            .catch(error => {
                                this.chosenAppointmentPrescribedMedicine = [];
                                console.log(error);
                            })
            },

            startAppointment() {
                // uraditi za zapocinjanje savetovanja
                if (this.type === 'pharmacist')
                    return;
                let appointmentId = this.chosenAppointment.appointmentId;
                this.axios.get(`/api/appointments/${appointmentId}/start`, {
                                    headers: {
                                        Authorization: "Bearer " + localStorage.getItem("token"),
                                    },
                                })
                                .then(response => {
                                    let medicalReportId = response.data;

                                    this.axios.get(`/api/dermatologist/examination/${appointmentId}/upcoming`, {
                                                        headers: {
                                                            Authorization: "Bearer " + localStorage.getItem("token"),
                                                        },
                                                    })
                                                    .then(nestedResponse => {
                                                        let appointment = nestedResponse.data;
                                                        appointment.medicalReportId = medicalReportId;

                                                        // otvoriti stranicu pregleda
                                                        this.$router.push({ name: 'DermatologistPageAppointmentPage',
                                                                            params: { appointment, medicalReportId } });
                                                    })
                                                    .catch(nestedError => {
                                                        console.log(nestedError);
                                                    });
                                })
                                .catch(error => {
                                    console.log(error);
                                })
            },

            hideInfoModal() {
                this.$refs['info-modal'].hide()
            },

            showInfoModal() {
                this.$refs['info-modal'].show()
            },

            hideReportInfoModal() {
                this.$refs['report-info-modal'].hide()
            },

            showReportInfoModal() {
                this.$refs['report-info-modal'].show()
            }
        },

        created() {
            this.type = this.$route.query.type;
        }
    }
</script>
