<template>
	<div>
		<div>
			<div style="display: flex;justify-content: space-between">
				<div>
					<el-input placeholder="Please enter the account's username to search..."
					          prefix-icon="el-icon-search"
					          clearable
					          @clear="initAccounts"
					          style="width: 350px;margin-right: 10px" v-model="keyword"
					          @keydown.enter.native="initAccounts" :disabled="showAdvanceSearchView"/>
					<el-button icon="el-icon-search" type="primary" @click="initAccounts"
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
			<transition name="slide-fade">
				<div v-show="showAdvanceSearchView"
				     style="border: 1px solid #409eff;border-radius: 5px;box-sizing: border-box;padding: 5px;margin: 10px 0;">
					<el-row>
						<el-col :span="7">
							Account's email:
							<el-input placeholder="Enter email..." v-model="searchValue.email"/>
						</el-col>
						<el-col :span="7" :offset="1">
							Account's ID:
							<el-input placeholder="Enter email..." v-model="searchValue.id"/>
						</el-col>
					
					</el-row>
					<el-row style="margin-top: 10px">
						<el-col :span="6" :offset="18">
							<el-button size="mini" @click="showAdvanceSearchView = !showAdvanceSearchView">Cancel
							</el-button>
							<el-button size="mini" icon="el-icon-search" type="primary"
							           @click="initAccounts('advanced')">
								Search
							</el-button>
						</el-col>
					</el-row>
				</div>
			</transition>
		</div>
		<div style="margin-top: 10px">
			<el-table
					:data="accounts"
					stripe
					border
					v-loading="loading"
					element-loading-text="Loading..."
					element-loading-spinner="el-icon-loading"
					element-loading-background="rgba(0, 0, 0, 0.8)"
					:default-sort="{prop:'viewId',order: 'ascending'}"
					style="width: 100%">
				<el-table-column
						prop="username"
						fixed
						align="left"
						label="Username"
						width="150">
				</el-table-column>
				<el-table-column
						prop="viewId"
						label="ID"
						align="left"
						sortable>
				</el-table-column>
				<el-table-column
						prop="email"
						label="Email"
						align="left"
						width="200">
				</el-table-column>
				<el-table-column
						prop="roleName"
						width="150"
						align="left"
						label="Role">
				</el-table-column>
				<el-table-column
						prop="status"
						width="130"
						align="left"
						fixed="right"
						label="Status"
						:filters="[{ text: 'Activated', value: 'Activated' }, { text: 'Unactivated', value: 'Unactivated' }]"
						:filter-method="filterStatusTag">
					<template slot-scope="scope">
						<el-tag :type="tagType(scope.row.status)" style="font-size: 14px" disable-transitions>
							{{scope.row.status}}
						</el-tag>
					</template>
				</el-table-column>
				<el-table-column
						fixed="right"
						width="300"
						label="Action">
					<template slot-scope="scope" style="text-align: center">
						<el-button size="mini" @click="showEditAccountView(scope.row)">Edit Role
						</el-button>
						<el-button size="mini" @click="activateAccount(scope.row)" type="success">Activate
						</el-button>
						<el-button size="mini" @click="deleteAccount(scope.row)" type="danger">Close
						</el-button>
					</template>
				</el-table-column>
			</el-table>
			<div style="text-align: center">
				<el-pagination
						background
						@current-change="currentChange"
						@size-change="sizeChange"
						layout="sizes, prev, pager, next, jumper, ->, total, slot"
						:total="total">
				</el-pagination>
			</div>
		</div>
		<el-dialog
				:title="title"
				:visible.sync="accountDialogView"
				width="80%"
				:before-close="handleClose"
		>
			<div>
				<el-form style="text-align: left;margin-left: 7%;margin-right: 7%"  :model="user"
				         status-icon>
					<el-form-item label="Email address: " prop="email">
						<el-input type="email" placeholder="Enter your email" v-model="user.email" disabled>
						
						</el-input>
					</el-form-item>
					<el-form-item label="Role: " prop="roleName">
						<el-input type="text" placeholder="Role Name"
						          v-model="user.roleName" disabled>
						</el-input>
					</el-form-item>
					<el-form-item label="Role: " prop="newRoleId">
						<el-select  style="width: 100%" v-model="user.newRoleId" clearable placeholder="Please select a new role">
							<el-option
									v-for="role in roleOptions"
									:key="role.id"
									:label="role.role_name"
									:value="role.id">
							</el-option>
						</el-select>
					</el-form-item>
					
				</el-form>
			</div>
			<span slot="footer" class="dialog-footer">
		    <el-button @click="cancelPromoteAccount">Cancel</el-button>
		    <el-button type="primary" @click="doPromoteAccount">Submit</el-button>
		  </span>
		</el-dialog>
	</div>
