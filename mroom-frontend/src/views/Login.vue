<template>
	<div>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<div class="login"></div>
		<div class="description">
			<h1>Meeting-Room-Booking</h1>
			<br>
			<h2>欢迎使用会议室预订系统</h2>
		</div>
		<el-card class="login-card">
			<div slot="header">
				<h1>Login</h1>
			</div>
			<el-form style="text-align: left" label-position="top" :model="form">
				<el-form-item label="Please enter your email:" prop="email">
					<el-input
							placeholder="Enter email"
							type="email"
							v-model="form.email"
					/>
					We will never share your email
				</el-form-item>
				
				<el-form-item label="Please enter your password:" prop="password">
					<el-input
							placeholder="Enter password"
							type="text"
							v-model="form.password"
					/>
					We will never share your email
				</el-form-item>
				<a href="javascript:void(0);" @click="toRegister">Do not have an account? Click here!</a>
				<br><br>
				<div class="bottom-buttons">
					<el-button  @click="onSubmit" v-loading="loading" id="submit" type="primary" size="medium">
						<span style="font-size: 1.5rem">
							Submit</span></el-button>
				</div>
			
			</el-form>
		
		</el-card>
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
        computed: {},
        mounted() {
            window.scrollTo(0, 0)
        },
        data() {
            return {
                form: {
                    email: '',
                    password: '',
                },
                username: '',
                show: true,
                errorMsg: '',
	            loading: false
            }
        },
        methods: {
            onSubmit(evt) {
                evt.preventDefault();
                $("#submit").get(0).disabled = true;
                this.loading = true;
                this.axios({
                    method: 'POST',
                    params: {
                        'email': this.form.email,
                        'password': this.form.password
                    },
                    url: '/api/auth/login'
                }).then((res) => {
                    // console.log(res);
                    if (res.data.code === 200) {
                        let token = res.headers["access-token"];
                        localStorage.removeItem("access-token");
                        localStorage.setItem("access-token", token);
                        this.loading = false;
                        this.$router.push({name: "home"})
                    } else {
                        this.errorMsg = res.data.msg;
                        this.$message({
	                        message: this.errorMsg,
	                        type: "warning"
                        });
                        $("#submit").get(0).disabled = false;
                        this.loading = false;
                    }
                }).catch((error) => {
                    $("#submit").get(0).disabled = false;
                    this.$messageUtil.errorMessage(this);
					this.loading = false;
                })
            },
            toRegister() {
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
	
	.login-card {
		background: rgba(255, 255, 255, 0.95);
		border-radius: 12px;
		padding: 20px;
		position: fixed;
		right: 20px;
		top: 5%;
		width: 450px;
	}
	
	
	.bottom-buttons {
		/*position: absolute;*/
		width: 100%;
		/*top: 100%;*/
		/*padding-top: 10%;*/
		padding-top: 5%;
		margin-bottom: 5%;
		height: 50px;
		/*border: 5px solid pink;*/
	}
	
	.bottom-buttons button:nth-child(1) {
		width: 100%;
		height: 100%;
	}

</style>

<style>
	.el-form-item__label{
		font-size: 16px;
	}
</style>
