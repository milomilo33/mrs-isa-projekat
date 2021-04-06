<template>
  <main class="my-form">
    <div class="cotainer">
      <div class="row justify-content-center">
        <div class="col-md-8">
          <div class="card">
            <div class="card-header">Pharmacists</div>
            <b-col lg="4" class="my-1">
              <b-form-group
                label-for="filter-input"
                label-cols-sm="3"
                label-align-sm="right"
                label-size="sm"
                class="mb-0">
                <b-input-group size="sm">
                  <b-form-input
                    id="filter-input"
                    v-model="filter"
                    type="search"
                    placeholder="Search">
                  </b-form-input>
                  <b-input-group-append>
                    <b-button :disabled="!filter" @click="filter = ''">Clear</b-button>
                  </b-input-group-append>
                </b-input-group>
              </b-form-group>
            </b-col>
            <b-table 
             :items="items" 
             :fields="fields" 
              responsive="sm" 
             :sort-by.sync="sortBy"
             :sort-desc.sync="sortDesc"
             :filter="filter"
             :filter-included-fields="filterOn"
             small
             @filtered="onFiltered">
            <template v-slot:cell(info)="data"> 
              <b-button size="sm" @click="infoP(data.item, $event.target)" class="mr-1" variant="light">
                Show details 
              </b-button>
            </template>
            <template v-slot:cell(delete)="data">
              <b-button size="sm" @click="DeleteOne(data.item, $event.target)" class="mr-1" variant="danger">
                Delete
              </b-button>
            </template>
            <template v-slot:cell(update)="data">
              <b-button size="sm" @click="UpdateOne(data.item)" class="mr-1" variant="info">
                Edit
              </b-button>
            </template>
           <!-- <b-table
      :items="items"
      :fields="fields"
      :select-mode="selectMode"
      responsive="sm"
      ref="selectableTable"
      selectable
      @row-selected="onRowSelected"
    > -->   </b-table>
            <b-modal :id="infoModal.id" :title="infoModal.title" ok-only @ended="resetInfoModal">
              <pre>{{ infoModal.content }}</pre>
            </b-modal>
            <b-modal :id="errorModal.id" :title="errorModal.title" ok-only @ended="errModal">
              <pre>{{ errorModal.content }}</pre>
            </b-modal>
         </div>
        </div>
      </div>
    </div>
  </main>
</template>


<script>
  export default {
    data() {
      return {
        fields: [{key:'name',sortable:true, label:'Name'}, {key:'lastName',sortable:true, label:'Last name'},{key: 'email',sortable:true, label:'Email'}, {key:'phoneNumber',sortable:true, label:'Phone number'},'info',{key:'update', label:'Edit'},'delete'],
        items: [
        ],
        selectMode: 'single',
        selected: [],
        sortBy:'name',
        sortDesc :false,
        filter: null,
        filterOn: [],
        totalRows: 1,
        infoModal: {
          id: 'info-modal',
          title: '',
          content: ''
        },
        errorModal:{
          id: 'error-modal',
          title:'',
          content:''
        },
        all:[]
      }
    },
    mounted() {
    var self = this;
    self.axios
      .get("http://localhost:8081/api/pharmacist/all")
      .then(function (response) {
        for(var i = 0;i<response.data.length;i++){
          var item = {};
          item.name = response.data[i].name;
          item.lastName = response.data[i].lastName;
          item.email = response.data[i].email;
          item.phoneNumber = response.data[i].phoneNumber;
          self.items.push(item);
          item.hours = response.data[i].workHours;
          item.address = response.data[i].address.street + ", "+response.data[i].address.number+", "+response.data[i].address.city;
          self.all.push(item);
        }
         self.totalRows = self.items.length;
      });
  },
    methods: {
      onRowSelected(items) {
        this.selected = items
      },
      DeleteOne(item,button){
        var self = this;
          self.axios.delete("http://localhost:8081/api/pharmacist/"+item.email)
      .then(function(response){
         const idx = self.items.indexOf(item);
         self.items.splice(idx,1);
         console.log(response);
         self.errorModal.title = 'Success',
         self.errorModal.content = "Successfuly deleted!";
        self.$root.$emit('bv::show::modal', self.errorModal.id, button)
      }
      ).catch(function (error) {
        self.errorModal.title = 'Error',
        self.errorModal.content = "You cannot delete!";
        self.$root.$emit('bv::show::modal', self.errorModal.id, button)
           console.log(error);
        });
      },
      UpdateOne(item){
        console.log(item);
        
      },
      onFiltered(filteredItems) {
        self.totalRows = filteredItems.length

      },
       infoP(i, button) {
        var item ={};
        for(var j =0;j<this.all.length;j++){
          if(this.all[j].email == i.email){
            item.email =this.all[j].email;
            item.name = this.all[j].name;
            item.lastName = this.all[j].lastName;
            item.phoneNumber = this.all[j].phoneNumber;
            item.hours = this.all[j].hours;
            item.address = this.all[j].address;
          }
        }
        /*var r = '';
        for(var k =0;k<item.hours.length;k++){
          var from = '';
          var to ='';
            if(item.hours[k].workHourFrom == null && item.hours[k].workHourTo == null){
              from = '/';
              to = '/';
            }else if(item.hours[k].workHourFrom == null && item.hours[k].workHourTo != null){
                from = '/';
                to = item.hours[k].workHourTo;
            }else if(item.hours[k].workHourFrom != null && item.hours[k].workHourTo == null){
             from =item.hours[k].workHourFrom ;
             to = '/';
            }else{
              from =item.hours[k].workHourFrom ;
              to = item.hours[k].workHourTo;
            }
            r += "\n"+item.hours[k].day + ": " + from +"-"+ to;
            

        }*/
        this.infoModal.title = 'Info',
        this.infoModal.content = "Name: "+ item.name +"\nLast name: "+item.lastName+"\nEmail: "+item.email+
        "\nPhone number: "+item.phoneNumber+"\nAddress: "+item.address;
        this.$root.$emit('bv::show::modal', this.infoModal.id, button)
      },
       resetInfoModal() {
        this.infoModal.title = ''
        this.infoModal.content = ''
      },

      errModal(){
        this.errorModal.title = ''
        this.errorModal.content = ''
      },
    }
  }
</script>