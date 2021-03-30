<template>
    <b-nav-form>
          <b-form-input size="sm" class="mr-sm-2" placeholder="Search" v-model="search"></b-form-input>
          <b-button size="sm" class="my-2 my-sm-0" type="submit">Search</b-button>
    </b-nav-form>
</template>

<script>

// eslint-disable-next-line no-unused-vars
const urlAPI = "http://localhost:8080/api/pharmacy"

export default {
    data() {
        return {
            search: '',
        }
    }
}
</script>

<style scoped>

</style>