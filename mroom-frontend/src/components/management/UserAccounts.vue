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
				<div>
					<el-button type="primary" icon="el-icon-plus" @click="showAddAccountView">
						Add Account
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
						<el-col :span="7" :offset="1">
							Account's Phone:
							<el-input placeholder="Enter email..." v-model="searchValue.phone"/>
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
						width="130">
				</el-table-column>
				<el-table-column
						prop="viewId"
						label="ID"
						align="left"
						width="120"
						sortable>
				</el-table-column>
				<el-table-column
						prop="email"
						label="Email"
						align="left"
						width="250">
				</el-table-column>
				<el-table-column
						prop="phone"
						width="150"
						align="left"
						label="Phone">
				</el-table-column>
				<el-table-column
						prop="roleName"
						width="120"
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
						width="250"
						label="Action">
					<template slot-scope="scope" style="text-align: center">
						<el-button size="mini" @click="showEditAccountView(scope.row)">Edit
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
				<el-form style="text-align: left;margin-left: 7%;margin-right: 7%" :rules="rules" :model="user"
				         status-icon>
					<el-form-item label="Email address: " prop="email">
						<el-input type="email" placeholder="Enter your email" v-model="user.email">
						
						</el-input>
					</el-form-item>
					<el-form-item label="Your username: " prop="username">
						<el-input type="text" placeholder="Enter your username" v-model="user.username">
						
						</el-input>
					</el-form-item>
					<el-form-item label="Cellphone: " prop="phone">
						<el-input type="phone" placeholder="Please enter your Cellphone number!"
						          v-model="user.phone">
						
						</el-input>
					</el-form-item>
				
				</el-form>
			</div>
			<span slot="footer" class="dialog-footer">
		    <el-button @click="cancelEditAccount">Cancel</el-button>
		    <el-button type="primary" @click="doEditAccount">Submit</el-button>
		  </span>
		</el-dialog>
		<el-dialog
				:title="title"
				:visible.sync="addDialogView"
				width="80%"
				:before-close="handleClose"
		>
			<div>
				<el-form style="text-align: left;margin-left: 7%;margin-right: 7%" :rules="rules" :model="user"
				         status-icon>
					<el-form-item label="Email address: " prop="newEmail">
						<el-input type="email" placeholder="Enter  email" v-model="user.newEmail">
						
						</el-input>
					</el-form-item>
					<el-form-item label="Username: " prop="username">
						<el-input type="text" placeholder="Enter username" v-model="user.username">
						
						</el-input>
					</el-form-item>
					<el-form-item label="Password: " prop="password">
						<el-input type="password" placeholder="Please enter password!" v-model="user.password">
						
						</el-input>
					</el-form-item>
					<el-form-item label="Cellphone: " prop="phone">
						<el-input type="phone" placeholder="Please enter Cellphone number!"
						          v-model="user.phone">
						
						</el-input>
					</el-form-item>
				
				</el-form>
			</div>
			<span slot="footer" class="dialog-footer">
		    <el-button @click="addDialogView = false">Cancel</el-button>
		    <el-button type="primary" @click="doAddAccount">Submit</el-button>
		  </span>
		</el-dialog>
	</div>
</template>

