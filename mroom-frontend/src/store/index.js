import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex);

const now = new Date();

const store = new Vuex.Store({
    state: {
        routes: [],
    },
    mutations: {
        initRoutes(state, data) {
            state.routes = data;
        },

    }
});



export default store;
