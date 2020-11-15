<template>
  <div class="createlisting">
    <div class="formContainerCreate">
      <div class="container-contact100">
        <div class="wrap-contact100">
          <span class="contact100-form validate-form">
            <span class="contact100-form-title">
              <h1 id="publishTitle">Publish a New Artwork</h1>
            </span>
            <div
              class="wrap-input100 validate-input"
              data-validate="Please enter your title"
            >
              <input
                id="titleInput"
                type="text"
                name="title"
                placeholder="Title"
                @input="artListingAttributes.title = $event.target.value"
              />
              <span class="focus-input100"></span>
            </div>
            <div
              class="wrap-input100 validate-input"
              data-validate="Please enter your description"
            >
              <input
                id="descriptionInput"
                type="text"
                name="description"
                placeholder="Description"
                @input="artListingAttributes.description = $event.target.value"
              />
              <span class="focus-input100"></span>
            </div>
            <div
              class="wrap-input100 validate-input"
              data-validate="Please enter your price"
            >
              <input
                id="priceInput"
                type="number"
                min="0"
                name="price"
                placeholder="Price"
                @input="artListingAttributes.price = $event.target.value"
              />
              <span class="focus-input100"></span>
            </div>
            <div
              class="wrap-input100 validate-input"
              data-validate="Please enter your image(s) url"
            >
              <input
                type="file"
                id="urlInput"
                name="url"
                placeholder="Image Files"
                @input="artListingAttributes.images = $event.target.files"
                :multiple="allowMultipleImages"
                accept="image/png, image/jpeg"
              />
              <span class="focus-input100"></span>
            </div>
            <div
              class="wrap-input100 validate-input"
              data-validate="Please enter your image(s) url"
            >
              <input
                id="tagInput"
                name="tags"
                placeholder="Tags (separate by comma)"
                @input="artListingAttributes.tags = $event.target.value"
              />
              <span class="focus-input100"></span>
            </div>
            <div
              class="wrap-input100 validate-input"
              data-validate="Please enter your image(s) url"
            >
              <input
                id="dimensionsInput"
                name="dimensions"
                placeholder="Dimensions (separate by comma)"
                @input="artListingAttributes.dimensions = $event.target.value"
              />
              <span class="focus-input100"></span>
            </div>
            <div
              class="wrap-input100 validate-input"
              data-validate="Please enter your Visibility"
            >
              <input
                id="visibilityInput"
                type="text"
                name="visibility"
                placeholder="Visibility"
                :value="artListingAttributes.visibility"
              />
              <select @change="artListingAttributes.visibility = $event.target.value">
                <option value="Public">Public</option>
                <option value="Private">Private</option>
                <option value="Unlisted">Unlisted</option>
                <option value="Draft">Draft</option>
              </select>
              <span class="focus-input100"></span>
            </div>
            <div class="container-contact100-form-btn">
              <button
                class="contact100-form-btn submitArtwork"
                v-on:click="submitListing()"
              >
                <span>
                  <i class="fa fa-paper-plane-o m-r-6" aria-hidden="true"></i>
                  Submit
                </span>
              </button>
            </div>
          </span>
        </div>
      </div>
      <div id="dropDownSelect1"></div>
    </div>
  </div>
</template>
<script>
/* eslint-disable */
// @ is an alias to /src
//  bar scroll
import "hooper/dist/hooper.css";
var backend = require("@/tools/backend");
var storage = require("@/tools/cloud-storage");
console.log("im here");
export default {
  name: "CreateListing",
  components: {},
  data() {
    return {
      artistId: "",
      artListingId: "",
      artListingAttributes: {
        title: "",
        description: "",
        price: "",
        images: null,
        image_links: null,
        tags: "",
        dimensions: "",
        visibility: "Public"
      },
      allowMultipleImages: true,
      hooperSettings: {
        itemsToShow: 7,
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
  created: function() {
    let vm = this;
    backend.onFirebaseAuth(user => {
      if (user != null) {
        backend.get("users/get/" + user.uid).then(resp => {
          if (resp.data.role == "Customer") {
            backend.get("customers/get/" + user.uid).then(resp => {
              vm.isArtistLoggedIn = true;
              vm.artistId = resp.data.artistId;
            });
          } else {
            vm.isArtistLoggedIn = false;
          }
        });
      } else {
        vm.isArtistLoggedIn = false;
      }
    });
  },
  methods: {
    submitListing: function() {
      let vm = this;

      // create
      backend
        .post(
          "/artlisting/create",
          backend.parse({
            aVisibility: vm.artListingAttributes.visibility,
            aDescription: vm.artListingAttributes.description,
            aTitle: vm.artListingAttributes.title,
            artistId: vm.artistId,
            price: vm.artListingAttributes.price
          })
        )
        .then(resp => {
          vm.artListingId = resp.data.idCode;
          backend.post(
            "/artpiece/create",
            backend.parse({
              pieceLocation: "Offsite", // TODO: Fill in pieceLocation
              aAddressLocation: "TBD", // TODO: Fill in address
              aArtListingId: vm.artListingId
            })
          );
          backend.post(
            "/artlisting/update_dimensions/" + vm.artListingId,
            backend.parse({
              dimensions: vm.artListingAttributes.dimensions
                .split(",")
                .map(s => s.trim())
            })
          );

          vm.uploadImages(vm.artListingId, vm.artListingAttributes.images)

          vm.addTags(
            vm.artListingId,
            vm.artListingAttributes.tags.split(",").map(s => s.trim())
          );

          window.alert("Your art listing has been created!")
        });
    },
    addTags: function(id, keywords) {
      keywords.forEach(key => {
        backend.post('tags/create',backend.parse({
          aListing: id,
          aKeyword: key,
          aType: 'Other'
        }))
      })
    },
    uploadImages: function(id, images) {
      return Promise.all([...images].map(i => storage.writeSafe(i)))
        .then(responses => {
          return Promise.all(responses.map(r => storage.read(r[0])));
        })
        .then(urls => {
          // console.log(urls);
          return backend.post(
            "/artlisting/update_post_images/" +id,
            backend.parse({
              images: urls
            })
          );
        });
    }
  }
};
</script>
