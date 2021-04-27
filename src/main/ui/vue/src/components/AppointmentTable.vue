<template>
<div class="my-form">
   <div class="card-header">Appointments</div>
            <b-row>
              <b-col lg="4" class="my-1">
                <b-form-group
                  label-for="filter-input"
                  label-cols-sm="3"
                  label-align-sm="right"
                  label-size="sm"
                  class="mb-0"
                >
                  <b-input-group size="sm">
                    <b-form-input
                      id="filter-input"
                      v-model="filter"
                      type="search"
                      placeholder="Search"
                    >
                    </b-form-input>
                    <b-input-group-append>
                      <b-button :disabled="!filter" @click="filter = ''"
                        >Clear</b-button
                      >
                    </b-input-group-append>
                  </b-input-group>
                </b-form-group>
              </b-col>
               <b-col lg="8" class="my-1">
                <b-form-group
                  label-cols-sm="3"
                  label-align-sm="right"
                  label-size="sm"
                  class="mb-0">
                  <b-button
                    @click="AddAppointment($event.target)"
                    variant="success"
                    >Add</b-button>
                </b-form-group>
              </b-col>
            </b-row>
            <b-table
              ref="table"
              :items="items"
              :fields="fields"
              responsive="sm"
              :sort-by.sync="sortBy"
              :sort-desc.sync="sortDesc"
              :filter="filter"
              striped hover
              :filter-included-fields="filterOn"
              small
              @filtered="onFiltered">
            </b-table>
            <b-modal
              :id="errorModal.id"
              :title="errorModal.title"
              ok-only
              @ended="errModal"
              :header-bg-variant="headerErrorVariant"
              :footer-bg-variant="headerErrorVariant"
            >
              <pre>{{ errorModal.content }}</pre>
            </b-modal>
            <b-modal
              size="lm"
              :id="addModal.id"
              :title="addModal.title"
              @ok = "AddOne"
              @ended="resetInfoModal"
              :header-bg-variant="headerBgVariant"
              :footer-bg-variant="headerBgVariant">
              
              <template>
                    <b-alert v-model="showAlert" dismissible fade variant="danger">
                        {{message}}
                    </b-alert>
                  <div>
                <b-form-group label="Appointment type" v-slot="{ ariaDescribedby }">
                <b-form-radio v-model="selected" :aria-describedby="ariaDescribedby" :value="true">Examination</b-form-radio>
                <b-form-radio v-model="selected" :aria-describedby="ariaDescribedby"  :value="false">Counseling</b-form-radio>
                </b-form-group>
                </div>
                <div>
                  <label class="empl"
                    >Choose dermatologist/pharmacist</label
                  >
                  <multiselect
                    v-model="e"
                    v-if="selected" 
                    :options="dermatologists"
                    :multiple="false"
                    :close-on-select="false"
                    :clear-on-select="false"
                    :preserve-search="true"
                    :preselect-first="true"
                    :t =1
                    track-by="email"
                    label="FullName"
                  >
                  </multiselect>
                  <multiselect
                    v-model="e"
                    v-if="selected == false" 
                    :options="pharmacists"
                    :multiple="false"
                    :close-on-select="false"
                    :clear-on-select="false"
                    :preserve-search="true"
                    :preselect-first="true"
                    :t =0
                    track-by="email"
                    label="FullName"
                  >
                  </multiselect>
                </div>
                <div>
                    <label></label>
                    <label for="dd"> Date:   </label>
                    <b-form-datepicker :date-disabled-fn="dateDisabled"  v-model="date"  locale="en" class="mb-2"></b-form-datepicker>
              </div>
              <div>
                    <label></label>
                    <label for="tf"> Time from:   </label>
                   <b-form-timepicker v-model="timefrom"  locale="en"></b-form-timepicker>
              </div>
              <div>
                    <label></label>
                    <label for="tt"> Time to:   </label>
                     <b-form-timepicker v-model="timeto"  locale="en"></b-form-timepicker>
              </div>
                </template>
            </b-modal>
          </div>
</template>

<script>
import Multiselect from "vue-multiselect";

