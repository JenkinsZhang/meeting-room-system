<template>
	<div>
		{{history_records}}
	</div>
</template>

<script>
    export default {
        name: "history",
	    data(){
            return {
                history_records:[]
            }
	    },
	    mounted() {
            this.axios({
	            url: "api/booking/history/"+this.$jwtUtil.getTokenEmail(),
	            method: 'GET'
            }).then((res)=>{
                this.history_records = res.data.data;
            }).catch((error)=>{
                console.log(error)
            })
        }
    }
</script>

<style scoped>

</style>
