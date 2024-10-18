<template>
	<div class="add-article">
		<div v-if="user.status === 'Active'">
			<div class="container">
				<h1>New Article</h1>
				<form>
					<div class="form-group"><label>Article title:</label><input v-model="article.title" type="text" class="form-control" /></div>
					<div class="form-group"><label>Category:</label><select v-model="article.category" class="form-control">
						<option v-for="category in categories" :key="category.id" :value="category.id">{{ category.name }}</option>
					</select></div>
					<div class="form-group"><label>Article content:</label><textarea v-model="article.content" class="form-control"></textarea></div>
					<div class="form-group"><label>Tags:</label><input v-model="article.tags" type="text" class="form-control" />
						<small>Separate tag names with a space  i.e &quot;tag-1 tag-2 tag-3&quot;</small></div>
					<button @click="addArticle" type="button" class="btn btn-primary">Add Article</button>
				</form>
			</div>
		</div>
		<div v-else>
			<h1>ACCOUNT LABELED AS INACTIVE. ACCESS DENIED!</h1>
		</div>
	</div>
</template>

<script>
export default {
	name: "AddArticleView",
	data() {
		return {
			user: JSON.parse(atob(localStorage.getItem('jwt').split('.')[1])),
			categories: [],
			article: {
				id: "",
				title: "",
				category: "",
				tags: "",
				content: ""
			},
			splitTags: []
		}
	},
	mounted() {
		this.$axios.get('/api/categories')
			.then(response => {
				if (response.status === 200) {
					console.log(response.data)
					this.categories = response.data
				}
			});
	},
	methods: {
		addArticle() {
			if (this.article.title.trim() && this.article.category
				&& this.article.tags.trim() && this.article.content.trim()) {

				this.splitTags = this.article.tags.split(" ");

				console.log(this.splitTags)

				this.$axios.post('/api/articles', {
					categoryId: this.article.category,
					title: this.article.title,
					content: this.article.content,
					authorId: this.user.id,
				})
					.then(response => {
						if (response.status === 200) {
							this.article.id = response.data.id

							this.splitTags.forEach(this.addTags)

							alert('Article added successfully')
						}
					})
			} else {
				alert('Please fill in all fields.')
			}
		},


		addTags(tagName){
			this.$axios.post('/api/tags', {
				name: tagName
			})
				.then(response => {
					if (response.status === 200) {
						console.log("uspesno dodat")
						console.log("tag id -> " + response.data.id)

						this.addTagsArticles(response.data.id)

					}
				})
		},

		addTagsArticles(tagId){
			this.$axios.post('/api/tags/tags_articles/' +tagId + "/" + this.article.id, {})
		},

		checkIfTagExists(){//todo ovo mozda da se uradi da ne bi imali duplicate tagove
		}
	}
}
</script>

<style scoped>

</style>