<template>
	<div class="edit-user">
		<div class="container">
			<h1>Edit user</h1>
			<form>
				<div class="form-group"><label>Name:</label><input v-model="user.name" type="text" class="form-control" /></div>
				<div class="form-group"><label>Lastname:</label><input v-model="user.lastname" type="text" class="form-control" /></div>
				<div class="form-group"><label>E-mail:</label><input v-model="user.email" type="text" class="form-control" /></div>
				<div class="form-group"><label>Role:</label><select v-model="user.role" class="form-control">
					<option value="admin" selected>Admin</option>
					<option value="content_creator">Content Creator</option>
				</select></div>
			</form>
		</div>
		<button @click="editUser" type="button" class="btn btn-primary">Edit user</button>
	</div>
</template>

<script>
export default {
	name: "EditUserView",
	data() {
		return {
			user: {}
		}
	},
	mounted() {
		this.$axios.get(`/api/users/${this.$route.params.id}`)
			.then(response => {
				if (response.status === 200) {
					this.user = response.data
				}
			})//
	},
	methods: {
		editUser() {
			if (this.user.name.trim() && this.user.lastname.trim() && this.user.email.trim() && this.user.role.trim()) {

				if (!this.user.email.match(/^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/)) {
					alert('Please enter a valid email address')
					return
				}

				this.$axios.put("/api/cms_users",{
					id: `${this.$route.params.id}`,
					name: this.user.name,
					lastname: this.user.lastname,
					email: this.user.email,
					role: this.user.role,
					status: this.user.status,
					password : this.user.password
				})
					.then(response => {
						if (response.status === 200) {
							alert('User edited successfully')
							// this.$router.push('/cms')
						} else {
							alert(response.data.message)
						}
					})
					.catch(() => {
						alert('Email address already taken');
					})

			} else {
				alert('Please fill in all fields')
			}
		}
	}
}
</script>

<style scoped>

</style>