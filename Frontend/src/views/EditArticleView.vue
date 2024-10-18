<template>
	<div class="add-article">
		<div class="container">
			<form>
				<div class="form-group">
					<label>Article title:</label>
					<input v-model="article.title" type="text" class="form-control" />
				</div>
				<div class="form-group">
					<label>Category:</label>
					<select v-model="article.category" class="form-control">
						<option v-for="category in categories" :key="category.id" :value="category.id">{{ category.name }}</option>
					</select>
				</div>
				<div class="form-group">
					<label>Article content:</label>
					<textarea v-model="article.content" class="form-control"></textarea>
				</div>
				<div class="form-group">
					<label>Tags:</label>
					<input v-model="article.tags" type="text" class="form-control" v-on:keyup.enter="onEnter"/>
					<small>Enter a tag and press enter.</small>
				</div>
				<p>Article tags:</p>
				<p v-if="allTags.length === 0">This article has no tags.</p>
				<b-button v-for="tag in allTags" :key="tag.id" disabled>{{tag.name}}</b-button><br><br>
				<button @click="editArticle" type="button" class="btn btn-primary">Edit Article</button>
				<button type="button" class="btn btn-primary">Clear entered tags</button>
			</form>
		</div>
	</div>
</template>

<script>
export default {
	name: "EditArticleView",
	data(){
		return {
			user: JSON.parse(atob(localStorage.getItem('jwt').split('.')[1])),
			article: {},
			allTags: [],
			categories: [],
		}
	},
	mounted() {
		this.$axios.get(`/api/articles/${this.$route.params.id}`)
			.then(response => {
				if (response.status === 200) {
					this.article = response.data
				}
			});

		this.$axios.get('/api/categories')
			.then(response => {
				if (response.status === 200) {
					console.log(response.data)
					this.categories = response.data
				}
			});

		this.$axios.get(`/api/articles/${this.$route.params.id}/tags`)
			.then(response => {
				if (response.status === 200) {
					console.log(response.data)
					this.allTags = response.data
				}
			});
	},

	methods: {
		onEnter(){
			this.allTags.push(this.article.tags)
			this.article.tags = ''
		},


		editArticle(){
			this.$axios.put('/api/articles/', {
				id: this.article.id,
				categoryId: this.article.category,
				title: this.article.title,
				content: this.article.content,
			})
				.then((response) => {
					if(response.status === 200) {
						this.allTags.forEach( tagName =>
							this.$axios.post('/api/tags', {
								name: tagName
							})
								.then(response => {
									if (response.status === 200) {
										console.log("uspesno dodat")
										console.log("tag id -> " + response.data.id)

										this.$axios.post('/api/tags/tags_articles/' + response.data.id + "/" + this.article.id, {})
										//this.addTagsArticles(response.data.id)

									}
								})
						)
					}
				})
				.catch((err) => {
					console.log(err)
				})
		}
	}
}
</script>

<style scoped>

</style>