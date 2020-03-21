<template>
	<div v-loading="loading">
		<b-card class="editCard" v-show="cardShow">
			<b-container fluid class="booking">
				<p style="color: red">Pay attention! The following fields are all required!</p>
				<b-row class="booking_row">
					<b-col md="6" style="line-height:40px;height:40px;text-align: left">
						Select booking date:
						<b>{{selectedRecord.date}}</b>
					</b-col>
					<b-col md="6">
						<el-date-picker
								:editable="true"
								v-model="selectedRecord.date"
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
						Select starting time:&nbsp;&nbsp;<b>{{selectedRecord.startTime}}</b>
					</b-col>
					<b-col md="6">
						<el-time-select
								v-model="selectedRecord.startTime"
								:editable="false"
								placeholder="Select starting time..."
								style="width: 100%;font-size: 1rem"
								:picker-options="{
                                start: '09:00',
                                step: '00:30',
                                end: '22:00',
                                maxTime: selectedRecord.endTime
                                }">
						</el-time-select>
					</b-col>
				</b-row>
				<b-row class="booking_row">
					<b-col md="6" style="line-height:40px;text-align: left">
						Select ending time:&nbsp;&nbsp;<b>{{selectedRecord.endTime}}</b>
					</b-col>
					<b-col md="6">
						<el-time-select
								v-model="selectedRecord.endTime"
								placeholder="select ending time..."
								style="width: 100%;font-size: 1rem"
								size="large"
								:editable="false"
								:picker-options="{
                                start: '09:00',
                                step: '00:30',
                                end: '22:00',
                                minTime: selectedRecord.startTime
                                }">
						</el-time-select>
					</b-col>
				</b-row>
				<b-row class="booking_row">
					<b-col md="6" style="line-height:40px;text-align: left">
						Choose your meeting room:&nbsp;&nbsp;<b>{{this.selectedRecord.roomName}}</b>
					</b-col>
					<b-col md="6">
						<el-select v-model="selectedRecord.roomName" style="width: 100%;" placeholder="Select room..."
						           no-data-text="No data">
							<el-option v-for="(room,index) in rooms"
							           style="font-size: 1rem;"
							           :key="index"
							           :value=room.roomName
							           :label="room.roomName"/>
						</el-select>
					</b-col>
				</b-row>
				<b-row class="booking_row">
					<b-col md="12">
						<b-button @click="submitEdit" style="width: 100px" variant="outline-primary">Submit
						</b-button>
						<b-button @click="cancelEdit" style="margin-left: 20%;width: 100px" variant="outline-danger">
							Cancel
						</b-button>
					</b-col>
				</b-row>
			
			</b-container>
		</b-card>
		<div :class="{'darker':darkActive}"></div>
		<!--		{{historyRecords}}-->
		<el-table
				:data="historyRecords"
				stripe
				style="width: 100%;"
				@filter-change="filter"
		>
			<el-table-column type="expand">
				<template slot-scope="props">
					<el-form label-position="left" inline class="history-table-expand">
						<el-form-item>
							<label>Record ID</label>
							<span><b>{{ props.row.recordId }}</b></span>
						</el-form-item>
						<el-form-item>
							<label>Start Time</label>
							<span><b>{{ props.row.startTime }}</b></span>
						</el-form-item>
						<el-form-item>
							<label>End Time</label>
							<span><b>{{ props.row.endTime }}</b></span>
						</el-form-item>
						<el-form-item>
							<label>Create Time</label>
							<span><b>{{ props.row.createTime }}</b></span>
						</el-form-item>
						<el-form-item>
							<label>Room Name</label>
							<span><b>{{ props.row.roomName }}</b></span>
						</el-form-item>
						<el-form-item>
							<label>Room Address</label>
							<span><b>{{ props.row.roomAddress }}</b></span>
						</el-form-item>
						<el-form-item>
							<label>Max People</label>
							<span><b>{{ props.row.maxPeople}}</b></span>
						</el-form-item>
						<el-form-item>
							<label>Projectors</label>
							<span><b>{{ props.row.projection}}</b></span>
						</el-form-item>
						<el-form-item>
							<label>Air Conditioners</label>
							<span><b>{{ props.row.airConditioner}}</b></span>
						</el-form-item>
					
					</el-form>
				</template>
			</el-table-column>
			<el-table-column
					label="ID"
					prop="recordId"
					sortable
					fixed
					width="60px">
			</el-table-column>
			<el-table-column
					label="Start Time"
					prop="startTime"
					sortable
			>
			</el-table-column>
			<el-table-column
					label="End Time"
					prop="endTime"
			>
			</el-table-column>
			<el-table-column
					label="Room Name"
					prop="roomName"
			>
			</el-table-column>
			<el-table-column
					prop="status"
					label="Status"
					:filters="[
					    {text:'completed',value:'completed'},
						{text:'uncompleted',value:'uncompleted'},
						{text:'canceled',value:'canceled'}
						]"
			>
				<template slot-scope="scope">
					<el-tag :type="tagType(scope.row.status)" style="font-size: 14px" disable-transitions>
						{{scope.row.status}}
					</el-tag>
				</template>
			</el-table-column>
			<el-table-column label="Action" fixed="right" width="250">
				<template slot-scope="props">
					<el-button
							size="mini"
							:disabled="props.row.status !=='uncompleted'"
							@click="handleEdit(props.$index, props.row)">Edit
					</el-button>
					<el-button
							size="mini"
							type="danger"
							:disabled="props.row.status !=='uncompleted'"
							@click="handleCancel(props.$index, props.row)">Cancel
					</el-button>
					<el-button
							size="mini"
							type="success"
							:disabled="props.row.status !=='uncompleted'"
							@click="handleSuccess(props.$index, props.row)">Complete
					</el-button>
				</template>
			</el-table-column>
		
		</el-table>
		<el-pagination
				background
				layout="prev, pager, next"
				:total="totalItems"
				@current-change="getPageData"
				class="history-pagination"
				:page-size="pageSize"
				:current-page.sync="currentPage"
		>
		</el-pagination>
	</div>
