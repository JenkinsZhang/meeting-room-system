<template>
	<div class="rooms">
		<el-row>
			<el-col :span="7" v-for="(room, index) in rooms" :key="room.roomId" :offset="1" style="height: 25%">
				<el-card :body-style="{ padding: '0px'}" shadow="hover"
				         style="height: 100%;margin-bottom: 20px;padding-bottom: 20px">
					<img :src="room.imageURL"
					     class="image">
					<div>
						<div style="margin-top: 30px;width: 100%;">
							<el-row style="height: 50px">
								<el-col :span="8" :offset="1">Room Name:</el-col>
								<el-col :span="14" :offset="1" style="font-weight: bolder">{{room.roomName}}</el-col>
							</el-row>
							<el-row style="height: 60px">
								<el-col :span="8" :offset="1">Address:</el-col>
								<el-col :span="14" :offset="1" style="font-weight: bolder">{{room.address}}</el-col>
							</el-row>
							<br>
							<el-row>
								<el-col :span="10" :offset="1">
									<el-button type="primary" @click="getDetails(room.roomId)">Details</el-button>
								</el-col>
								<el-col :span="10" :offset="2">
									<el-button type="success" @click="booking(room.roomId)">Booking</el-button>
								</el-col>
							</el-row>
							<br>
							<el-row>
								<el-col :span="22" :offset="1">
									<el-button type="danger" @click="report(room.roomId)">Report</el-button>
								</el-col>
							</el-row>
						</div>
					</div>
				
				</el-card>
			</el-col>
		</el-row>
		<el-dialog
				title="Room Details"
				:visible.sync="dialogVisible"
				width="80%"
				class="dialogMain">
			<el-row>
				<el-col :span="16">
					<img :src="this.selectedRoom.imageURL"
					     class="dialogImage" alt="Null"/>
				</el-col>
				<el-col :span="7" :offset=1 class="dialogDetail">
					<el-row>
						<el-col :span="10" :offset="1">Room ID:</el-col>
						<el-col :span="12" :offset="1" style="font-weight: bolder;font-size: 16px">
							{{this.selectedRoom.roomId}}
						</el-col>
					
					</el-row>
					<el-row>
						<el-col :span="10" :offset="1">Room Name:</el-col>
						<el-col :span="12" :offset="1" style="font-weight: bolder;font-size: 16px">
							{{this.selectedRoom.roomName}}
						</el-col>
					
					</el-row>
					<el-row>
						<el-col :span="10" :offset="1">Address:</el-col>
						<el-col :span="12" :offset="1" style="font-weight: bolder;font-size: 16px">
							{{this.selectedRoom.address}}
						</el-col>
					
					</el-row>
					<el-row>
						<el-col :span="10" :offset="1">Capacity:</el-col>
						<el-col :span="12" :offset="1" style="font-weight: bolder;font-size: 16px">
							{{this.selectedRoom.capacity}}
						</el-col>
					
					</el-row>
					<el-row>
						<el-col :span="10" :offset="1">Number of projectors</el-col>
						<el-col :span="12" :offset="1" style="font-weight: bolder;font-size: 16px">
							{{this.selectedRoom.projection}}
						</el-col>
					
					</el-row>
					<el-row>
						<el-col :span="10" :offset="1">Number of Air Conditioners</el-col>
						<el-col :span="12" :offset="1" style="font-weight: bolder;font-size: 16px">
							{{this.selectedRoom.airConditioner}}
						</el-col>
					
					</el-row>
					<el-row style="margin-top:50px">
						<el-col :span="14" :offset="6">
							<el-button type="success" @click="booking(selectedRoom.roomId)">Booking</el-button>
						</el-col>
					</el-row>
				</el-col>
			</el-row>
		
		</el-dialog>
		<el-dialog
				title="Report"
				:visible.sync="reportVisible"
				width="80%"
				class="dialogMain">
			<el-row>
				<el-col :span="10" :offset="1">Room ID:</el-col>
				<el-col :span="12" :offset="1" style="font-weight: bolder;font-size: 16px">
					{{this.selectedRoom.roomId}}
				</el-col>
			
			</el-row>
			<el-row style="margin-top: 3%">
				<el-col :span="10" :offset="1">Room Name:</el-col>
				<el-col :span="12" :offset="1" style="font-weight: bolder;font-size: 16px">
					{{this.selectedRoom.roomName}}
				</el-col>
			
			</el-row>
			<el-row style="margin-top: 3%">
				<el-col :span="10" :offset="1">Address:</el-col>
				<el-col :span="12" :offset="1" style="font-weight: bolder;font-size: 16px">
					{{this.selectedRoom.address}}
				</el-col>
			
			</el-row>
			<el-row style="margin-top: 3%">
				<el-col :span="10" :offset="1"><b>Report Details: </b></el-col>
			</el-row>
			<el-row style="margin-top: 1%">
				<el-col :span="22" :offset="1">
					<el-input
							type="textarea"
							:rows="8"
							placeholder="Please fill in where the repair is needed "
							v-model="reportDetail"
					>
					</el-input>
				</el-col>
			</el-row>
			<el-row style="margin-top: 4%">
				<el-col :span="4" :offset="19">
					<el-button type="success" @click="doReport">Submit</el-button>
				</el-col>
			</el-row>
		
		</el-dialog>
	</div>

