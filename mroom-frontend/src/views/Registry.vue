<template>
	<div>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<div class="registry"></div>
		<!--		<b-alert variant="success" show>Success Alert</b-alert>-->
		<div class="description">
			<h1>Meeting-Room-Booking</h1>
			<br>
			<h2>Welcome!</h2>
		</div>
		<el-row>
			<el-col :span="8" :offset="5">
				<el-card class="registry-card"
				>
					<div slot="header">
						<h1>Welcome to Register</h1>
					</div>
					<el-form style="text-align: left;" :rules="rules" :model="form" status-icon>
						<el-form-item label="Email address: " style="margin-bottom: 5%" prop="email">
							<el-input type="email" placeholder="Enter your email" v-model="form.email">
							
							</el-input>
						</el-form-item>
						<el-form-item label="Your Username: " style="margin-bottom: 5%" prop="username">
							<el-input type="text" placeholder="Enter your username" v-model="form.username">
							
							</el-input>
						</el-form-item>
						<el-form-item label="Your Password: " style="margin-bottom: 5%" prop="password">
							<el-input type="password" placeholder="Please enter your password!" v-model="form.password">
							
							</el-input>
						</el-form-item>
						<el-form-item label="Password validation: " style="margin-bottom: 5%" prop="reEnter">
							<el-input type="password" placeholder="Please enter your password again!"
							          v-model="form.reEnter">
							
							</el-input>
						</el-form-item>
						<el-form-item label="Cellphone: " style="margin-bottom: 5%" prop="phone">
							<el-input type="phone" placeholder="Please enter your Cellphone number!"
							          v-model="form.phone">
							
							</el-input>
						</el-form-item>
						
						
						<div class="bottom-buttons">
							<el-button v-loading="loading" style="height: 50px;" id="submit" type="primary"
							           @click="onSubmit">
								Registry
							</el-button>
							<el-button style="height: 50px;" type="danger" @click="onReset">Reset</el-button>
						</div>
					</el-form>
				</el-card>
			</el-col>
		
		
		</el-row>
	</div>
</template>

