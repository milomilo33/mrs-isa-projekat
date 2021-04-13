<template>
  <main class="my-form">
    <div class="cotainer">
      <div class="row justify-content-center">
        <div class="col-md-8">
          <div class="card">
            <div class="card-header">{{ title }}</div>
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
                    v-if="!isHidden"
                    @click="$emit('AddDermatologist', $event.target)"
                    variant="success"
                    >Add</b-button
                  >
                </b-form-group>
              </b-col>
            </b-row>
            <b-table
              :items="items"
              :fields="fields"
              responsive="sm"
              :sort-by.sync="sortBy"
              :sort-desc.sync="sortDesc"
              :filter="filter"
              :filter-included-fields="filterOn"
              small
              ref="table"
              @filtered="$emit('onFiltered')"
            >
              <template v-slot:cell(info)="data">
                <b-button
                  size="sm"
                  @click="$emit('InfoP', data.item, $event.target)"
                  class="mr-1"
                  variant="light"
                >
                  Show details
                </b-button>
              </template>
              <template v-slot:cell(delete)="data">
                <b-button
                  size="sm"
                  @click="$emit('DeleteOne', data.item, $event.target)"
                  class="mr-1"
                  variant="danger"
                >
                  Delete
                </b-button>
              </template>
              <template v-slot:cell(update)="data">
                <b-button
                  size="sm"
                  @click="$emit('UpdateOne', data.item)"
                  class="mr-1"
                  variant="info"
                >
                  Edit
                </b-button>
              </template>
            </b-table>
            <b-modal
              :id="infoModal.id"
              :title="infoModal.title"
              ok-only
              @ended="$emit('resetInfoModal')"
              :header-bg-variant="headerBgVariant"
              footer-bg-variant="light"
            >
              <pre>{{ infoModal.content }}</pre>
            </b-modal>
            <b-modal
              :id="errorModal.id"
              :title="errorModal.title"
              ok-only
              @ended="$emit('errModal')"
              :header-bg-variant="errorModal.varning"
              footer-bg-variant="light"
            >
              <pre>{{ errorModal.content }}</pre>
            </b-modal>
            <b-modal
              size="lg"
              :id="addModal.id"
              :title="addModal.title"
              @ok="check"
              @ended="$emit('resetInfoModal')"
              :header-bg-variant="headerBgVariant"
              :footer-bg-variant="headerBgVariant"
            >
              <pre> 
                   <b-table
                    :items="dermatologistss" 
                    :fields="fields2" 
                    responsive="sm" 
                    small
                    select-mode="single"
                    ref="selectableTable"
                    selectable
                    @row-selected="onRowSelected">  
            ></b-table>

             <b-table
                :items="hours" 
                :fields="fields3"
                :head-variant="headerBgVariant" 
                responsive="sm" 
                bordered
                small>
                <template v-slot:cell(workhourfrom)="data">
                <b-form-timepicker v-model="data.item.workHourFrom"  locale="en" :required="rec"></b-form-timepicker>
                </template>
                <template v-slot:cell(workhourto)="data">
                <b-form-timepicker v-model="data.item.workHourTo"  locale="en"></b-form-timepicker>
                </template>        
            </b-table>
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
  name: "Table",
  components: {},
  data() {
    return {
      rec: true,
      sortBy: "name",
      sortDesc: false,
      filter: null,
      filterOn: [],
      totalRows: 1,
      all: [],
      headerBgVariant: "success",
      headerErrorVariant: "danger",
      hours: [
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
    };
  },
  props: {
    items: Array,
    fields: Array,
    errorModal: {},
    infoModal: {},
    title: String,
    isHidden: Boolean,
    addModal: {},
    dermatologistss: Array,
    fields2: Array,
  },
  methods: {
    onRowSelected(item) {
      this.$emit("OnRowSelected", item);
      this.$refs.table.refresh();
    },

    check() {
      this.$emit("check", this.hours);
      this.hours = [];
    },
  },
};
</script>

