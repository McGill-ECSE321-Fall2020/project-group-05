<template>
  <!---->
  <div class="container-fluid">
    <!--Introduction-->
    <div class="jumbotron bg-dark">
      <h1 class="text-center text-white">VisArt</h1>
    </div>

    <!--Tabs for Categories-->
    <div class="row"></div>

    <!--Title of listings-->
    <h4>Most popular categories:</h4>
    <b-tabs pills fill align="center">
      <b-button v-for="(category,index) in categoriesFiltered" v-bind:key="index" class="btn btn-primary mx-3" v-bind:title="category">{{capitalizedString(category)}}</b-button>
    </b-tabs>
    <div class="row">
    </div>
    <br/>

    <!--Actual Listings-->
    <h3>-Full Collection-</h3>
    <div
      class="row mb-2"
      v-for="(listingrow, index) in artlisting_rows"
      v-bind:key="index"
    >
      <div
        class="col-md-3"
        v-for="(listing, index1) in listingrow"
        v-bind:key="index1"
      >
        <!--art listing content-->

        <div class="card">
          <img
            :src="getPostImage(listing)[0]"
            class="card-img-top"
            onerror="this.style.display='none'"
            v-bind:alt="listing.title"
          />
          <div class="card-body">
            <h5 class="card-title">{{ listing.title }}</h5>
            <p class="card-text">
              {{ listing.description }}
            </p>
            <p>CAD${{ parseFloat(listing.price).toFixed(2) }}</p>
            <router-link :to="'/userpage/' + listing.artistUserId">
              <p>by {{ listing.artistName }}</p>
            </router-link>
            <router-link :to="'/purchasepage/' + listing.idCode">
              <p href="#" class="btn btn-primary">View This Artwork</p>
            </router-link>
            <p
              href="#"
              class="btn btn-primary"
              @click="deleteListing(listing, index1)"
              hidden
            >
              Delete This Artwork
            </p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
/* eslint-disable */
var backend = require("@/tools/backend");

export default {
  created: function() {
    
    this.loadTagCategories()
    console.log(this.categories)
    this.loadArtListings();
  },
  data: function() {
    return {
      // ArtListingDto:
      // description
      // postImages
      // title
      // visibility
      // dimensions
      // idCode
      // artPieceIds
      // favoritedCustomerIds
      // tagIds
      // managerId
      // artistId
      // price
      categories: [],
      artlistings: [],
      maxCategories: 10
    };
  },
  computed: {
    artlisting_rows: function() {
      let itemsPerRow = 4;
      let result = [];
      for (
        let i = 0;
        i < Math.ceil(this.artlistings.length / itemsPerRow);
        i++
      ) {
        result.push([]);
        let k = i * itemsPerRow;
        for (
          let j = 0;
          j < itemsPerRow && k + j < this.artlistings.length;
          j++
        ) {
          result[i].push(this.artlistings[k + j]);
        }
      }
      return result;
    },
    categoriesFiltered: function(){
      if (!!this.categories)
        return this.categories.slice(0, this.maxCategories)
      else
        return []
    }
  },
  methods: {
    getPostImage: function(listing) {
      if (!!listing.postImages && listing.postImages.length > 0)
        return listing.postImages;
      else return "";
    },
    loadArtListings: function() {
      let vm = this;
      // vm.artlistings
      backend.get("artlisting/get_all").then(resp => {
        return Promise.all(resp.data.map(listing => {
          backend.get("artists/get/" + listing.artist).then(artistResp => {
            listing["artistUserId"] = artistResp.data.customer.idCode;
            listing["artistName"] = artistResp.data.customer.user.displayname;
            listing["postImages"].push(
              "https://firebasestorage.googleapis.com/v0/b/visartapplication.appspot.com/o/postedImages%2F0e548bcd-dbe4-4bdb-952c-c1690ad636a7.png?alt=media&token=6e09902f-1234-4d04-8503-14d681cfa3bb"
            );
            vm.artlistings.push(listing);
          });
        }))
      });
    },
    deleteListing: function(listing, index) {
      backend.post("artlisting/delete/" + listing.idCode).then(resp => {
        this.artlistings.splice(index, index);
      });
    },
    loadTagCategories: function() {
      backend.get("tags/get_all").then(resp => {
        let dict = {};
        resp.data.forEach(t => {
          dict[t.keyword] = 0;
        });
        let arr = resp.data
          .filter(t => {
            return ++dict[t.keyword] == 1;
          })
          .sort((x, y) => dict[y] - dict[x]).map(t=>t.keyword);
        console.log(arr)
        arr.forEach(val=>{this.categories.push(val)})
      });
    },
    capitalizedString: function(s) {
      return s.charAt(0).toUpperCase() + s.slice(1)
    }
  }
};
</script>
