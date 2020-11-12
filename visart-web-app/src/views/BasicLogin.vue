<template>
  <span class="form-signin" id="signin">
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
    <label for="testFile">Upload an Image</label>
    <input
      type="file"
      id="testFile"
      name="testFile"
      accept="image/png, image/jpeg"
    />
    <button v-on:click="uploadFile">Confirm upload</button>
  </span>
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
      console.log(email, password);
      backend.authenticateEmail(email, password);
      backend.onFirebaseAuth(function(user) {
        // listener function that occurs whenever signing in or signing out
        console.log(user);
      });
    },
    uploadFile: function() {
      let fileElement = document.getElementById("testFile");
      let file = fileElement.files[0];

      // use this method to change the file.name into a randomized filename for the cloud storage
      let filename = storage.createStoredName(file.name);
      console.log(filename);
      storage.write(filename, file).then(function(snapshot) {
        // 'then' occurs when file is uploaded
        setupImage(filename);
      });
    }
  },
  created: function() {}
};
</script>
