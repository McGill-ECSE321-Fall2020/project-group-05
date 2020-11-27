<template>
  <div class="search">
    <section id="tagSection">
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
          <hooper-navigation slot="hooper-addons"></hooper-navigation>
        </hooper>
      </div>
    </section>
    <div class="listingContainer">
      <label for="toggle_button">
        <h1 v-if="isActive" class="listingsTitle">
          – Featured Search Results –
        </h1>
        <h1 v-if="!isActive" class="listingsTitle">– Search Results –</h1>
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
          <div class="card-header bg-transparent border-bottom-0"></div>
          <div
            class="sectionContent sectionContentListing"
            @click="goToListing(artlisting.idCode)"
          >
            {{ artlisting.title
            }}<svg
              v-if="isPromoted(artlisting)"
              width=".8em"
              height=".8em"
              viewBox="0 0 16 16"
              class="bi bi-star-fill"
              fill="currentColor"
              xmlns="http://www.w3.org/2000/svg"
              style="color:blue;"
            >
              <path
                d="M3.612 15.443c-.386.198-.824-.149-.746-.592l.83-4.73L.173 6.765c-.329-.314-.158-.888.283-.95l4.898-.696L7.538.792c.197-.39.73-.39.927 0l2.184 4.327 4.898.696c.441.062.612.636.283.95l-3.523 3.356.83 4.73c.078.443-.36.79-.746.592L8 13.187l-4.389 2.256z"
              />
            </svg>
            <svg
              v-if="isFavorited(artlisting)"
              width=".8em"
              height=".8em"
              viewBox="0 0 16 16"
              class="bi bi-heart-fill"
              fill="currentColor"
              xmlns="http://www.w3.org/2000/svg"
            >
              <path
                fill-rule="evenodd"
                style="color:red;"
                d="M8 1.314C12.438-3.248 23.534 4.735 8 15-7.534 4.736 3.562-3.248 8 1.314z"
              />
            </svg>
          </div>
          <div class="card-body cardBody">
            <div class="cardTitlesContainer">
              <h4
                class="card-title cardArtist"
                @click="goToArtist(artlisting.artist)"
              >
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
          <div class="card-header bg-transparent border-bottom-0"></div>
          <div
            class="sectionContent sectionContentListing"
            @click="goToListing(artlisting.idCode)"
          >
            {{ artlisting.title }}
            <svg
              v-if="isPromoted(artlisting)"
              width=".8em"
              height=".8em"
              viewBox="0 0 16 16"
              class="bi bi-star-fill"
              fill="currentColor"
              xmlns="http://www.w3.org/2000/svg"
              style="color:blue;"
            >
              <path
                d="M3.612 15.443c-.386.198-.824-.149-.746-.592l.83-4.73L.173 6.765c-.329-.314-.158-.888.283-.95l4.898-.696L7.538.792c.197-.39.73-.39.927 0l2.184 4.327 4.898.696c.441.062.612.636.283.95l-3.523 3.356.83 4.73c.078.443-.36.79-.746.592L8 13.187l-4.389 2.256z"
              />
            </svg>
            <svg
              v-if="isFavorited(artlisting)"
              width=".8em"
              height=".8em"
              viewBox="0 0 16 16"
              class="bi bi-heart-fill"
              fill="currentColor"
              xmlns="http://www.w3.org/2000/svg"
            >
              <path
                fill-rule="evenodd"
                style="color:red;"
                d="M8 1.314C12.438-3.248 23.534 4.735 8 15-7.534 4.736 3.562-3.248 8 1.314z"
              />
            </svg>
          </div>
          <div class="card-body cardBody">
            <div class="cardTitlesContainer">
              <h4
                class="card-title cardArtist"
                @click="goToArtist(artlisting.artist)"
              >
                {{ artlisting.artistName }}
              </h4>
              <h5 class="card-title cardPrice">CAD$ {{ artlisting.price }}</h5>
            </div>
            <p class="card-text cardDesc">{{ artlisting.description }}</p>
          </div>
        </div>
      </div>
    </div>
    <div></div>
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
var backend = require("@/tools/backend");
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
  name: "Search",
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
      hooperSettings: {
        itemsToShow: 5,
        centerMode: true,
        infiniteScroll: true,
        itemsToSlide: 1,
        rtl: false,
        pagination: "no",
        mouseDrag: false,
        autoPlay: true
      }
    };
  },
  methods: {
    isPromoted: function(listing) {
      return listing.managerId != null;
    },
    isFavorited: function(listing) {
      let userId = backend.retrieveCurrentUser();
      userId = userId != null ? userId.uid : null;
      return listing.favoritedCustomerIds.some(id => id == userId);
    },
    redirectToHome: function(tag) {
      this.$router.push({ path: "/search/?keywords=" + tag });
      this.$router.go();
    },
    goToArtist: function(id) {
      this.$router.push({ path: "/artistpage/" + id });
    },
    goToListing: function(id) {
      this.$router.push({ path: "/purchasepage/" + id });
    },
    deleteListing: function(id) {
      backend
        .post("/artlisting/delete/" + id)
        .then(response => {
          console.log(response.data);
          console.log("successfully deleted");
          this.$router.go();
        })
        .catch(e => {
          console.log(e);
        });
    }
  },
  created: function() {
    //    make array of keyword parsed by space
    var key = this.$route.query.keywords;
    console.log(key);
    AXIOS.get("/artlisting/get_artwork_by_keyword/", {
      params: { keywords: this.$route.query.keywords }
    })
      .then(response => {
        console.log(response.data);
        console.log("listing by keyword");
        for (const artListing of response.data) {
          if (artListing.artPieces[0].ticketId == null) {
            if (artListing.managerId != null) {
              this.artListingsFeatured.push(artListing);
            }
            AXIOS.get("artists/get/" + artListing.artist).then(response => {
              artListing.artistName = response.data.customer.user.displayname;
              console.log(artListing.artistName);
              this.artListingsFull.push(artListing);
            });
          }
        }
      })
      .catch(e => {
        console.log(e);
      });
    AXIOS.get("/tags/get_all")
      .then(response => {
        console.log(response.data);
        var tagArr = [];
        for (var i = 0; i < response.data.length; i++) {
          tagArr[response.data[i].keyword] = response.data[i];
        }
        for (var key in tagArr) {
          this.tags.push(tagArr[key]);
        }
        console.log("////////////");
        console.log(tagArr.length);
      })
      .catch(e => {
        console.log(e);
      });
  }
};
</script>
