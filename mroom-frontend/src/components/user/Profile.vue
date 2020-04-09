<template>
	<div id="profile">
		<el-card
		        style="border-radius: 12px;
		        text-align: center;"
		>
			<div slot="header">
				<h1>Personal Information</h1>
			</div>
			<br>
				<el-row>
					<el-col :span="8">
						
						<img src="../../assets/images/avatar.jpeg" width="100%" height="300">
					</el-col>
					<el-col :span="16">
						<el-row class="rowInfo">
							<el-col :span="6">
								<label for="email"/>Email:
							</el-col>
							<el-col :span="12">
								<input id="email" disabled="true"
								       style="width: 100%" v-model="email"/>
							</el-col>
						</el-row>
						<el-row class="rowInfo">
							<el-col :span="6">
								<label for="role"/>Role:
							</el-col>
							<el-col :span="12">
								<input id="role" disabled="true"
								       style="width: 100%" v-model="roleName"/>
							</el-col>
						</el-row>
						<el-row class="rowInfo">
							<el-col :span="6">
								<label for="username"/>Username:
							</el-col>
							<el-col :span="12">
								<input id="username" disabled="true"
								       v-model="username" style="width: 100%"/>
							</el-col>
							<el-col :span="6">
								<a href="javascript:void(0);" @click="editUsername">Edit</a>
								<a href="javascript:void(0);" @click="saveUsername" style="margin-left:5%">Save</a>
							</el-col>
						</el-row>
						<el-row class="rowInfo">
							<el-col :span="6">
								<label for="cellPhone"/>Cell Phone:
							</el-col>
							<el-col :span="12">
								<input id="cellPhone" disabled="true" style="width: 100%"
								       v-model="phone"/>
							</el-col>
							<el-col :span="6">
								<a href="javascript:void(0);" @click="editPhone">Edit</a>
								<a href="javascript:void(0);" @click="savePhone" style="margin-left:5%">Save</a>
							</el-col>
						</el-row>
					</el-col>
				</el-row>
			
			
			</b-container>
		</el-card>
	</div>
</template>

<script>
    export default {
        name: "profile",
        data() {
            return {
                email: null,
                username: null,
                phone: null,
	            roleName: null
            }
        },
        methods: {
            editUsername() {
                $("#username").attr("disabled", false);
            },
            async saveUsername() {
                if($("#username").attr("disabled") === "disabled")
                {
                    this.$message({
                        message: "You haven't done any change",
                        type: "warning"
                    });
	                return
                }
                await this.axios({
                    url: "/api/user/username/" + this.email + "/" + this.username,
                    method: "PUT"
                }).then((res) => {
                    if (res.data.code === 200) {
                        this.$jwtUtil.refreshToken()
                            .then((res) => {
                                if (res.data.code === 200) {
                                    this.$message({
                                        message: "Saved!",
                                        type: "success"
                                    });
                                    localStorage.setItem("access-token", res.data.data);
                                    this.$emit("renderUsername")
                                } else {
                                    this.$message({
                                        message: "You have to login again to see the change!",
                                        type: "warning"
                                    })
                                }
                            })
                            .catch((err) => {
                                this.$message({
                                    message: "Server error!",
                                    type: "danger"
                                })
                            })
                    } else {
                        this.$message({
                            message: "Save failed!",
                            type: "warning"
                        })
                    }
                }).catch((error) => {
                    this.$message({
                        message: "Server error!",
                        type: "danger"
                    })
                });
                $("#username").attr("disabled", true);


            },
            editPhone() {
                $("#cellPhone").attr("disabled", false);
            },
            savePhone() {
                if($("#cellPhone").attr("disabled") === "disabled")
                {
                    this.$message({
                        message: "You haven't done any change",
                        type: "warning"
                    });
                    return
                }
                this.axios({
                    url: "/api/user/phone/" + this.email + "/" + this.phone,
                    method: "PUT"
                }).then((res) => {
                    if (res.data.code === 200) {
                        this.axios({
                            url: "/api/user/phone/" + this.email,
                            method: "GET"
                        }).then((res) => {
                            if (res.data.code === 200) {
                                this.phone = res.data.data;
                                this.$message({
                                    message: "Saved!",
                                    type: "success"
                                })
                            } else {
                                this.$message({
                                    message: "Failed to get phone number! Please refresh the page!",
                                    type: "warning"
                                })

                            }
                        }).catch((err) => {
                            this.$message({
                                message: "Server Error",
                                type: "danger"
                            })
                        }).finally($("#cellPhone").attr("disabled", true));
                    } else {
                        this.$message({
                            message: res.data.msg,
                            type: "warning"
                        })
                    }
                }).catch((err) => {
                    this.$message({
                        message: "Server Error!",
                        type: "danger"
                    })
                }).finally($("#cellPhone").attr("disabled", true));
            }

        },
	    mounted() {
            this.username = this.$jwtUtil.getTokenUsername();
            this.email = this.$jwtUtil.getTokenEmail();
            this.roleName = this.$jwtUtil.getUserRoleName();
            this.axios({
                url: "/api/user/phone/" + this.email,
                method: "GET"
            }).then((res) => {
                if (res.data.code === 200) {
                    this.phone = res.data.data;
                } else {
                    this.$message({
                        message: "Failed to get phone number! Please refresh the page!",
                        type: "warning"
                    })
                }
            }).catch((err) => {
                this.$message({
                    message: "Server Error",
                    type: "danger"
                })
            })
        }
    }
</script>

<style scoped>
	#profile {
		margin-left: 5%;
		margin-right: 5%;
		font-weight: bold;

	}
	
	.rowInfo {
		padding-bottom: 10%;
		text-align: center;
	}
	input {
		font-size: 16px;
	}
</style>
<style>

</style>
