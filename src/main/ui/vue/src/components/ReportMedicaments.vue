<template >
    <div class="Filter">
        <b-col lg="4" class="my-1">
        <b-form-group
          label="Choose period"
          label-cols-sm="3"
          label-align-sm="right"
          label-size="sm"
          class="mb-0"
        >
          <b-form-select v-model="f" :options="o" v-on:change="findFilter"></b-form-select>
        </b-form-group>
      </b-col>
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
        o:["Month", "Quarter","Year"],
        f:0,
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
              type:'bar',
              label: 'Medicaments',
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
            console.log(this.f);
            var self = this;
            self.l = [];
            self.d =[];
            if(self.f == "Month"){
                 self.axios
                    .get(`/api/pharmacy/reportMedicaments/` + parseInt(self.pharmacyId), {
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
               
            }else if(self.f == "Year"){
                 self.axios
                    .get(`/api/pharmacy/reportMedicaments1/` + parseInt(self.pharmacyId), {
                    headers: {
                    Authorization: "Bearer " + localStorage.getItem('token'),
                    },
                })
                .then(function(response) {
                for(var i =0;i<response.data.monthAppoinntments.length;i++){
                    self.l.push(response.data.monthAppoinntments[i].month);
                    self.d.push(response.data.monthAppoinntments[i].value);   
                } 
                })
                .catch(error => {
                    console.log(error);
                });
                
            }else if(self.f == "Quarter"){
                 self.axios
                    .get(`/api/pharmacy/reportMedicaments2/` + parseInt(self.pharmacyId), {
                    headers: {
                    Authorization: "Bearer " + localStorage.getItem('token'),
                    },
                })
                .then(function(response) {
                for(var i =0;i<response.data.monthAppoinntments.length;i++){
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


