import HelloWorld from "../components/HelloWorld";
import Activation from "../views/Activation";
import VueRouter from "vue-router";
import Registry from "../views/Registry";
import Test from "../views/Test";
import Login from "../views/Login";
import Home from "../views/Home";
export default new VueRouter({
    mode: 'history',
    routes: [
        {
            path: '/',
            name: 'helloworld',
            component: HelloWorld,
            meta: {
                title: 'Helloworld'
            }
        },
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
            }
        }


    ]
})


