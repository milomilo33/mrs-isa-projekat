<template>
  <div class="card">
    <div class="card-body">
      <div v-if="this.category.category === 'GOLD'">
        <h5 class="card-title gold">{{category.category}}</h5>
      </div>  
      <div v-else-if="this.category.category=== 'SILVER'">
        <h5 class="card-title silver">{{category.category}}</h5>
      </div>
      <div v-else>
        <h5 class="card-title bronze">{{category.category}}</h5>
      </div>
        
      <b-alert v-model="showDismissibleAlert" dismissible fade variant="danger">
        Failed to edit category
      </b-alert>
      <b-alert v-model="showSuccessAlert" dismissible fade variant="success">
        Category has been edited
      </b-alert>
      <label>Threshold</label>
      <input v-model="category.threshold" class="form-control">
      <label>Discount</label>
      <input type="number" class="form-control" v-model="category.discount">
    </div>
    <div class="card-footer">
      <small><button type="button" class="btn btn-secondary" @click="editCategory">
            Edit
      </button></small>
    </div>
  </div>
</template>

<script>
import { defineComponent } from '@vue/composition-api'

export default defineComponent({
  name: "CategoryComponent",
  props: {
    category: Object
  },
  data(){
    return{
      showDismissibleAlert: false,
      showSuccessAlert: false,
    }
  },
  methods: {
     editCategory() {
      

      if(this.category.threshold > 0 && this.category.discount > 0){
        console.log("sssssssssss");
         this.axios
        .post(
          `/api/loyaltyProgram/updateCategory`,
          {
            id: this.category.id,
            category: this.category.category,
            threshold: this.category.threshold,
            discount: this.category.discount
          },
          {
            headers: {
              Authorization: "Bearer " + localStorage.getItem("token"),
            },
          }
        )
        .then((response) => {
          if (response.data != null) {
            console.log("sssssssssssssssssssssssssss")
            this.showSuccessAlert = true;
            this.showDismissibleAlert = false;
           
          }
        })
        .catch((error) => {
          console.log(error);
          this.showDismissibleAlert = true;
        });
      }
     

    },
  }
})
</script>
<style scoped>
.bronze{
  background-color: #ff6666;
  color: white;
  border-radius: 5rem;
}
.silver{
  background-color: #ff9999;
  color: white;
   border-radius: 5rem;
}
.gold{
  background-color: #ff4d4d;
  color: white;
   border-radius: 5rem;
}
</style>
