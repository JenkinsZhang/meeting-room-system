<template>
	<div>
		<el-tabs v-model="activeName" type="border-card">
			<el-tab-pane label="Rooms" name="rooms">
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
							v-loading="roomLoading"
							element-roomLoading-text="Loading..."
							element-roomLoading-spinner="el-icon-roomLoading"
							element-roomLoading-background="rgba(0, 0, 0, 0.8)"
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
								prop="status"
								width="120"
								align="left"
								label="Status">
						</el-table-column>
						<el-table-column
								fixed="right"
								width="300"
								label="Action"
								align="center">
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
								<el-input type="text" placeholder="Enter address" v-model="selectedRoom.address">
								
								</el-input>
							</el-form-item>
							<el-row>
								<el-col :span="7">
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
							<el-row>
								
								<el-col :span="7">
									<el-form-item label="Status: ">
										<el-input type="text" placeholder="Status"
										          v-model="selectedRoom.status" disabled>
										</el-input>
									</el-form-item>
								</el-col>
								<el-col :span="7" :offset="1">
									<el-form-item label="New Status: ">
										<el-select style="width: 100%" v-model="selectedRoom.newStatus" clearable>
											<el-option
													v-for="status in statusOptions"
													:key="status.code"
													:label="status.label"
													:value="status.code">
											</el-option>
										</el-select>
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
								<el-input type="text" placeholder="Enter address" v-model="selectedRoom.address">
								
								</el-input>
							</el-form-item>
							<el-row>
								<el-col :span="7">
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
						<div class="el-upload__text">Please drag your picture here or <br><em>click here to upload</em>
						</div>
						<div class="el-upload__tip" slot="tip">Only image file.The recommended size is less than
							500kb.
						</div>
					
					</el-upload>
					<span slot="footer" class="dialog-footer">
		    <el-button @click="cancelUploadRoom">Cancel</el-button>
		    <el-button type="primary" @click="submitUpload">Submit</el-button>
		  </span>
				</el-dialog>
			</el-tab-pane>
			<el-tab-pane label="Reports" name="reports">
				<div>
					<el-row>
						<el-col :span="16">
							<div style="display: flex;justify-content: space-between;">
								<div>
									<el-date-picker
											v-model="reportSearchValue.startDate"
											prefix-icon="el-icon-search"
											type="date"
											value-format="yyyy-MM-dd"
											placeholder="Please select date"
											style="width: 350px;margin-right: 10px"
											clearable
											@clear="initReport"
											@keydown.enter.native="initReport" :disabled="showAdvanceSearchView">
									</el-date-picker>
									<el-button icon="el-icon-search" type="primary" @click="initReport"
									           :disabled="showAdvanceSearchView">
										Search
									</el-button>
									<el-button type="primary" @click="showAdvanceSearchView = !showAdvanceSearchView">
										<i :class="showAdvanceSearchView?'fa fa-angle-double-up':'fa fa-angle-double-down'"
										   aria-hidden="true"/>
										Advance
									</el-button>
								</div>
							</div>
						</el-col>
						<el-col style="margin-top: 0.5%;font-size: 20px" :span="4" v-for="tag in reportTags" :key="tag.name">
							<el-tag :type="tag.type" style="font-size: 15px">
								{{tag.name}}:<b style="font-size: 20px">{{tag.value}}</b>
							</el-tag>
						</el-col>
					</el-row>
					
					<transition name="slide-fade">
						<div v-show="showAdvanceSearchView"
						     style="border: 1px solid #409eff;border-radius: 5px;box-sizing: border-box;padding: 5px;margin: 10px 0;">
							<el-row>
								<el-col :span="7">
									<el-row>
										Room ID:
									</el-row>
									<el-row>
										<el-input placeholder="Enter room ID..." v-model="reportSearchValue.roomId"/>
									</el-row>
									
								</el-col>
								<el-col :span="7" :offset="1">
									<el-row>
										Reporter's email:
									</el-row>
									
									<el-row>
										<el-input placeholder="Enter email..." v-model="reportSearchValue.reporter"/>
									</el-row>
									
								</el-col>
								<el-col :span="7" :offset="1">
									<el-row>
										Date:
									</el-row>
									<el-row>
										<el-date-picker
												v-model="reportSearchValue.startDate"
												prefix-icon="el-icon-search"
												type="date"
												value-format="yyyy-MM-dd"
												placeholder="Please select date"
												clearable>
										</el-date-picker>
									</el-row>
									
								</el-col>
							
							</el-row>
							<el-row style="margin-top: 10px">
								<el-col :span="6" :offset="18">
									<el-button size="mini" @click="showAdvanceSearchView = !showAdvanceSearchView">
										Cancel
									</el-button>
									<el-button size="mini" icon="el-icon-search" type="primary"
									           @click="initReport('advance')">
										Search
									</el-button>
								</el-col>
							</el-row>
						</div>
					</transition>
				</div>
				<div style="margin-top: 10px">
					<el-table
							:data="reports"
							stripe
							border
							v-loading="reportLoading"
							element-loading-text="Loading..."
							element-loading-spinner="el-icon-loading"
							element-loading-background="rgba(0, 0, 0, 0.8)"
							:default-sort="{prop:'createTime',order: 'descending'}"
							style="width: 100%">
						<el-table-column
								prop="reportId"
								fixed
								align="left"
								label="Report ID"
								width="120"
								sortable>
						</el-table-column>
						<el-table-column
								prop="roomName"
								label="Room Name"
								align="left"
								width="120">
						</el-table-column>
						<el-table-column
								prop="address"
								label="Address"
								align="left"
								width="250">
						</el-table-column>
						<el-table-column
								prop="detail"
								width="250"
								align="left"
								label="Detail">
						</el-table-column>
						<el-table-column
								prop="reporter"
								width="120"
								align="left"
								label="Reporter"
								sortable>
						</el-table-column>
						<el-table-column
								prop="createTime"
								width="150"
								align="left"
								label="Create Time"
								sortable>
						</el-table-column>
						<el-table-column
								prop="completeTime"
								width="150"
								align="left"
								label="Complete Time"
								sortable>
						</el-table-column>
						<el-table-column
								prop="status"
								width="150"
								align="center"
								fixed="right"
								label="Status"
								:filters="[{ text: 'Completed', value: 'Completed' }, { text: 'Uncompleted', value: 'Uncompleted' }]"
								:filter-method="filterStatusTag">
							<template slot-scope="scope">
								<el-tag :type="tagType(scope.row.status)" style="font-size: 14px" disable-transitions>
									{{scope.row.status}}
								</el-tag>
							</template>
						</el-table-column>
						<el-table-column
								fixed="right"
								width="120"
								label="Action"
								align="center">
							<template slot-scope="scope" style="text-align: center">
								<el-button size="mini" @click="completeReport(scope.row)" type="primary">Complete
								</el-button>
							</template>
						</el-table-column>
					</el-table>
				</div>
				<div style="text-align: center">
					<el-pagination
							background
							@current-change="currentChange"
							@size-change="sizeChange"
							layout="sizes, prev, pager, next, jumper, ->, total, slot"
							:total="total">
					</el-pagination>
				</div>
			</el-tab-pane>
		</el-tabs>
	
	</div>
