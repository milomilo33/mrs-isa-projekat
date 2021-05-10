<template>
  <div>
    <h3>Offer List preview</h3>
    <hr />
    <div class="card m-auto">
      <b-alert v-model="showFailedAlertFilter" dismissible fade variant="danger">
        No result of given filter.
      </b-alert>
        <div class="filter-content">
            <div class="justify-content">
              
                <input
                  type="radio"
                  id="accepted"
                  name="rate"
                  value="ACCEPTED"
                  @click="status = 'ACCEPTED'"
                />
                <label for="star5" title="text">Accepted</label>
                <input
                  type="radio"
                  id="rejected"
                  name="rate"
                  value="REJECTED"
                  @click="status = 'REJECTED'"
                />
                <label for="star4" title="text">Rejected</label>
                <input
                  type="radio"
                  id="waitingforanswer"
                  name="rate"
                  value="WAITINGFORANSWER"
                  @click="status = 'WAITINGFORANSWER'"
                />
                <label for="star3" title="text">Waiting for answer</label>
                 <input
                  type="radio"
                  id="all"
                  name="rate"
                  value="ALL"
                  @click="status = 'ALL'"
                />
                <label for="star3" title="text">All</label>
            </div>
            <div>
              <div lg="2">
                <button class="btn btn-light" @click="fillterOffers()">
                  Fillter
                </button>
              </div>
            </div>
          </div>
          <!-- card-body.// -->
      <!-- card-group-item.// -->
    </div>
    <div class="row row-cols-2 row-cols-md-3 g-2">
      <div
        class="col-lg-4 col-md-6 col-sm-10 offset-md-0 offset-sm-1"
        v-for="o in this.offers"
        :key="o.id"
      >
        <div>
          <SupplierOffer :offer="o"> </SupplierOffer>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { defineComponent } from "@vue/composition-api";

import SupplierOffer from "../components/SupplierOffer";
export default defineComponent({
  components: {
    SupplierOffer,
  },
  data() {
    return {
      status: "",
      offers: null,
      user: "",
      showFailedAlertFilter: false,
    };
  },
  mounted() {
    this.user = JSON.parse(
      atob(localStorage.getItem("token").split(".")[1])
    ).sub;

    this.axios
      .get(`/api/offers/` + this.user, {
        headers: { Authorization: "Bearer " + localStorage.getItem("token") },
      })
      .then((response) => {
        this.offers = response.data;
        //console.log(this.medicaments);
      })
      .catch((error) => console.log(error.response.data));
  },
  methods: {
    fillterOffers() {
      
      if(this.status=="ALL")
      {
        this.axios
        .get(`/api/offers/` + this.user, {
          headers: { Authorization: "Bearer " + localStorage.getItem("token") },
        })
        .then((response) => {
          this.offers = response.data;
          //console.log(this.medicaments);
        })
        .catch((error) => console.log(error.response.data));
      }
      else{
          this.axios
          .get(`/api/offers/filter/` + this.user + `,` + this.status, {
            headers: { Authorization: "Bearer " + localStorage.getItem("token") },
          })
          .then((response) => {
            this.offers = response.data;
            console.log(this.offers);
          })
          .catch((error) => console.log(error.response.data));
      }
      
    },
  },
});
</script>
<style scoped>
</style>