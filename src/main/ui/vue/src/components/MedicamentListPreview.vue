<template>
  <div id="MedicamentsListPreview" class="div">
    <div class="row justify-content-center">
      <b-nav-form>
          <input size="sm" class="mr-sm-2 search" placeholder="Search" v-model="search">
          <b-button size="sm" class="my-2 my-sm-0 button" type="submit" >Search</b-button>
    </b-nav-form>
    </div>
    <div v-for="p in this.medicaments" :key="p.id">
      <div class="row justify-content-center">
        <MedicamentPreview :medicament="p"> </MedicamentPreview>
      </div>
    </div>
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
    };
  },

  mounted() {
    this.axios
      .get(`http://localhost:8080/api/medicaments/all`)
      .then((response) => {
        this.medicaments = response.data;
        console.log(this.medicaments);
      })
      .catch((error) => console.log(error));
  },
});
</script>

<style scoped>
.row{
  margin-bottom: 1rem;
}
.search{
  border-color: black;
  border-radius: 5%;
}
</style>