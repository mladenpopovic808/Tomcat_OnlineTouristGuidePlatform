<template>
	<div class="add-user">
		<div class="container">
			<h1>Add a new user</h1>
			<form>
				<div class="form-group"><label>Name:</label><input v-model="user.name" type="text" class="form-control" /></div>
				<div class="form-group"><label>Lastname:</label><input v-model="user.lastname" type="text" class="form-control" /></div>
				<div class="form-group"><label>E-mail:</label><input v-model="user.email" type="text" class="form-control" /></div>
				<div class="form-group"><label>Role:</label><select v-model="user.role" class="form-control">
					<option value="admin" selected>Admin</option>
					<option value="content_creator">Content Creator</option>
				</select></div>
				<div class="form-group"><label>Password:</label><input v-model="user.password" class="form-control" type="password" /></div>
				<div class="form-group"><label>Password confirmation:</label><input v-model="user.passwordConfirmation" class="form-control" type="password" /></div>
			</form>
		</div>
		<button @click="addUser" type="button" class="btn btn-primary">Add user</button>
	</div>
</template>

<script>
import UsersView from "@/views/UsersView";

export default {
	name: "AddUserView",
	data() {
		return {
			user: {
				name: "",
				lastname: "",
				email: "",
				role: "",
				password: "",
				passwordConfirmation: ""
			}
		}
	},//
	methods: {
		addUser() {
			if (this.user.name.trim() && this.user.lastname.trim() && this.user.email.trim()
				&& this.user.role.trim() && this.user.password && this.user.passwordConfirmation) {

				if (!this.user.email.match(/^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/)) {
					alert('Unesite validnu email adresu')
					return
				}

				if (this.user.password !== this.user.passwordConfirmation) {
					alert('Sifre se ne poklapaju')
					return
				}

				//check if user with email exists
				this.$axios.get('/api/users/email/' + this.user.email)
					.then(response => {
						console.log(response.data)
						console.log(response)
						if (response.data !== "") {
							alert('Vec postoji korisnik sa takvim emailom!')
							return
						}else{

							this.$axios.post('/api/cms_users', {
								name: this.user.name,
								lastname: this.user.lastname,
								email: this.user.email,
								role: this.user.role,
								status: "Active",
								password: this.user.password
							})
									.then(response => {
										alert('Korisnik uspesno dodat')
										this.$router.push(UsersView)

									}).catch(err => {
								alert(err.response.data.message);
							})
						}
					}).catch(err => {
						alert("usao u error");
					})
			} else {
				alert('Popunite sva polja')
			}
		}
	}
}
</script>

<style scoped>

</style>