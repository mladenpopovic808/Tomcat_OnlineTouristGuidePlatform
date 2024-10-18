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
	name: "EditCategoryView",//todo pogledaj da li radi pravilno
	props: ["category"],
	data() {
		return {
			form: {
				id: this.category.id,
				name: this.category.name,
				description: this.category.description
			},

		}
	},
	methods: {
		onSubmit(event) {
			event.preventDefault()

			this.$axios.put('/api/cms_category', this.form)
				.then((response) => {
					if(response.status === 200) {
						alert("Category successfully edited")
						this.$router.push('/categories')
					}
				})
				.catch((err) => {
					console.log(err)
				})
		},
	},
}
</script>

<style scoped>

</style>