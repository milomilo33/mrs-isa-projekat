<template>
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
                    //select: this.handleDateSelect,
                    eventClick: this.handleEventClick,
                    //eventsSet: this.handleEvents
                    //datesSet: this.datesChanged,
                    events: this.loadEvents,
                    /* you can update a remote database when these fire:
                    eventAdd:
                    eventChange:
                    eventRemove:
                    */
                },
                currentEvents: []
            }
        },

        methods: {
            handleEventClick(clickInfo) {
                console.log(clickInfo.event.extendedProps);
                // this.calendarOptions.events.push({
                //             title: 'abcssssss',
                //             start: '2021-05-21',
                //             end: '2021-05-23',
                //             data: {
                //                 lol: 'lol'
                //             }
                // });
                console.log(this.calendarOptions);
            },

            loadEvents(dateInfo, success, failure) {
                console.log(dateInfo);
                let startDate = dateInfo.startStr;
                let endDate = dateInfo.endStr;
                console.log(encodeURI(startDate));
                this.axios.get(`/api/dermatologist/appointments/calendar?startDateStr=${encodeURIComponent(startDate)}&endDateStr=${encodeURIComponent(endDate)}`, {
                    headers: {
                        Authorization: "Bearer " + localStorage.getItem("token"),
                    },
                })
                .then(response => {
                    let calendarAppointments = response.data;
                    console.log(calendarAppointments);
                    // this.calendarOptions.events = [];
                    let eventObjects = calendarAppointments.map(appointment => {
                        let title = '';
                        switch (appointment.status) {
                            case 'slot':
                                title = 'Appointment slot ';
                                break;
                            case 'done':
                                title = 'Finished appointment ';
                                break;
                            case 'started':
                                title = 'Appointment in progress ';
                                break;
                            case 'scheduled':
                                title = 'Scheduled appointment ';
                                break;
                            default:
                                title = 'ERROR';
                                break;
                        }

                        let eventObj = {
                            title,
                            start: appointment.dateStart,
                            end: appointment.dateEnd,
                            data: appointment
                        };

                        // this.events.push(eventObj);
                        return eventObj;
                    });

                    success(eventObjects).map(eventElement => {
                        // let title = '';
                        // let appointment = eventElement;

                        // switch (appointment.status) {
                        //     case 'slot':
                        //         title = 'Appointment slot ';
                        //         break;
                        //     case 'done':
                        //         title = 'Finished appointment ';
                        //         break;
                        //     case 'started':
                        //         title = 'Appointment in progress ';
                        //         break;
                        //     case 'scheduled':
                        //         title = 'Scheduled appointment ';
                        //         break;
                        //     default:
                        //         title = 'ERROR';
                        //         break;
                        // }

                        return eventElement;
                    });
                })
                .catch(error => {
                    //this.calendarOptions.events = [];
                    failure(error);
                    console.log(error);
                })
            }
        },

        mounted() {

        }
    }
</script>
