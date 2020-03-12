<template>
	<div>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<div class="registry"></div>
<!--		<b-alert variant="success" show>Success Alert</b-alert>-->
		<div class="description">
			<h1>Meeting-Room-Booking</h1>
			<br>
			<h2>欢迎使用会议室预订系统</h2>
		</div>
		<b-container fluid>
			<b-row>
				<b-col md="4" xl="4" offset-md="8" offset-xl="8">
					<b-card class="registry-card"
					        title="Welcome to Register"
					        title-tag="h2"
					        style="margin-bottom: 5%;margin-top: 5%"
					        align="center">
						<b-form @submit="onSubmit" @reset="onReset" v-if="show" style="text-align: left;margin-top: 5%">
							<b-form-group
									id="input-group-email"
									label="Email address:"
									label-for="input-box-email"
									:invalid-feedback="emailFeedback"
							>
								<b-form-input
										id="input-box-email"
										v-model="form.email"
										type="email"
										required
										placeholder="Enter your email"
										:autofocus="true"
										:lazy="true"
										:state="emailValid"
										v-on:change="emailValidate"
								/>
							</b-form-group>
							
							<b-form-group id="input-group-username"
							              label="Your Username:"
							              label-for="input-box-username"
							              :invalid-feedback="usernameFeedback">
								<b-form-input
										id="input-box-username"
										v-model="form.username"
										required
										placeholder="Enter your username"
										:state="usernameValid"
										:lazy="true"
										v-on:change="usernameValidate"
								/>
							</b-form-group>
							
							<b-form-group
									id="input-group-password"
									label="Your Password:"
									label-for="input-2"
									:invalid-feedback="passwordFeedback"
							
							>
								<b-form-input
										id="input-box-password"
										v-model="form.password"
										required
										type="password"
										placeholder="Please enter your password!"
										:state="passwordValid"
										:lazy="true"
										v-on:change="passwordValidate"
								/>
							</b-form-group>
							
							<b-form-group id="input-group-reEnter"
							              label="Password validation:"
							              label-for="input-box-reEnter"
							              :invalid-feedback="reEnterFeedback"
							>
								<b-form-input
										id="input-box-reEnter"
										v-model="form.reEnter"
										required
										type="password"
										placeholder="Please enter your password again!"
										:state="reEnterValid"
										:lazy="true"
										v-on:change="reEnterValidate"
								/>
							</b-form-group>
							
							<b-form-group id="input-group-phone"
							              label="Cellphone:"
							              label-for="input-box-phone"
							>
								<b-form-input
										id="input-box-phone"
										v-model="form.phone"
										required
										type="tel"
										placeholder="Please enter your Cellphone number!"
								/>
							</b-form-group>
							
							<div class="bottom-buttons">
								<b-button id="submit" type="submit" variant="primary" size="lg">
									<b-spinner id="spinner" class="small" style="display: none"/>
									Registry
								</b-button>
								<b-button type="reset" variant="danger" size="lg">Reset</b-button>
							</div>
						</b-form>
					</b-card>
				</b-col>
			
			
			</b-row>
		</b-container>
	</div>
</template>

<script>
    export default {
        computed: {
            emailFeedback(){
                if(this.form.email.length <1)
                {
                    return "Please enter your email!"
                }
                else {
                    return this.state === true ? "" : "This email has been registered!"
                }
                
            },
            usernameFeedback() {
                return this.state === true ? "" : "Your username should be 5 to 20 characters long!"
            },
            passwordFeedback() {
                return this.state === true ? ""
                    : "Your password should be 8 to 25 characters long and contain at least one [a-z],one [A-Z] and one [0-9]"
            },
            reEnterFeedback() {
                if (this.form.reEnter.length < 1) {
                    return "Please enter your password again!"
                } else if (this.form.password !== this.form.reEnter) {
                    return "Passwords entered twice are inconsistent"
                }

            }
        },
        data() {
            return {
                form: {
                    email: '',
                    username: '',
                    phone: '',
                    password: '',
                    reEnter: ''
                },
                show: true,
                emailValid: null,
	            usernameValid: null,
	            passwordValid: null,
	            reEnterValid: null,
            }
        },
        methods: {
	        emailValidate() {
	            if(this.form.email.length<1)
	            {
	                this.emailValid = false;
	                return;
	            }
                this.axios({
	                method: 'POST',
	                url: "api/user/check/"+this.form.email
                }).then((res) =>{
                    console.log(res.data);
                    console.log(res.data.code === 200);
                    this.emailValid = res.data.code === 200;
                }).catch((err)=>{
                    alert("Server error! Please contact the administrator!")
                })
            },
	        usernameValidate(){
                this.usernameValid = !(this.form.username.length < 5 || this.form.username.length > 25);
	        },
	        passwordValidate(){
                let reg = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[A-Za-z\d$@$!%*?&]{8,25}/g;
                this.passwordValid = (this.form.password.match(reg) != null);
	        },
	        reEnterValidate(){
                this.reEnterValid = (this.form.reEnter !== '' && (this.form.reEnter === this.form.password))
	        },
            onSubmit(evt) {
                evt.preventDefault();

                let reg = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[A-Za-z\d$@$!%*?&]{8,25}/g;
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
                $("#spinner").show();
                this.axios({
                    method: 'POST',
                    data: submitData,
                    url: 'api/user/registry'
                }).then((res) => {
                    console.log(res.data);
                    if(res.data.code === 200)
                    {
                        $(window).scrollTop(0);
                        alert("Registry succeeded! An activation email has been sent to your email, now returning to login page");
                        setTimeout(this.toLogin,3000)
                    }
                    else {
                        $("#submit").attr("disabled", false);
                        $("#spinner").hide();
                        $(window).scrollTop(0);
	                    alert(res.data.msg)
                    }
                }).catch((error) => {
                    $("#submit").attr("disabled", false);
                    // $("#submit").get(0).disabled = false;
                    $("#spinner").hide();
                    alert("Server error! Please contact the administrator!")
                })

            },
            onReset(evt) {
                evt.preventDefault();
                // Reset our form values
                this.form.email = '';
                this.form.username = '';
                this.form.password = '';
                this.form.reEnter = '';
                this.form.phone = ''
                // Trick to reset/clear native browser form validation state
                // this.show = false
                // this.$nextTick(() => {
                //     this.show = true
                // })
            },
	        toLogin(evt){
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
		margin-bottom: 5%;
		/*border: 5px solid pink;*/
	}
	
	.bottom-buttons button:nth-child(1) {
		margin-left: 10%;
		width: 40%;
		/*position: absolute;*/
		/*left: 20%;*/
		/*bottom: 3%;*/
	}
	
	.bottom-buttons button:nth-child(2) {
		margin-left: 10%;
		width: 30%;
		/*position: absolute;*/
		/*right: 20%;*/
		/*bottom: 3%;*/
		
	}

</style>
