<template>
    <main class="my-form">
       <div class="cotainer">
           <b-alert v-model="showSuccessAlert" dismissible fade variant="success">
      Success!
    </b-alert>
           <div class="row justify-content-center">
               <div class="col-md-6">
                       <div class="card">
                           <div class="card-header">Personal informations</div>
                           <div class="card-body">
                               <form name="myform" @submit="onSubmit">
                                   <div class="form-group row">
                                       <label for="email" class="col-md-4 col-form-label text-md-right">Email</label>
                                       <div class="col-md-6">
                                           <input type="text" id="email" class="form-control" name="email"  v-model="email" disabled=true >
                                       </div>
                                   </div>
                                   <div class="form-group row">
                                       <label for="name" class="col-md-4 col-form-label text-md-right">Name</label>
                                       <div class="col-md-6">
                                           <input type="text" id="name" class="form-control" name="name"  v-model="name" :disabled="d">
                                       </div>
                                   </div>
                                    <div class="form-group row">
                                       <label for="lastName" class="col-md-4 col-form-label text-md-right">Last name</label>
                                       <div class="col-md-6">
                                           <input type="text" id="lastName" class="form-control" name="lastName"  v-model="lastName" :disabled="d">
                                       </div>
                                   </div>    
                                   <div class="form-group row">
                                        <label for="phoneNumber" class="col-md-4 col-form-label text-md-right">Phone number</label>
                                        <div class="col-md-6">
                                            <input type="text" id="phoneNumber" class="form-control" name="phoneNumber"  v-model="phoneNumber" :disabled="d">
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <label for="country" class="col-md-4 col-form-label text-md-right">Country</label>
                                        <div class="col-md-6">
                                            <input type="text" id="country" class="form-control" name="country"  v-model="country" :disabled="d" >
                                        </div>
                                    </div>
    
                                    <div class="form-group row">
                                        <label for="city" class="col-md-4 col-form-label text-md-right">City</label>
                                        <div class="col-md-6">
                                            <input type="text" id="city" class="form-control" name="city"  v-model="city" :disabled="d" >
                                        </div>
                                    </div>
    
                                    <div class="form-group row">
                                        <label for="street" class="col-md-4 col-form-label text-md-right">Street</label>
                                        <div class="col-md-6">
                                            <input type="text" id="street" class="form-control" name="street"  v-model="street" :disabled="d">
                                        </div>
                                    </div>
    
                                    <div class="form-group row">
                                        <label for="number" class="col-md-4 col-form-label text-md-right">Number</label>
                                        <div class="col-md-6">
                                            <input type="number" min="1" id="number" class="form-control" name="number"  v-model="number" :disabled="d">
                                        </div>
                                    </div>
                                    <div class="buttons form-group row">
                                      <button class="col-md-2 btn btn-success">Submit</button>
                                    </div>
                               </form>
                               <div class="buttonsss form-group row">
                                    <button class="col-md-2 btn btn-success" v-on:click="onEdit">Edit</button> 
                                </div>
                               
                           </div>
                       </div>
               </div>
           </div>
       </div>
   </main>   
</template>

<script>
import { defineComponent } from '@vue/composition-api'
export default defineComponent({
  name: "Profile",
  components: {},
   data() {
    return {
      email:"",
      password:"",
      password_confirmation:"", 
      name: "",
      lastName: "",
      phoneNumber: "",
      country: "",
      city: "",
      street:"",
      number:"",
      d: true,
      user: {},
      role:"",
      pharmacyId:0,
      showSuccessAlert:false,
    };
  },
  mounted(){
    var userRole = JSON.parse(
        atob(localStorage.getItem("token").split(".")[1])
      ).role;
    if(userRole=="ROLE_PHARMACY_ADMIN"){
         var AdminUsername = JSON.parse(
      atob(localStorage.getItem('token').split(".")[1])
    ).sub;
    var self = this;
    self.role = userRole;
    self.axios
      .get(`/api/pharmacyAdmin/` + AdminUsername, {
        headers: {
          Authorization: "Bearer " + localStorage.getItem('token'),
        },
      })
      .then(function (response) {
        self.email = response.data.email;
        self.name = response.data.name;
        self.lastName = response.data.lastName;
        self.country = response.data.address.country;
        self.city = response.data.address.city;
        self.street = response.data.address.street;
        self.phoneNumber = response.data.phoneNumber;
        self.number = response.data.address.number;
        self.pharmacyId = response.data.pharmacy.id;
        self.ret = true;
      })
      .catch(function (error) {
        console.log(error);
      });
    }
    
  },
  methods:{
        onEdit(){
            this.d = false;
        },
        onSubmit(e){
            e.preventDefault();
            var self = this;
            if(self.role == "ROLE_PHARMACY_ADMIN"){
                self.axios.put(`/api/pharmacyAdmin/updatePharmacyAdmin/` +
                self.email,
          {
            email: self.email,
            pharmacy: 
              {
                id: self.pharmacyId,
              },
            phoneNumber: self.phoneNumber,
            lastName: self.lastName,
            name: self.name,
            address:{
                country: self.country,
                city:self.city,
                street: self.street,
                number:self.number
            }
          },
          {
            headers: {
              Authorization: "Bearer " + localStorage.getItem('token'),
            },
          }).then(function(response){
              console.log(response);
              self.showSuccessAlert =true;
          });
      }
            }
           
  },
  
});
</script>

<style scoped>
body{
   margin: 0;
   font-size: .9rem;
   font-weight: 400;
   line-height: 1.6;
   color: #212529;
   text-align: left;
   background-color: #f5f8fa;
}

.my-form
{
   padding-top: 0rem;
   padding-bottom: 1rem;
}
.my-form .card-header
{
    background-color:#ccffbc;
    text-align: center;
}
.my-form .row
{
   margin-left: 0;
   margin-right: 0;
}
.buttons button{
    width:50px;
    color:white;
    margin:auto;
}

.my-form .form-control{
    width:200px;
}
</style>