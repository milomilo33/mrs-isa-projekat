<template>
  <div>
    <b-container fluid>
      <b-alert v-model="showSuccessAlert" dismissible fade variant="success">
        Success! {{message}}
      </b-alert>
      <b-alert v-model="showFailedAlert" dismissible fade variant="danger">
        Failed to {{message}}.
      </b-alert>
      <b-alert v-model="showSuccessAlertDelete" dismissible fade variant="success">
        Success! You deleted medicament.
      </b-alert>
      <b-alert v-model="showFailedAlertDelete" dismissible fade variant="danger">
        Failed to delete medicament
      </b-alert>
      <!-- Main table element -->
      <b-table
        :items="items"
        :fields="fields"
        :sort-by.sync="sortBy"
        :sort-desc.sync="sortDesc"
        :sort-direction="sortDirection"
        :head-variant="headVariant"
        :small="false"
        :fixed="true"
        stacked="md"
        show-empty
        striped
      >
        <template #cell(deleteMedicament)="row">
          <b-button size="sm" @click="deleteMedicament(row)" class="mr-2">
            Delete
          </b-button>
        </template>
        <template #cell(updateMedicement)="row">
          <b-button
            size="sm"
            @click="info(row.item, row.index, $event.target)"
            class="mr-1"
          >
            Change
          </b-button>
        </template>
      </b-table>
      <b-modal
        :id="infoModal.id"
        :title="infoModal.title"
        ok-only
        @hide="resetInfoModal"
      >
        <div>
            <div class="form-row">
              <div class="from-group col-md-4">
                <label name="id">Id</label>
                <input type="text" class="form-control" v-model="updated.id" required />
              </div>
              <div class="form-group col-md-4">
                <label name="name">Name</label>
                <input
                  type="text"
                  class="form-control"
                  v-model="updated.name"
                  required
                />
              </div>
              <div class="form-group col-md-4">
                <label name="type">Type</label>
                <input
                  type="text"
                  class="form-control"
                  v-model="updated.type"
                  required
                />
              </div>
            </div>
            <div class="form-row">
              <div class="form-group col-md-4">
                <label name="manufacturer">Manufacturer</label>
                <input
                  type="text"
                  class="form-control"
                  v-model="updated.manufacturer"
                  required
                />
              </div>
              <div class="form-group col-md-4">
                <label name="medicamentForm">MedicamentForm</label>
                <select class="custom-select" v-model="updated.form" required>
                  <option value="POWDER">Powder</option>
                  <option value="CAPSULE">Capsule</option>
                  <option value="TABLET">Tablet</option>
                  <option value="OINTMENTS">Ointments</option>
                  <option value="PASTE">Paste</option>
                  <option value="GEL">Gel</option>
                  <option value="LIQUID">Liquid</option>
                  <option value="SYRUP">Syrup</option>
                </select>
              </div>
              <div class="form-group col-md-4">
                <label name="issuanceMode">IssuanceMode</label>
                <select class="custom-select" v-model="updated.mode" required>
                  <option value="WITHOUTPRESCRIPTION">
                    Without Prescription
                  </option>
                  <option value="WITHPRESCRIPTION">With Prescription</option>
                </select>
              </div>
            </div>
            <div class="form-row">
              <div class="form-group col-md-12">
                <label name="Content">Content</label>
                <input
                  type="text"
                  class="form-control"
                  v-model="updated.structure"
                  required
                />
              </div>
            </div>
            <div class="form-row">
              <div class="form-group col-md-12">
                <label name="annotations">Annotations</label>
                <input
                  type="text"
                  class="form-control"
                  v-model="updated.annotation"
                  required
                />
              </div>
            </div>
            <div class="form-row">
              <div class="form-group col-md-12">
                <div>
                  <label class="typo__label"
                    >Choose substitute medicaments</label
                  >
                  <multiselect
                    v-model="substitute"
                    :options="items"
                    :multiple="true"
                    :close-on-select="false"
                    :clear-on-select="false"
                    :preserve-search="true"
                    :preselect-first="true"
                    track-by="id"
                    label="name"
                  >
                  </multiselect>
                </div>
              </div>
            </div>
            <div class="col-md-12">
              <button class="btn btn-success" @click="updateMed">Edit</button>
            </div>
          </div>
      </b-modal>
    </b-container>
  </div>
