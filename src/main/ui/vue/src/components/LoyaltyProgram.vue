<template>
 
  <div class="justify-content-center">
    <h3>Loyalty Program</h3>
    <hr />

    <div class="categoriesPart row justify-content-center">
      
      <div class="offset-md-1 offset-sm-0"  v-for="c in this.categories"
        :key="c.id">
        <div>
          <CategoryComponent :category="c"> </CategoryComponent>
        </div>
      </div>
    </div>
    <hr>
    
    <div class="pointsPart row justify-content-center">
      
       <div class="offset-md-1 offset-sm-0"  v-for="p in this.points"
        :key="p.id">
        <div> 
          <PointsComponent :point="p"></PointsComponent>
        </div>
       </div>
    </div>
  </div>
</template>

<script>
import { defineComponent } from "@vue/composition-api";
import CategoryComponent from '../components/CategoryComponent'
import PointsComponent from '../components/PointsComponent'

export default defineComponent({
  components: {
    CategoryComponent,
    PointsComponent
  },
  data() {
    return {
      categories: [],
      points: [],
      point: null,
      category: null,
      discount: 0,
      threshold: 0,
      pointValue: 0,
    };
  },
  mounted() {
    this.axios
      .get(`/api/loyaltyProgram/getCategories`, {
        headers: { Authorization: "Bearer " + localStorage.getItem("token") },
      })
      .then((response) => {
        this.categories = response.data;
      })
      .catch((error) => console.log(error.response.data));

    this.axios
      .get(`/api/loyaltyProgram/getPoints`, {
        headers: { Authorization: "Bearer " + localStorage.getItem("token") },
      })
      .then((response) => {
        this.points = response.data;
      })
      .catch((error) => console.log(error.response.data));
  },
  
});
</script>
<style scoped>

</style>
