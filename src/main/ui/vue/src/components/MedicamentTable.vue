<template>
  <main class="my-form">
    <div class="cotainer">
      <div class="row justify-content-center">
        <div class="col-md-8">
          <div class="card">
            <div class="card-header">Medicaments</div>
             <b-row>
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
             <b-col lg="8" class="my-1">
                <b-form-group
                    label-cols-sm="3"
                    label-align-sm="right"
                    label-size="sm"
                    class="mb-0">
                    <b-button  @click="AddMedicament($event.target)" variant="success">Add</b-button>
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
           </b-table>
            <b-modal size="lg" :id="infoModal.id" :title="infoModal.title" ok-only @ended="resetInfoModal" :header-bg-variant="headerBgVariant" :footer-bg-variant="headerBgVariant">
              <pre>{{ infoModal.content }}</pre>
            </b-modal>
            <b-modal :id="errorModal.id" :title="errorModal.title" ok-only @ended="errModal" :header-bg-variant="headerErrorVariant" :footer-bg-variant="headerErrorVariant">
              <pre>{{ errorModal.content }}</pre>
            </b-modal>
            <b-modal size="lg" :id="addModal.id" :title="addModal.title" ok-only @ended="resetInfoModal" :header-bg-variant="headerBgVariant" :footer-bg-variant="headerBgVariant">
              <pre> 
                   <b-table
                    :items="medicamentss" 
                    :fields="fields2" 
                    responsive="sm" 
                    small
                    :select-mode="selectMode"
                    ref="selectableTable"
                    selectable
                    @row-selected="onRowSelected">  
            ></b-table>
            </pre>
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
        fields: [{key:'id',sortable:true, label:'Id'}, {key:'name',sortable:true, label:'Name'},{key: 'type',sortable:true, label:'Type'},{key:'medicamentForm',sortable:true,label:'Medicament form'},{key:'manufacturer',sortable:true,label:'Manufacturer'},{key:'quantity', sortable:true, label:'Quantity'},'info','delete'],
        fields2: [{key:'id', label:'Id'}, {key:'name', label:'Name'},{key: 'type', label:'Type'},{key:'medicamentForm',label:'Medicament form'},{key:'manufacturer', label:'Manufacturer'}],
        items: [
        ],
        selectMode: 'single',
        sortBy:'name',
        sortDesc :false,
        filter: null,
        filterOn: [],
        totalRows: 1,
        totalRows1: 1,
        ocene :[],
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
        addModal:{
          id: 'add-modal',
          title:'',
          content:''
        },
        headerBgVariant: 'success',
        headerErrorVariant: 'warning',
        all:[],
        medicamentss:[],
        addedMedicament :{},
        quantity :0,
        pharmacy:{},
        index : 0,
        medicamentItems:[],
      }
    },
    mounted() {
    var self = this;
    self.axios
      .get("http://localhost:8081/api/pharmacy/medicamentItems/1")
      .then(function (response) {
        for(var i = 0;i<response.data.length;i++){
          var item = {};
          item.id = response.data[i].medicament.id;
          item.name = response.data[i].medicament.name;
          item.type = response.data[i].medicament.type;
          item.medicamentForm = response.data[i].medicament.form;
          item.manufacturer = response.data[i].medicament.manufacturer;
          item.quantity = response.data[i].quantity;
          self.items.push(item);
          item.medicamentItemId = response.data[i].id;
          item.structure = response.data[i].medicament.structure;
          item.issuanceMode = response.data[i].medicament.mode;
          item.annotations = response.data[i].medicament.annotation;
          item.rating = 0;
          self.all.push(item);
        }
         self.totalRows = self.items.length;
         self.GetAllRatings();
      });
    self.axios
      .get("http://localhost:8080/api/medicaments/all")
      .then(function (response) {
        for (var i = 0; i < response.data.length; i++) {
            if(self.all.filter(e=> e.id === response.data[i].id).length == 0){
                var item = {};
                item.id = response.data[i].id;
                item.name = response.data[i].name;
                item.type = response.data[i].type;
                item.annotation = response.data[i].annotations;
                item.structure = response.data[i].structure;
                item.manufacturer = response.data[i].manufacturer;
                item.medicamentForm = response.data[i].form;
                self.medicamentss.push(item);
            }
        }
        self.totalRows1 = self.medicamentss.length;
      });

      self.axios
         .get("http://localhost:8081/api/pharmacy/1")
         .then(function(response) {
             self.pharmacy.id = response.data.id;
             self.pharmacy.name = response.data.name;
        })
         .catch(function (error){ 
             console.log(error);
        });  },
    methods: {
        GetAllRatings(){
            var self = this;
            self.all.forEach((item)=>{
                self.axios.get("http://localhost:8081/api/medicaments/ratings/" +
                    parseInt(item.id))
                    .then(function(response){
                        item.rating = response.data;
                    })
            .catch((error) => console.log(error));
        })},
      onFiltered(filteredItems) {
        self.totalRows = filteredItems.length

      },

      infoP(i, button) {
        var item ={};
        for(var j =0;j<this.all.length;j++){
          if(this.all[j].id == i.id){
            item.id = this.all[j].id;
            item.name = this.all[j].name;
            item.type = this.all[j].type;
            item.form = this.all[j].form;
            item.annotations = this.all[j].annotations;
            item.rating = this.all[j].rating;
            item.manufacturer = this.all[j].manufacturer;
            item.structure = this.all[j].structure;
            item.mode = this.all[j].issuanceMode;
            item.quantity = this.all[j].quantity;
          }
        }
        this.infoModal.title = 'Info',
        this.infoModal.content = "Id: "+item.id+"\n\nName: "+item.name+"\n\nType: "+item.type+"\n\nMedicament form: "+item.form
        +"\n\nStructure: "+item.structure+"\n\nAnnotations: "+item.annotations+"\n\nManufacturer: "+item.manufacturer+"\n\nIssuance mode: "+item.mode
        +"\n\nQuantity: "+item.quantity+"\n\nRating: "+ item.rating;
        this.$root.$emit('bv::show::modal', this.infoModal.id, button)
      },
      AddMedicament(button){
           this.addModal.title = 'Add medicaments',
           this.$root.$emit('bv::show::modal', this.addModal.id, button)
      },
      onRowSelected(items) {
          this.addedMedicament = items;
          this.all.push(this.addedMedicament[0]);
          for(var k =0;k<this.all.length;k++){
            var medicamentItem ={};
            medicamentItem.id = this.all[k].medicamentItemId;
            medicamentItem.quantity = this.all[k].quantity;
            medicamentItem.deleted = false;
            var medicament ={};
            medicament.id = this.all[k].id;
            medicament.name = this.all[k].name;
            medicament.type = this.all[k].type;
            medicament.issuanceMode =  this.all[k].issuanceMode;
            medicament.medicamentForm = this.all[k].medicamentForm;
            medicament.structure =  this.all[k].structure;
            medicament.annotation =  this.all[k].annotations;
            medicament.manufacturer =  this.all[k].manufacturer;
            medicamentItem.medicament = medicament;
            this.medicamentItems.push(medicamentItem);
          }
        this.addedMedicament = {};
        console.log(this.medicamentItems);
        this.axios.put("http://localhost:8081/api/pharmacy/updateMedicaments/1",{
                name: this.pharmacy.name,
                id: this.pharmacy.id,
                medicaments : this.medicamentItems
            });
            this.medicamentItems = [];
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