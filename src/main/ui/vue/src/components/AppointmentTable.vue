<template>
  <div class="my-form">
    <div class="card-header">Appointments</div>
    <b-modal
        :id="errorModal.id"
        :title="errorModal.title"
        ok-only
        @ended="errModal"
      >
        <pre>{{ errorModal.content }}</pre>
      </b-modal>
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
          class="mb-0"
        >
          <b-button @click="AddAppointment($event.target)" variant="success"
            >Add</b-button
          >
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
      striped
      hover
      :filter-included-fields="filterOn"
      small
      @filtered="onFiltered"
    >
    </b-table>
    <b-modal
      size="lm"
      :id="addModal.id"
      :title="addModal.title"
      @ok="AddOne"
      @ended="resetInfoModal"
    >
      <template>
        <div>
          <label class="empl">Choose dermatologist</label>
          <multiselect
            v-model="e"
            v-if="selected"
            :options="dermatologists"
            :multiple="false"
            :close-on-select="false"
            :clear-on-select="false"
            :preserve-search="true"
            :preselect-first="true"
            track-by="email"
            label="FullName"
          >
          </multiselect>
        </div>
        <div>
          <label></label>
          <label for="dd"> Date: </label>
          <b-form-datepicker
            :date-disabled-fn="dateDisabled"
            v-model="date"
            locale="en"
            class="mb-2"
          ></b-form-datepicker>
        </div>
        <div>
          <label></label>
          <label for="tf"> Time from: </label>
          <b-form-timepicker v-model="timefrom" locale="en"></b-form-timepicker>
        </div>
        <div>
          <label></label>
          <label for="tt"> Time to: </label>
          <b-form-timepicker v-model="timeto" locale="en"></b-form-timepicker>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script>
import Multiselect from "vue-multiselect";
import moment from 'moment'

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
        { key: "type", sortable: true, label: "Appointment" },
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
      pharmacy: {},
      pharmacyId: 0,
      e: {},
      dermatologists: [],
      selected: Boolean,
      date: "",
      timefrom: "",
      timeto: "",
      message: "",
      d: Object,
      weekday: "",
      list: [],
      allAppointments: [],
    };
  },
  mounted() {
    var AdminUsername = JSON.parse(
      atob(localStorage.getItem("token").split(".")[1])
    ).sub;
    var self = this;
    self.axios
      .get(`/api/pharmacyAdmin/` + AdminUsername, {
        headers: {
          Authorization: "Bearer " + localStorage.getItem("token"),
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
     datum: function(date){
      return moment(date,"YYYY-MM-DD").format("DD/MM/YYYY");
    },
    vreme: function(time){
      return moment(time,"hh:mm:ss").format("hh:mm:ss");
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
      var self = this;
      if (
        !self.e ||
        !self.selected ||
        !self.date ||
        !self.timefrom ||
        !self.timeto
      ) {
        self.message = "You must enter all data! ";
        self.showAlert = true;
      }
      self.d = new Date(self.date);
      if (self.d.getDay() == 1) {
        self.weekday = "monday";
      } else if (self.d.getDay() == 2) {
        self.weekday = "tuesday";
      } else if (self.d.getDay() == 3) {
        self.weekday = "wednesday";
      } else if (self.d.getDay() == 4) {
        self.weekday = "thursday";
      } else if (self.d.getDay() == 5) {
        self.weekday = "friday";
      } else if (self.d.getDay() == 6) {
        self.weekday = "saturday";
      } else if (self.d.getDay() == 7) {
        self.weekday = "sunday";
      }
      self.axios
        .post(
          `/api/appointments/` + parseInt(self.pharmacyId)+"/"+self.e.email,
          {
            date: self.date,
            termFrom: self.timefrom,
            termTo: self.timeto,
            type: 1,
          },
          {
            headers: {
              Authorization: "Bearer " + localStorage.getItem("token"),
            },
          }
        )
        .then(function (response) {
           self.errorModal.content ="Successfully created appointment! ";
          self.$root.$emit("bv::show::modal", self.errorModal.id);
          console.log(response);
          self.items = [];
          self.refresh();
        })
        .catch(function (error) {
           self.errorModal.content ="Error! ";
          self.$root.$emit("bv::show::modal", self.errorModal.id);
          console.log(error);
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
    refresh() {
      var self = this;
      self.axios
        .get(`/api/pharmacy/appointments/` + self.pharmacyId, {
          headers: { Authorization: "Bearer " + localStorage.getItem("token") },
        })
        .then(function (response) {
          for (var i = 0; i < response.data.length; i++) {
            var item = {};
            item.id = response.data[i].appointmentId;
            item.date1 = self.datum(response.data[i].date);
            item.timeFrom = self.vreme(response.data[i].termFrom);
            item.timeTo = self.vreme(response.data[i].termTo);
            item.type = response.data[i].type;
            item.employee =
              response.data[i].employee.name +
              " " +
              response.data[i].employee.lastName;
            self.items.push(item);
          }
          self.totalRows = self.items.length;
        });
    },
    GetAllEmployees() {
      var self = this;
      self.axios
        .get(`/api/pharmacy/dermatologists/` + parseInt(self.pharmacyId), {
          headers: {
            Authorization: "Bearer " + localStorage.getItem("token"),
          },
        })
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
              item.FullName = item.name + " " + item.lastName;
              self.dermatologists.push(item);
            }
          }
        });
    },
  },
};
</script>
<style scoped>
.my-form .card-header {
  background-color: #ccffbc;
}
</style>