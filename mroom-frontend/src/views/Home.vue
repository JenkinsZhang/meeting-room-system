<template>
	
	
	<div :class="{'test':isActive}">
		<div class="darker"></div>
		<div class="login" style="border: 1px solid red"></div>
		<b-navbar id="header" toggleable="lg" style="height: 60px" type="dark" variant="primary">
			<b-navbar-brand style="margin-left: 2%" to="/home">Meeting Room Booking System</b-navbar-brand>
			
			<b-collapse id="nav-collapse" is-nav>
				
				<!-- Right aligned nav items -->
				<b-navbar-nav style="align:center;margin-right: 15%;font-size: 20px;" class="ml-auto">
					<b-nav-item to="/home">
						<b-icon-house/>
						Home
					</b-nav-item>
					<b-nav-item-dropdown :text=username right>
						<b-dropdown-item @click="logout">Sign Out</b-dropdown-item>
					</b-nav-item-dropdown>
				</b-navbar-nav>
			</b-collapse>
		</b-navbar>
			<el-menu
					class="side-bar"
							:default-active="getRouterName"
							router>
						<el-menu-item index="booking">
							<i class="el-icon-reading"/>
							<span slot="title">Booking 我要预订</span>
						</el-menu-item>
						<el-menu-item index="history">
							<i class="el-icon-search"/>
							<span slot="title">History 预订历史</span>
						</el-menu-item>
						<el-menu-item index="calendar">
							<i class="el-icon-date"/>
							<span slot="title">Calendar 日程表</span>
						</el-menu-item>
						<el-menu-item index='rooms'>
							<i class="el-icon-view"/>
							<span slot="title">Rooms 会议室详情</span>
						</el-menu-item>
						
						<el-menu-item index='profile'>
							<i class="el-icon-user"/>
							<span slot="title">Profile 个人信息</span>
						</el-menu-item>
					
					
					</el-menu>
			<router-view class="main-body" @renderUsername="renderUsername"/>
			
		
		
		</div>
	
	
</template>

<script>
    export default {
        data() {
            return {
                username: this.$jwtUtil.getTokenUsername(),
                isActive: false,
                show: true
            }
        },
        methods: {
            test() {
                this.$router.push({name: "", params: {username: this.username}})
            },
            logout() {
                this.$bvModal.msgBoxConfirm("Are you sure you want to sign out? All your records will be cleaned!", {
                    title: "Confirm",
                    centered: true
                }).then(value => {
                    if (value) {

                        localStorage.removeItem("access-token");
                        this.username = '';
                        this.$router.push("/login");
                    }
                })

            },
            renderUsername() {
                this.username = this.$jwtUtil.getTokenUsername();
                console.log(this.username);
            }
        },
        computed: {
            getHeight() {
                return window.outerHeight
            },
            getRouterName() {
                return this.$route.name
            }
        },
        mounted() {
            // console.log(localStorage.getItem("access-token"))
            console.log(this.$jwtUtil.getTokenEmail())
        }
    }
</script>


<style scoped>
	.test {
		-webkit-filter: brightness(0.6);
	}
	
	.el-menu {
		position: relative;
	}
	
	.el-menu-item {
		margin-top: 16%;
		font-size: 16px;
	}
	
	.darker {
		/*background-color: rgba(0, 0, 0, 0.05);*/
		height: 100%;
		z-index: -1000;
		position: fixed;
		width: 100%;
		left: 0;
		top: 0;
	}
	.side-bar{
		position: fixed;
		z-index: 90;
		top: 0;
		left: 0;
		box-sizing: border-box;
		width: 230px;
		height: 100%;
		padding-top: 55px;
		border-right: 1px solid #dedede;
		overflow-y: auto;
	}
	#header{
		position: fixed;
		z-index: 91;
		top: 0;
		left: 0;
		width: 100%;
		height: 55px;
		line-height: 55px;
	}
	.main-body{
		height: 90%;
		right: 40px;
		left: 280px;
		top: 90px;
		position: absolute;
	}
</style>
