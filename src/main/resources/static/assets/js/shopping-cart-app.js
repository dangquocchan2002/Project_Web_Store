const app = angular.module("shopping-cart-app", []);

app.controller("shopping-cart-ctrl", function($scope, $http){
	
	$scope.cart = {
		items: [],
		//them sp vao gioi hang
		add(id){
			var item = this.items.find(item => item.id == id);
			if(item){
				item.qty++;
				this.saveToLocalStorage();
			}
			else{
				$http.get(`/rest/products/${id}`).then(resp => {
					resp.data.qty =1;
					this.items.push(resp.data);
					this.saveToLocalStorage();
				})
			}
		},
		//xoa sp khoi gio hang
		remove(id){
			var index = this.items.findIndex(item => item.id == id);
			this.items.splice(index, 1);
			this.saveToLocalStorage();
		},
		//xoa sach het sp khoi gio hang
		clear(){
			this.items =[];
			this.saveToLocalStorage();
		},
		amt_of(item){},
		//Tinh tong so luong mat hang trong gio
		get count(){
			return this.items
				.map(item => item.qty)
				.reduce((total, qty) => total += qty, 0);
		},
		//thanh tien
		get amount(){
			return this.items
				.map(item => item.qty * item.price)
				.reduce((total, qty) => total += qty, 0);
		},
		//Luu gio hang vao local storage
		saveToLocalStorage(){
			var json = JSON.stringify(angular.copy(this.items));
			localStorage.setItem("cart", json);
		},
		//doc gio hang tu local
		loadFromLocalStorage(){
			var json = localStorage.getItem("cart");
			this.items = json ? JSON.parse(json) : [];
		}
		
	}
	$scope.cart.loadFromLocalStorage();
	
	$scope.order ={
		createDate: new Date(),
		address: "",
		account: {username: $("#username").text()},
		get orderDetails(){
			return $scope.cart.items.map(item => {
				return{
					product:{id: item.id},
					price: item.price,
					quantity: item.qty
				}
			})
		},
		purchase(){
			var order = angular.copy(this);
			//thuc hien dat hang
			$http.post("/rest/orders", order).then(resp => {
				alert("Dat hang thanh cong!");
				$scope.cart.clear();
				location.href = "/order/detail/" + resp.data.id;
			}).catch(error => {
				alert("Dat hang loi!");
				console.log(error)
			})
		}
	}
})