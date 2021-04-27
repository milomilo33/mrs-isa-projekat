<template>
  <div id="MedicamentsListPreview" class="div">
  <section id="sidebar">
    <p class="colorIt">Search medicaments by its name</p>
    <input size="sm" class="mr-sm-2 search" placeholder="Search" v-model="search">
    <b-button size="sm" class="my-2 my-sm-0 button" type="submit" >Search</b-button>
    <hr>
    <p class="colorIt"><b>Apply Filter:</b></p>
  </section>
  <section id="products">
    <div class="row row-cols-2 row-cols-md-3 g-2" >
      <div class="col-lg-4 col-md-6 col-sm-10 offset-md-0 offset-sm-1" v-for="p in this.medicaments" :key="p.id">
        <div >
          <MedicamentPreview :medicament="p"> </MedicamentPreview>
        </div>
      </div>  
    </div>
  </section>
  </div>
</template>

<script>
import { defineComponent } from "@vue/composition-api";
import MedicamentPreview from "../components/MedicamentPreview";

export default defineComponent({
  name: "MedicamentListPreview",
  components: {
    MedicamentPreview,
  },

  data() {
    return {
      medicaments: [],
      search :'',
    };
  },

  mounted() {
    this.axios
      .get(`/api/medicaments/all`,{
          headers: {Authorization: "Bearer " + localStorage.getItem('token')}
        })
      .then((response) => {
        this.medicaments = response.data;
        //console.log(this.medicaments);
      })
      .catch((error) => console.log(error));
  },
});
</script>

<style scoped>


.search{
  border-color: black;
  border-radius: 5%;
}
#sidebar {
    width: 25%;
    float: left;
}
#products {
    width: 100%;
    padding-left: 30px;
}
.colorIt{
  color: #009933;
}
</style>