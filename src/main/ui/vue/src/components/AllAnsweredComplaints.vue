<template>
  <div>
    <h3>Complaint History</h3>
    <hr />
    <b-alert v-model="showDismissibleAlert" dismissible fade variant="danger">
      Could not send answer.
    </b-alert>
    <b-alert v-model="showSuccessAlert" dismissible fade variant="success">
      Your answer has been submited.
    </b-alert>
    <div class="jusify-content">
      <div class="row row-cols-4 row-cols-md-3 g-2">
        <div
          class="col-lg-3 col-md-6 col-sm-8 offset-md-1 offset-sm-0"
          style="height: 25rem"
          v-for="c in complaints"
          :key="c.id"
        >
          <div>
            <AnsweredComplaint :complaint="c"> </AnsweredComplaint>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { defineComponent } from "@vue/composition-api";
import AnsweredComplaint from '../components/AnsweredComplaint'

export default defineComponent({
  components: {
    AnsweredComplaint,
  },
  data() {
    return {
      showDismissibleAlert: false,
      showSuccessAlert: false,
      complaints: [],
      user: "",
    };
  },
  mounted() {
    this.user = JSON.parse(
      atob(localStorage.getItem("token").split(".")[1])
    ).sub;

    this.axios
      .get(`/api/complaints/getAllAnswered/` + this.user, {
        headers: { Authorization: "Bearer " + localStorage.getItem("token") },
      })
      .then((response) => {
        this.complaints = response.data;
        console.log(this.complaints);
      })
      .catch((error) => console.log(error.response.data));
  },
});
</script>