</template>

<script>
    export default {
        name: "UserRoles",
        data() {
            return {
                searchValue: {
                    email: null,
                    id: 0,
                },
                title: '',
                showAdvanceSearchView: false,
                accounts: [],
                loading: false,
                accountDialogView: false,
                total: 0,
                page: 1,
                keyword: '',
                size: 10,
	            roleOptions: [],
                user: {
                    id: null,
                    username: null,
                    email: null,
                    roleName: null,
                    newEmail: null,
	                active: null,
	                newRoleId: null
                },
            }
        },
        mounted() {
            this.initAccounts();
            this.getRoleOptions();
        },
        methods: {
            getRoleOptions(){
                this.axios({
                    url: "/api/user/admin/roles",
                    method: "GET"
                }).then((res)=>{
                    if(res.data.code === 200)
                    {
                        // console.log(res.data.data);
                        this.roleOptions = res.data.data;
                    }
                    else
                    {
                        this.$messageUtil.warningMessage(this.res.data.msg);
                    }
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
            initAccounts(type) {
                this.loading = true;
                let url = '/api/user/admin/accounts/?page=' + this.page + '&size=' + this.size;
                if (type && type === 'advanced') {
                    if (this.searchValue.email) {
                        url += '&email=' + this.searchValue.email;
                    }
                    if (this.searchValue.id !== 0) {
                        url += '&id=' + this.searchValue.id;
                    }
                } else {
                    url += "&username=" + this.keyword;
                }
                this.axios({
                    url: url,
                    method: "GET"
                }).then((res) => {
                    if (res.data.code === 200) {
                        this.accounts = res.data.data.accounts;
                        this.total = res.data.data.total;
                    }
                }).catch((err) => {
                    this.$messageUtil.errorMessage(this);
                }).finally(() => {
                    this.loading = false;
                    window.scrollTo(0,0);
                })
            },
            tagType(val) {
                switch (val) {
                    case "Activated":
                        return "primary";
                    case "Unactivated":
                        return "danger"
                }
                return null;
            },
            emptyAccount() {
                this.user = {
                    id: null,
                    username: null,
                    email: null,
	                roleName: null,
	                active: null
                }
            },
            async deleteAccount(data) {
                await this.axios({
                    url: "/api/user/admin/" + data.email,
                    method: "DELETE"
                }).then((res) => {
                    if (res.data.code === 200) {
                        this.$messageUtil.successMessage(this,res.data.msg);
                    }
                    else{
                        this.$messageUtil.warningMessage(this,res.data.msg);
                    }
                }).catch((err)=>{
                    this.$messageUtil.errorMessage(this);
                });
                this.initAccounts();
            },
            async activateAccount(data) {
                await this.axios({
                    url: "/api/user/admin/" + data.email,
                    method: "PUT"
                }).then((res) => {
                    if (res.data.code === 200) {
                        this.$messageUtil.successMessage(this, res.data.msg)
                    } else {
                        this.$messageUtil.warningMessage(this, res.data.msg)
                    }
                }).catch((err) => {
                    this.$messageUtil.errorMessage(this);
                });
                this.initAccounts();
            },
            async doPromoteAccount() {
                await this.axios({
                    url: "/api/user/admin/changeRole/" + this.user.id + "/" + this.user.newRoleId,
                    method: "PUT"
                }).then((res) => {
                    if (res.data.code === 200) {
                        this.$messageUtil.successMessage(this, res.data.msg);
                        this.accountDialogView = false;
                    } else {
                        this.$messageUtil.warningMessage(this, res.data.msg);
                        this.accountDialogView = false;
                    }
                }).catch((err) => {
                    this.$messageUtil.errorMessage(this);
                });
                await this.initAccounts();
            },
            async cancelPromoteAccount() {
                this.accountDialogView = false;
                await this.initAccounts();
            },
            showEditAccountView(data) {
                this.title = "Edit Account";
                this.user = data;
                this.accountDialogView = true;
            },
            async handleClose(done) {
                await this.initAccounts();
                done();
            },

            filterStatusTag(value, row) {
                return row.status === value;
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
