import Vue from 'vue'
import App from './App.vue'
import router from "./router/router";
import VueRouter from "vue-router";
import { BootstrapVue, IconsPlugin } from 'bootstrap-vue'
import axios from 'axios'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import VueAxios from "vue-axios";
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import * as jwtUtil from './assets/utils/jwt'
Vue.prototype.$jwtUtil = jwtUtil;
import {from} from "bootstrap-vue/esm/utils/array";
Vue.config.productionTip = false;
Vue.use(VueRouter);
Vue.use(VueAxios,axios);
Vue.use(BootstrapVue);
Vue.use(IconsPlugin);
Vue.use(ElementUI);
// router.beforeEach((to,from,next)=>{
//   if (to.meta.title){
//     document.title = to.meta.title
//   }
//   next()
// });
axios.interceptors.request.use(function (config) {
  console.log(config);
  let token = localStorage.getItem("access-token");
  if(token)
  {
    config.headers["access-token"] = token
  }
  // router.push("/login");
  return config;
},function (error) {
  console.log(error)
});


axios.defaults.withCredentials = true;
new Vue({
  router,
  render: h => h(App),
}).$mount('#app');
