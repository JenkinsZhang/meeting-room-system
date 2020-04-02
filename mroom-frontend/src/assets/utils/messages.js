
export function errorMessage(vm){
    return vm.$message({
        message: "Server Error!",
        type: "danger"
    })
}

