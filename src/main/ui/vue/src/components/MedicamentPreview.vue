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
            </div>
      </div>
    </div>
</template>


<script>


export default {
  name: "MedicamentPreview",
  props: {
    medicament: Object,
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
  },
  data() {
    return {
      ratings: 0,
      modal: "",
      show: false,
      nameM: "",
      success: false
    };
  },
  methods: {
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
  },
};
</script>
<style scoped>
.colorHeaders {
  color: #009933;
}
</style>