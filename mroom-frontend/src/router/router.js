import VueRouter from "vue-router";

const router = new VueRouter({
        mode: 'history',
        routes: [
            {
                path: '/activation',
                name: 'activation',
                component: ()=>import("../views/Activation"),
                meta: {
                    title: 'Activation'
                }
            },
            {
                path: '/registry',
                name: 'registry',
                component: ()=>import("../views/Registry"),
                meta: {
                    title: 'Registry'
                }
            },
            {
                path: '/test',
                name: ' test',
                component: ()=>import("../views/Test"),
                meta: {
                    title: 'Test'
                }
            },
            {
                path: '/login',
                name: 'login',
                component: ()=>import("../views/Login"),
                meta: {
                    title: 'Login'
                }
            },
            {
                path: '/',
                name: 'home',
                component: ()=>import("../views/Home"),
                meta: {
                    title: 'Home'
                },
                children: [
                    {
                        path: '/booking/reservation',
                        name: 'booking',
                        component: ()=>import("../components/Reservation"),
                        meta: {
                            title: 'Booking Room',
                            icon: 'el-icon-s-claim'
                        }
                    },
                    {
                        path: '/booking/history',
                        name: 'history',
                        component: ()=>import("../components/History"),
                        meta: {
                            title: 'Booking History',
                            icon: 'el-icon-time'
                        }
                    },
                    {
                        path: '/views/calendar',
                        name: 'calendar',
                        component: ()=>import("../components/Calendar"),
                        meta: {
                            title: 'Resources Calendar',
                            icon: 'el-icon-date'
                        }
                    },
                    {
                        path: '/views/bookingStatistics',
                        name: 'bookingStatistics',
                        component: ()=>import("../components/BookingStatistics"),
                        meta: {
                            title: 'My Booking Statistics',
                            icon: 'el-icon-s-data'
                        }
                    },
                    {
                        path: '/views/admin/bookingStatistics',
                        name: 'adminBookingStatistics',
                        component: ()=>import("../components/AdminBookingStatistics"),
                        meta: {
                            title: 'Booking Statistics',
                            icon: 'el-icon-s-data'
                        }
                    },
                    {
                        path: '/rooms/overview',
                        name: 'rooms',
                        component: ()=>import("../components/Rooms"),
                        meta: {
                            title: 'Room Overview',
                            icon: 'el-icon-monitor'
                        }
                    },
                    {
                        path: '/rooms/repairReport',
                        name: 'report',
                        component: ()=>import("../components/RepairReport"),
                        meta: {
                            title: 'Repair Report',
                            icon: 'el-icon-setting'
                        }
                    },
                    {
                        path: 'user/profile',
                        name: "profile",
                        component: ()=>import("../components/Profile"),
                        meta: {
                            title: "My Profile",
                            icon: "el-icon-document"
                        }
                    },
                    {
                        path: "user/password",
                        name: "password",
                        component: ()=>import("../components/Password"),
                        meta: {
                            title: "Change Password",
                            icon: "el-icon-edit"
                        }
                    },
                    {
                        path: "/admin/accounts",
                        name: "accounts",
                        component: ()=>import("../components/AccountManagement"),
                        meta: {
                            title: "User Accounts",
                            icon: "el-icon-user-solid"
                        }
                    },
                    {
                        path: "/admin/records",
                        name: "records",
                        component: ()=>import("../components/BookingManagement"),
                        meta: {
                            title: "Booking Records",
                            icon: "el-icon-s-order"
                        }
                    },
                    {
                        path: "/admin/rooms",
                        name: "adminRooms",
                        component: ()=>import("../components/RoomsManagement"),
                        meta: {
                            title: "Room Management",
                            icon: "el-icon-s-platform"
                        }
                    },
                    {
                        path: "/admin/roles",
                        name: "roles",
                        component: ()=>import("../components/RolesManagement"),
                        meta: {
                            title: "Roles Authorization",
                            icon: "el-icon-s-check"
                        }
                    },

                ]
            },
            {
                path: "*",
                redirect: "/"
            }


        ],
    })
;

// router.beforeEach((to,from,next)=>{
//     if(to.fullPath === "/login" || to.fullPath ==="/register")
//     {
//         console.log(Date.now());
//         next()
//     }
//     else{
//         let token = localStorage.getItem("access-token");
//         if(token == null)
//         {
//             next("/login")
//         }
//         else {
//             const expireTime = Number(jwtUtil.getExpireTime())*1000;
//             const now = Number(Date.now());
//             const greaterNow = Number(now + 60 * 1000 * 5);
//             const lessNow = Number(now - 60 * 1000 * 5);
//             //refresh Token
//             console.log("expireTime: "+ expireTime);
//             console.log("greaterNow: "+ greaterNow);
//             if (lessNow <= expireTime && expireTime <= greaterNow) {
//                 axios({
//                     url: 'api/auth/refresh',
//                     method: "GET"
//                 }).then((res) => {
//                     if (res.data.code === 200) {
//                         console.log("Token refreshed!");
//                         let newToken = res.data.data;
//                         localStorage.setItem("access-token", newToken);
//                         next()
//                     } else {
//                         next("/login")
//                     }
//                 }).catch((error) => {
//                     next("/login")
//                 })
//             }
//             //redirect to login page
//             else if (expireTime < greaterNow) {
//                 alert("User authentication expired");
//                 console.log(expireTime < greaterNow)
//                 localStorage.removeItem("access-token");
//                 next("/login")
//             } else {
//                 next();
//             }
//         }
//     }
//
//
//
// });
export default router

