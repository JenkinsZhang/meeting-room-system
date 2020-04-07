<template>
	<div style="padding-left: 20px;padding-right: 20px">
		<p style="color: red">Pay attention! The following fields are all required!</p>
		<el-row class="booking_row">
			<el-col :span="12" style="line-height:40px;height:40px;text-align: left">
				<i class="el-icon-warning-outline" style="color: orangered"
				        v-show="this.date ===null || this.date === undefined || this.date === ''"/>
				<i class="el-icon-circle-check"style="color: limegreen" v-show="this.date" />
				Select booking date:
				<b>{{date}}</b>
			</el-col>
			<el-col :span="12">
				<el-date-picker
						:editable="false"
						v-model="date"
						type="date"
						value-format="yyyy-MM-dd"
						placeholder="Select booking date..."
						style="width: 100%;font-size: 1rem"
						:picker-options="pickerOptions">
				</el-date-picker>
			</el-col>
		</el-row>
		
		<el-row class="booking_row">
			<el-col :span="12" style="line-height:40px;text-align: left">
				<i class="el-icon-warning-outline" style="color: orangered"
				        v-show="this.startTime ===null || this.startTime === undefined || this.startTime === ''"/>
				<i class="el-icon-circle-check"style="color: limegreen" v-show="this.startTime"/>
				Select starting time:&nbsp;&nbsp;<b>{{startTime}}</b>
			</el-col>
			<el-col :span="12">
				<el-time-select
						v-model="startTime"
						:editable="false"
						placeholder="Select starting time..."
						style="width: 100%;font-size: 1rem"
						:picker-options="{
                                start: '09:00',
                                step: '00:30',
                                end: '22:00',
                                maxTime: endTime
                                }">
				</el-time-select>
			</el-col>
		</el-row>
		<el-row class="booking_row">
			<el-col :span="12" style="line-height:40px;text-align: left">
				<i class="el-icon-warning-outline" style="color: orangered"
				        v-show="this.endTime ===null || this.endTime === undefined || this.endTime === ''"/>
				<i class="el-icon-circle-check"style="color: limegreen" v-show="this.endTime"/>
				Select ending time:&nbsp;&nbsp;<b>{{endTime}}</b>
			</el-col>
			<el-col :span="12">
				<el-time-select
						v-model="endTime"
						placeholder="select ending time..."
						style="width: 100%;font-size: 1rem"
						size="large"
						:editable="false"
						:picker-options="{
                                start: '09:00',
                                step: '00:30',
                                end: '22:00',
                                minTime: startTime
                                }">
				</el-time-select>
			</el-col>
		</el-row>
		<el-row class="booking_row">
			<el-col :span="12" style="line-height:40px;text-align: left">
				<i class="el-icon-warning-outline" style="color: orangered"  v-show="this.roomId==='' || this.roomId === undefined"/>
				<i class="el-icon-circle-check"style="color: limegreen"  v-show="!(this.roomId==='' || this.roomId === undefined)"/>
				Choose your meeting room:&nbsp;&nbsp;<b>{{getRoomNameById}}</b>
			</el-col>
			<el-col :span="12">
				<el-select v-model="roomId" style="width: 100%;" placeholder="Select room..." no-data-text="No data">
					<el-option v-for="(room,index) in rooms"
					           style="font-size: 1rem;"
					           :key="index"
					           :value=room.roomId
					           :label="room.roomName"/>
				</el-select>
			</el-col>
		</el-row>
		<el-row class="booking_row">
			<el-col :span="24">
				<el-button :disabled="checkField" @click="showMsgBox" style="margin-left: 25%;width: 100px"
				           type="primary">Submit
				</el-button>
				<el-button @click="reset"  type="danger" style="margin-left: 25%;width: 100px" variant="outline-danger">Reset
				</el-button>
			</el-col>
		</el-row>
		<el-dialog
				title="Please confirm your booking information"
				width="500px" style="border-radius: 12px"
				center :visible.sync="dialogVisible">
			<span>The room reserved for you is: </span>
			<b>{{dialogRoomName}}</b>
			<br><br><br><br>
			<span>Starting time:  </span>
			<b>{{dialogStartTime}}</b>
			<br><br><br><br>
			<span>Closing time: </span>
			<b>{{dialogEndTime}}</b>
			<br><br>
			<span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">Cancel</el-button>
    <el-button type="primary" @click="submit">Submit</el-button>
  </span>
		</el-dialog>
	</div>
