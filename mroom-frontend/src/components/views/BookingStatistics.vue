<template>
	<!--	<div>-->
	<!--		<div class='tableauPlaceholder' style='width: 1000px; height: 841px;'>-->
	<!--			<object class='tableauViz' width='1000' height='841' style='display:none;'>-->
	<!--				<param name='host_url' value='https%3A%2F%2Fprod-useast-a.online.tableau.com%2F'/>-->
	<!--				<param name='embed_code_version' value='3'/>-->
	<!--				<param name='site_root' value='/t/jenkinszhangstableau'/>-->
	<!--				<param name='name' value='GraduationProject/GraduationProject'/>-->
	<!--				<param name='tabs' value='no'/>-->
	<!--				<param name='toolbar' value='yes'/>-->
	<!--				<param name='showAppBanner' value='false'/>-->
	<!--			</object>-->
	<!--		</div>-->
	<!--	</div>-->
	<div class='tableauPlaceholder' id='viz1588919288932' style='width: 1000px; height: 841px;margin-top: 100px' >
		<noscript><a href='#'><img alt=' '
		                           src='https://public.tableau.com/static/images/Gr/GraduationProject/GraduationProject/1_rss.png'
		                           style='border: none'/></a></noscript>
		<object class='tableauViz' style='display:none;'>
			<param name='host_url' value='https%3A%2F%2Fpublic.tableau.com%2F'/>
			<param name='embed_code_version' value='3'/>
			<param name='site_root' value=''/>
			<param name='name' value='GraduationProject/GraduationProject'/>
			<param name='tabs' value='no'/>
			<param name='toolbar' value='yes'/>
			<param name='static_image'
			       value='https://public.tableau.com/static/images/Gr/GraduationProject/GraduationProject/1.png'/>
			<param name='animate_transition' value='yes'/>
			<param name='display_static_image' value='yes'/>
			<param name='display_spinner' value='yes'/>
			<param name='display_overlay' value='yes'/>
			<param name='display_count' value='yes'/>
		</object>
	</div>

</template>

<script>
    export default {
        name: "BookingStatisics",
        mounted() {
            // const s = document.createElement('script');
            // s.type = 'text/javascript';
            // s.src = 'https://prod-useast-a.online.tableau.com/javascripts/api/viz_v1.js';
            // document.body.appendChild(s);
            var divElement = document.getElementById('viz1588919288932');
            var vizElement = divElement.getElementsByTagName('object')[0];
            if (divElement.offsetWidth > 800) {
                vizElement.style.width = '1000px';
                vizElement.style.height = '841px';
            } else if (divElement.offsetWidth > 500) {
                vizElement.style.width = '1000px';
                vizElement.style.height = '841px';
            } else {
                vizElement.style.width = '100%';
                vizElement.style.height = '1127px';
            }
            var scriptElement = document.createElement('script');
            scriptElement.src = 'https://public.tableau.com/javascripts/api/viz_v1.js';
            vizElement.parentNode.insertBefore(scriptElement, vizElement);
        }
    }
</script>

<style scoped>

</style>
