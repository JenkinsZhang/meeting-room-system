import HelloWorld from "../components/HelloWorld";
import Activation from "../components/Activation";
import VueRouter from "vue-router";

export default new VueRouter({
    mode: 'history',
    routes: [
        {
            path: '/',
            name: 'helloworld',
            component: HelloWorld
        },
        {
            path: '/activation',
            name: 'activation',
            component: Activation
        }
    ]
})


