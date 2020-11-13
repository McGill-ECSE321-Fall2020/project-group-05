<template>
  <div class="createlisting">
    <div class="formContainerCreate">
      <div class="container-contact100">
        <div class="wrap-contact100">
          <span class="contact100-form validate-form" >
            <span class="contact100-form-title">
              <h1>Publish a New Artwork</h1>
            </span>
            <div
              class="wrap-input100 validate-input"
              data-validate="Please enter your title"
            >
              <input
                class="input100"
                type="text"
                name="title"
                placeholder="Title"
              />
              <span class="focus-input100"></span>
            </div>
            <div
              class="wrap-input100 validate-input"
              data-validate="Please enter your description"
            >
              <input
                class="input100"
                type="text"
                name="description"
                placeholder="Description"
              />
              <span class="focus-input100"></span>
            </div>
            <div
              class="wrap-input100 validate-input"
              data-validate="Please enter your price"
            >
              <input
                class="input100"
                type="text"
                name="price"
                placeholder="Price"
              />
              <span class="focus-input100"></span>
            </div>
            <div
              class="wrap-input100 validate-input"
              data-validate="Please enter your image(s) url"
            >
              <textarea
                class="input100"
                name="url"
                placeholder="Image URL"
              ></textarea>
              <span class="focus-input100"></span>
            </div>
            <div
              class="wrap-input100 validate-input"
              data-validate="Please enter your Visibility"
            >
              <input
                class="input100"
                type="text"
                name="visibility"
                placeholder="Visibility"
              />
              <span class="focus-input100"></span>
            </div>
            <div class="container-contact100-form-btn">
              <button class="contact100-form-btn" v-on:click="submitListing()">
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
console.log("im here");
export default {
  name: "CreateListing",
  components: {},
  data() {
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
        pagination: "no",
        mouseDrag: false,
        autoPlay: true
      }
    };
  },
  created: function () {
  },
  methods: {
    submitListing: function() {
      backend
        .post("/artlisting/create/", backend.parse({
          aTitle: this.$route.query.title,
          aDescription: this.$route.query.description,
          price: this.$route.query.price,
          artistId: "27332e3c-c356-4a12-8d0b-e35a3c970c49",
          aVisibility: "Public"
        }))
        .then(response => {
          backend
            .post(
              "/artlisting/update_post_images/" + response.data.idCode + "/",
              backend.parse({ images: [this.$route.query.url] })
            )
            .then(response => {
              console.log(response.data);
            })
            .catch(e => {
              console.log(e);
            });
          console.log(response.data);
          console.log("submit");
          this.$router.push({ path: '/' })
        })
        .catch(e => {
          console.log(e);
        });
    }
  }
};
</script>