<script>
    export default {
        name: "UserAccounts",
        data() {
            return {
                searchValue: {
                    email: null,
                    id: 0,
                    phone: null,
                },
                title: '',
                showAdvanceSearchView: false,
                accounts: [],
                loading: false,
                accountDialogView: false,
                addDialogView: false,
                total: 0,
                page: 1,
                keyword: '',
                size: 10,
                user: {
                    id: null,
                    username: null,
                    email: null,
                    phone: null,
                    password: null,
                    active: null,
                    newEmail: null,
                },
                rules: {
                    newEmail: [
                        {validator: this.emailValidate, trigger: 'blur'}
                    ],
                    username: [
                        {validator: this.usernameValidate, trigger: 'blur'}
                    ],
                    password: [
                        {validator: this.passwordValidate, trigger: 'blur'}
                    ],
                    phone: [
                        {validator: this.phoneValidate, trigger: 'blur'}
                    ]
                },
                emailValid: null,
                usernameValid: null,
                passwordValid: null,
                reEnterValid: null,
            }
        },
        mounted() {
            this.initAccounts();
        },
        methods: {
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
                    if (this.searchValue.phone) {
                        url += '&phone=' + this.searchValue.phone;
                    }
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
                    phone: null,
                    password: null,
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
            async doAddAccount() {
                let reg = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[A-Za-z\d$@$!%*?&]{8,25}/g;
                if (this.emailValid !== true) {
                    alert("Please enter valid email!");
                    return;
                }
                if (!(this.user.username.length >= 5 && this.user.username.length <= 20)) {
                    alert("Username's length should be between 5~20 characters long");
                    return;
                }
                if (this.user.password.match(reg) == null) {
                    alert("Invalid password!");
                    return;
                }

                let submitData = {
                    email: this.user.newEmail,
                    password: this.user.password,
                    username: this.user.username,
                    phone: this.user.phone
                };
                await this.axios({
                    method: 'POST',
                    data: submitData,
                    url: '/api/user/admin/addAccount'
                }).then((res) => {
                    console.log(res.data);
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
                this.initAccounts();
            },
            async doEditAccount() {
                let reg = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[A-Za-z\d$@$!%*?&]{8,25}/g;
                if (this.emailValid !== true) {
                    alert("Please enter valid email!");
                    return;
                }
                if (!(this.user.username.length >= 3 && this.user.username.length <= 20)) {
                    alert("Username's length should be between 3~20 characters long");
                    return;
                }
                await this.axios({
                    url: "/api/user/admin",
                    params: {
                        phone: this.user.phone,
                        id: this.user.id,
                        email: this.user.email,
                        username: this.user.username,
                        active: this.user.active
                    },
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
                this.initAccounts();
            },
            async cancelEditAccount() {
                this.accountDialogView = false;
                await this.initAccounts();
            },
            showAddAccountView() {
                this.emptyAccount();
                this.title = "Add Account";
                this.addDialogView = true;
            },
            showEditAccountView(data) {
                this.title = "Edit Account";
                this.user = data;
                this.emailValid = true;
                this.usernameValid = true;
                this.passwordValid = true;
                this.accountDialogView = true;
            },

            emailValidate(rule, value, callback) {
                if (!this.user.newEmail) {
                    this.emailValid = false;
                    return callback(new Error('Email can not be null!'))
                }
                const mailReg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
                if (mailReg.test(this.user.newEmail)) {
                    setTimeout(() => {
                        this.axios({
                            method: 'POST',
                            url: "/api/user/check/" + this.user.newEmail
                        }).then((res) => {
                            console.log(res.data);
                            console.log(res.data.code === 200);
                            this.emailValid = res.data.code === 200;
                            if (!this.emailValid) {
                                return callback(new Error('This email has been taken!'))
                            } else {
                                callback()
                            }
                        }).catch((err) => {
                            this.$messageUtil.errorMessage(this);
                        })
                    }, 500);
                } else {
                    callback(new Error("Invalid email format!"))
                }

            },
            usernameValidate(rule, value, callback) {
                this.usernameValid = !(this.user.username.length < 3 || this.user.username.length > 25);
                if (!this.usernameValid) {
                    return callback(new Error("Your username should be 3 to 25 characters long"))
                } else {
                    callback();
                }
            },
            passwordValidate(rule, value, callback) {
                let reg = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[A-Za-z\d$@$!%*?&]{8,25}/g;
                this.passwordValid = (this.user.password.match(reg) != null);
                if (!this.passwordValid) {
                    return callback(new Error("Your password should be 8 to 25 characters long and contain at least one [a-z],one [A-Z] and one [0-9]"))
                } else {
                    callback();
                }
            },
            reEnterValidate(rule, value, callback) {
                this.reEnterValid = (this.user.reEnter !== '' && (this.user.reEnter === this.user.password));
                if (!this.reEnterValid) {
                    return callback(new Error("Password should be the same!"))
                } else {
                    callback();
                }
            },
            phoneValidate(rule, value, callback) {
                const phoneReg = /^1[3|4|5|7|8][0-9]{9}$/;
                if (!this.user.phone) {
                    return callback(new Error("Phone number can not be null"))
                }
                setTimeout(() => {
                    // Number.isInteger是es6验证数字是否为整数的方法,但是我实际用的时候输入的数字总是识别成字符串
                    // 所以我就在前面加了一个+实现隐式转换

                    if (!Number.isInteger(+this.user.phone)) {
                        callback(new Error("Please enter number!"))
                    } else {
                        if (phoneReg.test(value)) {
                            callback()
                        } else {
                            callback(new Error("Format incorrect!"))
                        }
                    }
                }, 100)
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
