<template>
<div>
    <div class="pricing-header px-3 py-3 pt-md-5 pb-md-4 mx-auto text-center">
      <h1 class="display-4">{{actionTitle}}</h1>
      <p class="lead">{{actionDescription}}</p>
      <div class="row">
          <div class="col-xl">
            <b-button v-on:click="showPinned">Show Managers Pinned Art</b-button>
          </div>
          <div class="col-xl">
            <b-button v-on:click="showSold">Show All Sold Art In Gallery</b-button>
          </div>
        </div>
    <div class="album py-5 bg-light">
        <div class="container">
          <div class="row">
            <div class="col-md-4" v-for="(artlisting, index) in artListings" :key="index">
              <div class="card mb-4 box-shadow">
                <div class="card-header">
                    <h4 class="my-0 font-weight-normal">{{artlisting.title}}</h4>
                </div>
                <div class="card-body">
                    <h1 class="card-title pricing-card-title">Price: ${{artlisting.price}}</h1>
                    <ul class="list-unstyled mt-3 mb-4">
                    <li v-if="actionTitle.localeCompare('Pinned Art')===0"><b>Description:</b> {{artlisting.description}}</li>
                    <li v-if="actionTitle.localeCompare('Sold Art')===0"><b>Artist email:</b> {{artists[index].customer.user.emailAddress}} </li>
                    <li v-if="actionTitle.localeCompare('Sold Art')===0">Artist displayname: {{artists[index].customer.user.displayname}} </li>
                    <li v-if="actionTitle.localeCompare('Sold Art')===0">Customer email: {{customers[index].user.emailAddress}}</li>
                    <li v-if="actionTitle.localeCompare('Sold Art')===0">Customer displayname: {{customers[index].user.displayname}}</li>
                    <li v-if="actionTitle.localeCompare('Sold Art')===0">Piece location: {{artlisting.artPieces[0].basicLocation}}</li>
                    <li v-if="actionTitle.localeCompare('Sold Art')===0">Target Piece location:{{artOrders[index].targetLocation}}, {{artOrders[index].targetAddress}}</li>
                    <li v-if="actionTitle.localeCompare('Sold Art')===0">Delivery method: {{artOrders[index].deliveryTracker}}</li>
                    <li v-if="actionTitle.localeCompare('Sold Art')===0">Art Delivered: {{artOrders[index].delivered}}</li>
                    <li v-if="actionTitle.localeCompare('Sold Art')===0">Payment Confirmed: {{artTickets[index].paymentConfirmed}}</li>
                    <li>Gallery Commision: ${{.1*artlisting.price}}</li>
                    </ul>
                    <button type="button" class="btn btn-lg btn-block btn-outline-primary"
                    v-show="actionTitle.localeCompare('Pinned Art')===0"
                    v-on:click="unpinArt(artlisting.idCode)">Unpin listing</button>
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
      actionTitle: '',
      actionDescription: '',
      artListings: [],
      artOrders: [],
      artTickets: [],
      artists: [], 
      customers: []
    }
  },
  created: function () {
      this.showPinned();
  },
  methods: {
      showPinned: function () {
          this.actionTitle = 'Pinned Art';
          this.actionDescription = 'Managers Pinned Art In the Gallary';
          this.artListings = [];  
          this.artOrders = [];
          this.artTickets = [];
          console.log(this.artListings)
          backend
            .get("/managers/get_listings/" + this.$route.params.id)
            .then(response => {
                var listings = (response.data);
                for(let listing of listings){
                    backend.get("/artlisting/get/" + listing)
                        .then(response =>{
                            this.artListings.push(response.data);
                        })
                        .catch(e => {
                            console.log(e);
                        });
                    }
                
            }).catch(e => {
                console.log(e);
            });
          
      },
      showSold: function (){
          this.actionTitle = 'Sold Art';
          this.actionDescription = 'All Sold Art In the Gallary';
          this.artListings = [];
          this.artOrders = [];
          this.artTickets = [];
          
          backend
          .get("/artlisting/get_all/")
          .then(response => {
                var listings = (response.data);
                
                for(let listing of listings){
                    console.log(((listing.artPieces[0].artOrder).localeCompare('')));
                    if (listing != null && (listing.artPieces[0].artOrder).localeCompare('')!=0){
                            this.artListings.push(listing);
                            var customerId = '';
                            var artistId = '';
                            backend.get("/artorder/get/" + listing.artPieces[0].artOrder)
                            .then(response =>{
                                this.artOrders.push(response.data);
                                return backend.get("/tickets/get/" + listing.artPieces[0].ticketId)
                            })
                            .then(response => {
                                this.artTickets.push(response.data);
                                customerId = (response.data).ticketCustomer;
                                artistId = (response.data).ticketArtist;
                                return backend.get("/customers/get/" + customerId);
                            }).then(response => {
                                this.customers.push((response.data));
                                return backend.get("/artists/get/" + artistId);
                            }).then(response =>{
                                this.artists.push((response.data));
                            })
                            .catch(e => {
                                console.log(e);
                            });
                        }
                    } 
            }).catch(e => {
                console.log(e);
            });
           

      },
      unpinArt: function (listingId) {
        if ((backend.retrieveCurrentUser().uid).localeCompare(this.$route.params.id)){  
            backend.post('managers/remove_listing/'+backend.retrieveCurrentUser().uid, backend.parse({'listingIdCode':listingId}));
            this.showPinned();
      }
      }
  }
}
</script>  