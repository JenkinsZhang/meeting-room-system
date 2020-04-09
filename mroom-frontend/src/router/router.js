import VueRouter from "vue-router";
import Vue from 'vue'
import {initMenu} from "../assets/utils/menu";
import store from "../store";
Vue.use(VueRouter)
const createRouter = ()=> new VueRouter({
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
                path: '/registry',
                name: 'registry',
                component: ()=>import("../views/Registry"),
                meta: {
                    title: 'Registry'
                }
            }

        ],
    });
const router = createRouter();

export function resetRouter() {
    const newRouter = createRouter();
    router.matcher = newRouter.matcher;
}


export default router



