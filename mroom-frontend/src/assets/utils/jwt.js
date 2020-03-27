import axios from "axios";

const jwt = require('jsonwebtoken');

export function getTokenEmail() {
    let token = localStorage.getItem("access-token");
    if(token == null )
        return null;
    return jwt.decode(token).email;
}

export function getTokenUsername() {
    let token = localStorage.getItem("access-token");
    if(token == null)
        return null;
    return jwt.decode(token).username;
}

export function getUserRoles(){
    let token = localStorage.getItem("access-token");
    if(token == null)
        return null;
}

export function getExpireTime(){
    let token = localStorage.getItem("access-token");
    if(token == null)
        return null;
    return jwt.decode(token).exp
}

export function refreshToken() {
    return axios({
        url: 'api/auth/refresh',
        method: "GET"
    })
}
