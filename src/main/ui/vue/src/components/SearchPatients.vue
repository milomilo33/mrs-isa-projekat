<template>
    <div>
        <b-form inline>
            <b-form-input id="name"
                        name="name"
                        placeholder="Name"
                        class="mb-2 mr-sm-2 mb-sm-0"
                        v-model="name">
            </b-form-input>

            <b-form-input id="lastName"
                        name="lastName"
                        placeholder="Last name"
                        class="mb-2 mr-sm-2 mb-sm-0"
                        v-model="lastName">
            </b-form-input>

            <b-button @click="onSubmit" class="mb-2 mr-sm-2 mb-sm-0">Search</b-button>
        </b-form>

        <!-- <div v-for="patient in patients" v-bind:key="patient.email">

        </div> -->
        <br>
        <b-table striped hover :items="patients" :fields="patientFields"></b-table>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                patients: [],
                name: '',
                lastName: '',
                patientFields: [
                    {
                        key: 'name',
                        headerTitle: 'Name'
                    },
                    {
                        key: 'lastName',
                        headerTitle: 'Last Name'
                    },
                    {
                        key: 'phoneNumber',
                        headerTitle: 'Phone Number'
                    }
                ]
            }
        },
        methods: {
            onSubmit(e) {
                e.preventDefault();

                let params = { name: this.name, lastName: this.lastName };
                this.axios.get(`/api/patients/search`, { params ,
                            headers: {Authorization: "Bearer " + localStorage.getItem("token")}
                            })
                          .then(response => {
                                this.patients = response.data;
                                //console.log(this.pharmacy);
                           })
                           .catch(error => {
                              if (error.response.status === 404) {
                                  this.patients = [];
                              }
                              else {
                                console.log(error);
                              }
                           });
            }
        }
    }
</script>
