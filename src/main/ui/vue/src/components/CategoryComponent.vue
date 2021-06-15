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
        {{message}}
      </b-alert>
      <b-alert v-model="showSuccessAlert" dismissible fade variant="success">
        {{message}}
      </b-alert>
      <label>Threshold</label>
      <input v-model="category.threshold" class="form-control">
      <label>Discount</label>
      <input type="number" class="form-control" v-model="category.discount">
    </div>
    <div class="card-footer">
      <small><button type="button" class="btn btn-secondary" @click="editCategory">
            Edit
      </button></small> &nbsp;&nbsp;&nbsp;
       <small><button type="button" class="btn btn-secondary" @click="deleteCategory">
            Delete
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
      message: "",
    }
  },
  methods: {
     editCategory() {
      

      if(this.category.threshold > 0 && this.category.discount > 0){
         this.axios
        .post(
          process.env.VUE_APP_API_URL+`/loyaltyProgram/updateCategory`,
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
            this.message = "Success! Category has been updated.";
            this.showSuccessAlert = true;
            this.showDismissibleAlert = false;
           
          }
        })
        .catch((error) => {
          console.log(error);
          this.message = "Category failed to update."
          this.showDismissibleAlert = true;
        });
      }
    },
    deleteCategory(){
       this.axios
        .post(
          process.env.VUE_APP_API_URL+`/loyaltyProgram/delete`,
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
            this.message = "Category has been deleted";
            this.showSuccessAlert = true;
            this.showDismissibleAlert = false;
            window.location.reload();
           
          }
        })
        .catch((error) => {
          console.log(error);
          this.message = "Category failed to delete."
          this.showDismissibleAlert = true;
        });
    }
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
