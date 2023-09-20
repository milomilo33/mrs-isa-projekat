<template>
   <div class="card">
      <div class="card-body">
        <div v-if="this.point.type === 'EXAMINATION'">
          <h5 class="card-title examination">{{point.type}}</h5>
        </div>  
    
        <div v-else>
          <h5 class="card-title counseling">{{point.type}}</h5>
        </div>
        
        <b-alert v-model="showDismissibleAlert" dismissible fade variant="danger">
        Failed to edit appointment points,
        </b-alert>
        <b-alert v-model="showSuccessAlert" dismissible fade variant="success">
        Appointment points has been edited.
        </b-alert>
        <label>Points</label>
        <input type="number" v-model="point.points" class="form-control">
      </div>
      <div class="card-footer">
        <small><button type="button" class="btn btn-secondary" @click="editPoints">
            Edit
        </button></small>
      </div>
    </div>
</template>
<script>
import { defineComponent } from '@vue/composition-api'

export default defineComponent({
  name: "PointsComponent",
  props:{ 
    point: Object
  },
   data(){
    return{
      showDismissibleAlert: false,
      showSuccessAlert: false,
    }
  },
  methods: {
    editPoints() {
      console.log(this.point);
      if(this.point.points > 0){
         this.axios
        .post(process.env.VUE_APP_API_URL+
          `/loyaltyProgram/updatePoints`,
          {
            id: this.point.id,
            points: this.point.points,
            type: this.point.type,
          },
          {
            headers: {
              Authorization: "Bearer " + localStorage.getItem("token"),
            },
          }
        )
        .then((response) => {
          if (response.data != null) {
            this.point=response.data;
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
.examination{
  color: white;
  background-color:rgb(1, 213, 255);
  border-radius: 5rem; 
}
.counseling{
  color: white;
  background-color:rgb(153, 0, 255);
  border-radius: 5rem; 
}
</style>
