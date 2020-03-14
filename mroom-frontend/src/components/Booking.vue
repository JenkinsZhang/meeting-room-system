<template>
	<div v-loading="loading" class="booking">
		<b-container fluid>
			<p style="color: red">Pay attention! The following fields are all required!</p>
			<b-row class="booking_row">
				<b-col md="6" style="line-height:40px;height:40px;text-align: left">
					<b-icon  icon="alert-circle" scale="1.5" variant="danger" v-show="this.date===''"/>
					<b-icon  icon="check-circle" scale="1.5" variant="success" v-show="this.date!==''"/>
					Select booking date:
					<b>{{date}}</b>
				</b-col>
				<b-col md="6">
					<el-date-picker
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
					<b-icon  icon="alert-circle" scale="1.5" variant="danger" v-show="this.start_time===''"/>
					<b-icon  icon="check-circle" scale="1.5" variant="success" v-show="this.start_time!==''"/>
					Select starting time:&nbsp;&nbsp;<b>{{start_time}}</b>
				</b-col>
				<b-col md="6">
					<el-time-select
							v-model="start_time"
							placeholder="Select starting time..."
							style="width: 100%;font-size: 1rem"
							:picker-options="{
                                start: '09:00',
                                step: '00:30',
                                end: '22:00',
                                maxTime: end_time
                                }">
					</el-time-select>
				</b-col>
			</b-row>
			<b-row class="booking_row">
				<b-col md="6" style="line-height:40px;text-align: left">
					<b-icon  icon="alert-circle" scale="1.5" variant="danger" v-show="this.end_time===''"/>
					<b-icon  icon="check-circle" scale="1.5" variant="success" v-show="this.end_time!==''"/>
					Select ending time:&nbsp;&nbsp;<b>{{end_time}}</b>
				</b-col>
				<b-col md="6">
					<el-time-select
							v-model="end_time"
							placeholder="select ending time..."
							style="width: 100%;font-size: 1rem"
							size="large"
							:picker-options="{
                                start: '09:00',
                                step: '00:30',
                                end: '22:00',
                                minTime: start_time
                                }">
					</el-time-select>
				</b-col>
			</b-row>
			<b-row class="booking_row">
				<b-col md="6" style="line-height:40px;text-align: left">
					<b-icon  icon="alert-circle" scale="1.5" variant="danger" v-show="this.room_id===''"/>
					<b-icon  icon="check-circle" scale="1.5" variant="success" v-show="this.room_id!==''"/>
					Choose your meeting room:&nbsp;&nbsp;<b>{{getRoomNameById}}</b>
				</b-col>
				<b-col md="6">
					<el-select v-model="room_id" style="width: 100%;" placeholder="Select room...">
						<el-option v-for="(room,index) in rooms"
						           style="font-size: 1rem;"
						           :key="index"
						           :value=room.room_id
						           :label="room.room_name"/>
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
                start_time: '',
                end_time: '',
                room_id: '',
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
                    if (this.rooms[i].room_id === this.room_id) {
                        this.room_name = this.rooms[i].room_name;
                        return this.room_name;
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
                if(this.start_time ==='')
                {
                    return flag;
                }
                if(this.end_time ==='')
                {
                    return flag;
                }
                if(this.room_id ==='')
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
                    url: 'api/roomInfo',
                }).then((res) => {
                    for (let i = 0; i < res.data.data.length; i++) {
                        let room = res.data.data[i];
                        this.rooms.push(room)
                    }
                })
            },
	        
            showMsgBox() {
                const h = this.$createElement;
                let start_time = this.date + " " + this.start_time;
                let end_time = this.date + " " + this.end_time;
                let room_name = this.room_name;
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
                                }},room_name
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
                            }},start_time
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
                            end_time
                        ),
                    ])
                ]);
                this.$bvModal.msgBoxConfirm(messageNode, {
                    title: "Please confirm your booking information",
                    centered: true
                }).then(value => {
                    if(value)
                    {
                        this.submit(start_time,end_time);
                    }
                }).catch(error=>{
                    alert(error)
                })
            },
	        submit(start_time,end_time){
                // console.log(start_time,end_time);
                this.loading = true;
                let submitData={
                    room_id: this.room_id,
	                start_time : start_time,
	                end_time : end_time,
	                booker_email : this.$jwtUtil.getTokenEmail()
                };
                this.axios({
	                method: 'POST',
	                data: submitData,
	                url: 'api/booking'
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
                this.start_time ='';
                this.end_time ='';
                this.room_id = '';
	        }
        },
        mounted() {
            setTimeout(this.changeStatus, 500);
            this.getRooms()
        }
    }
</script>

<style scoped>
	.booking {
		padding-top: 5%;
		white-space: pre;
	}
	
	.booking_row:nth-of-type(n+2) {
		margin-top: 5%;
	}
	
	.booking_row:nth-of-type(5) {
		margin-top: 10%;
	}
	*{
		font-size: 1rem;
	}
</style>
