<template>

<b-container fluid>
   <b-alert v-model="showSuccessAlert" dismissible fade variant="success">
        Success! You deleted user.
      </b-alert>
      <b-alert v-model="showFailedAlert" dismissible fade variant="danger">
        {{message}}
      </b-alert>
    <!-- User Interface controls -->
    <b-row>
      <b-col lg="4" class="my-3">
        <b-form-group
          label="Filter"
          label-for="filter-input"
          label-cols-sm="2"
          label-align-sm="left"
          label-size="sm"
          class="mb-0"
        >
          <b-input-group size="sm">
            <b-form-input
              id="filter-input"
              v-model="filter"
              type="search"
              placeholder="Type to Search"
            ></b-form-input>

            <b-input-group-append>
              <b-button :disabled="!filter" @click="filter = ''">Clear</b-button>
            </b-input-group-append>
          </b-input-group>
        </b-form-group>
      </b-col>
    </b-row>

    <!-- Main table element -->
    <b-table
      id="my-table"
      :items="items"
      :fields="fields"
      :filter="filter"
      :filter-included-fields="filterOn"
      :sort-by.sync="sortBy"
      :sort-desc.sync="sortDesc"
      :sort-direction="sortDirection"
      :head-variant="headVariant"
      :small=false
      :fixed=true
      stacked="md"
      show-empty
      striped
      @filtered="onFiltered"
    >
     <template #cell(deleteUser)="row">
        <b-button size="sm" @click="deleteUser(row)" class="mr-2">
            Delete
        </b-button>
      </template>

      
    </b-table>
  </b-container>
</template>
<script>
import { defineComponent } from '@vue/composition-api'

export default defineComponent({
 data(){
   return{
      items: [],
      fields: [{ key: 'name', label: "Name", sortable: true},
              { key: 'lastName', label: "Lastname", sortable: true},
              { key: 'email', label: "Email", sortable: true},
              { key: 'number', label: "Phone number", sortable: true},
              { key: 'role', label: "Role", sortable: true},
              { key: 'deleteUser', label: "Delete"} ],
      totalRows: 1,
      currentPage: 1,
      perPage: 5,
      sortBy: '',
      sortDesc: false,
      sortDirection: 'asc',
      filter: null,
      filterOn: [],
      deleted: null,
      admin: "",
      showSuccessAlert: false,
      showFailedAlert: false,
      headVariant: "dark",
      message: "",
   }
 },
 
 computed: {
      sortOptions() {
        // Create an options list from our fields
        return this.fields
          .filter(f => f.sortable)
          .map(f => {
            return { text: f.label, value: f.key }
          })
      }
    },
  mounted(){
    this.admin = JSON.parse(atob(localStorage.getItem("token").split(".")[1])).sub;

    var _this = this;
    this.axios
      .get(process.env.VUE_APP_API_URL+`/users/getUsers/`+this.admin, {
        headers: {
          Authorization: "Bearer " + localStorage.getItem("token"),
        },
      })
      .then(function (response) {
        _this.items = response.data;
      })
      .catch(function (error) {
        console.log(error);
      });
      this.totalRows = this.items.length;
  } , 
  methods: {
      deleteUser(row){
        console.log(row.item.email);
        var _this = this;
        this.axios
      .get(process.env.VUE_APP_API_URL+`/users/deleteUser/`+row.item.email, {
        headers: {
          Authorization: "Bearer " + localStorage.getItem("token"),
        },
      })
      .then(function (response) {
        _this.deleted = response.data;
        _this.showFailedAlert = false;
        _this.showSuccessAlert = true;
         const idx = _this.items.indexOf(row.item);
         console.log(idx);
        _this.items.splice(idx, 1);
      })
      .catch(function (error) {
        if (error.response.status == 403){
          _this.message = "This dermatologist cannot be deleted";
        }
        else{
          _this.message = "Failed to delete user.";
        }
        _this.showFailedAlert = true;
      });
      },
      onFiltered(filteredItems) {
        // Trigger pagination to update the number of buttons/pages due to filtering
        this.totalRows = filteredItems.length
        this.currentPage = 1
      }
    }
})
</script>
<style scoped>

</style>
