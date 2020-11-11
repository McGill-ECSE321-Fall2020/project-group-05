
<template>
<div>
     <div class="container">
        <div class="row">
            <div class="col-xl">
                 <b-button v-on:click="showPublishedArt">Published Art</b-button>
        </div>
            <div class="col-xl">
                 <b-button v-on:click="showPurchasedArt">PurchasedArt</b-button>
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
                  <p class="card-text">{{artlisting.title}}</p>
                  <div class="d-flex justify-content-between align-items-center">
                    <div class="btn-group">
                      <button type="button" class="btn btn-sm btn-outline-secondary">Go to listing</button>
                      <button type="button" class="btn btn-sm btn-outline-secondary">Delete</button>
                    </div>
                    <small class="text-muted">{{artlisting.price}}</small>
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
// @ is an alias to /src
/* eslint-disable */
import HelloWorld from '@/components/HelloWorld.vue'
import axios from 'axios'
var config = require('../../config')
var backend = require('@/tools/backend')

var frontendUrl = config.site

var backendUrl = config.backend.site 

const pathName = window.location.pathname;

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl, 'Content-Type':'raw', 'Data-Type':'raw' }
})
/* eslint-disable */
export default {
  name: 'UserPage',
  Component: {
    HelloWorld
  },
  data: function () {
    return {
      count: 0,
      purchasedArt: [],
      artListings: [],
      artistId: '',
    }
  },
  props: [],
  methods: {
    showPurchasedArt: function () {
      backend
      .get("/customers/user/"+ this.$route.params.id)
      .then(response => {
        console.log(response.data);
        var tickets = JSON.parse(response.data).boughtTickets;
        
        for (ticket in tickets){
           //get the ArtOrder corresponding to each ticket
           backend
            .get("/artorder/get/"+ticket.ticketOrder)
            .then(response => {
               //get the ArtPiece corresponding to each order
               
               backend
                .get("/artorder/get/"+JSON.parse(response.data).artPieceId)
                .then(response => {
                    //get the artlisting attached to each ArtPiece
                    backend
                        .get("/artorder/get/"+JSON.parse(response.data).artListingId)
                        .then(response => {
                        
                            //PUSH the ArtListing to the array in data
                            this.artListings.push(JSON.parse(response.data));    
                            
                        }).catch(e => {
                            console.log(e);
                        }); 
                    
                }).catch(e => {
                    console.log(e);
                }); 
      
            }).catch(e => {
                console.log(e);
            }); 
        }
        
      }).catch(e => {
        console.log(e);
      });
    },
    showPublishedArt: function () {

    },
    showSoldArt: function () {
      this.count++
      console.log(this.count)
    },
    created: function () {
    console.log(config.site)
    AXIOS.post('/managers/create',{
      'emailAddress':'auryan898@gmail.com', 
      'displayname':'dingdong2', 
      'username':'hiIAmBilly2', 
      'password':'password', 
      'profilePicLink':'',
      'profileDescription':'' 
      }).then(response => {
      console.log(response.data)
    }).catch(error => {
      console.log(error)
    })
    AXIOS.get('/managers/get_all').then(response => {
      console.log(response.data)
    }).catch(e => {
      console.log(e)
    })
  }
  }
}
</script>
