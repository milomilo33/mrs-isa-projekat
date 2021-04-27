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
        type="password"
        v-model="password"
        placeholder="Password"
        required
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
        .post("http://localhost:8080/api/auth/login", {
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
      if (userRole == "ROLE_PATIENT") {
        this.$router.push("/PatientPage");
      }
      if (userRole == "ROLE_SYSTEM_ADMIN") {
        this.$router.push("/SystemAdminPage");
      }
      if (userRole == "ROLE_PHARMACY_ADMIN") {
        this.$router.push("/PharmacyAdminPage");
      }
      if (userRole == "ROLE_SUPPLIER") {
        this.$router.push("/SupplierPage");
      }
      if (userRole == "ROLE_PHARMACIST") {
        this.$router.push("/PharmacistPage");
      }
      if (userRole == "ROLE_DERMATOLOGIST") {
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