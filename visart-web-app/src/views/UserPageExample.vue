<template>
  <div>
    <div class="container">
      <div class="row">
        <div class="col-xl">
          <b-button v-on:click="showPublishedArt">Published Art</b-button>
        </div>
        <div class="col-xl">
          <b-button v-on:click="showPurchasedArt">PurchasedArt</b-button>
        </div>
        <div class="col-xl">
          <b-button v-on:click="showSoldArt">Sold Art</b-button>
        </div>
      </div>
    </div>
    <div class="album py-5 bg-light">
      <div class="container">
        <div class="row">
          <div class="col-md-4" v-for="(person, index) in people" :key="index">
            <div class="card mb-4 box-shadow">
              <img
                class="card-img-top"
                src="../assets/logo.png"
                alt="Card image cap"
              />
              <div class="card-body">
                <p class="card-text">{{ person.title }}</p>
                <div class="d-flex justify-content-between align-items-center">
                  <div class="btn-group">
                    <button
                      type="button"
                      class="btn btn-sm btn-outline-secondary"
                    >
                      Go to listing
                    </button>
                    <button
                      type="button"
                      class="btn btn-sm btn-outline-secondary"
                    >
                      Delete
                    </button>
                  </div>
                  <small class="text-muted">{{ person.price }}</small>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
// @ is an alias to /src
/* eslint-disable */
import HelloWorld from "@/components/HelloWorld.vue";

// backend.js has on it: backend.get(path,params), backend.post(path,data), backend.parse(json) => post data
var backend = require("@/tools/backend");

export default {
  name: "UserPage",
  Component: {
    HelloWorld
  },
  data: function() {
    return {
      count: 0,
      people: [
        { title: "published", price: "9$" },
        { title: "purchased", price: "1$" },
        { title: "sold", price: "10$" },
        { title: "sold2", price: "8$" }
      ]
    };
  },
  props: [],
  methods: {
    showPublishedArt: function() {
      this.count++;
      console.log(this.count);
    },
    showPurchasedArt: function() {
      this.people = [];
    },
    showSoldArt: function() {
      this.count++;
      console.log(this.count);
    }
  },
  created: function() {
    backend
      .get("/managers/get_all")
      .then(response => {
        console.log(response.data);
      })
      .catch(e => {
        console.log(e);
      });

    // for POST, create your data as an object:
    var dat = {
      emailAddress: "email@gmail.com",
      displayname: "dingdong4",
      username: "hiIAmBilly4",
      password: "password",
      profilePicLink: "dsa",
      profileDescription: "fdasf"
    };
    // then perform the POST method, but first you must PARSE the object using backend.parse
    // Spring REST API doesn't like "json" objects, but does just fine with "form data". backend.parse, converts "json" to "form data".
    backend.post("/managers/create", backend.parse(dat));
  }
};
</script>
