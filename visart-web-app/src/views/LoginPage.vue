
<template>
    <form class="form-signin">
    <img class="mb-4" src="../assets/navlogo0.png" alt="" width="72" height="72">
    <h1 class="h3 mb-3 font-weight-normal">Please sign in</h1>
    <label for="inputEmail" class="sr-only">Email address</label>
    <input
        type="email"
        id="inputEmail"
        class="form-control"
        placeholder="Email address"
        name="emailAddress"
        required
        autofocus>
    <label for="inputPass" class="sr-only">Password</label>
    <input
        type="password"
        id="inputPass"
        class="form-control"
        placeholder="Password"
        name="password"
        required>
    <button class="btn btn-lg btn-primary btn-block" type="submit" v-on:click="authenticateUser">Sign in</button>
      <p class="mt-5 mb-3 text-muted">&copy; 2020-2020</p>
    </form>
</template>

<script>
/* eslint-disable */
var backend = require("@/tools/backend");
var storage = require("@/tools/cloud-storage");

function setupImage(storedImageName) {
  storage.read(storedImageName).then(url => {
    // method to get a download url
    let downloadURL = url;
    let img = document.createElement("img");
    img.src = downloadURL;
    document.getElementById("signin").append(img);
  });
}

export default {
  name: "BasicLogin",
  Component: {},
  data: function() {
    return {};
  },
  props: [],
  methods: {
    authenticateUser: function() {
      let email = document.getElementById("inputEmail").value;
      let password = document.getElementById("inputPass").value;
      backend.authenticateEmail(email, password);
      backend.onFirebaseAuth(function(user) {
        // listener function that occurs whenever signing in or signing out
      });
    }
  },
  created: function() {}
};
</script>

