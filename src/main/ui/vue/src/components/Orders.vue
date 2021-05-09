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
      <b-col lg="4" class="my-1">
        <b-form-group
          label="Filter"
          label-cols-sm="3"
          label-align-sm="right"
          label-size="sm"
          class="mb-0"
        >
          <b-form-select v-model="f" :options="options" v-on:change="Filter"></b-form-select>
        </b-form-group>
      </b-col>
      <b-col lg="4" class="my-1">
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
     <template v-slot:cell(edit)="data">
                <b-button
                  size="sm"
                  @click="Edit(data.item, $event.target)"
                  class="mr-1"
                  variant="info">
                  Edit
                </b-button>
    </template>
    <template v-slot:cell(delete)="data">
                <b-button
                  size="sm"
                  @click="Delete(data.item, $event.target)"
                  class="mr-1"
                  variant="danger">
                  Delete
                </b-button>
    </template>
    <template v-slot:cell(details)="data">
                <b-button
                  size="sm"
                  @click="ShowDetails(data.item, $event.target)"
                  class="mr-1"
                  variant="light">
                  Details
                </b-button>
    </template>
    <template v-slot:cell(offers)="data">
                <b-button
                  size="sm"
                  @click="ShowOffers(data.item, $event.target)"
                  class="mr-1"
                  variant="warning">
                  Offers
                </b-button>
    </template>
    </b-table>
     <b-modal
      :id="detailsModal.id"
      :title="detailsModal.title"
      ok-only
      @ended="resetInfoModal"
    >
      <pre><b-table
                  :items="allmeds" 
                  :fields="fields2" 
                  responsive="sm" 
                  small></b-table></pre>
    </b-modal>
     <b-modal
      :id="editModal.id"
      :title="editModal.title"
      @ok="Ok"
      @ended="resetEditModal"
    >
      <div>
          <form name="myform">
              <div class="form-group row">
                        <label class="col-md-4 col-form-label text-md-right">Medicament </label>
                        <label class="col-md-4 col-form-label text-md-right">Quantity </label>
                    </div>
              <div v-for="item in allmeds" :key="item.name">
                    <div class="form-group row">
                        <div class="col-md-6">
                        <input type="text" class="form-control" v-model="item.name" disabled=true >
                        </div>
                        <div class="col-md-6">
                            <input type="number" class="form-control"  v-model="item.quantity"  >
                        </div>
                    </div>
            </div>
            </form>
         <label for="dd"> Deadline:    </label>
          <br>
          <label><b-form-datepicker
            :date-disabled-fn="dateDisabled"
            v-model="date"
            locale="en"
            class="mb-2"
          ></b-form-datepicker></label>
            <br>
          <b-button
                  size="sm"
                  @click="Save()"
                  variant="success"
                >   Save
                </b-button>
      </div>
    </b-modal>
    <b-modal
      :id="errorModal.id"
      :title="errorModal.title"
      ok-only
      @ended="errModal"
      :header-bg-variant="headerErrorVariant"
    >
      <pre>{{ errorModal.content }}</pre>
    </b-modal>
    <b-modal
      size="lg"
      :id="addModal.id"
      :title="addModal.title"
      @ok="AddAll"
      @ended="resetInfoModal"
      :header-bg-variant="headerBgVariant"
      :footer-bg-variant="headerBgVariant"
    >
      <template>
        <div>
          <b-table
                  :items="medicamentss" 
                  :fields="fields3" 
                  responsive="sm" 
                  small
                  :select-mode="selectMode"
                ref="selectableTable"
                selectable
                @row-selected="onRowSelected"></b-table>
        </div>
        <div>
             <label>  Quantity:    </label>
             <br>
              <label><b-form-input
              id="inline-form-input-quantity"
              class="mb-2 mr-sm-2 mb-sm-0"
              type="number"
              v-model="quantity"
              ></b-form-input></label>
              <label></label>
              <br>
          <label for="dd"> Deadline:    </label>
          <br>
          <label><b-form-datepicker
            :date-disabled-fn="dateDisabled"
            v-model="date"
            locale="en"
            class="mb-2"
          ></b-form-datepicker></label>
            <br>
          <b-button
                  size="sm"
                  @click="AddOne()"
                  variant="success"
                >   Add
                </b-button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script>
