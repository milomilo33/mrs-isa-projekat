<template>
  <div class="my-form">
    <div class="card-header">Orders</div>
    <b-row>
      <b-col lg="4" class="my-1">
        <b-form-group
          label-for="filter-input"
          label-cols-sm="3"
          label-align-sm="right"
          label-size="sm"
          class="mb-0"
        >
          <b-input-group size="sm">
            <b-form-input
              id="filter-input"
              v-model="filter"
              type="search"
              placeholder="Search"
            >
            </b-form-input>
            <b-input-group-append>
              <b-button :disabled="!filter" @click="filter = ''"
                >Clear</b-button
              >
            </b-input-group-append>
          </b-input-group>
        </b-form-group>
      </b-col>
      <b-col lg="8" class="my-1">
        <b-form-group
          label-cols-sm="3"
          label-align-sm="right"
          label-size="sm"
          class="mb-0"
        >
          <b-button @click="AddOrder($event.target)" variant="success"
            >Add</b-button
          >
        </b-form-group>
      </b-col>
    </b-row>
    <b-table
      ref="table"
      :items="items"
      :fields="fields"
      responsive="sm"
      :sort-by.sync="sortBy"
      :sort-desc.sync="sortDesc"
      :filter="filter"
      striped
      hover
      :filter-included-fields="filterOn"
      small
      @filtered="onFiltered"
    >
    </b-table>
    <b-modal
      :id="errorModal.id"
      :title="errorModal.title"
      ok-only
      @ended="errModal"
      :header-bg-variant="headerErrorVariant"
      :footer-bg-variant="headerErrorVariant"
    >
      <pre>{{ errorModal.content }}</pre>
    </b-modal>
    <b-modal
      size="lm"
      :id="addModal.id"
      :title="addModal.title"
      @ok="AddOne"
      @ended="resetInfoModal"
      :header-bg-variant="headerBgVariant"
      :footer-bg-variant="headerBgVariant"
    >
      <template>
        <div>
          
        </div>
        <div>
          <label></label>
          <label for="dd"> Deadline: </label>
          <b-form-datepicker
            :date-disabled-fn="dateDisabled"
            v-model="date"
            locale="en"
            class="mb-2"
          ></b-form-datepicker>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script>
export default {
  components: {
  },
  data() {
    return {
      fields: [
        { key: "status", sortable: true, label: "Status" },
        { key: "date1", sortable: true, label: "Deadline" },
        { key: "employee", sortable: true, label: "Employee" },
        "details",
        "offers",
        "edit",
        "delete"
      ],
      items: [],
      selectMode: "single",
      sortBy: "date1",
      sortDesc: false,
      filter: null,
      filterOn: [],
      totalRows: 1,
      totalRows1: 1,
      new: [],
      errorModal: {
        id: "error-modal",
        title: "",
        content: "",
      },
      addModal: {
        id: "add-modal",
        title: "",
        content: "",
      },
      headerBgVariant: "success",
      headerErrorVariant: "warning",
      pharmacyId: 0,
      selected: Boolean,
      date: "",
      message: "",
      d: Object,
      adminU:"",
      medicamentss:[],
    };
  },
  mounted() {
    var AdminUsername = JSON.parse(
      atob(localStorage.getItem("token").split(".")[1])
    ).sub;
    var self = this;
    self.axios
      .get(`/api/pharmacyAdmin/` + AdminUsername, {
        headers: {
          Authorization: "Bearer " + localStorage.getItem("token"),
        },
      })
      .then(function (response) {
        self.pharmacyId = response.data.pharmacy.id;
        self.adminU = AdminUsername;
        self.GetAllMedicaments();
        self.refresh();
      })
      .catch(function (error) {
        console.log(error);
      });
  },
  methods: {
    onFiltered(filteredItems) {
      self.totalRows = filteredItems.length;
    },

    resetInfoModal() {
      this.infoModal.title = "";
      this.infoModal.content = "";
    },

    errModal() {
      this.errorModal.title = "";
      this.errorModal.content = "";
    },
    AddOrder(button) {
      (this.addModal.title = "Add order"),
        this.$root.$emit("bv::show::modal", this.addModal.id, button);
    },
    AddOne() {
      //var self = this;
      
    },
    
    dateDisabled(ymd, date) {
      const today = new Date();
      const m = today.getMonth();
      const d = today.getDate();
      const day1 = date.getDate();
      const month1 = date.getMonth();
      return (day1 <= d && month1 <= m) || (day1 >= d && month1 < m);
    },
    refresh() {
      var self = this;
      self.axios
        .get(`/api/orders/` + self.pharmacyId, {
          headers: { Authorization: "Bearer " + localStorage.getItem("token") },
        })
        .then(function (response) {
          for (var i = 0; i < response.data.length; i++) {
              console.log(response.data[i]);
            var item = {};
            item.id = response.data[i].id;
            item.date = response.data[i].deadline;
            item.status = response.data[i].status;
            item.employee = response.data[i].admin.name + " "+response.data[i].admin.lastName;
            self.items.push(item);
          }
          self.totalRows = self.items.length;
          self.items.forEach((obj) => {
            obj["date1"] = new Date(obj["date"]).toDateString();
            //obj["date"][1] -= 1;
          });
        });
    },
    GetAllMedicaments() {
      var self = this;
      self.axios
        .get(`/api/medicaments/all`, {
          headers: {
            Authorization: "Bearer " + localStorage.getItem('token'),
          },
        })
        .then(function (response) {
          for (var i = 0; i < response.data.length; i++) {
              var item = {};
              item.id = response.data[i].id;
              item.name = response.data[i].name;
              item.type = response.data[i].type;
              item.annotation = response.data[i].annotation;
              item.structure = response.data[i].structure;
              item.manufacturer = response.data[i].manufacturer;
              item.medicamentForm = response.data[i].form;
              item.issuanceMode = response.data[i].mode;
              self.medicamentss.push(item);
          }
        });
    },
  },
};
</script>
<style scoped>
.my-form .card-header {
  background-color: #ccffbc;
}
</style>