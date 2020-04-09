const userRoutes = [
    {
        path: '/home',
        name: 'Booking',
        component: () => import("../views/Home"),
        meta: {
            title: 'Home',
            menuName: "Booking",
            icon: "el-icon-reading"
        },
        children: [
            {
                path: '/booking/reservation',
                name: 'booking',
                component: () => import("../components/booking/Reservation"),
                meta: {
                    title: 'Booking Room',
                    icon: 'el-icon-s-claim'
                }
            },
            {
                path: '/booking/history',
                name: 'history',
                component: () => import("../components/booking/History"),
                meta: {
                    title: 'Booking History',
                    icon: 'el-icon-time'
                }
            }
        ]
    },
    {
        path: '/home',
        name: 'Views',
        component: () => import("../views/Home"),
        meta: {
            title: 'Home',
            icon: "el-icon-view",
            menuName: "Views"
        },

        children: [
            {
                path: '/views/calendar',
                name: 'calendar',
                component: () => import("../components/views/Calendar"),
                meta: {
                    title: 'Resources Calendar',
                    icon: 'el-icon-date'
                }
            },
            {
                path: '/views/bookingStatistics',
                name: 'bookingStatistics',
                component: () => import("../components/views/BookingStatistics"),
                meta: {
                    title: 'My Booking Statistics',
                    icon: 'el-icon-s-data'
                }
            }
        ]
    },
    {
        path: '/home',
        name: 'Rooms',
        component: () => import("../views/Home"),
        meta: {
            title: 'Home',
            icon: "el-icon-office-building",
            menuName: "Rooms"
        },

        children: [
            {
                path: '/rooms/overview',
                name: 'rooms',
                component: () => import("../components/rooms/Rooms"),
                meta: {
                    title: 'Room Overview',
                    icon: 'el-icon-monitor'
                }
            },
            {
                path: '/rooms/repairReport',
                name: 'report',
                component: () => import("../components/rooms/RepairReport"),
                meta: {
                    title: 'Repair Report',
                    icon: 'el-icon-setting'
                }
            },
        ]
    },
    {
        path: '/home',
        name: 'User',
        component: () => import("../views/Home"),
        meta: {
            title: 'Home',
            icon: "el-icon-user",
            menuName: "User"
        },

        children: [
            {
                path: '/user/profile',
                name: "profile",
                component: () => import("../components/user/Profile"),
                meta: {
                    title: "My Profile",
                    icon: "el-icon-document"
                }
            },
            {
                path: "/user/password",
                name: "password",
                component: () => import("../components/user/Password"),
                meta: {
                    title: "Change Password",
                    icon: "el-icon-edit"
                }
            },
        ]
    },
];

export default userRoutes
