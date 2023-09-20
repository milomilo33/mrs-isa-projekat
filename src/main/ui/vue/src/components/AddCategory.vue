<template>
  <div class="container">
    <b-alert v-model="showDismissibleAlert" dismissible fade variant="danger">
      Category registration failed!
    </b-alert>
    <b-alert v-model="showSuccessAlert" dismissible fade variant="success">
      Success! You registered a new category.
    </b-alert>
    <div class="row justify-content-center">
      <div class="card">
        <div class="card-header">Register Category</div>
        <div class="card-body">
          <form @submit="formSubmit">
            <div class="form-row">
              <div class="form-group col-md-12">
                <label name="category">Category</label>
                <input
                  type="text"
                  class="form-control"
                  v-model="category"
                  required
                />
              </div>
            </div>
            <div class="form-row">
              <div class="form-group col-md-12">
                <label name="threshold">Threshold</label>
                <input
                  type="number"
                  class="form-control"
                  v-model="threshold"
                  required
                />
              </div>
            </div>
            <div class="form-row">
              <div class="form-group col-md-12">
                <label name="discount">Discount</label>
                <input
                  type="number"
                  class="form-control"
                  v-model="discount"
                  required
                />
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

export default {
  data() {
    return {
      showDismissibleAlert: false,
      showSuccessAlert: false,
      id: "",
      category: "",
      threshold: 0,
      discount: 0,
    };
  },
  mounted() {
  },
  methods: {
    formSubmit(e) {
      var _this = this;
      var error_found = false;

      e.preventDefault();

      if (isNaN(this.id)) {
        error_found = true;
      }
      if(this.threshold < 0){
        error_found = true;
      }

      if (error_found == false) {
        this.axios
          .post(
           process.env.VUE_APP_API_URL+`/loyaltyProgram/add`,
            {
              category: this.category,
              threshold: this.threshold,
              discount: this.discount,
            },
            {
              headers: {
                Authorization: "Bearer " + localStorage.getItem('token'),
              },
            }
          )
          .then(function (response) {
            if (response.data != null) {
              _this.showSuccessAlert = true;
            }
          })
          .catch(function (error) {
            console.log(error);
            _this.showDismissibleAlert = true;
          });
      } else {
        _this.showDismissibleAlert = true;
      }
    },
  },
  name: "AddCategory",
};
</script>
<style scoped>
.card-header {
  background-color: #ccffbc;
}
</style>
