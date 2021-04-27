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
              :filter-included-fields="filterOn"
              small
              @filtered="onFiltered">
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
              <b-form inline>
              <label> Points:    </label>
              <b-form-input
              id="inline-form-input-points"
              class="mb-2 mr-sm-2 mb-sm-0"
              type="number"
              v-model="points"
              ></b-form-input>
              </b-form>
              <b-form inline>
              <label for="example-datepicker"> Date from: </label>
              <b-form-datepicker :date-disabled-fn="dateDisabled" :value="this.datefrom" v-model="datefrom"  locale="en" class="mb-2"></b-form-datepicker>
              </b-form>
            </pre>
            </b-modal>
          </div>
</template>

<script>
export default {
  data() {
    return {
      fields: [
        { key: "name", sortable: true, label: "Medicament" },
        { key: "price", sortable: true, label: "Price" },
        { key: "points", sortable: true, label: "Points" },
        { key: "dateFrom", sortable: true, label: "Date from" },
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
      medicamentss: [],
      old: [],
      quantity: 0,
      pharmacy: {},
      selected: [],
      medicamentItems: [],
      price: 0,
      points:0,
      datefrom :"",
      id : 0,
      priceid :0,
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
     dateDisabled(ymd, date) {
      const today = new Date();
      const m = today.getMonth();
      const d = today.getDate();
      const day1 = date.getDate();
      const month1 = date.getMonth();
      return (day1 <= d && month1 <= m) || (day1 >= d && month1 < m);
    },
    Edit(data, button) {
        this.id = data.id;
        this.price = data.price;
        this.points = data.points;
        this.datefrom = data.date; 
        this.priceid = data.priceid;
      (this.editModal.title = "Edit pricelist"),
        this.$root.$emit("bv::show::modal", this.editModal.id, button);
    },
    check(){
        var self = this;
        self.axios.put(
          `/api/pricelistItems/update/`+self.id,
          {
            price:{
                id :self.priceid,
                value: self.price,
                points: self.points,
                dateTo: self.datefrom
            }
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
      .get(`/api/pricelistItems/`+ self.pharmacyId, {
          headers: {Authorization: "Bearer " + localStorage.getItem('token')}
        })
      .then(function (response) {
        for (var i = 0; i < response.data.length; i++) {
          var item = {};
          item.id = response.data[i].id;
          item.medicamentId = response.data[i].medicament.id
          item.name = response.data[i].medicament.name;
          item.type = response.data[i].medicament.type;
          item.medicamentForm = response.data[i].medicament.form;
          item.manufacturer = response.data[i].medicament.manufacturer;
          item.structure = response.data[i].medicament.structure;
          item.issuanceMode = response.data[i].medicament.mode;
          item.annotation = response.data[i].medicament.annotation;
          item.medicamentRating = 0;
          item.pharmacyId = response.data[i].pharmacy.id;
          item.dateFrom = response.data[i].price.dateFrom[2]+ "-"+response.data[i].price.dateFrom[1]+"-"+response.data[i].price.dateFrom[0];
          item.date = response.data[i].price.dateFrom;
          item.points =  response.data[i].price.points;
          item.price =  response.data[i].price.value;
          item.priceid =  response.data[i].price.id;
          self.items.push(item);
        }
        self.totalRows = self.items.length;
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
