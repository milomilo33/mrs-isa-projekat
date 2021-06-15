<template>
<div class="justify-content-center login">
  <b-alert v-model="showSuccessAlert" dismissible fade variant="danger">
      Bad credentials.
    </b-alert>
  <b-card title="Login">
    <b-form>
      <b-form-input
        id="input-1"
        v-model="username"
        placeholder="Username"
        required
      >
      </b-form-input>

      <b-form-input
        id="input-2"
        v-model="password"
        placeholder="Password"
        required
        type="password"
      >
      </b-form-input>
      <div class="mt-2">
        <b-button variant="primary" type="button" v-on:click="login()"
          >Login</b-button
        >
      </div>
    </b-form>
  </b-card>
</div>
</template>

<script>
export default {
  name: "Login",
  data() {
    return {
      username: "",
      password: "",
      showSuccessAlert: false,
    };
  },

  methods: {
    login() {
      var _this = this;
      if (this.username == "" && this.password == "") {
        console.log("A username and password must be present");
      }
      this.axios
        .post(process.env.VUE_APP_API_URL + "/auth/login", {
          username: this.username,
          password: this.password,
        })
        .then((response) => {
          localStorage.setItem("token", response.data.accessToken);
          this.findUserRole();
        })
          .catch((error) => {
          console.log(error);
          _this.showSuccessAlert = true;
        });
    },

    findUserRole() {
      var userRole = JSON.parse(
        atob(localStorage.getItem("token").split(".")[1])
      ).role;
      var activeUser = JSON.parse(
        atob(localStorage.getItem("token").split(".")[1])
      ).active;
      if (userRole == "ROLE_PATIENT" && activeUser==false) {
       this.$router.push("PatientPage/ChangePassword");
      }else if(userRole == "ROLE_PATIENT" && activeUser==true){
           this.$router.push("/PatientPage");
      }
      if (userRole == "ROLE_SYSTEM_ADMIN" && activeUser == false) {
        this.$router.push("SystemAdminPage/ChangePassword");
      }else if(userRole == "ROLE_SYSTEM_ADMIN" && activeUser == true){
           this.$router.push("/SystemAdminPage");
      }
      if (userRole == "ROLE_PHARMACY_ADMIN"  && activeUser==false) {
        this.$router.push("PharmacyAdminPage/ChangePassword");
      }else if(userRole =="ROLE_PHARMACY_ADMIN"  && activeUser==true){
         this.$router.push("/PharmacyAdminPage");
      }
      if (userRole == "ROLE_SUPPLIER" && activeUser == false) {
        this.$router.push("SupplierPage/ChangePassword");
      }else if(userRole == "ROLE_SUPPLIER" && activeUser == true){
          this.$router.push("/SupplierPage");
      }
      if (userRole == "ROLE_PHARMACIST" && activeUser == false) {
        this.$router.push("PharmacistPage/ChangePassword");
      }else if(userRole == "ROLE_PHARMACIST" && activeUser == true){
           this.$router.push("/PharmacistPage");
      }
      if (userRole == "ROLE_DERMATOLOGIST" && activeUser == false) {
        this.$router.push("DermatologistPage/ChangePassword");
      }else if(userRole == "ROLE_DERMATOLOGIST" && activeUser == true){
        this.$router.push("/DermatologistPage");
      }
    },
  },
}
</script>

<style scoped>
.login {
  max-width: 40rem;
  background-color: #ffffff;
  margin: auto;
  margin-top: 100px;
  margin-bottom: 200px;
  padding: 20px;
}
</style>