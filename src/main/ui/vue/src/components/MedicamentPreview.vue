<template>
  <div class="card h-100" style="width: 20rem">
      <img
        src="../medicament.jpg"
        class="card-img-top"
        alt="..."
      />
      <div class="card-body">
        <h5 class="card-title">{{this.medicament.name}}</h5>
        <b-card-text>
              Type: {{ this.medicament.type }}
            </b-card-text>
            <b-card-text>
              Rating: {{ this.ratings }}
            </b-card-text>
            <div>
              <b-button @click="seeMore()"
                >See more</b-button
              >

              <b-button class="ml-2" variant="success" @click="addAllergy()" v-if="!this.isAllergic">Add allergy</b-button>
              <b-button class="ml-2" variant="danger" @click="removeAllergy()" v-if="this.isAllergic">Remove allergy</b-button>
            </div>
      </div>
      <b-modal ref="add-modal" hide-footer title="Success">
            <div class="d-block text-center">
                <p>You added this medication to your list of allergies!</p>
            </div>
            <b-button class="mt-3" variant="outline-success" block @click="hideAddModal">Close</b-button>
        </b-modal>
      <b-modal ref="remove-modal" hide-footer title="Success">
            <div class="d-block text-center">
                <p>You removed this medication to your list of allergies!</p>
            </div>
            <b-button class="mt-3" variant="outline-success" block @click="hideRemoveModal">Close</b-button>
        </b-modal>
    </div>
</template>


<script>


export default {
  name: "MedicamentPreview",
  props: {
    medicament: Object,
    allergies: Array
  },
  components: {
    
  },
  mounted() {
    
    this.nameM = this.medicament.name;
    this.axios
      .get(
          `/api/medicaments/ratings/ `+
          parseInt(this.medicament.id),{
          headers: {Authorization: "Bearer " + localStorage.getItem('token')}
          }
      )
      .then((response) => {
        this.ratings = response.data;
        //console.log(this.ratings);
      })
      .catch((error) => console.log(error));
      
      this.checkAllergy();

    
  },
  data() {
    return {
      ratings: 0,
      modal: "",
      show: false,
      nameM: "",
      success: false,
      isAllergic: false
    };
  },
  methods: {
    checkAllergy() {
      for(var i = 0; i < this.allergies.length; i++) {
        if(this.allergies[i].id === this.medicament.id) 
        {
          this.isAllergic = true;
        }
      }
    },

    calculateRating: function (ratings) {
      var total = 0;
      for (var i = 0; i < ratings.lenght; i++) {
        total += ratings[i];
      }

      return total / ratings.lenght;
    },
    seeMore(){
      this.$router.push("MedicamentInPharmacy/"+this.medicament.id)
    },

    addAllergy() {
      this.axios.post(`/api/patients/add_allergy/`, {
        patientEmail: JSON.parse(atob(localStorage.getItem('token').split(".")[1])).sub,
        medicamentId: this.medicament.id
      }, {headers: {
            Authorization: "Bearer " + localStorage.getItem('token')
            }})
        .then(() => {
          this.showAddModal();
          this.isAllergic = true;
        })
        .catch(error => alert(error.response.data));

       
        this.isAllergic = true;
      
    },

    removeAllergy() {
      
      this.axios.post(`/api/patients/remove_allergy/`, {
        patientEmail: JSON.parse(atob(localStorage.getItem('token').split(".")[1])).sub,
        medicamentId: this.medicament.id
      }, {headers: {
            Authorization: "Bearer " + localStorage.getItem('token')
            }})
        .then(() => {
          this.showRemoveModal();
          this.isAllergic = false;
        })
        .catch(error => alert(error.response.data));

      
    },

    hideAddModal() {
        this.$refs['add-modal'].hide()
    },
    showAddModal() {
        this.$refs['add-modal'].show()
    },

    hideRemoveModal() {
        this.$refs['remove-modal'].hide()
    },
    showRemoveModal() {
        this.$refs['remove-modal'].show()
    }
    
    },
};
</script>
<style scoped>
.colorHeaders {
  color: #009933;
}
</style>