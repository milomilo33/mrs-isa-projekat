<template>
  <main class="my-form">
    <div class="cotainer">
      <div class="row justify-content-center">
        <div class="col-md-8">
          <div class="card">
            <div class="card-header">Pharmacists</div>
            <table>
              <tr>
                <th>Name</th>
                <th>Last name</th>
                <th>Email</th>
                <th>Phone number</th>
              </tr>

              <tr v-for="p in pharmacists" :key="p.Id">
                <td>{{ p.name }}</td>
                <td>{{ p.lastName }}</td>
                <td>{{ p.email }}</td>
                <td>{{ p.phoneNumber }}</td>
                <td><button v-on:click="deletePharmacist(p)">Delete</button></td>
              </tr>
            </table>
          </div>
        </div>
      </div>
    </div>
  </main>
</template>

<script>
export default {
  props: ['value']
  ,
  data() {
    return {
      fields: ["Name", "First name", "Email", "Phone number", ""],
      pharmacists: [],
      selectMode: "single"
    };
  },
  mounted() {
    var self = this;
    self.axios
      .get("http://localhost:8081/api/pharmacist/all")
      .then(function (response) {
        console.log(response.data);
        self.pharmacists = response.data;
      });
  },
  methods: {
    deletePharmacist(toDelete){
      console.log(toDelete);
      this.axios.delete("http://localhost:8081/api/pharmacist/"+toDelete.email)
      .then(function(response){
         const idx = this.pharmacists.indexOf(toDelete);
         this.pharmacists.splice(idx,1);

        console.log(response);
      });
    }
  },
};
</script>
<style>
my-form {
  padding-top: 0rem;
  padding-bottom: 1rem;
}
.my-form .card-header {
  background-color: powderblue;
  text-align: center;
}
</style>