</template>

<script>
    export default {
        name: "booking",
        data() {
            return {
                date: '',
                startTime: '',
                endTime: '',
                roomId: '',
                loading: true,
                pickerOptions: {
                    disabledDate(time) {
                        return time.getTime() <= Date.now();
                    },
                    shortcuts: [{
                        text: 'Today',
                        onClick(picker) {
                            picker.$emit('pick', new Date());
                        }
                    }, {
                        text: 'Tomorrow',
                        onClick(picker) {
                            const date = new Date();
                            date.setTime(date.getTime() + 3600 * 1000 * 24);
                            picker.$emit('pick', date);
                        }
                    }, {
                        text: 'A week later',
                        onClick(picker) {
                            const date = new Date();
                            date.setTime(date.getTime() + 3600 * 1000 * 24 * 7);
                            picker.$emit('pick', date);
                        }
                    }]
                },
                rooms: [],
	            dialogVisible: false,
	            dialogStartTime: '',
	            dialogEndTime: '',
	            dialogRoomName: '',
            }
        },
        computed: {
            getRoomNameById() {
                for (let i = 0; i < this.rooms.length; i++) {
                    if (this.rooms[i].roomId === this.roomId) {
                        this.roomName = this.rooms[i].roomName;
                        return this.roomName;
                    }
                }
                return null;
            },
            checkField() {
                let flag = true;
                if (this.date === '') {
                    return flag;
                }
                if (this.startTime === '') {
                    return flag;
                }
                if (this.endTime === '') {
                    return flag;
                }
                if (this.roomId === '' || this.roomId === undefined) {
                    return flag;
                }
                return !flag;
            },
        },
        methods: {

            changeStatus() {
                this.loading = !this.loading;
            },
            getRooms() {
                this.axios({
                    method: 'GET',
                    url: '/api/roomInfo',
                }).then((res) => {
                    for (let i = 0; i < res.data.data.length; i++) {
                        let room = res.data.data[i];
                        this.rooms.push(room)
                    }
                })
            },

            showMsgBox() {
                this.dialogStartTime = this.date + " " + this.startTime;
                this.dialogEndTime = this.date + " " + this.endTime;
                this.dialogRoomName = this.roomName;
                this.dialogVisible = true;
            },
            submit() {
                // console.log(startTime,endTime);
                let submitData = {
                    roomId: this.roomId,
                    startTime: this.dialogStartTime,
                    endTime: this.dialogEndTime,
                    bookerEmail: this.$jwtUtil.getTokenEmail()
                };
                this.axios({
                    method: 'POST',
                    data: submitData,
                    url: '/api/booking'
                }).then((res) => {
                    this.loading = false;
                    if (res.data.code === 200) {
                        this.$message({
	                        message: "Booking Success!",
	                        type: "success"
                        })
                    } else {
                        this.$message({
	                        message: res.data.msg,
	                        type: "warning"
                        });
                    }
                }).catch((error) => {
                    this.loading = false;
                    this.$messageUtil.errorMessage(this)
                }).finally(() => {
                    this.reset();
                    this.dialogVisible = false;
                })
            },
            reset() {
                this.date = '';
                this.startTime = '';
                this.endTime = '';
                this.roomId = '';
            }
        },
        mounted() {
            setTimeout(this.changeStatus, 500);
            this.getRooms();

        },
        created() {
            if (this.$route.params.roomId !== null) {
                this.roomId = this.$route.params.roomId;
            }
            else {
                this.roomId = "";
            }
        }
    }
</script>

<style scoped>
	
	.booking_row:nth-of-type(n+2) {
		padding-top: 5%;
	}
	
	.booking_row:nth-of-type(5) {
		padding-top: 5%;
	}
	
</style>
<style>
	.el-dialog__body{
		font-size: 16px;
	}
</style>
