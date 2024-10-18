<template>
	<div>
		<div>
			<b-table hover :items="articles" :fields="computedFields">
				<template v-slot:cell(edit)="data">
					<router-link :to="{ path: `/editArticle/${data.item.id}`}" tag="b-btn" class="btn-info">Edit</router-link>
				</template>
				<template v-slot:cell(delete)="data">
					<span><b-btn class="btn-danger" @click="deleteArticle(data.item.id)">Delete</b-btn></span>
				</template>
				<template v-slot:cell(title)="data">
					<router-link :to="{ path: `/articles/${data.item.id}`}" target="_blank">{{ data.item.title }}</router-link>
				</template>
				<template v-slot:cell(date)="data">
					{{new Date(data.item.date).toLocaleDateString('en-GB')}}
				</template>
				<template v-slot:cell(author)>
					{{`${users.filter(u => u.id === user.id)[0].name} ${users.filter(u => u.id === user.id)[0].lastname}`}}
				</template>
			</b-table>
		</div>
		<router-link :to="{name: 'AddArticleView'}" tag="b-btn" class="btn-info" :class="{active: this.$router.currentRoute.name === 'AddArticleView'}">New Article</router-link>
	</div>
</template>

<script>
export default {
	name: "ArticlesSearchedView",
	computed: {
		computedFields() {
			if (this.user.role !== 'admin') {
				return this.fields.filter(field => !field.adminOnly)
			} else {
				return this.fields
			}
		}
	},
	data() {
		return {
			user: JSON.parse(atob(localStorage.getItem('jwt').split('.')[1])),
			users: [],
			articles: [],
			fields: [
				{
					label: 'Title',
					key: 'title'
				},
				'content',
				{
					label: 'Edit',
					key: 'edit',
					adminOnly: true
				},
				{
					label: 'Delete',
					key: 'delete',
					adminOnly: true
				},
				{
					label: 'Author',
					key: 'author'
				},
				{
					label: 'Date',
					key: 'date'
				}
			]
		}
	},
	methods: {
		deleteArticle(articleId){
			this.$axios.delete(`/api/cms_articles/${articleId}`,{
				headers: {
					Authorization: "Bearer " + localStorage.getItem("jwt")
				}
			})
				.then(() => {
					this.articles= this.articles.filter(article => article.id !== articleId)
				})
				.catch((err)=> {
					console.log(err);
				})
		}
	},

	mounted() {
		this.$axios.get(`/api/cms_articles/search/${this.$route.params.search}`)
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

</style>