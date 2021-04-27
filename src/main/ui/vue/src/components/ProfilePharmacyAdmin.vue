<template>
  <Profile  v-if="ret" :user="user"></Profile>
</template>
<script>
import { defineComponent } from '@vue/composition-api'
import Profile from '../components/Profile'
export default defineComponent({
  name: 'ProfilePharmacyAdmin',
  components: {
    Profile,
  },
  data() {
    return {
      user: {},
      ret: false,
   }
  },
  mounted(){
    
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
        self.user.email = response.data.email;
        self.user.name = response.data.name;
        self.user.lastName = response.data.lastName;
        self.user.country = response.data.address.country;
        self.user.city = response.data.address.city;
        self.user.street = response.data.address.street;
        self.user.phoneNumber = response.data.phoneNumber;
        self.user.number = response.data.address.number;
        self.ret = true;
        console.log(self.user);
        //self.GetPharmacists();
      })
      .catch(function (error) {
        console.log(error);
      });
  },
  onSubmit(u){
    console.log(u)
  }
  
})
</script>
