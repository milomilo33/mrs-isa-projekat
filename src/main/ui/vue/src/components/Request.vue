<template>
          <div class="my-form">
            <div class="card-header">Requests</div>
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
              <b-col lg="6" class="my-1">
        <b-form-group
          label-cols-sm="0"
          label-align-sm="right"
          label-size="sm"
          class="mb-0"
        >
          <b-button @click="Accept($event.target)" variant="success"
            >Accept</b-button
          >
        </b-form-group>
      </b-col>
      <b-col lg="0" class="my-1">
        <b-form-group
          label-cols-sm="0"
          label-align-sm="right"
          label-size="sm"
          class="mb-0"
        >
          <b-button @click="reasonModal($event.target)" variant="danger"
            >Reject</b-button
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
              :filter-included-fields="filterOn"
              small
              select-mode="single"
               selectable
                @row-selected="onRowSelected"
              @filtered="onFiltered">
            </b-table>
            <b-modal
              :id="errorModal.id"
              :title="errorModal.title"
              ok-only
              @ended="errModal"
              @reset="resetModal"
            >
              <pre>{{ errorModal.content }}</pre>
            </b-modal>

            <b-modal
          :id="reasonM.id"
          @ok="Reject"
          >
      <form ref="form" @submit.stop.prevent="handleSubmit">
        <b-form-group
          label="Reason for rejection"
          label-for="name-input"
          invalid-feedback="Reason is required"
        >
          <b-form-input
            id="name-input"
            v-model="reason"
            required
          ></b-form-input>
        </b-form-group>
      </form>
    </b-modal>
            </div>
</template>

<script>
import moment from 'moment'

export default {
  data() {
    return {
      fields: [
        { key: "employee", sortable: true, label: "Employee" },
        { key: "date1", sortable: true, label: "Date from" },
        { key: "date2", sortable: true, label: "Date to" },
        {key: "status", sortable: true, label: "Status"},
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
      reasonM: {
        id: "reason-modal",
        
      },
      pharmacyId:0,
      selected:[],
      reason:"",
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

    errModal() {
      this.errorModal.title = "";
      this.errorModal.content = "";
    },
    resetModal(){
      this.reason =""
    },
     datum: function(date){
      return moment(date,"YYYY-MM-DD").format("DD/MM/YYYY");
    },
    onRowSelected(item){
        this.selected = item;
    },
    Accept(){
       var self = this;
       if(self.selected.length == 0){
            (self.errorModal.title = "Error"),
            (self.errorModal.content = "You need to select employee!");
             self.$root.$emit("bv::show::modal", self.errorModal.id);
        }else{
        if(self.selected[0].status == "REJECTED" || self.selected[0].status=="ACCEPTED"){
             (self.errorModal.title = "Error"),
            (self.errorModal.content = "You cannot accept this request!");
          self.$root.$emit("bv::show::modal", self.errorModal.id);
        }else{
            self.axios.put(
          `/api/request/update/` + parseInt(self.selected[0].id),
          {
            id: self.selected[0].id,
            accepted: true,
            rejectionReason:"",
          },
          {
            headers: {
              Authorization: "Bearer " + localStorage.getItem('token'),
            },
          }
        ).then(function(response){
          console.log(response);
           self.selected = [];
            self.items = [];
            self.refresh();
        }).catch((error => {
         
          if(error.response.status == "400"){
           
             (self.errorModal.content = "You cannot accept this request!");
          self.$root.$emit("bv::show::modal", self.errorModal.id);
          }
         
        }));
        
       
        }}
    },
    reasonModal(button){
      if(this.selected.length == 0){
            (this.errorModal.title = "Error"),
            (this.errorModal.content = "You need to select employee!");
             this.$root.$emit("bv::show::modal", this.errorModal.id);
        }else{
        this.$root.$emit("bv::show::modal", this.reasonM.id, button);
        }
    },
    Reject(){
        var self = this;
        
        if(self.selected[0].status == "REJECTED" || self.selected[0].status=="ACCEPTED"){
             (self.errorModal.title = "Error"),
            (self.errorModal.content = "You cannot accept this request!");
          self.$root.$emit("bv::show::modal", self.errorModal.id);
        }else{
            this.axios.put(
          `/api/request/update/` + parseInt(self.selected[0].id),
          {
            id: self.selected[0].id,
            accepted: false,
            rejectionReason: self.reason
          },
          {
            headers: {
              Authorization: "Bearer " + localStorage.getItem('token'),
            },
          }
        ).then(function(response){
          console.log(response);
           self.selected = [];
            self.items = [];
            self.refresh();
        }).catch((error => {
         
          if(error.response.status == "400"){
           
             (self.errorModal.content = "You cannot accept this request!");
          self.$root.$emit("bv::show::modal", self.errorModal.id);
          }
         
        }));
        }
        
    }
    ,
    refresh(){
        var self = this;
        self.axios
      .get(`/api/pharmacy/requests/`+ self.pharmacyId, {
          headers: {Authorization: "Bearer " + localStorage.getItem('token')}
        })
      .then(function (response) {
        for (var i = 0; i < response.data.length; i++) {
            console.log(response.data[i]);
            var item  = {};
            item.id = response.data[i].id;
            item.employee = response.data[i].employee.name + " "+ response.data[i].employee.lastName;
            item.employeeEmail = response.data[i].employee.email;
            item.date1 = self.datum(response.data[i].dateFrom);
            item.date2 = self.datum(response.data[i].dateTo);
            if(response.data[i].accepted == false && response.data[i].deleted == false){
                item.status = "PENDING";
            }else if(response.data[i].accepted == false && response.data[i].deleted == true){
                item.status = "REJECTED";
            }else if(response.data[i].accepted == true && response.data[i].deleted == true){
                item.status ="ACCEPTED";
            }
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
