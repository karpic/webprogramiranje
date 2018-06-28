myApp.controller('topTenItemsController', function ($scope, itemsFactory, restaurantsFactory, $location) {
    function init() {
        console.log('TopTenItemsController.Init');
        itemsFactory.getTopTenItems().success(function (data) {
            $scope.topTenItems = data;
        });
    }

    $scope.getRestaurantsForCategory = function (category) {
        console.log('TopTenItemsController.RestaurantsForCategory');
        restaurantsFactory.getRestaurantsForCategory(category).success(function (data) {
            $scope.restaurants = data;
        });
    }

    $scope.save = function (id) {
        restaurantsFactory.saveRestaurant(id).success(function (data) {
            toast('Successfully saved!');
        })
    };

    $scope.goOrder = function (id) {
        $location.path('/order/' + id);
    };

    init();
});

myApp.controller('newItemController', function ($scope, postNewItemFactory, restaurantsFactory, $location) {
    function init() {
        console.log('newItemController.itit')
        restaurantsFactory.getRestaurants().success(function (data) {
            console.log(data);
            $scope.restaurants = data;
        });
    }

    $scope.submitNewItem = function (item) {
        console.log(item);
        postNewItemFactory.createNewItem(item).success(function (data) {
            toast('Successfully created new item!');
            console.log('Data created!');
            console.log(data);
            $location.path('/');
        });
    };

    init();

});

myApp.controller('allItemsController', function ($scope, itemsFactory, $location, postNewItemFactory) {
    function init() {
        itemsFactory.getAll().success(function (data) {
            $scope.allItems = data;
        });
    }

    $scope.go = function (id) {
        $location.path('/updateitem/' + id);
    }

    $scope.delete = function (id) {
        postNewItemFactory.deleteItem(id).success(function (data) {
            toast('Successfully deleted item!');
            init();
        });
    };
    init();
});

myApp.controller('newRestaurantController', function ($scope, restaurantsFactory, $location) {

    $scope.submitNewRestaurant = function (restaurant) {
        restaurantsFactory.createNewRestaurant(restaurant).success(function (data) {
            toast('Successfully created new restaurant');
            $location.path('/');
        });
    }
});

myApp.controller('allRestaurantsController', function ($scope, restaurantsFactory, $location) {

    function init() {
        restaurantsFactory.getRestaurants().success(function (data) {
            $scope.restaurants = data;
        });
    };

    $scope.go = function (id) {
        $location.path('/updaterestaurant/' + id);
    };

    $scope.delete = function (id) {
        restaurantsFactory.deleteRestaurant(id).success(function (data) {
            toast('Successfully deleted restaurant!');
            init();
        });
    };

    init();
});

myApp.controller('newVehicleController', function ($scope, vehiclesFactory, $location) {

    $scope.submitNewVehicle = function (vehicle) {
        vehiclesFactory.createNewVehicle(vehicle).success(function (data) {
            toast('Successfully created new vehicle!');
            $location.path('/');
        });
    }
});

myApp.controller('allVehiclesController', function ($scope, vehiclesFactory, $location) {
    function init() {
        vehiclesFactory.getAll().success(function (data) {
            $scope.vehicles = data;
        });
    };

    $scope.go = function (id) {
        $location.path('/updatevehicle/' + id);
    };

    $scope.delete = function (id) {
        vehiclesFactory.deleteVehicle(id).success(function (data) {
            toast('Successfully deleted vehicle!');
            init();
        });
    };

    init();
});

myApp.controller('userPageController', function ($scope, restaurantsFactory, ordersFactory) {
    function init() {
        restaurantsFactory.getSaved().success(function (data) {
            $scope.savedRestaurants = data;
        });

        ordersFactory.getMyOrders().success(function (data) {
            $scope.myOrders = data;
        });
    }

    init();
});

myApp.controller('usersController', function ($scope, usersFactory, $location) {

    $scope.register = function (user) {
        usersFactory.register(user).success(function (data) {
            toast('Thank you for registering ' + data.firstName + '!');
            $location.path('/');
            console.log(data);
        })
    }
});


myApp.controller('authController', function ($scope, $rootScope, authFactory) {

    $scope.login = function (username, password) {
        authFactory.login(username, password);
        $rootScope.$broadcast('userLoggedIn');
    }
});

