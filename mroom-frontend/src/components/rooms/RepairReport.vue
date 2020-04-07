<template>
 <div>
	 RepairReport
	 {{roomId}}
 </div>
</template>

<script>
    export default {
        name: "RepairReport",
	    data(){
            return {
                roomId:null
            }
	    },
	    created() {
            if(this.$route.params.roomId !==null)
            {
                this.roomId = this.$route.params.roomId;
            }
        }
    }
</script>

<style scoped>

</style>
