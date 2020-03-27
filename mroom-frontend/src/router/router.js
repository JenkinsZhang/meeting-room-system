import Activation from "../views/Activation";
import VueRouter from "vue-router";
import Registry from "../views/Registry";
import Test from "../views/Test";
import Login from "../views/Login";
import Home from "../views/Home";
import Booking from "../components/Booking";
import Calendar from "../components/Calendar";
import History from "../components/History";
import Profile from "../components/Profile";
import Rooms from "../components/Rooms";
import * as jwtUtil from '../assets/utils/jwt'
import {getExpireTime} from "../assets/utils/jwt";
import axios from 'axios'
import number from "less/lib/less/functions/number";
const router = new VueRouter({
    mode: 'history',
    routes: [
        {
            path: '/activation',
            name: 'activation',
            component: Activation,
            meta: {
                title: 'Activation'
            }
        },
        {
            path: '/registry',
            name: 'registry',
            component: Registry,
            meta: {
                title: 'Registry'
            }
        },
        {
            path: '/test',
            name: ' test',
            component: Test,
            meta: {
                title: 'Test'
            }
        },
        {
            path: '/login',
            name: 'login',
            component: Login,
            meta: {
                title: 'Login'
            }
        },
        {
            path: '/home',
            name: 'home',
            component: Home,
            meta: {
                title: 'Home'
            },
            children: [
                {
                    path: '/booking',
                    name: 'booking',
                    component: Booking,
                    meta: {
                        title: 'Booking'
                    }
                },
                {
                    path: '/calendar',
                    name: 'calendar',
                    component: Calendar,
                    meta: {
                        title: 'Calendar'
                    }
                },
                {
                    path: '/history',
                    name: 'history',
                    component: History,
                    meta: {
                        title: 'History'
                    }
                },
                {
                    path: '/profile',
                    name: 'profile',
                    component: Profile,
                    meta: {
                        title: 'Profile'
                    }
                },
                {
                    path: '/rooms',
                    name: 'rooms',
                    component: Rooms,
                    meta: {
                        title: 'Rooms'
                    }
                },
            ]
        }


    ],
});

router.beforeEach((to,from,next)=>{
    if(to.fullPath === "/login" || to.fullPath ==="/register")
    {
        console.log(Date.now());
        next()
    }
    else{
        let token = localStorage.getItem("access-token");
        if(token == null)
        {
            next("/login")
        }
        else {
            const expireTime = Number(jwtUtil.getExpireTime())*1000;
            const now = Number(Date.now());
            const greaterNow = Number(now + 60 * 1000 * 5);
            const lessNow = Number(now - 60 * 1000 * 5);
            //refresh Token
            console.log("expireTime: "+ expireTime);
            console.log("greaterNow: "+ greaterNow);
            if (lessNow <= expireTime && expireTime <= greaterNow) {
                axios({
                    url: 'api/auth/refresh',
                    method: "GET"
                }).then((res) => {
                    if (res.data.code === 200) {
                        console.log("Token refreshed!");
                        let newToken = res.data.data;
                        localStorage.setItem("access-token", newToken);
                        next()
                    } else {
                        next("/login")
                    }
                }).catch((error) => {
                    next("/login")
                })
            }
            //redirect to login page
            else if (expireTime < greaterNow) {
                alert("User authentication expired");
                console.log(expireTime < greaterNow)
                localStorage.removeItem("access-token");
                next("/login")
            } else {
                next();
            }
        }
    }



});
export default router