</template>

<script>
    export default {
        name: "RoomsManagement",
        data() {
            return {
                title: '',
                roomLoading: false,
                reportLoading: false,
                roomDialogView: false,
                addDialogView: false,
                uploadDialogView: false,
                showAdvanceSearchView: false,
                rooms: [],
                selectedRoom: {
                    roomId: null,
                    roomName: null,
                    address: null,
                    maxPeople: 0,
                    projection: 0,
                    airConditioner: 0,
                    imageUrl: null,
                    status: null,
                    newStatus: null,
                },
                statusOptions: [
                    {
                        code: 0,
                        label: "Closed"
                    },
                    {
                        code: 1,
                        label: "Opening"
                    }
                ],
                activeName: 'rooms',
                reports: [],
                selectedReport: {},
                reportDate: null,
                reportSearchValue: {
                    roomId: null,
                    reporter: null,
                    startDate: null,
                },
                total: 0,
                page: 1,
                size: 10,
	            reportTags:[
                    { name: 'Finished Report ', type: 'success',value: null },
                    { name: 'Unfinished Report', type: 'danger',value: null }
	            ]
            }
        },
        async mounted() {
            await this.initRooms();
            await this.initReport();
        },
        methods: {
            initRooms() {
                this.roomLoading = true;
                this.axios({
                    url: "/api/roomInfo/admin/roomOverview",
                    method: "GET"
                }).then((res) => {
                    if (res.data.code === 200) {
                        this.rooms = res.data.data;
                    } else {
                        this.rooms = null;
                    }
                }).catch((error) => {
                    this.$messageUtil.errorMessage(this);
                }).finally(() => {
                    this.roomLoading = false;
                })
            },
            initReport(type) {
                this.reportLoading = true;
                let submitData = this.reportSearchValue;
                if(submitData.startDate != null)
                {
                    submitData.startDate = submitData.startDate + " " +"00:00:00"
                }
                if(! (type && type ==="advance"))
                {
                    submitData.reporter=null;
                    submitData.roomId = null;
                }
                this.axios({
                    url: '/api/roomInfo/admin/reportOverview/' + this.page + "/" + this.size,
                    data: submitData,
                    method: "POST"
                }).then((res) => {
                    if (res.data.code === 200) {
                        this.reports = res.data.data;
                        this.total = this.reports.length;
                        this.initReportTags();
                        this.reportLoading = false;
                    }
                }).catch((err) => {
                    this.$messageUtil.errorMessage(this);
                    this.reportLoading = false;
                }).finally(()=>{
                    window.scrollTo(0,0);
                })
            },
	        initReportTags(){
                this.axios({
	                url: "/api/roomInfo/admin/statusCount",
	                method: "GET"
                }).then((res)=>{
                    this.reportTags[0].value = res.data.data.Finished;
                    this.reportTags[1].value = res.data.data.Unfinished;
                }).catch((err)=>{
                    this.$messageUtil.errorMessage(this);
                })
         
	        },
            sizeChange(currentSize) {
                this.size = currentSize;
                this.initAccounts();
            },
            currentChange(currentPage) {
                this.page = currentPage;
                this.initAccounts();
            },
            tagType(val) {
                switch (val) {
                    case "Completed":
                        return "primary";
                    case "Uncompleted":
                        return "danger"
                }
                return null;
            },
            emptyRoom() {
                this.selectedRoom = {
                    roomId: null,
                    roomName: null,
                    address: null,
                    maxPeople: 0,
                    projection: 0,
                    airConditioner: 0,
                    imageUrl: null,
                    status: null,
                    newStatus: null
                }
            },
            async deleteRoom(data) {
                await this.axios({
                    url: "/api/roomInfo/admin/" + data.roomId,
                    method: "DELETE"
                }).then((res) => {
                    if (res.data.code === 200) {
                        this.$messageUtil.successMessage(this, res.data.msg);
                    } else {
                        this.$messageUtil.warningMessage(this, res.data.msg);
                    }
                }).catch((err) => {
                    this.$messageUtil.errorMessage(this);
                });
                this.initRooms();
            },
            async doAddRoom() {
                this.selectedRoom.status = 1;
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
                this.selectedRoom.status = this.selectedRoom.newStatus;
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
                this.emptyRoom();
            },
            async cancelEditRoom() {
                this.roomDialogView = false;
                await this.initRooms();
                this.emptyRoom();
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
                this.selectedRoom.address = data.address;
                this.selectedRoom.imageUrl = data.imageURL;
                this.selectedRoom.roomName = data.roomName;
                this.selectedRoom.roomId = data.roomId;
                this.selectedRoom.maxPeople = data.capacity;
                this.selectedRoom.projection = data.projection;
                this.selectedRoom.airConditioner = data.airConditioner;
                this.selectedRoom.status = data.status;
                this.roomDialogView = true;
            },
            uploadImage(data) {
                this.title = "Upload Image";
                this.selectedRoom.roomName = data.roomName;
                this.selectedRoom.roomId = data.roomId;
                this.uploadDialogView = true;
            },
            async handleClose(done) {
                await this.initRooms();
                this.emptyRoom();
                done();
            },
            upload() {
                console.log(this.file);
                const form = new FormData();
                form.append("roomImage", this.file);
                this.axios({
                    url: "/api/roomInfo/admin/post/roomImage",
                    method: 'POST',
                    data: form,
                    params: {
                        roomId: this.selectedRoom.roomId
                    },
                    headers: {
                        'Content-Type': 'multipart/form-data'
                    }
                }).then((res) => {
                    if (res.data.code === 200) {
                        this.$messageUtil.successMessage(this, res.data.msg);
                    } else {
                        this.$messageUtil.warningMessage(this, res.data.msg);
                    }
                }).catch((err) => {
                    this.$messageUtil.errorMessage(this)
                }).finally(() => {
                    this.initRooms();
                    this.emptyRoom();
                    this.uploadDialogView = false;
                })
            },
            beforeUpload(file) {

                const filenameSplit = file.name.split(".");
                const suffix = filenameSplit[filenameSplit.length - 1];
                this.file = new File([file], this.selectedRoom.roomName + "." + suffix)
                // console.log(this.file);
            },
            submitUpload() {
                this.$refs.upload.submit();
            },
            filterStatusTag(value, row) {
                return row.status === value;
            },
            async completeReport(data) {
                this.selectedReport = data;
                console.log(this.selectedReport);
                await this.axios({
                    url: "/api/roomInfo/admin/report/" + this.selectedReport.reportId + "/" + 1,
                    method: "PUT"
                }).then((res) => {
                    if (res.data.code === 200) {
                        this.$messageUtil.successMessage(this, res.data.msg);
                        this.initReport();
                    } else {
                        this.$messageUtil.warningMessage(this, res.data.msg);
                    }
                }).catch((err) => {
                    this.$messageUtil.errorMessage(this);
                })
                this.selectedReport = {};
            }
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
