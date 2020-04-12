<template>
	<div>
		<div>
			<div style="display: flex;justify-content: space-between">
				<div>
					<el-button type="primary" icon="el-icon-plus" @click="showAddRoomView">
						Add Room
					</el-button>
				</div>
			</div>
		</div>
		<div style="margin-top: 10px">
			<el-table
					:data="rooms"
					stripe
					border
					v-loading="loading"
					element-loading-text="Loading..."
					element-loading-spinner="el-icon-loading"
					element-loading-background="rgba(0, 0, 0, 0.8)"
					:default-sort="{prop:'viewId',order: 'ascending'}"
					style="width: 100%">
				<el-table-column
						prop="roomName"
						fixed
						align="left"
						label="Room Name"
						width="130">
				</el-table-column>
				<el-table-column
						prop="address"
						label="Address"
						align="left"
						width="250"
						sortable>
				</el-table-column>
				<el-table-column
						prop="capacity"
						label="Capacity"
						align="left"
						width="100">
				</el-table-column>
				<el-table-column
						prop="projection"
						width="90"
						align="left"
						label="Projector Number">
				</el-table-column>
				<el-table-column
						prop="airConditioner"
						width="120"
						align="left"
						label="Air-conditioner Number">
				</el-table-column>
				<el-table-column
						fixed="right"
						width="300"
						label="Action">
					<template slot-scope="scope" style="text-align: center">
						<el-button size="mini" @click="showEditRoomView(scope.row)">Edit
						</el-button>
						<el-button size="mini" @click="uploadImage(scope.row)" type="success">Upload Image
						</el-button>
						<el-button size="mini" @click="deleteRoom(scope.row)" type="danger">Delete
						</el-button>
					</template>
				</el-table-column>
			</el-table>
		</div>
		<el-dialog
				:title="title"
				:visible.sync="roomDialogView"
				width="80%"
				:before-close="handleClose"
		>
			<div>
				<el-form style="text-align: left;margin-left: 7%;margin-right: 7%" :model="selectedRoom"
				         status-icon>
					<el-form-item label="Room Name: " prop="roomName">
						<el-input type="text" placeholder="Enter room name" v-model="selectedRoom.roomName">
						
						</el-input>
					</el-form-item>
					<el-form-item label="Address: " prop="address">
						<el-input  type="text" placeholder="Enter address" v-model="selectedRoom.address">
						
						</el-input>
					</el-form-item>
					<el-row>
						<el-col :span="7" >
							<el-form-item label="Capacity: " prop="maxPeople">
								<el-input type="number" v-model="selectedRoom.maxPeople"/>
							</el-form-item>
						</el-col>
						<el-col :span="7" :offset="1">
							<el-form-item label="Projector Number: " prop="projection">
								<el-input type="number" v-model="selectedRoom.projection"/>
							</el-form-item>
						</el-col>
						<el-col :span="7" :offset="1">
							<el-form-item label="Air-conditioner Number: " prop="airConditioner">
								<el-input type="number" v-model="selectedRoom.airConditioner"/>
							</el-form-item>
						</el-col>
					</el-row>
					
					
				</el-form>
			</div>
			<span slot="footer" class="dialog-footer">
		    <el-button @click="cancelEditRoom">Cancel</el-button>
		    <el-button type="primary" @click="doEditRoom">Submit</el-button>
		  </span>
		</el-dialog>
		<el-dialog
				:title="title"
				:visible.sync="addDialogView"
				width="80%"
				:before-close="handleClose"
		>
			<div>
				<el-form style="text-align: left;margin-left: 7%;margin-right: 7%" :model="selectedRoom"
				         status-icon>
					<el-form-item label="Room Name: " prop="roomName">
						<el-input type="text" placeholder="Enter room name" v-model="selectedRoom.roomName">
						
						</el-input>
					</el-form-item>
					<el-form-item label="Address: " prop="address">
						<el-input  type="text" placeholder="Enter address" v-model="selectedRoom.address">
						
						</el-input>
					</el-form-item>
					<el-row>
						<el-col :span="7" >
							<el-form-item label="Capacity: " prop="maxPeople">
								<el-input type="number" v-model="selectedRoom.maxPeople"/>
							</el-form-item>
						</el-col>
						<el-col :span="7" :offset="1">
							<el-form-item label="Projector Number: " prop="projection">
								<el-input type="number" v-model="selectedRoom.projection"/>
							</el-form-item>
						</el-col>
						<el-col :span="7" :offset="1">
							<el-form-item label="Air-conditioner Number: " prop="airConditioner">
								<el-input type="number" v-model="selectedRoom.airConditioner"/>
							</el-form-item>
						</el-col>
					</el-row>
				
				
				</el-form>
			</div>
			<span slot="footer" class="dialog-footer">
		    <el-button @click="cancelEditRoom">Cancel</el-button>
		    <el-button type="primary" @click="doAddRoom">Submit</el-button>
		  </span>
		</el-dialog>
		<el-dialog
				:title="title"
				:visible.sync="uploadDialogView"
				width="80%"
				:before-close="handleClose"
		>
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
					style="margin: 0;text-align: center"
			>
				<i class="el-icon-upload"/>
				<div class="el-upload__text">Please drag your picture here or <br><em>click here to upload</em></div>
				<div class="el-upload__tip" slot="tip">Only image file.The recommended size is less than 500kb.</div>
			
			</el-upload>
			<span slot="footer" class="dialog-footer">
		    <el-button @click="cancelUploadRoom">Cancel</el-button>
		    <el-button type="primary" @click="submitUpload">Submit</el-button>
		  </span>
		</el-dialog>
	</div>
