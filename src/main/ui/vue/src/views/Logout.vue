<template>
  <p>redirecting...</p>
</template>

<script>
import { defineComponent } from '@vue/composition-api'

export default defineComponent({
  name: "Logout",
  mounted(){
    localStorage.removeItem("token");
    this.$router.push("/Login");
    //localStorage.removeItem('token');
    //this.$router.push("/MedicamentList");
    }
})
</script>
