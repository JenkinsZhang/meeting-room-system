<template>
	<div>
		<!--		<div :class="{'darker':active}"></div>-->
		<!--		{{history_records}}-->
		<el-table
				:data="history_records"
				style="width: 100%;"
				stripe
				align="center"
				@filter-change="filter"
				:cell-style=rowStyle
		>
			<el-table-column type="expand" width="16">
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
					label="Record ID"
					prop="recordId"
					sortable
					width="120"
					fixed>
			</el-table-column>
			<el-table-column
					label="Start Time"
					prop="startTime"
					sortable
			>
			</el-table-column>
			<el-table-column
					label="End Time"
					prop="endTime">
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
			<el-table-column label="Action">
				<template slot-scope="props">
					<el-button
							size="mini"
							@click="handleEdit(props.$index, props.row)">Edit
					</el-button>
					<el-button
							size="mini"
							type="danger"
							@click="handleCancel(props.$index, props.row)">Delete
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
                active: false,
                history_records: [],
                currentPage: 1,
                totalItems: 50,
                pageSize: 8,
                rowStyle: {
                    height: "50px"
                },
                new_filters: [-1, 0, 1]
            }
        },
        mounted() {
            this.getPageData(1).then((res) => {
                if (res.data.code === 200) {
                    this.history_records = [];
                    this.history_records = res.data.data;
                    this.renderStatus();
                    // console.log(this.history_records)
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
                        filters: this.new_filters
                    },
                    paramsSerializer: params => {
                        return qs.stringify(params, {indices: false})
                    }
                }).then((res) => {
                    if (res.data.code === 200) {
                        this.history_records = [];
                        this.history_records = res.data.data;
                        this.renderStatus();
                        // console.log(this.history_records)
                        // console.log("wocaonima")
                    } else {
                        this.history_records = [];
                    }
                }).catch((error) => {
                    console.log(error)
                })
            },
	        getRecordsCount(){
                return this.axios({
                    url: "api/booking/history/count/" +
                        this.$jwtUtil.getTokenEmail(),
                    method: 'GET',
                    params: {
                        filters: this.new_filters
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
	        // refreshData(val){
            //     this.getPageData(val).then((res) => {
            //         if (res.data.code === 200) {
            //             this.history_records = [];
            //             this.history_records = res.data.data;
            //             this.renderStatus();
            //             // console.log(this.history_records)
            //             // console.log("wocaonima")
            //         } else {
            //             alert(res.data.msg)
            //         }
            //     }).catch((error) => {
            //         console.log(error)
            //     })
	        // },
            renderStatus() {
                this.history_records.forEach((record, index) => {
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
                console.log(index, row)
            },
            handleEdit(index, row) {
                console.log(index, row)
            },
            async filter(filters) {
                // console.log(filters);
                this.currentPage = 1;
                this.new_filters = null;
                for (let key in filters) {
                    this.new_filters = filters[key]
                }
                // console.log("new_filters:"+this.new_filters);
	            let temp_filters = [];
                for (let i = 0; i < this.new_filters.length; i = i + 1) {
	                if(this.new_filters[i] ==="completed")
	                {
	                    temp_filters.push(1)
	                }
	                else if(this.new_filters[i] ==="uncompleted")
	                {
	                    temp_filters.push(0)
	                }
	                else if(this.new_filters[i] ==="canceled")
	                {
	                    temp_filters.push(-1)
	                }
                }
                this.new_filters = temp_filters;
                if (this.new_filters.length === 0 || this.new_filters ==='') {
                    this.new_filters = [0, -1, 1];
                }
                console.log("new_filters after processing " + this.new_filters);
                console.log("========================1   " + this.new_filters);
                await this.getPageData(this.currentPage).then((res) => {
                    if (res.data.code === 200) {
                        this.history_records = [];
                        this.history_records = res.data.data;
                        this.renderStatus();
                        // console.log(this.history_records)
                        // console.log("wocaonima")
                    } else {
                        alert(res.data.msg)
                    }
                }).catch((error) => {
                    console.log(error)
                });
                // console.log("========================2   " +this.new_filters)
                await this.getRecordsCount().then((res) => {
                    if (res.data.code === 200) {
                        this.totalItems = res.data.data;
                    } else {
                        alert(res.data.msg)
                    }
                }).catch((error) => {
                    console.log(error)
                });
                // console.log("========================3")
                // console.log(this.new_filters)
				// console.log(filters)
            }
        }
    }
</script>

<style scoped>
	.darker {
		background-color: rgba(0, 0, 0, 0.8);
		height: 100%;
		z-index: 10000;
		position: fixed;
		width: 100%;
		left: 0;
		top: 0;
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
	
	/*.demo-table-expand {*/
	/*	font-size: 0;*/
	/*}*/
	/*.demo-table-expand label {*/
	/*	width: 150px;*/
	/*	color: #99a9bf;*/
	/*}*/
	/*.demo-table-expand .el-form-item {*/
	/*	margin-right: 0;*/
	/*	margin-bottom: 0;*/
	/*	width: 50%;*/
	/*}*/
</style>