</template>

<script>
    import * as qs from "qs";

    export default {
        name: "history",

        data() {
            return {
                darkActive: false,
                historyRecords: [],
                currentPage: 1,
                totalItems: 50,
                pageSize: 8,
                newFilters: [-1, 0, 1],
                selectedRecord:{
                    recordId: '',
                    date: '',
	                startTime: '',
	                endTime: '',
	                roomName: '',
                    creationTime: '',
	                recordId: null,
                },
                loading: false,
	            cardShow: false,
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
        mounted() {
            this.getPageData(1).then((res) => {
                if (res.data.code === 200) {
                    this.historyRecords = [];
                    this.historyRecords = res.data.data;
                    this.renderStatus();
                    // console.log(this.historyRecords)
                    // console.log("wocaonima")
                } else {
                    alert(res.data.msg)
                }
            }).catch((error) => {
                console.log(error)
            });

            this.getRecordsCount().then((res) => {
                if (res.data.code === 200) {
                    this.totalItems = res.data.data;
                } else {
                    alert(res.data.msg)
                }
            }).catch((error) => {
                console.log(error)
            });
	        
            this.getRooms();
	        
	        this.cardShow = false;

        },
        methods: {
            getPageData(val) {
                this.currentPage = val;
                return this.axios({
                    url: "api/booking/history/" +
                        this.$jwtUtil.getTokenEmail() + "/"
                        + this.currentPage + "/" + this.pageSize,
                    method: 'GET',
                    params: {
                        filters: this.newFilters
                    },
                    paramsSerializer: params => {
                        return qs.stringify(params, {indices: false})
                    }
                }).then((res) => {
                    if (res.data.code === 200) {
                        this.historyRecords = [];
                        this.historyRecords = res.data.data;
                        this.renderStatus();
                    } else {
                        this.historyRecords = [];
                    }
                }).catch((error) => {
                    console.log(error)
                })
            },
            getRecordsCount() {
                return this.axios({
                    url: "api/booking/history/count/" +
                        this.$jwtUtil.getTokenEmail(),
                    method: 'GET',
                    params: {
                        filters: this.newFilters
                    },
                    paramsSerializer: params => {
                        return qs.stringify(params, {indices: false})
                    }
                }).then((res) => {
                    if (res.data.code === 200) {
                        this.totalItems = res.data.data;
                    } else {
                        // alert(res.data.msg)
                        this.totalItems = 0;
                    }

                }).catch((error) => {
                    console.log(error)
                })
            },
            renderStatus() {
                this.historyRecords.forEach((record, index) => {
                    switch (record.status) {
                        case 0:
                            record.status = "uncompleted";
                            break;
                        case 1:
                            record.status = "completed";
                            break;
                        case -1:
                            record.status = "canceled";
                            break;
                    }
                })
            },
            tagType(val) {
                switch (val) {
                    case "uncompleted":
                        return "primary";
                    case "completed":
                        return "success";
                    case "canceled":
                        return "danger"
                }
                return null;
            },
            handleCancel(index, row) {
                this.$bvModal.msgBoxConfirm("Are you sure you want to cancel this meeting?",{
                    title:"Complete",
                    centered: true
                }).then(value=>{
                    if(value)
                    {
                        this.axios({
                            method: "GET",
                            url: "api/booking/history/"+row.recordId+"/cancel"
                        }).then((res)=>{
                            if(res.data.code === 200)
                            {
                                this.$message({
                                    message: "Cancel success!",
                                    type: "success"
                                });
                                this.refresh();
                            }
                            else{
                                this.$message({
                                    message: "Cancel failed!",
                                    type: "warning"
                                })
                            }
                        }).catch((error)=>{
                            this.$message({
                                message: "Server Error",
                                type: "danger"
                            })
                        })

                    }
                })
            },
            handleEdit(index, row) {
                // console.log(index, row);
                console.log(row);
                let rowStartTime = row.startTime.split(" ");
                let rowEndTime = row.endTime.split(" ");
                this.selectedRecord.date = rowStartTime[0];
                this.selectedRecord.startTime = rowStartTime[1];
                this.selectedRecord.endTime = rowEndTime[1];
                this.selectedRecord.roomName = row.roomName;
                this.selectedRecord.creationTime = row.creationTime;
                this.selectedRecord.recordId = row.recordId;
                this.cardShow=true;
                this.darkActive = true;
            },
	        handleSuccess(index,row) {
                this.$bvModal.msgBoxConfirm("Are you sure this meeting has finished?",{
                    title:"Complete",
                    centered: true
                }).then(value=>{
                    if(value)
                    {
	                    this.axios({
		                    method: "GET",
		                    url: "api/booking//history/"+row.recordId+"/complete"
	                    }).then((res)=>{
	                        if(res.data.code === 200)
	                        {
	                            this.$message({
		                            message: "Complete success!",
		                            type: "success"
	                            });
		                        this.refresh()
	                        }
	                        else{
                                this.$message({
                                    message: "Complete failed!",
                                    type: "warning"
                                })
	                        }
	                    }).catch((error)=>{
                            this.$message({
                                message: "Server Error",
                                type: "danger"
                            })
	                    })
	                    
                    }
                })
	        },
            cancelEdit(index,row) {
                this.changeStatus();
                setTimeout(this.changeStatus,500);
                this.darkActive = false;
                this.cardShow = false;
            },
	        submitEdit(index,row){
                let roomId = null;
                let recordId = this.selectedRecord.recordId;
                let startTime = this.selectedRecord.date + " " + this.selectedRecord.startTime;
                let endTime = this.selectedRecord.date + " " + this.selectedRecord.endTime;
                let creationTime = this.selectedRecord.creationTime;
                let bookerEmail = this.$jwtUtil.getTokenEmail();
                let status = 0;
                
                for (let i = 0; i < this.rooms.length; i++) {
                    if (this.rooms[i].roomName === this.selectedRecord.roomName) {
                        roomId = this.rooms[i].roomId;
                        break;
                    }
                }
		       this.axios({
			       method: 'POST',
			       data: {
                       recordId: recordId,
                       startTime: startTime,
                       endTime: endTime,
				       roomId: roomId,
				       creationTime: creationTime,
				       bookerEmail: bookerEmail,
				       status: status
			       },
			       url: "api/booking/history/edit"
		       }).then((res)=>{
		           if(res.data.code === 200) {
		               this.cardShow = false;
		               this.darkActive = false;
                       this.$message({
                           message: "Edit Success!",
                           type: "success"
                       });
                       this.refresh();
		           }
		           else {
		               this.$message({
			               message: res.data.msg,
			               type: "warning"
		               })
		           }
		       }).catch((error)=>{
                   this.$message({
                       message: "Server Error!",
                       type: "danger"
                   })
		       })
	        },
            async filter(filters) {
                // console.log(filters);
                this.currentPage = 1;
                this.newFilters = null;
                for (let key in filters) {
                    this.newFilters = filters[key]
                }
                // console.log("newFilters:"+this.newFilters);
                let temp_filters = [];
                for (let i = 0; i < this.newFilters.length; i = i + 1) {
                    if (this.newFilters[i] === "completed") {
                        temp_filters.push(1)
                    } else if (this.newFilters[i] === "uncompleted") {
                        temp_filters.push(0)
                    } else if (this.newFilters[i] === "canceled") {
                        temp_filters.push(-1)
                    }
                }
                this.newFilters = temp_filters;
                if (this.newFilters.length === 0 || this.newFilters === '') {
                    this.newFilters = [0, -1, 1];
                }
                console.log("newFilters after processing " + this.newFilters);
                console.log("========================1   " + this.newFilters);
                this.getPageData(this.currentPage).then((res) => {
                    if (res.data.code === 200) {
                        this.historyRecords = [];
                        this.historyRecords = res.data.data;
                        this.renderStatus();
                        // console.log(this.historyRecords)
                        // console.log("wocaonima")
                    } else {
                        alert(res.data.msg)
                    }
                }).catch((error) => {
                    console.log(error)
                });
                // console.log("========================2   " +this.newFilters)
                this.getRecordsCount().then((res) => {
                    if (res.data.code === 200) {
                        this.totalItems = res.data.data;
                    } else {
                        alert(res.data.msg)
                    }
                }).catch((error) => {
                    console.log(error)
                });
                // console.log("========================3")
                // console.log(this.newFilters)
                // console.log(filters)
            },
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
	        async refresh(){
                await this.getPageData(1);
                await this.getRecordsCount();
	        }
	        
        }
       
    }
</script>

<style scoped>
	.darker {
		background-color: rgba(0, 0, 0, 0.4);
		height: 100%;
		z-index: 10;
		position: fixed;
		width: 100%;
		left: 0;
		top: 0;
	}
	
	.editCard {
		width: 70%;
		position: fixed;
		left: 20%;
		text-align: center;
		z-index: 100;
		top: 10%;
		height: 85%;
		
	}
	
	.history-table-expand label {
		width: 150px;
		color: #99a9bf;
	}
	
	.history-table-expand .el-form-item {
		margin-right: 0;
		margin-bottom: 0;
		width: 50%;
		float: left;
	}
	
	.history-pagination {
		text-align: center;
		height: 50px;
		margin-top: 2%;
	}
	
	.booking {
		padding-top: 3%;
		white-space: pre;
	}
	
	.booking_row:nth-of-type(n+2) {
		margin-top: 5%;
	}
	
	.booking_row:nth-of-type(5) {
		margin-top: 10%;
	}
	
	* {
		font-size: 1rem;
	}
</style>


<style>
	.el-table .cell {
		line-height: 40px;
	}
</style>
