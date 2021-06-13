<template>
    <main class="my-form">
       <div class="cotainer">
    <b-modal
        :id="errorModal.id"
        :title="errorModal.title"
        ok-only
        @ended="errModal"
      >
        <pre>{{ errorModal.content }}</pre>
      </b-modal>
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
      username:"",
      rolepath:"",
      errorModal: {
        id: "error-modal",
        title: "",
        content: "",
      },
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
     errModal() {
      this.errorModal.title = "";
      this.errorModal.content = "";
    },
        onSubmit(e){
            e.preventDefault();
            var self = this;
             if(!self.oldone|| !self.password || !self.password_confirmation ){
                self.showSuccessAlert = true;
                self.errorModal.content = "You must enter all data.";
                self.$root.$emit("bv::show::modal", self.errorModal.id);
             }
             if(self.password !=self.password_confirmation){
                self.showSuccessAlert = true;
                self.errorModal.content = "The password confirmation does not match.";
                self.$root.$emit("bv::show::modal", self.errorModal.id);
              }else{
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
          self.showSuccessAlert = true;
          self.errorModal.content = "Successfully changed password!";
          self.$root.$emit("bv::show::modal", self.errorModal.id);
            
        }).catch(function(error){
          console.log(error);
          self.showSuccessAlert = true;
          self.errorModal.content = "Error! You enter wrong old password!";
          self.$root.$emit("bv::show::modal", self.errorModal.id);
            
        });
              }      
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