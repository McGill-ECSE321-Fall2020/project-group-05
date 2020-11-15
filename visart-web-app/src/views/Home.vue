<template>
  <div class="home">
    <section id="mainArtSection">
      <hooper
        id="hooperContainer"
        :autoPlay="true"
        :playSpeed="7000"
        :itemsToShow="1"
        :infiniteScroll="true"
      >
        <slide>
          <div class="sectionImage" id="mainHomeArt1"></div>
        </slide>
        <slide>
          <div class="sectionImage" id="mainHomeArt2"></div>
        </slide>
        <slide>
          <div class="sectionImage" id="mainHomeArt3"></div>
        </slide>
        <slide>
          <div class="sectionImage" id="mainHomeArt4"></div>
        </slide>
        <hooper-navigation
          id="tagNavigation"
          slot="hooper-addons"
        ></hooper-navigation>
      </hooper>
      <div class="sectionContent" id="sectionContentTitle">Vis Art</div>
    </section>
    <section id="tagSection">
      <h5 class="mainTagsTitle">Our Main Tags</h5>
      <div id="tagContainer">
        <hooper id="hooperContainerTags" :settings="hooperSettings">
          <slide v-for="(tag, index) in tags" :key="index">
            <button
              type="button"
              class="btn btn-primary tagBtn"
              @click="redirectToHome(tag.keyword)"
            >
              {{ tag.keyword }}
            </button>
          </slide>
          <slide>
            <button type="button" class="btn btn-primary tagBtn">
              Sculpture
            </button>
          </slide>
          <slide>
            <button type="button" class="btn btn-primary tagBtn">Cubism</button>
          </slide>
          <slide>
            <button type="button" class="btn btn-primary tagBtn">
              Realism
            </button>
          </slide>
          <slide>
            <button type="button" class="btn btn-primary tagBtn">
              Fauvism
            </button>
          </slide>
          <slide>
            <button type="button" class="btn btn-primary tagBtn">Oil</button>
          </slide>
          <slide>
            <button type="button" class="btn btn-primary tagBtn">Absurd</button>
          </slide>
          <slide>
            <button type="button" class="btn btn-primary tagBtn">
              Comtemporary
            </button>
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
    <div id="listingContainer">
      <label for="toggle_button">
        <h1 v-if="isActive" id="listingsTitle">– Featured Art –</h1>
        <h1 v-if="!isActive" id="listingsTitle">– Full Collection –</h1>
        <button class="toggleBtnHome" @click="isActive = !isActive">
          {{ isActive ? "View Full Collection" : "View Featured Art" }}
        </button>
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
            <button data-dismiss="alert" data-target="#closeablecard" type="button" class="close" aria-label="Close" @click="deleteListing(artlisting.idCode)">
              <span aria-hidden="true">&times;</span>
            </button>
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
            <button data-dismiss="alert" data-target="#closeablecard" type="button" class="close" aria-label="Close" @click="deleteListing(artlisting.idCode)">
              <span aria-hidden="true">&times;</span>
            </button>
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
  </div>
</template>
<script>
/* eslint-disable */
// @ is an alias to /src
//  bar scroll
import { Hooper, Slide, Navigation as HooperNavigation } from "hooper";
import "hooper/dist/hooper.css";
import axios from "axios";
var config = require("../../config");
var frontendUrl = config.site;
var backendUrl = config.backend.site;
var backend = require('@/tools/backend')
var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: {
    "Access-Control-Allow-Origin": frontendUrl,
    "Content-Type": "raw",
    "Data-Type": "raw"
  }
});
console.log("im here");
export default {
  name: "Home",
  components: {
    Hooper,
    Slide,
    HooperNavigation
  },
  data() {
    return {
      isActive: false,
      tags: [],
      artListingsFull: [],
      artListingsFeatured: [],
      postImagesArray: [],
      hooperSettings: {
        itemsToShow: 7,
        centerMode: true,
        infiniteScroll: true,
        itemsToSlide: 1,
        rtl: false,
        pagination: "no",
        mouseDrag: false,
        autoPlay: true
      },
      artistNames: {}
    };
  },
  created: function() {
    let vm = this;
    AXIOS.get("/artlisting/get_all")
      .then(response => {
        console.log(response.data);
        for (const artListing of response.data) {
          if (artListing.managerId.length !== 0) {
            this.artListingsFeatured.push(artListing)
          }
          AXIOS.get("artists/get/" + artListing.artist).then(response => {
            artListing.artistName = response.data.customer.user.displayname;
            console.log(artListing.artistName);
            vm.artListingsFull.push(artListing);
          });
        }
      })
      .catch(e => {
        console.log(e);
      });
    AXIOS.get("/tags/get_all")
      .then(response => {
        console.log(response.data);
        var tagArr = [];
        for (const tag of response.data) {
          if(tagArr.length===0){
              console.log('added first');
              tagArr.push(tag)
            }
          for (const existingTag in tagArr) {
            console.log('================');
            console.log(tag.keyword );
            console.log(existingTag);
          }
        }
        for(const tag of tagArr) {
          this.tags.push(tag);
        } 
        console.log('////////////')
        console.log(tagArr.length)
      })
      .catch(e => {
        console.log(e);
      });
  },
  methods: {
    redirectToHome: function(tag) {
      this.$router.push({ path: "/search/?keywords=" + tag });
    },
    goToArtist: function(id) {
      this.$router.push({ path: "/artistpage/" + id });
    },
    goToListing: function(id) {
      this.$router.push({ path: "/purchasepage/" + id });
    },
    deleteListing: function(id) {
      backend
        .post(
          '/artlisting/delete/' + id
        )
        .then(response => {
          console.log(response.data);
          console.log('successfully deleted')
          this.$router.go()
        })
        .catch(e => {
          console.log(e);
        });
    }
  }
};
</script>
