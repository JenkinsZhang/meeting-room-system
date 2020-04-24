<template>
	<div>
		<div id="header">
			<a style="font-size: 28px;margin-left: 10px"> Meeting Room Booking System </a>
		</div>
		<div style="margin-top: 15%">
			<el-row>
				<el-col :span="8" :offset="4">
					Please enter your email:
				</el-col>
			</el-row>
			<el-row>
				<el-col :span="8" :offset="4">
					<el-form style="text-align: left;" :rules="rules" :model="form" status-icon>
						<el-form-item style="margin-bottom: 5%" prop="email">
							<el-input type="email" placeholder="Enter your email" v-model="form.email"/>
						</el-form-item>
					</el-form>
				</el-col>
			</el-row>
			<el-row>
				<el-col :span="4" :offset="4">
					<el-button type="primary" @click="openSendDialog">Next</el-button>
				</el-col>
			</el-row>
			<el-dialog
					title="Email Verification"
					:visible.sync="sendDialog"
					width="50%">
				<el-form style="text-align: left;" :rules="rules" :model="form" status-icon>
					<el-row>
						<el-col :span="12">
							<el-form-item style="margin-bottom: 5%" prop="email">
								<el-input type="email" placeholder="Enter your email" v-model="form.email" disabled/>
							</el-form-item>
						</el-col>
						<el-col :span="6" :offset="2">
							<el-button type="primary" :disabled="sendDisable" @click="sendCode">{{sendCodeText()}}</el-button>
						</el-col>
					</el-row>
					<el-row>
						<el-col :span="12">
							<el-form-item style="margin-bottom: 5%" prop="email">
								<el-input  placeholder="Enter your code" v-model="code"/>
							</el-form-item>
						</el-col>
					</el-row>
					<el-row>
						<el-col :span="6" :offset="18">
							<el-button type="primary"  @click="openChangeDialog">Next</el-button>
						</el-col>
					</el-row>
					
				</el-form>
			</el-dialog>
			<el-dialog
					title="New Password"
					:visible.sync="changeDialog"
					width="50%">
				<el-form style="text-align: left;" :rules="rules" :model="form" status-icon>
					<el-row>
						<el-col :span="24">
							<el-form-item label="Email:" style="margin-bottom: 5%" prop="email">
								<el-input type="email" placeholder="Enter your email" v-model="form.email" disabled/>
							</el-form-item>
						</el-col>
					</el-row>
					<el-row>
						<el-col :span="24">
							<el-form-item label="New Password:" style="margin-bottom: 5%" prop="newPassword">
								<el-input  placeholder="Enter your password" type="password" v-model="form.newPassword"/>
							</el-form-item>
						</el-col>
					</el-row>
					<el-row>
						<el-col :span="24">
							<el-form-item label="Again:" style="margin-bottom: 5%" prop="newPasswordRe">
								<el-input  placeholder="Enter again " type="password" v-model="form.newPasswordRe"/>
							</el-form-item>
						</el-col>
					</el-row>
					<el-row>
						<el-col :span="6" :offset="18">
							<el-button type="primary"  @click="changePassword">Submit</el-button>
						</el-col>
					</el-row>
				</el-form>
			</el-dialog>
			
		</div>
	</div>

</template>

