<template>
  <div class="body">
    <b-card no-body class="overflow-hidden" style="max-width: 460px" >
      <b-row no-gutters>
        <b-col md="6">
          <img class="pharmacy_img" src="../medicament.jpg" alt="" />
        </b-col>
        <b-col md="6">
          <b-card-body :title="this.medicament.name">
            <b-card-text class="colorIt1">
              Tip: {{ this.medicament.type }}
            </b-card-text>
            <b-card-text class="colorIt1">
              Ocena: {{ this.ratings }}
            </b-card-text>
            <div>
              <b-button v-b-modal="'id' + medicament.id"
                >Specifikacija</b-button
              >

              <b-modal
                content-class="my-class"
                :id="'id' + this.medicament.id"
                centered
                :title="this.medicament.name"
                header-bg-variant="dark"
                header-text-variant="light"
                body-bg-variant="light"
                body-text-variant="dark"
                hide-footer="true"
              >
                <b-container fluid>
                  <b-row class="mb-1 text-center colorIt">
                    <p>
                      <b class="colorHeaders">Naziv :</b> &nbsp; #{{this.medicament.id}}
                      {{ this.medicament.name }} 
                    </p>
                  </b-row>
                  <b-row class="mb-1 colorIt">
                    <p>
                      <b class="colorHeaders"> Proizvodjac: </b>&nbsp;
                      {{ this.medicament.manufacturer }}
                    </p>
                  </b-row>

                  <b-row class="mb-1 colorIt">
                    <p>
                      <b class="colorHeaders">Sastav:</b>
                      {{ this.medicament.structure }}
                    </p>
                  </b-row>
                  <b-row class="mb-1 colorIt">
                    <p>
                      <b class="colorHeaders">Napomene:</b>
                      {{ this.medicament.annotation }}
                    </p>
                  </b-row>
                  
                </b-container>
                <template #modal-header="{ close }">
                    <b-button
                      size="sm"
                      @click="close()"
                    >
                      Close
                    </b-button>
                  </template>
              </b-modal>
            </div>
          </b-card-body>
        </b-col>
      </b-row>
    </b-card>
  </div>
</template>


<script>
export default {
  name: "MedicamentPreview",
  props: {
    medicament: Object,
  },
  mounted() {
    this.nameM = this.medicament.name;
    this.axios
      .get(
        "http://localhost:8081/api/medicaments/ratings/" +
          parseInt(this.medicament.id)
      )
      .then((response) => {
        this.ratings = response.data;
        console.log(this.ratings);
      })
      .catch((error) => console.log(error));
  },
  data() {
    return {
      ratings: 0,
      modal: "",
      show: false,
      nameM: ""
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
  },
};
</script>
<style scoped>
.colorHeaders {
  color: #009933;
}

.pharmacy_img {
  max-height: 30rem;
  max-width: 30rem;
}
.colorIt1{
  background-color: rgb(255, 255, 204,0.7);
  border-radius: 5%;
}

</style>