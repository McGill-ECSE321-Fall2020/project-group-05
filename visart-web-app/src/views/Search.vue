<template>
  <div class="search">
<section id="tagSection">
<div id="tagContainer">
 <hooper id="hooperContainerTags" :settings="hooperSettings">
    <slide v-for="(tag, index) in tags" :key="index">
    <button type="button" class="btn btn-primary tagBtn">{{tag.keyword}}</button>
    </slide>
      <hooper-navigation slot="hooper-addons"></hooper-navigation>
  </hooper>
</div>
</section>
    <div class="listingContainer">
      <label for="toggle_button">
        <h1 v-if="isActive" class="listingsTitle"> – Featured Search Results – </h1>
        <h1 v-if="! isActive" class="listingsTitle"> – Search Results – </h1>
        <button class="toggleBtnHome" @click="isActive = !isActive">{{isActive ? 'View Full Collection' : 'View Featured Art'}}</button>
        <span class="toggle__switch"></span>
    </label>
        <div v-if="!isActive" class="card-columns card-columns-home">
        <div
          class="card shadow homeCard"
          v-for="(artlisting, index) in artListingsFull"
          :key="index"
        >
              <img
                class="card-img-top cardImg"
                :src="artlisting.postImages[0]"
                alt="Card image cap"
                @click="goToListing(artlisting.idCode)"
              />
          <div class="card-header bg-transparent border-bottom-0">
          </div>
          <div class="sectionContent sectionContentListing" @click="goToListing(artlisting.idCode)">
            {{ artlisting.title }}
          </div>
          <div class="card-body cardBody">
            <div class="cardTitlesContainer">
              <h4 class="card-title cardArtist" @click="goToArtist(artlisting.artist)">
                {{ artlisting.artistName }}
              </h4>
              <h5 class="card-title cardPrice">CAD$ {{ artlisting.price }}</h5>
            </div>
              <p class="card-text cardDesc">{{ artlisting.description }}</p>
          </div>
        </div>
      </div>
      <div v-if="isActive" class="card-columns card-columns-home">
        <div
          class="card shadow homeCard"
          v-for="(artlisting, index) in artListingsFeatured"
          :key="index"
        >
         <img
                class="card-img-top cardImg"
                :src="artlisting.postImages[0]"
                alt="Card image cap"
                @click="goToListing(artlisting.idCode)"
              />
          <div class="card-header bg-transparent border-bottom-0">
          </div>
          <div class="sectionContent sectionContentListing" @click="goToListing(artlisting.idCode)">
            {{ artlisting.title }}
          </div>
          <div class="card-body cardBody">
            <div class="cardTitlesContainer">
              <h4 class="card-title cardArtist" @click="goToArtist(artlisting.artist)">
                {{ artlisting.artistName }}
              </h4>
              <h5 class="card-title cardPrice">CAD$ {{ artlisting.price }}</h5>
            </div>
              <p class="card-text cardDesc">{{ artlisting.description }}</p>
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
      artListingsFull: [],
      artListingsFeatured: [],
      hooperSettings: {
        itemsToShow: 5,
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
  methods: {
    goToArtist: function (id) {
      this.$router.push({ path: '/artistpage/' + id })
    },
    goToListing: function (id) {
      this.$router.push({ path: '/purchasepage/' + id })
    },
    deleteListing: function (id) {
      backend
        .post(
          '/artlisting/delete/' + id
        )
        .then(response => {
          console.log(response.data)
          console.log('successfully deleted')
          this.$router.go()
        })
        .catch(e => {
          console.log(e)
        })
    }
  },
  created: function () {
    //    make array of keyword parsed by space
    var key = this.$route.query.keywords
    console.log(key)
    AXIOS.get('/artlisting/get_artwork_by_keyword/', { params: { keywords: this.$route.query.keywords } }).then(response => {
      console.log(response.data)
      console.log('listing by keyword')
      for (const artListing of (response.data)) {
        if (artListing.artPieces[0].ticketId == null) {
          if (artListing.managerId != null) {
            this.artListingsFeatured.push(artListing)
          }
          AXIOS.get('artists/get/' + artListing.artist)
            .then(
              response => {
                artListing.artistName = response.data.customer.user.displayname
                console.log(artListing.artistName)
                this.artListingsFull.push(artListing)
              })
        }
      }
    }).catch(e => {
      console.log(e)
    })
    AXIOS.get('/tags/get_all')
      .then(response => {
        console.log(response.data)
        var tagArr = []
        for (var i = 0; i < (response.data).length; i++) {
          tagArr[response.data[i].keyword] = response.data[i]
        }
        for (var key in tagArr) {
          this.tags.push(tagArr[key])
        }
        console.log('////////////')
        console.log(tagArr.length)
      })
      .catch(e => {
        console.log(e)
      })
  }
}
</script>
