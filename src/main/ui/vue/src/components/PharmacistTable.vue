<template>
  <Table
    title="Pharmacists"
    :addModal="addModal"
    :isHidden="isHidden"
    :items="items"
    :fields="fields"
    :infoModal="infoModal"
    :errorModal="errorModal"
    @DeleteOne="DeleteOne"
    @InfoP="InfoP"
    @onSubmit="onSubmit"
    @Filter="Filter"
  ></Table>
</template>
<script>
import { defineComponent } from "@vue/composition-api";
import Table from "../components/Table";
export default defineComponent({
  name: "PharmacistTable",
  components: {
    Table,
  },
  data() {
    return {
      items: [],
      all: [],
      fields: [
        { key: "name", sortable: true, label: "Name" },
        { key: "lastName", sortable: true, label: "Last name" },
        { key: "email", sortable: true, label: "Email" },
        { key: "rating", sortable: true, label: "Rating" },
        "info",
        "delete",
      ],
      sortBy: "name",
      sortDesc: false,
      filter: null,
      filterOn: [],
      totalRows: 1,
      infoModal: {
        id: "info-modal",
        title: "",
        content: "",
      },
      errorModal: {
        id: "error-modal",
        title: "",
        content: "",
        varning: "",
      },
      isHidden: true,
      addModal: {
        id: "add-modal",
        title: "",
        content: "",
        pharmacyId: 0,
        vall: false,
      },
    };
  },
  mounted() {
    var AdminUsername = JSON.parse(
      atob(localStorage.getItem("token").split(".")[1])
    ).sub;
    var self = this;
    self.axios
      .get(process.env.VUE_APP_API_URL + `/pharmacyAdmin/` + AdminUsername, {
        headers: {
          Authorization: "Bearer " + localStorage.getItem("token"),
        },
      })
      .then(function (response) {
        self.pharmacyId = response.data.pharmacy.id;
        self.GetPharmacists();
      })
      .catch(function (error) {
        console.log(error);
      });
  },
  methods: {
    GetPharmacists() {
      var self = this;
      self.axios
        .get(process.env.VUE_APP_API_URL + `/pharmacy/pharmacists/` + parseInt(self.pharmacyId), {
          headers: {
            Authorization: "Bearer " + localStorage.getItem("token"),
          },
        })
        .then(function (response) {
          for (var i = 0; i < response.data.length; i++) {
            var count = 0;
            for (var j = 0; j < response.data[i].workHours.length; j++) {
              if (response.data[i].workHours[j].deleted) {
                count++;
              }
            }
            if (count != 7) {
              var item = {};
              item.name = response.data[i].name;
              item.lastName = response.data[i].lastName;
              item.email = response.data[i].email;
              item.phoneNumber = response.data[i].phoneNumber;
              item.rating = response.data[i].rating;
              self.items.push(item);
              item.hours = response.data[i].workHours;
              item.address =
                response.data[i].address.street +
                " " +
                response.data[i].address.number +
                ", " +
                response.data[i].address.city;
              self.all.push(item);
            }
          }
          //self.totalRows = self.items.length;
        });
    },
    DeleteOne(item, button) {
      var self = this;
      self.axios
        .delete(process.env.VUE_APP_API_URL + `/pharmacist/` + item.email, {
          headers: {
            Authorization: "Bearer " + localStorage.getItem("token"),
          },
        })
        .then(function (response) {
          const idx = self.items.indexOf(item);
          self.items.splice(idx, 1);
          console.log(response);
          (self.errorModal.title = "Success"),
            (self.errorModal.content = "Successfuly deleted!");
          self.errorModal.varning = "success";
          self.$root.$emit("bv::show::modal", self.errorModal.id, button);
        })
        .catch(function (error) {
          (self.errorModal.title = "Error"),
            (self.errorModal.content = "You cannot delete!");
          self.errorModal.varning = "warning";
          self.$root.$emit("bv::show::modal", self.errorModal.id, button);
          console.log(error);
        });
    },
    InfoP(i, button) {
      var item = {};
      for (var j = 0; j < this.all.length; j++) {
        if (this.all[j].email == i.email) {
          item.email = this.all[j].email;
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
      (this.infoModal.title = "Info"),
        (this.infoModal.content =
          "Name: " +
          item.name +
          "\nLast name: " +
          item.lastName +
          "\nEmail: " +
          item.email +
          "\nPhone number: " +
          item.phoneNumber +
          "\nAddress: " +
          item.address);
      this.$root.$emit("bv::show::modal", this.infoModal.id, button);
    },
    resetInfoModal() {
      this.infoModal.title = "";
      this.infoModal.content = "";
    },

    errModal() {
      this.errorModal.title = "";
      this.errorModal.content = "";
    },
    onSubmit(name, lastname) {
      var self = this;
      var filtered = [];
      for (var i = 0; i < self.all.length; i++) {
        if (
          self.all[i].name.toLowerCase().includes(name.toLowerCase()) &&
          self.all[i].lastName.toLowerCase().includes(lastname.toLowerCase())
        ) {
          filtered.push(self.all[i]);
        }
      }
      self.items = filtered;
    },
    Filter(r) {
      var self = this;
      var filtered = [];
      for (var i = 0; i < self.all.length; i++) {
        if (
          self.all[i].rating == r
        ) {
          filtered.push(self.all[i]);
        }
      }
      self.items = filtered;
    },
  },
});
</script>
