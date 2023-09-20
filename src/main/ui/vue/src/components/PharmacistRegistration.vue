<template>
  <main class="my-form">
    <div class="cotainer">
      <b-modal
        :id="errorModal.id"
        :title="errorModal.title"
        ok-only
        @ended="errModal"
      >
        <pre>{{ errorModal.content }}</pre>
      </b-modal>
      <div class="row justify-content-center">
        <div class="col-md-6">
          <div class="card">
            <div class="card-header">Register Pharmacist</div>
            <div class="card-body">
              <form name="myform" @submit="formSubmit">
                <div class="form-group row">
                  <label
                    for="email"
                    class="col-md-4 col-form-label text-md-right"
                    >Email</label
                  >
                  <div class="col-md-6">
                    <input
                      type="text"
                      id="email"
                      class="form-control"
                      name="email"
                      v-model="email"
                    />
                  </div>
                </div>

                <div class="form-group row">
                  <label
                    for="password"
                    class="col-md-4 col-form-label text-md-right"
                    >Password</label
                  >
                  <div class="col-md-6">
                    <input
                      type="password"
                      id="password"
                      class="form-control"
                      name="password"
                      v-model="password"
                    />
                  </div>
                </div>
                <div class="form-group row">
                  <label
                    for="password_confirmation"
                    class="col-md-4 col-form-label text-md-right"
                    >Confirm password</label
                  >
                  <div class="col-md-6">
                    <input
                      type="password"
                      id="password_confirmation"
                      class="form-control"
                      name="password_confirmation"
                      v-model="password_confirmation"
                    />
                  </div>
                </div>
                <div class="form-group row">
                  <label
                    for="name"
                    class="col-md-4 col-form-label text-md-right"
                    >Name</label
                  >
                  <div class="col-md-6">
                    <input
                      type="text"
                      id="name"
                      class="form-control"
                      name="name"
                      v-model="name"
                    />
                  </div>
                </div>
                <div class="form-group row">
                  <label
                    for="lastName"
                    class="col-md-4 col-form-label text-md-right"
                    >Last name</label
                  >
                  <div class="col-md-6">
                    <input
                      type="text"
                      id="lastName"
                      class="form-control"
                      name="lastName"
                      v-model="lastName"
                    />
                  </div>
                </div>
                <div class="form-group row">
                  <label
                    for="phoneNumber"
                    class="col-md-4 col-form-label text-md-right"
                    >Phone number</label
                  >
                  <div class="col-md-6">
                    <input
                      type="text"
                      id="phoneNumber"
                      class="form-control"
                      name="phoneNumber"
                      v-model="phoneNumber"
                    />
                  </div>
                </div>
                <div class="form-group row">
                  <label
                    for="country"
                    class="col-md-4 col-form-label text-md-right"
                    >Country</label
                  >
                  <div class="col-md-6">
                    <input
                      type="text"
                      id="country"
                      class="form-control"
                      name="country"
                      v-model="country"
                    />
                  </div>
                </div>

                <div class="form-group row">
                  <label
                    for="city"
                    class="col-md-4 col-form-label text-md-right"
                    >City</label
                  >
                  <div class="col-md-6">
                    <input
                      type="text"
                      id="city"
                      class="form-control"
                      name="city"
                      v-model="city"
                    />
                  </div>
                </div>

                <div class="form-group row">
                  <label
                    for="street"
                    class="col-md-4 col-form-label text-md-right"
                    >Street</label
                  >
                  <div class="col-md-6">
                    <input
                      type="text"
                      id="street"
                      class="form-control"
                      name="street"
                      v-model="street"
                    />
                  </div>
                </div>

                <div class="form-group row">
                  <label
                    for="number"
                    class="col-md-4 col-form-label text-md-right"
                    >Number</label
                  >
                  <div class="col-md-6">
                    <input
                      type="number"
                      min="1"
                      id="number"
                      class="form-control"
                      name="number"
                      v-model="number"
                    />
                  </div>
                </div>
                <div class="card1">
                  <div class="card1-header">Work hours</div>
                  <b-table
                    :items="items"
                    :fields="fields"
                    :head-variant="headVariant"
                    responsive="sm"
                    bordered
                    small
                  >
                    <template v-slot:cell(workhourfrom)="data">
                      <b-form-timepicker
                        v-model="data.item.workHourFrom"
                        locale="en"
                      ></b-form-timepicker>
                    </template>
                    <template v-slot:cell(workhourto)="data">
                      <b-form-timepicker
                        v-model="data.item.workHourTo"
                        locale="en"
                      ></b-form-timepicker>
                    </template>
                  </b-table>
                </div>
                <div class="buttons col-md-1 offset-md-4">
                  <button class="btn btn-success" style="margin: 1px">
                    Submit
                  </button>
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  </main>
</template>

