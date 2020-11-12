<template>
  <div class="search">
  <button type="button" class="btn btn-primary" id="fixedbutton">+</button>
<section id="tagSection">
<div id="tagContainer">
 <hooper id="hooperContainerTags" :settings="hooperSettings">
    <slide v-for="(tag, index) in tags" :key="index">
    <button type="button" class="btn btn-primary tagBtn">{{tag.keyword}}</button>
    </slide>
    <slide>
    <button type="button" class="btn btn-primary tagBtn">Sculpture</button>
    </slide>
    <slide>
    <button type="button" class="btn btn-primary tagBtn">Cubism</button>
    </slide>
    <slide>
    <button type="button" class="btn btn-primary tagBtn">Realism</button>
    </slide>
    <slide>
    <button type="button" class="btn btn-primary tagBtn">Fauvism</button>
    </slide>
    <slide>
    <button type="button" class="btn btn-primary tagBtn">Oil</button>
    </slide>
    <slide>
    <button type="button" class="btn btn-primary tagBtn">Absurd</button>
    </slide>
    <slide>
    <button type="button" class="btn btn-primary tagBtn">Comtemporary</button>
    </slide>
    <slide>
    <button type="button" class="btn btn-primary tagBtn">Resin</button>
    </slide>
    <slide>
    <button type="button" class="btn btn-primary tagBtn">Glass</button>
    </slide>
      <hooper-navigation slot="hooper-addons"></hooper-navigation>
  </hooper>
</div>
</section>
    <div class="listingContainer">
      <label for="toggle_button">
        <h1 v-if="isActive" id="listingsTitle"> – Featured Art – </h1>
        <h1 v-if="! isActive" id="listingsTitle"> –  – </h1>
        <button class="toggleBtnHome" @click="isActive = !isActive">{{isActive ? 'View Full Collection' : 'View Featured Art'}}</button>
        <span class="toggle__switch"></span>
    </label>
      <div v-if="! isActive" class="card-columns card-columns-home">
  <div class="card shadow homeCard" v-for="(artlisting,index) in artListings" :key="index">
    <img class="card-img-top cardImg" src="../assets/cardTrial.png" alt="Card image cap">
    <div class="sectionContent sectionContentListing">{{artlisting.title}}</div>
    <div class="card-body">
      <h4 class="card-title cardArtist"><a href="/artists">Picasso</a></h4>
      <h5 class="card-title cardPrice">$ {{artlisting.price}}</h5>
      <div class="descriptionContainer">
        <p class="card-text cardDesc">{{artlisting.description}}</p>
      </div>
    </div>
  </div>
</div>
    </div>
    <div>
    </div>
  </div>
</template>
<script>
// @ is an alias to /src
//  bar scroll
import { Hooper, Slide, Navigation as HooperNavigation } from 'hooper'
import 'hooper/dist/hooper.css'
import axios from 'axios'
var config = require('../../config')
var frontendUrl = config.site
var backendUrl = config.backend.site
var backend = require('@/tools/backend')

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl, 'Content-Type': 'raw', 'Data-Type': 'raw' }
})
console.log('im here')
export default {
  name: 'Search',
  components: {
    Hooper,
    Slide,
    HooperNavigation
  },
  data () {
    return {
      isActive: false,
      tags: [],
      artListings: [],
      hooperSettings: {
        itemsToShow: 7,
        centerMode: true,
        infiniteScroll: true,
        itemsToSlide: 1,
        rtl: false,
        pagination: 'no',
        mouseDrag: false,
        autoPlay: true
      }
    }
  },
  created: function () {
    //    make array of keyword parsed by space
    backend.get('/artlisting/get_artwork_by_keyword/' + this.$route.params.keywords).then(response => {
      console.log(response.data)
      console.log('listing by keyword')
      for (const artListing of (response.data)) {
        this.artListings.push(artListing)
      }
    }).catch(e => {
      console.log(e)
    })
    AXIOS.get('/managers/get_listings').then(response => {
      console.log(response.data)
      for (const managerListing of (response.data)) {
        this.artListingsFeatured.push(managerListing)
      }
    }).catch(e => {
      console.log(e)
    })
    AXIOS.get('/tags/get_all').then(response => {
      console.log(response.data)
      for (const tag of (response.data)) {
        this.tags.push(tag)
      }
    }).catch(e => {
      console.log(e)
    })
  }
}
</script>
