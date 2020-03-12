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

export default new VueRouter({
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
            path: '/',
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


    ]
})


