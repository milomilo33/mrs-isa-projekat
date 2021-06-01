<template >
    <div class="Filter">
         <b-row>
         <b-col lg="2" class="my-1">
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

      <b-col lg="2" class="my-1">
        <b-form-group
          label="Choose year"
          label-cols-sm="3"
          label-align-sm="right"
          label-size="sm"
          class="mb-0"
        >
          <b-form-select v-model="y" :options="years" v-on:change="findFilter"></b-form-select>
        </b-form-group>
      </b-col>
      </b-row>
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
        o:["January","February","March","April","May","June","July","August","September","October","November","December", "Quarter","Year"],
        years:['2021','2020', '2019','2018','2017'],
        f:0,
        y:0,
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
            var self = this;
            self.l = [];
            self.d =[];
            if(self.y != 0 && self.f != 0){
             if(self.f =="January" || self.f =="February" || self.f =="March" || self.f =="April" || self.f =="May" || self.f =="June"
            || self.f =="July" || self.f =="August" || self.f =="September"|| self.f =="October" || self.f =="November"
            || self.f =="December"){
                 self.axios
                    .get(`/api/pharmacy/reportMedicaments/` + parseInt(self.pharmacyId)+"/"+self.f+"/"+self.y, {
                    headers: {
                    Authorization: "Bearer " + localStorage.getItem('token'),
                    },
                })
                .then(function(response) {
                for(var i =0;i<response.data.monthAppoinntments.length;i++){
                    self.l.push(response.data.monthAppoinntments[i].day);
                    self.d.push(response.data.monthAppoinntments[i].value);   
                } 
                self.fillData();
                })
                .catch(error => {
                    console.log(error);
                });
               
            }else if(self.f == "Year"){
                 self.axios
                    .get(`/api/pharmacy/reportMedicaments1/` + parseInt(self.pharmacyId)+"/"+self.y, {
                    headers: {
                    Authorization: "Bearer " + localStorage.getItem('token'),
                    },
                })
                .then(function(response) {
                for(var i =0;i<response.data.monthAppoinntments.length;i++){
                    self.l.push(response.data.monthAppoinntments[i].month);
                    self.d.push(response.data.monthAppoinntments[i].value);   
                } 
                self.fillData();
                })
                .catch(error => {
                    console.log(error);
                });
                
            }else if(self.f == "Quarter"){
                 self.axios
                    .get(`/api/pharmacy/reportMedicaments2/` + parseInt(self.pharmacyId)+"/"+self.y, {
                    headers: {
                    Authorization: "Bearer " + localStorage.getItem('token'),
                    },
                })
                .then(function(response) {
                for(var i =0;i<response.data.monthAppoinntments.length;i++){
                    self.l.push(response.data.monthAppoinntments[i].quarter);
                    self.d.push(response.data.monthAppoinntments[i].value);   
                } 
                self.fillData();
                })
                .catch(error => {
                    console.log(error);
                });
            }
            }
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


