<template>
          <div class="my-form">
            <div class="card-header">Pricelist</div>
            <b-row>
              <b-col lg="4" class="my-1">
                <b-form-group
                  label-for="filter-input"
                  label-cols-sm="3"
                  label-align-sm="right"
                  label-size="sm"
                  class="mb-0"
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
              striped hover
              small
            >
             <template v-slot:cell(edit)="data">
                <b-button
                  size="sm"
                  @click="Edit(data.item, $event.target)"
                  class="mr-1"
                  variant="info">
                  Edit
                </b-button>
              </template>
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
              size="lg"
              :id="addModal.id"
              :title="addModal.title"
              ok-only
              @ended="resetInfoModal"
              :header-bg-variant="headerBgVariant"
              :footer-bg-variant="headerBgVariant">
            </b-modal>
             <b-modal
              size="sm"
              :id="editModal.id"
              :title="editModal.title"
              @ok= "check"
              @ended="resetInfoModal"
              :header-bg-variant="headerBgVariant"
              :footer-bg-variant="headerBgVariant">
              <pre> 
              <b-form inline>
              <label> Price:     </label>
              <b-form-input
              id="inline-form-input-price"
              class="mb-2 mr-sm-2 mb-sm-0"
              type="number"
              v-model="price"
              ></b-form-input>
              </b-form>
            </pre>
            </b-modal>
          </div>
</template>

<script>
import moment from 'moment'
export default {
  data() {
    return {
      fields: [
        { key: "appointment", sortable: true, label: "Appointment" },
        { key: "price", sortable: true, label: "Price" },
        { key: "date1", sortable: true, label: "Date from" },
        {key :"edit", label:""},
      ],
      items: [],
      selectMode: "single",
      sortBy: "name",
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
      editModal: {
        id: "edit-modal",
        title: "",
        content: "",
      },
      headerBgVariant: "success",
      headerErrorVariant: "warning",
      all: [],
      old: [],
      price: 0,
      id : 0,
      pharmacyId:0,
    };
  },
  mounted() {
      var AdminUsername = JSON.parse(
      atob(localStorage.getItem('token').split(".")[1])
    ).sub;
    var self = this;
    self.axios
      .get(`/api/pharmacyAdmin/` + AdminUsername, {
        headers: {
          Authorization: "Bearer " + localStorage.getItem('token'),
        },
      })
      .then(function (response) {
        self.pharmacyId = response.data.pharmacy.id;
        self.refresh(); 
      })
      .catch(function (error) {
        console.log(error);
      });
       
  },
  methods: {
    resetInfoModal() {
      this.infoModal.title = "";
      this.infoModal.content = "";
    },
     datum: function(date){
      return moment(date,"YYYY-MM-DD").format("DD/MM/YYYY");
    },

    errModal() {
      this.errorModal.title = "";
      this.errorModal.content = "";
    },
    Edit(data, button) {
        this.id = data.id;
        this.price = data.price;
      (this.editModal.title = "Edit pricelist"),
        this.$root.$emit("bv::show::modal", this.editModal.id, button);
    },
    check(){
        var self = this;
        self.axios.put(
          `/api/pricelistItems/appointments/update/`+self.id,
          {
            price:[{
                id :self.priceid,
                value: self.price,
            },
            ]
          },{
          headers: {Authorization: "Bearer " + localStorage.getItem('token')}
        }
        ).then(function(response){
          console.log(response);
          self.items = [];
          self.refresh();
        });
        
    }
    ,
    refresh(){
        var self = this;
        self.axios
      .get(`/api/pricelistItems/appointments/`+ self.pharmacyId, {
          headers: {Authorization: "Bearer " + localStorage.getItem('token')}
        })
      .then(function (response) {
        for (var i = 0; i < response.data.length; i++) {
          console.log(response.data[i]);
          var item = {};
          item.id = response.data[i].id;
          item.appointment = response.data[i].apointment;
          item.price = response.data[i].price[0].value;
          item.date1 = self.datum(response.data[i].price[0].dateFrom);
          self.items.push(item);
        }
        
    });
    }
   
  },
};
</script>
<style scoped>
.my-form .card-header {
  background-color:#ccffbc;
}
</style>
