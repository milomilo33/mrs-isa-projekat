<template>
  <b-col align-h="start" class="border pt-2 ml-2">
    
    <a href="#">
      <img class="img" src="../doctors.jpg" alt="" />
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
        <b-alert v-model="showRatingAlert" dismissible fade variant="success">
            Success! You gave this {{employee.e.toLowerCase()}} {{rating}} stars.
        </b-alert>
        <b-alert v-model="showFailedAlert" dismissible fade variant="danger">
        {{message}}
      </b-alert>
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
        <b-row v-if="role === 'ROLE_PATIENT'">
          <select id="mySelect" name="available_appointments" v-model="app" @change="setAppointment(app)">
              <option class="med" v-for="a in this.employee.appointments" :key="a.id" :value="a">{{parseAppointment(a)}}</option>
          </select>
          <b-button class="ml-2" @click="reserveExamination()">Rezerviši izabrani termin</b-button>
        </b-row>
      </b-container>
      <template #modal-header="{ close }">
        <b-col align-h="start">
          <b-button class="mt-1" @click="close()"> Close </b-button>
        </b-col>
        <b-col class="rate" v-if="role === 'ROLE_PATIENT'" style="margin-right:auto;">
          <input type="radio" id="star52" name="rate" value="5" @click="postRatingEmployee(5)" v-model.number="rating"/>
          <label for="star52" title="text"></label>
          <input type="radio" id="star42" name="rate" value="4" @click="postRatingEmployee(4)" v-model.number="rating"/>
          <label for="star42" title="text"></label>
          <input type="radio" id="star32" name="rate" value="3" @click="postRatingEmployee(3)" v-model.number="rating"/>
          <label for="star32" title="text"></label>
          <input type="radio" id="star22" name="rate" value="2" @click="postRatingEmployee(2)" v-model.number="rating"/>
          <label for="star22" title="text"></label>
          <input type="radio" id="star12" name="rate" value="1" @click="postRatingEmployee(1)" v-model.number="rating"/>
          <label for="star12" title="text"></label> 
        </b-col>
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
  mounted() {
    this.role = JSON.parse(
        atob(localStorage.getItem("token").split(".")[1])
      ).role;
  },
  data() {
    return {
      ratings: 0,
      modal: "",
      show: false,
      appointment: null,
      app:"",
      role: '',
      rating: null,
      showRatingAlert: false,
      showFailedAlert: false,
      showFailedReserve:false,
      message:"",
      
    };
  },
  methods: {

    getRatingOfUser() {
      var patientEmail = JSON.parse(atob(localStorage.getItem('token').split(".")[1])).sub;
      this.axios.get(process.env.VUE_APP_API_URL + `/patients/get_rating/${patientEmail}/${this.$route.params.id}/pharmacy`)
        .then(response => 
        this.rating = response.data
        )
        .catch(error => console.log(error));
    }, 

    postRatingEmployee(rating) {
      this.rating = rating
      this.showFailedAlert = false;
      this.axios.post(process.env.VUE_APP_API_URL+'/patients/rating', {
        rateType: this.employee.e === "Pharmacist" ? 2 : 3,
        ratedEmployeeEmail: this.employee.email,
        rating: this.rating,
        patientEmail: JSON.parse(atob(localStorage.getItem('token').split(".")[1])).sub,
      },
      {
        headers: {
          Authorization: "Bearer " + localStorage.getItem('token'),
      }}).then(() => this.showRatingAlert = true)
        .catch(() => {
          this.showFailedAlert = true;
          this.message = "You never visited this employee, you cannot rate it!"
          this.rating = -1;
          });
    },

    parseAppointment(a) {
      let retval = "";
      let date = new Date(a.date[0], a.date[1] - 1, a.date[2]);

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
              console.log(typeof JSON.parse(atob(localStorage.getItem('token').split(".")[1])).sub);

      this.axios.post(process.env.VUE_APP_API_URL+'/patients/reserve_appointment', {
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
      }).catch((error => {
         
          if(error.response.status == "400"){
           
            this.message = " You have 3 penaults You can't reserve it.";
               this.showFailedAlert = true;
          }else if(error.response.status == "403"){
           this.message = " You can't reserve it";
           this.showFailedAlert =true;
          }
         
        }));
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


	.rate {
	float: left;
	height: 46px;
	padding: 0 10px;
	}
	.rate:not(:checked) > input {
		position:absolute;
		top:-9999px;
	}
	.rate:not(:checked) > label {
		float:right;
		width:1em;
		overflow:hidden;
		white-space:nowrap;
		cursor:pointer;
		font-size:30px;
		color:#ccc;

	}
	.rate:not(:checked) > label:before {
		content: '★ ';
	}
	.rate > input:checked ~ label {
		color: #ffc700;    
	}
	.rate:not(:checked) > label:hover,
	.rate:not(:checked) > label:hover ~ label {
		color: #deb217;  
	}
	.rate > input:checked + label:hover,
	.rate > input:checked + label:hover ~ label,
	.rate > input:checked ~ label:hover,
	.rate > input:checked ~ label:hover ~ label,
	.rate > label:hover ~ input:checked ~ label {
		color: #c59b08;
	}

</style>