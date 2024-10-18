<template>
	<div>
		<div class="mt-5">
			<b-card class="mt-3" v-for="article in articles" :key="article.id">
				<b-card-title>
					<b-link :to="{ path: `/articles/${article.id}`}">{{article.title}}</b-link>
				</b-card-title>
				<b-card-sub-title>
					{{new Date(article.date).toLocaleDateString('en-GB')}}
				</b-card-sub-title>
				<b-card-text>
					{{article.content | shorten}} <!--da skrati opis artikla-->
				</b-card-text>
			</b-card>
		</div>

		<div class="text-center container">
			<ul class="pagination">
				<li class="page-item"><a @click="previousPage" class="page-link" aria-label="Previous"><span aria-hidden="true">Previous</span></a></li>
				<h2>-</h2>
				<h2>{{ pageNum }}</h2>
				<h2>-</h2>
				<li class="page-item"><a @click="nextPage" class="page-link" aria-label="Next"><span aria-hidden="true">Next</span></a></li>
			</ul>
		</div>
	</div>
</template>

<script>

export default {
	name: 'HomeView',
	components: {
	},

	data(){
		return {
			pageNum: 1,
			articles: [],
			categories: []
		}
	},

	methods: {
		nextPage() {
			this.pageNum++
			this.$axios.get(`/api/articles/page/${this.pageNum}`)
				.then(response => {
					if (response.status === 200) {
						this.articles = response.data
					}
				})
		},
		previousPage() {
			this.pageNum--
			if (this.pageNum <= 0) this.pageNum = 1
			this.$axios.get(`/api/articles/page/${this.pageNum}`)
				.then(response => {
					if (response.status === 200) {
						this.articles = response.data
					}
				})
		}
	},

	filters: {
		shorten(text){
			return text.substring(0, 50) + '...';
		}
	},

	mounted() {
		this.$axios.get('/api/articles/page/1')
			.then(response => {
				this.articles = response.data
			})
		this.$axios.get('/api/categories')
			.then(response => {
				this.categories = response.data
			})
	}
}
</script>