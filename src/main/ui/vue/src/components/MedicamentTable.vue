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
                  <b-button
                    @click="AddMedicament($event.target)"
                    variant="success"
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
              :filter-included-fields="filterOn"
              small
              @filtered="onFiltered"
            >
              <template v-slot:cell(info)="data">
                <b-button
                  size="sm"
                  @click="infoP(data.item, $event.target)"
                  class="mr-1"
                  variant="light"
                >
                  Show details
                </b-button>
              </template>
              <template v-slot:cell(delete)="data">
                <b-button
                  size="sm"
                  @click="DeleteOne(data.item, $event.target)"
                  class="mr-1"
                  variant="danger"
                >
                  Delete
                </b-button>
              </template>
            </b-table>
            <b-modal
              size="lg"
              :id="infoModal.id"
              :title="infoModal.title"
              ok-only
              @ended="resetInfoModal"
              :header-bg-variant="headerBgVariant"
              :footer-bg-variant="headerBgVariant"
            >
              <pre>{{ infoModal.content }}</pre>
            </b-modal>
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
              :footer-bg-variant="headerBgVariant"
            >
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
      fields: [
        { key: "id", sortable: true, label: "Id" },
        { key: "name", sortable: true, label: "Name" },
        { key: "type", sortable: true, label: "Type" },
        { key: "medicamentForm", sortable: true, label: "Medicament form" },
        { key: "manufacturer", sortable: true, label: "Manufacturer" },
        { key: "quantity", sortable: true, label: "Quantity" },
        "info",
        "delete",
      ],
      fields2: [
        { key: "id", label: "Id" },
        { key: "name", label: "Name" },
        { key: "type", label: "Type" },
        { key: "medicamentForm", label: "Medicament form" },
        { key: "manufacturer", label: "Manufacturer" },
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
      infoModal: {
        id: "info-modal",
        title: "",
        content: "",
      },
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
      all: [],
      medicamentss: [],
      old: [],
      quantity: 0,
      pharmacy: {},
      selected: [],
      medicamentItems: [],
    };
  },
  mounted() {
    var self = this;
    self.axios
      .get("http://localhost:8081/api/pharmacy/medicamentItems/1")
      .then(function (response) {
        for (var i = 0; i < response.data.length; i++) {
          var item = {};
          item.id = response.data[i].medicament.id;
          item.name = response.data[i].medicament.name;
          item.type = response.data[i].medicament.type;
          item.medicamentForm = response.data[i].medicament.form;
          item.manufacturer = response.data[i].medicament.manufacturer;
          item.quantity = response.data[i].quantity;
          item.medicamentItemId = response.data[i].id;
          item.structure = response.data[i].medicament.structure;
          item.issuanceMode = response.data[i].medicament.mode;
          item.annotation = response.data[i].medicament.annotation;
          item.rating = 0;
          self.items.push(item);
          self.old.push(item);
          //self.all.push(item);
        }
        self.totalRows = self.items.length;
        self.GetAllRatings();
      });
    self.axios
      .get("http://localhost:8080/api/medicaments/all")
      .then(function (response) {
        for (var i = 0; i < response.data.length; i++) {
          if (
            self.items.filter((e) => e.id === response.data[i].id).length == 0
          ) {
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
        }
        //self.totalRows1 = self.medicamentss.length;
      });
    self.axios
      .get("http://localhost:8081/api/pharmacy/1")
      .then(function (response) {
        self.pharmacy.id = response.data.id;
        self.pharmacy.name = response.data.name;
      })
      .catch(function (error) {
        console.log(error);
      });
  },
  methods: {
    GetAllRatings() {
      var self = this;
      self.items.forEach((item) => {
        self.axios
          .get(
            "http://localhost:8081/api/medicaments/ratings/" + parseInt(item.id)
          )
          .then(function (response) {
            item.rating = response.data;
          })
          .catch((error) => console.log(error));
      });
    },
    onFiltered(filteredItems) {
      self.totalRows = filteredItems.length;
    },

    infoP(i, button) {
      var item = {};
      for (var j = 0; j < this.items.length; j++) {
        if (this.items[j].id == i.id) {
          item.id = this.items[j].id;
          item.name = this.items[j].name;
          item.type = this.items[j].type;
          item.form = this.items[j].medicamentForm;
          item.annotation = this.items[j].annotation;
          item.rating = this.items[j].rating;
          item.manufacturer = this.items[j].manufacturer;
          item.structure = this.items[j].structure;
          item.mode = this.items[j].issuanceMode;
          item.quantity = this.items[j].quantity;
        }
      }
      (this.infoModal.title = "Info"),
        (this.infoModal.content =
          "Id: " +
          item.id +
          "\n\nName: " +
          item.name +
          "\n\nType: " +
          item.type +
          "\n\nMedicament form: " +
          item.form +
          "\n\nStructure: " +
          item.structure +
          "\n\nAnnotations: " +
          item.annotation +
          "\n\nManufacturer: " +
          item.manufacturer +
          "\n\nIssuance mode: " +
          item.mode +
          "\n\nQuantity: " +
          item.quantity +
          "\n\nRating: " +
          item.rating);
      this.$root.$emit("bv::show::modal", this.infoModal.id, button);
    },
    AddMedicament(button) {
      (this.addModal.title = "Add medicaments"),
        this.$root.$emit("bv::show::modal", this.addModal.id, button);
    },
    onRowSelected(item) {
      this.selected = item;
      const idx = this.medicamentss.indexOf(item[0]);
      if (idx != -1) {
        this.medicamentss.splice(idx, 1);
        var i = {};
        i.annotation = this.selected[0].annotation;
        i.id = this.selected[0].id;
        i.issuanceMode = this.selected[0].issuanceMode;
        i.manufacturer = this.selected[0].manufacturer;
        i.medicamentForm = this.selected[0].medicamentForm;
        i.name = this.selected[0].name;
        i.structure = this.selected[0].structure;
        i.type = this.selected[0].type;
        i.quantity = 0;
        this.items.push(i);
        for (var k = 0; k < this.items.length; k++) {
          var medicamentItem = {};
          //medicamentItem.id = this.all[k].medicamentItemId;
          medicamentItem.quantity = this.items[k].quantity;
          medicamentItem.deleted = false;
          var medicament = {};
          medicament.id = this.items[k].id;
          medicament.name = this.items[k].name;
          medicament.type = this.items[k].type;
          medicament.issuanceMode = this.items[k].issuanceMode;
          medicament.medicamentForm = this.items[k].medicamentForm;
          medicament.structure = this.items[k].structure;
          medicament.annotation = this.items[k].annotation;
          medicament.manufacturer = this.items[k].manufacturer;
          medicamentItem.medicament = medicament;
          this.medicamentItems.push(medicamentItem);
        }
        this.selected = [];
        this.GetAllRatings();
        for(var l =0;l<this.medicamentItems.length;l++){
           if (this.old.filter((e) => e.id === this.medicamentItems[l].medicament.id).length == 0){
                this.new.push(this.medicamentItems[l]);
           }
        }
        this.axios.put(
          "http://localhost:8081/api/pharmacy/updateMedicaments/1",
          {
            name: this.pharmacy.name,
            id: this.pharmacy.id,
            medicaments: this.new,
          }
        );
        this.medicamentItems = [];
        this.$refs.table.refresh();
      }
    },

    resetInfoModal() {
      this.infoModal.title = "";
      this.infoModal.content = "";
    },

    errModal() {
      this.errorModal.title = "";
      this.errorModal.content = "";
    },

    DeleteOne(item, button) {
      var self = this;
      console.log(item.medicamentItemId);
      self.axios
        .delete("http://localhost:8081/api/medicamentItems/" + item.medicamentItemId)
        .then(function (response) {
          const idx = self.items.indexOf(item);
          self.items.splice(idx, 1);
          console.log(response);
          (self.errorModal.title = "Success"),
            (self.errorModal.content = "Successfuly deleted!");
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