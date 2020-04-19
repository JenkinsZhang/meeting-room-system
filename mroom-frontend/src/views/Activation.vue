<template>
	<div>
		<div v-show="activate">
			<h1>Activation Successful! </h1>

		</div>
		<div v-show="onload">
			<h1>Activating.....</h1>
		</div>
		<div v-show="failed">
			<h1>Activation failed! Please try to get a new activation email!</h1>
		</div>
		<h4>Now returning to login page...  {{time}}</h4>
		<a href="#" @click="toLogin">if your browser doesn't respond, please click here! </a>
	</div>
</template>

<script>
    export default {
        name: "activation",
        data() {
			return {
				activate: false,
				onload: true,
				failed: false,
				time: 5
			}
        },
	    mounted() {
            let token = window.location.search;
		    this.axios({
			    method: "PUT",
			    url: '/api/user/activate'+token
		    }).then((res)=>{
		        if(res.data.code ===200)
		        {
		            this.activate = true;
		            this.onload = false;
		            setInterval(this.countdown,1000);
		        }
		        else
		        {
		            this.onload = false;
		            this.failed = true;
                    setInterval(this.countdown,1000);
		        }
		        console.log(res.data)
		    }).catch((error)=>{
		        console.log(error);
                this.onload = false;
                this.failed = true;
                setInterval(this.countdown,1000);
		    })
        },
	    methods: {
            countdown(){
                this.time = this.time-1;
                if(this.time ===0)
                {
                    this.$router.push("/login");
                }
            },
            toLogin(){
                this.$router.push("/login");
            }
	    }
    }
</script>


<style scoped>
	div {
		text-align: center;
		margin-top: 15%;
	}
</style>
