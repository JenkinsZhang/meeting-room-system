<template>
	<div>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<div class="login"></div>
		<div class="description">
			<h1>Meeting-Room-Booking</h1>
			<br>
			<h2>欢迎使用会议室预订系统</h2>
		</div>
		<b-container fluid="">
			<b-row style="margin-top: 7%;">
				<b-col md="4" offset-md="8">
					<b-card class="login-card"
					        title="Login"
					        title-tag="h2"
							align="center"
							v-if="show">
						<b-form   @submit="onSubmit" style="text-align: left">
							<b-form-group
									description="We'll never share your email with anyone else."
									id="input-group-1"
									label="Email address:"
									label-for="input-1"
							>
								<b-form-input
										id="input-1"
										placeholder="Enter email"
										required
										type="email"
										v-model="form.email"
								/>
							</b-form-group>
							
							<b-form-group
									id="input-group-2"
									label="Your Password:"
									label-for="input-2">
								<b-form-input
										id="input-2"
										placeholder="Enter your password"
										required
										type="password"
										v-model="form.password"
								/>
								<b-form-text id="input-2-help">
									Please enter your Password
								</b-form-text>
							</b-form-group>
							
							
							<b-form-group id="input-group-4">
								<b-form-checkbox-group id="checkboxes-4" v-model="form.checked">
									<b-form-checkbox value="remember">Remember Me</b-form-checkbox>
									<b-form-text> Do not select this option in public</b-form-text>
								</b-form-checkbox-group>
							</b-form-group>
							<a href="#" @click="toRegister">Do not have an account? Click here!</a>
							<div class="bottom-buttons">
								<b-button id="submit" type="submit" variant="primary" size="lg">
									<b-spinner id="spinner" class="small" style="display: none"/>
									Submit
								</b-button>
							</div>
						
						</b-form>
					
					</b-card>
				</b-col>
			</b-row>
		</b-container>
		<b-modal id="loginServerError" centered title="Error">
			Server error! Please try another time !
		</b-modal>
		<b-modal id="loginInfoError" centered title="Error">
			{{errorMsg}}
		</b-modal>
	</div>
</template>

<script>
    export default {
        name: 'login',
        // mounted() {
        //     function setFeedback() {
        // 		var element = document.querySelector("#username-feedback");
        // 		console.log(element);
        // 		element.style.display = "none"
        //     }
        //     setFeedback()
        // },
        computed: {
        
        },
	    mounted() {
            window.scrollTo(0,0)
        },
        data() {
            return {
                form: {
                    email: '',
                    password: '',
                },
	            username: '',
                show: true,
	            errorMsg: ''
            }
        },
        methods: {
            onSubmit(evt) {
                evt.preventDefault();
                $("#submit").get(0).disabled = true;
                $("#spinner").show();
                this.axios({
                    method: 'POST',
                    params: {
                        'email': this.form.email,
	                    'password': this.form.password
                    },
                    url: 'api/auth/login'
                }).then((res)=>{
                    // console.log(res);
	                if(res.data.code===200)
	                {
		                let token = res.headers["access-token"];
		                localStorage.removeItem("access-token");
		                localStorage.setItem("access-token",token);
	                    this.$router.push({name:"home"})
	                }
	                else {
                        this.errorMsg = res.data.msg;
                        this.$bvModal.show("loginInfoError");
                        $("#submit").get(0).disabled = false;
                        $("#spinner").hide();
	                }
                }).catch((error)=>{
                    this.$bvModal.show("loginServerError");
                    $("#submit").get(0).disabled = false;
                    $("#spinner").hide();
                    
                })
            },
	        toRegister(){
                this.$router.push("/registry")
	        }
        }
    }
</script>


<style scoped>
	* {
		margin: 0;
		padding: 0;
	}
	
	.login {
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
	
	.login-card{
		background: rgba(255,255,255,0.95);
		border-radius: 12px;
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
		width: 100%;
		height: 100%;
	}

</style>
