<template>
	<div>
		<div>
			<b-table hover :items="categories" :fields="fields">
				<template v-slot:cell(name)="data">
					<router-link :to="{ path: 'articlesByCategory/' + data.item.id  }"> {{ data.item.name }} </router-link>
				</template>
				<template v-slot:cell(edit)="data">
					<router-link :to="{ name: 'EditCategoryView', params: { category: {id: data.item.id, name: data.item.name, description: data.item.description }}}" tag="b-btn" class="btn-info">Edit</router-link>
				</template>
				<template v-slot:cell(delete)="data">
					<span><b-btn class="btn-danger" @click="deleteCategory(data.item.id)">Delete</b-btn></span>
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
		<router-link :to="{name: 'NewCategoryView'}" tag="b-btn" class="btn-info" :class="{active: this.$router.currentRoute.name === 'NewCategoryView'}">New Category</router-link>
	</div>
</template>

<script>
export default {
	name: "CategoryView",
	data(){
		return {
			user: JSON.parse(atob(localStorage.getItem('jwt').split('.')[1])),
			pageNum: 1,
			fields: [
				{
					label: 'Name',
					key: 'name'
				},
				'description',
				{
					label: 'Edit',
					key: 'edit'
				},
				{
					label: 'Delete',
					key: 'delete'
				}
			],
			categories: []
		}
	},
	methods: {
		deleteCategory(categoryId) {

			this.$axios
					.delete(`/api/cms_category/${categoryId}`)
					.then(() => {
							alert("Uspesno obrisana kategorija");
							this.$router.go(); //refreshuje
					})
					.catch((err) => {
						alert("Artitkli sa ovom kategorijom jos uvek postoje");
					});
		},

		nextPage() {
			this.pageNum++
			this.$axios.get(`/api/categories/page/${this.pageNum}`)
				.then(response => {
					if (response.status === 200) {
						this.categories = response.data

					}
				})
		},

		previousPage() {
			this.pageNum--
			if (this.pageNum <= 0) this.pageNum = 1
			this.$axios.get(`/api/categories/page/${this.pageNum}`)
				.then(response => {
					if (response.status === 200) {
						this.categories = response.data

					}
				})
		}

	},

	mounted() {
		this.$axios.get('/api/categories/page/1')
			.then(response => {
				this.categories = response.data
			})
	}
}
</script>

<style scoped>

</style>