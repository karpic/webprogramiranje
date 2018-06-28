var myApp = angular.module('myApp', ['ngRoute', 'ngStorage']);

myApp.config(function($routeProvider, $locationProvider) {

	$routeProvider.when('/',{
		templateUrl: 'partials/startingPage.html'
	}).when('/register',{
		templateUrl: 'partials/register.html'
	}).when('/login', {
		templateUrl: 'partials/login.html'
	}).when('/newitem', {
		templateUrl: 'partials/newItem.html'
	}).when('/allitems', {
		templateUrl: 'partials/allItems.html'
	}).when('/newrestaurant', {
		templateUrl: 'partials/newRestaurant.html'
	}).when('/allrestaurants', {
		templateUrl: 'partials/allRestaurants.html'
	}).when('/newvehicle', {
		templateUrl: 'partials/newVehicle.html'
	}).when('/allvehicles', {
		templateUrl: 'partials/allVehicles.html'
	}).when('/userprofile', {
		templateUrl: 'partials/userPage.html'
	}).when('/itemsearch', {
		templateUrl: 'partials/itemSearch.html'
	}).when('/restaurantsearch', {
		templateUrl: 'partials/restaurantSearch.html'
	}).when('/updateitem/:id', {
		templateUrl: 'partials/itemUpdate.html'
	}).when('/updaterestaurant/:id', {
		templateUrl: 'partials/restaurantUpdate.html'
	}).when('/updatevehicle/:id', {
		templateUrl: 'partials/vehicleUpdate.html'
	}).when('/order/:id', {
		templateUrl: 'partials/newOrder.html',
	}).when('/delievererpanel', {
		templateUrl: 'partials/delievererPanel.html'
	})
});

myApp.config(function($logProvider){
    $logProvider.debugEnabled(true);
});