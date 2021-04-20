<template>
    <div id="login">
        <h1>Login</h1>
        <input type="text" name="username" v-model="username" placeholder="Username" />
        <input type="password" name="password" v-model="password" placeholder="Password" />
        <button type="button" v-on:click="login()">Login</button>
    </div>
</template>

<script>
    export default {
        name: 'Login',
        data() {
            return {
                username: "",
                password: "",
            }
        },
        methods: {
            login() {
                if(this.username == "" && this.password == "") {
                    console.log("A username and password must be present");
                }

                this.axios.post(`/api/auth/login`,{
                  username: this.username,
                  password: this.password 
                }).then((response) => {
                    localStorage.setItem("token", response.data.accessToken);
                    this.findUserRole();
                })
                  .catch((error) => {
                    console.log(error);

                  });
                  

                },

            findUserRole(){
                var userRole = JSON.parse(atob(localStorage.getItem('token').split('.')[1])).role;
                if(userRole=="ROLE_PATIENT"){
                  this.$router.push("/PatientPage");
                }
                if(userRole=="ROLE_SYSTEM_ADMIN"){
                  this.$router.push("/SystemAdminPage");
                }
                if(userRole=="ROLE_PHARMACY_ADMIN"){
                  this.$router.push("/PharmacyAdminPage");
                }
                if(userRole=="ROLE_SUPPLIER"){
                  this.$router.push("/SupplierPage");
                }
                if(userRole=="ROLE_PHARMACIST"){
                  this.$router.push("/PharmacistPage");
                }
                if(userRole=="ROLE_DERMATOLOGIST"){
                  this.$router.push("/DermatologistPage");
                }

            }
        }
    }
</script>

<style scoped>
    #login {
        width: 500px;
        border: 1px solid #CCCCCC;
        background-color: #FFFFFF;
        margin: auto;
        margin-top: 200px;
        padding: 20px;
    }
</style>