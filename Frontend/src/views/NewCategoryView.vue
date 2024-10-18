<template>
	<div>
		<b-form @submit="onSubmit">

			<b-form-group id="catName" label="Category name:" label-for="name">
				<b-form-input
					v-model="form.name"
					placeholder="Enter name"
					required
				></b-form-input>
			</b-form-group>

			<b-form-group id="catDesc" label="Category description:" label-for="description">
				<b-form-input
					v-model="form.description"
					placeholder="Enter description"
					required
				></b-form-input>
			</b-form-group>

			<b-button type="submit" variant="primary">Submit</b-button>
		</b-form>
	</div>
</template>

<script>
export default {
	name: "NewCategoryView",
	data() {
		return {
			form: {
				name: '',
				description: ''
			}
		}
	},
	methods: {
		onSubmit(event) {
			event.preventDefault()

			this.$axios.post('/api/cms_category', this.form)
				.then((response) => {
					if(response.status === 200) {
						alert("Category successfully added")
						this.$router.push('/categories')
					}
				})
				.catch((err) => {
					alert("Vec postoji kategorija sa ovakvim imenom")
					this.form.name = ''
				})
		},
	}
}
</script>

<style scoped>

</style>