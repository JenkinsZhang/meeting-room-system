<template>
	<div :class="{'test':isActive}">
		<div class="darker"></div>
		<div id="header">
			<a style="font-size: 28px;margin-left: 10px"> Meeting Room Booking System </a>
			<a href="javascript:void(0)"
			   style="color: white;text-decoration: none;font-size: 22px;position: fixed;right: 25%"
			   @click="toHome"><i class="el-icon-s-home"/>Home</a>
			
			<el-dropdown style="position: fixed;right: 5%;color: white;font-size: 22px" trigger="click">
				
				<a href="javascript:void(0)" style="text-decoration: none;color:white"><span class="el-dropdown-link">
                      <i class="el-icon-location-information"/>
					{{username}}
					<i class="el-icon-arrow-down el-icon--right"/>
                 </span>
				</a>
				<el-dropdown-menu slot="dropdown">
					<el-dropdown-item style="width: 100px;font-size: 16px" >
						<a @click="logout">Sign out</a>
					</el-dropdown-item>
				</el-dropdown-menu>
			</el-dropdown>
		</div>
		<el-menu
				class="side-bar"
				:default-active="getRouterName"
				router>
			<el-submenu index="booking">
				<template slot="title">
					<i class="el-icon-reading" style="margin-top: -3%"/>
					<span>Booking</span>
				</template>
				<el-menu-item index="/booking/reservation">
					<i class="el-icon-s-claim" style="margin-top: -3%"/>
					<span>Booking Room</span>
				</el-menu-item>
				<el-menu-item index="/booking/history">
					<i class="el-icon-time" style="margin-top: -3%"/>
					<span>Booking History</span>
				</el-menu-item>
			</el-submenu>
			<el-submenu index="views">
				<template slot="title">
					<i class="el-icon-view" style="margin-top: -3%"/>
					<span>Views</span>
				</template>
				<el-menu-item index="/views/calendar">
					<i class="el-icon-date" style="margin-top: -3%"/>
					<span>Resources Calendar</span>
				</el-menu-item>
				<el-menu-item index="/views/bookingStatistics">
					<i class="el-icon-s-data" style="margin-top: -3%"/>
					<span>My Booking Statistics</span>
				</el-menu-item>
				<el-menu-item index="/views/admin/bookingStatistics">
					<i class="el-icon-s-data" style="margin-top: -3%"/>
					<span>Booking Statistics</span>
				</el-menu-item>
			</el-submenu>
			<el-submenu index="rooms">
				<template slot="title">
					<i class="el-icon-office-building" style="margin-top: -3%"/>
					<span>Rooms</span>
				</template>
				<el-menu-item index="/rooms/overview">
					<i class="el-icon-monitor" style="margin-top: -3%"/>Room Overview
				</el-menu-item>
				<el-menu-item index="/rooms/repairReport">
					<i class="el-icon-setting" style="margin-top: -3%"/>Repair Report
				</el-menu-item>
			</el-submenu>
			<el-submenu index="user">
				<template slot="title">
					<i class="el-icon-user" style="margin-top: -3%"/>
					<span>User</span>
				</template>
				<el-menu-item index="/user/profile">
					<i class="el-icon-document" style="margin-top: -3%"/>My Profile
				</el-menu-item>
				<el-menu-item index="/user/password">
					<i class="el-icon-edit" style="margin-top: -3%"/>Change Password
				</el-menu-item>
			</el-submenu>
			<el-submenu index="management">
				<template slot="title">
					<i class="el-icon-s-custom" style="margin-top: -3%"/>
					<span>Management</span>
				</template>
				<el-menu-item index="/admin/accounts">
					<i class="el-icon-user-solid" style="margin-top: -3%"/>User Accounts
				</el-menu-item>
				<el-menu-item index="/admin/records">
					<i class="el-icon-s-order" style="margin-top: -3%"/>Booking Records
				</el-menu-item>
				<el-menu-item index="/admin/rooms">
					<i class="el-icon-s-platform" style="margin-top: -3%"/>Room Management
				</el-menu-item>
				<el-menu-item index="/admin/roles">
					<i class="el-icon-s-check" style="margin-top: -3%"/>Roles Authorization
				</el-menu-item>
			</el-submenu>
		
		
		</el-menu>
		<router-view class="main-body" @renderUsername="renderUsername" />
	
	
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
                console.log(this.$router)
            },
            logout() {
                this.$confirm("Are you sure you want to logout? All your data will be cleaned","Logout",{
                    type: "info",
                    confirmButtonText: "Submit",
                    cancelButtonText: "Cancel",
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
            },
            toHome() {
                this.$router.push("/")
            }
        },
        computed: {
            getRouterName() {

                return this.$route.path;

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
	
	.darker {
		/*background-color: rgba(0, 0, 0, 0.05);*/
		height: 100%;
		z-index: -1000;
		position: fixed;
		width: 100%;
		left: 0;
		top: 0;
	}
	
	.side-bar {
		position: fixed;
		z-index: 6;
		top:3%;
		left: 0;
		box-sizing: border-box;
		width: 18%;
		height: 100%;
		padding-top: 70px;
		border-right: 1px solid #dedede;
		overflow-y: auto;
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
	
	.main-body {
		position: absolute;
		left: 19%;
		right: 2%;
		top: 20%;
		margin-bottom: 5%;
	}

</style>
<style>
	.el-message-box__title{
		font-size: 22px;
	}
	.el-message-box__content{
		font-size: 16px;
	}
</style>
