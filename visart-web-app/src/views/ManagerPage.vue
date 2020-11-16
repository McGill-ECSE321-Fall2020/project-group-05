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
                      <b>Customer email:</b>
                      {{ artlisting.ticketObj.customerObj.user.emailAddress }}
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
                      <b>Target Piece location:</b
                      >{{ artOrders[parseInt(index)].targetLocation }},
                      {{ artOrders[parseInt(index)].targetAddress }}
                    </li>
                    <li v-if="isSoldAvailable">
                      <b>Delivery method:</b>
                      {{ artOrders[parseInt(index)].deliveryTracker }}
                    </li>
                    <li v-if="isSoldAvailable">
                      <b>Art Delivered:</b>
                      {{ artOrders[parseInt(index)].delivered }}
                    </li>
                    <li v-if="isSoldAvailable">
                      <b>Payment Confirmed:</b>
                      {{ artTickets[parseInt(index)].paymentConfirmed }}
                    </li>
                    <li>
                      <b>Gallery Commision:</b> ${{ 0.1 * artlisting.price }}
                    </li>
                  </ul>
                  <button
                    type="button"
                    class="btn btn-lg btn-block btn-outline-primary"
                    v-show="isLoggedIn && !isSoldAvailable"
                    v-on:click="unpinArt(artlisting.idCode)"
                  >
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
    let vm = this;
    backend.onFirebaseAuth(function(user) {
      if (user != null) {
        vm.isLoggedIn = user.uid.localeCompare(vm.$route.params.id) === 0;
      } else {
        vm.isLoggedIn = false;
      }
    });
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
       console.log('show sold is called');
      this.actionTitle = "Sold Art";
      this.actionDescription = "All Sold Art In the Gallary";
      this.artListings = [];
      this.artOrders = [];
      this.artTickets = [];
      this.artists = [];
      this.customers = [];
      let vm = this;
      vm.isSoldAvailable = false;
      let listingsObj;
      backend
<<<<<<< HEAD
        .get("/artlisting/get_all/")
        .then(response => {
          var listings = response.data.filter(
            listing =>
              listing != null &&
              listing.artPieces[0].artOrder == ''
          );
          var artorders = listings.map(l => {
            vm.artListings.push(l);
            return l.artPieces[0].artOrder;
=======
        .get("artlisting/get_all/")
        .then(resp => {
          let listings = resp.data;
          listingsObj = {};
          listings.forEach(l => {
            listingsObj[l.idCode] = l;
>>>>>>> ea4ac48861db90cdb81bb4b7f85a83a1d663c393
          });
          let ticketIds = listings
            .map(l => l.artPieces[0].ticketId)
            .filter(t => t != null);

          return Promise.all(
            ticketIds.map(t => backend.get("tickets/get/" + t))
          );
        })
        .then(resp => {
          // retrieved tickets
          let tickets = resp.map(r => r.data);
          tickets.forEach(t => {
            let curr = listingsObj[t.ticketArtListingId];
            curr.ticket = t;
            vm.artListings.push(curr);
          });
          console.log(this.artListings);
          vm.isSoldAvailable = false;
        });
      // artlistings is populated, and has a artlistings[0].ticket
    },
    unpinArt: function(listingId) {
      if (
        backend
          .retrieveCurrentUser()
          .uid.localeCompare(this.$route.params.id) === 0
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
