<template>
	<div v-loading =loading >
		<b-container fluid>
			<p style="color: red">Pay attention! The following fields are all required!</p>
			<b-row class="booking_row">
				<b-col md="6" style="line-height:40px;height:40px;text-align: left">
					<b-icon  icon="alert-circle" scale="1.5" variant="danger" v-show="this.date ===null || this.date === undefined || this.date === ''"/>
					<b-icon  icon="check-circle" scale="1.5" variant="success" v-show="this.date"/>
					Select booking date:
					<b>{{date}}</b>
				</b-col>
				<b-col md="6">
					<el-date-picker
							:editable="false"
							v-model="date"
							type="date"
							value-format="yyyy-MM-dd"
							placeholder="Select booking date..."
							style="width: 100%;font-size: 1rem"
							:picker-options="pickerOptions">
					</el-date-picker>
				</b-col>
			</b-row>
			
			<b-row class="booking_row">
				<b-col md="6" style="line-height:40px;text-align: left">
					<b-icon  icon="alert-circle" scale="1.5" variant="danger" v-show="this.startTime ===null || this.startTime === undefined || this.startTime === ''"/>
					<b-icon  icon="check-circle" scale="1.5" variant="success" v-show="this.startTime"/>
					Select starting time:&nbsp;&nbsp;<b>{{startTime}}</b>
				</b-col>
				<b-col md="6">
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
				</b-col>
			</b-row>
			<b-row class="booking_row">
				<b-col md="6" style="line-height:40px;text-align: left">
					<b-icon  icon="alert-circle" scale="1.5" variant="danger" v-show="this.endTime ===null || this.endTime === undefined || this.endTime === ''"/>
					<b-icon  icon="check-circle" scale="1.5" variant="success" v-show="this.endTime"/>
					Select ending time:&nbsp;&nbsp;<b>{{endTime}}</b>
				</b-col>
				<b-col md="6">
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
				</b-col>
			</b-row>
			<b-row class="booking_row">
				<b-col md="6" style="line-height:40px;text-align: left">
					<b-icon  icon="alert-circle" scale="1.5" variant="danger" v-show="this.roomId===''"/>
					<b-icon  icon="check-circle" scale="1.5" variant="success" v-show="this.roomId!==''"/>
					Choose your meeting room:&nbsp;&nbsp;<b>{{getRoomNameById}}</b>
				</b-col>
				<b-col md="6">
					<el-select v-model="roomId" style="width: 100%;" placeholder="Select room..." no-data-text="No data">
						<el-option v-for="(room,index) in rooms"
						           style="font-size: 1rem;"
						           :key="index"
						           :value=room.roomId
						           :label="room.roomName" />
					</el-select>
				</b-col>
			</b-row>
			<b-row class="booking_row">
			<b-col md="12">
				<b-button :disabled="checkField" @click="showMsgBox" style="margin-left: 25%;width: 100px" variant="outline-primary">Submit
				</b-button>
				<b-button @click="reset" style="margin-left: 25%;width: 100px" variant="outline-danger">Reset</b-button>
			</b-col>
		</b-row>
		
		</b-container>
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
                rooms: []
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
            checkField(){
                let flag = true;
                if(this.date ==='')
                {
                    return flag;
                }
                if(this.startTime ==='')
                {
                    return flag;
                }
                if(this.endTime ==='')
                {
                    return flag;
                }
                if(this.roomId ==='')
                {
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
                const h = this.$createElement;
                let startTime = this.date + " " + this.startTime;
                let endTime = this.date + " " + this.endTime;
                let roomName = this.roomName;
                const messageNode = h('div', [
                    h('div', [
                            h('span', "The room reserved for you is: "),
                            h('span',{
                                domProps: {
                                    innerHTML: "&nbsp;&nbsp;&nbsp;&nbsp;"
                                }
                            }),
                            h('span', {style: {
                                    fontWeight: 'bold'
                                }},roomName
                            ),
	                        h('span',{
                                domProps: {
                                    innerHTML: "<br><br>"
                                }
	                        })
                        ]
                    ),
                    h('div', [
                        h('span', "Starting time: "),
                        h('span',{
                            domProps: {
                                innerHTML: "&nbsp;&nbsp;&nbsp;&nbsp;"
                            }
                        }),
                        h('span', {style: {
                                fontWeight: 'bold'
                            }},startTime
                        ),
                        h('span',{
                            domProps: {
                                innerHTML: "<br><br>"
                            }
                        })
                    ]),
                    h('div', [
                        h('span', "Closing time: "),
                        h('span',{
                            domProps: {
                                innerHTML: "&nbsp;&nbsp;&nbsp;&nbsp;"
                            }
                        }),
                        h('span',
	                        {style: {
                                    fontWeight: 'bold'
                                }},
                            endTime
                        ),
                    ])
                ]);
                this.$bvModal.msgBoxConfirm(messageNode, {
                    title: "Please confirm your booking information",
                    centered: true
                }).then(value => {
                    if(value)
                    {
                        this.submit(startTime,endTime);
                    }
                }).catch(error=>{
                    alert(error)
                })
            },
	        submit(startTime,endTime){
                // console.log(startTime,endTime);
                this.loading = true;
                let submitData={
                    roomId: this.roomId,
	                startTime : startTime,
	                endTime : endTime,
	                bookerEmail : this.$jwtUtil.getTokenEmail()
                };
                this.axios({
	                method: 'POST',
	                data: submitData,
	                url: '/api/booking'
                }).then((res)=>{
                    this.loading = false;
                    if(res.data.code === 200)
                    {
                        this.$bvModal.msgBoxOk("Booking success！",{
                            centered:true
                        })
                    }
                    else
                    {
                        this.$bvModal.msgBoxOk(res.data.msg,{
                            centered:true
                        })
                    }
                }).catch((error)=>{
                    this.loading = false;
                    this.$bvModal.msgBoxOk("预订失败！服务器错误",{
                        centered:true
                    })
                }).finally(()=>{
                    this.reset();
                })
	        },
	        reset(){
                this.date ='';
                this.startTime ='';
                this.endTime ='';
                this.roomId = '';
	        }
        },
        mounted() {
            setTimeout(this.changeStatus, 500);
            this.getRooms();
	        
        },
	    created() {
            if(this.$route.params.roomId !== null)
            {
                this.roomId = this.$route.params.roomId;
            }
        }
    }
</script>

<style scoped>
	
	.booking_row:nth-of-type(n+2) {
		padding-top: 5%;
	}
	
	.booking_row:nth-of-type(5) {
		padding-top: 10%;
	}
	
</style>
