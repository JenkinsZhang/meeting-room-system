import userRoutes from "../../router/userRouter";
import developerRoutes from "../../router/DeveloperRouter";
import adminRouter from "../../router/AdminRouter";
import * as jwtUtil from "../utils/jwt"
import {resetRouter} from "../../router/router";
export const initMenu = (router,store) =>{
    console.log(store.state.routes);
    if(store.state.routes.length > 0) {
        return;
    }
    else
    {
        let role = jwtUtil.getUserRoleName();
        if(role.toString() === "Developer")
        {
            resetRouter();
            router.addRoutes(developerRoutes);
            store.commit("initRoutes",developerRoutes);
        }
        if(role.toString() === "Administrator")
        {
            resetRouter();
            router.addRoutes(adminRouter);
            store.commit("initRoutes",adminRouter);
        }
        if(role.toString() === "User")
        {
            resetRouter();
            router.addRoutes(userRoutes);
            store.commit("initRoutes",userRoutes);
        }
    }
}
