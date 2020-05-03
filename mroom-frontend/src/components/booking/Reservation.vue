<template>
	<div style="padding-left: 20px;padding-right: 20px">
		<p style="color: red">Pay attention to the required items！
			<a  style="display: inline;margin-left: 40%;text-decoration: none" href="javascript:void(0)"  @click="advanceBooking">Advance Booking</a>
			<el-divider/>
		</p>
		
<!--		<el-divider/>-->
		<el-row class="booking_row">
			<el-col :span="12" style="line-height:40px;height:40px;text-align: left">
				<i class="el-icon-warning-outline" style="color: orangered"
				        v-show="this.date ===null || this.date === undefined || this.date === ''"/>
				<i class="el-icon-circle-check" style="color: limegreen" v-show="this.date" />
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
			<el-col :span="12" style="line-height:40px;text-align: left">
				<i class="el-icon-warning-outline" style="color: orangered"  v-show="this.subject==='' || this.subject === undefined"/>
				<i class="el-icon-circle-check"style="color: limegreen"  v-show="!(this.subject==='' || this.subject === undefined)"/>
				Meeting subject:&nbsp;&nbsp;<b>{{subject}}</b>
			</el-col>
			<el-col :span="12">
				<el-input v-model="subject" style="width: 100%;" placeholder="Type in subject...">
				</el-input>
			</el-col>
		</el-row>
		<el-row class="booking_row">
			<el-col :span="12" style="line-height:40px;text-align: left">
				Choose attenders:&nbsp;&nbsp
			</el-col>
			<el-col :span="12">
				<el-select v-model="attenders" style="width: 100%;" placeholder="Select attenders..." multiple no-data-text="No data">
					<el-option
							v-for="user in generalUsers"
							:key="user.id"
							:label="user.username"
							:value="user.id">
						<span style="float: left">{{ user.username }}</span>
						<span style="float: right; color: #8492a6; font-size: 13px">{{ user.email }}</span>
					</el-option>
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
		<el-dialog
				:visible.sync="advanceBookingView"
				style="width:100%"
				width="85%"
				title="Advance Booking"
				@close="reset">
			<el-row class="booking_row">
				<el-col :span="12" style="line-height:40px;height:40px;text-align: left">
					<i class="el-icon-warning-outline" style="color: orangered"
					   v-show="this.date ===null || this.date === undefined || this.date === ''"/>
					<i class="el-icon-circle-check" style="color: limegreen" v-show="this.date" />
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
					<i class="el-icon-warning-outline" style="color: orangered"  v-show="this.subject==='' || this.subject === undefined"/>
					<i class="el-icon-circle-check"style="color: limegreen"  v-show="!(this.subject==='' || this.subject === undefined)"/>
					Meeting subject:&nbsp;&nbsp;<b>{{subject}}</b>
				</el-col>
				<el-col :span="12">
					<el-input v-model="subject" style="width: 100%;" placeholder="Type in subject...">
					</el-input>
				</el-col>
			</el-row>
			<el-row class="booking_row">
				<el-col :span="12" style="line-height:40px;text-align: left">
					Choose attenders:&nbsp;&nbsp
				</el-col>
				<el-col :span="12">
					<el-select v-model="attenders" style="width: 100%;" placeholder="Select attenders..." multiple no-data-text="No data">
						<el-option
								v-for="user in generalUsers"
								:key="user.id"
								:label="user.username"
								:value="user.id">
							<span style="float: left">{{ user.username }}</span>
							<span style="float: right; color: #8492a6; font-size: 13px">{{ user.email }}</span>
						</el-option>
					</el-select>
				</el-col>
			</el-row>
			
			<el-row class="booking_row">
				<el-col :span="5" style="line-height:40px;text-align: left">
					<i class="el-icon-warning-outline" style="color: orangered"
					   v-show="this.capacity ===null || this.capacity <= 0 "/>
					<i class="el-icon-circle-check"style="color: limegreen" v-show="this.capacity >0"/>
					Number of People:&nbsp;&nbsp;<b>{{capacity}}</b>
				</el-col>
				<el-col :span="2">
					<el-input type="number" v-model="capacity"/>
				</el-col>
				<el-col :span="5" :offset="1" style="line-height:40px;text-align: left">
					<i class="el-icon-warning-outline" style="color: orangered"
					   v-show="this.projection ===null || this.projection < 0 "/>
					<i class="el-icon-circle-check"style="color: limegreen" v-show="this.projection >=0"/>
					Number of Projectors:&nbsp;&nbsp;<b>{{projection}}</b>
				</el-col>
				<el-col :span="2">
					<el-input type="number" v-model="projection"/>
				</el-col>
				<el-col :span="6"  :offset="1" style="line-height:40px;text-align: left">
					<i class="el-icon-warning-outline" style="color: orangered"
					   v-show="this.airConditioner ===null || this.airConditioner < 0 "/>
					<i class="el-icon-circle-check"style="color: limegreen" v-show="this.airConditioner >=0"/>
					Number of Air-Conditioners:&nbsp;&nbsp;<b>{{airConditioner}}</b>
				</el-col>
				<el-col :span="2">
					<el-input type="number" v-model="airConditioner"/>
				</el-col>
			</el-row>
			<el-row style="margin-top: 2%">
				<el-col :offset="11">
					<el-button type="primary" :disabled="checkAdvanceField" @click="advanceBookingSearch">Search</el-button>
				</el-col>
			</el-row>
			<br>
			<el-table v-show="tableShow" v-loading="tableLoading"
			          element-loading-text="Loading..."
			          element-loading-spinner="el-icon-loading"
			          element-loading-background="rgba(0, 0, 0, 0.8)"
			          :data="table"
			          border>
				<el-table-column
						prop="roomName"
						fixed
						align="left"
						label="Room Name"
						width="150">
				</el-table-column>
				<el-table-column
						prop="address"
						label="Room Address"
						align="left"
						sortable>
				</el-table-column>
				<el-table-column
						prop="capacity"
						label="Capacity"
						align="left"
						sortable>
				</el-table-column>
				<el-table-column
						prop="airConditioner"
						label="Air-Conditioner"
						align="left"
						sortable>
				</el-table-column>
				<el-table-column
						prop="projection"
						label="Projection"
						align="left"
						sortable>
				</el-table-column>
				<el-table-column
						fixed="right"
						width="100"
						label="Action">
					<template slot-scope="scope" style="text-align: center">
						<el-button size="mini" type="primary" @click="advanceBookingSubmit(scope.row)">Book
						</el-button>
		
					</template>
				</el-table-column>
			</el-table>
		</el-dialog>
	</div>
