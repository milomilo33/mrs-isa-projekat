<template>
    <div>
        <b-container>
            <h1>Request days off</h1>

            <b-row class="text-center">
                <b-table striped hover :items="requests" :fields="requestsFields" caption-top v-if="requests.length > 0">
                    <template #table-caption>Requests</template>
                </b-table>
            </b-row>
            <b-row class="text-center">
                <b-col></b-col>
                <b-col cols="10">
                    <div style="margin:25px;">
                        <b-form-textarea
                            id="textarea"
                            v-model="description"
                            placeholder="Description..."
                            rows="10"
                            max-rows="20"
                        ></b-form-textarea>
                    </div>
                </b-col>
                <b-col></b-col>
            </b-row>

            <b-row class="text-center">
                <b-col></b-col>
                <b-col cols="3">
                        <label></label>
                        <label for="dd"> Date of leave: </label>
                        <div>
                        <b-form-datepicker
                            v-model="dateFrom"
                            :min="minDate"
                            locale="en"
                            class="mb-2"
                        ></b-form-datepicker>
                        </div>
                        <label></label>
                        <label for="dd"> Date of return: </label>
                        <b-form-datepicker
                            v-model="dateTo"
                            :min="minDate"
                            locale="en"
                            class="mb-2"
                        ></b-form-datepicker>
                </b-col>
                <b-col></b-col>
            </b-row>

            <b-row class="text-center" style="margin-top: 25px;">
                <b-col></b-col>
                <b-col cols="3">
                    <div style="text-align:center;">
                        <b-button variant="secondary" @click="onSendRequest">Send request</b-button>
                    </div>
                </b-col>
                <b-col></b-col>
            </b-row>

            <b-modal ref="error-modal" hide-footer title="Error">
                <div class="d-block text-center">
                    <p>{{ this.errorMessage }}</p>
                </div>
                <b-button class="mt-3" variant="outline-danger" block @click="hideErrorModal">Close</b-button>
            </b-modal>
            
            <b-modal ref="success-modal" hide-footer title="Success">
                <div class="d-block text-center">
                    <p>Request successfully submitted.</p>
                </div>
                <b-button class="mt-3" variant="outline-success" block @click="hideSuccessModal">Close</b-button>
            </b-modal>
        </b-container>
    </div>
</template>

<script>

export default {
    name: 'RequestDaysOff',

    data() {
        return {
            dateFrom: "",
            dateTo: "",
            description: "",
            minDate: new Date(),
            requests: [],
            requestsFields: [
                {
                    key: 'dateFrom',
                    headerTitle: 'Date of leave',
                    label: 'Date of leave'
                },
                {
                    key: 'dateTo',
                    headerTitle: 'Date of return',
                    label: 'Date of return'
                },
                {
                    key: 'status',
                    headerTitle: 'Status',
                    label: 'Status'
                }
            ],
            errorMessage: "",
            type: ""
        }
    },

    methods: {
        loadRequests() {
            this.axios.get(`/api/request/${this.type}`,  {
                headers: {
                    Authorization: "Bearer " + localStorage.getItem('token'),
                },
            })
            .then((response) => {
                let requests = response.data;
                requests = requests.map((request) => {
                    let status = '';
                    if (request.accepted)
                        status = 'APPROVED';
                    else {
                        if (request.rejectionReason)
                            status = 'REJECTED';
                        else
                            status = 'PENDING';
                    }
                    return {
                        ...request,
                        status
                    };
                });

                // date conversion
                requests.forEach((obj) => {
                    obj["dateFrom"] = new Date(obj["dateFrom"]).toLocaleDateString();
                    obj["dateTo"] = new Date(obj["dateTo"]).toLocaleDateString();
                });

                this.requests = requests;
            })
            .catch(error => {
                console.log(error);
                // this.showErrorModal();
            });
        },

        onSendRequest() {
            let body = {
                dateFrom: this.dateFrom,
                dateTo: this.dateTo,
                description: this.description
            };

            this.axios.post(`/api/request/${this.type}`, body, {
                    headers: {
                        Authorization: "Bearer " + localStorage.getItem('token'),
                    },
                })
            .then(() => {
                this.showSuccessModal();
                this.loadRequests();
            })
            .catch(error => {
                this.errorMessage = error.response.data;
                this.showErrorModal();
            });
        },

        hideErrorModal() {
            this.$refs['error-modal'].hide()
        },

        hideSuccessModal() {
            this.$refs['success-modal'].hide()
        },

        showErrorModal() {
            this.$refs['error-modal'].show()
        },

        showSuccessModal() {
            this.$refs['success-modal'].show()
        },
    },

    mounted() {
        this.type = this.$route.query.type;
        this.loadRequests();
    }
}

</script>