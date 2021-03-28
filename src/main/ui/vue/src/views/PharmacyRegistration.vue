<template>
    <main class="my-form">
        <div class="cotainer">
            <div class="row justify-content-center">
                <div class="col-md-8">
                        <div class="card">
                            <div class="card-header">Register Pharmacy</div>
                            <div class="card-body">
                                <form name="my-form" @submit="formSubmit">
                                    <div class="form-group row">
                                        <label for="name" class="col-md-4 col-form-label text-md-right">Name</label>
                                        <div class="col-md-6">
                                            <input type="text" id="name" class="form-control" name="name" v-model="name" >
                                        </div>
                                    </div>
    
                                    <div class="form-group row">
                                        <label for="description" class="col-md-4 col-form-label text-md-right">Description</label>
                                        <div class="col-md-6">
                                            <input type="text" id="description" class="form-control" name="description" v-model="description" >
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
                                    <div class = "col-md-6 offset-md-4"><p class="show_error">{{error_message}}</p></div>
                                    <div class="col-md-6 offset-md-4">
                                        <button class="btn btn-success">Submit</button>
                                    </div>
                                    
                                </form>
                                <div class = "col-md-6 offset-md-4"><p class="show_success">{{success_message}}</p></div>
                            </div>
                        </div>
                </div>
            </div>
        </div>
    </main>   
</template>

<script>
export default {
  name: "PharmacyRegistration",
  mounted() {
    console.log("Component mounted.");
  },
  data() {
    return {
      name: "",
      description: "",
      country: "",
      city: "",
      street: "",
      number: 0,
      output: "",
      error_message: "",
      success_message: "",
    };
  },
  methods: {
    formSubmit(e) {
      e.preventDefault();
      let currentObj = this;
      let errorFound = false;

      if(!this.name || isNaN(this.number) || !this.description || !this.country || !this.city || !this.street){
        errorFound = true;
        this.error_message = "Niste uneli validne podatke!";
      }

      if(errorFound==false){
         this.axios
        .post("api/pharmacy", {
          name: this.name,
          description: this.description,
          address: {
            country: this.country,
            city: this.city,
            street: this.street,
            number: this.number,
          },
        })
        .then(function (response) {
          currentObj.output = response.data;
        })
        .catch(function (error) {
          currentObj.output = error;
        });
        this.success_message = "Uspesno ste registrovali apoteku."
      }
    },
  },
};
</script>
<style>
body {
  margin: 0;
  font-size: 0.9rem;
  font-weight: 400;
  line-height: 1.6;
  color: #212529;
  text-align: left;
  background-color: #f5f8fa;
}

.my-form {
  padding-top: 1.5rem;
  padding-bottom: 1.5rem;
}

.my-form .row {
  margin-left: 0;
  margin-right: 0;
}
.show_error{
  color: red;
  text-align: center;
}
.show_success{
  color: #009933;
  text-align: center; 
}
</style>