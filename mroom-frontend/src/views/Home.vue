<template>
	
	<div :class="{'test':isActive}">
		<div class="darker"></div>
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
				<el-menu-item index="/rooms/repairReport" >
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
				<el-menu-item index="/user/password" >
					<i class="el-icon-edit" style="margin-top: -3%"/>Change Password
				</el-menu-item>
			</el-submenu>
			<el-submenu index="management">
				<template slot="title">
					<i class="el-icon-s-custom" style="margin-top: -3%"/>
					<span>Management</span>
				</template>
				<el-menu-item index="/admin/accounts">
					<i class="el-icon-user-solid"style="margin-top: -3%"/>User Accounts
				</el-menu-item>
				<el-menu-item index="/admin/records">
					<i class="el-icon-s-order" style="margin-top: -3%"/>Booking Records
				</el-menu-item>
				<el-menu-item index="/admin/rooms">
					<i class="el-icon-s-platform"style="margin-top: -3%"/>Room Management
				</el-menu-item>
				<el-menu-item index="/admin/roles">
					<i class="el-icon-s-check"style="margin-top: -3%"/>Roles Authorization
				</el-menu-item>
			</el-submenu>
			
			
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
	            console.log(this.$router)
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
		z-index: 9;
		top: 0;
		left: 0;
		box-sizing: border-box;
		width: 18%;
		height: 100%;
		padding-top: 80px;
		border-right: 1px solid #dedede;
		overflow-y: auto;
	}
	
	#header {
		position: fixed;
		z-index: 91;
		top: 0;
		left: 0;
		width: 100%;
		height: 55px;
		line-height: 55px;
	}
	
	.main-body {
		right: 0px;
		left: 19%;
		top: 20%;
		position: absolute;
	}
	
</style>

