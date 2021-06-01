<template>
  <b-container fluid> 
    <b-alert v-model="showSuccess" dismissible fade variant="success">
      Success! You deleted pharmacy.
    </b-alert>
    <b-alert v-model="showAlert" dismissible fade variant="danger">
     Failed to delete pharmacy.
    </b-alert> 
    <b-alert v-model="showAdminAcceptance" dismissible fade variant="success">
     Success! Admin added.
    </b-alert> 
    

    <b-table
      :items="items"
      :fields="fields"
      :current-page="currentPage"
      :per-page="perPage"
      stacked="md"
      show-empty
    >
      <template #cell(deletePharmacy)="row">
        <b-button size="sm" @click="deletePharmacy(row)" class="mr-2">
            Delete
        </b-button>
      </template>
      <template #cell(admins)="row">
        <b-button size="sm" @click="info(row.item, row.index, $event.target)" class="mr-1">
          Add admin
        </b-button>
      </template>
    </b-table>

    <!-- Info modal -->
    <b-modal :id="infoModal.id" :title="infoModal.title" ok-only @hide="resetInfoModal">
       <div class="form-row">
              <div class="form-group col-md-12">
                <div>
                  <label class="typo__label"
                    >Choose Admin</label
                  >
                  <multiselect
                    v-model="added"
                    :options="admins"
                    :multiple="false"
                    :close-on-select="false"
                    :clear-on-select="false"
                    :preserve-search="true"
                    :preselect-first="false"
                    track-by="email"
                    label="email"
                  >
                  </multiselect>
                </div>
              </div>
       </div>
       <div class="form-row">
        <b-button type="button" @click="setAdmins"> Add </b-button>
       </div>
    </b-modal>

    <b-row class="justify-content-center">
      <b-col sm="7" md="8" class="my-3">
        <b-pagination
          v-model="currentPage"
          :total-rows="totalRows"
          :per-page="perPage"
          align="fill"
          size="sm"
          class="my-3"
        ></b-pagination>
      </b-col>
    </b-row>
  </b-container>
</template>
<script>
import { defineComponent } from '@vue/composition-api'
import Multiselect from "vue-multiselect";
export default defineComponent({
 name: "PharmacyTable",
  components: {
    Multiselect,
  },
 data(){
   return{
     items: [],
     admins: [],
     added: [],
     pharmacy: null,
     showAlert: false,
     showSuccess: false,
     showAdminAcceptance: false,
     fields: [{key: "id", sortable: true, label: "Id"},
              {key: "name", sortable:true, label: "Name"}, 
             { key: "address", sortable: true, label: "Address", formatter: value => {
              return value.street + " "+ value.number;
            }},
              {key: "admins", label: "Add admin"},
              {key: "deletePharmacy", label: "Delete"}],
     totalRows: 1,
     currentPage: 1,
     perPage: 5,
     infoModal: {
          id: 'info-modal',
          title: '',
          content: ''
        }
   }
 }, 
 mounted(){
    var self = this;
    self.axios
      .get(`/api/pharmacy/getAllWithAdmins`, {
        headers: {
          Authorization: "Bearer " + localStorage.getItem("token"),
        },
      })
      .then(function (response) {
        self.items = response.data;
        self.totalRows = self.items.length;
        
      })
      .catch(function (error) {
        console.log(error);
       
      });

      self.axios
      .get(`/api/pharmacyAdmin/getUnemployedAdmins`, {
        headers: {
          Authorization: "Bearer " + localStorage.getItem("token"),
        },
      })
      .then(function (response){
        self.admins = response.data;
      })
      .catch(function (error) {
        console.log(error);
      });
 },
 methods: {
    deletePharmacy(row){
     var self = this;
    self.axios
      .get(`/api/pharmacy/delete/`+parseInt(row.item.id), {
        headers: {
          Authorization: "Bearer " + localStorage.getItem("token"),
        },
      })
      .then(function () {
        
        const idx = self.items.indexOf(row.item);
        self.items.splice(idx, 1);
        self.showAlert = false;
        self.showSuccess = true;
        self.showAdminAcceptance = false;
      })
      .catch(function (error) {
        console.log(error);
        self.showAlert = true;
      });
   },
   info(i, button) {
        this.infoModal.title = `Add admin to pharmacy:`
        this.pharmacy = i;
        this.$root.$emit('bv::show::modal', this.infoModal.id, button)
      },
    resetInfoModal() {
        this.infoModal.title = ''
        this.infoModal.content = ''
      },
    setAdmins(){
      var self = this;
       this.axios
          .post(
            `/api/pharmacyAdmin/updatePharmacyToAdmin`,
            {
              name: this.added.name,
              email: this.added.email,
              lastName: this.added.lastName,
              phoneNumber: this.added.phoneNumber,
              pharmacy: {
                id: this.pharmacy.id,
                name: this.pharmacy.name}
            },
            {
              headers: {
                Authorization: "Bearer " + localStorage.getItem('token'),
              },
            }
          )
          .then(function (response) {
            if (response.data != null) {
              self.showAdminAcceptance = true;
              var idx = 0;
              for(let i = 0; i < self.admins.length; i++){
                
                if(self.admins[i].email== self.added.email){
                  break;
                }
                idx++;
              }
              self.admins.splice(idx, 1);
              self.added = [];
            }
          })
          .catch(function (error) {
            console.log(error);
            self.showAlert = true;
          });

    }
 }
})
</script>
