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
    </div>
    <div class="row">
      <div class="col-md-12">
        <div class="card">
          <div class="card-header">
            <h4>Performing a POST Request</h4>
            <label for="postFieldNum">Number of Keys to Send: </label>
            <input
              type="number"
              placeholder="2"
              class="postField form-control"
              id="postFieldNum"
              name="postFieldNum"
              v-on:change="setPostFieldNum"
              @input="updatePostField('postFieldNum',parseInt($event.target.value))"
              :value="postFieldForm.postFieldNum"
              min=1
              max=20
            /><br />
            <label for="postFieldPath">POST path to call: </label>
            <input
              type="text"
              placeholder="managers/create"
              class="postField form-control"
              id="postFieldPath"
              name="postFieldPath"
              @input="updatePostField('postFieldPath',($event.target.value))"
              :value="postFieldForm.postFieldPath"
            /><br />
            <button class="btn btn-warning" v-on:click="postFieldRequest">
              Send POST Request
            </button>
            <button class="btn btn-danger ml-1" v-on:click="clearPostFields">Reset this form</button>
          </div>
          <div class="card-body">
            <span
              v-for="n in postFieldForm.postFieldNum"
              :key="'postField-' + n"
              class="form-group"
              id="postFields"
            >
              <div>
                <label v-bind:for="'postFieldKey-' + n" class="sr-only"
                  >Key {{ n }}</label
                >
                <input
                  v-bind:name="'postFieldKey-' + n"
                  v-bind:id="'postFieldKey-' + n"
                  class="postField form-control"
                  type="text"
                  v-bind:placeholder="'Key ' + n"
                  @input="updatePostField('postFieldKey-' + n,($event.target.value))"
                  :value="postFieldForm['postFieldKey-' + n]"
                />
                <label v-bind:for="'postFieldVal-' + n" class="sr-only"
                  >Value {{ n }}</label
                >
                <input
                  v-bind:name="'postFieldVal-' + n"
                  v-bind:id="'postFieldVal-' + n"
                  class="postField form-control"
                  type="text"
                  v-bind:placeholder="'Value ' + n"
                  @input="updatePostField('postFieldVal-' + n,($event.target.value))"
                  :value="postFieldForm['postFieldVal-' + n]"
                />
                <br />
              </div>
            </span>
          </div>
        </div>
      </div>
    </div>
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
      userLoggedIn: "",
      postFieldForm: {
        postFieldNum: 3,
        postFieldPath: ''
      }
    };
  },
  created: function() {
    let vm = this;
    let tempPostFieldForm = this.loadStorage('postFieldForm')
    if (!!tempPostFieldForm)
      this.postFieldForm = tempPostFieldForm 
    backend.onFirebaseAuth(function(user) {
      vm.userLoggedIn = user.uid;
    });
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
    },
    testPostReq: function() {},
    setPostFieldNum: function(inputField) {
      // this.postFields = parseInt(inputField.target.value);
    },
    postFieldRequest: function() {
      let obj = {};
      for (let i = 0; i < this.postFieldForm.postFieldNum; i++) {
        obj[getElId("postFieldKey-" + (i + 1)).value] = getElId(
          "postFieldVal-" + (i + 1)
        ).value;
      }
      backend
        .post(getElId("postFieldPath").value, backend.parse(obj))
        .then(resp => {
          console.log(resp);
        });
    },
    loadPostFields: function() {

    },
    updatePostField: function(key,value) {
      this.postFieldForm[key] = value
      this.saveStorage('postFieldForm',this.postFieldForm)
    },
    loadStorage: function(name) {
      return JSON.parse(localStorage.getItem(name));
    },
    saveStorage: function(name, obj) {
      localStorage.setItem(name, JSON.stringify(obj));
    },
    clearPostFields: function(){
      for(let key in this.postFieldForm) {
        this.postFieldForm[key] = ''
      }
      this.postFieldForm['postFieldNum'] = 2
      localStorage.setItem('postFieldForm',{})
    }
  }
};
</script>

<style>
.postField {
  display: inline;
  width: auto;
}
</style>
