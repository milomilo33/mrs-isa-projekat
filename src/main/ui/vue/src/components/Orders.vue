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
                  variant="secondary">
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
            <b-alert v-model="showEditAlert" dismissible fade :variant="varian">
            {{message}}
    </b-alert>
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
     
    >
      <pre>{{ errorModal.content }}</pre>
    </b-modal>
    <b-modal
      size="lg"
      :id="addModal.id"
      :title="addModal.title"
      @ok="AddAll"
      @ended="resetInfoModal"
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
    <b-modal
      size="lg"
      :id="offersModal.id"
      :title="offersModal.title"
      @ok="OfferOk"
      @ended="resetOfferModal"
    >
      <template>
        <div>
            <b-alert v-model="showSuccessAlert" dismissible fade :variant="varian">
            {{message}}
    </b-alert>
    </div>
        <div>
       
          <b-table
                  :items="alloffers" 
                  :fields="fields4" 
                  responsive="sm" 
                  small
                  :select-mode="selectMode"
                ref="selectableTable"
                selectable
                @row-selected="onSelect"></b-table>
        </div>
        
          <b-button
                  v-if="processed === true"
                  size="sm"
                  @click="Accept()"
                  variant="success"
                >   Accept
                </b-button>
      </template>
    </b-modal>
    <b-modal
      size="lg"
      :id="priceModal.id"
      :title="priceModal.title"
      @ok="PriceSave"
      @ended="resetPriceModal"
    >
      <div>
            <b-alert v-model="showEditAlert" dismissible fade :variant="varian">
            {{message}}
    </b-alert>
          <form name="myform">
              <div class="form-group row">
                        <label class="col-md-3 col-form-label text-md-right">Medicament </label>
                        <label class="col-md-3 col-form-label text-md-right">Price </label>
                        <label class="col-md-3 col-form-label text-md-right">Points </label>
                    </div>
              <div v-for="item in newMeds" :key="item.name">
                    <div class="form-group row">
                        <div class="col-md-4">
                        <input type="text" class="form-control" v-model="item.name" disabled=true >
                        </div>
                        <div class="col-md-4">
                            <input type="number" class="form-control"  v-model="item.price"  >
                        </div>
                         <div class="col-md-4">
                            <input type="number" class="form-control"  v-model="item.points">
                        </div>
                    </div>
            </div>
            </form>
            <br>
      </div>
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
      fields4: [
        { key: "id", label: "Id" },
        { key: "totalPrice", label: "Total price" },
        { key: "supplier", label: "Supplier" },
        {key: "status", label: "Status"},
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
       offersModal: {
        id: "offers-modal",
        title: "",
        content: "",
      },
      priceModal: {
        id: "price-modal",
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
      alloffers :[],
      selectedOffer:[],
      processed:false,
       showSuccessAlert:false,
       varian:"success",
       showEditAlert:false,
       meds :[],
       newMeds: [],
       names:[],
    };
  },
  mounted() {
    var AdminUsername = JSON.parse(
      atob(localStorage.getItem("token").split(".")[1])
    ).sub;
    var self = this;
    self.axios
      .get(process.env.VUE_APP_API_URL+`/pharmacyAdmin/` + AdminUsername, {
        headers: {
          Authorization: "Bearer " + localStorage.getItem("token"),
        },
      })
      .then(function (response) {
        self.pharmacyId = response.data.pharmacy.id;
        self.adminU = AdminUsername;
        self.GetAllMedicaments();
        self.GetAllMedicamentItems();
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
    resetOfferModal() {
      
      this.offersModal.title = "";
      this.offersModal.content = "";
      this.alloffers=[];
      this.processed = false;
    },
    
    resetEditModal() {
      this.editModal.title = "";
      this.editModal.content = "";
    },
    resetPriceModal() {
      this.priceModal.title = "";
      this.priceModal.content = "";
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
      if(data.status == "PROCESSED"){
           this.message = "You cannot edit processed order!";
         
           this.$root.$emit("bv::show::modal", this.errModal.id, button);
      }else{
         this.allmeds = data.allmeds;
         this.orderId = data.id;
         this.da = data.date;
      (this.editModal.title = "Edit"),
        this.$root.$emit("bv::show::modal", this.editModal.id, button);
      }
    },
    onRowSelected(item) {
      this.selected = item;
      
    },
    onSelect(item) {
      this.selectedOffer = item;
      
    },
    ShowOffers(data, button){
      this.selected[0] = data;
      if(data.status == "WAITINGFOROFFERS"){
        this.processed =true;
      }else{
                this.processed =false;
      }
      this.alloffers = data.offers;
      (this.offersModal.title = "Offers"),
        this.$root.$emit("bv::show::modal", this.offersModal.id, button);
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
      self.axios.get(process.env.VUE_APP_API_URL+`/orders/filter/` + self.filterStatus+"/"+self.pharmacyId, {
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
            var offers =[];
            for (var k = 0; k < response.data[i].offers.length; k++) {
                  var offer ={};
                  offer.id = response.data[i].offers[k].id;
                  offer.totalPrice = response.data[i].offers[k].totalPrice;
                  offer.status = response.data[i].offers[k].status;
                  offer.supplier = response.data[i].offers[k].supplier;
                  offer.orderId = response.data[i].id;
                  offers.push(offer);

            }
            item.offers = offers;
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
      var self = this;
        for(var i =0; i<self.allmeds.length; i++){
          
            var medicamentItem ={};
            var medicament ={};
            medicamentItem.id = self.allmeds[i].id;
            medicamentItem.quantity = parseInt(self.allmeds[i].quantity);
            medicamentItem.medicament = medicament;
            self.a.push(medicamentItem);

        }
         self.axios.put(
          process.env.VUE_APP_API_URL+`/orders/updateOrder/` + self.adminU,
          {
            id: self.orderId,
            deadline:self.da,
            medicamentItems:self.a,
          },
          {
            headers: {
              Authorization: "Bearer " + localStorage.getItem('token'),
            },
          }
        ).then(function(response){
          console.log(response);
          self.message = "Succesfully!";
          self.showEditAlert = true;
        }
        ).catch(function(error){
            self.message = "You cannot edit processed order!";
          self.showEditAlert = true;
          self.varian="danger";
          console.log(error);
        });
        self.a =[];
        
        
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
      this.items = [];
      this.refresh();
      
    },
    PriceSave(){

      for(var k = 0;k<this.newMeds.length;k++){
        console.log(this.newMeds[k].id);
        this.axios.post(
            process.env.VUE_APP_API_URL+`/pricelistItems/`,
            {
              price: [{
                value: this.newMeds[k].price,
                
                points: this.newMeds[k].points,
              },],
              pharmacy: {
                id: this.pharmacyId,
              },
              medicament: {
                id: this.newMeds[k].id,
              },
            },
            {
              headers: {
                Authorization: "Bearer " + localStorage.getItem('token'),
              },
            }
          );
        }
        this.newMeds = [];
      
    },
    Accept(){
      this.newMeds = [];
      for(var j = 0;j<this.selected[0].allmeds.length;j++){
          if(this.meds.includes(this.selected[0].allmeds[j].name)){
                console.log("d");
          }else{
               var item = {};
               item.id = this.selected[0].allmeds[j].idMed;
              item.name =this.selected[0].allmeds[j].name;
              this.newMeds.push(item);
         
          }

      }
      console.log(this.newMeds);   
      var self = this;
      var d = self.selectedOffer[0];
     // console.log(d.id);
      self.axios.put(
            process.env.VUE_APP_API_URL+`/offers/updateOffers/`+parseInt(d.orderId)+"/"+parseInt(d.id)+"/"+d.supplier,
            {
              id: d.id,
          },
            {
              headers: {
                Authorization: "Bearer " + localStorage.getItem('token'),
              },
            }
            
          ).then(function (response) {
          console.log(response);
           if(self.newMeds.length >0){
            self.$root.$emit("bv::show::modal", self.priceModal.id);
          }else{
            self.message = "Successfuly accepted!";
            self.showSuccessAlert = true;
          }
         
        })
        .catch(function (error) {
           self.message = "You cannot accept before deadline!";
          self.showSuccessAlert = true;
          self.varian="danger";
          console.log(error);
        });
    
    },
    OfferOk(){
      this.items =[];
      this.allmeds =[];
      this.alloffers =[];
      this.refresh();
    },
    AddAll(){
        var self = this;
         self.axios.post(
            process.env.VUE_APP_API_URL+`/orders/`,
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
        .get(process.env.VUE_APP_API_URL+`/orders/` + self.pharmacyId, {
          headers: { Authorization: "Bearer " + localStorage.getItem("token") },
        })
        .then(function (response) {
          for (var i = 0; i < response.data.length; i++) {
            console.log(response.data[i]);
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
                med.idMed = response.data[i].medicamentItems[j].medicament.id;
                allM.push(med);
            }
            item.allmeds = allM;
            var offers =[];
            for (var k = 0; k < response.data[i].offers.length; k++) {
                  var offer ={};
                  offer.id = response.data[i].offers[k].id;
                  offer.totalPrice = response.data[i].offers[k].totalPrice;
                  offer.status = response.data[i].offers[k].status;
                  offer.supplier = response.data[i].offers[k].supplier;
                  offer.orderId = response.data[i].id;
                  offers.push(offer);

            }
            item.offers = offers;
            self.items.push(item);
          }
          self.totalRows = self.items.length;
        });
        
    },
   
    GetAllMedicaments() {
      var self = this;
      self.axios
        .get(process.env.VUE_APP_API_URL+`/medicaments/all`, {
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
        .delete(process.env.VUE_APP_API_URL+`/orders/` + item.id +`/`+ self.adminU, {
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
    GetAllMedicamentItems() {
      var self = this;
      self.axios
        .get(process.env.VUE_APP_API_URL+`/pharmacy/medicamentItems/` + parseInt(self.pharmacyId), {
          headers: {
            Authorization: "Bearer " + localStorage.getItem('token'),
          },
        })
        .then(function (response) {
          for (var i = 0; i < response.data.length; i++) {
            self.meds.push(response.data[i].medicament.name);
           
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