<script>
export default {
  name: "PharmacistRegistration",
  mounted() {
    var AdminUsername = JSON.parse(
      atob(localStorage.getItem("token").split(".")[1])
    ).sub;
    var self = this;
    self.axios
      .get(process.env.VUE_APP_API_URL+`/pharmacyAdmin/` + AdminUsername, {
        headers: {
          Authorization: "Bearer " + localStorage.getItem("token"),
        },
      })
      .then(function (response) {
        self.pharmacyId = response.data.pharmacy.id;
      })
      .catch(function (error) {
        console.log(error);
      });
  },
  data() {
    return {
      fields: [
        { key: "day", label: "Day" },
        { key: "workHourFrom", label: "Work hour from" },
        { key: "workHourTo", label: "Work hour to" },
      ],
      items: [
        { day: "Monday" },
        { day: "Tuesday" },
        { day: "Wednesday" },
        { day: "Thursday" },
        { day: "Friday" },
        { day: "Saturday" },
        { day: "Sunday" },
      ],
      headVariant:"success",
      email: "",
      password: "",
      password_confirmation: "",
      name: "",
      lastName: "",
      phoneNumber: "",
      country: "",
      city: "",
      street: "",
      number: 0,
      output: {},
      error_message: "",
      pharmacyId: {},
      output3: {},
      addressId: {},
      pharmacyW: {},
      showSuccessAlert: false,
      errorModal: {
        id: "error-modal",
        title: "",
        content: "",
      },
      lon:0,
      lat:0,
    };
  },
  methods: {
    formSubmit(e) {
      e.preventDefault();
      let errorFound = false;

      if (
        !this.email ||
        !this.password ||
        !this.phoneNumber ||
        !this.lastName ||
        !this.name ||
        !this.password_confirmation ||
        !this.country ||
        !this.city ||
        !this.street
      ) {
        errorFound = true;
        this.showSuccessAlert = true;
        this.errorModal.content = "You must enter all data";
        this.$root.$emit("bv::show::modal", this.errorModal.id);
      }
      if (this.workHourFrom > this.workHourTo) {
        errorFound = true;
        this.showSuccessAlert = true;
        this.error_message = "Not valid work hour.";
        this.errorModal.content = "Not valid work hour.";
        this.$root.$emit("bv::show::modal", this.errorModal.id);
      }
      if (this.password != this.password_confirmation) {
        errorFound = true;
        this.showSuccessAlert = true;
        this.error_message = "The password confirmation does not match";
        this.errorModal.content = "The password confirmation does not match.";
        this.$root.$emit("bv::show::modal", this.errorModal.id);
      }
      if (errorFound == false) {
        this.guessCoordinatesFromLocation();
        this.axios
          .post(
            process.env.VUE_APP_API_URL+`/address`,
            {
              country: this.country,
              city: this.city,
              street: this.street,
              number: this.number,
              longitude :this.lon,
              latitude:this.lat
            },
            {
              headers: {
                Authorization: "Bearer " + localStorage.getItem("token"),
              },
            }
          )
          .then((response) => {
            this.GetAdress(response);
          })
          .catch(function (error) {
            this.error_message = error;
          });
      }
    },
    errModal() {
      this.errorModal.title = "";
      this.errorModal.content = "";
    },
    GetAdress(posts) {
      var self = this;
      self.axios
        .get(process.env.VUE_APP_API_URL+`/address/` + parseInt(posts.data.id), {
          headers: {
            Authorization: "Bearer " + localStorage.getItem("token"),
          },
        })
        .then(function (response) {
          self.addressId = response.data;
          self.GetFarmacy(response);
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    GetFarmacy(addressP) {
      var self = this;
      self.axios
        .get(process.env.VUE_APP_API_URL+`/pharmacy/` + parseInt(self.pharmacyId), {
          headers: {
            Authorization: "Bearer " + localStorage.getItem("token"),
          },
        })
        .then(function (response) {
          self.GetPharmacist(addressP, response);
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    GetPharmacist(addressP, posts) {
      var self = this;
      self.axios
        .post(
          process.env.VUE_APP_API_URL+`/pharmacist/`,
          {
            email: self.email,
            password: self.password,
            name: self.name,
            lastName: self.lastName,
            phoneNumber: self.phoneNumber,
            workHourFrom: self.workHourFrom,
            workHourTo: self.workHourTo,
            pharmacy: {
              name: posts.data.name,
              description: posts.data.description,
              id: posts.data.id,
              address: {
                country: posts.data.address.country,
                city: posts.data.address.city,
                street: posts.data.address.street,
                number: posts.data.address.number,
              },
            },
            address: {
              country: addressP.data.country,
              id: addressP.data.id,
              city: addressP.data.city,
              street: addressP.data.street,
              number: addressP.data.number,
            },
            workHours: [
              {
                day: self.items[0].day,
                workHourFrom: self.items[0].workHourFrom,
                workHourTo: self.items[0].workHourTo,
                deleted: false,
                pharmacy: null,
              },
              {
                day: self.items[1].day,
                workHourFrom: self.items[1].workHourFrom,
                workHourTo: self.items[1].workHourTo,
                deleted: false,
                pharmacy: null,
              },
              {
                day: self.items[2].day,
                workHourFrom: self.items[2].workHourFrom,
                workHourTo: self.items[2].workHourTo,
                deleted: false,
                pharmacy: null,
              },
              {
                day: self.items[3].day,
                workHourFrom: self.items[3].workHourFrom,
                workHourTo: self.items[3].workHourTo,
                deleted: false,
                pharmacy: null,
              },
              {
                day: self.items[4].day,
                workHourFrom: self.items[4].workHourFrom,
                workHourTo: self.items[4].workHourTo,
                deleted: false,
                pharmacy: null,
              },
              {
                day: self.items[5].day,
                workHourFrom: self.items[5].workHourFrom,
                workHourTo: self.items[5].workHourTo,
                deleted: false,
                pharmacy: null,
              },
              {
                day: self.items[6].day,
                workHourFrom: self.items[6].workHourFrom,
                workHourTo: self.items[6].workHourTo,
                deleted: false,
                pharmacy: null,
              },
            ],
          },
          {
            headers: {
              Authorization: "Bearer " + localStorage.getItem("token"),
            },
          }
        )
        .then((response) => {
          (self.showSuccessAlert = true);
          self.error_message = "Success!";
          console.log(response);
          self.errorModal.content = "Success! You registred pharmacist!";
          self.$root.$emit("bv::show::modal", self.errorModal.id);
        })
        .catch(function (error) {
          (self.showSuccessAlert = true);
          console.log(error);
          self.errorModal.content =
            "Error, already exist user with same email address!";
          self.$root.$emit("bv::show::modal", self.errorModal.id);
        });
    },
    guessCoordinatesFromLocation() {
      const url =
        "https://nominatim.openstreetmap.org/search/" +
        this.city +
        ", " +
        this.street +
        " " +
        this.number;

      this.axios
        .get(url, {
          params: {
            format: "json",
            limit: 1,
            "accept-language": "en",
          },
        })
        .then((response) => {
          if (response.data && response.data.lenght != 0) {
            this.lon  = response.data[0].lon;
            this.lat = response.data[0].lat;
            
            
          }
        })
        .catch(() => {
        
        });
    },
  },
};
</script>

<style>
body {
  margin: 0;
  font-size: 0.9rem;
  font-weight: 400;
  line-height: 1.6;
  color: #212529;
  text-align: left;
  background-color: #f5f8fa;
}

.my-form {
  padding-top: 0rem;
  padding-bottom: 1rem;
}
.my-form .card-header {
  background-color: #ccffbc;
  text-align: center;
}
.my-form .row {
  margin-left: 0;
  margin-right: 0;
}
.buttons button {
  display: inline-block;
  width: 100px;
  color: white;
}
.buttons .btn-cancel {
  background-color: red;
}
.my-form .input[type="password"] {
  width: 100% !important;
  display: block !important;
}

.my-form .form-control {
  width: 200px;
}
</style>