import moment from 'moment'
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
       fields2: [
        { key: "name", label: "Name" },
        { key:"quantity", label: "Quantity"}
      ],
      fields3: [
        { key: "id", label: "Id" },
        { key: "name", label: "Name" },
        { key: "type", label: "Type" },
        { key: "medicamentForm", label: "Medicament form" },
        { key: "manufacturer", label: "Manufacturer" },
      ],
      options:["Processed", "Waiting for offers","All"],
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
      detailsModal: {
        id: "details-modal",
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
      pharmacyId: 0,
      selected: [],
      date: "",
      message: "",
      d: Object,
      adminU:"",
      medicamentss:[],
      allmeds:[],
      addedItems :[],
      quantity:0,
      orderId:0,
      da:"",
        a:[],
      f:0,
      filterStatus:"",
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

    datum: function(date){
      return moment(date,"YYYY-MM-DD").format("DD/MM/YYYY");
    },
    onFiltered(filteredItems) {
      self.totalRows = filteredItems.length;
    },

    resetInfoModal() {
      this.detailsModal.title = "";
      this.detailsModal.content = "";
      this.allmeds=[];
    },
    
    resetEditModal() {
      this.editModal.title = "";
      this.editModal.content = "";
    },

    errModal() {
      this.errorModal.title = "";
      this.errorModal.content = "";
    },
    AddOrder(button) {
      (this.addModal.title = "Add order"),
        this.$root.$emit("bv::show::modal", this.addModal.id, button);
    },
     ShowDetails(data,button) {
         this.allmeds = data.allmeds;
      (this.detailsModal.title = "Details"),
        this.$root.$emit("bv::show::modal", this.detailsModal.id, button);
    },
    Edit(data,button) {
         this.allmeds = data.allmeds;
         this.orderId = data.id;
         this.da = data.date;
      (this.editModal.title = "Edit"),
        this.$root.$emit("bv::show::modal", this.editModal.id, button);
    },
    onRowSelected(item) {
      this.selected = item;
    },
    Filter(){
      var self = this;
      if(self.f != "All"){
      if(self.f == "Processed"){
          self.filterStatus = "processed";

      }else if(self.f == "Waiting for offers"){
          self.filterStatus="Waitingforoffers";
      }
      self.items =[];
      self.axios.get(`/api/orders/filter/` + self.filterStatus+"/"+self.pharmacyId, {
          headers: { Authorization: "Bearer " + localStorage.getItem("token") },
        })
        .then(function (response) {
          for (var i = 0; i < response.data.length; i++) {
            var item = {};
            item.id = response.data[i].id;
            item.date1 = self.datum(response.data[i].deadline);
            item.status = response.data[i].status;
            item.employee = response.data[i].admin.name + " "+response.data[i].admin.lastName;
            item.admin = response.data[i].admin.email;
            var allM =[];
            for(var j = 0;j<response.data[i].medicamentItems.length;j++){
                var med ={};
                med.name = response.data[i].medicamentItems[j].medicament.name;
                med.quantity = response.data[i].medicamentItems[j].quantity;
                med.id = response.data[i].medicamentItems[j].id;
                allM.push(med);
            }
            item.allmeds = allM;
            self.items.push(item);
          }
          console.log(self.items);
          self.totalRows = self.items.length;
        });
      }else{
        self.items =[];
        self.refresh();
      }
    },
    Save(){
        for(var i =0; i<this.allmeds.length; i++){
            console.log(this.allmeds[i]);
            var medicamentItem ={};
            var medicament ={};
            medicamentItem.id = this.allmeds[i].id;
            medicamentItem.quantity = parseInt(this.allmeds[i].quantity);
            medicamentItem.medicament = medicament;
            this.a.push(medicamentItem);

        }
        console.log(this.a);
         this.axios.put(
          `/api/orders/updateOrder/` + this.adminU,
          {
            id: this.orderId,
            deadline:this.da,
            medicamentItems:this.a,
          },
          {
            headers: {
              Authorization: "Bearer " + localStorage.getItem('token'),
            },
          }
        );
        this.a =[];
        
        
    },
    Ok(){
        this.allmeds=[];
        this.items=[];
        this.refresh();
    },
    AddOne() {
    var d = this.selected[0];
    const idx = this.medicamentss.indexOf(d);
      if (idx != -1) {
        this.medicamentss.splice(idx, 1);
        var medicamentItem ={};
        var medicament= {};
        medicament.id = this.selected[0].id;
        medicamentItem.medicament = medicament;
        medicamentItem.quantity = this.quantity;
        this.addedItems.push(medicamentItem);
        this.selected =[];
        this.quantity = 0;
      }
      
    },
    AddAll(){
        var self = this;
         self.axios.post(
            `/api/orders/`,
            {
                deadline : self.date,
                medicamentItems: self.addedItems,
                admin:{
                    email:self.adminU,
                }

            },
            {
              headers: {
                Authorization: "Bearer " + localStorage.getItem('token'),
              },
            }
          );
          self.allmeds =[];
          self.items = [];
          self.refresh();


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
            var item = {};
            item.id = response.data[i].id;
            item.date1 = self.datum(response.data[i].deadline);
            item.status = response.data[i].status;
            item.employee = response.data[i].admin.name + " "+response.data[i].admin.lastName;
            item.admin = response.data[i].admin.email;
            var allM =[];
            for(var j = 0;j<response.data[i].medicamentItems.length;j++){
                var med ={};
                med.name = response.data[i].medicamentItems[j].medicament.name;
                med.quantity = response.data[i].medicamentItems[j].quantity;
                med.id = response.data[i].medicamentItems[j].id;
                allM.push(med);
            }
            item.allmeds = allM;
            self.items.push(item);
          }
          self.totalRows = self.items.length;
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
     Delete(item, button) {
      var self = this;
      self.axios
        .delete(`/api/orders/` + item.id +`/`+ self.adminU, {
          headers: {
            Authorization: "Bearer " + localStorage.getItem('token'),
          },
        })
        .then(function (response) {
          const idx = self.items.indexOf(item);
          self.items.splice(idx, 1);
          console.log(response);
          (self.errorModal.title = "Success"),
            (self.errorModal.content = "Successfuly deleted!");
            self.headerErrorVariant="success";
          self.$root.$emit("bv::show::modal", self.errorModal.id, button);
        })
        .catch(function (error) {
          (self.errorModal.title = "Error"),
            (self.errorModal.content = "You cannot delete!");
          self.$root.$emit("bv::show::modal", self.errorModal.id, button);
          console.log(error);
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