</template>

<script>
    export default {
        name: "rooms",
        data() {
            return {
                rooms: [],
                selectedRoom: {},
                dialogVisible: false,
                reportVisible: false,
                reportDetail: null
            }
        },
        methods: {
            clearRoom(){
                this.selectedRoom = {}
            },
            getDetails(roomId) {
                for (let room of this.rooms) {
                    if (room.roomId === roomId) {
                        this.selectedRoom = room;
                        break;
                    }
                }
                this.dialogVisible = true;
            },
            getRooms() {
                this.axios({
                    url: "/api/roomInfo/roomOverview",
                    method: "GET"
                }).then((res) => {
                    if (res.data.code === 200) {
                        this.rooms = res.data.data;
                    } else {
                        this.rooms = null;
                    }
                }).catch((error) => {
                    this.$messageUtil.errorMessage(this);
                })
            },
            booking(roomId) {
                this.$router.push({
                    name: 'booking',
                    params: {
                        roomId: roomId
                    }
                })
            },
            report(roomId) {
                for (let room of this.rooms) {
                    if (room.roomId === roomId) {
                        this.selectedRoom = room;
                        break;
                    }
                }
                this.reportVisible = true;
            },
	        doReport(){
                let report = {
                    roomId : this.selectedRoom.roomId,
	                reporter: this.$jwtUtil.getTokenEmail(),
	                detail: this.reportDetail
                };
                this.axios({
	                url : "/api/roomInfo/report",
	                data: report,
	                method: "POST"
                }).then((res)=>{
                    if(res.data.code === 200)
                    {
                        this.$messageUtil.successMessage(this,res.data.msg);
                        this.reportVisible = false;
                        this.clearRoom();
                        this.reportDetail = null;
                    }
                    else {
                        this.$messageUtil.warningMessage(this,res.data.msg);
                    }
                }).catch((err)=>{
                    this.$messageUtil.errorMessage(this)
                })
	        }


        },
        mounted() {
            this.getRooms();
        }
    }
</script>

<style scoped>
	.time {
		font-size: 13px;
		color: #999;
	}
	
	.bottom {
		margin-top: 13px;
		line-height: 12px;
	}
	
	.button {
		padding: 0;
		float: right;
	}
	
	.image {
		height: 200px;
		width: 100%;
	}
	
	.dialogMain {
		height: 100%;
		position: fixed;
		left: 10%;
		top: 0%;
	}
	
	.dialogImage {
		width: 100%;
	}
	
	.el-button {
		width: 100%;
	}
	
	.dialogDetail .el-row {
		margin-bottom: 20px;
	}
</style>
