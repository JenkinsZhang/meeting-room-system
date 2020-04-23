<template>
	<div>
		<div class='tableauPlaceholder' style='width: 1000px; height: 841px;'>
			<object class='tableauViz' width='1000' height='841' style='display:none;'>
				<param name='host_url' value='https%3A%2F%2Fprod-useast-a.online.tableau.com%2F'/>
				<param name='embed_code_version' value='3'/>
				<param name='site_root' value='/t/jenkinszhangstableau'/>
				<param name='name' value='GraduationProject/GraduationProject'/>
				<param name='tabs' value='no'/>
				<param name='toolbar' value='yes'/>
				<param name='showAppBanner' value='false'/>
			</object>
		</div>
	</div>
</template>

<script>
    export default {
        name: "BookingStatisics",
        mounted() {
            const s = document.createElement('script');
            s.type = 'text/javascript';
            s.src = 'https://prod-useast-a.online.tableau.com/javascripts/api/viz_v1.js';
            document.body.appendChild(s);
        }
    }
</script>

<style scoped>

</style>
