<template>
	<div>
		<div>
			<b-table hover :items="users" :fields="fields">
				<template v-slot:cell(edit)="data">
					<router-link :to="{ path: 'cms_editUser/' + data.item.id }" tag="b-btn" class="btn-info">Edit</router-link>
				</template>
				<template v-slot:cell(status)="data">
					<span><b-btn v-if="data.item.role !== 'admin' " class="b-btn" @click="changeUserStatus(data,data.item.id, data.item.status)"> {{ data.item.status }} </b-btn></span>
				</template>
				<template v-slot:cell(delete)="data">
					<span><b-btn class="btn-danger" @click="deleteUser(data.item.id)">Delete</b-btn></span>
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
		<router-link :to="{name: 'AddUserView'}" tag="b-btn" class="btn-info" :class="{active: this.$router.currentRoute.name === 'AddUserView'}">New User</router-link>
	</div>
</template>

<script>


export default {
	name: "UsersView",
	data(){
		return {
			user: JSON.parse(atob(localStorage.getItem('jwt').split('.')[1])),
			pageNum: 1,
			fields: [
				'name',
				'lastname',
				'email',
				'role',
				'status',
				{
					label: 'Edit',
					key: 'edit'
				},
				{
					label: 'Delete',
					key: 'delete'
				},
				{
					label: 'Status',
					key: 'status'
				}
			],
			users: []
		}
	},
	methods: {

		deleteUser(userId){
			this.$axios.delete(`/api/cms_users/delete/${userId}`)
				.then(() => {
					this.users = this.users.filter(user => user.id !== userId)
				})
				.catch((err)=> {
					console.log(err);
				})
		},

		changeUserStatus(data,userId, status){

			if (status === "active"){
				this.$axios.put(`/api/cms_users/deactivate/${userId}`)
					.then(() => {
						data.item.status = "notActive"
					})
					.catch((err)=> {
						console.log(err);
					})

			}
			else {

				this.$axios.put(`/api/cms_users/activate/${userId}`)
					.then(() => {
						data.item.status = "active"
					})
					.catch((err)=> {
						console.log(err);
					})
			}
		},
		nextPage() {
			this.pageNum++
			this.$axios.get(`/api/users/page/${this.pageNum}`)
				.then(response => {
					if (response.status === 200) {
						this.users = response.data
						console.log(response.data)
					}
				})
		},

		previousPage() {
			this.pageNum--
			if (this.pageNum <= 0) this.pageNum = 1
			this.$axios.get(`/api/users/page/${this.pageNum}`)
				.then(response => {
					if (response.status === 200) {
						this.users = response.data
						console.log(response.data)
					}
				})
		}
	},
	mounted() {
		this.$axios.get('/api/users/page/1')
			.then(response => {
				this.users = response.data
			})
		if(this.user==null){
			this.$router.push({name: 'HomeView'})
			alert('Niste ulogovani')
		}
		if(this.user.role !== 'admin'){
			this.$router.push({name: 'HomeView'})
			alert('Niste admin,ne mozete uci na stranicu korisnika')
		}
	}
}
</script>

<style scoped>

</style>