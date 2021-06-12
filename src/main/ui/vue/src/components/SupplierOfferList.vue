<template>
  <div>
    <h3>Offer List preview</h3>
    <hr />
    <div class="m-auto">
      <b-alert v-model="showFailedAlertFilter" dismissible fade variant="danger">
        No result of given filter.
      </b-alert>
        <div class="filters">
            <div class="justify-content">
              
                <input
                  type="radio"
                  id="accepted"
                  name="rate"
                  value="accepted"
                  @click="status = 'ACCEPTED'"
                />
                <label for="accepted" title="text">Accepted</label>
                <input
                  type="radio"
                  id="rejected"
                  name="rate"
                  value="rejected"
                  @click="status = 'REJECTED'"
                />
                <label for="rejected" title="text">Rejected</label>
                <input
                  type="radio"
                  id="waitingforanswer"
                  name="rate"
                  value="waitingforanswer"
                  @click="status = 'WAITINGFORANSWER'"
                />
                <label  for="waitingforanswer" title="text">Waiting for answer</label>
                 <input
                  type="radio"
                  id="all"
                  name="rate"
                  value="all"
                  checked
                  @click="status = 'ALL'"
                />
                <label for="all" title="text">All</label>
            </div><br>
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
    <div class="justify-content-center">
    <div class="row row-cols-6 row-cols-md-2">
      <div
        class="col-lg-3 col-md-6 col-sm-8 offset-md-1 offset-sm-1" style="height: 25rem;"
        v-for="o in this.offers"
        :key="o.id"
      >
        <div>
          <SupplierOffer :offer="o"> </SupplierOffer>
        </div>
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
      active: false,
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
        console.log(this.offers);
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

input[type="radio"] {
  position: absolute;
  left: -9999px;
}
.filters {
  text-align: center;
  margin-bottom: 2rem;
}
 
.filters * {
  display: inline-block;
}
 
.filters label {
  padding: 0.5rem 1rem;
  margin-bottom: 0.25rem;
  border-radius: 2rem;
  min-width: 50px;
  line-height: normal;
  cursor: pointer;
  transition: all 0.1s;
}
 
.filters label:hover {
  background: var(--green);
  color: var(--white);
}

input:checked + label {
  color: var(--white);
  background: var(--green);
}

</style>