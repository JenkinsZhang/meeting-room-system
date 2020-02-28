import Vue from 'vue'
import App from './App.vue'
import router from "./router/router";
import VueRouter from "vue-router";
import { BootstrapVue, IconsPlugin } from 'bootstrap-vue'
import axios from 'axios'
import VueAxios from "vue-axios";
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import {from} from "bootstrap-vue/esm/utils/array";
Vue.config.productionTip = false;
Vue.use(VueRouter);
Vue.use(VueAxios,axios);
Vue.use(BootstrapVue);
Vue.use(IconsPlugin);
// router.beforeEach((to,from,next)=>{
//   if (to.meta.title){
//     document.title = to.meta.title
//   }
//   next()
// });
new Vue({
  router,
  render: h => h(App),
}).$mount('#app');
