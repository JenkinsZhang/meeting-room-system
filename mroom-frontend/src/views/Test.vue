<template>
	<div>
		<el-button @click="testRoute">
		</el-button>
		<el-upload
				class="upload-demo"
				drag
				ref="upload"
				:action="'aaa'"
				:limit="1"
				accept="image/*"
				:http-request="upload"
				:before-upload="beforeUpload"
				:auto-upload="false"
				>
			<i class="el-icon-upload"/>
			<div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
			<div class="el-upload__tip" slot="tip">只能上传jpg/png文件，且不超过500kb</div>
			
		</el-upload>
		<el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload">上传到服务器</el-button>
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
                calendarPlugins: [ resourceTimelinePlugin ],
	            headers: {
                    'Content-Type': 'multipart/form-data'
	            },
	            file: null
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
            },
		    upload(){
                console.log(this.file);
                const form = new FormData();
                form.append("roomImage", this.file);
                this.axios({
                    url: "/api/roomInfo/post/roomImage",
                    method: 'POST',
                    data: form,
	                params: {
                        roomId : 1
	                },
                    headers: {
                        'Content-Type': 'multipart/form-data'
                    }
                }).then((res) => {
                    console.log(res.data);
                })
		    },
		    beforeUpload(file){
                
                const filenameSplit = file.name.split(".")
			    const suffix = filenameSplit[filenameSplit.length-1];
                this.file = new File([file],"heiheihei"+"."+suffix)
                // console.log(this.file);
		    },
            submitUpload() {
                this.$refs.upload.submit();
            },
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
