<template>
	<div>
		<FullCalendar scheduler-license-key="GPL-My-Project-Is-Open-Source"
		              ref="fullCalendar"
		              style="padding: 25px;padding-top: 0px"
		              defaultView="resourceTimelineDay"
		              :editable=false
		              :plugins="calendarPlugins"
		              :minTime="'09:00'"
		              :maxTime="'22:00'"
		              :header="{
					      left: 'prev,next',
					      center: 'title',
					      right: 'resourceTimelineDay,resourceTimelineWeek'
					  }"
		              :titleFormat="{
		                  year: 'numeric', month: 'short', day: 'numeric'
		              }"
		              :themeSystem="'bootstrap'"
		              :datesRender="dateRender"
		              :height="480"
		              :resources="resources"
		              :resourceColumns="[
					      {
					        labelText: 'Room',
					        field: 'title'
					      },
					      {
					        labelText: 'Capacity',
					        field: 'capacity'
					      }
					    ]"
		              :events="events"
		
		/>
	
	</div>
</template>

<script>
    import FullCalendar from "@fullcalendar/vue"
    import resourceTimelinePlugin from '@fullcalendar/resource-timeline'
    import bootstrapPlugin from '@fullcalendar/bootstrap';
    import interactionPlugin from '@fullcalendar/interaction';
    import formatDate from '../assets/utils/formatDate'

    export default {

        name: "calendar",
        components: {
            FullCalendar // make the <FullCalendar> tag available
        },
        data() {
            return {
                calendarPlugins: [resourceTimelinePlugin, bootstrapPlugin, interactionPlugin],
                activeStart: null,
                activeEnd: null,
                resources: [],
                events: []
            }
        },
        methods: {
            dateRender(info) {
                let calendarApi = this.$refs.fullCalendar.getApi();
                let activeStart = new Date(Date.parse(info.view.activeStart));
                let activeEnd = new Date(Date.parse(info.view.activeEnd));
                this.activeStart = activeStart;
                this.activeEnd = activeEnd;
                this.getResources();
                calendarApi.refetchResources();
                this.getEvents();
                calendarApi.refetchEvents();
            },
            getResources() {
                this.axios({
                    url: '/api/roomInfo/roomResources',
                    method: "GET"
                }).then((res) => {
                    if (res.data.code === 200) {
                        this.resources = res.data.data;
                    } else {
                        this.resources = [];
                    }
                }).catch((err) => {
                    this.$messageUtil.errorMessage(this);
                });
            },
            getEvents() {
                this.axios({
                    url: '/api/booking/calendar/events',
                    method: "GET",
                    params: {
                        startTime: this.activeStart,
                        endTime: this.activeEnd
                    }
                }).then((res) => {
                    if (res.data.code === 200) {
                        this.events = res.data.data;
                    } else {
                        this.events = []
                    }
                }).catch((err) => {
                    this.$messageUtil.errorMessage(this);
                })
            }

        },
        mounted() {
            this.getResources();
            this.getEvents();
        }
    }
</script>

<style scoped>

</style>

<style lang='scss'>
	
	@import '~@fullcalendar/core/main.css';
	@import '~@fullcalendar/timeline/main.css';
	@import '~@fullcalendar/resource-timeline/main.css';
	@import '~@fullcalendar/bootstrap/main.css';
	@import '~@fullcalendar/daygrid/main.css';
	@import url('https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css');
	@import url('https://use.fontawesome.com/releases/v5.0.6/css/all.css');


</style>
