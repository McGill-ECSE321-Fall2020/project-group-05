<template>
<<<<<<< HEAD
  <div class="row">
  <div class="col-75">
    <div class="container">
      <form action="/action_page.php">

        <div class="row">
          <div class="col-50">
            <h3>Billing Address</h3>
            <label for="fname"><i class="fa fa-user"></i> Full Name</label>
            <input type="text" id="fname" name="firstname" placeholder="John M. Doe">
            <label for="email"><i class="fa fa-envelope"></i> Email</label>
            <input type="text" id="email" name="email" placeholder="john@example.com">
            <label for="adr"><i class="fa fa-address-card-o"></i> Address</label>
            <input type="text" id="adr" name="address" placeholder="542 W. 15th Street">
            <label for="city"><i class="fa fa-institution"></i> City</label>
            <input type="text" id="city" name="city" placeholder="New York">

            <div class="row">
              <div class="col-50">
                <label for="state">State</label>
                <input type="text" id="state" name="state" placeholder="NY">
              </div>
              <div class="col-50">
                <label for="zip">Zip</label>
                <input type="text" id="zip" name="zip" placeholder="10001">
              </div>
            </div>
          </div>

          <div class="col-50">
            <h3>Payment</h3>
            <label for="fname">Accepted Cards</label>
            <div class="icon-container">
              <i class="fa fa-cc-visa" style="color:navy;"></i>
              <i class="fa fa-cc-amex" style="color:blue;"></i>
              <i class="fa fa-cc-mastercard" style="color:red;"></i>
              <i class="fa fa-cc-discover" style="color:orange;"></i>
            </div>
            <label for="cname">Name on Card</label>
            <input type="text" id="cname" name="cardname" placeholder="John More Doe">
            <label for="ccnum">Credit card number</label>
            <input type="text" id="ccnum" name="cardnumber" placeholder="1111-2222-3333-4444">
            <label for="expmonth">Exp Month</label>
            <input type="text" id="expmonth" name="expmonth" placeholder="September">

            <div class="row">
              <div class="col-50">
                <label for="expyear">Exp Year</label>
                <input type="text" id="expyear" name="expyear" placeholder="2018">
              </div>
              <div class="col-50">
                <label for="cvv">CVV</label>
                <input type="text" id="cvv" name="cvv" placeholder="352">
              </div>
            </div>
          </div>

        </div>
        <label>
          <input type="checkbox" checked="checked" name="sameadr"> Shipping address same as billing
        </label>
        <input type="submit" value="Continue to checkout" class="btn">
      </form>
    </div>
  </div>

  <div class="col-25">
    <div class="container">
      <h4>Cart
        <span class="price" style="color:black">
          <i class="fa fa-shopping-cart"></i>
          <b>4</b>
        </span>
      </h4>
      <p><a href="#">Product 1</a> <span class="price">$15</span></p>
      <p><a href="#">Product 2</a> <span class="price">$5</span></p>
      <p><a href="#">Product 3</a> <span class="price">$8</span></p>
      <p><a href="#">Product 4</a> <span class="price">$2</span></p>
      <hr>
      <p>Total <span class="price" style="color:black"><b>$30</b></span></p>
    </div>
  </div>
</div>
</template>
=======
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
    <div class="listingContainer">
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
          <hooper
            id="hooperContainer"
            :autoPlay="true"
            :playSpeed="10000"
            :itemsToShow="1"
            :infiniteScroll="true"
          >
            <slide
              v-for="(imageUrl, index) in artlisting.postImages"
              :key="index"
            >
              <img
                class="card-img-top cardImg"
                :src="imageUrl"
                alt="Card image cap"
              />
            </slide>
            <hooper-navigation
              id="tagNavigation"
              slot="hooper-addons"
            ></hooper-navigation>
          </hooper>
          <div class="sectionContent sectionContentListing">
            {{ artlisting.title }}
          </div>
          <div class="card-body">
            <h4 class="card-title cardArtist">
              <a href="/artists">name : {{ artlisting.artistName }}</a>
            </h4>
            <h5 class="card-title cardPrice">$ {{ artlisting.price }}</h5>
            <div class="descriptionContainer">
              <p class="card-text cardDesc">{{ artlisting.description }}</p>
            </div>
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
            src="../assets/cardTrial.png"
            alt="Card image cap"
          />
          <div class="sectionContent sectionContentListing">
            {{ artlisting.title }}
          </div>
          <div class="card-body">
            <h4 class="card-title cardArtist">
              <a href="/artists">Picasso</a>
            </h4>
            <h5 class="card-title cardPrice">$ {{ artlisting.price }}</h5>
            <div class="descriptionContainer">
              <p class="card-text cardDesc">{{ artlisting.description }}</p>
            </div>
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
          AXIOS.get("artists/get/" + artListing.artist).then(response => {
            artListing.artistName = response.data.customer.user.displayname;
            console.log(artListing.artistName);
            vm.artListingsFull.push(artListing);
            for (const imageUrl of artListing.postImages) {
              vm.postImagesArray.push(imageUrl);
            }
          });
        }
      })
      .catch(e => {
        console.log(e);
      });
    // AXIOS.get('/managers/get_listings/'+managerId).then(response => { // TODO: needs managerId
    //   console.log(response.data)
    //   for (const managerListing of (response.data)) {
    //     this.artListingsFeatured.push(managerListing)
    //   }
    // }).catch(e => {
    //   console.log(e)
    // })
    AXIOS.get("/tags/get_all")
      .then(response => {
        console.log(response.data);
        for (const tag of response.data) {
          this.tags.push(tag);
        }
      })
      .catch(e => {
        console.log(e);
      });
  },
  methods: {
    redirectToHome: function(tag) {
      this.$router.push({ path: "/search/?keywords=" + tag });
    }
  }
};
</script>
>>>>>>> 45db6891acdfe4c0df7a97edb0994f4e9118709c