</template>

<script>
    export default {
        name: "booking",
        data() {
            return {
                subject: '',
                date: '',
                startTime: '',
                endTime: '',
                roomId: '',
                loading: true,
	            capacity: null,
	            projection: 0,
	            airConditioner: 0,
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
	            table: [],
	            dialogVisible: false,
	            dialogStartTime: '',
	            dialogEndTime: '',
	            dialogRoomName: '',
	            advanceBookingView: false,
	            tableShow: false,
	            tableLoading: false,
	            generalUsers:[],
	            attenders:[]
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
                if(this.subject === '')
                {
                    return flag;
                }
                return !flag;
            },
	        checkAdvanceField(){
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
                if(this.subject === '')
                {
                    return flag;
                }
                if (this.capacity === null || this.capacity <= 0)
                {
                    return flag;
                }
                if (this.airConditioner < 0)
                {
                    return flag;
                }
                if (this.projection < 0)
                {
                    return flag;
                }
                return !flag;
	        },
	        
        },
        methods: {
			getUserGeneral(){
			  this.axios({
				  url: "/api/user/general",
				  method: "GET"
			  }).then((res)=>{
			      this.generalUsers = res.data.data;
			      console.log(this.generalUsers)
			  }).catch((err)=>{
			      this.$messageUtil.errorMessage(this);
			  })
			},
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
                    bookerEmail: this.$jwtUtil.getTokenEmail(),
	                subject:this.subject
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
                        });
	                    let recordId = res.data.data;
	                    if(this.attenders === "" || this.attenders.length ===0 || this.attenders === [])
	                    {
	                        return;
	                    }
	                    else{
                            this.axios({
                                url: "/api/booking/attender",
                                method: "POST",
                                params:{
                                    recordId: recordId,
                                    attenders: this.attenders.toString()
                                }
                            }).then((res)=>{
                                if(res.data.code === 200)
                                {
                                    this.$messageUtil.successMessage(this,"Adding Attenders Successfully!")
                                }
                                else {
                                    this.$messageUtil.warningMessage(this,"Adding Attenders Failed!")
                                }
                            }).catch((err)=>{
                                this.$messageUtil.errorMessage(this);
                            })
	                    }
	                    
	                    console.log(res.data.data)
	                    
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
                this.airConditioner = 0;
                this.capacity = null;
                this.projection = 0;
                this.table = [];
                this.attenders = [];
            },
            advanceBooking(){
                this.reset();
                this.advanceBookingView = true;
            },
	        advanceBookingSearch(){
                this.tableShow = true;
                this.tableLoading = true;
                this.axios({
	                url: "/api/booking/advance",
	                method: "GET",
	                params: {
	                    startTime: this.date + " " + this.startTime,
		                endTime: this.date + " " + this.endTime,
		                capacity: this.capacity,
		                airConditioner: this.airConditioner,
		                projection: this.projection
	                }
                }).then((res)=>{
                    this.table = res.data.data;
	                this.tableLoading = false;
                }).catch((err)=>{
                    this.$messageUtil.errorMessage(this)
                })
	        },
	        advanceBookingSubmit(data){
                let submitData = {
                    roomId: data.roomId,
                    startTime: this.date + " " + this.startTime,
                    endTime: this.date + " " + this.endTime,
                    bookerEmail: this.$jwtUtil.getTokenEmail(),
	                subject: this.subject
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
                        let recordId = res.data.data;
                        if(this.attenders === "" || this.attenders.length ===0 || this.attenders === [])
                        {
                            return;
                        }
                        else{
                            this.axios({
                                url: "/api/booking/attender",
                                method: "POST",
                                params:{
                                    recordId: recordId,
                                    attenders: this.attenders.toString()
                                }
                            }).then((res)=>{
                                if(res.data.code === 200)
                                {
                                    this.$messageUtil.successMessage(this,"Adding Attenders Successfully!")
                                }
                                else {
                                    this.$messageUtil.warningMessage(this,"Adding Attenders Failed!")
                                }
                            }).catch((err)=>{
                                this.$messageUtil.errorMessage(this);
                            })
                        }
                    } else {
                        this.$message({
                            message: res.data.msg,
                            type: "warning"
                        });
                    }
                }).catch((error) => {
                    this.$messageUtil.errorMessage(this)
                }).finally(() => {
                    this.reset();
                    this.advanceBookingView =false;
                    this.tableShow = false;
                })
	        },
	        handleClose(done){
                this.reset();
                this.tableLoading = false;
                this.tableShow = false;
                this.table = [];
		        done();
	        }
        },
        mounted() {
            setTimeout(this.changeStatus, 500);
            this.getRooms();
            this.getUserGeneral();

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