export default {
components: {
    Multiselect,
  },
  data() {
    return {
      fields: [
        { key: "date1", sortable: true, label: "Date" },
        { key: "timeFrom", sortable: true, label: "Time from" },
        { key: "timeTo", sortable: true, label: "Time to" },
        { key: "employee", sortable: true, label: "Employee" },
        { key: "type", sortable: true, label: "Appointment" }
      ],
      items: [],
      selectMode: "single",
      sortBy: "date",
      sortDesc: false,
      filter: null,
      filterOn: [],
      totalRows: 1,
      totalRows1: 1,
      new: [],
      errorModal: {
        id: "error-modal",
        title: "",
        content: "",
      },
      addModal: {
        id: "add-modal",
        title: "",
        content: "",
      },
      showAlert: false,
      headerBgVariant: "success",
      headerErrorVariant: "warning",
      pharmacy: {},
      pharmacyId:0,
      e :{},
      dermatologists: [],
      pharmacists: [],
      selected :  Boolean,
      date : "",
      timefrom : "",
      timeto : "",
      message: "",
      d : Object,
      weekday: "",
      list:  [],
      allAppointments : [],
      t :1,
    };
  },
  mounted() {
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
      .then(function (response) {
        self.pharmacyId = response.data.pharmacy.id;
        self.GetAllEmployees();
        self.refresh(); 
      })
      .catch(function (error) {
        console.log(error);
      });
       
  },
  methods: {
    onFiltered(filteredItems) {
      self.totalRows = filteredItems.length;
    },

    resetInfoModal() {
      this.infoModal.title = "";
      this.infoModal.content = "";
    },

    errModal() {
      this.errorModal.title = "";
      this.errorModal.content = "";
    },
    AddAppointment(button) {
      (this.addModal.title = "Add appointment"),
        this.$root.$emit("bv::show::modal", this.addModal.id, button);
    },
    AddOne() {
        if( !this.e || !this.selected || !this.date || !this.timefrom || !this.timeto){
            this.message = "You must enter all data! ";
            this.showAlert = true;
        }
      this.d =  new Date(this.date);
      if(this.d.getDay() == 1){
        this.weekday ="monday";
      }else if(this.d.getDay() == 2){
        this.weekday ="tuesday";
      }else if(this.d.getDay() == 3){
        this.weekday = "wednesday";
      }else if(this.d.getDay() == 4){
        this.weekday = "thursday";
      }else if(this.d.getDay() == 5){
        this.weekday = "friday";
      }else if(this.d.getDay() == 6){
        this.weekday = "saturday";
      }else if(this.d.getDay() == 7){
        this.weekday = "sunday";
      }
      if(this.selected == true){
        this.t =1;
      }else{
        this.t =0;
      }
               this.axios.post(`/api/appointments/`+ parseInt(this.pharmacyId),{
                  patient : {
                      email: this.e.email,
                  },
                  date : this.date,
                  termFrom : this.timefrom,
                  termTo: this.timeto,
                  type : parseInt(this.t),
        }, {
             headers: {
            Authorization: 'Bearer ' + localStorage.getItem('token')
        }
        })
        .then((response) => {
            this.message = "Successfully created appointment! ";
            this.showAlert = true;
            console.log(response);
            this.items = [];
            this.refresh();
        })
        .catch(function (error) {
           console.log(error);
           this.message = "Error! ";
            this.showAlert = true;
        });
            
     
      
    },
     dateDisabled(ymd, date) {
      const today = new Date();
      const m = today.getMonth();
      const d = today.getDate();
      const day1 = date.getDate();
      const month1 = date.getMonth();
      return (day1 <= d && month1 <= m) || (day1 >= d && month1 < m);
    },
    refresh(){
        var self = this;
        self.axios
      .get(`/api/pharmacy/appointments/`+ self.pharmacyId, {
          headers: {Authorization: "Bearer " + localStorage.getItem('token')}
        })
      .then(function (response) {
        for (var i = 0; i < response.data.length; i++) {
          var item = {};
          item.id = response.data[i].appointmentId;
          item.date = response.data[i].date;
          item.timeFrom = response.data[i].termFrom;
          item.timeTo = response.data[i].termTo;
          item.type = response.data[i].type;
          item.employee = response.data[i].employee.name + " "+ response.data[i].employee.lastName;
          self.items.push(item);
        }
        self.totalRows = self.items.length;
        self.items.forEach((obj) => {
                                obj["date1"] = new Date(obj["date"]).toDateString();
                                obj["date"][1] -= 1;
                                let timeFromArray = obj["date"].concat(obj["timeFrom"]).concat([0, 0]);
                                obj["timeFrom"] = new Date(...timeFromArray).toTimeString();
                                let timeToArray = obj["date"].concat(obj["timeTo"]).concat([0, 0]);
                                obj["timeTo"] = new Date(...timeToArray).toTimeString();
                            });
      });
  },
  GetAllEmployees(){
      var self = this;
      self.axios
        .get(`/api/pharmacy/dermatologists/` +
            parseInt(self.pharmacyId),
          {
            headers: {
              Authorization: "Bearer " + localStorage.getItem('token'),
            },
          }
        )
        .then(function (response) {
          for (var i = 0; i < response.data.length; i++) {
            //console.log(response.data[i]);
            var count = 0;
            for (var j = 0; j < response.data[i].workHours.length; j++) {
              if (response.data[i].workHours[j].deleted) {
                count++;
              }
            }
            if (count != 7) {
              var item = {};
              item.name = response.data[i].name;
              item.lastName = response.data[i].lastName;
              item.email = response.data[i].email;
              item.phoneNumber = response.data[i].phoneNumber;
              item.hours = response.data[i].workHours;
              item.address = response.data[i].address;
              item.FullName = item.name + " "+ item.lastName;
              self.dermatologists.push(item);
            }
          }
    });
    self.axios
        .get(`/api/pharmacy/pharmacists/` +
            parseInt(self.pharmacyId),
          {
            headers: {
              Authorization: "Bearer " + localStorage.getItem('token'),
            },
          }
        )
        .then(function (response) {
          for (var i = 0; i < response.data.length; i++) {
            var count = 0;
            for (var j = 0; j < response.data[i].workHours.length; j++) {
              if (response.data[i].workHours[j].deleted) {
                count++;
              }
            }
            if (count != 7) {
              var item = {};
              item.name = response.data[i].name;
              item.lastName = response.data[i].lastName;
              item.email = response.data[i].email;
              item.phoneNumber = response.data[i].phoneNumber;
              item.hours = response.data[i].workHours;
              item.address = response.data[i].address;
              item.FullName = item.name + " "+ item.lastName;
              self.pharmacists.push(item);
            }
          }
    });
  }
    
  },
};
</script>
<style scoped>
.my-form .card-header {
  background-color:#ccffbc;
}
</style>