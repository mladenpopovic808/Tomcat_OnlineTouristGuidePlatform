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
		<div class="text-center container">
			<ul class="pagination">
				<li class="page-item"><a @click="previousPage" class="page-link" aria-label="Previous"><span aria-hidden="true">Previous</span></a></li>
				<h2>-</h2>
				<h2>{{ pageNum }}</h2>
				<h2>-</h2>
				<li class="page-item"><a @click="nextPage" class="page-link" aria-label="Next"><span aria-hidden="true">Next</span></a></li>
			</ul>
		</div>
		<router-link :to="{name: 'AddArticleView'}" tag="b-btn" class="btn-info" :class="{active: this.$router.currentRoute.name === 'AddArticleView'}">New Article</router-link>

	</div>
</template>

<script>
export default {
	name: "ArticlesView",
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
			pageNum: 1,
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
		},
		nextPage() {
			this.pageNum++
			this.$axios.get(`/api/articles/page/${this.pageNum}`)
				.then(response => {
					if (response.status === 200) {
					this.articles = response.data
						console.log(response.data)
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
						console.log(response.data)
					}
				})
		}
	},
	mounted() {
		this.$axios.get('/api/articles/page/1')
			.then(response => {
				this.articles = response.data
				//sort articles by date
				this.articles.sort((a, b) => {
					return new Date(b.date) - new Date(a.date)
					//TODO testirati
				})

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