<template>
    <b-div>
        <b-alert v-model="showSuccessAlert" dismissible fade variant="success">
            You have succesfully updated your profile.
        </b-alert>
        <b-alert v-model="showErrorAlert" dismissible fade variant="danger">
            Invalid input data!
        </b-alert>
        <b-row>
            <b-col class="col-lg-4">
                <img src="../patient.jpg" alt="">
            </b-col>
            <b-col class="text-left col-lg-8">
                <b-row class="mb-3">
                    <b-col class="col-lg-2 label"> Email: </b-col>
                    <b-col> 
                        {{patient.email}}
                    </b-col> 
                </b-row>
                <b-row class="mb-3">
                    <b-col class="col-lg-2 mt-2 label"> First Name: </b-col>
                    <b-col> 
                        <b-input class="col-lg-7" v-model="patient.name" ></b-input>
                    </b-col>
                </b-row>
                <b-row class="mb-3">
                    <b-col class="col-lg-2 mt-2 label"> Last Name: </b-col>  
                    <b-col> 
                        <b-input class="col-lg-7" v-model="patient.lastName"></b-input>    
                    </b-col>  
                </b-row>
                <b-row class="mb-3">
                    <b-col class="col-lg-2 mt-2 label"> Phone number: </b-col>
                    <b-col> 
                        <b-input class="col-lg-7" v-model="patient.phoneNumber"></b-input>    
                    </b-col> 
                </b-row>
                <b-row class="mb-3 mt-5"> 
                    <b-col class="col-lg-2 mt-2 label"> Country: </b-col>
                    <b-col> 
                        <b-input class="col-lg-7" v-model="patient.address.country"></b-input>    
                    </b-col> 
                </b-row>
                <b-row class="mb-3"> 
                    <b-col class="col-lg-2 mt-2 label"> City: </b-col>
                    <b-col> 
                        <b-input class="col-lg-7" v-model="patient.address.city"></b-input>    
                    </b-col> 
                </b-row>
                <b-row class="mb-3"> 
                    <b-col class="col-lg-2 mt-2 label"> Street: </b-col>
                    <b-col> 
                        <b-input class="col-lg-7" v-model="patient.address.street"></b-input>    
                    </b-col> 
                </b-row>
                <b-row class="mb-3"> 
                    <b-col class="col-lg-2 mt-2 label"> Number: </b-col>
                    <b-col> 
                        <b-input type="number" class="col-lg-2" v-model="patient.address.number"></b-input>    
                    </b-col> 
                </b-row>
                <br>
                <b-row class="mb-3 mt-2" v-if="type === 'patient'">
                   <b-col class="col-lg-2 label"> Category: </b-col>
                   <b-col class="ml-3 text-center" 
                   v-bind:class="{ bronze: loyalty.category === 'REGULAR', silver: loyalty.category === 'SILVER', gold: loyalty.category === 'GOLD'}"> 
                       {{loyalty.category}} </b-col>
                </b-row>
                <b-row class="mb-3" v-if="type === 'patient'">
                    <b-col class="col-lg-2 label"> Loyalty points: </b-col>
                    <b-col> {{loyalty.points}} </b-col>
                </b-row>
                <b-row>
                    <b-col>
                        
                    </b-col>
                    <b-col class="mb-5"><b-button variant="success" @click="saveChanges()"> Save changes </b-button></b-col>
                </b-row>
            </b-col>
        </b-row>
    </b-div>
</template>

<script>
export default {
    data() {
        return {
            loyalty: null,
            patient: null,
            username: "",
            address: "",
            showSuccessAlert: false,
            showErrorAlert: false,
            type: "patient"
        }
    },

    mounted() {
        this.username = JSON.parse(atob(localStorage.getItem("token").split(".")[1])).sub;

        this.loadPatient();
        if (this.type === "patient")
            this.loadLoyalty();
    },

    created() {
        if (this.$route.query.type)
            this.type = this.$route.query.type;

        console.log(this.type);
    },

    methods: {
        loadPatient() {
            let targetApi = this.type === "patient" ? "patients" : this.type;
            this.axios.get(`http://localhost:8080/api/${targetApi}/${this.username}`, {
                    headers: {
                        Authorization: "Bearer " + localStorage.getItem("token"),
                    },
                })
                .then((response) => {
                    this.patient = response.data   
                    this.address = this.addressToString(this.patient.address);
                }).catch((err) => {
                    console.log(err)
                });
        },

        loadLoyalty() {
            this.axios.get(`http://localhost:8080/api/patients/loyalty/${this.username}`)
                .then((response) => {
                    this.loyalty = response.data  
                    this.loyalty.category 
                }).catch((err) => {
                    console.log(err)
                });
        },

        addressToString(address) {
            return address.street + " " + address.number 
                + ", " + address.city + " " + address.country;
        },

        saveChanges() {
            let valid = true;
            if (!this.patient.name.trim())
                valid = false;
            if (!this.patient.lastName.trim())
                valid = false;
            if (!this.patient.phoneNumber.toString().trim())
                valid = false;
            if (!this.patient.address.country.trim())
                valid = false;
            if (!this.patient.address.city.trim())
                valid = false;
            if (!this.patient.address.street.trim())
                valid = false;
            if (!this.patient.address.number.toString().trim())
                valid = false;
            if (!valid) {
                this.showErrorAlert = true;
                return;
            }

            this.showErrorAlert = false;
            this.showSuccessAlert = false;
            if (this.type === "patient")
                alert(this.addressToString(this.patient.address))

            let targetApi = this.type === "patient" ? "patients" : this.type + "/update";
            this.axios.post(`http://localhost:8080/api/${targetApi}`, this.patient, {
                    headers: {
                        Authorization: "Bearer " + localStorage.getItem("token"),
                    },
                })
                .then(() => this.showSuccessAlert = true)
                .catch(error => console.log(error.data));
        }
    }
}
</script>

<style scoped>
    img {
        max-width: 50%;
        border: lightgray solid 2px;
    }

    .bronze{
        background-color: #ff6666;
        color: white;
        border-radius: 5rem;
        max-width: 150px;
    }
    .silver{
        background-color: #ff9999;
        color: white;
        border-radius: 5rem;
        max-width: 150px;

    }
    .gold{
        background-color: #ff4d4d;
        color: white;
        border-radius: 5rem;
        max-width: 150px;
    }

    .label {
        color: rgb(0, 148, 0);
        font-weight: bold;

    }
</style>