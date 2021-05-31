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
      <pre>{{ infoModal.content }}</pre>
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

export default defineComponent({
 name: "PharmacyTable",
 data(){
   return{
     items: [],
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
        console.log(self.items);
        self.totalRows = self.items.length;
        
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
        console.log(idx);
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
   info(item, index, button) {
        this.infoModal.title = `Row index: ${index}`
        this.infoModal.content = JSON.stringify(item, null, 2)
        this.$root.$emit('bv::show::modal', this.infoModal.id, button)
      },
    resetInfoModal() {
        this.infoModal.title = ''
        this.infoModal.content = ''
      },
 }
})
</script>
