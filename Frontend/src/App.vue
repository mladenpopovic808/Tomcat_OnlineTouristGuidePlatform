<template>

	<div v-if="this.user == null">
		<NavBarPublic/>

		<div class="container">
			<router-view/> <!--prikaz stranice koja je trenutno u ruteru-->
		</div>
	</div>

  <div v-else>
    <Navbar/>

    <div class="container">
      <router-view/> <!--prikaz stranice koja je trenutno u ruteru-->
    </div>

  </div>
</template>

<script>
import Navbar from "@/components/NavbarComp";
import NavBarPublic from "@/components/NavbarCompPublic";

export default {
	data() {
		return {
			user: null
		}
	},
	mounted() {
		if (localStorage.getItem('jwt') != null){
			this.user = JSON.parse(atob(localStorage.getItem('jwt').split('.')[1]))
		}
	},
	components: {
    Navbar,
	NavBarPublic
  },
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

nav {
  padding: 30px;
}

nav a {
  font-weight: bold;
  color: #2c3e50;
}

nav a.router-link-exact-active {
  color: #42b983;
}
</style>
