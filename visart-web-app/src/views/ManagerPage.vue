<template>
  <div>
    <div class="pricing-header px-3 py-3 pt-md-5 pb-md-4 mx-auto text-center">
      <h1 class="display-4">{{ actionTitle }}</h1>
      <p class="lead">{{ actionDescription }}</p>
      <div class="row">
        <div class="col-xl">
          <b-button v-on:click="showPinned">Show Managers Pinned Art</b-button>
        </div>
        <div class="col-xl">
          <b-button v-on:click="showSold"
            >Show All Sold Art In Gallery</b-button
          >
        </div>
      </div>
      <div class="album py-5 bg-light">
        <div class="container">
          <div class="row">
            <div
              class="col-md-4"
              v-for="(artlisting, index) in artListings"
              :key="index"
            >
              <div class="card mb-4 box-shadow">
                <div class="card-header">
                  <h4 class="my-0 font-weight-normal">
                    {{ artlisting.title }}
                  </h4>
                </div>
                <div class="card-body">
                  <h1 class="card-title pricing-card-title">
                    Price: ${{ artlisting.price }}
                  </h1>
                  <ul class="list-unstyled mt-3 mb-4">
                    <li v-if="actionTitle.localeCompare('Pinned Art') === 0">
                      <b>Description:</b> {{ artlisting.description }}
                    </li>
                    <li v-if="isSoldAvailable">
                      <b>Artist email:</b>
                      {{ artlisting.ticketObj.customerObj.user.emailAddress }}
                    </li>
                    <li v-if="isSoldAvailable">
                      <b>Artist displayname:</b>
                      {{ artlisting.ticketObj.customerObj.user.displayname }}
                    </li>
                    <li v-if="isSoldAvailable">
                      <b>Customer email:</b> {{ artlisting.ticketObj.customerObj.user.emailAddress }}
                    </li>
                    <li v-if="isSoldAvailable">
                      <b>Customer displayname:</b>
                      {{ artlisting.ticketObj.customerObj.user.displayname }}
                    </li>
                    <li v-if="isSoldAvailable">
                      <b>Piece location:</b>
                      {{ artlisting.artPieces[0].basicLocation }}
                    </li>
                    <li v-if="isSoldAvailable">
                      <b>Target Piece location:</b>{{
                        artOrders[parseInt(index)].targetLocation
                      }}, {{ artOrders[parseInt(index)].targetAddress }}
                    </li>
                    <li v-if="isSoldAvailable">
                      <b>Delivery method:</b> {{ artOrders[parseInt(index)].deliveryTracker }}
                    </li>
                    <li v-if="isSoldAvailable">
                      <b>Art Delivered:</b> {{ artOrders[parseInt(index)].delivered }}
                    </li>
                    <li v-if="isSoldAvailable">
                      <b>Payment Confirmed:</b>
                      {{ artTickets[parseInt(index)].paymentConfirmed }}
                    </li>
                    <li><b>Gallery Commision:</b> ${{ 0.1 * artlisting.price }}</li>
                  </ul>
                  <button
                    type="button"
                    class="btn btn-lg btn-block btn-outline-primary"
                    v-show="isLoggedIn && !isSoldAvailable"
                    v-on:click="unpinArt(artlisting.idCode)">
                    Unpin listing
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
/* eslint-disable */
import HelloWorld from "@/components/HelloWorld.vue";
import axios from "axios";
var config = require("../../config");
var backend = require("@/tools/backend");

var frontendUrl = config.site;

var backendUrl = config.backend.site;

const pathName = window.location.pathname;

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: {
    "Access-Control-Allow-Origin": frontendUrl,
    "Content-Type": "raw",
    "Data-Type": "raw"
  }
});
export default {
  name: "ManagerPage",
  data: function() {
    return {
      actionTitle: "",
      actionDescription: "",
      artListings: [],
      artOrders: [],
      artTickets: [],
      artists: [],
      customers: [],
      isSoldAvailable: false,
      isLoggedIn: false
    };
  },
  created: function() {
    this.isSoldAvailable = false;
    this.showPinned();
    this.isLoggednIn = false;
    let vm = this
    backend.onFirebaseAuth(function(user){
    if (user != null){
        vm.isLoggedIn = ((user.uid).localeCompare(vm.$route.params.id) ===0);
    } else {
       vm.isLoggedIn = false;
    }
    })
},
  methods: {
    showPinned: function() {
      this.isSoldAvailable = false;
      this.actionTitle = "Pinned Art";
      this.actionDescription = "Managers Pinned Art In the Gallary";
      this.artListings = [];
      this.artOrders = [];
      this.artTickets = [];
      backend
        .get("/managers/get_listings/" + this.$route.params.id)
        .then(response => {
          var listings = response.data;
          for (let listing of listings) {
            backend
              .get("/artlisting/get/" + listing)
              .then(response => {
                this.artListings.push(response.data);
              })
              .catch(e => {
                console.log(e);
              });
          }

        })
        .catch(e => {
          console.log(e);
        });
    },
    showSold: function() {
      this.actionTitle = "Sold Art";
      this.actionDescription = "All Sold Art In the Gallary";
      this.artListings = [];
      this.artOrders = [];
      this.artTickets = [];
      this.artists = [];
      this.customers = [];
      let vm = this;
      vm.isSoldAvailable = false

      backend
        .get("/artlisting/get_all/")
        .then(response => {
          var listings = response.data.filter(
            listing =>
              listing != null &&
              listing.artPieces[0].artOrder.localeCompare("") != 0
          );
          var artorders = listings.map(l => {
            vm.artListings.push(l);
            return l.artPieces[0].artOrder;
          });
          var promises0 = artorders.map(id =>
            backend.get("/artorder/get/" + id)
          );
          Promise.all(promises0)
            .then(responses => {
              console.log(responses)
              responses.forEach(r => {
                vm.artOrders.push(r.data);
              });
              var promises1 = listings.map(l => {
                return backend.get("tickets/get/" + l.artPieces[0].ticketId);
              });
              return Promise.all(promises1);
            })
            .then(responses => {
              console.log(responses)
              // retrieved all tickets
              var promises2 = responses.map(r => {
                vm.artTickets.push(r.data);
                return backend.get(
                  "/customers/get/" + r.data.ticketCustomer
                );
              });
              var promises3 = responses.map(r => {
                return backend.get("/artists/get/" + r.data.ticketArtist);
              });
              return Promise.all([Promise.all(promises2), Promise.all(promises3)]);
            })
            .then(bothResponses => {
              console.log(bothResponses)
              let cResps = bothResponses[0]
              let aResps = bothResponses[1]
              for (let i = 0; i < cResps.length && i < aResps.length && i < vm.artTickets.length && vm.artListings.length; i++){
                vm.artTickets[i].artistObj = aResps[i].data
                vm.artists.push(aResps[i].data)
                vm.artTickets[i].customerObj = cResps[i].data
                vm.customers.push(cResps[i].data)
                vm.artListings[i].ticketObj = vm.artTickets[i]
              }
              console.log(vm.artListings)
              console.log(vm.customers)
              vm.isSoldAvailable = true
            });
        })
        .catch(e => {
          console.log(e);
        });
    },
    unpinArt: function(listingId) {
      if (
        backend.retrieveCurrentUser().uid.localeCompare(this.$route.params.id) ===0
      ) {
        backend.post(
          "managers/remove_listing/" + backend.retrieveCurrentUser().uid,
          backend.parse({ listingIdCode: listingId })
        );
        this.showPinned();
      }
    }
  }
};
</script>
