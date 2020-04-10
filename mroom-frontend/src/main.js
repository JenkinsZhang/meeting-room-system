import Vue from 'vue'
import App from './App.vue'
import router from "./router/router";
import {resetRouter} from "./router/router"
import adminRouter from "./router/AdminRouter";
import developerRoutes from "./router/DeveloperRouter";
import userRoutes from "./router/userRouter";
import store from "./store";
import axios from 'axios'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import VueAxios from "vue-axios";
import * as jwtUtil from './assets/utils/jwt'
import locale from 'element-ui/lib/locale/lang/en'
import * as messageUtil from './assets/utils/messages'
import {initMenu} from "./assets/utils/menu";


Vue.prototype.$jwtUtil = jwtUtil;
Vue.prototype.$messageUtil = messageUtil;
Vue.config.productionTip = false;
Vue.use(VueAxios,axios);
Vue.use(ElementUI,{locale});

router.beforeEach((to,from,next)=>{
  if(to.fullPath === "/login" || to.fullPath ==="/registry" || to.fullPath ==="/activation")
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
        alert("user authentication expired");
        localStorage.removeItem("access-token");
        next("/login")
      } else {
        next();
      }
    }
  }



});

axios.interceptors.request.use(function (config) {
  // console.log(config);
  let token = localStorage.getItem("access-token");
  if(token)
  {
    config.headers["access-token"] = token
  }
  return config;
},function (error) {
  console.log(error)
});

axios.defaults.withCredentials = true;
new Vue({
  router,
  store,
  render: h => h(App),
}).$mount('#app');

