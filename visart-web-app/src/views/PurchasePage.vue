<template>
  <span>
    <h1 class="purchasePageTitle">Discover – {{ title }} –</h1>
    <div class="container container-space">
      <div class="row">
        <div class="col-xl">
          <div class="card shadow homeCardBuy homeCard" style="width: 35rem;">
            <b-carousel
              id="carousel-1"
              v-model="slide"
              :interval="4000"
              controls
              indicators
              background="#ababab"
              img-width="1024"
              img-height="480"
              style="text-shadow: 1px 1px 2px #333;"
              @sliding-start="onSlideStart"
              @sliding-end="onSlideEnd"
            >
              <!-- Slides with image only -->
              <div
                class="carouselPurchaseDiv"
                v-for="(img, index) in images"
                :key="index"
              >
                <b-carousel-slide :img-src="img.toString()"></b-carousel-slide>
              </div>
            </b-carousel>
            <div class="card-body cardBodyBuy">
              <h5 class="card-title text-center">
                {{ title }}
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
              </h5>
            </div>
          </div>
        </div>
        <div class="col-sm">
          <ul class="list-group card-space listingInfoBuy shadow">
            <li class="list-group-item list-group-item-secondary infoColor">
              Artist:
            </li>
            <li
              class="list-group-item varInfo"
              id="artistLinkBuy"
              @click="goToArtistPage(artistId)"
            >
              {{ artistName }}
            </li>
            <li class="list-group-item list-group-item-secondary infoColor">
              Description:
            </li>
            <li class="list-group-item varInfo">{{ description }}</li>
            <li class="list-group-item list-group-item-secondary infoColor">
              Price:
            </li>
            <li class="list-group-item varInfo">{{ price }}</li>
          </ul>
          <b-button
            v-on:click="goToCheckout(listId)"
            class="btn btn-secondary btn-lg btn-block btn-space btnPurchase"
            v-if="!isManager"
            >Buy Now</b-button
          >
          <button
            type="button"
            class="btn btn-secondary btn-lg btn-block btnPurchase"
            @click="addFavorite"
          >
            {{ isManager ? "Promote Artwork as Manager" : "Add to favorite" }}
          </button>
        </div>
      </div>
    </div>
  </span>
</template>

<script>
/* eslint-disable */
// backend.js has on it: backend.get(path,params), backend.post(path,data), backend.parse(json) => post data
var backend = require("@/tools/backend");
export default {
  data: function() {
    return {
      title: "",
      artistName: "",
      description: "",
      price: "",
      images: [],
      listId: "",
      slide: 0,
      sliding: null,
      artistId: "",
      loggedin: "",
      isManager: false
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
    goToArtistPage: function(id) {
      this.$router.push({ path: "/artistpage/" + id });
    },
    onSlideStart(slide) {
      this.sliding = true;
    },
    onSlideEnd(slide) {
      this.sliding = false;
    },
    parseArtListing: function(response) {
      this.title = response.data.title;
      this.description = response.data.description;
      this.price = response.data.price;
      this.images.push(response.data.postImages);
      this.artistId = response.data.artist;
      this.artlisting = response.data;
    },
    parseArtist: function(response) {
      this.artistName = response.data.customer.user.displayname;
    },
    getArtist: function() {
      backend
        .get("/artlisting/get/" + this.$route.params.id)
        .then(response => {
          console.log(response.data);
          return backend.get("/artists/get/" + response.data.artist);
        })
        .then(this.parseArtist)
        .catch(e => {
          console.log(e);
        });
    },
    getArt: function() {
      backend
        .get("/artlisting/get/" + this.$route.params.id)
        .then(this.parseArtListing)
        .catch(e => {
          console.log(e);
        });
    },
    addFavorite: function() {
      const user = backend.retrieveCurrentUser();
      if (user == null) {
        this.$router.push({ path: "/login/" });
      } else {
        const userId = user.uid;
        const artlistingId = this.$route.params.id;
        backend
          .get("/users/get/" + userId)
          .then(resp => {
            switch (resp.data.role) {
              case "Customer":
                return backend.post(
                  "/customers/add_favorite_listing/" + userId,
                  backend.parse({
                    listingIdCode: artlistingId
                  })
                );
              case "Manager":
                return backend.post(
                  "/managers/add_listing/" + userId,
                  backend.parse({
                    listingIdCode: artlistingId
                  })
                );
            }
          }).then(this.getArt)
          .catch(function(error) {
            // This function will run if the id is not a valid customer,
            // or just something goes wrong
            console.log(error);
          });
      }
    },
    goToCheckout: function(idCode) {
      const user = backend.retrieveCurrentUser();
      if (user != null) {
        this.$router.push({ path: "/checkout/" + idCode });
      } else {
        this.$router.push({ path: "/login/" });
      }
    }
  },
  created: function() {
    this.getArt();
    this.getArtist();
    this.listId = this.$route.params.id;
    backend.onFirebaseAuth(user => {
      backend.get("/users/get/" + user.uid).then(resp => {
        this.isManager = resp.data.role == "Manager";
      });
    });
  }
};
</script>

<style>
.btn-space {
  margin-top: 50px;
}

.container-space {
  margin-top: 100px;
}

.infoColor {
  background-color: rgb(168, 162, 138);
  font-size: 120%;
  color: #fff;
}
.btnPurchase {
  background-color: rgb(146, 135, 113);
  color: #fff;
}

.btnPurchase:hover {
  background-color: rgb(104, 95, 77);
  color: #fff;
}

.card-space {
  margin-top: 35px;
}

.card-space-image {
  margin-top: 70px;
}
.varInfo {
  color: rgb(80, 73, 65);
}
.purchasePageTitle {
  margin-top: 10%;
  margin-bottom: -5%;
  font-size: 200%;
  color: rgb(80, 73, 65);
}
#artistLinkBuy:hover {
  cursor: pointer;
  color: rgb(121, 111, 99);
}
</style>
