<template>
	<div class="mt-5">
		<b-card class="mt-3" @click="openArticle(article.id)" :title="article.title" :sub-title="new Date(article.date).toLocaleDateString('en-GB')" v-for="article in articles" :key="article.id">
			<b-card-text>
				{{article.content | shorten}}
			</b-card-text>

		</b-card>
	</div>
</template>

<script>

import methods from "methods";

export default {
	name: 'PublicArtByCatView',
	components: {
	},

	data(){
		return {
			user: JSON.parse(atob(localStorage.getItem('jwt').split('.')[1])),
			users: [],
			articles: [],
			categories: []
		}
	},
	methods:{
		openArticle(articleId){
			this.$router.push(`/articles/${articleId}`)
		},
		addCardHoverClass(event) {
			event.target.classList.add('card-hover');
		},
		removeCardHoverClass(event) {
			event.target.classList.remove('card-hover');
		}
	},

	filters: {
		shorten(text){
			return text.substring(0, 20) + '...';
		}
	},

	mounted() {
		this.$axios.get(`/api/articles/category/${this.$route.params.id}`)
			.then(response => {
				this.articles = response.data
			})

		this.$axios.get('/api/users')
			.then(response => {
				this.users = response.data
			})
	}
}
</script>
<style scoped>

.card-hover{
	border: 3px solid blue;
}

</style>
```