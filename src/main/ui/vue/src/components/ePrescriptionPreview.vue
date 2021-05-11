<template>
    <div>
        <div class="card-header" style="background-color:#ccffbc;">ePrescriptions</div>
            <b-row>
              <b-col lg="4" class="my-1">
                <b-form-group
                  label-for="filter-input"
                  label-cols-sm="3"
                  label-align-sm="right"
                  label-size="sm"
                  class="mb-0"
                >
                  <b-input-group size="md">
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
                </b-form-group>
              </b-col>
            </b-row>
            <div v-for="e in this.ePrescriptions" :key="e.id" :value="e" class="col-lg-12">
                <b-row class="card-header eprescription-header" @click="log()" selectable>
                    <b-col> 
                        <div >ePrescription ID: {{e.id}}</div>
                    </b-col>
                    <b-col>
                        <div style="text-align:center;">Expiry Date: {{formatDate(e.expiryDate)}}</div>
                    </b-col>
                    
                </b-row>
                <b-row class="mb-4">
                        <b-table
                        fixed
                        ref="table"
                        :items="e.medicine"
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
                        style="border-bottom: 1px solid lightgrey">
                        </b-table>
                </b-row>
            </div>
            <template slot="actions" slot-scope="row">
    <!-- We use @click.stop here to prevent a 'row-clicked' event from also happening -->
    <b-button size="sm" @click.stop="row.toggleDetails">
        {{ row.detailsShowing ? 'Hide' : 'Show' }} Details
    </b-button>
    </template>

    </div>
</template>

<script>
export default {
    
    data() {
        return {
        fields: [
            { key: "name", sortable: true, label: "Medicament" },
            { key: "quantity", sortable: true, label: "Quantity" },
        ],
        ePrescriptions: [],
        items: [],
        selectMode: "single",
        sortBy: "date",
        sortDesc: false,
        filter: null,
        filterOn: [],
        totalRows: 1,
        totalRows1: 1,
        new: [],
        
    
        };
    },

    mounted() {
        this.loadePrescriptions();
    },

    methods: {
        loadePrescriptions() {
            var username = JSON.parse(atob(localStorage.getItem("token").split(".")[1])).sub;

            this.axios.get(`http://localhost:8080/api/patients/eprescription/${username}`)
                .then(response => {this.ePrescriptions = response.data
                console.log(this.ePrescriptions)})
                .catch(error => alert(error));
        },

        formatDate(date) {
            return `${date[0]}/${date[1]}/${date[2]}`
        },
        
        onFiltered(filteredItems) {
            self.totalRows = filteredItems.length;
        },
    },
}
</script>

<style scoped>
    .eprescription-header {
        text-align: left;
        font-size: 16pt;
        background-color: rgb(214, 214, 214);
        
    }

    .eprescription-header:hover {
        background: rgb(235, 235, 235);
    }

</style>