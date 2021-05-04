<template>
<div class="my-form">
   <div class="card-header">Appointments</div>
            <b-alert v-model="showFailedAlert" dismissible fade variant="danger">
                Cannot cancel this medicament! It's expiry date is in less than 24 hours.
            </b-alert>
            <b-alert v-model="showSuccessAlert" dismissible fade variant="success">
                You successfully canceled your medicament reservation!
            </b-alert>
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
                  <b-button variant="danger" @click="cancelAppointment()">
                      Cancel medicament reservation
                  </b-button>
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
              striped hover selectable 
              :filter-included-fields="filterOn"
              select-mode="single"
              small
              @filtered="onFiltered"
              @row-clicked="test">
            </b-table>
            <template slot="actions" slot-scope="row">
    <!-- We use @click.stop here to prevent a 'row-clicked' event from also happening -->
    <b-button size="sm" @click.stop="row.toggleDetails">
        {{ row.detailsShowing ? 'Hide' : 'Show' }} Details
    </b-button>
</template>
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
              
              
            </b-modal>
          </div>
</template>

<script>

export default {
components: {
  },
  data() {
    return {
      fields: [
        { key: "medicamentName", sortable: true, label: "Medicament" },
        { key: "quantity", sortable: true, label: "Quantity" },
        { key: "expiryDate", sortable: true, label: "Expiry date" },
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
      
      e :{},
      selected :  Boolean,
      date : "",
      message: "",
      d : Object,
      weekday: "",
      list:  [],
      allMedicaments : "",
      t : 0,
      selectedMedicament: "",
      selectedIndex: "",
      showFailedAlert: false,
      showSuccessAlert: false
      };
  },
  mounted() {
      var username = JSON.parse(
      atob(localStorage.getItem('token').split(".")[1])
    ).sub;
    var self = this;
    self.axios
      .get(`http://localhost:8080/api/patients/reservedMedication/` + username, {
        headers: {
          Authorization: "Bearer " + localStorage.getItem('token'),
        },
      })
      .then(function (response) {
        self.allMedicaments = response.data;
        console.log(response.data);
        for (var i = 0; i < response.data.length; i++) {
          var item = {};
          item.id = response.data[i].patientEmail;
          item.expiryDate = response.data[i].expiryDate;
          item.quantity = response.data[i].quantity;
          item.medicamentName = response.data[i].medicament.name
          item.medicament = response.data[i].medicament
          self.items.push(item);
        }
        self.totalRows = self.items.length;
        self.items.forEach((obj) => {
                                obj["expiryDate"] = new Date(obj["expiryDate"]).toDateString();
                                });
      })
      .catch(function (error) {
        console.log(error);
      });
       
  },
  methods: {
    test(item, index) {
        this.selectedMedicament = item.medicament;
        console.log(this.selectedMedicament);
        this.selectedIndex = index;
        console.log(index);
        
    },
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
    
    cancelAppointment() {
        this.showFailedAlert = false;
        this.showSuccessAlert = false;
        this.axios.delete(`http://localhost:8080/api/patients/cancelReservation/`+this.selectedMedicament.id,  {
            headers: {
              Authorization: "Bearer " + localStorage.getItem('token'),
            },
          }).then(() => {
              
              this.allMedicaments.splice(this.selectedIndex, 1);
              this.items.splice(this.selectedIndex, 1);
              console.log(this.allMedicaments);
              this.showSuccessAlert = true;
          }).catch(err => {this.showFailedAlert = true;
          console.log(err)});
    },
      
      dateDisabled(ymd, date) {
      const today = new Date();
      const m = today.getMonth();
      const d = today.getDate();
      const day1 = date.getDate();
      const month1 = date.getMonth();
      return (day1 <= d && month1 <= m) || (day1 >= d && month1 < m);
    },
  },
};
</script>
<style scoped>
.my-form .card-header {
  background-color:#ccffbc;
}
</style>