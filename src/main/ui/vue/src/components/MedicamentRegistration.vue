<template>
  <div class="container">
    <b-alert v-model="showDismissibleAlert" dismissible
      fade variant="danger">
      Medicament registration failed!
    </b-alert>
    <b-alert v-model="showSuccessAlert" dismissible
      fade variant="success">
      Success! You registered a new medicament.
    </b-alert>
    <div class="row justify-content-center">
      <div class="card">
        <div class="card-header">Register Medicament</div>
        <div class="card-body">
          <form @submit="formSubmit">
            <div class="form-row">
              <div class="from-group col-md-4">
                <label name="id">Id</label>
                <input type="text" class="form-control" v-model="id" required />
              </div>
              <div class="form-group col-md-4">
                <label name="name">Name</label>
                <input
                  type="text"
                  class="form-control"
                  v-model="name"
                  required
                />
              </div>
              <div class="form-group col-md-4">
                <label name="type">Type</label>
                <input
                  type="text"
                  class="form-control"
                  v-model="type"
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
                  v-model="manufacturer"
                  required
                />
              </div>
              <div class="form-group col-md-4">
                <label name="medicamentForm">MedicamentForm</label>
                <select class="custom-select" v-model="medicamentForm" required>
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
                <select class="custom-select" v-model="issuanceMode" required>
                  <option value="WITHOUTPRESCRIPTION">
                    Without Prescription
                  </option>
                  <option value="WITHPRESCRIPTION">With Prescription</option>
                </select>
              </div>
            </div>
            <div class="form-row">
              <div class="form-group col-md-6">
                <label name="Content">Content</label>
                <input
                  type="text"
                  class="form-control"
                  v-model="structure"
                  required
                />
              </div>
              <div class="form-group col-md-6">
                <label name="annotations">Annotations</label>
                <input
                  type="text"
                  class="form-control"
                  v-model="annotations"
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
                    :options="medicaments"
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
              <button class="btn btn-success">Submit</button>
            </div>
          </form>
        </div>
      </div>
    </div>
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
      showDismissibleAlert: false,
      showSuccessAlert: false,
      medicaments: [],
      id: "",
      name: "",
      type: "",
      manufacturer: "",
      annotations: "",
      medicamentForm: "",
      issuanceMode: "",
      substitute: [],
      content: "",
      structure: ""
    };
  },
  mounted() {
    var _this = this;
    this.axios
      .get("http://localhost:8080/api/medicaments/all")
      .then(function (response) {
        for (var i = 0; i < response.data.length; i++) {
          var item = {};
          item.id = response.data[i].id;
          item.name = response.data[i].name;
          item.type = response.data[i].type;
          item.annotations = response.data[i].annotations;
          item.structure = response.data[i].structure;
          item.manufacturer = response.data[i].manufacturer;
          _this.medicaments.push(item);
        }
      });
  },
  methods: {
   
    formSubmit(e) {
      var _this = this;
      var error_found = false;

      e.preventDefault();

      if(isNaN(this.id)){
        error_found = true;
      }

      if(error_found == false){
        this.axios
        .post("http://localhost:8081/api/medicaments", {
          name: this.name,
          id: this.id,
          type: this.type,
          structure: this.structure,
          manufacturer: this.manufacturer,
          issuanceMode: this.issuanceMode,
          medicamentForm: this.medicamentForm,
          annotation: this.annotations,
          substituteMedicaments: this.substitute
        })
        .then(function (response) {
          if (response.data != null) {
            _this.showSuccessAlert = true;
          }
        })
        .catch(function (error) {
          console.log(error);
          _this.showDismissibleAlert = true;
        });
      }
      else{
        _this.showDismissibleAlert = true;
      }
      
    },
  },
  name: "MedicamentRegistration",
};
</script>
<style src="vue-multiselect/dist/vue-multiselect.min.css">
</style>
<style scoped>
.card-header{
  background-color: #fecdcd;
}
</style>
