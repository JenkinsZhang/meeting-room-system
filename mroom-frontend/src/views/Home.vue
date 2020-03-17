<template>
	
	
	<div :class="{'test':isActive}">
		<div class="darker"></div>
		<b-navbar  id="header"  toggleable="lg" style="height: 60px" type="dark" variant="primary">
			<b-navbar-brand style="margin-left: 2%" to="/">Meeting Room Booking System</b-navbar-brand>
			
			<b-collapse id="nav-collapse" is-nav>
				
				<!-- Right aligned nav items -->
				<b-navbar-nav  style="align:center;margin-right: 15%;font-size: 20px;" class="ml-auto">
					<b-nav-item to="/">
						<b-icon-house/>
						Home
					</b-nav-item>
					<b-nav-item-dropdown :text=username right >
						<b-dropdown-item @click="logout">Sign Out</b-dropdown-item>
					</b-nav-item-dropdown>
				</b-navbar-nav>
			</b-collapse>
		</b-navbar>
		<div>
			<b-container fluid>
				<b-row>
					<b-col sm="3">
						<el-menu class="side_bar"
						         :style="{'height':getHeight+'px'}"
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
							<el-menu-item index="calendar" >
								<i class="el-icon-date"/>
								<span slot="title">Calendar 日程表</span>
							</el-menu-item>
							<el-menu-item index='rooms' >
								<i class="el-icon-view"/>
								<span slot="title">Rooms 会议室详情</span>
							</el-menu-item>
							<el-menu-item index='profile' >
								<i class="el-icon-user"/>
								<span slot="title">Profile 个人信息</span>
							</el-menu-item>
							
						</el-menu>
					</b-col>
					<b-col md="9" class="">
						<router-view v-if="false"/>
						<router-view/>
					</b-col>
				</b-row>
			</b-container>
			
		</div>
		
		
		
	</div>
</template>

<script>
    export default {
        data(){
            return {
                username: this.$jwtUtil.getTokenUsername(),
	            isActive: false,
	            show:true
            }
        },
	    methods:{
            test(){
                this.$router.push({name:"",params:{username:this.username}})
            },
            logout(){
                this.$bvModal.msgBoxConfirm("确认登出账号？",{
                    title:"确认",
	                centered: true
                }).then(value=>{
                    if(value)
                    {
                        
                        localStorage.removeItem("access-token");
                        this.username = '';
                        this.$router.push("/login");
                    }
                })
                
            }
	    },
	    computed:{
            getHeight(){
                return window.innerHeight-60
            },
		    getRouterName(){
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
	.test{
		-webkit-filter: brightness(0.6);
	}
	.el-menu{
		position: relative;
	}
	.el-menu-item{
		margin-top: 5%;
		font-size: 16px;
	}
	*{
		padding: 0;
		margin: 0;
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
</style>
