<template>
  <div>
    <b-navbar toggleable="lg" type="dark" variant="primary">
      <b-navbar-brand href="#">Raf News</b-navbar-brand>

      <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

      <b-collapse id="nav-collapse" is-nav>
				<b-navbar-nav>
					<router-link :to="{name: 'MostRead'}" tag="a" class="nav-link" :class="{active: this.$router.currentRoute.name === 'MostRead'}">Most Read Monthly</router-link>
					<router-link :to="{name: 'ArticlesView'}" tag="a" class="nav-link" :class="{active: this.$router.currentRoute.name === 'ArticlesView'}">Articles</router-link>
					<router-link :to="{name: 'CategoryView'}" tag="a" class="nav-link" :class="{active: this.$router.currentRoute.name === 'CategoryView'}">Categories</router-link>
					<router-link v-if="user.role === 'admin'" :to="{name: 'UsersView'}" tag="a" class="nav-link" :class="{active: this.$router.currentRoute.name === 'UsersView'}">Users</router-link>
					<b-nav-item-dropdown text="Categories" right>																																			<!--:to="`/publicArtByCategory/${category.id}-->
						<b-dropdown-item v-for="category in categories" :key="category.id" @click="reload(category.id)" :value="category.id" :to="`/publicArtByCategory/${category.id}`">{{ category.name }}</b-dropdown-item>
					</b-nav-item-dropdown>
        </b-navbar-nav>

        <b-navbar-nav class="ml-auto">
			<li class="nav-item">
				<form>
					<input type="text" v-model="searchText" placeholder="Search.." name="search">
					<button type="submit" @click = "search" >Submit</button>
				</form>
			</li>
			<li class="nav-item" v-if="this.user != null">
				<b-nav-text>Welcome, {{user.name}}</b-nav-text>
			</li>
		<b-nav-item @click="logout">Sign out</b-nav-item>

		</b-navbar-nav>

      </b-collapse>
    </b-navbar>
  </div>
</template>

<script>
import router from "@/router";
//NAVBAR AKO korisnik nije ulogovan
export default {
  name: "navBar",
	data() {
		return {
			user: null,//JSON.parse(atob(localStorage.getItem('jwt').split('.')[1])),
			categories: [],
			searchText: "",
		}
	},
	methods: {
		logout(){
			localStorage.removeItem('jwt')
			localStorage.removeItem('user')
			this.user = null
			this.$router.push('/')
			window.location.reload()
		},
		reload(categoryId){
			//ovo ne radi kada sam vec u toj stranici
			//this.$router.push(`/publicArtByCategory/${categoryId}`)
			window.location.reload()
		},
		search(){
			router.push('/search/' + this.searchText)

		}
	},

	mounted() {
		console.log("mounted")

		this.$axios.get('/api/categories')
			.then(response => {
				console.log(response.data)
				this.categories = response.data
				console.log(this.categories[1])
			}).catch(error => {
				console.log(error)
			})

		if (localStorage.getItem('jwt') != null){
			this.user = JSON.parse(atob(localStorage.getItem('jwt').split('.')[1]))
		}
	}


}
</script>

<style scoped>

</style>