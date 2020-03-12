var jwt = require('jsonwebtoken');



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


