<template>
	<div v-loading="loading" class="booking">
		<b-container fluid>
			<p style="color: red">请注意！以下字段均为必填字段</p>
			<b-row class="booking_row">
				<b-col md="4" style="line-height:40px;height:40px;text-align: left">
					<b-icon  icon="alert-circle" scale="1.5" variant="danger" v-show="this.date===''"/>
					<b-icon  icon="check-circle" scale="1.5" variant="success" v-show="this.date!==''"/>
					请选择预订日期:&nbsp;<b>{{date}}</b>
				</b-col>
				<b-col md="8">
					<el-date-picker
							v-model="date"
							type="date"
							value-format="yyyy-MM-dd"
							placeholder="选择预订日期"
							style="width: 100%"
							:picker-options="pickerOptions">
					</el-date-picker>
				</b-col>
			</b-row>
			
			<b-row class="booking_row">
				<b-col md="4" style="line-height:40px;text-align: left">
					<b-icon  icon="alert-circle" scale="1.5" variant="danger" v-show="this.start_time===''"/>
					<b-icon  icon="check-circle" scale="1.5" variant="success" v-show="this.start_time!==''"/>
					请选择会议开始时间:&nbsp;&nbsp;<b>{{start_time}}</b>
				</b-col>
				<b-col md="8">
					<el-time-select
							v-model="start_time"
							placeholder="选择开始时间"
							style="width: 100%"
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
				<b-col md="4" style="line-height:40px;text-align: left">
					<b-icon  icon="alert-circle" scale="1.5" variant="danger" v-show="this.end_time===''"/>
					<b-icon  icon="check-circle" scale="1.5" variant="success" v-show="this.end_time!==''"/>
					请选择会议结束时间:&nbsp;&nbsp;<b>{{end_time}}</b>
				</b-col>
				<b-col md="8">
					<el-time-select
							v-model="end_time"
							placeholder="选择结束时间"
							style="width: 100%"
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
				<b-col md="4" style="line-height:40px;text-align: left">
					<b-icon  icon="alert-circle" scale="1.5" variant="danger" v-show="this.room_id===''"/>
					<b-icon  icon="check-circle" scale="1.5" variant="success" v-show="this.room_id!==''"/>
					请选择会议室:&nbsp;&nbsp;<b>{{getRoomNameById}}</b>
				</b-col>
				<b-col md="8">
					<el-select v-model="room_id" style="width: 100%">
						<el-option v-for="(room,index) in rooms"
						           :key="index"
						           :value=room.room_id
						           :label="room.room_name"/>
					</el-select>
				</b-col>
			</b-row>
			<b-row class="booking_row">
				<b-col md="12">
					<b-button :disabled="checkField" @click="showMsgBox" style="margin-left: 25%;width: 100px" variant="outline-primary">提交
					</b-button>
					<b-button @click="reset" style="margin-left: 25%;width: 100px" variant="outline-danger">重置</b-button>
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
                        return time.getTime() < Date.now();
                    },
                    shortcuts: [{
                        text: '今天',
                        onClick(picker) {
                            picker.$emit('pick', new Date());
                        }
                    }, {
                        text: '明天',
                        onClick(picker) {
                            const date = new Date();
                            date.setTime(date.getTime() + 3600 * 1000 * 24);
                            picker.$emit('pick', date);
                        }
                    }, {
                        text: '一周后',
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
                            h('span', "您预订的会议室为:"),
                            h('span', room_name),
	                        h('span',{
                                domProps: {
                                    innerHTML: "<br><br>"
                                }
	                        })
                        ]
                    ),
                    h('div', [
                        h('span', "会议开始的时间为:"),
                        h('span', start_time),
                        h('span',{
                            domProps: {
                                innerHTML: "<br><br>"
                            }
                        })
                    ]),
                    h('div', [
                        h('span', "会议结束的时间为:"),
                        h('span', end_time)
                    ])
                ]);
                this.$bvModal.msgBoxConfirm(messageNode, {
                    title: "请确认您的预订信息",
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
                        this.$bvModal.msgBoxOk("预订成功！",{
                            centered:true
                        })
                    }
                    else
                    {
                        this.$bvModal.msgBoxOk("预订失败！",{
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
</style>
