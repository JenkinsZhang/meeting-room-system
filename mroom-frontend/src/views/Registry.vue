<template>
	<div>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<div class="registry"></div>
		<div class="description">
			<h1>Meeting-Room-Booking</h1>
			<br>
			<h2>欢迎使用会议室预订系统</h2>
		</div>
		<b-container fluid="md">
			<b-row>
				<b-col md="5" offset-md="7">
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
							>
								<b-form-input
										id="input-box-email"
										v-model="form.email"
										type="email"
										required
										placeholder="Enter your email"
										:autofocus="true"
								/>
								<b-form-text>We will never share your email with others!</b-form-text>
							</b-form-group>
							
							<b-form-group id="input-group-username"
							              label="Your Username:"
							              label-for="input-box-username">
								<b-form-input
										id="input-box-username"
										v-model="form.username"
										required
										placeholder="Enter your username"
										autocomplete="false"
										:state="nameState"
								/>
								<b-form-invalid-feedback id="username-feedback">
									Length should be between 5~20 characters long
								</b-form-invalid-feedback>
							</b-form-group>
							
							<b-form-group
									id="input-group-password"
									label="Your Password:"
									label-for="input-2"
							>
								<b-form-input
										id="input-box-password"
										v-model="form.password"
										required
										type="password"
										placeholder="Please enter your password!"
										autocomplete="new-password"
										:state="passwordState"
								/>
								<b-form-invalid-feedback>
									Your password should contain at least one [0-9], one [a-z] and one [A-Z].
									<br>
									Your password should be at 5~25 characters long.
								</b-form-invalid-feedback>
							</b-form-group>
							
							<b-form-group id="input-group-reEnter"
							              label="Password validation:"
							              label-for="input-box-reEnter"
							>
								<b-form-input
										id="input-box-reEnter"
										v-model="form.reEnter"
										required
										type="password"
										placeholder="Please enter your password again!"
										autocomplete="new-password"
										:state="passwordReEnter"
								/>
								<b-form-invalid-feedback id="reEnter-feedback">
									The password entered twice should be the same!
								</b-form-invalid-feedback>
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
										autocomplete="false"
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
<!--		<b-button variant="primary">6666</b-button>-->
<!--		<b-card class="mt-3" header="Form Data Result">-->
<!--			<pre class="m-0">{{ form }}</pre>-->
<!--		</b-card>-->
	</div>
</template>

<script>
    export default {
        // mounted() {
        //     function setFeedback() {
		// 		var element = document.querySelector("#username-feedback");
		// 		console.log(element);
		// 		element.style.display = "none"
        //     }
        //     setFeedback()
        // },
        computed: {
            nameState(){
                return this.form.username.length>=5 && this.form.username.length<=20
            },
	        passwordState(){
                let reg = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[A-Za-z\d$@$!%*?&]{8,25}/g;
                return this.form.password.match(reg) != null;
	        },
            passwordReEnter(){
                return (this.form.reEnter === this.form.password);
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
                show: true
            }
        },
        methods: {
            onSubmit(evt) {
                evt.preventDefault();

                let reg = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[A-Za-z\d$@$!%*?&]{8,25}/g;
	            if(!(this.form.username.length>=5 && this.form.username.length<=20))
	            {
	                alert("Username's length should be between 5~20 characters long");
		            return;
	            }
	            if(this.form.password.match(reg) == null){
	                alert("Invalid password!");
		            return;
	            }
	            if(this.form.reEnter !== this.form.password){
	                alert("Check the password you re - entered");
		            return;
	            }
	            let submitData ={
	                email: this.form.email,
		            password: this.form.password,
		            username: this.form.username,
		            phone: this.form.phone
	            };
	            $("#submit").get(0).disabled = true;
	            $("#spinner").show();
	            this.axios({
		            method: 'POST',
		            data: submitData,
		            url: 'api/user/registry'
	            }).then((res)=>{
	                console.log(res.data)
	            }).catch((error)=>{
                    $("#submit").get(0).disabled = false;
                    $("#spinner").hide();
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
	
	.registry-card{
		background: rgba(255,255,255,0.95);
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
	
	[id^="input-box"]
	{
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
