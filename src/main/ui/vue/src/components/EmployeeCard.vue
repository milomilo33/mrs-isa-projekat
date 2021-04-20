<template>
  <b-col align-h="start" class="border pt-2 ml-2">
    <a href="#">
      <img class="img" src="../employee.jpg" alt="" />
    </a>
    <b-container>
      <b-row align-h="start" class="pl-1">
        <h4>{{ employee.name }} {{ employee.lastName }}</h4>
      </b-row>
      <b-row align-h="start" class="pl-1">
        <p>{{ employee.e }}</p>
      </b-row>
      <b-row align-h="start" class="pl-1">
        <p><b-button v-b-modal="'email' + employee.email">About</b-button></p>
      </b-row>
    </b-container>
    <b-modal
      content-class="my-class"
      :id="'email' + this.employee.email"
      centered
      title="Info"
      header-bg-variant="dark"
      header-text-variant="light"
      body-bg-variant="light"
      body-text-variant="dark"
      :hide-footer="true"
    >
      <b-container fluid>
        <b-row class="mb-1 text-center colorIt">
          <p>
            <b class="colorHeaders">Name:</b>
            {{ this.employee.name }} {{ this.employee.lastName }}
          </p>
        </b-row>
      

        <b-row class="mb-1 colorIt">
          <p>
            <b class="colorHeaders">Phone number:</b>
            {{ this.employee.phoneNumber }}
          </p>
        </b-row>
        <b-row class="mb-1 colorIt">
          <p>
            <b class="colorHeaders">Email:</b>
            {{ this.employee.email }}
          </p>
        </b-row>
      </b-container>
      <template #modal-header="{ close }">
        <b-button size="sm" @click="close()"> Close </b-button>
      </template>
    </b-modal>
  </b-col>
</template>


<script>
export default {
  name: "EmployeeCard",
  props: {
    employee: Object,
  },
  mounted() {},
  data() {
    return {
      ratings: 0,
      modal: "",
      show: false,
    };
  },
  methods: {},
};
</script>
<style scoped>
.colorHeaders {
  color: #009933;
}

.img {
  max-height: 30rem;
  max-width: 30rem;
}
.colorIt1 {
  background-color: rgb(255, 255, 204, 0.7);
  border-radius: 5%;
}
</style>