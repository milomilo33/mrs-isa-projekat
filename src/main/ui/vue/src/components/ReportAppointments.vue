<template >
    <div class="Filter">
        <p>Choose period:</p>
        <div class="form-check">
          <input
            class="form-check-input"
            type="radio"
            name="tip"
            id="tip"
            value=0
            v-model="tip"
          />
          <label class="form-check-label" for="tip">
            Month
          </label>
        </div>

        <div class="form-check">
          <input
            class="form-check-input"
            type="radio"
            name="tip"
            id="tip1"
            value=1
            checked
            v-model="tip"
          />
          <label class="form-check-label" for="tip1">
            Quarter
          </label>
        </div>
         <div class="form-check">
          <input
            class="form-check-input"
            type="radio"
            name="tip"
            id="tip2"
            value=2
            checked
            v-model="tip"
          />
          <label class="form-check-label" for="tip2">
            Year
          </label>
        </div>
         <b-button size="sm" class="my-2 my-sm-0 button" type="submit" @click = "findFilter"
        >Filter</b-button>
   
  <div class="large" v-if="value === true">
    <line-chart :chartData="chartData" :options="options"></line-chart>
  </div>
</div>
</template>

<script>
  import LineChart from './Chart.js'

  export default {
    components: {
      LineChart
    },
    data () {
      return {
        chartData: null,
        options: null,
        d:[],
        l:[],
        value : false,
        tip:"",
        pharmacyId:"",
      }
    },
    mounted () {
       var AdminUsername = JSON.parse(
      atob(localStorage.getItem('token').split(".")[1])
    ).sub;
    var self = this;
    self.axios
      .get(`/api/pharmacyAdmin/` + AdminUsername, {
        headers: {
          Authorization: "Bearer " + localStorage.getItem('token'),
        },
      })
      .then(response => {
           self.pharmacyId = response.data.pharmacy.id;
       
      })
      .catch(error => {
        console.log(error);
      }); 
    },
    methods:{
        fillData(){
        this.chartData = {
          labels: this.l,
          datasets: [
            {
              label: 'Appointments',
              backgroundColor: '#ccffbc',
              data:this.d
            }, 
          ],
        },
        this.options= {
        //responsive: true, 
        maintainAspectRatio: false
        }
        this.value = true;
        },
        findFilter(){
            var self = this;
            self.l = [];
            self.d =[];
            if(self.tip == "0"){
                 self.axios
                    .get(`/api/pharmacy/reportAppointments/` + parseInt(self.pharmacyId), {
                    headers: {
                    Authorization: "Bearer " + localStorage.getItem('token'),
                    },
                })
                .then(function(response) {
                for(var i =0;i<response.data.monthAppoinntments.length;i++){
                    self.l.push(response.data.monthAppoinntments[i].day);
                    self.d.push(response.data.monthAppoinntments[i].value);   
                } 
                })
                .catch(error => {
                    console.log(error);
                });
               
            }else if(self.tip == "2"){
                 self.axios
                    .get(`/api/pharmacy/reportAppointments1/` + parseInt(self.pharmacyId), {
                    headers: {
                    Authorization: "Bearer " + localStorage.getItem('token'),
                    },
                })
                .then(function(response) {
                for(var i =0;i<response.data.monthAppoinntments.length;i++){
                    console.log(response.data.monthAppoinntments[i]);
                    self.l.push(response.data.monthAppoinntments[i].month);
                    self.d.push(response.data.monthAppoinntments[i].value);   
                } 
                })
                .catch(error => {
                    console.log(error);
                });
                
            }else if(self.tip == "1"){
                 self.axios
                    .get(`/api/pharmacy/reportAppointments2/` + parseInt(self.pharmacyId), {
                    headers: {
                    Authorization: "Bearer " + localStorage.getItem('token'),
                    },
                })
                .then(function(response) {
                for(var i =0;i<response.data.monthAppoinntments.length;i++){
                    console.log(response.data.monthAppoinntments[i]);
                    self.l.push(response.data.monthAppoinntments[i].quarter);
                    self.d.push(response.data.monthAppoinntments[i].value);   
                } 
                })
                .catch(error => {
                    console.log(error);
                });
            }
             self.fillData();
        }
    }
  }
</script>

<style>
  .small {
    max-width: 600px;
    margin:  150px auto;
  }
</style>


