<template>
          <div class="my-form">
            <div class="card-header">Requested medicaments</div>
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
          </div>
</template>

<script>
export default {
  data() {
    return {
      fields: [
        { key: "name", sortable: true, label: "Medicament" },
        { key: "quantity", sortable: true, label: "Quantity" },
        { key: "employee", sortable: true, label: "Employee" },
      ],
      items: [],
      sortBy: "name",
      sortDesc: false,
      filter: null,
      filterOn: [],
      totalRows: 1,
      pharmacyId:0,
      adminUser:"",
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
        self.adminUser = response.data.email;
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
    refresh(){
        var self = this;
        self.axios
      .get(`/api/pharmacyAdmin/requestMedicaments/`+ self.adminUser, {
          headers: {Authorization: "Bearer " + localStorage.getItem('token')}
        })
      .then(function (response) {
          //console.log(response.data);
        for (var i = 0; i < response.data.requestMedicaments.length; i++) {
            var rm = {};
            rm.quantity = response.data.requestMedicaments[i].quantity;
            rm.name =  response.data.requestMedicaments[i].medicament.name;
            rm.employee =  response.data.requestMedicaments[i].employee.email;
            self.items.push(rm);
        }
        self.totalRows = self.items.length;
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
