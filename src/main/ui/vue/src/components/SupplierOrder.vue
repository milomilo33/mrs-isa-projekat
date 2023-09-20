<template>
  <div class="card">
    <div class="card-header">
      <h5 class="header-color">
        <b>Pharmacy: {{ this.order.admin.pharmacy.name }}</b>
      </h5>
    </div>
    <div class="card-body">
      <b-alert v-model="showDismissibleAlert" dismissible fade variant="danger">
        Could not send offer.
      </b-alert>
      <b-alert v-model="showSuccessAlert" dismissible fade variant="success">
        Success! You sent offer.
      </b-alert>
      <h5 class="card-title">Medicaments required</h5>
      <div v-for="mi in this.order.medicamentItems" :key="mi.id">
        <p class="card-text">{{ mi.medicament.name }}: {{ mi.quantity }}</p>
      </div>

      <button
        type="button"
        class="btn btn-secondary"
        data-toggle="modal"
        :data-target="`#exampleModalCenter${order.id}`"
      >
        Write offer
      </button>
    </div>
    <div class="card-footer">
      Deadline: {{ this.frontEndDateFormat(this.order.deadline) }}
    </div>
    <div
      class="modal fade"
      :id="`exampleModalCenter${order.id}`"
      tabindex="-1"
      role="dialog"
      aria-labelledby="exampleModalCenterTitle"
      aria-hidden="true"
    >
      <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title header-color" id="exampleModalLongTitle">
              <b>Write offer</b>
            </h5>
            <button
              type="button"
              class="close"
              data-dismiss="modal"
              aria-label="Close"
            >
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div class="modal-body">
            <div class="form-row">
              <template>
                <div class="form-row">
                  <label>Deadline: </label>
                  <b-form-datepicker
                    v-model="deadline"
                    :min="min"
                    :max="max"
                    locale="en"
                    class="col-md-12"
                  ></b-form-datepicker>
                  <label name="totalPrice">Total Price</label>
                  <input
                    type="number"
                    class="form-control col-md-12"
                    v-model="totalPrice"
                    min="1"
                    required
                  />
                </div>
                <p v-show="showDismissibleAlertModal" class="red-color">
                  Total price must be greater than 0.
                </p>
              </template>
            </div>
          </div>
          <div class="modal-footer">
            <button
              type="button"
              class="btn btn-secondary"
              data-dismiss="modal"
            >
              Close
            </button>
            <button type="button" class="btn btn-primary" @click="submitOffer">
              Send offer
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { defineComponent } from "@vue/composition-api";
import moment from "moment";

export default defineComponent({
  name: "SupplierOrder",
  props: {
    order: { type: Object },
  },

  data() {
    const now = new Date();
    const today = new Date(now.getFullYear(), now.getMonth(), now.getDate());
    // 15th two months prior
    const minDate = new Date(today);
    // 15th in two months
    const maxDate = new Date(today);
    maxDate.setMonth(maxDate.getMonth() + 2);
    maxDate.setDate(15);
    return {
      showDismissibleAlertModal: false,
      showDismissibleAlert: false,
      showSuccessAlert: false,
      min: minDate,
      max: maxDate,
      deadline: "",
      user: "",
      status: "WAITINGFORANSWER",
      supplier: "",
      totalPrice: 0.0,
    };
  },
  mounted() {
    this.supplier = JSON.parse(
      atob(localStorage.getItem("token").split(".")[1])
    ).sub;
    console.log(`Order ${this.order.id}`);
  },
  methods: {
    frontEndDateFormat: function (date) {
      return moment(date, "YYYY-MM-DD").format("DD.MM.YYYY");
    },
    submitOffer() {
      if(this.totalPrice < 1){
         this.showDismissibleAlertModal = true;
      }
      else{
          this.axios
        .post(process.env.VUE_APP_API_URL +
          `/offers/save`,
          {
            deadline: this.deadline + "T00:00:00",
            status: this.status,
            supplier: this.supplier,
            totalPrice: this.totalPrice,
            order: this.order,
          },
          {
            headers: {
              Authorization: "Bearer " + localStorage.getItem("token"),
            },
          }
        )
        .then((response) => {
          if (response.data != null) {
            this.showSuccessAlert = true;
            this.showDismissibleAlertModal = false;
          }
        })
        .catch((error) => {
          console.log(error);
          this.showDismissibleAlert = true;
        });
      }
      
    },
  },
});
</script>

<style>
.header-color {
  color: var(--green);
}
.red-color {
  color:brown 
}
</style>