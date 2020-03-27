<template>
	<div>
		<FullCalendar scheduler-license-key="GPL-My-Project-Is-Open-Source"
		              defaultView="resourceTimelineDay"
		              editable="true"
		              :editable=true
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
		              :height="500"
		              :resources= "'https://fullcalendar.io/demo-resources.json'"
		              :events="'https://fullcalendar.io/demo-events.json?single-day&for-resource-timeline'"
		
		/>
	
	</div>
</template>

<script>
    import FullCalendar from "@fullcalendar/vue"
    import resourceTimelinePlugin from '@fullcalendar/resource-timeline'
    import bootstrapPlugin from '@fullcalendar/bootstrap';
    import interactionPlugin from '@fullcalendar/interaction';

    export default {

        name: "calendar",
        components: {
            FullCalendar // make the <FullCalendar> tag available
        },
        data() {
            return {
                calendarPlugins: [resourceTimelinePlugin, bootstrapPlugin, interactionPlugin]
            }
        },
        methods: {
            dateRender(info) {

            }
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
