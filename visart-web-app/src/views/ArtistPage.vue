<template>
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
                        <b-button v-on:click="showPublishedArt">Unsold Published Art</b-button>
                    </div>
                    <div class="col-xl">
                        <b-button v-on:click="showSoldArt">Sold Art</b-button>
                    </div>
                </div>
            </div>
      <div class="album py-5 bg-light">
        <div class="container">
          <div class="row">
            <div class="col-md-4" v-for="(artlisting, index) in artListings" :key="index">
              <div class="card mb-4 box-shadow">
                <img class="card-img-top" src="../assets/logo.png" alt="Card image cap">
                <div class="card-body">
                  <p class="card-text">{{artlisting.title}}, ${{artlisting.price}}</p>
                  <div class="d-flex justify-content-between align-items-center">
                    <div class="btn-group">
                      <button type="button" v-on:click="goToListing(artlisting.idCode)" class="btn btn-sm btn-outline-secondary">Go to listing</button>
                      <button type="button" v-on:click="preformAction2(artlisting.idCode)" class="btn btn-sm btn-outline-secondary">{{action2}}</button>
                    </div>
                    <small class="text-muted">{{paymentConfirmed[index]}}</small>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
</div>
</template>

<script>/* eslint-disable */
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
    name: "ArtistPage",
    Component: {
        HelloWorld
    },
    data: function () {
        return {
            artListings : [],
            paymentConfirmed: [],
            action2: '',
           
        };
    }, 
    created: function () {
        console.log('created');
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
    methods: {
        showPurchasedArt: function () {
            this.action2 = '';
            this.artListings = [];
            this.paymentConfirmed = [];
            backend
                .get("/artists/get/" + this.$route.params.id)
                .then(response => {
                    console.log(response.data)
                    var tickets = response.data.customer.boughtTickets;

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
        showFavoriteArt: function () {
            let uId = this.getUID();
            if (uId.localeCompare('')===0){
                this.action2 = '';
            } else {
                this.action2 = 'Unfavorite';
            }
             backend
                .get("/artists/get/" + this.$route.params.id)
                .then(response => {
                    this.artListings = response.data.customer.favoriteListings;
                }).catch(e => {
                    console.log(e);
                });
        },
        showPublishedArt: function () {
            let uId = this.getUID();
            if (uId.localeCompare('')===0){
                this.action2 = '';
            } else {
                this.action2 = 'Delete Listing';
            }
            this.artListings = [];
            this.paymentConfirmed = [];
            backend
                .get("/artists/get/" + this.$route.params.id)
                .then(response => {
                    var listings = (response.data).listings;
                    for(let listing of listings){
                    backend
                        .get("/artlisting/get/" + listing)
                        .then(response => {
                            this.artListings.push(response.data);
                        }).catch(e => {
                            console.log(e);
                        });
                    }
                }).catch(e => {
                    console.log(e);
                });
        },
        showSoldArt: function () {
            let uId = this.getUID();
            if (uId.localeCompare('')===0){
                this.action2 = '';
            } else {
                this.action2 = 'Confirm Payment';
            }
            this.artListings = [];
            this.paymentConfirmed = [];
            backend
                .get("/artists/get/" + this.$route.params.id)
                .then(response => {
                    console.log(response.data)
                    var tickets = response.data.soldTickets;

                    for (let ticket of tickets) {
                        //get the ArtOrder corresponding to each ticket
                        backend
                            .get("/tickets/get/" + ticket)
                            .then(response => {
                                this.paymentConfirmed.push("Payment Confirmed: " + (response.data).paymentConfirmed);
                                return backend
                                    .get("artorder/get/" + (response.data).ticketOrder)
                            })
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
        goToListing: function (idCode) {
            this.$router.push({path:'/purchasepage/'+idCode});
        },
        preformAction2: function (listingId) {
            
            if (this.action2.localeCompare('Unfavorite')===0){
                    backend.post('customers/remove_favorite_listing/'+backend.retrieveCurrentUser().uid, backend.parse({'listingIdCode':listingId}));
                this.showFavoriteArt();       
            }else if(this.action2.localeCompare('Delete Listing')===0){
                backend.post('artlisting/delete/'+listingId);
                this.showPublishedArt();  
            }else if(this.action2.localeCompare('Confirm Payment')===0){
                var ticket;
                backend
                .get("/artlisting/get/" + listingId)
                .then(response => {
                   console.log((response.data).artPieces[0].ticketId);
                   return backend
                   .get("/tickets/get/" + (response.data).artPieces[0].ticketId)
                })
                .then(response =>{
                    backend.post('tickets/update/'+(response.data).idCode, 
                    backend.parse({'paymentConfirmed': true}));
                })
                .catch(e => {
                    console.log(e);
                });
                

                this.showSoldArt();
            }
        },
        getUID: function () {
            let user = backend.retrieveCurrentUser();
            if (user == null || user.uid.localeCompare('')===0 || user.uid.localeCompare(this.$route.params.id)===-1) {
                return '';
            } else {
                return user.uid;
            }
        } 
    }
}
</script>