<template>
	<div id="profile">
		<b-card title="Personal Information"
		        style="border-radius: 12px;
		        height: 90%;text-align: center;"
		>
			<br>
			<b-container fluid style="text-align: left;">
				<b-row>
					<b-col sm="4">
						
						<img src="../assets/images/avatar.jpeg"  width="100%" height="300">
					</b-col>
					<b-col sm="8">
						<b-row class="rowInfo">
							<b-col sm="3">
								<label for="email"/>Email:
							</b-col>
							<b-col sm="6">
								<input id="email" disabled="true"
								       style="width: 100%" v-model="email"/>
							</b-col>
						</b-row>
						<b-row class="rowInfo">
							<b-col sm="3">
								<label for="username"/>Username:
							</b-col>
							<b-col sm="6">
								<input id="username" disabled="true"
								       v-model="username" style="width: 100%"/>
							</b-col>
							<b-col sm="3">
								<a href="javascript:void(0);" @click="editUsername">Edit</a>
								<a href="javascript:void(0);" @click="saveUsername" style="margin-left:5%">Save</a>
							</b-col>
						</b-row>
						<b-row class="rowInfo">
							<b-col sm="3">
								<label for="cellPhone"/>Cell Phone:
							</b-col>
							<b-col sm="6">
								<input id="cellPhone" disabled="true" style="width: 100%"
								       v-model="phone"/>
							</b-col>
							<b-col sm="3">
								<a href="javascript:void(0);" @click="editPhone">Edit</a>
								<a href="javascript:void(0);" @click="savePhone" style="margin-left:5%">Save</a>
							</b-col>
						</b-row>
					</b-col>
				</b-row>
			
			
			</b-container>
		</b-card>
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
                    url: "api/user/username/" + this.email + "/" + this.username,
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
                    url: "api/user/phone/" + this.email + "/" + this.phone,
                    method: "PUT"
                }).then((res) => {
                    if (res.data.code === 200) {
                        this.axios({
                            url: "api/user/phone/" + this.email,
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
            this.axios({
                url: "api/user/phone/" + this.email,
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
		padding-bottom: 20%;
		text-align: center;
	}
</style>
