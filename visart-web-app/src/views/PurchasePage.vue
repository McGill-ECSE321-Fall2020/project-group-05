<template>
<span>
  <button type="button" class="btn btn-primary" id="fixedbutton">+</button>
  <div class="container container-space">
    <div class="row">
      <div class="col-xl">
        <div class="card" style="width: 35rem;">
          <img
            class="card-img-top card-space-image"
            src="image"
            alt="Card image cap"
          />
          <div class="card-body">
            <h5 class="card-title text-center">{{ title }}</h5>
          </div>
        </div>
      </div>
      <div class="col-sm">
        <ul class="list-group card-space">
          <li class="list-group-item list-group-item-secondary">Artist:</li>
          <li class="list-group-item">{{ artistName }}</li>
          <li class="list-group-item list-group-item-secondary">
            Description:
          </li>
          <li class="list-group-item">{{ description }}</li>
          <li class="list-group-item list-group-item-secondary">Price:</li>
          <li class="list-group-item">{{ price }}</li>
        </ul>
        <b-button href="#" class="btn btn-secondary btn-lg btn-block btn-space"
          >Buy Now</b-button
        >
        <button
          type="button"
          class="btn btn-secondary btn-lg btn-block btn-space"
          onclick="addFavorite"
        >
          Add to favorite
        </button>
      </div>
    </div>
  </div>
</span>
</template>

<script>
// backend.js has on it: backend.get(path,params), backend.post(path,data), backend.parse(json) => post data
var backend = require('@/tools/backend')
export default {
  data: function () {
    return {
      title: '',
      artistName: '',
      description: '',
      price: ''
    }
  },
  methods: {
    parseArtListing: function (response) {
      this.title = (response.data).title
      this.description = (response.data).description
      this.price = (response.data).price
      this.image = (response.data).postImages
    },
    parseArtist: function (response) {
      this.artistName = (response.data).customer.user.displayname
    },
    getArtist: function () {
      backend
        .get('/artlisting/get/' + this.$route.params.id)
        .then(response => {
          console.log(response.data)
          return backend
            .get('/artists/get/' + (response.data).artist)
        }).then(this.parseArtist)
        .catch(e => {
          console.log(e)
        })
    },
    getArt: function () {
      backend
        .get('/artlisting/get/' + this.$route.params.id)
        .then(this.parseArtListing)
        .catch(e => {
          console.log(e)
        })
    },
    addFavorite: function () {
      let userId = backend.retrieveCurrentUser().uid
      if (userId == null) {
          this.$alert("Sign in to add Artwork to your Favorites!")
      } else {
          // User id is therefore valid
          let artlistingId = this.$route.params.id
          backend.get('/customers/get/' + userId).then(function (response) {
              // This function will run if the id is for a customer
              return backend.post('/customers/add_favorite_listing/'+userId, {
                  'listingIdCode' : artlistingId
              })
          }).then(function(response){
              // response.data is the newly updated CustomerDto object, if you need it
          }).catch(function (error){
              // This function will run if the id is not a valid customer,
              // or just something goes wrong
          })
      }
    }
  },
  created: function () {
    this.getArt()
    this.getArtist()
  }
}
</script>

<style>
.btn-space {
  margin-top: 50px;
}

.container-space {
  margin-top: 100px;
}

.card-space {
  margin-top: 25px;
}

.card-space-image {
  margin-top: 70px;
}
</style>