</template>
<script>
import { defineComponent } from '@vue/composition-api'
import Multiselect from "vue-multiselect";
export default defineComponent({
   components: {
    Multiselect,
  },
 data(){
   return{
      items: [],
      fields: [{ key: 'id', label: "Id", sortable: true},
              { key: 'name', label: "Name", sortable: true},
              { key: 'deleteMedicament', label: "Delete"},
              { key: 'updateMedicement', label: "Edit"},
             ],
      totalRows: 1,
      currentPage: 1,
      perPage: 5,
      sortBy: '',
      sortDesc: false,
      sortDirection: 'asc',
      deleted: null,
      admin: "",
      id: "",
      name: "",
      type: "",
      manufacturer: "",
      annotations: "",
      medicamentForm: "",
      issuanceMode: "",
      substitute: [],
      content: "",
      structure: "",
      points: 0,
      message: "",
      showSuccessAlert: false,
      showFailedAlert: false,
      showSuccessAlertDelete: false,
      showFailedAlertDelete: false,
      headVariant: "dark",
      infoModal: {
        id: "info-modal",
        title: "",
        content: "",
        element: null,
      },
      updated: {
        name: "",
        structure: "",
        manufacturer: "",
        annotation: "",
        form: "",
        type: "",
        mode: "",
        id: "",
      },
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
      .get(process.env.VUE_APP_API_URL+`/medicaments/all`, {
        headers: {
          Authorization: "Bearer " + localStorage.getItem("token"),
        },
      })
      .then(function (response) {
        _this.items = response.data;
        this.updated = this.items[0];
      })
      .catch(function (error) {
        console.log(error);
      });
      this.totalRows = this.items.length;
      
  } , 
  methods: {
      deleteMedicament(row){
        var _this = this;
        this.axios
      .get(process.env.VUE_APP_API_URL+`/medicaments/deleteMedicament/`+row.item.id, {
        headers: {
          Authorization: "Bearer " + localStorage.getItem("token"),
        },
      })
      .then(function (response) {
        _this.deleted = response.data;
        _this.showFailedAlertDelete = false;
        _this.showSuccessAlertDelete = true;
         const idx = _this.items.indexOf(row.item);
        _this.items.splice(idx, 1);
      })
      .catch(function (error) {
        _this.showFailedAlertDelete = true;
        _this.showSuccessAlertDelete = false;
        console.log(error);
      });
      },
      updateMed(){
        console.log(this.updated);
        var _this = this;
        this.axios
          .post(
            process.env.VUE_APP_API_URL+`/medicaments/updateMedicament`,
            {
              name: this.updated.name,
              id: this.updated.id,
              type: this.updated.type,
              structure: this.updated.structure,
              manufacturer: this.updated.manufacturer,
              issuanceMode: this.updated.mode,
              medicamentForm: this.updated.form,
              annotations: this.updated.annotations,
              substituteMedicaments: this.substitute,
            },
            {
              headers: {
                Authorization: "Bearer " + localStorage.getItem('token'),
              },
            }
          )
          .then(function (response) {
            if (response.data != null) {
              _this.message = "You edited medicament ";
              _this.showSuccessAlert = true;
            }
          })
          .catch(function (error) {
            console.log(error);
            _this.message = "edit medicament ";
            _this.showDismissibleAlert = true;
          });
       
      },
      info(i, button){
        (this.infoModal.title = "Edit medicament"),
        (this.infoModal.element = i),
        this.updated = i; 
      this.$root.$emit("bv::show::modal", this.infoModal.id, button);
      this.updated = i; 
      },
      resetInfoModal() {
      this.infoModal.title = "";
      this.infoModal.content = "";
      }
    }
})
</script>
<style src="vue-multiselect/dist/vue-multiselect.min.css">
</style>
<style scoped>
</style>
