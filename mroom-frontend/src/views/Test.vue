<template>
	<div>
		<div class='tableauPlaceholder' id='viz1587449784753' style='position: relative;z-index: 9999;border: 1px solid black;height: 500px'>
			<noscript>
				<a href='https://www.tableau.com/covid-19-coronavirus-data-resources'>
					<img alt=' '
					     src='https://public.tableau.com/static/images/CO/COVID-19Cases_15840488375320/COVID-19Cases/1_rss.png'
					     style='border: none'/>
				</a>
			</noscript>
			<object class='tableauViz' style='display:none;'>
				<param name='host_url' value='https%3A%2F%2Fpublic.tableau.com%2F'/>
				<param name='embed_code_version' value='3'/>
				<param name='site_root' value=''/>
				<param name='name' value='COVID-19Cases_15840488375320/COVID-19Cases'/>
				<param name='tabs' value='no'/>
				<param name='toolbar' value='yes'/>
				<param name='static_image'
				       value='https://public.tableau.com/static/images/CO/COVID-19Cases_15840488375320/COVID-19Cases/1.png'/>
				<param name='animate_transition' value='yes'/>
				<param name='display_static_image' value='yes'/>
				<param name='display_spinner' value='yes'/>
				<param name='display_overlay' value='yes'/>
				<param name='display_count' value='yes'/>
			</object>
		</div>
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
                msg: 'Hello Tableau Fans!',
                url: "https://public.tableau.com/views/COVID-19Cases_15840488375320/COVID-19Cases?:display_count=y&:origin=viz_share_link",
            }
        },
        mounted() {
            const divElement = document.getElementById('viz1587449784753');
            const vizElement = divElement.getElementsByTagName('object')[0];
            if (divElement.offsetWidth > 800) {
                vizElement.style.width = '1240px';
                vizElement.style.height = '877px';
            } else if (divElement.offsetWidth > 500) {
                vizElement.style.width = '1240px';
                vizElement.style.height = '877px';
            } else {
                vizElement.style.width = '100%';
                vizElement.style.height = '827px';
            }
            const scriptElement = document.createElement('script');
            scriptElement.src = 'https://public.tableau.com/javascripts/api/viz_v1.js';
            vizElement.parentNode.insertBefore(scriptElement, vizElement);
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
