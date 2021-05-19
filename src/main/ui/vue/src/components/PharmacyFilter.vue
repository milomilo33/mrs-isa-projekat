<template>
  <div class="card m-auto">


    <b-alert v-model="showFailedAlert" dismissible fade variant="danger">
      No result of given filter. All pharmacies will be shown.
    </b-alert>

    <b-alert v-model="showSubscribedAlert" dismissible fade variant="danger">
      You are not subscribed to any pharmacy. All pharmacies will be shown.
    </b-alert>

    <article class="card-group-item">

      <div class="filter-content">
        <div class="card-body">
          <div class="form-row d-flex pt-2">
            <b-col class="mt-1" lg="2"><h4 style="text-align: right;">Distance: </h4></b-col>
            <b-col lg="1" class="mt-2"><h5 style="text-align: right;">Min</h5></b-col>
            <b-col><input type="number" class="form-control" placeholder="0 km" v-model="minDistance" min="0" oninput="validity.valid || (value='');"></b-col>
            <b-col lg="1" class="mt-2"><h5 style="text-align: right;">Max</h5></b-col>
            <b-col><input type="number" class="form-control" placeholder="0 km" v-model="maxDistance" min="0" oninput="validity.valid || (value='');"></b-col>
          </div>

          <div class="d-flex justify-content-start p-2">
            <b-col lg="2" class="pt-2"><h4 style="text-align: right;">Rating:</h4></b-col>
            <b-col cols="3"  class="rate" align-h="start">
              <input type="radio" id="star5" name="rate" value="5" @click="rating = 5"/>
              <label for="star5" title="text">5 stars</label>
              <input type="radio" id="star4" name="rate" value="4" @click="rating = 4"/>
              <label for="star4" title="text">4 stars</label>
              <input type="radio" id="star3" name="rate" value="3" @click="rating = 3"/>
              <label for="star3" title="text">3 stars</label>
              <input type="radio" id="star2" name="rate" value="2" @click="rating = 2"/>
              <label for="star2" title="text">2 stars</label>
              <input type="radio" id="star1" name="rate" value="1" @click="rating = 1"/>
              <label for="star1" title="text">1 star</label>
            </b-col>
            <b-col class="pt-2">
              <b-form-checkbox
                  v-model="isSubscribed"
                  size="lg"
                  switch
              >
                <h4>Subscribed </h4>

              </b-form-checkbox>
            </b-col>
          </div>



          <div lg="2">
            <button class="btn btn-light" @click="filterPharmacy()">Filtriraj</button>
          </div>

        </div> <!-- card-body.// -->
      </div>
    </article> <!-- card-group-item.// -->
    <article class="card-group-item">
    </article> <!-- card-group-item.// -->
  </div> <!-- card.// -->
</template>

<script>

