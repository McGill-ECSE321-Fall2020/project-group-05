<template>
  <span class="container-fluid">
    <div class="row">
      <div class="col-md-4">
        <div class="card">
          <div class="card-header">
            <h4>Login for Testing</h4>
            <p>Logged in User: {{ userLoggedIn }}</p>
          </div>
          <div class="card-body">
            <span class="form-group" id="signin">
              <label for="inputEmail" class="sr-only">Email Address</label>
              <input
                type="email"
                id="inputEmail"
                class="form-control"
                name="emailAddress"
                placeholder="an email"
                required
                autofocus
              />
              <label for="inputPass" class="sr-only">Password</label>
              <input
                type="password"
                id="inputPass"
                class="form-control"
                name="password"
                placeholder="the password"
                required
              />
              <button
                class="btn btn-lg btn-primary btn-block"
                v-on:click="authenticateUser"
              >
                Sign in
              </button>
              <button
                class="btn btn-lg btn-primary btn-block"
                v-on:click="unauthenticateUser"
              >
                Sign out
              </button>
            </span>
          </div>
        </div>
      </div>
      <div class="col-md-4">
        <div class="card">
          <div class="card-header">
            <h4>Example of Uploading an Image</h4>
            <p>
              Image Link:
              <a v-bind:href="uploadedImgLink">{{ uploadedImgLink }}</a>
            </p>
          </div>
          <div class="card-body">
            <label for="uploadImageFile">Upload an Image</label>
            <input
              type="file"
              id="uploadImageFile"
              name="uploadImageFile"
              accept="image/png, image/jpeg"
              class="form-control-file"
            />
            <button class="btn btn-success mt-1" v-on:click="uploadImage">
              Confirm upload
            </button>
          </div>
        </div>
      </div>
      <div class="col-md-4"></div>
    </div>
    <div class="row"></div>
    <div class="row"></div>
  </span>
</template>

<script>
/* eslint-disable */
var backend = require("@/tools/backend");
var storage = require("@/tools/cloud-storage");

function getElId(id) {
  return document.getElementById(id);
}

export default {
  name: "ExamplePage",
  components: {},
  data: function() {
    return {
      uploadedImgLink: "",
      userLoggedIn: ""
    };
  },
  created: function() {
    let vm = this
    backend.onFirebaseAuth(function(user){
      vm.userLoggedIn = user.uid
    })
  },
  methods: {
    authenticateUser: function() {
      backend.authenticateEmail(
        getElId("inputEmail").value,
        getElId("inputPass").value
      );
    },
    unauthenticateUser: function() {
      backend.unauthenticate();
    },
    uploadImage: function() {
      let vm = this; // temporarily store the component this
      let files = getElId("uploadImageFile").files;
      if (files.length > 0) {
        let filename = storage.createStoredName(files[0].name);
        storage
          .write(filename, files[0])
          .then(resp => {
            return storage.read(filename);
          })
          .then(downloadUrl => {
            vm.uploadedImgLink = downloadUrl;
          });
      }
    }
  }
};
</script>
