<template>
    <main class="my-form">
       <div class="cotainer">
           <div class="row justify-content-center">
               <div class="col-md-8">
                       <div class="card">
                           <div class="card-header">Employees</div>
                                <b-table
                                    :items="items"
                                    :fields="fields"
                                    :select-mode="selectMode"
                                    responsive="sm"
                                    ref="selectableTable"
                                    selectable
                                    @row-selected="onRowSelected">
                                </b-table>
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
        fields: ['Name', 'First name', 'Email', 'Phone number', 'Employee',''],
        items: [
          { isActive: true, age: 40, first_name: 'Dickerson', last_name: 'Macdonald' },
          { isActive: false, age: 21, first_name: 'Larsen', last_name: 'Shaw' },
          { isActive: false, age: 89, first_name: 'Geneva', last_name: 'Wilson' },
          { isActive: true, age: 38, first_name: 'Jami', last_name: 'Carney' }
        ],
        selectMode: 'single',
        selected: []
      }
    },
    methods: {
      onRowSelected(items) {
        this.selected = items
      },
    }
  }
</script>
<style>
my-form {
  padding-top: 0rem;
  padding-bottom: 1rem;
}
.my-form .card-header {
  background-color: powderblue;
  text-align: center;
}
</style>
