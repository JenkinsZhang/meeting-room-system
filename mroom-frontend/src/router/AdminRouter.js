const adminRouter =[
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
                    title: 'Booking Statistics',
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
            }
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
    {
        path: '/home',
        name: 'Management',
        component: () => import("../views/Home"),
        meta: {
            title: 'Home',
            icon: "el-icon-s-custom",
            menuName: "Management"
        },
        children: [
            {
                path: "/admin/accounts",
                name: "accounts",
                component: () => import("../components/management/UserAccounts"),
                meta: {
                    title: "User Accounts",
                    icon: "el-icon-user-solid"
                }
            },
            {
                path: "/admin/records",
                name: "records",
                component: () => import("../components/management/BookingManagement"),
                meta: {
                    title: "Booking Records",
                    icon: "el-icon-s-order"
                }
            },
            {
                path: "/admin/rooms",
                name: "adminRooms",
                component: () => import("../components/management/RoomsManagement"),
                meta: {
                    title: "Room Management",
                    icon: "el-icon-s-platform"
                }
            },
        ]
    }
];

export default adminRouter
