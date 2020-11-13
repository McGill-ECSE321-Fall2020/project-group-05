<template>
  <div>
    <div>
      <div class="container">
        <div class="row">
          <div class="col-xl">
            <b-button v-on:click="showFavoriteArt">Favorited Art</b-button>
          </div>
          <div class="col-xl">
            <b-button v-on:click="showPurchasedArt">Purchased Art</b-button>
          </div>
          <div class="col-xl">
            <b-button v-on:click="showSoldArt">Sold Art</b-button>
          </div>
        </div>
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
              <img
                class="card-img-top"
                src="../assets/logo.png"
                alt="Card image cap"
              />
              <div class="card-body">
                <p class="card-text">
                  {{ artlisting.title }}<br />{{ artlisting.description }}
                </p>
                <div class="d-flex justify-content-between align-items-center">
                  <div class="btn-group">
                    <button
                      type="button"
                      class="btn btn-sm btn-outline-secondary"
                    >
                      Go to listing
                    </button>
                  </div>
                  <small class="text-muted"
                    >{{ artlisting.price }},
                    {{ paymentConfirmed[index] }}</small
                  >
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

export default {
  name: "UserPage",
  data: function() {
    return {
      purchasedArt: [],
      artListings: [],
      paymentConfirmed: [],
      artistId: ""
    };
  },
  created: function() {
    console.log("created");
    let vm = this
    backend.onFirebaseAuth(function(user){
      if (user != null){
        vm.showPurchasedArt(user.uid)
      } else {
        vm.showPurchasedArt(vm.$route.params.id)
      }
    })
  },
  props: [],
  methods: {
    showPurchasedArt: function(customerId) {
      let vm = this; // Can't access this in different places, so I renamed it to vm
      this.artListings = [];
      backend
        .get("/customers/get/" + customerId) // get the current customer
        .then(function(response) {
          console.log(response.data);
          var tickets = response.data.boughtTickets;
          return Promise.all(
            tickets.map(function(ticket) {
              return backend
                .get("/artorder/get/" + ticket["ticketOrder"])
                .then(function(response) {
                  //get the ArtPiece corresponding to each order
                  return backend.get("/artpiece/get/" + response.data.artPiece);
                })
                .then(function(response) {
                  //get the artlisting attached to each ArtPiece
                  return backend.get(
                    "/artlisting/get/" + response.data.artListing
                  );
                });
            })
          );
        })
        .then(function(artlistings) {
          for (let resp of artlistings) {
            let listing = resp.data;
            console.log(listing);
            // Now that we have artlistings that were purchased, we add them
            vm.artListings.push(listing)
          }
        })
        .catch(e => {
          console.log(e);
        });
      /*
      backend
        .get("/customers/get/" + this.$route.params.id) // get the current customer
        .then(function(response) {
          console.log(response.data);
          var tickets = response.data.boughtTickets;

          for (let ticket of tickets) {
            //get the ArtOrder corresponding to each ticket
            this.paymentConfirmed.push(
              "Payment Confirmed: " + ticket.paymentConfirmed
            );
            backend
              .get("/artorder/get/" + ticket["ticketOrder"])
              .then(function(response) {
                //get the ArtPiece corresponding to each order
                console.log(response.data);
                return backend.get("/artpiece/get/" + response.data.artPiece);
              })
              .then(function(response) {
                //get the artlisting attached to each ArtPiece
                return backend.get(
                  "/artlisting/get/" + response.data.artListing
                );
              })
              .then(function(response) {
                //PUSH the ArtListing to the array in data
                this.artListings.push(response.data);
              })
              .catch(e => {
                console.log(e);
              });

          }
        })
        .catch(e => {
          console.log(e);
        });*/
    },
    showFavoriteArt: function() {
      backend
        .get("/customers/get/" + this.$route.params.id)
        .then(function(response) {
          this.artListings = response.data.favoriteListings;
        })
        .catch(e => {
          console.log(e);
        });
    },
    showSoldArt: function() {
      this.count++;
      console.log(this.count);
    }
  }
};
</script>
