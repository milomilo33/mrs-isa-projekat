<template>
  <div class="cotainer">
    <b-alert v-model="showSuccessAlert" dismissible fade variant="success">
      Success! You registered a new patient '{{this.email}}'.
    </b-alert>
    <div class="row justify-content-center">
      <div class="col-md-8">
        <div class="card">
          <div class="card-header">Register Patient</div>
          <div class="card-body">
            <form name="my-form" @submit="formSubmit">
              <div class="form-group row">
                <label for="name" class="col-md-4 col-form-label text-md-right"
                  >First Name</label
                >
                <div class="col-md-4">
                  <input
                    type="text"
                    id="name"
                    class="form-control"
                    name="name"
                    v-model="name"
                  />
                </div>
              </div>

              <div class="form-group row">
                <label
                  for="lastName"
                  class="col-md-4 col-form-label text-md-right"
                  >Last Name</label
                >
                <div class="col-md-4">
                  <input
                    type="text"
                    id="lastName"
                    class="form-control"
                    name="lastName"
                    v-model="lastName"
                  />
                </div>
              </div>
              <div class="form-group row">
                <label
                  for="phoneNumber"
                  class="col-md-4 col-form-label text-md-right"
                  >Phone Number</label
                >
                <div class="col-md-4">
                  <input
                    type="text"
                    id="phoneNumber"
                    class="form-control"
                    name="phoneNumber"
                    v-model="phoneNumber"
                  />
                </div>
              </div>
              <div class="form-group row">
                <label for="email" class="col-md-4 col-form-label text-md-right"
                  >Email</label
                >
                <div class="col-md-4">
                  <input
                    type="text"
                    id="email"
                    class="form-control"
                    name="email"
                    v-model="email"
                  />
                </div>
              </div>

              <div class="form-group row">
                <label
                  for="password1"
                  class="col-md-4 col-form-label text-md-right"
                  >Password</label
                >
                <div class="col-md-4">
                  <input
                    type="password"
                    id="password1"
                    class="form-control"
                    name="password1"
                    v-model="password1"
                  />
                </div>
              </div>
              <div class="form-group row">
                <label
                  for="password2"
                  class="col-md-4 col-form-label text-md-right"
                  >Password</label
                >
                <div class="col-md-4">
                  <input
                    type="password"
                    id="password2"
                    class="form-control"
                    name="password2"
                    v-model="password2"
                  />
                </div>
              </div>

              <div class="form-group row">
                <label
                  for="country"
                  class="col-md-4 col-form-label text-md-right"
                  >Country</label
                >
                <div class="col-md-4">
                  <input
                    type="text"
                    id="country"
                    class="form-control"
                    name="country"
                    v-model="country"
                  />
                </div>
              </div>

              <div class="form-group row">
                <label for="city" class="col-md-4 col-form-label text-md-right"
                  >City</label
                >
                <div class="col-md-4">
                  <input
                    type="text"
                    id="city"
                    class="form-control"
                    name="city"
                    v-model="city"
                  />
                </div>
              </div>

              <div class="form-group row">
                <label
                  for="street"
                  class="col-md-4 col-form-label text-md-right"
                  >Street</label
                >
                <div class="col-md-4">
                  <input
                    type="text"
                    id="street"
                    class="form-control"
                    name="street"
                    v-model="street"
                  />
                </div>
              </div>

              <div class="form-group row">
                <label
                  for="number"
                  class="col-md-4 col-form-label text-md-right"
                  >Number</label
                >
                <div class="col-md-4">
                  <input
                    type="number"
                    min="1"
                    id="number"
                    class="form-control"
                    name="number"
                    v-model="number"
                  />
                </div>
              </div>
              <div class="col-md-6 offset-md-4">
                <p class="show_error">{{ error_message }}</p>
              </div>
              <div class="col-md-4 offset-md-4">
                <button class="btn btn-success">Submit</button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "UserRegistration",
  mounted() {
    console.log("Component mounted.");
  },
  data() {
    return {
      name: "",
      lastName: "",
      country: "",
      city: "",
      street: "",
      email: "",
      password1: "",
      password2: "",
      phoneNumber: "",
      number: 0,
      output: "",
      error_message: "",
      showSuccessAlert: false,
    };
  },
  methods: {
    formSubmit(e) {
      e.preventDefault();
      let currentObj = this;
      let errorFound = false;
      let _this = this;
      if (
        !this.name ||
        isNaN(this.number) ||
        !this.lastName ||
        !this.email ||
        !this.password1 ||
        !this.password2 ||
        !this.country ||
        !this.city ||
        !this.street
      ) {
        errorFound = true;
        this.error_message = "Enter valid data!";
      }
      if (this.password2 !== this.password1) {
        errorFound = true;
        this.error_message = "Passwords don't match!";
      }
     
      if (errorFound == false) {
        this.axios
          .post(process.env.VUE_APP_API_URL+`/patients`, {
            name: this.name,
            lastName: this.lastName,
            email: this.email,
            phoneNumber: this.phoneNumber,
            password: this.password2,
            address: {
              country: this.country,
              city: this.city,
              street: this.street,
              number: this.number,
            },
          }, {
        headers: {
          Authorization: "Bearer " + localStorage.getItem('token')
        }})
          .then(function (response) {
            currentObj.output = response.data;
             _this.showSuccessAlert = true;
             this.$router.push("/Login");
          })
          .catch(function (error) {
            currentObj.output = error;
            console.log(error);
          });
        this.success_message = "Succes. You have been registered.";
      }
    },
  },
};
</script>
<style scoped>
body {
  margin: 0;
  font-size: 0.9rem;
  font-weight: 400;
  line-height: 1.6;
  color: #212529;
  text-align: left;
  background-color: #f5f8fa;
}

.card-header {
  background-color: #ccffbc;
}

.show_error {
  color: red;
  text-align: center;
}
.show_success {
  color: #009933;
  text-align: center;
}
</style>