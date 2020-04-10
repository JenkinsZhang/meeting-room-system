<template>
  <div id="app">
      <router-view />
  </div>
  
</template>

<script>
    import router, {resetRouter} from "./router/router";
import developerRoutes from "./router/DeveloperRouter";
    import userRoutes from "./router/userRouter";
    import {initMenu} from "./assets/utils/menu";

export default {
  name: 'app',
    created() {
      initMenu(this.$router,this.$store);
    }
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  margin-top: 0;
  padding: 0;
}
.el-table .cell{
    line-height: 40px;
}

</style>