myApp.controller('headerController', function ($scope, $rootScope, $localStorage, $http) {
    $scope.loggedIn = false;
    $scope.loggedInUserRole = '';
    $scope.isAdmin = false;
    $scope.isDelieverer = false;

    function init() {
        $rootScope.$on('userLoggedIn', function () {
            console.log('TEST HEADER $ON');
            $scope.loggedIn = true;
            $scope.loggedInUserRole = $localStorage.currentUser.role;

            if ($scope.loggedInUserRole == 'ADMIN') {
                $scope.isAdmin = true;
                $scope.isDelieverer = false;
            }
            if ($scope.loggedInUserRole == 'DELIEVERER') {
                $scope.isDelieverer = true;
                $scope.isAdmin = false;
            }
        });
    };

    $scope.logOut = function () {
        $scope.loggedIn = false;
        $scope.isAdmin = false;
        $scope.isDelieverer = false;
        $scope.loggedInUserRole = '';
        $localStorage.currentUser = {};
        $http.defaults.headers.common.Authorization = '';
    }

    init();
});

myApp.controller('itemSearchController', function ($scope, itemsFactory) {
    function init() {
        $scope.searchParams = { name: '', price: '', type: '', restaurantName: '' };
    }

    $scope.search = function () {
        itemsFactory.search($scope.searchParams.name, $scope.searchParams.price, $scope.searchParams.type, $scope.searchParams.restaurantName).success(function (data) {
            $scope.searchedItems = data;
        });
    }

    init();
});

myApp.controller('restaurantSearchController', function ($scope, restaurantsFactory, $location) {
    function init() {
        $scope.searchParams = { name: '', address: '', category: '' };
    };

    $scope.search = function () {
        restaurantsFactory.search($scope.searchParams.name, $scope.searchParams.address, $scope.searchParams.category).success(function (data) {
            $scope.searchedRestaurants = data;
        });
    };

    $scope.save = function (id) {
        restaurantsFactory.saveRestaurant(id).success(function (data) {
            toast('Successfully saved!');
        });
    };

    $scope.goOrder = function (id) {
        $location.path('/order/' + id);
    };

    init();
});


myApp.controller('itemUpdateController', function ($scope, itemsFactory, $routeParams, postNewItemFactory, $location, restaurantsFactory) {
    function init() {
        itemsFactory.getOne($routeParams.id).success(function (data) {
            $scope.item = data;
        });

        restaurantsFactory.getRestaurants().success(function (data) {
            $scope.restaurants = data;
        });
    }
    $scope.updateItem = function () {
        postNewItemFactory.updateItem($scope.item).success(function (data) {
            toast('Successfully updated item!');
            $location.path('/allitems');
        })
    }

    init();
});

myApp.controller('restaurantUpdateController', function ($scope, $routeParams, restaurantsFactory, $location) {
    function init() {
        restaurantsFactory.getRestaurantForId($routeParams.id).success(function (data) {
            $scope.restaurant = data;
        });
    };

    $scope.updateRestaurant = function () {
        restaurantsFactory.updateRestaurant($scope.restaurant).success(function (data) {
            toast('Successfully updated restaurant!');
            $location.path('/allrestaurants');
        });
    }

    init();
});

myApp.controller('vehicleUpdateController', function ($scope, $routeParams, $location, vehiclesFactory) {
    function init() {
        vehiclesFactory.getVehicleById($routeParams.id).success(function (data) {
            $scope.vehicle = data;
        });
    };

    $scope.updateVehicle = function () {
        vehiclesFactory.updateVehicle($scope.vehicle).success(function (data) {
            toast('Successfully updated vehicle!');
            $location.path('/allvehicles');
        });
    };

    init();
});

myApp.controller('newOrderController', function ($scope, $routeParams, ordersFactory, restaurantsFactory, itemsFactory, $location, $localStorage) {
    function init() {
        console.log('Logged in: ' + $localStorage.loggedIn);
        if ($localStorage.loggedIn == true) {

            console.log('newOrderController.init');
            $scope.quantity = 0;

            $scope.newOrder = {
                "orderItems": [],
                "dateOrdered": new Date(),
                "userUsername": '',
                "delievererUsername": '',
                "status": 'ORDERED',
                "note": '',
                "deleted": false
            };

            restaurantsFactory.getRestaurantForId($routeParams.id).success(function (data) {
                $scope.restaurant = data;

                itemsFactory.getItemsForRestaurantId($routeParams.id).success(function (data) {
                    $scope.items = data;
                });
            });
        }else{
            $location.path('/login');
        }
    };

    $scope.submitOrder = function () {
        ordersFactory.createNewOrder($scope.newOrder).success(function (data) {
            toast('Successfully created new order!');
            $location.path('/');
        });
    };

    $scope.addItemToOrder = function (item, temporaryQuantity) {
        var newOrderItem = {
            "itemId": item.id,
            "itemName": item.name,
            "quantity": temporaryQuantity
        };

        console.log('Order item: ');
        console.log(newOrderItem);
        $scope.newOrder.orderItems.push(newOrderItem);
    }


    init();
});