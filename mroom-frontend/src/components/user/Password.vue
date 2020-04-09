<template>
<div>
	<el-card class="password">
		<h3>Change your password</h3>
		<br>
		<div >
			<el-row style="line-height: 50px;margin-bottom: 3%">
				<el-col :span="6">Your old password:</el-col>
				<el-col :span="18">
					<el-input type="password" :show-password="true" v-model="oldPassword"
				                             placeholder="Please enter your old password"
											v-on:change="oldPasswordValidation"/>
				</el-col>
				<el-col :span="18" :offset="6" class="invalid" v-show="oldPasswordInvalid">
					<i class="el-icon-warning-outline"/> Password doesn't match!
				</el-col>
				<el-col :span="18" :offset="6" class="valid" v-show="oldPasswordValid">
					<i class="el-icon-warning-outline"/> OK!
				</el-col>
			</el-row>
			<el-row style="line-height: 50px;margin-bottom: 2%">
				<el-col :span="6">Your new password:</el-col>
				<el-col :span="18">
					<el-input type="password" :show-password="true" v-model="newPassword"
				                             placeholder="Please enter your new password"
					@change="passwordValidate"/>
				</el-col>
				<el-col :span="18" :offset="6" class="invalid" v-show="newPasswordInvalid">
					<i class="el-icon-warning-outline"/>
					Your password should be 8 to 25 characters long and contain at least one [a-z],one [A-Z] and one [0-9]
				</el-col>
				
				<el-col :span="18" :offset="6" class="valid" v-show="newPasswordValid">
					<i class="el-icon-warning-outline"/> OK!
				</el-col>
			</el-row>
			<el-row style="line-height: 50px;margin-bottom: 3%">
				<el-col :span="6">Please enter again:</el-col>
				<el-col :span="18">
					<el-input type="password" :show-password="true" v-model="newPasswordRe"
				                             placeholder="Please enter again" @change="reEnterValidate"/></el-col>
				<el-col :span="18" :offset="6" class="invalid" v-if="reEnteredInvalid">
					<i class="el-icon-warning-outline"/> Passwords entered twice are inconsistent
				</el-col>
				<el-col :span="18" :offset="6" class="valid" v-if="reEnteredValid">
					<i class="el-icon-warning-outline"/> OK!
				</el-col>
			</el-row>
			<div style="margin-top: 5%">
				<el-button type="primary" style="margin-left: 10%;width:20%" @click="submit">Submit</el-button>
				<el-button type="danger" style="margin-left: 40%;width: 20%" @click="reset">Reset</el-button>
			</div>
			
			
		</div>
	</el-card>
</div>
</template>

<script>
    export default {
        name: "Password",
	    data(){
          return {
              oldPassword : null,
              newPassword : null,
	          newPasswordRe : null,
	          oldPasswordInvalid: null,
	          newPasswordInvalid: null,
	          reEnteredInvalid :null,
              oldPasswordValid: null,
              newPasswordValid: null,
              reEnteredValid :null
          }
	    },
	    methods:{
		    oldPasswordValidation(){
                this.axios({
	                url: "/api/user/password/validate",
	                params: {
	                    email: this.$jwtUtil.getTokenEmail(),
		                password: this.oldPassword
	                },
	                method: "POST"
                }).then((res)=>{
                    if(res.data.code === 200)
                    {
                        this.oldPasswordValid = true;
                        this.oldPasswordInvalid = false;
                    }
                    else
                    {
                        this.oldPasswordValid = false;
                        this.oldPasswordInvalid = true;
                    }
                }).catch((error)=>{
                    this.$messageUtil.errorMessage(this);
                })
		    },
            passwordValidate(){
                let reg = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[A-Za-z\d$@$!%*?&]{8,25}/g;
                this.newPasswordValid = (this.newPassword.match(reg) != null);
                this.newPasswordInvalid = !this.newPasswordValid;
                this.reEnteredValid = this.newPasswordRe === this.newPassword;
                this.reEnteredInvalid = !this.reEnteredValid;
            },
            reEnterValidate(){
                this.reEnteredValid = this.newPasswordRe === this.newPassword;
                this.reEnteredInvalid = !this.reEnteredValid;
            },
		    submit(){
		        console.log(this.reEnteredValid,this.newPasswordValid,this.oldPasswordValid);
		        if( true === this.reEnteredValid  === this.newPasswordValid === this.oldPasswordValid)
		        {
		            this.axios({
                        url: "/api/user/password/changePassword",
			            method: "POST",
			            params:{
                            email: this.$jwtUtil.getTokenEmail(),
				            oldPassword: this.oldPassword,
				            newPassword: this.newPassword
			            }
		            }).then((res)=>{
		                if(res.data.code === 200)
		                {
		                    this.$message({
			                    message:"Change success! Please re-login!",
			                    type: "success"
                            });
		                    localStorage.clear();
		                    this.$store.commit("initRoutes",[]);
		                    this.$router.push("/login");
		                }
		                else{
		                    this.$message({
			                    type: "warning",
			                    message: "Failed! Please check all fields!"
		                    })
		                }
		            }).catch((error)=>{
		                this.$messageUtil.errorMessage(this)
		            })
		        }
		        else{
		            this.$message({
			            type: "warning",
			            message: "Please check all fields before you submit!"
		            })
		        }
		    },
		    reset(){
                this.oldPassword =null;
                    this.newPassword = null;
                    this.newPasswordRe = null;
                    this.oldPasswordInvalid = null;
                    this.newPasswordInvalid = null;
                    this.reEnteredInvalid = null;
                    this.oldPasswordValid = null;
                    this.newPasswordValid = null;
                    this.reEnteredValid = null;
		    }
	    }
    }
</script>

<style scoped>
	.password{
		margin-left:5%;
		width: 90%;
		border-radius: 12px;
		margin-bottom: 5%;
	}
	.invalid{
		color: orangered;
	}
	.valid {
		color: limegreen;
	}
</style>
