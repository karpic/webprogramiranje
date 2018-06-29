myApp.factory('itemsFactory', function ($http) {
    var factory = {}

    var config = {
        headers: {
            'Content-Type': 'application/json'
        }
    };

    /* factory.createNewItem = function (item) {
        console.log(item);
        return $http.post('http://localhost:8080/webproject/webapi/items',
                {
                    "name": item.name,
                    "price": item.price,
                    "description": item.description,
                    "type": item.type,
                    "quantity": item.quantity,
                    "timeOrdered": new Date(),
                    "deleted": false,
                    "restaurantId": item.restaurantId.id
                },
                config
        );
    }; */

    factory.getOne = function(id) {
        return $http.get('http://localhost:8080/NonMavenWebproject/webapi/items/id/' + id);
    }

    factory.getTopTenItems = function () {
        return $http.get('http://localhost:8080/NonMavenWebproject/webapi/items/top');
    };

    factory.getAll = function () {
        return $http.get('http://localhost:8080/NonMavenWebproject/webapi/items');
    };

    factory.getItemsForRestaurantId = function (id) {
        return $http.get('http://localhost:8080/NonMavenWebproject/webapi/items/restaurant?id=' + id);
    };

    factory.search = function (name, price, type, restaurantName) {
        var url = 'http://localhost:8080/NonMavenWebproject/webapi/items/search?';

        if (name != '') {
            url = url + 'name=' + name + '&';
        }
        if (price != '') {
            url = url + 'price=' + price + '&';
        }
        if (type != '') {
            url = url + 'type=' + type + '&';
        }
        if (restaurantName != '') {
            url = url + 'restaurant=' + restaurantName + '&';
        }

        return $http.get(url);
    }

    return factory;
});

myApp.factory('postNewItemFactory', function ($http) {
    var factory = {}

    var config = {
        headers: {
            'Content-Type': 'application/json'
        }
    };

    factory.createNewItem = function (item) {
        console.log(item);
        return $http.post('http://localhost:8080/NonMavenWebproject/webapi/items',
            {
                "name": item.name,
                "price": item.price,
                "description": item.description,
                "type": item.type,
                "quantity": item.quantity,
                "timesOrdered": 0,
                "deleted": false,
                "restaurantId": item.restaurantId.id
            },
            config
        );
    };

    factory.updateItem = function(item) {
        return $http.put('http://localhost:8080/NonMavenWebproject/webapi/items/update', item, config);
    }

    factory.deleteItem = function(id) {
        return $http.delete('http://localhost:8080/NonMavenWebproject/webapi/items/delete/' + id, config);
    }

    return factory;
});

myApp.factory('usersFactory', function ($http) {
    var factory = {};

    var config = {
        headers: {
            'Content-Type': 'application/json'
        }
    };

    factory.register = function (newUser) {
        console.log(newUser);
        return $http.post('http://localhost:8080/NonMavenWebproject/webapi/users',
            {
                "username": '' + newUser.username,
                "password": '' + newUser.password,
                "firstName": '' + newUser.firstName,
                "lastName": '' + newUser.lastName,
                "phoneNumber": newUser.phoneNumber,
                "email": newUser.email,
                "registrationDate": new Date()
            },
            config
        )
    };

    factory.updateRole = function(id, role) {
        return $http.put('http://localhost:8080/NonMavenWebproject/webapi/users/changerole?id=' + id + '&role=' + role, config);
    };

    factory.getAll = function (){
        return $http.get('http://localhost:8080/NonMavenWebproject/webapi/users');
    };

    return factory;
});

