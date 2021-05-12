<template>
    <main class="my-form">
       <div class="cotainer">
           <b-alert v-model="showSuccessAlert" dismissible fade :variant="v">
     {{ message}}
    </b-alert>
           <div class="row justify-content-center">
               <div class="col-md-6">
                       <div class="card">
                           <div class="card-header">Change password
                           </div>
                           <div class="card-body">
                               <form name="myform" @submit="onSubmit">
                                   <div class="form-group row">
                                       <label for="oldone" class="col-md-4 col-form-label text-md-right">Old password</label>
                                       <div class="col-md-6">
                                           <input type="password" id="oldone" class="form-control" name="oldone"  v-model="oldone" >
                                       </div>
                                   </div>
                                    <div class="form-group row">
                                       <label for="password" class="col-md-4 col-form-label text-md-right">Password</label>
                                       <div class="col-md-6">
                                           <input  type="password" id="password" class="form-control" name="password" v-model="password" >
                                       </div>
                                   </div>
                                   <div class="form-group row">
                                       <label for="password_confirmation" class="col-md-4 col-form-label text-md-right">Confirm password</label>
                                       <div class="col-md-6">
                                           <input type="password" id="password_confirmation" class="form-control" name="password_confirmation" v-model="password_confirmation">
                                       </div>
                                   </div>
                                    <div class="buttons form-group row">
                                      <button class="col-md-2 btn btn-success">Submit</button>
                                    </div>
                               </form>
                               
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
  name: "ChangePassword",
  components: {},
   data() {
    return {
      email:"",
      password:"",
      password_confirmation:"", 
      oldone:"",
      d: true,
      user: {},
      role:"",
      showSuccessAlert:false,
      message:"",
      v:"success",
      username:"",
      rolepath:"",
    };
  },
  mounted(){
    this.role = JSON.parse(
        atob(localStorage.getItem("token").split(".")[1])
      ).role;
        this.username = JSON.parse(
      atob(localStorage.getItem('token').split(".")[1])
    ).sub;
    
  },
  methods:{
        onSubmit(e){
            e.preventDefault();
            var self = this;
             if(!self.oldone|| !self.password || !self.password_confirmation ){
                self.v ="warning";
                self.message = "You must enter all data";
                self.showSuccessAlert = true;
             }
             if(self.password !=self.password_confirmation){
                self.showSuccessAlert = true;
                self.v ="warning";
                self.message = "The password confirmation does not match";
              }
              if(self.role == "ROLE_PHARMACY_ADMIN"){
                self.rolepath ="pharmacyAdmin";
            
              }else if(self.role == "ROLE_SUPPLIER"){
                self.rolepath ="supplier";
              }else if(self.role == "ROLE_PHARMACIST"){
                self.rolepath="pharmacist";
              }else if(self.role =="ROLE_DERMATOLOGIST"){
                self.rolepath ="dermatologist";
  
              }else if(self.role == "ROLE_PATIENT"){
                self.rolepath="patients";
 
              }else if(self.role =="ROLE_SYSTEM_ADMIN"){
                self.rolepath ="systemAdmin";
              }


                self.axios.put(`/api/`+self.rolepath+`/changePassword/` +
                self.oldone,
              {
              email: self.username,
              password:self.password,
             },
          {
            headers: {
              Authorization: "Bearer " + localStorage.getItem('token'),
            },
          }
        ).then(function (response){
          console.log(response);
           self.v ="success";
            self.message = "Successfully changed password!";
            self.showSuccessAlert = true;
        }).catch(function(error){
          console.log(error);
            self.v ="danger";
            self.message = "Error! You enter wrong old password!";
            self.showSuccessAlert = true;
        });
           
  }},
  
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