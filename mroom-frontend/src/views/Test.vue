<template>
	<div>
		<el-button @click="testRoute">
		</el-button>
		

	</div>
	
</template>

<script>
    import FullCalendar from "@fullcalendar/vue"
    import resourceTimelinePlugin from '@fullcalendar/resource-timeline'
	import adminRouter from "../router/AdminRouter";
    export default {

        name: "Test",
        components: {
            FullCalendar // make the <FullCalendar> tag available
        },
        data() {
            return {
                calendarPlugins: [ resourceTimelinePlugin ]
            }
        },
	    methods: {
            handleOpen(key, keyPath) {
                console.log(key, keyPath);
            },
            handleClose(key, keyPath) {
                console.log(key, keyPath);
            },
            testRoute()
            {
                localStorage.setItem("routes",JSON.stringify(adminRouter));
                this.$router.addRoutes(adminRouter);
	            this.$router.options.routes = adminRouter;
	            this.$router.push("/registry");
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
	
</style>
