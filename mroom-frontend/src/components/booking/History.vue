<template>
	<div>
		<el-card class="editCard" v-show="cardShow" >
				<p style="color: red">Pay attention! The following fields are all required!</p>
				<el-row class="booking_row">
					<el-col :span="12" style="line-height:40px;height:40px;text-align: left">
						Select booking date:
						<b>{{selectedRecord.date}}</b>
					</el-col>
					<el-col :span="12">
						<el-date-picker
								:editable="true"
								v-model="selectedRecord.date"
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
						Select starting time:&nbsp;&nbsp;<b>{{selectedRecord.startTime}}</b>
					</el-col>
					<el-col :span="12">
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
					</el-col>
				</el-row>
				<el-row class="booking_row">
					<el-col :span="12" style="line-height:40px;text-align: left">
						Select ending time:&nbsp;&nbsp;<b>{{selectedRecord.endTime}}</b>
					</el-col>
					<el-col :span="12">
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
					</el-col>
				</el-row>
				<el-row class="booking_row">
					<el-col :span="12" style="line-height:40px;text-align: left">
						Choose your meeting room:&nbsp;&nbsp;<b>{{this.selectedRecord.roomName}}</b>
					</el-col>
					<el-col :span="12">
						<el-select v-model="selectedRecord.roomName" style="width: 100%;" placeholder="Select room..."
						           no-data-text="No data">
							<el-option v-for="(room,index) in rooms"
							           style="font-size: 1rem;"
							           :key="index"
							           :value=room.roomName
							           :label="room.roomName"/>
						</el-select>
					</el-col>
				</el-row>
				<el-row class="booking_row" style="text-align: center">
					<el-col :span="24">
						<el-button @click="submitEdit" style="width: 100px" type="primary">Submit
						</el-button>
						<el-button @click="cancelEdit" style="margin-left: 20%;width: 100px" type="danger">
							Cancel
						</el-button>
					</el-col>
				</el-row>
			
		</el-card>
		<div :class="{'darker':darkActive}"></div>
		<!--		{{historyRecords}}-->
		<div>
			<el-row>
				<el-col :span="16">
					<div style="display: flex;justify-content: space-between;">
						<div>
							<el-date-picker
									v-model="date"
									prefix-icon="el-icon-search"
									type="date"
									value-format="yyyy-MM-dd"
									placeholder="Please select date"
									style="width: 350px;margin-right: 10px"
									clearable
									@clear="getPageData"
									@keydown.enter.native="getPageData">
							</el-date-picker>
							<el-button icon="el-icon-search" type="primary" @click="getPageData">Search</el-button>
						</div>
					</div>
				</el-col>
				<el-col style="margin-top: 0.5%;font-size: 20px" :span="4" :offset="4">
					<el-button @click="autoComplete" type="info">Complete Overdue </el-button>
				</el-col>
			</el-row>
			
		</div>
		<el-table
				:data="historyRecords"
				stripe
				style="width: 100%;margin-left: 20px"
				@filter-change="filter"
				v-loading="loading"
				element-loading-text="Loading..."
				element-loading-spinner="el-icon-loading"
				element-loading-background="rgba(0, 0, 0, 0.8)"
		>
			<el-table-column type="expand">
				<template slot-scope="props">
					<el-form label-position="left" inline class="history-table-expand">
						<el-form-item>
							<label>Record ID: </label>
							<span><b>{{ props.row.recordId }}</b></span>
						</el-form-item>
						<el-form-item>
							<label>Start Time: </label>
							<span><b>{{ props.row.startTime }}</b></span>
						</el-form-item>
						<el-form-item>
							<label>End Time: </label>
							<span><b>{{ props.row.endTime }}</b></span>
						</el-form-item>
						<el-form-item>
							<label>Creation Time: </label>
							<span><b>{{ props.row.creationTime }}</b></span>
						</el-form-item>
						<el-form-item>
							<label>Room Name: </label>
							<span><b>{{ props.row.roomName }}</b></span>
						</el-form-item>
						<el-form-item>
							<label>Room Address: </label>
							<span><b>{{ props.row.roomAddress }}</b></span>
						</el-form-item>
						<el-form-item>
							<label>Capacity: </label>
							<span><b>{{ props.row.maxPeople}}</b></span>
						</el-form-item>
						<el-form-item>
							<label>Projectors: </label>
							<span><b>{{ props.row.projection}}</b></span>
						</el-form-item>
						<el-form-item>
							<label>Air Conditioners: </label>
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
					width="80px">
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
			<el-table-column label="Action" fixed="right" width="280" >
				<template slot-scope="props">
					<el-button
							size="mini"
							:disabled="props.row.status !=='uncompleted'"
							style="width: 40%"
							@click="handleEdit(props.$index, props.row)">Edit
					</el-button>
					<el-button
							size="mini"
							type="success"
							:disabled="props.row.status !=='uncompleted'"
							style="width: 40%"
							@click="handleSuccess(props.$index, props.row)">Complete
					</el-button>
					<br>
					<el-button
							size="mini"
							type="danger"
							:disabled="props.row.status !=='uncompleted'"
							style="width: 40%"
							@click="handleCancel(props.$index, props.row)">Cancel
					</el-button>
					<el-button
							size="mini" style="width: 40%"
							:disabled="props.row.status !=='uncompleted'"
							type="primary"
							@click="handleAttender(props.$index, props.row)">Attenders
					</el-button>
				</template>
			</el-table-column>
		
		</el-table>
		<el-pagination
				background
				layout="sizes, prev, pager, next, jumper, ->, total, slot"
				:total="totalItems"
				@current-change="currentChange"
				@size-change="sizeChange"
				class="history-pagination"
				:page-size="pageSize"
				:current-page.sync="currentPage"
				style="bottom: 0"
		>
		</el-pagination>
		<el-dialog :visible.sync="attenderDialog"
		           width="55%"
		           title="Attenders"
		style="margin-top: 10%"
		           @close="reset">
			<el-row class="booking_row">
				<el-col :span="8" style="line-height:40px;text-align: left">
					Choose attenders:&nbsp;
				</el-col>
				<el-col :span="15" :offset="1">
					<el-select v-model="attenders" style="width: 100%;font-size:30px"  size="medium" placeholder="Select attenders..." multiple no-data-text="No data">
						<el-option
								style="font-size: 20px"
								v-for="user in generalUsers"
								:key="user.id"
								:label="user.username"
								:value="user.id">
							<span style="float: left;font-size:16px">{{ user.username }}</span>
							<span style="float: right;margin-right:5%;color: #8492a6; font-size: 18px">{{ user.email }}</span>
						</el-option>
					</el-select>
				</el-col>
			</el-row>
			<br>
			<el-button  style="margin-left: 35%" size="mini" type="success" @click="updateAttenders">Update</el-button>
			<el-button  v-loading="notifyLoading"
			            element-loading-text="Loading..."
			            element-loading-spinner="el-icon-loading"
			            element-loading-background="rgba(0, 0, 0, 0.8)"
			            size="mini" type="primary" @click="notifyAttenders">Notify</el-button>
		</el-dialog>
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
                totalItems: 0,
                pageSize: 10,
                newFilters: [-1, 0, 1],
                selectedRecord:{
                    date: '',
	                startTime: '',
	                endTime: '',
	                roomName: '',
                    creationTime: '',
	                recordId: null,
	                roomAddress: ''
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
	            rooms: [],
	            date: null,
                generalUsers:[],
	            attenders: [],
	            attenderFlag: false,
	            attenderDialog: false,
	            attenderRecordId: null,
	            attenderRecord:{
                    startTime: null,
		            endTime: null,
		            address: null,
		            roomName: null
	            },
                notifyLoading: false,
	            beforeEdit: null,
            }
        },
        async mounted() {
            this.cardShow = false;
            this.changeStatus();
            await this.getRooms();
            await this.getPageData();
            this.changeStatus();
            this.getUserGeneral();
            window.scrollTo(0,0)
	        
	        

        },
        methods: {
            notifyAttenders(){
                console.log(this.attenderRecord);
                this.notifyLoading = true;
                this.axios({
	                url: "/api/booking/notify",
	                method: "POST",
	                params:{
	                    recordId: this.attenderRecordId,
                        subject:"booking",
                        startTime:this.attenderRecord.startTime,
                        endTime:this.attenderRecord.endTime,
                        roomName:this.attenderRecord.roomName,
                        roomAddress:this.attenderRecord.address,
	                }
                }).then((res)=>{
                    if(res.data.code === 200)
                    {
                        this.$messageUtil.successMessage(this,res.data.msg);
                        
                    }
                    else
                    {
                        this.$messageUtil.warningMessage(this,res.data.msg);
                    }
                }).catch((err)=>{
                    this.$messageUtil.errorMessage(this);
                }).finally(()=>{
                    this.notifyLoading = false;
                    this.reset();
                    this.attenderDialog = false;
                })
            },
            updateAttenders(){
                
                if(this.attenderFlag)
                {
                    this.axios({
                        url: "/api/booking/attender",
                        method: "POST",
                        params:{
                            recordId: this.attenderRecordId,
                            attenders: this.attenders.toString()
                        }
                    }).then((res)=>{
                        if(res.data.code === 200)
                        {
                            this.$messageUtil.successMessage(this,"Adding Attenders Successfully!")
	                        this.attenderFlag = false;
                        }
                        else {
                            this.$messageUtil.warningMessage(this,"Adding Attenders Failed!")
                        }
                    }).catch((err)=>{
                        this.$messageUtil.errorMessage(this);
                    })
                }
                else {
                    this.axios({
                        url:"/api/booking/attender",
                        method: "PUT",
                        params:{
                            recordId: this.attenderRecordId,
                            attenders: this.attenders.toString()
                        }
                    }).then((res)=>{
                        if(res.data.code === 200)
                        {
                            this.$messageUtil.successMessage(this,"Update Successfully!")
                        }else
                        {
                            this.$messageUtil.warningMessage(this,"Update Failed!");
                        }
                    }).catch((err)=>{
                        this.$messageUtil.errorMessage(this);
                    })
                }
                
            },
            handleAttender(index,row){
                this.attenderDialog = true;
                this.attenderRecordId = row.recordId;
                this.attenderRecord.address = row.roomAddress;
                this.attenderRecord.roomName = row.roomName;
                this.attenderRecord.startTime = row.startTime;
                this.attenderRecord.endTime = row.endTime;
                this.axios({
	                url: "/api/booking/attender",
	                method: "GET",
	                params:{
	                    recordId: row.recordId
	                }
                }).then((res)=>{
                    if(res.data.code === 200)
                    {
                        if(res.data.data!= null)
                        {
                            let dataString = res.data.data.attenders.toString().split(",")
	                        this.attenders = dataString.map(function (data) {
								return +data;
                            })
	                        console.log(this.attenders);
                        }
                        else {
                            this.attenderFlag = true;
                        }
                        
                    }
                })
            },
            getUserGeneral(){
                this.axios({
                    url: "/api/user/general",
                    method: "GET"
                }).then((res)=>{
                    this.generalUsers = res.data.data;
                    // console.log(this.generalUsers)
                }).catch((err)=>{
                    this.$messageUtil.errorMessage(this);
                })
            },
            currentChange(val) {
                this.currentPage = val;
                this.getPageData();
            },
	        sizeChange(val) {
                this.pageSize = val;
                this.getPageData();
	        },
            getPageData() {
                return this.axios({
                    url: "/api/booking/history/" +
                        this.$jwtUtil.getTokenEmail() + "/"
                        + this.currentPage + "/" + this.pageSize,
                    method: 'GET',
                    params: {
                        filters: this.newFilters,
	                    date: this.date
                    },
                    paramsSerializer: params => {
                        return qs.stringify(params, {indices: false})
                    }
                }).then((res) => {
                    if (res.data.code === 200) {
                        this.historyRecords = [];
                        this.historyRecords = res.data.data;
                        this.getRecordsCount();
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
                    url: "/api/booking/history/count/" +
                        this.$jwtUtil.getTokenEmail(),
                    method: 'GET',
                    params: {
                        filters: this.newFilters,
	                    date: this.date
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
                this.$confirm("Are you sure you want to cancel this meeting?","Cancel",{
                    type: "error",
                    confirmButtonText: "Submit",
                    cancelButtonText: "Cancel",
                    customClass: "testClass"
                }).then(value=>{
                    if(value)
                    {
                        this.axios({
                            method: "GET",
                            url: "/api/booking/history/"+row.recordId+"/cancel"
                        }).then((res)=>{
                            if(res.data.code === 200)
                            {
                                this.$message({
                                    message: "Cancel success!",
                                    type: "success"
                                });
                                this.axios({
                                    url: "/api/booking/notify",
                                    method: "POST",
                                    params:{
                                        recordId: row.recordId,
                                        subject:"canceling",
                                        startTime:row.startTime,
                                        endTime:row.endTime,
                                        roomName:row.roomName,
                                        roomAddress:row.roomAddress,
                                    }
                                }).then((res)=>{
                                    if(res.data.code === 200)
                                    {
                                        this.$messageUtil.successMessage(this,res.data.msg);
                                    }
                                    else
                                    {
                                        this.$messageUtil.warningMessage(this,res.data.msg);
                                    }
                                }).catch((err)=> {
                                    this.$messageUtil.errorMessage(this);
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
                            this.$messageUtil.errorMessage(this);
                        })

                    }
                })
            },
            handleEdit(index, row) {
                // console.log(index, row);
                console.log(row);
                this.beforeEdit = row;
                let rowStartTime = row.startTime.split(" ");
                let rowEndTime = row.endTime.split(" ");
                this.selectedRecord.date = rowStartTime[0];
                this.selectedRecord.startTime = rowStartTime[1];
                this.selectedRecord.endTime = rowEndTime[1];
                this.selectedRecord.roomName = row.roomName;
                this.selectedRecord.creationTime = row.creationTime;
                this.selectedRecord.recordId = row.recordId;
                this.selectedRecord.roomAddress = row.roomAddress;
                this.cardShow=true;
                this.darkActive = true;
            },
	        handleSuccess(index,row) {
                this.$confirm("Are you sure this meeting has finished?","Complete",{
                    type: "warning",
	                confirmButtonText: "Submit",
	                cancelButtonText: "Cancel",
                }).then(value=>{
                    if(value)
                    {
	                    this.axios({
		                    method: "GET",
		                    url: "/api/booking//history/"+row.recordId+"/complete"
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
                            this.$messageUtil.errorMessage(this);
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
                let roomAddress = null;
                let recordId = this.selectedRecord.recordId;
                let startTime = this.selectedRecord.date + " " + this.selectedRecord.startTime;
                let endTime = this.selectedRecord.date + " " + this.selectedRecord.endTime;
                let creationTime = this.selectedRecord.creationTime;
                let bookerEmail = this.$jwtUtil.getTokenEmail();
                let status = 0;
                for (let i = 0; i < this.rooms.length; i++) {
                    if (this.rooms[i].roomName === this.selectedRecord.roomName) {
                        roomId = this.rooms[i].roomId;
                        roomAddress = this.rooms[i].address;
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
			       url: "/api/booking/history/edit"
		       }).then((res)=>{
		           if(res.data.code === 200) {
		               this.cardShow = false;
		               this.darkActive = false;
                       this.$message({
                           message: "Edit Success!",
                           type: "success"
                       });
                       this.axios({
	                       url: "/api/booking/notify/update",
	                       method: "POST",
	                       params:{
                               recordId:recordId,
                               newStartTime: startTime,
                               newEndTime: endTime,
                               oldStartTime: this.beforeEdit.startTime,
                               oldEndTime: this.beforeEdit.endTime,
                               oldRoomName: this.beforeEdit.roomName,
                               oldRoomAddress: this.beforeEdit.roomAddress,
                               newRoomName: this.selectedRecord.roomName,
                               newRoomAddress: roomAddress
	                       }
                       }).then((res)=>{
                           if(res.data.code === 200)
                           {
                               this.$messageUtil.successMessage(this,res.data.msg);
                           }
                           else {
                               this.$messageUtil.warningMessage(this,res.data.msg);
                           }
                       }).catch((err)=>{
                           this.$messageUtil.errorMessage(this);
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
                   this.$messageUtil.errorMessage(this);
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
                    url: '/api/roomInfo',
                }).then((res) => {
                    for (let i = 0; i < res.data.data.length; i++) {
                        let room = res.data.data[i];
                        this.rooms.push(room)
                    }
                })
            },
	        async refresh(){
                await this.getPageData();
                await this.getRecordsCount();
	        },
	        autoComplete(){
                this.axios({
	                url: "/api/booking//history/autoComplete/" + this.$jwtUtil.getTokenEmail(),
	                method: "PUT"
                }).then((res)=>{
                    if(res.data.code === 200)
                    {
                        this.getPageData();
                        this.$messageUtil.successMessage(this,res.data.msg);
                    }
                    else{
                        this.$messageUtil.warningMessage(this,res.data.msg);
                    }
                }).catch((err)=>{
                    this.$messageUtil.errorMessage(this);
                })
	        },
	        reset(){
                this.attenders= [];
                this.attenderRecordId = null;
                this.attenderFlag = false;
                this.attenderRecord.address = null;
                this.attenderRecord.roomName = null;
                this.attenderRecord.startTime = null;
                this.attenderRecord.endTime = null;
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
		width: 60%;
		position: fixed;
		left: 20%;
		z-index: 11;
		top: 20%;
		height: 75%;
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
	
	
	.booking_row:nth-of-type(n+2) {
		margin-top: 5%;
	}
	
	.booking_row:nth-of-type(5) {
		margin-top: 10%;
	}
	
	* {
		font-size: 14px;
	}
</style>


<style>
	.el-table .cell {
		line-height: 40px;
	}
	.el-message-box__title{
		font-size: 22px;
	}
	.el-message-box__content{
		font-size: 16px;
	}
	


</style>