myApp.factory('restaurantsFactory', function ($http) {
    var factory = {};

    var config = {
        headers: {
            'Content-Type': 'application/json'
        }
    };

    factory.createNewRestaurant = function (restaurant) {
        return $http.post(
            'http://localhost:8080/NonMavenWebproject/webapi/restaurants',
            {
                "name": restaurant.name,
                "address": restaurant.address,
                "category": restaurant.category,
                "deleted": false
            },
            config
        );
    };

    factory.getRestaurants = function () {
        return $http.get('http://localhost:8080/NonMavenWebproject/webapi/restaurants');
    };

    factory.getRestaurantsForCategory = function (category) {
        return $http.get('http://localhost:8080/NonMavenWebproject/webapi/restaurants' + '/category?category=' + category);
    };

    factory.getRestaurantForId = function (id) {
        return $http.get('http://localhost:8080/NonMavenWebproject/webapi/restaurants' + '/id/' + id);
    };

    factory.getSaved = function () {
        return $http.get('http://localhost:8080/NonMavenWebproject/webapi/restaurants/saved');
    }

    factory.search = function (name, address, category) {
        var url = 'http://localhost:8080/NonMavenWebproject/webapi/restaurants/search?';

        if (name != '') {
            url = url + 'name=' + name + '&';
        }
        if (address != '') {
            url = url + 'address=' + address + '&';
        }
        if (category != '') {
            url = url + 'category=' + category + '&';
        }

        return $http.get(url);
    };

    factory.saveRestaurant = function(id) {
        console.log(id);
        return $http.post('http://localhost:8080/NonMavenWebproject/webapi/restaurants/save?id=' + id);
    };

    factory.updateRestaurant = function(restaurant) {
        return $http.put('http://localhost:8080/NonMavenWebproject/webapi/restaurants/update', restaurant, config);
    };

    factory.deleteRestaurant = function(id) {
        return $http.delete('http://localhost:8080/NonMavenWebproject/webapi/restaurants/delete/' + id, config);
    };

    return factory;
});

myApp.factory('vehiclesFactory', function ($http) {
    var factory = {};

    var config = {
        headers: {
            'Content-Type': 'application/json'
        }
    };

    factory.createNewVehicle = function (vehicle) {
        return $http.post(
            'http://localhost:8080/NonMavenWebproject/webapi/vehicles',
            {
                "brand": vehicle.brand,
                "model": vehicle.model,
                "type": vehicle.type,
                "registrationNumber": vehicle.registrationNumber,
                "productionYear": vehicle.productionYear,
                "active": false,
                "note": vehicle.note,
                "deleted": false
            }
            ,
            config
        );
    };

    factory.getVehicleById = function (id){
        return $http.get('http://localhost:8080/NonMavenWebproject/webapi/vehicles/id/' + id);
    };

    factory.updateVehicle = function (vehicle){
        return $http.put('http://localhost:8080/NonMavenWebproject/webapi/vehicles/update', vehicle, config);
    };

    factory.getAll = function () {
        return $http.get('http://localhost:8080/NonMavenWebproject/webapi/vehicles');
    };

    factory.deleteVehicle = function (id) {
        return $http.delete('http://localhost:8080/NonMavenWebproject/webapi/vehicles/delete/' + id, config);
    };

    return factory;
});

myApp.factory('authFactory', function ($http, $localStorage, $location) {
    var factory = {};

    var config = {
        headers: {
            'Content-Type': 'application/json'
        }
    };

    factory.login = function (username, password) {
        return $http.post('http://localhost:8080/NonMavenWebproject/webapi/authentication', { username: username, password: password }).success(function (response) {
            if (response.token) {
                $localStorage.currentUser = {
                    username: username,
                    token: response.token,
                    role: response.role
                };
                $localStorage.loggedIn = true;

                $http.defaults.headers.common.Authorization = 'Bearer ' + response.token;
                console.log('Logged in');
                console.log('Current user: ' + $localStorage.currentUser.username);
                toast('You are now logged in as ' + $localStorage.currentUser.username);
                $location.path('/');
            } else {
                toast('Bad credentials!');
            }

        });
    }

    return factory;
});

myApp.factory('ordersFactory', function($http) {
    var factory = {};

    var config = {
        headers: {
            'Content-Type': 'application/json'
        }
    };

    factory.getAll = function () {
        return $http.get('http://localhost:8080/NonMavenWebproject/webapi/orders');
    };

    factory.getMyDelieveries = function () {
        return $http.get('http://localhost:8080/NonMavenWebproject/webapi/orders/mydelieveries');
    };

    factory.takeOrder = function (id) {
        return $http.post('http://localhost:8080/NonMavenWebproject/webapi/orders/take?id=' + id, config);
    };

    factory.delieverOrder = function (id) {
        return $http.post('http://localhost:8080/NonMavenWebproject/webapi/orders/delievered?id=' + id, config);
    }

    factory.getMyOrders = function() {
        return $http.get('http://localhost:8080/NonMavenWebproject/webapi/orders/myorders');
    };

    factory.createNewOrder = function(order){
        return $http.post('http://localhost:8080/NonMavenWebproject/webapi/orders', order, config);
    }

    return factory;
});