export default {

  setup() {

  },

  props: {
    allPharmacies: [],
  },

  data() {
    return {
      minDistance: -Infinity,
      maxDistance: Infinity,
      pharmacyLocation: null,
      myPosition: null,
      pharmacies: null,
      rating: -1,
      temp: [],
      showFailedAlert: false,
      isSubscribed: false,
      showSubscribedAlert: false,
    }
  },

  mounted() {

  },

  methods: {

    checkNegative(e) {

      if(!((e.keyCode > 95 && e.keyCode < 106)
          || (e.keyCode > 47 && e.keyCode < 58)
          || e.keyCode == 8)) {
        return false;
      }
    },

    filterPharmacy() {
      this.showFailedAlert = false;
      this.temp = [];
      if(this.rating === undefined) this.rating = -1
      if(this.minDistance === undefined) this.minDistance = 0
      if(this.maxDistance === undefined) this.maxDistance = Infinity

      navigator.geolocation.getCurrentPosition(pos => this.myPosition = [pos.coords.latitude, pos.coords.longitude]);


      if(this.rating != -1) {
        this.axios.get(`http://localhost:8080/api/pharmacy/filter/rating=${this.rating}`, {
          headers: {Authorization: "Bearer " + localStorage.getItem('token')}
        })
            .then(response => {
              console.log(response.data.length);
              if(response.data.length === 0) {
                this.showFailedAlert = true;
                this.pharmacies = this.allPharmacies;
              }
              else {
                this.pharmacies = response.data;
              }

              this.filterByDistance();


            }).catch(error => console.log(error));
      }
      this.filterBySubscription();
    },

    filterBySubscription() {

      if(this.isSubscribed) {
        var username = JSON.parse(
            atob(localStorage.getItem('token').split(".")[1])
        ).sub;
        this.axios.get(`http://localhost:8080/api/patients/allSubscribed/${username}`)
            .then(response => {
            this.pharmacies = response.data
            //this.showSubscribedAlert = true;
            //this.isSubscribed = false;
            }).catch(error => console.log(error.data));
        this.$emit('childToParent', this.pharmacies);

      }
      else {
        this.pharmacies = this.allPharmacies;
      }
    },

    filterByDistance() {
      var i;
      for(i = 0; i < this.pharmacies.length; i++) {
        this.measureDistance(this.pharmacies[i], i)
      }
    },


    measureDistance(pharmacy, index) {

      const url =
          "https://nominatim.openstreetmap.org/search/" +
          pharmacy.address.city +
          ", " +
          pharmacy.address.street +
          " " +
          pharmacy.address.number;
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

              const { lat, lon } = response.data[0];

              var distance = this.euclideanDistance([lat, lon], this.myPosition);
              console.log(this.minDistance, this.maxDistance);
              if(distance >= this.minDistance && distance <= this.maxDistance) {
                this.temp.push(pharmacy);
              }
              if(index === this.pharmacies.length - 1) {
                if(this.temp.length === 0) {
                  this.pharmacies = this.allPharmacies;
                  this.showFailedAlert = true;

                } else {
                  this.pharmacies = this.temp;
                }
                this.$emit('childToParent', this.pharmacies);
              }
            }
          })
          .catch(() => {
            //console.log(error);
            alert('Could not find coordinates based on given info.', '');
          });
    },

    euclideanDistance(c1, c2) {
      const R = 6371e3; // metres
      const x1 = c1[0] * Math.PI/180; // φ, λ in radians
      const x2 = c2[0] * Math.PI/180;
      const deltaX = (c2[0]-c1[0]) * Math.PI/180;
      const deltaLambda = (c2[1]-c1[1]) * Math.PI/180;

      const a = Math.sin(deltaX/2) * Math.sin(deltaX/2) +
          Math.cos(x1) * Math.cos(x2) *
          Math.sin(deltaLambda/2) * Math.sin(deltaLambda/2);
      const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

      const d = Math.round(R * c) / 1000; // in kilometres
      return d;
    },

    updateCoordinatesHandler: function(coordinates) {
      this.pharmacyLocation.x = coordinates[0];
      this.pharmacyLocation.y = coordinates[1];

    },
  }
}
</script>

<style scoped>
.card {
  width: 75%;

}

.rate {
  float: left;
  height: 46px;
  padding: 0 10px;
}
.rate:not(:checked) > input {
  position:absolute;
  top:-9999px;
}
.rate:not(:checked) > label {
  float:right;
  width:1em;
  overflow:hidden;
  white-space:nowrap;
  cursor:pointer;
  font-size:30px;
  color:#ccc;
}
.rate:not(:checked) > label:before {
  content: '★ ';
}
.rate > input:checked ~ label {
  color: #ffc700;
}
.rate:not(:checked) > label:hover,
.rate:not(:checked) > label:hover ~ label {
  color: #deb217;
}
.rate > input:checked + label:hover,
.rate > input:checked + label:hover ~ label,
.rate > input:checked ~ label:hover,
.rate > input:checked ~ label:hover ~ label,
.rate > label:hover ~ input:checked ~ label {
  color: #c59b08;
}
</style>