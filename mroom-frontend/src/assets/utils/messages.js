
export function errorMessage(vm){
    return vm.$message({
        message: "Server Error!",
        type: "danger"
    })
}

export function successMessage(vm,msg) {
    return vm.$message({
        message: msg,
        type: "success"
    })
}

export function warningMessage(vm,msg){
    return vm.$message({
        message: msg,
        type: "warning"
    })
}