</template>

<script>
    export default {
        name: "RoomsManagement",
        data() {
            return {
                title: '',
                accounts: [],
                loading: false,
                roomDialogView: false,
                addDialogView: false,
	            uploadDialogView: false,
                rooms: [],
                selectedRoom:{
                    roomId: null,
                    roomName: null,
                    address: null,
                    maxPeople: 0,
                    projection: 0,
	                airConditioner: 0,
                    imageUrl: null
                },
	            
            }
        },
        mounted() {
            this.initRooms();
        },
        methods: {
            initRooms() {
                this.loading = true;
                this.axios({
                    url: "/api/roomInfo/roomOverview",
                    method: "GET"
                }).then((res)=>
                {
                    if(res.data.code === 200)
                    {
                        this.rooms = res.data.data;
                    }
                    else{
                        this.rooms = null;
                    }
                }).catch((error)=>{
                    this.$messageUtil.errorMessage(this);
                }).finally(()=>{
                    this.loading = false;
                })
            },
            emptyRoom() {
                this.selectedRoom = {
                    roomId: null,
                    roomName: null,
                    address: null,
                    maxPeople: 0,
                    projection: 0,
                    airConditioner: 0,
	                imageUrl: null
                }
            },
            async deleteRoom(data) {
                await this.axios({
                    url: "/api/roomInfo/admin/" + data.roomId,
                    method: "DELETE"
                }).then((res) => {
                    if (res.data.code === 200) {
                        this.$messageUtil.successMessage(this,res.data.msg);
                    }
                    else{
                        this.$messageUtil.warningMessage(this,res.data.msg);
                    }
                }).catch((err)=>{
                    this.$messageUtil.errorMessage(this);
                });
                this.initRooms();
            },
            async doAddRoom() {
                await this.axios({
                    method: 'POST',
                    data: this.selectedRoom,
                    url: '/api/roomInfo/admin'
                }).then((res) => {
                    if (res.data.code === 200) {
                        this.$message({
                            message: res.data.msg,
                            type: "success"
                        })
                    } else {
                        this.$message({
                            message: res.data.msg,
                            type: "warning"
                        })
                    }
                }).catch((error) => {
                    this.$messageUtil.errorMessage(this);
                });
                this.addDialogView = false;
                this.initRooms();
            },
            async doEditRoom() {
                await this.axios({
                    url: "/api/roomInfo/admin/editRoom",
                    data: this.selectedRoom,
                    method: "POST"
                }).then((res) => {
                    if (res.data.code === 200) {
                        this.$messageUtil.successMessage(this, res.data.msg);
                        this.roomDialogView = false;
                    } else {
                        this.$messageUtil.warningMessage(this, res.data.msg);
                        this.roomDialogView = false;
                    }
                }).catch((err) => {
                    this.$messageUtil.errorMessage(this);
                });
                this.initRooms();
            },
            async cancelEditRoom() {
                this.roomDialogView = false;
                await this.initRooms();
            },
            async cancelUploadRoom() {
                this.uploadDialogView = false;
                await this.initRooms();
            },
            showAddRoomView() {
                this.emptyRoom();
                this.title = "Add Room";
                this.addDialogView = true;
            },
            showEditRoomView(data) {
                this.title = "Edit Room";
                this.selectedRoom.address =data.address;
                this.selectedRoom.imageUrl = data.imageURL;
                this.selectedRoom.roomName = data.roomName;
                this.selectedRoom.roomId = data.roomId;
                this.selectedRoom.maxPeople = data.capacity;
                this.selectedRoom.projection = data.projection;
                this.selectedRoom.airConditioner = data.airConditioner;
                this.roomDialogView = true;
            },
	        uploadImage(data)
	        {
                this.title = "Upload Image";
                this.selectedRoom.roomName = data.roomName;
                this.selectedRoom.roomId = data.roomId;
                this.uploadDialogView = true;
	        },

            async handleClose(done) {
                await this.initRooms();
                done();
            },

            upload(){
                console.log(this.file);
                const form = new FormData();
                form.append("roomImage", this.file);
                this.axios({
                    url: "/api/roomInfo/admin/post/roomImage",
                    method: 'POST',
                    data: form,
                    params: {
                        roomId : this.selectedRoom.roomId
                    },
                    headers: {
                        'Content-Type': 'multipart/form-data'
                    }
                }).then((res)=>{
                    if(res.data.code === 200)
                    {
                        this.$messageUtil.successMessage(this,res.data.msg);
                    }
                    else{
                        this.$messageUtil.warningMessage(this,res.data.msg);
                    }
                }).catch((err)=>{
                    this.$messageUtil.errorMessage(this)
                }).finally(()=>{
                    this.initRooms();
                    this.uploadDialogView = false;
                })
            },
            beforeUpload(file){

                const filenameSplit = file.name.split(".");
                const suffix = filenameSplit[filenameSplit.length-1];
                this.file = new File([file],this.selectedRoom.roomName+"."+suffix)
                // console.log(this.file);
            },
            submitUpload() {
                this.$refs.upload.submit();
            },
	        
        }
    }
</script>

<style scoped>
	.slide-fade-enter-active {
		transition: all .8s ease;
	}
	
	.slide-fade-leave-active {
		transition: all .8s cubic-bezier(1.0, 0.5, 0.8, 1.0);
	}
	
	.slide-fade-enter, .slide-fade-leave-to
		/* .slide-fade-leave-active for below version 2.1.8 */
	{
		transform: translateX(10px);
		opacity: 0;
	}
</style>
