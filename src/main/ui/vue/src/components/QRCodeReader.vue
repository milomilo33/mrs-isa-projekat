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
                <input type="file" @change="fileSelected">
              </b-col>
            </b-row>
                <b-row class="mb-4">
                        <b-table
                        fixed
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
                        style="border-bottom: 1px solid lightgrey">
                        <template #cell(show_details)="row">
                        <b-button size="sm" @click="row.toggleDetails" class="mr-2">
                            {{ row.detailsShowing ? 'Hide' : 'Show'}} Details
                        </b-button>
                        </template>
                        <template #cell(update)="row">
                        <b-button size="sm" @click="purchase(row)" class="mr-2">
                            Purchase
                        </b-button>
                        </template>
                        <template #row-details="row">
                          <b-card> 
                            <div  v-for="m in qrCodeItem.qrCode.prescriptionMedicaments" :value="m" :key="m.medicamentId">
                            <b-row class="mb-2">
                              <b-col sm="3" class="text-sm-right"><b>Medicament: </b></b-col>
                              ({{m.medicamentId}}) {{m.name}}
                            </b-row>

                            <b-row class="mb-2">
                              <b-col sm="3" class="text-sm-right"><b>Quantity: </b></b-col>
                              {{m.quantity}}
                            </b-row>
                             <b-row class="mb-2">
                              <b-col sm="3" class="text-sm-right"><b>Points: </b></b-col>
                              {{m.points}}
                            </b-row>
                            </div>
                            <b-button size="sm" @click="row.toggleDetails">Hide Details</b-button>
                        </b-card>
                      </template>
                        </b-table>
                </b-row>
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
            { key: "name", sortable: true, label: "Pharmacy" },
            { key: "address", sortable: true, label: "Address", formatter: value => {
              return value.street + " "+ value.number;
            }},
            { key: "rating", sortable: true, label: "Rating"},
            { key: "cost", sortable: true, label: "TotalPrice" },
            { key: "show_details", label: "Medicaments"}, 
            { key: "update", label: "Buy" }
        ],
       
        items: [],
        selectMode: "single",
        sortBy: "date",
        sortDesc: false,
        filter: null,
        filterOn: [],
        totalRows: 1,
        totalRows1: 1,
        selectedFile: "",
        new: [],
        username: "",
        qrCodeItem: null,
        addressFormat: "",
    
        };
    },

    methods: {
        
        fileSelected(event){
          this.username = JSON.parse(atob(localStorage.getItem("token").split(".")[1])).sub;
          this.selectedFile = event.target.files[0].name;
          this.axios.get(`http://localhost:8080/api/patients/uploadQRCode/${this.selectedFile},${this.username}`)
                .then(response => {this.qrCodeItem = response.data
                this.items = this.qrCodeItem.pharmacySet;
                console.log(this.qrCodeItem)
                })
                .catch(error => console.log(error));

        },
        purchase(row){
          console.log(row.item);
        }
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