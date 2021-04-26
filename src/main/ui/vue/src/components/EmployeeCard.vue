<template>
  <b-col align-h="start" class="border pt-2 ml-2">
    <a href="#">
      <img class="img" src="../employee.jpg" alt="" />
    </a>
    <b-container>
      <b-row align-h="start" class="pl-1">
        <h4>{{ employee.name }} {{ employee.lastName }}</h4>
      </b-row>
      <b-row align-h="start" class="pl-1">
        <p>{{ employee.e }}</p>
      </b-row>
      <b-row align-h="start" class="pl-1">
        <p><b-button v-b-modal="'email' + employee.email">About</b-button></p>
      </b-row>
    </b-container>
    <b-modal
      content-class="my-class"
      :id="'email' + this.employee.email"
      centered
      title="Info"
      header-bg-variant="dark"
      header-text-variant="light"
      body-bg-variant="light"
      body-text-variant="dark"
      :hide-footer="true"
    >
      <b-container fluid>
        <b-row class="mb-1 text-center colorIt">
          <p>
            <b class="colorHeaders">Name:</b>
            {{ this.employee.name }} {{ this.employee.lastName }}
          </p>
        </b-row>
      

        <b-row class="mb-1 colorIt">
          <p>
            <b class="colorHeaders">Phone number:</b>
            {{ this.employee.phoneNumber }}
          </p>
        </b-row>
        <b-row class="mb-1 colorIt">
          <p>
            <b class="colorHeaders">Email:</b>
            {{ this.employee.email }}
          </p>
        </b-row>
        <b-row>
          <select id="mySelect" name="available_appointments" v-model="app" @change="setAppointment(app)">
              <option class="med" v-for="a in this.employee.appointments" :key="a.id" :value="a">{{parseAppointment(a)}}</option>
          </select>
          <b-button class="ml-2" @click="reserveExamination()">Rezerviši izabrani termin</b-button>
        </b-row>
      </b-container>
      <template #modal-header="{ close }">
        <b-button size="sm" @click="close()"> Close </b-button>
      </template>
    </b-modal>
  </b-col>
</template>


<script>
export default {
  name: "EmployeeCard",
  props: {
    employee: Object,
  },
  mounted() {},
  data() {
    return {
      ratings: 0,
      modal: "",
      show: false,
      appointment: null
    };
  },
  methods: {
    parseAppointment(a) {
      let retval = "";
      let date = new Date(a.date[0], a.date[1], a.date[2]);

      let from = new Date();
      from.setHours(a.termFrom[0]);
      from.setMinutes(a.termFrom[1]);
      from.setSeconds(0);

      from = from.toLocaleTimeString('en-GB').split(':').slice(0, 2).join(":")

      let to = new Date();
      to.setHours(a.termTo[0]);
      to.setMinutes(a.termTo[1]);
      to.setSeconds(0);

      to = to.toLocaleTimeString('en-GB').split(':').slice(0, 2).join(":")


      if(a.termTo[1] === Number(0)) {
        a.termTo[1] == "00";
      }
      if(a.termFrom[1] === "0") {
        a.termFrom[1] == "00";
      }
      retval = date.toDateString() + " " + from + "-" + to; 
      return retval;
    },

    reserveExamination() {
              console.log(JSON.parse(atob(localStorage.getItem('token').split(".")[1])).sub);

      this.axios.post('http://localhost:8080/api/patients/reserve_appointment', {
        patientEmail: JSON.parse(atob(localStorage.getItem('token').split(".")[1])).sub,
        appointmentId: this.appointment.id
      },
      {
        headers: {
          Authorization: "Bearer " + localStorage.getItem('token'),
      }})
      .then(response => {
        console.log(response);
        var x = document.getElementById("mySelect");
        x.remove(x.selectedIndex);
        this.employee.appointments = this.employee.appointments.filter(el => el.id !== this.appointment.id);
        x.selectedIndex = -1;
        alert("Rezervacija uspesno izvrsena!");
      });
    },

    setAppointment(a) {
      this.appointment = a;
      console.log(this.appointment);
      
    }
  },
};
</script>
<style scoped>
.colorHeaders {
  color: #009933;
}

.img {
  max-height: 30rem;
  max-width: 30rem;
}
.colorIt1 {
  background-color: rgb(255, 255, 204, 0.7);
  border-radius: 5%;
}
</style>