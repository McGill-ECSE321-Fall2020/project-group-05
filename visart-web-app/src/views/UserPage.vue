<template>
<div>
 <div class="container">
    <div class="pricing-header px-3 py-3 pt-md-5 pb-md-4 mx-auto text-center">
        <h1 class="display-4">{{ actionTitle }}</h1>
        <p class="lead">{{ actionDescription }}</p>
            <div class="row">
                <div class="col-xl">
                    <b-button v-on:click="showFavoriteArt">Favorited Art</b-button>
                </div>
                <div class="col-xl">
                    <b-button v-on:click="showPurchasedArt">Purchased Art</b-button>
                </div>
            </div>
        </div>
    </div>
      <div class="album py-5 bg-light">
        <div class="container">
          <div class="row">
            <div class="col-md-4" v-for="(artlisting, index) in artListings" :key="index">
              <div class="card mb-4 box-shadow">
                <img class="card-img-top" :src="getPostImage(artlisting)[0]" alt="Card image cap">
                <div class="card-body">
                  <p class="card-text">{{artlisting.title}}, ${{artlisting.price}}</p>
                  <div class="d-flex justify-content-between align-items-center">
                    <div class="btn-group">
                      <button type="button" v-on:click="goToListing(artlisting.idCode)" class="btn btn-sm btn-outline-secondary">Go to listing</button>
                      <button type="button"
                      v-on:click="preformAction2(artlisting.idCode)"
                      class="btn btn-sm btn-outline-secondary"
                      v-show="isLoggedIn">{{action2}}</button>
                    </div>
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
// @ is an alias to /src
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
/* eslint-disable */
export default {
    name: "UserPage",
    Component: {
        HelloWorld
    },
    data: function () {
        return {
            artListings : [],
            paymentConfirmed: [],
            action2: 'Unfavorite',
            actionTitle: 'Not a User',
            actionDescription: '',
            isLoggedIn: false,
        };
    },
    created: function () {
        console.log('created');
        console.log("created");
        let vm = this

        backend
        .get("/customers/get/" + this.$route.params.id)
        .then(response => {
            this.actionTitle = (response.data).user.displayname;
            this.actionDescription = 'Certified buyer in collaboration with Vis Art Gallery';
        })
        .catch(e => {
            console.log(e);
        });

        backend.onFirebaseAuth(function(user){
        if (user != null){
            backend
            .get("/customers/get/" + vm.$route.params.id)
            .then(response => {
               vm.isLoggedIn = (user.uid).localeCompare((response.data).user.idCode)===0;
               vm.showFavoriteArt();
            })
            .catch(e => {
                console.log(e);
            });

        }
    })
    },
    methods: {
        showPurchasedArt: function () {
            this.action2 = 'Confirm Delivery';
            this.artListings = [];
            this.paymentConfirmed = [];
            backend
                .get("/customers/get/" + this.$route.params.id)
                .then(response => {
                    console.log(response.data)
                    var tickets = response.data.boughtTickets;

                    for (let ticket of tickets) {
                        //get the ArtOrder corresponding to each ticket
                        this.paymentConfirmed.push("Payment Confirmed: " + ticket.paymentConfirmed);
                        backend
                            .get("/artorder/get/" + ticket['ticketOrder'])
                            .then(response => {
                                //get the ArtPiece corresponding to each order
                                console.log(response.data)
                                return backend
                                    .get("/artpiece/get/" + (response.data).artPiece)

                            }).then(response => {
                                //get the artlisting attached to each ArtPiece
                                return backend.get(
                                    "/artlisting/get/" + (response.data).artListing
                                );
                            })
                            .then(response => {
                                //PUSH the ArtListing to the array in data
                                this.artListings.push((response.data));
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
        getPostImage: function(listing) {
            if (!!listing.postImages && listing.postImages.length > 0)
                return listing.postImages;
            else return "";
        },
        showFavoriteArt: function () {
            if (!this.isLoggedIn){
                this.action2 = '';
            } else {
                this.action2 = 'Unfavorite';
            }
             backend
                .get("/customers/get/" + this.$route.params.id)
                .then(response => {
                    this.artListings = response.data.favoriteListings;
                }).catch(e => {
                    console.log(e);
                });
        },
        goToListing: function (idCode) {
            this.$router.push({path:'/purchasepage/'+idCode});
        },
        preformAction2: function (listingId) {

            if (this.action2.localeCompare('Unfavorite')===0){
                backend.post('customers/remove_favorite_listing/'+this.$route.params.id, backend.parse({'listingIdCode':listingId}))
                .then(response => {this.showFavoriteArt();});
                this.showFavoriteArt();
            }else if(this.action2.localeCompare('Confirm Delivery')===0){
                var ticket;
                backend
                .get("/artlisting/get/" + listingId)
                .then(response => {
                   console.log((response.data).artPieces[0].ticketId);
                   return backend
                   .get("/artorder/get/" + (response.data).artPieces[0].orderId)
                })
                .then(response =>{
                    backend.post('artorder/update/'+(response.data).idCode,
                    backend.parse({'aIsDelivered': true}));
                })
                .catch(e => {
                    console.log(e);
                });

                this.showSoldArt();
            }
        },
    }
}
</script>