<script>
    export default {
        name: "ForgetPassword",
        data() {
            return {
                form: {
                    email: '',
                    newPassword: '',
                    newPasswordRe: ''
                },
                code: null,
                sendDialog: false,
                changeDialog: false,
                emailValid: null,
                passwordValid: null,
                reEnterValid: null,
	            sendDisable: false,
                rules: {
                    email: [
                        {validator: this.emailValidate, trigger: 'blur'}
                    ],
                    newPassword: [
                        {validator: this.passwordValidate, trigger: 'blur'}
                    ],
                    newPasswordRe: [
                        {validator: this.reEnterValidate, trigger: 'blur'}
                    ],
                },
	            timer: null
            }

        },
        methods: {
            emailValidate(rule, value, callback) {
                if (!this.form.email) {
                    this.emailValid = false;
                    return callback(new Error('Email can not be null!'))
                }
                const mailReg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
                if (mailReg.test(this.form.email)) {
                    this.emailValid = true;
                    callback();
                } else {
                    this.emailValid = false;
                    callback(new Error("Invalid email format!"))
                }

            },
            passwordValidate(rule, value, callback) {
                let reg = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[A-Za-z\d$@$!%*?&]{8,25}/g;
                this.passwordValid = (this.form.newPassword.match(reg) != null);
                this.reEnterValid = (this.form.newPasswordRe !== '' && (this.form.newPasswordRe === this.form.newPassword));
                if (!this.passwordValid) {
                    return callback(new Error("Your password should be 8 to 25 characters long and contain at least one [a-z],one [A-Z] and one [0-9]"))
                } else {
                    callback();
                }
            },
            reEnterValidate(rule, value, callback) {
                this.reEnterValid = (this.form.newPasswordRe !== '' && (this.form.newPasswordRe === this.form.newPassword));
                if (!this.reEnterValid) {
                    return callback(new Error("Password should be the same!"))
                } else {
                    callback();
                }
            },
	        openSendDialog(){
                if(this.emailValid)
                {
                    this.sendDialog = true;
                }
                else{
                    this.$messageUtil.warningMessage(this,"Please check your email!");
                }
                
	        },
	        openChangeDialog(){
                
                this.axios({
	                url: "/api/user/password/forget/verifyCode",
	                params:{
	                    code: this.code,
		                email: this.form.email
	                },
	                method: "POST"
                }).then((res)=>{
                    if(res.data.code === 200)
                    {
                        this.code = null;
                        this.sendDialog = false;
                        this.changeDialog = true;
                        
                    }else {
                        this.$messageUtil.warningMessage(this,res.data.msg);
                    }
                }).catch((err)=>{
                    this.$messageUtil.errorMessage(this);
                });
	        },
	        sendCode(){
                this.timer = 59;
                this.sendDisable =true;
                let that =this;
                const timer_interval = setInterval(function(){
                    if(that.timer > 0){
                        that.timer =  that.timer -1 ;
                    }else{
                        that.sendDisable = false;
                        clearInterval(timer_interval);
                    }
                },1000);
                this.axios({
	                url: "/api/user/password/forget/sendCode",
	                params:{
	                    email: this.form.email
	                },
	                method:"POST"
                }).then((res)=>{
                    console.log(res.data.code);
                }).catch((err)=>{
                    this.$messageUtil.errorMessage(this);
                })
	        },
	        sendCodeText(){
                if(this.timer === null)
                {
                    return "Send Verification Code";
                }
                if(this.timer !== 0)
                {
                    return "Sent ("+this.timer +"s)";
                }
                else{
                    return "Re-send";
                }
	        },
	        changePassword(){
                if(this.passwordValid && this.reEnterValid)
                this.axios({
	                url: "/api/user/password/forget/forgetPassword",
	                params:{
	                    email: this.form.email,
		                newPassword: this.form.newPassword
	                },
	                method: "POST"
                }).then((res)=>{
                    if(res.data.code === 200)
                    {
                        this.$messageUtil.successMessage(this,"Returning to login page....");
                        setTimeout(()=>{
                            this.$router.push("/login")
                        },1500);
                    }
                    else{
                        this.$messageUtil.warningMessage(this,res.data.msg);
                    }
                }).catch((err)=>{
                    this.$messageUtil.errorMessage(this);
                })
	        }
        }
    }
</script>

<style scoped>
	.darker {
		/*background-color: rgba(0, 0, 0, 0.05);*/
		height: 100%;
		z-index: -1000;
		position: fixed;
		width: 100%;
		left: 0;
		top: 0;
	}
	
	#header {
		position: fixed;
		z-index: 7;
		top: 0;
		left: 0;
		width: 100%;
		height: 65px;
		line-height: 65px;
		background-color: #007bff;
		color: white;
	}
</style>
