<template>
	<div class="pt-5">
		<form @submit.prevent="login">
			<div class="form-group">
				<label for="username">Email</label>
				<input v-model="email" type="text" class="form-control" id="username" aria-describedby="usernameHelp" placeholder="Enter email ">
				<small id="usernameHelp" class="form-text text-muted">We'll never share your email with anyone else.</small>
			</div>
			<div class="form-group">
				<label for="exampleInputPassword1">Password</label>
				<input v-model="password" type="password" class="form-control" id="exampleInputPassword1" placeholder="Password">
			</div>
			<button type="submit" class="btn btn-primary mt-2">Submit</button>
		</form>
	</div>
</template>

<script>
export default {
	name: "LoginView",
	data() {
		return {
			email: '',
			password: '',
		}
	},
	methods: {
		login() {
			this.$axios.post('/api/users/login', {
				email: this.email,
				password: this.password,
			}).then(response => {
				if (response.status === 200) {
					let user = this.parseJwt(response.data.jwt)

					if(user.status !== "Active" && user.status !== "active"){
						alert("Niste aktivan korisnik,ne mozete se logovati!.")
						return;
					}
					localStorage.setItem('jwt', response.data.jwt);
					localStorage.setItem('user', user)

					console.log(user)
					this.$router.push('/categories')
					window.location.reload()
				}

			})
				.catch(err => {
					alert("Netacni ime ili lozinka.")
					console.log(err)
				})

		},
		parseJwt (token) {
			var base64Url = token.split('.')[1];
			var base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
			var jsonPayload = decodeURIComponent(window.atob(base64).split('').map(function(c) {
				return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
			}).join(''));

			return JSON.parse(jsonPayload);
		}
	},
}
</script>

<style scoped>

</style>