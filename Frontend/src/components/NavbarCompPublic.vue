<template>
	<div>
		<b-navbar toggleable="lg" type="dark" variant="primary">
			<b-navbar-brand>RafNewsss</b-navbar-brand>

			<b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

			<b-collapse id="nav-collapse" is-nav>
				<b-navbar-nav>
					<router-link :to="{name: 'HomeView'}" tag="a" class="nav-link">Homepage</router-link>
					<router-link :to="{name: 'MostRead'}" tag="a" class="nav-link">Most Read Monthly</router-link>

				</b-navbar-nav>

				<b-navbar-nav class="ml-auto">
					<li class="nav-item">
						<form>
							<input type="text" v-model="searchText" placeholder="Search.." name="search">
							<button type="submit" @click = "search" >Submit</button>
						</form>
					</li>
					<li class="nav-item">
						<router-link :to="{name: 'SignInView'}" tag="a" class="nav-link">Sign in</router-link>
					</li>

				</b-navbar-nav>
			</b-collapse>
		</b-navbar>
	</div>
</template>

<script>
import router from "@/router";

export default {
	name: "NavbarCompPublic",
	data() {
		return {
			searchText: "",
		}
	},
	methods: {

		search(){
			router.push('/search/' + this.searchText)

		}
	},
	mounted() {

	}

}
</script>

<style scoped>

</style>