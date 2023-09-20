<template>
    <b-nav-form>
          <input size="sm" class="mr-sm-2" placeholder="Search" v-model="search">
          <b-button size="sm" class="my-2 my-sm-0" type="submit" :href="path + '/PharmacyList/' + search">Search</b-button>
    </b-nav-form>
</template>

<script>


export default {
    components: {

    },
    props: {
        search: String,
        path: String,
    },

    data() {
        return {
            pharmacies: ''
        }
    },

    methods: {
    },


}
</script>

<style scoped>

</style>