<script>
    export default {
        data() {
            return {
                form: {
                    email: '',
                    username: '',
                    phone: '',
                    password: '',
                    reEnter: ''
                },
                loading: false,
                emailValid: null,
                usernameValid: null,
                passwordValid: null,
                reEnterValid: null,
                rules: {
                    email: [
                        {validator: this.emailValidate, trigger: 'blur'}
                    ],
                    username: [
                        {validator: this.usernameValidate, trigger: 'blur'}
                    ],
                    password: [
                        {validator: this.passwordValidate, trigger: 'blur'}
                    ],
                    reEnter: [
                        {validator: this.reEnterValidate, trigger: 'blur'}
                    ],
                    phone: [
                        {validator: this.phoneValidate, trigger: 'blur'}
                    ]
                }
            }
        },
        methods: {
            emailValidate(rule, value, callback) {
                if (!this.form.email) {
                    this.emailValid = false;
                    return callback(new Error('Email can not be null!'))
                }
                const mailReg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
                if (mailReg.test(this.form.email)){
                    setTimeout(() => {
                        this.axios({
                            method: 'POST',
                            url: "/api/user/check/" + this.form.email
                        }).then((res) => {
                            console.log(res.data);
                            console.log(res.data.code === 200);
                            this.emailValid = res.data.code === 200;
                            if (!this.emailValid) {
                                return callback(new Error('This email has been taken!'))
                            }
                            else{
                                callback()
                            }
                        }).catch((err) => {
                            this.$messageUtil.errorMessage(this);
                        })
                    }, 500);
                }
				else{
				    callback(new Error("Invalid email format!"))
                }

            },
            usernameValidate(rule,value,callback) {
                this.usernameValid = !(this.form.username.length < 5 || this.form.username.length > 25);
                if(! this.usernameValid)
                {
                    return callback(new Error("Your username should be 5 to 25 characters long"))
                }
                else {
                    callback();
                }
            },
            passwordValidate(rule,value,callback) {
                let reg = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[A-Za-z\d$@$!%*?&]{8,25}/g;
                this.passwordValid = (this.form.password.match(reg) != null);
                this.reEnterValid = (this.form.reEnter !== '' && (this.form.reEnter === this.form.password));
                if(!this.passwordValid) {
                    return callback(new Error("Your password should be 8 to 25 characters long and contain at least one [a-z],one [A-Z] and one [0-9]"))
                }else{
	                callback();
                }
            },
            reEnterValidate(rule,value,callback) {
                this.reEnterValid = (this.form.reEnter !== '' && (this.form.reEnter === this.form.password));
	            if(!this.reEnterValid)
	            {
	                return callback(new Error("Password should be the same!"))
	            }
	            else {
	                callback();
	            }
            },
	        phoneValidate(rule,value,callback) {
                const phoneReg = /^1[3|4|5|7|8][0-9]{9}$/;
                if (!this.form.phone) {
                    return callback(new Error("Phone number can not be null"))
                }
                setTimeout(() => {
                    // Number.isInteger是es6验证数字是否为整数的方法,但是我实际用的时候输入的数字总是识别成字符串
                    // 所以我就在前面加了一个+实现隐式转换

                    if (!Number.isInteger(+this.form.phone)) {
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
            onSubmit(evt) {
                evt.preventDefault();

                let reg = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[A-Za-z\d$@$!%*?&]{8,25}/g;
                if (this.emailValid !== true)
                {
                    alert("Please enter valid email!");
                    return;
                }
                if (!(this.form.username.length >= 5 && this.form.username.length <= 20)) {
                    alert("Username's length should be between 5~20 characters long");
                    return;
                }
                if (this.form.password.match(reg) == null) {
                    alert("Invalid password!");
                    return;
                }
                if (this.form.reEnter !== this.form.password) {
                    alert("Check the password you re - entered");
                    return;
                }
                
                let submitData = {
                    email: this.form.email,
                    password: this.form.password,
                    username: this.form.username,
                    phone: this.form.phone
                };
                $("#submit").attr("disabled", false);
                this.loading = true;
                this.axios({
                    method: 'POST',
                    data: submitData,
                    url: '/api/user/registry'
                }).then((res) => {
                    console.log(res.data);
                    if (res.data.code === 200) {
                        $(window).scrollTop(0);
                        alert("Registry succeeded! An activation email has been sent to your email, now returning to login page");
                        this.loading = false;
                        setTimeout(this.toLogin, 3000)
                    } else {
                        $("#submit").attr("disabled", false);
                        $(window).scrollTop(0);
                        alert(res.data.msg)
                    }
                }).catch((error) => {
                    $("#submit").attr("disabled", false);
                    // $("#submit").get(0).disabled = false;
                    this.loading = false;
                    alert("Server error! Please contact the administrator!")
                })

            },
            onReset(evt) {
                // Reset our form values
                this.form.email = null;
                this.form.username = null;
                this.form.password = null;
                this.form.reEnter = null;
                this.form.phone = null;
                this.emailValid = false;
            },
            toLogin(evt) {
                this.$router.push("/login")
            }
        }
    }
</script>


<style scoped>
	* {
		margin: 0;
		padding: 0;
	}
	
	.registry {
		position: fixed;
		left: 0;
		right: 0;
		top: 0;
		bottom: 0;
		background: url("../assets/images/meetingroom.jpeg") no-repeat;
		max-width: 1920px;
		min-width: 320px;
		background-size: cover;
	}
	
	.description {
		position: fixed;
		top: 30%;
		left: 2%;
		height: 50px;
		width: 30%;
		line-height: 50px;
		text-align: center;
		/*border: 1px solid white;*/
		/*background: white;*/
		/*font-size: 36px;*/
		color: black;
	}
	
	.registry-card {
		background: rgba(255, 255, 255, 0.95);
		border-radius: 12px;
		padding: 20px;
		width: 450px;
		position: absolute;
		right: 0;
	}
	
	
	[id^="input-group"]:nth-child(-n+4) {
		/*border: 1px solid black;*/
		margin-bottom: 3%;
		margin-left: 2%;
	}
	
	[id^="input-group"]:nth-child(5) {
		/*border: 1px solid black;*/
		/*margin-bottom: 3%;*/
		margin-left: 2%;
		
	}
	
	[id^="input-box"] {
		margin-left: -1%;
	}
	
	.bottom-buttons {
		/*position: absolute;*/
		width: 100%;
		/*top: 100%;*/
		/*padding-top: 10%;*/
		padding-top: 5%;
		/*border: 5px solid pink;*/
	}
	
	.bottom-buttons button:nth-child(1) {
		margin-left: 5%;
		width: 40%;
		/*position: absolute;*/
		/*left: 20%;*/
		/*bottom: 3%;*/
	}
	
	.bottom-buttons button:nth-child(2) {
		margin-left: 10%;
		width: 40%;
		/*position: absolute;*/
		/*right: 20%;*/
		/*bottom: 3%;*/
		
	}

</style>
<style>
	.el-form-item__label {
		font-size: 16px;
	}
	
	.el-form-item__error {
		font-size: 14px;
	}
</style>
