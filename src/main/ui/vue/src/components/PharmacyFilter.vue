<template>
    <div class="card m-auto">
    <article class="card-group-item">
        
        <div class="filter-content">
            <div class="card-body">
            <div class="form-row d-flex p-2">
				<b-col class="mt-1" lg="2"><h4>Udaljenost </h4></b-col>
              <b-col lg="1" class="mt-2"><h5>Min:</h5></b-col>
              <b-col><input type="number" class="form-control" placeholder="0 km"></b-col>
              <b-col lg="1" class="mt-2"><h5>Max:</h5></b-col>
              <b-col><input type="number" class="form-control" placeholder="0 km"></b-col>
            </div>

			<div class="d-flex justify-content-start p-2">
				<b-col lg="2"><h4>Rejting</h4></b-col>
				<b-col class="rate">
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
			</div>
			<div>
				<div lg="2">
					<button class="btn btn-light" @click="filterPharmacy()">Filtriraj</button>
				</div>
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
		minDistance: -1,
		maxDistance: -1,
		rating: -1,
		pharmacies: null
	},
	methods: {
		filterPharmacy() {
			if(this.rating === undefined) this.rating = -1
			if(this.minDistance === undefined) this.minDistance = -1
			if(this.maxDistance === undefined) this.maxDistance = -1

			this.axios.get(`http://localhost:8080/api/pharmacy/filter/distance=${this.minDistance},${this.maxDistance}&rating=${this.rating}`, {
          headers: {Authorization: "Bearer " + localStorage.getItem('token')}
        })
				.then(response => {
					console.log(response.data);
					this.pharmacies = response.data;

				}).catch(error => console.log(error));

			this.$emit('childToParent', this.pharmacies);
			
		}
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