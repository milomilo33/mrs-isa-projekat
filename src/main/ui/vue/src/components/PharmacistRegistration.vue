<template>
    <main class="my-form">
       <div class="cotainer">
           <div class="row justify-content-center">
               <div class="col-md-6">
                       <div class="card">
                           <div class="card-header">Register Pharmacist</div>
                           <div class="card-body">
                               <form name="myform" @submit="formSubmit">
                                   <div class="form-group row">
                                       <label for="email" class="col-md-4 col-form-label text-md-right">Email</label>
                                       <div class="col-md-6">
                                           <input type="text" id="email" class="form-control" name="email" v-model="email" >
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
                                   <div class="form-group row">
                                       <label for="name" class="col-md-4 col-form-label text-md-right">Name</label>
                                       <div class="col-md-6">
                                           <input type="text" id="name" class="form-control" name="name" v-model="name" >
                                       </div>
                                   </div>
                                    <div class="form-group row">
                                       <label for="lastName" class="col-md-4 col-form-label text-md-right">Last name</label>
                                       <div class="col-md-6">
                                           <input type="text" id="lastName" class="form-control" name="lastName" v-model="lastName" >
                                       </div>
                                   </div>    
                                   <div class="form-group row">
                                        <label for="phoneNumber" class="col-md-4 col-form-label text-md-right">Phone number</label>
                                        <div class="col-md-6">
                                            <input type="text" id="phoneNumber" class="form-control" name="phoneNumber" v-model="phoneNumber" >
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <label for="country" class="col-md-4 col-form-label text-md-right">Country</label>
                                        <div class="col-md-6">
                                            <input type="text" id="country" class="form-control" name="country" v-model="country" >
                                        </div>
                                    </div>
    
                                    <div class="form-group row">
                                        <label for="city" class="col-md-4 col-form-label text-md-right">City</label>
                                        <div class="col-md-6">
                                            <input type="text" id="city" class="form-control" name="city" v-model="city" >
                                        </div>
                                    </div>
    
                                    <div class="form-group row">
                                        <label for="street" class="col-md-4 col-form-label text-md-right">Street</label>
                                        <div class="col-md-6">
                                            <input type="text" id="street" class="form-control" name="street" v-model="street" >
                                        </div>
                                    </div>
    
                                    <div class="form-group row">
                                        <label for="number" class="col-md-4 col-form-label text-md-right">Number</label>
                                        <div class="col-md-6">
                                            <input type="number" min="1" id="number" class="form-control" name="number" v-model="number" >
                                        </div>
                                    </div>
                                     <div class="card1">
                                    <div class="card1-header">Work hours</div>
                                        <b-table 
                                            :items="items" 
                                            :fields="fields"
                                            :head-variant="headVariant" 
                                            responsive="sm" 
                                            bordered
                                            small>
                                        <template v-slot:cell(workhourfrom)="data">
                                            <b-form-timepicker v-model="data.item.workHourFrom"  locale="en"></b-form-timepicker>
                                        </template>
                                         <template v-slot:cell(workhourto)="data">
                                            <b-form-timepicker v-model="data.item.workHourTo"  locale="en"></b-form-timepicker>
                                        </template>        
                                        </b-table>
                                    </div>
                                   <div class="buttons col-md-1 offset-md-4">
                                        <button class="btn btn-success" style="margin:1px;">Submit</button>
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
export default {
  name: "PharmacistRegistration",
  mounted() {
    console.log("Component mounted.");
  },
  data() {
    return {
        fields: [{key:'day', label:'Day'}, {key:'workHourFrom',label:'Work hour from'},{key: 'workHourTo',label:'Work hour to'}],
        items: [
            {day:'Monday'},{day: 'Tuesday'},{day:'Wednesday'},{day:'Thursday'},{day:'Friday'},{day:'Saturday'},{day:'Sunday'}
        ],
      headVariant: "danger",
      email: "",
      password:"",
      password_confirmation:"", 
      name: "",
      lastName: "",
      phoneNumber: "",
      country: "",
      city: "",
      street: "",
      number: 0,
      output: {},
      error_message: "",
      pharmacyId: {},
      output3: {},
      addressId:{},
      pharmacyW :{},
    };
  },
   methods: {
    formSubmit(e) {
      e.preventDefault();
      let errorFound = false;
    
    if(!this.email|| !this.password || !this.phoneNumber || !this.lastName|| !this.name|| !this.password_confirmation || !this.country || !this.city || !this.street){
        errorFound = true;
        this.error_message = "You must enter all data";

      }
      if(this.workHourFrom > this.workHourTo){
          errorFound = true;
          this.error_message = "Not valid work hour.";
      }
      if(this.password !=this.password_confirmation){
        errorFound = true;
        this.error_message = "The password confirmation does not match";
      }
      
      if(isNaN(this.number)){
          errorFound = true;
      }
      if(errorFound == true){
        alert(this.error_message);
      }
      if(errorFound==false){
       this.axios
        .post("http://localhost:8081/api/address", {
          country: this.country,
            city: this.city,
            street: this.street,
            number: this.number,
        })
        .then((response) => {
            
            this.GetAdress(response)
        })
        .catch(function (error) {
           this.error_message = error;
        });
        
      }
    },
    GetAdress(posts){
        var self = this;
        self.axios
            .get("http://localhost:8081/api/address/"+parseInt(posts.data.id))
            .then(function(response){
                self.addressId= response.data;
                self.GetFarmacy(response);

            })
            .catch(function (error){ 
                console.log(error);
            
            });
    },
    GetFarmacy(addressP){
        var self = this;
        self.axios
         .get("http://localhost:8081/api/pharmacy/1")
         .then(function(response) {
             self.pharmacyId = response.data;
             self.GetPharmacist(addressP,response);
        })
         .catch(function (error){ 
             console.log(error);
        });
        
    },
    GetPharmacist(addressP,posts){
        var self = this;
        self.axios.post("http://localhost:8081/api/pharmacist/",{
            email: this.email,
            password:this.password, 
            name: this.name,
            lastName: this.lastName,
            phoneNumber: this.phoneNumber,
            workHourFrom:this.workHourFrom,
            workHourTo:this.workHourTo,
            pharmacy: {
                name: posts.data.name,
                description: posts.data.description,
                id: posts.data.id,
                address: 
                    {
                        country: posts.data.address.country,
                        city: posts.data.address.city,
                        street: posts.data.address.street,
                        number:posts.data.address.number
                    }
            },
            address:{
                country:addressP.data.country,
                id: addressP.data.id,
                city: addressP.data.city,
                street:addressP.data.street,
                number:addressP.data.number
            },
            workHours :[
                {
                    day: this.items[0].day,
                    workHourFrom: this.items[0].workHourFrom,
                    workHourTo: this.items[0].workHourTo,
                    deleted : false,
                    pharmacy: null

                },
                {
                    day: this.items[1].day,
                    workHourFrom: this.items[1].workHourFrom,
                    workHourTo:this.items[1].workHourTo,
                    deleted : false,
                    pharmacy: null

                },
                {
                    day: this.items[2].day,
                    workHourFrom: this.items[2].workHourFrom,
                    workHourTo:this.items[2].workHourTo,
                    deleted : false,
                    pharmacy: null

                },
                {
                    day: this.items[3].day,
                    workHourFrom: this.items[3].workHourFrom,
                    workHourTo:this.items[3].workHourTo,
                    deleted : false,
                    pharmacy: null

                },
                {
                    day: this.items[4].day,
                    workHourFrom: this.items[4].workHourFrom,
                    workHourTo:this.items[4].workHourTo,
                    deleted : false,
                    pharmacy: null

                },
                {
                    day: this.items[5].day,
                    workHourFrom: this.items[5].workHourFrom,
                    workHourTo:this.items[5].workHourTo,
                    deleted : false,
                    pharmacy: null

                },
                {
                    day: this.items[6].day,
                    workHourFrom: this.items[6].workHourFrom,
                    workHourTo:this.items[6].workHourTo,
                    deleted : false,
                    pharmacy: null

                },

            ],
        })
        .then((response) => {
            
            console.log(response);
        })
        .catch(function (error) {
           console.log(error);
        });
    }
    }
};
</script>

<style>
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
    background-color:powderblue;
    text-align: center;
}
.my-form .row
{
   margin-left: 0;
   margin-right: 0;
}
.buttons button{
    display:inline-block;
    width:100px;
    color:white
}
.buttons .btn-cancel{
    background-color: red;
    
}
.my-form .input[type=password] {
  width: 100% !important;
  display: block !important;
}

.my-form .form-control{
    width:200px;
}
</style>