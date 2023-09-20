<template>
  <Table
    title="Dermatologists"
    :addModal="addModal"
    :dermatologistss="dermatologistss"
    :fields2="fields2"
    :isHidden="isHidden"
    :items="items"
    :fields="fields"
    :infoModal="infoModal"
    :errorModal="errorModal"
    :hours="hh"
    :fields3="fields3"
    @InfoP="InfoP"
    @DeleteOne="DeleteOne"
    @AddDermatologist="AddDermatologist"
    @OnRowSelected="OnRowSelected"
    @onSubmit ="onSubmit"
    @check="check"
    @Filter="Filter"
  ></Table>
</template>
<script>
import { defineComponent } from "@vue/composition-api";
import Table from "../components/Table";
export default defineComponent({
  name: "DermatologistTable",
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
      addModal: {
        id: "add-modal",
        title: "",
        content: "",
      },
      isHidden: false,
      dermatologistss: [],
      fields2: [
        { key: "name", label: "Name" },
        { key: "lastName", label: "Last name" },
        { key: "email", label: "Email" },
      ],
      hh: [
        { day: "Monday" },
        { day: "Tuesday" },
        { day: "Wednesday" },
        { day: "Thursday" },
        { day: "Friday" },
        { day: "Saturday" },
        { day: "Sunday" },
      ],
      fields3: [
        { key: "day", label: "Day" },
        { key: "workHourFrom", label: "Work hour from" },
        { key: "workHourTo", label: "Work hour to" },
      ],
      selected: {},
      hours: [],
      pharmacyId: 0,
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
          Authorization: "Bearer " + localStorage.getItem('token'),
        },
      })
      .then(function (response) {
        self.pharmacyId = response.data.pharmacy.id;
        self.GetDermatologists();
      })
      .catch(function (error) {
        console.log(error);
      });
  },
  methods: {
    GetDermatologists() {
      var self = this;
      self.axios
        .get(process.env.VUE_APP_API_URL+`/pharmacy/dermatologists/` +
            parseInt(self.pharmacyId),
          {
            headers: {
              Authorization: "Bearer " + localStorage.getItem('token'),
            },
          }
        )
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
              self.items.push(item);
              item.hours = response.data[i].workHours;
              item.address =
                response.data[i].address.street +
                " " +
                response.data[i].address.number +
                ", " +
                response.data[i].address.city;
              item.rating = response.data[i].rating;
              self.all.push(item);
            }
            
          }
          self.getAll();
          //self.totalRows = self.items.length;
        });
    },
    getAll() {
      var self = this;
      self.axios
        .get(process.env.VUE_APP_API_URL+`/dermatologist/all`, {
          headers: {
            Authorization: "Bearer " + localStorage.getItem('token'),
          },
        })
        .then(function (response) {
          //console.log(response.data);
          for (var i = 0; i < response.data.length; i++) {
            if (
              self.items.filter((e) => e.email === response.data[i].email)
                .length == 0
            ) {
              var item = {};
              item.name = response.data[i].name;
              item.lastName = response.data[i].lastName;
              item.email = response.data[i].email;
              item.phoneNumber = response.data[i].phoneNumber;
              item.hours = response.data[i].workHours;
              item.address =
                response.data[i].address.street +
                " " +
                response.data[i].address.number +
                ", " +
                response.data[i].address.city;
              self.dermatologistss.push(item);
            }
          }
          //self.totalRows = self.items.length;
        });
    },
    AddDermatologist(button) {
      (this.addModal.title = "Add dermatologists"),
        this.$root.$emit("bv::show::modal", this.addModal.id, button);
    },
    OnRowSelected(item) {
      this.selected = item[0];
    },
    check(item) {
      var self = this;
      const idx = this.dermatologistss.indexOf(self.selected);
      if (idx != -1) {
        //self.dermatologistss.splice(idx, 1);
        self.axios.put(process.env.VUE_APP_API_URL + `/dermatologist/updateDermatologist/` +
            self.selected.email,
          {
            email: self.selected.email,
            pharmacies: [
              {
                id: parseInt(self.pharmacyId),
              },
            ],
            workHours: [
              {
                day: item[0].day,
                workHourFrom: item[0].workHourFrom,
                workHourTo: item[0].workHourTo,
                deleted: false,
                pharmacy: {
                  id: parseInt(self.pharmacyId),
                },
              },
              {
                day: item[1].day,
                workHourFrom: item[1].workHourFrom,
                workHourTo: item[1].workHourTo,
                deleted: false,
                pharmacy: {
                  id: parseInt(self.pharmacyId),
                },
              },
              {
                day: item[2].day,
                workHourFrom: item[2].workHourFrom,
                workHourTo: item[2].workHourTo,
                deleted: false,
                pharmacy: {
                  id: parseInt(self.pharmacyId),
                },
              },
              {
                day: item[3].day,
                workHourFrom: item[3].workHourFrom,
                workHourTo: item[3].workHourTo,
                deleted: false,
                pharmacy: {
                  id: parseInt(self.pharmacyId),
                },
              },
              {
                day: item[4].day,
                workHourFrom: item[4].workHourFrom,
                workHourTo: item[4].workHourTo,
                deleted: false,
                pharmacy: {
                  id: parseInt(self.pharmacyId),
                },
              },
              {
                day: item[5].day,
                workHourFrom: item[5].workHourFrom,
                workHourTo: item[5].workHourTo,
                deleted: false,
                pharmacy: {
                  id: parseInt(self.pharmacyId),
                },
              },
              {
                day: item[6].day,
                workHourFrom: item[6].workHourFrom,
                workHourTo: item[6].workHourTo,
                deleted: false,
                pharmacy: {
                  id: parseInt(self.pharmacyId),
                },
              },
            ],
          },
          {
            headers: {
              Authorization: "Bearer " + localStorage.getItem('token'),
            },
          }
        ).then(function (response){
          console.log(response);
          self.dermatologistss.splice(idx, 1);
          self.items.push(self.selected);
        }).catch(function(error){
          console.log(error);
          self.hh = [];
          self.hh.push({day:"Monday"});
          self.hh.push({day:"Tuesday"});
          self.hh.push({ day: "Wednesday" });
          self.hh.push({ day: "Thursday" });
          self.hh.push({ day: "Friday" });
          self.hh.push({ day: "Saturday" });
          self.hh.push({ day: "Sunday" });
          
          (self.errorModal.title = "Error"),
          (self.errorModal.content = "You cannot add dermatologist with that work hour!");
          self.errorModal.varning = "warning";
          self.$root.$emit("bv::show::modal", self.errorModal.id);
          

        });
       self.axios.get(process.env.VUE_APP_API_URL+`/dermatologist/ratings/` + self.selected.email, {
            headers: {
              Authorization: "Bearer " + localStorage.getItem('token'),
            },
          }).then(function(response){
            self.selected.rating = response.data;
          });
      }
      
    },

    DeleteOne(item, button) {
      var self = this;
      self.axios
        .delete(process.env.VUE_APP_API_URL+`/dermatologist/` +
            item.email +
            "/" +
            parseInt(self.pharmacyId),
          {
            headers: {
              Authorization: "Bearer " + localStorage.getItem('token'),
            },
          }
        )
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
      for (var j = 0; j < this.items.length; j++) {
        if (this.items[j].email == i.email) {
          item.email = this.items[j].email;
          item.name = this.items[j].name;
          item.lastName = this.items[j].lastName;
          item.phoneNumber = this.items[j].phoneNumber;
          item.hours = this.items[j].hours;
          item.address = this.items[j].address;
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
