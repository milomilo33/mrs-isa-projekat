<template>
<div id="pharmacy-basic-info">
    <b-container>
    <b-row>
      <b-col cols="4" class="col-sm-12 col-md-4 col-lg-4">
        <img class="img" src="../slika.jpg" alt="">
      </b-col>

      <b-col cols="8" align-v="start">
        <b-container>
            <b-row align-v="start">
            <h1>Moja Apoteka</h1>
            </b-row>
            
            <b-row class="h-50 mh-50" cols="8" align-h="start">
            <p class="text-left">Pharmacy No.1</p>
            </b-row>
            <b-row class="h-25" align-v="end">
            <h5>Address of a Pharmacy</h5>
            </b-row>
    </b-container>
      </b-col>
    </b-row>
    </b-container>
</div>
</template>


<script>

export default {
    name: 'PharmacyBasicInfo',
   
    props: {
    }
}
</script>


<style scoped>
    .img {
    max-width: 100%;
    max-height: 100%;
  }

  .pharmacy {
    text-align: left;
    }

  .address {
    position: absolute;
    bottom: 0;
    left: 10;
  }
</style>