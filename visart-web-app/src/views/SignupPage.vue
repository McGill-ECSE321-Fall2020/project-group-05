
<template>
<div class="container">
      <div class="py-5 text-center">
        <h2>Registration form</h2>
      </div>

      <div class="row">
        <div class="col-md">
          <form class="needs-validation" novalidate>
            <div class="row">
              <div class="col-md-6 mb-3">
                <label for="username">Username</label>
                <input type="text" class="form-control" id="username" placeholder="" required>
                <div class="invalid-feedback">
                  Valid username is required.
                </div>
              </div>
              <div class="col-md-6 mb-3">
                <label for="displayName">Display name</label>
                <input type="text" class="form-control" id="displayName" placeholder="" required>
                <div class="invalid-feedback">
                  Valid display name is required.
                </div>
              </div>
            </div>

            <div class="mb-3">
              <label for="email">Email</label>
              <div class="input-group">
                <input type="email" class="form-control" id="email" placeholder="" required>
                <div class="invalid-feedback" style="width: 100%;">
                  Valid email is required.
                </div>
              </div>
            </div>

            <div class="mb-3">
              <label for="password">Password </label>
              <input type="password" class="form-control" id="password" placeholder="" required>
              <div class="invalid-feedback">
                Valid password required.
              </div>
            </div>
            <div class="custom-control custom-checkbox">
              <input type="checkbox" class="custom-control-input" id="isArtist">
              <label class="custom-control-label" for="isArtist">Do you want to be an Artist?</label>
            </div>

            <button class="btn btn-primary btn-lg btn-block" type="button" v-on:click="createAccount">Create Account</button>
          </form>
        </div>
      </div>
    </div>
</template>

<script>
/* eslint-disable */
var backend = require("@/tools/backend");
var storage = require("@/tools/cloud-storage");

function getElId(id) {
  return document.getElementById(id);
}

export default {
  name: "SignupPage",
  components: {},
  data: function() {
    return {
      isSuccess: true
    };
  },
  created: function() {
    let vm = this;
    backend.onFirebaseAuth(function(user) {
      if (user != null){
        vm.$router.push({path:'/'});
      }
      
    });
  },
  methods: {
    createAccount: function () {
      let vm = this;
      console.log(getElId("isArtist").value);
      backend
        .post('/customers/create', backend.parse({
          emailAddress: getElId("email")?.value,
          displayname: getElId("displayName")?.value,
          username: getElId("username")?.value,
          password: getElId("password")?.value,
          profilePicLink: "",
          profileDescription: ""
        })
        )
        .then(function (response) {
            console.log(response)
            vm.$router.push({path:'/login'});
        })
        .catch(e => {
          console.log(e)
        })
    }
  }
};
</script>

