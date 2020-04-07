<template>
<div>
	<el-tabs type="border-card">
		<el-tab-pane label="User Accounts" ><UserAccounts/></el-tab-pane>
		<el-tab-pane label="Roles"><UserRoles/></el-tab-pane>
	</el-tabs>
</div>
</template>

<script>
    import UserAccounts from "./UserAccounts";
    import UserRoles from "./UserRoles";
    export default {
        name: "AccountManagement",
        components: {UserRoles, UserAccounts}
    }
</script>

<style scoped>

</style>
